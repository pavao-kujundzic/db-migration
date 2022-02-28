package hr.sedamit.bss.databasemigrations.step;

import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.item.ItemWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import hr.sedamit.bss.databasemigrations.util.DatabaseUtilService;

@Component
@StepScope
public class PostgreWriter implements ItemWriter<Map<String, List<Map<String, Object>>>> {

	private final static Logger LOGGER = LoggerFactory.getLogger(PostgreWriter.class);

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

	@Override
	public void write(List<? extends Map<String, List<Map<String, Object>>>> data) throws Exception {
		LOGGER.info("writer start...");
		for (Map<String, List<Map<String, Object>>> job : data) {
			for (Entry<String, List<Map<String, Object>>> table : job.entrySet()) {
				String[] name = table.getKey() != null ? table.getKey().split("\\.") : null;
				if (name != null && name.length > 1) {
					String tableName = name[1];
					String schemaName = name[0];

					DatabaseUtilService service = new DatabaseUtilService(
							env.getProperty("batch.destination.datasource.driver"),
							env.getProperty("batch.source.datasource.driver"),
							sourceJdbcTemplate,
							destinationJdbcTemplate,
							schemaName,
							tableName);

					service.createTable(false);
					service.insertDataMap(table.getValue());
				}
			}
		}
		LOGGER.info("writer end...");
	}
}
