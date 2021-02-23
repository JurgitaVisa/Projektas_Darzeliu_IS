package it.akademija.application;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class ApplicationInfo {

	private Long id;

	private String childPersonalCode;

	private String childName;

	private String childSurname;

	private LocalDate submitedAt;

	private ApplicationStatus status;

	private String choiseName1;

	private String choiseName2;

	private String choiseName3;

	private String choiseName4;

	private String choiseName5;

	public ApplicationInfo(Long id, String childName, String childSurname, LocalDate submitedAt,
			ApplicationStatus status) {
		this.id = id;
		this.childName = childName;
		this.childSurname = childSurname;
		this.submitedAt = submitedAt;
		this.status = status;
	}

	public ApplicationInfo(Long id, String childPersonalCode, String childName, String childSurname, String choiseName1,
			String choiseName2, String choiseName3, String choiseName4, String choiseName5) {
		super();
		this.id = id;
		this.childPersonalCode = childPersonalCode;
		this.childName = childName;
		this.childSurname = childSurname;
		this.choiseName1 = choiseName1;
		this.choiseName2 = choiseName2;
		this.choiseName3 = choiseName3;
		this.choiseName4 = choiseName4;
		this.choiseName5 = choiseName5;
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

	public String getChoiseName1() {
		return choiseName1;
	}

	public void setChoiseName1(String choiseName1) {
		this.choiseName1 = choiseName1;
	}

	public String getChoiseName2() {
		return choiseName2;
	}

	public void setChoiseName2(String choiseName2) {
		this.choiseName2 = choiseName2;
	}

	public String getChoiseName3() {
		return choiseName3;
	}

	public void setChoiseName3(String choiseName3) {
		this.choiseName3 = choiseName3;
	}

	public String getChoiseName4() {
		return choiseName4;
	}

	public void setChoiseName4(String choiseName4) {
		this.choiseName4 = choiseName4;
	}

	public String getChoiseName5() {
		return choiseName5;
	}

	public void setChoiseName5(String choiseName5) {
		this.choiseName5 = choiseName5;
	}

}
