package it.akademija.kindergarten;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

public interface KindergartenDAO extends JpaRepository<Kindergarten, Long> {

	Kindergarten findByName(String name);
	
	List<Kindergarten> findAllOrderByNameAsc();
}
