package hr.sedamit.bss.databasemigrations.step;

import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.annotation.AfterWrite;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.item.ItemWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import hr.sedamit.bss.databasemigrations.batch.model.TableBatchConfiguration;
import hr.sedamit.bss.databasemigrations.batch.repository.TableBatchConfigurationRepository;
import hr.sedamit.bss.databasemigrations.util.DatabaseUtilService;

@Component
@StepScope
public class MigrationWriter implements ItemWriter<Map<String, List<Map<String, Object>>>> {

	private final static Logger LOGGER = LoggerFactory.getLogger(MigrationWriter.class);

	@Autowired
	private Environment env;

	@Autowired
	@Qualifier("sourceJdbcTemplate")
	JdbcTemplate sourceJdbcTemplate;

	@Autowired
	@Qualifier("destinationJdbcTemplate")
	JdbcTemplate destinationJdbcTemplate;

	@Value("#{jobParameters['updateTables']}")
	private String updateTablesString;

	@Value("#{jobParameters['appendTables']}")
	private String appendTablesString;

	@Autowired
	private TableBatchConfigurationRepository tableBatchConfigurationRepository;

	private DatabaseUtilService databaseUtilService;

	@Override
	public void write(List<? extends Map<String, List<Map<String, Object>>>> data) throws Exception {
		LOGGER.info("writer start...");
		for (Map<String, List<Map<String, Object>>> job : data) {
			for (Entry<String, List<Map<String, Object>>> table : job.entrySet()) {
				String[] name = table.getKey() != null ? table.getKey().split("\\.") : null;
				if (name != null && name.length > 1) {
					String tableName = name[1];
					String schemaName = name[0];

					databaseUtilService = new DatabaseUtilService(
							env.getProperty("batch.destination.datasource.driver"),
							env.getProperty("batch.source.datasource.driver"),
							sourceJdbcTemplate,
							destinationJdbcTemplate,
							schemaName,
							tableName);

					databaseUtilService.createTable(false);
					databaseUtilService.insertDataMap(table.getValue());
				}
			}
		}
		LOGGER.info("writer end...");
	}

	@AfterWrite
	public void test() {
		LOGGER.info("@AfterWrite start...");
		Map<String, Object> lastInsert = databaseUtilService.fetchLastInsertRow();

		Optional<TableBatchConfiguration> tableConfigurationOptional = tableBatchConfigurationRepository
				.findByTableName(databaseUtilService.getSourceSchema() + "." + databaseUtilService.getSourceTable());

		if (tableConfigurationOptional.isPresent() && lastInsert != null) {
			TableBatchConfiguration tableBatchConfiguration = tableConfigurationOptional.get();
			tableBatchConfiguration
					.setKeyColumnValue(lastInsert.get(tableBatchConfiguration.getKeyColumnName()).toString());

			tableBatchConfigurationRepository.save(tableBatchConfiguration);
		}
	}
}
