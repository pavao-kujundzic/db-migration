package hr.sedamit.bss.databasemigrations.batch.model;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
@Data
public class TableProcessConfiguration {

    @Id
    private int id;

    private String tableName;
    private long batchSize;
    private String keyColumnName;
    private String keyColumnValue;
    private String keyColumnDataType;
    private boolean active;


}
