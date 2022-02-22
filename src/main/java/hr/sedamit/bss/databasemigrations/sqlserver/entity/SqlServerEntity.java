package hr.sedamit.bss.databasemigrations.sqlserver.entity;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Data
@Entity
@Table(schema = "test_entity")
public class SqlServerEntity {
    @Id
    private Long id;


}
