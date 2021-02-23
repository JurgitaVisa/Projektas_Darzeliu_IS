package it.akademija.application.management;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class ApplicationManager {

	// apgalvoti, kaip įgyvendinti prašymų pateikimo aktyvavimą/deaktyvavimą
	// kas nutinka su prašymais, kai aktyvuojama/deaktyvuojama?
	// paleisti eilių sudarymą per čia?
	// paleisti eilių patvirtinimą

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	@Column(name = "application_status")
	private boolean applicationStatus = false;

}
