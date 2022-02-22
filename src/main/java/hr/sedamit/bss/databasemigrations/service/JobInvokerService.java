package hr.sedamit.bss.databasemigrations.service;

import hr.sedamit.bss.databasemigrations.command.TableCommand;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.JobParametersInvalidException;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.repository.JobExecutionAlreadyRunningException;
import org.springframework.batch.core.repository.JobInstanceAlreadyCompleteException;
import org.springframework.batch.core.repository.JobRestartException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Service
public class JobInvokerService {

    @Autowired
    private JobLauncher jobLauncher;

    @Autowired
    private Job migrateDataJob;

    public String invokeJob(List<String> appendTables, List<String> updateTables) throws Exception {

        String appendTablesString = String.join(",", appendTables);
        String updateTablesString = String.join(",", updateTables);
        JobParameters jobParametersBuilder = new JobParametersBuilder()
                .addString("appendTables", appendTablesString)
                .addString("updateTables", updateTablesString)
                .addLong("time", System.currentTimeMillis())
                .toJobParameters();

        jobLauncher.run(migrateDataJob, jobParametersBuilder);
        return "";
    }


}
