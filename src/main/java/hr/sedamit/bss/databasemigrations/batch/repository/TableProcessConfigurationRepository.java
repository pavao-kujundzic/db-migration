package hr.sedamit.bss.databasemigrations.batch.repository;

import hr.sedamit.bss.databasemigrations.batch.model.TableProcessConfiguration;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TableProcessConfigurationRepository extends JpaRepository<TableProcessConfiguration, Integer> {
}
