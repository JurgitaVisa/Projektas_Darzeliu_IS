package it.akademija.application;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface ApplicationDAO extends JpaRepository<Application, Long> {

	boolean existsApplicationByChildPersonalCode(String childPersonalCode);

	@Query("SELECT a FROM Application a WHERE a.childPersonalCode LIKE (concat(?1 ,'%'))")
	Page<ApplicationInfo> findByIdContaining(String childPersonalCode, Pageable pageable);

	@Query("SELECT new it.akademija.application.ApplicationInfo(a.id, a.childPersonalCode, a.childName, a.childSurname, k1.name, k2.name, k3.name , k4.name, k5.name ) FROM Application a LEFT JOIN Kindergarten k1 ON a.choise1.id=k1.id LEFT JOIN Kindergarten k2 ON a.choise2.id=k2.id LEFT JOIN Kindergarten k3 ON a.choise3.id=k3.id LEFT JOIN Kindergarten k4 ON a.choise4.id=k4.id LEFT JOIN Kindergarten k5 ON a.choise5.id=k5.id")
	Page<ApplicationInfo> findAllApplications(Pageable pageable);

	// get all, kurių statusas yra "Pateikta"

	// get all kur username yra String username

}
