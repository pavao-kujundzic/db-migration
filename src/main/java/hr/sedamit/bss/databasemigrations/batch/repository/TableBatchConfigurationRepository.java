package hr.sedamit.bss.databasemigrations.batch.repository;

import hr.sedamit.bss.databasemigrations.batch.model.TableBatchConfiguration;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TableBatchConfigurationRepository extends JpaRepository<TableBatchConfiguration, Integer> {


    TableBatchConfiguration findByTableName(String tableName);

}
