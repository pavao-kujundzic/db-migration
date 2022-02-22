package hr.sedamit.bss.databasemigrations.step;

import hr.sedamit.bss.databasemigrations.postgre.entity.T1;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.item.ItemWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

@Component
@StepScope
public class PostgreWriter implements ItemWriter<Map<String, List<Map<String, Object>>>> {

    @Autowired
    @Qualifier("postgreSqlJdbcTemplate")
    JdbcTemplate postgreSqlJdbcTemplate;

    @Value("#{jobParameters['updateTables']}")
    private String updateTablesString;

    @Value("#{jobParameters['appendTables']}")
    private String appendTablesString;

    @Override
    public void write(List<? extends Map<String, List<Map<String, Object>>>> data) throws Exception {
        List<String> appendTables = Arrays.asList(appendTablesString.split("\\s*,\\s*"));
        List<String> updateTables = Arrays.asList(updateTablesString.split("\\s*,\\s*"));

        Map<String, List<Map<String, Object>>> items = data.get(0);

        List<Map<String, Object>> table = items.get(appendTables.get(0));
        Map<String, Object> rows = table.get(0);


        StringBuilder stringBuilder = new StringBuilder();
        String query = stringBuilder.append("INSERT INTO " + appendTables.get(0))
                .append(rows.keySet() + " ")
                .append(" VALUES")
                .append(rows.values().toString()).append(";")
                .toString()
                .replace("[", "(")
                .replace("]", ")");


        System.out.println(query);

    }
}
