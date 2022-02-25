package hr.sedamit.bss.databasemigrations.service;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.util.List;

@Service
public class JobInvokerService {

    @Autowired
    private JobLauncher jobLauncher;

    @Autowired
    private Job migrateDataJob;

    public String invokeJob(List<String> appendTables, List<String> updateTables) throws Exception {

        String appendTablesString = appendTables != null ? String.join(",", appendTables) : "";
        String updateTablesString = updateTables != null ? String.join(",", updateTables) : "";


        JobParameters jobParametersBuilder = new JobParametersBuilder()
                .addString("appendTables", appendTablesString)
                .addString("updateTables", updateTablesString)
                .addLong("time", System.currentTimeMillis())
                .toJobParameters();

        jobLauncher.run(migrateDataJob, jobParametersBuilder);
        return "";
    }


}
