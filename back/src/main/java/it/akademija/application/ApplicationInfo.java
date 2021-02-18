package it.akademija.application;

import java.time.LocalDateTime;

public class ApplicationInfo {

	private Long id;

	private String childName;

	private String childSurname;

	private LocalDateTime submitedAt;

	private ApplicationStatus status;

	
	public ApplicationInfo(Long id, String childName, String childSurname, LocalDateTime submitedAt,
			ApplicationStatus status) {		
		this.id = id;
		this.childName = childName;
		this.childSurname = childSurname;
		this.submitedAt = submitedAt;
		this.status = status;
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

	public LocalDateTime getSubmitedAt() {
		return submitedAt;
	}

	public void setSubmitedAt(LocalDateTime submitedAt) {
		this.submitedAt = submitedAt;
	}

	public ApplicationStatus getStatus() {
		return status;
	}

	public void setStatus(ApplicationStatus status) {
		this.status = status;
	}
	
	
	
	

}
