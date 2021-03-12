package it.akademija.application.management;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class RegistrationStatus {

	@Id
	private Integer id;

	@Column(name = "registration_status")
	private boolean registrationActive;
	
	@Column(name = "queue_editing_locked")
	private boolean queueEditingLocked;
	

	public RegistrationStatus() {
		id = 1;
		registrationActive = false;
		queueEditingLocked = false;
	}		

	public boolean isRegistrationActive() {
		return registrationActive;
	}

	public void setRegistrationActive(boolean registrationActive) {
		this.registrationActive = registrationActive;
	}

	public boolean isQueueEditingLocked() {
		return queueEditingLocked;
	}

	public void setQueueEditingLocked(boolean queueEditingLocked) {
		this.queueEditingLocked = queueEditingLocked;
	}

	

}
