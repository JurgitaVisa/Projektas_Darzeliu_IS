
package it.akademija.application;

import java.time.LocalDate;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import org.springframework.format.annotation.DateTimeFormat;

import it.akademija.application.priorities.Priorities;
import it.akademija.kindergarten.Kindergarten;
import it.akademija.user.ParentDetails;
import it.akademija.user.User;

@Entity
public class Application {

	@Id
	@Column(name = "application_id")
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	@Column(name = "date_of_submition")
	LocalDate submitedAt;

	private ApplicationStatus status;

	@NotEmpty(message = "Vardas privalomas!")
	@Size(min = 2, max = 70)
	@Pattern(regexp = "^\\p{L}+(?: \\p{L}+)*$")
	private String childName;

	@NotEmpty(message = "Pavardė privaloma!")
	@Size(min = 2, max = 70)
	@Pattern(regexp = "^\\p{L}+(?: \\p{L}+)*$")
	private String childSurname;

	@Pattern(regexp = "^(?!\\s*$)[0-9\\s]{11}$|")
	private String childPersonalCode;

	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private LocalDate birthdate;

	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "priorities_id")
	private Priorities priorities;

	@ManyToOne(cascade = { CascadeType.MERGE, CascadeType.DETACH })
	@JoinColumn(name = "user_id")
	private User mainGuardian;

	@ManyToOne(cascade = { CascadeType.MERGE, CascadeType.DETACH })
	@JoinColumn(name = "additional_guardian_id")
	private ParentDetails additionalGuardian;
	// trinat application, istrinti parent details, jei jis nesusijęs su kt
	// prašymais. atgalinio ryšio nėra?
	// kaip jeigu jau yra toks antras tėvas? jei yra-- pridėti, jei ne-- sukurti ir
	// pridėti

	@ManyToOne(cascade = { CascadeType.MERGE, CascadeType.DETACH, CascadeType.REFRESH })
	private Kindergarten choise1;

	@ManyToOne(cascade = {  CascadeType.MERGE, CascadeType.DETACH, CascadeType.REFRESH })
	private Kindergarten choise2;
	
	@ManyToOne(cascade = {  CascadeType.MERGE, CascadeType.DETACH, CascadeType.REFRESH })
	private Kindergarten choise3;
	
	@ManyToOne(cascade = {  CascadeType.MERGE, CascadeType.DETACH, CascadeType.REFRESH })
	private Kindergarten choise4;
	
	@ManyToOne(cascade = {  CascadeType.MERGE, CascadeType.DETACH, CascadeType.REFRESH })
	private Kindergarten choise5;

	public Application() {

	}

	public Application(String childName, String childSurname, String childPersonalCode, LocalDate birthdate,
			User mainGuardian, ParentDetails additionalGuardian) {

		this.childName = childName;
		this.childSurname = childSurname;
		this.childPersonalCode = childPersonalCode;
		this.birthdate = birthdate;
		this.mainGuardian = mainGuardian;
		this.additionalGuardian = additionalGuardian;
	}

	public ApplicationStatus getStatus() {
		return status;
	}

	public void setStatus(ApplicationStatus status) {
		this.status = status;
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
	

	public Kindergarten getChoise1() {
		return choise1;
	}

	public void setChoise1(Kindergarten choise1) {
		this.choise1 = choise1;
	}

	public Kindergarten getChoise2() {
		return choise2;
	}

	public void setChoise2(Kindergarten choise2) {
		this.choise2 = choise2;
	}	

	public Kindergarten getChoise3() {
		return choise3;
	}

	public void setChoise3(Kindergarten choise3) {
		this.choise3 = choise3;
	}

	public Kindergarten getChoise4() {
		return choise4;
	}

	public void setChoise4(Kindergarten choise4) {
		this.choise4 = choise4;
	}

	public Kindergarten getChoise5() {
		return choise5;
	}

	public void setChoise5(Kindergarten choise5) {
		this.choise5 = choise5;
	}

	public Long getId() {
		return id;
	}

	public User getMainGuardian() {
		return mainGuardian;
	}

	public void setMainGuardian(User mainGuardian) {
		this.mainGuardian = mainGuardian;
	}

	public ParentDetails getAdditionalGuardian() {
		return additionalGuardian;
	}

	public void setAdditionalGuardian(ParentDetails additionalGuardian) {
		this.additionalGuardian = additionalGuardian;
	}

	public LocalDate getSubmitedAt() {
		return submitedAt;
	}

	public void setSubmitedAt() {
		this.submitedAt = LocalDate.now();
	}

	public Priorities getPriorities() {
		return priorities;
	}

	public void setPriorities(Priorities priorities) {
		this.priorities = priorities;
		priorities.setApplication(this);
	}

}
