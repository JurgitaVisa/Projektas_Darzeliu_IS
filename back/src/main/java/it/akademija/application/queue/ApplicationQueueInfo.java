package it.akademija.application.queue;

import it.akademija.application.ApplicationStatus;

public class ApplicationQueueInfo {
	
	private Long id;
	
	private String childPersonalCode;
	
	private String childName;
	
	private String childSurname;
	
	private String kindergartenName;
	
	private ApplicationStatus status;
	
	private int numberInWaitingList;

	public ApplicationQueueInfo() {
		
	}

	public ApplicationQueueInfo(Long id, String childPersonalCode, String childName, String childSurname,
			String kindergartenName, ApplicationStatus status, int numberInWaitingList) {
		super();
		this.id = id;
		this.childPersonalCode = childPersonalCode;
		this.childName = childName;
		this.childSurname = childSurname;
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
