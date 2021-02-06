package it.akademija.kindergarten;


import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface KindergartenDAO extends JpaRepository<Kindergarten, String> {

	Kindergarten findByNameIgnoreCase(String name);
	
	Page <Kindergarten> findAll(Pageable pageable);
	
	
}
