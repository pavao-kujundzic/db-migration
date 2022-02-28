package hr.sedamit.bss.databasemigrations.step;

import hr.sedamit.bss.databasemigrations.batch.model.TableBatchConfiguration;
import hr.sedamit.bss.databasemigrations.batch.repository.TableBatchConfigurationRepository;
import hr.sedamit.bss.databasemigrations.service.DatabaseService;
import hr.sedamit.bss.databasemigrations.util.mssql.MsSqlTable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.NonTransientResourceException;
import org.springframework.batch.item.ParseException;
import org.springframework.batch.item.UnexpectedInputException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Component
@StepScope
public class MigrationReader implements ItemReader<Map<String, List<Map<String, Object>>>> {
    private final static Logger LOGGER = LoggerFactory.getLogger(MsSqlTable.class);
    @Autowired
    @Qualifier("sourceJdbcTemplate")
    JdbcTemplate sourceJdbcTemplate;
    @Value("#{jobParameters['updateTables']}")
    private String updateTablesString;
    @Value("#{jobParameters['appendTables']}")
    private String appendTablesString;
    @Autowired
    private TableBatchConfigurationRepository tableBatchConfigurationRepository;
    @Autowired
    private DatabaseService databaseService;

    private boolean alreadyFetched = false;

    @Override
    public Map<String, List<Map<String, Object>>> read() throws Exception, UnexpectedInputException, ParseException, NonTransientResourceException {

        if (!alreadyFetched) {
            List<String> updateTables = updateTablesString.equals("") ? new ArrayList() : Arrays.asList(updateTablesString.split("\\s*,\\s*"));
            List<String> appendTables = appendTablesString.equals("") ? new ArrayList() : Arrays.asList(appendTablesString.split("\\s*,\\s*"));
            List<String> tables = Stream.concat(updateTables.stream(), appendTables.stream())
                    .collect(Collectors.toList());
            Map<String, List<Map<String, Object>>> tableData = new HashMap();
            for (String table : tables) {
                try {
                    tableData.put(table, fetchTableRows(table));
                } catch (Exception exc) {
                    LOGGER.error("Exception fetching data from " + table);
                }
            }
            alreadyFetched = true;
            return tableData;
        } else {
            return null;
        }
    }

    private List<Map<String, Object>> fetchTableRows(String table) {
        Optional<TableBatchConfiguration> tableBatchConfiguration = tableBatchConfigurationRepository.findByTableName(table);
        if (tableBatchConfiguration.isPresent()) {
            String[] parts = table.split("\\.");
            String query = databaseService.selectFromTable(parts[0], parts[1], tableBatchConfiguration.get());
            List<Map<String, Object>> tableRows = sourceJdbcTemplate.queryForList(query);
            return tableRows;
        } else {
            return new ArrayList<>();
        }
    }
}