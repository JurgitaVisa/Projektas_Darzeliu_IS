package it.akademija.user;

import org.springframework.data.jpa.repository.JpaRepository;

public interface ParentDetailsDAO extends JpaRepository<ParentDetails, String> {

	ParentDetails findByPersonalCode(String personalCode);
}
