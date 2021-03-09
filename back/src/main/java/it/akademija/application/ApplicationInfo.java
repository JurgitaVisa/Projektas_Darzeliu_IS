package it.akademija.application;

import java.time.LocalDate;

public class ApplicationInfo {

	private Long id;

	private String childPersonalCode;

	private String childName;

	private String childSurname;

	private LocalDate submitedAt;

	private ApplicationStatus status;	

	private String choise1;

	private String choise2;

	private String choise3;

	private String choise4;

	private String choise5;	

	public ApplicationInfo(Long id, String childPersonalCode, String childName, String childSurname, ApplicationStatus status, String choise1,
			String choise2, String choise3, String choise4, String choise5) {
		super();
		this.id = id;
		this.childPersonalCode = childPersonalCode;
		this.childName = childName;
		this.childSurname = childSurname;
		this.status = status;
		this.choise1 = choise1;
		this.choise2 = choise2;
		this.choise3 = choise3;
		this.choise4 = choise4;
		this.choise5 = choise5;
	}



	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getChildPersonalCode() {
		return childPersonalCode;
	}

	public void setChildPersonalCode(String childPersonalCode) {
		this.childPersonalCode = childPersonalCode;
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

	public LocalDate getSubmitedAt() {
		return submitedAt;
	}

	public void setSubmitedAt(LocalDate submitedAt) {
		this.submitedAt = submitedAt;
	}

	public ApplicationStatus getStatus() {
		return status;
	}

	public void setStatus(ApplicationStatus status) {
		this.status = status;
	}

	public String getChoise1() {
		return choise1;
	}

	public void setChoise1(String choise1) {
		this.choise1 = choise1;
	}

	public String getChoise2() {
		return choise2;
	}

	public void setChoise2(String choise2) {
		this.choise2 = choise2;
	}

	public String getChoise3() {
		return choise3;
	}

	public void setChoise3(String choise3) {
		this.choise3 = choise3;
	}

	public String getChoise4() {
		return choise4;
	}

	public void setChoise4(String choise4) {
		this.choise4 = choise4;
	}

	public String getChoise5() {
		return choise5;
	}

	public void setChoise5(String choise5) {
		this.choise5 = choise5;
	}

	
}
