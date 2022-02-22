package hr.sedamit.bss.databasemigrations.configuration;

import hr.sedamit.bss.databasemigrations.listener.JobCompletionNotificationListener;
import hr.sedamit.bss.databasemigrations.step.MigrationProcessor;
import hr.sedamit.bss.databasemigrations.step.PostgreWriter;
import hr.sedamit.bss.databasemigrations.step.SqlServerReader;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;
import java.util.Map;

@Configuration
@EnableBatchProcessing
public class BatchConfiguration {

    @Autowired
    private SqlServerReader sqlServerReader;
    @Autowired
    private MigrationProcessor migrationProcessor;
    @Autowired
    private PostgreWriter postgreWriter;
    @Autowired
    public JobBuilderFactory jobBuilderFactory;
    @Autowired
    public StepBuilderFactory stepBuilderFactory;

    @Bean
    public Job migrateDataJob(JobCompletionNotificationListener jobListener) {
        return jobBuilderFactory.get("migrateDataJob")
                .incrementer(new RunIdIncrementer())
                .listener(jobListener)
                .start(step())
                .build();
    }

    @Bean
    public Step step() {
        return stepBuilderFactory.get("migrationStep")
                .<Map<String, List<Map<String, Object>>>, Map<String, List<Map<String, Object>>>>chunk(Integer.MAX_VALUE)
                .reader(sqlServerReader)
                .processor(migrationProcessor)
                .writer(postgreWriter)
                .build();
    }


}
