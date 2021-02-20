package it.akademija.kindergarten;

import java.util.List;
import java.util.Set;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface KindergartenDAO extends JpaRepository<Kindergarten, String> {

	void deleteByName(String name);

	Kindergarten findByName(String name);

	@Query("SELECT new Kindergarten(k.id, k.name , k.address, k.elderate, k.capacityAgeGroup2to3, k.capacityAgeGroup3to6) FROM Kindergarten k WHERE LOWER(k.name) LIKE LOWER(concat('%', ?1,'%'))")
	Page<Kindergarten> findByNameContainingIgnoreCase(String name, Pageable pageable);

	@Query("SELECT new Kindergarten(k.id, k.name , k.address, k.elderate, k.capacityAgeGroup2to3, k.capacityAgeGroup3to6) FROM Kindergarten k")
	Page<Kindergarten> findAllKindergarten(Pageable pageable);

	// List<Kindergarten> findByNameContainingIgnoreCase(String name);

	@Query("SELECT DISTINCT(k.elderate) FROM Kindergarten k")
	Set<String> findDistinctElderates();

	@Query("SELECT k FROM Kindergarten k WHERE k.capacityAgeGroup2to3>0 OR k.capacityAgeGroup3to6>0")
	List<Kindergarten> findAllWithNonZeroCapacity(Sort ascending);

}
