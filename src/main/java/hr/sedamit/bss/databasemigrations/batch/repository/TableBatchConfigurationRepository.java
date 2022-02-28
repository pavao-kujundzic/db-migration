package hr.sedamit.bss.databasemigrations.batch.repository;

import hr.sedamit.bss.databasemigrations.batch.model.TableBatchConfiguration;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TableBatchConfigurationRepository extends JpaRepository<TableBatchConfiguration, Integer> {

    Optional<TableBatchConfiguration> findByTableName(String tableName);

}
