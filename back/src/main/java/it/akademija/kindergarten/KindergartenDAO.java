package it.akademija.kindergarten;

import java.util.List;
import java.util.Set;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface KindergartenDAO extends JpaRepository<Kindergarten, String> {

	void deleteByName(String name);
	
	Kindergarten findByName(String name);

	Page<Kindergarten> findByNameContainingIgnoreCase(String name, Pageable pageable);

	Page<Kindergarten> findAll(Pageable pageable);
	
	List<Kindergarten> findByNameContainingIgnoreCase(String name);
	
	@Query("SELECT DISTINCT(k.elderate) FROM Kindergarten k")
	Set<String> findDistinctElderates();

}
