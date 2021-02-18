package it.akademija.application;

import java.time.LocalDate;

import it.akademija.application.priorities.Priorities;
import it.akademija.kindergartenchoise.KindergartenChoiseDTO;
import it.akademija.user.ParentDetails;
import it.akademija.user.ParentDetailsDTO;
import it.akademija.user.UserDTO;

public class ApplicationDTO {
	
	private String childName;

	private String childSurname;

	private String childPersonalCode;
	
	private LocalDate birthdate;	
	
	private Priorities priorities;
	
	private UserDTO mainGuardian;		
	
	private ParentDetailsDTO additionalGuardian;
	
	private KindergartenChoiseDTO kindergartenChoises;

	public ApplicationDTO() {
		
	}	

	public ApplicationDTO(String childName, String childSurname, String childPersonalCode, LocalDate birthdate,
			Priorities priorities, UserDTO mainGuardian, ParentDetailsDTO additionalGuardian,
			KindergartenChoiseDTO kindergartenChoises) {
		super();
		this.childName = childName;
		this.childSurname = childSurname;
		this.childPersonalCode = childPersonalCode;
		this.birthdate = birthdate;
		this.priorities = priorities;
		this.mainGuardian = mainGuardian;
		this.additionalGuardian = additionalGuardian;
		this.kindergartenChoises = kindergartenChoises;
	}



	public String getChildName() {
		return childName;
	}

	public void setChildName(String childName) {
		this.childName = childName;
	}

	public String getChildSurname() {
		return childSurname;
	}

	public void setChildSurname(String childSurname) {
		this.childSurname = childSurname;
	}

	public String getChildPersonalCode() {
		return childPersonalCode;
	}

	public void setChildPersonalCode(String childPersonalCode) {
		this.childPersonalCode = childPersonalCode;
	}

	public LocalDate getBirthdate() {
		return birthdate;
	}

	public void setBirthdate(LocalDate birthdate) {
		this.birthdate = birthdate;
	}		

	public UserDTO getMainGuardian() {
		return mainGuardian;
	}

	public void setMainGuardian(UserDTO mainGuardian) {
		this.mainGuardian = mainGuardian;
	}

	public ParentDetailsDTO getAdditionalGuardian() {
		return additionalGuardian;
	}

	public void setAdditionalGuardian(ParentDetailsDTO additionalGuardian) {
		this.additionalGuardian = additionalGuardian;
	}

	public KindergartenChoiseDTO getKindergartenChoises() {
		return kindergartenChoises;
	}

	public void setKindergartenChoises(KindergartenChoiseDTO kindergartenChoises) {
		this.kindergartenChoises = kindergartenChoises;
	}

	public Priorities getPriorities() {
		return priorities;
	}

	public void setPriorities(Priorities priorities) {
		this.priorities = priorities;
	}
	
	
	
	
}
