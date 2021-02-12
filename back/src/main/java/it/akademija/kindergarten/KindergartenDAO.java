package it.akademija.kindergarten;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface KindergartenDAO extends JpaRepository<Kindergarten, String> {

	void deleteByName(String name);

	Page<Kindergarten> findByNameContainingIgnoreCase(String name, Pageable pageable);

	Page<Kindergarten> findAll(Pageable pageable);
	
	List<Kindergarten> findByNameContainingIgnoreCase(String name);

}
