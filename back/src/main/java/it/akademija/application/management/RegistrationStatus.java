package it.akademija.application.management;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class RegistrationStatus {

	@Id
	private Integer id;

	@Column(name = "registration_status")
	private boolean status;

	public RegistrationStatus() {
		id = 1;
		status = false;
	}

	public boolean isStatus() {
		return status;
	}

	public void setStatus(boolean status) {
		this.status = status;
	}

	@Override
	public String toString() {
		return status ? "On" : "Off";
	}

}
