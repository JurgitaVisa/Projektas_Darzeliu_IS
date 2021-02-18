package it.akademija.application;

import org.springframework.data.jpa.repository.JpaRepository;

public interface ApplicationDAO extends JpaRepository<Application, Long> {

	boolean existsApplicationByChildPersonalCode(String childPersonalCode);

	

	//get all, kurių statusas yra "Pateikta"
	
	//get all kur username yra String username
	
	
}
