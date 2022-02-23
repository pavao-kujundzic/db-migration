package hr.sedamit.bss.databasemigrations.step;

import hr.sedamit.bss.databasemigrations.batch.repository.TableProcessConfigurationRepository;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;


import java.util.Arrays;
import java.util.List;
import java.util.Map;

@Component
@StepScope
public class MigrationProcessor implements ItemProcessor<Map<String, List<Map<String, Object>>>, Map<String, List<Map<String, Object>>>> {

    @Value("#{jobParameters['updateTables']}")
    private String updateTablesString;

    @Value("#{jobParameters['appendTables']}")
    private String appendTablesString;

    @Autowired
    private TableProcessConfigurationRepository tableProcessConfigurationRepository;


    @Override
    public Map<String, List<Map<String, Object>>> process(Map<String, List<Map<String, Object>>> data) throws Exception {
        List<String> appendTables = Arrays.asList(appendTablesString.split("\\s*,\\s*"));





        return data;
    }
}
