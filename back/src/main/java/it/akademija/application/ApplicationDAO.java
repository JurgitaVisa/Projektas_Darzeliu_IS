package it.akademija.application;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface ApplicationDAO extends JpaRepository<Application, Long> {

	boolean existsApplicationByChildPersonalCode(String childPersonalCode);

	@Query("SELECT a FROM Application a WHERE a.childPersonalCode LIKE (concat(?1 ,'%'))")
	Page<ApplicationInfo> findByIdContaining(String childPersonalCode, Pageable pageable);

	@Query("SELECT new it.akademija.application.ApplicationInfo(a.id, a.childPersonalCode, a.childName, a.childSurname, a.choise1.name, a.choise2.name, a.choise3.name, a.choise4.name, a.choise5.name) FROM Application a")
	Page<ApplicationInfo> findAllApplications(Pageable pageable);


	//get all, kurių statusas yra "Pateikta"
	
	//get all kur username yra String username
	
	
}
