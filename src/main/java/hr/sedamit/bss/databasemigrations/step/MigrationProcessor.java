package hr.sedamit.bss.databasemigrations.step;

import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;



import java.util.Arrays;
import java.util.List;
import java.util.Map;

@Component
@StepScope
public class MigrationProcessor implements ItemProcessor
        <Map<String, List<Map<String, Object>>>, Map<String, List<Map<String, Object>>>> {

    @Value("#{jobParameters['updateTables']}")
    private String updateTablesString;

    @Value("#{jobParameters['appendTables']}")
    private String appendTablesString;

    @Override
    public Map<String, List<Map<String, Object>>> process(Map<String, List<Map<String, Object>>> data) throws Exception {
        return data;
    }
}
