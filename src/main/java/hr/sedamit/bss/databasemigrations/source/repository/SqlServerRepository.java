package hr.sedamit.bss.databasemigrations.source.repository;

import org.springframework.data.repository.CrudRepository;

import hr.sedamit.bss.databasemigrations.source.entity.SqlServerEntity;

public interface SqlServerRepository extends CrudRepository<SqlServerEntity, Long> {

}
