package hr.sedamit.bss.databasemigrations.postgre.repository;

import hr.sedamit.bss.databasemigrations.postgre.entity.T1;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostgreRepository extends JpaRepository<T1, Integer> {

}
