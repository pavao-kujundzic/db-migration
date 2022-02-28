package hr.sedamit.bss.databasemigrations.batch.model;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Data
@Table(name = "table_batch_configuration", schema = "public")
public class TableBatchConfiguration {

    @Id
    @Column(name = "table_name")
    private String tableName;
    @Column(name = "batch_size")
    private int batchSize;
    @Column(name = "key_column_name")
    private String keyColumnName;
    @Column(name = "key_column_value")
    private String keyColumnValue;
    @Column(name = "active")
    private boolean active;


}
