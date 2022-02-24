package hr.sedamit.bss.databasemigrations.destination.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import hr.sedamit.bss.databasemigrations.destination.entity.T1;

public interface PostgreRepository extends JpaRepository<T1, Integer> {

}
