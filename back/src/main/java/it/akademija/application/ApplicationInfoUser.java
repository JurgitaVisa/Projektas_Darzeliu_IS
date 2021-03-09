package it.akademija.application;

import java.time.LocalDate;

public class ApplicationInfoUser {

	private Long id;

	private String childName;

	private String childSurname;

	private LocalDate submitedAt;

	private String kindergartenName;

	private ApplicationStatus status;

	private int numberInWaitingList;

	public ApplicationInfoUser() {
		
	}

	public ApplicationInfoUser(Long id, String childName, String childSurname, LocalDate submitedAt,
			String kindergartenName, ApplicationStatus status, int numberInWaitingList) {
		super();
		this.id = id;
		this.childName = childName;
		this.childSurname = childSurname;
		this.submitedAt = submitedAt;
		this.kindergartenName = kindergartenName;
		this.status = status;
		this.numberInWaitingList = numberInWaitingList;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
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

	public String getKindergartenName() {
		return kindergartenName;
	}

	public void setKindergartenName(String kindergartenName) {
		this.kindergartenName = kindergartenName;
	}

	public ApplicationStatus getStatus() {
		return status;
	}

	public void setStatus(ApplicationStatus status) {
		this.status = status;
	}

	public int getNumberInWaitingList() {
		return numberInWaitingList;
	}

	public void setNumberInWaitingList(int numberInWaitingList) {
		this.numberInWaitingList = numberInWaitingList;
	}	

}
