/**
 *
 */
package hr.sedamit.bss.databasemigrations.service;

import hr.sedamit.bss.databasemigrations.batch.model.TableBatchConfiguration;
import hr.sedamit.bss.databasemigrations.util.mssql.MsSqlTable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.env.Environment;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import hr.sedamit.bss.databasemigrations.util.DatabaseUtilService;

/**
 * Created by J0ka on 22/02/22
 *
 */
@Service
public class DatabaseService {

    @Autowired
    private Environment env;

    @Autowired
    @Qualifier("sourceJdbcTemplate")
    JdbcTemplate sourceJdbcTemplate;

    @Autowired
    @Qualifier("destinationJdbcTemplate")
    JdbcTemplate destinationJdbcTemplate;

    public void createTable() throws Exception {
        DatabaseUtilService service = new DatabaseUtilService(
                env.getProperty("batch.destination.datasource.driver"),
                env.getProperty("batch.source.datasource.driver"),
                sourceJdbcTemplate,
                destinationJdbcTemplate,
                "billing",
                "payment_order");

        service.createTable(false);

    }


    public String selectFromTable(String schema, String table, TableBatchConfiguration tableBatchConfiguration) {
        return MsSqlTable.generateSelectQuerys(schema, table, tableBatchConfiguration);
    }
}
