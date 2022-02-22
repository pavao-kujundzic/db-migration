package hr.sedamit.bss.databasemigrations.sqlserver.repository;

import hr.sedamit.bss.databasemigrations.sqlserver.entity.SqlServerEntity;
import org.springframework.data.repository.CrudRepository;

public interface SqlServerRepository extends CrudRepository<SqlServerEntity, Long> {

}
