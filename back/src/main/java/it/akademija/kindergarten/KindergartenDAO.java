package it.akademija.kindergarten;

import org.springframework.data.jpa.repository.JpaRepository;

public interface KindergartenDAO extends JpaRepository<Kindergarten, Long> {

	Kindergarten findByNameIgnoreCase(String name);
	
	
}
