package hr.sedamit.bss.databasemigrations.step;

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

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Component
@StepScope
public class SqlServerReader implements ItemReader<Map<String, List<Map<String, Object>>>> {

    @Autowired
    @Qualifier("sqlServerJdbcTemplate")
    JdbcTemplate sqlServerJdbcTemplate;

    @Value("#{jobParameters['updateTables']}")
    private String updateTablesString;

    @Value("#{jobParameters['appendTables']}")
    private String appendTablesString;

    private boolean alreadyFetched = false;

    @Override
    public Map<String, List<Map<String, Object>>> read() throws Exception, UnexpectedInputException, ParseException, NonTransientResourceException {

        if (!alreadyFetched) {
            List<String> updateTables = Arrays.asList(updateTablesString.split("\\s*,\\s*"));
            List<String> appendTables = Arrays.asList(appendTablesString.split("\\s*,\\s*"));

            List<String> tables = Stream.concat(updateTables.stream(), appendTables.stream())
                    .collect(Collectors.toList());

            Map<String, List<Map<String, Object>>> tableData = new HashMap();
            for (String table : tables) {
                try {
                    List<Map<String, Object>> rows = sqlServerJdbcTemplate.queryForList("select * from " + table);
                    System.out.println("Read from " + table);
                    tableData.put(table, rows);
                } catch (Exception exc) {
                    System.out.println("Exception fetching data from " + table);
                }
            }
            alreadyFetched = true;
            return tableData;
        } else {
            return null;
        }
    }
}
