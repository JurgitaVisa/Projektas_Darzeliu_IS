package it.akademija.application.management;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class RegistrationStatusService {

	@Autowired
	private RegistrationStatusDAO statusDAO;

	/**
	 * Get registration status
	 * 
	 * @return registration status
	 */
	@Transactional
	public RegistrationStatus getStatus() {

		return statusDAO.findById(1).orElse(new RegistrationStatus());

	}
	
	/**
	 * 
	 * Set Application registration status
	 * 
	 * @param registrationActive
	 * @return
	 */
	@Transactional
	public RegistrationStatus setStatus(boolean registrationActive) {
		RegistrationStatus status = getStatus();
		status.setRegistrationActive(registrationActive);
		return statusDAO.save(status);
		
	}

	/**
	 * Save registration status
	 * 
	 * @return registration status
	 * @param status
	 */
	@Transactional
	public RegistrationStatus saveStatus(RegistrationStatus status) {
		statusDAO.save(status);

		return status;
	}

	/**
	 * Lock queue editing so that Manager cannot disable user applications. Method
	 * accessible to ADMIN users only.
	 */
	@Transactional
	public void lockQueueEditing() {

		RegistrationStatus status = getStatus();
		status.setQueueEditingLocked(true);
		statusDAO.save(status);
	}

	/**
	 * Unlock queue editing so that Manager can disable non-approved user
	 * applications. Method accessible to ADMIN users only.
	 */
	@Transactional
	public void unlockQueueEditing() {
		
		RegistrationStatus status = getStatus();
		status.setQueueEditingLocked(false);
		statusDAO.save(status);
	}

	public RegistrationStatusDAO getStatusDAO() {
		return statusDAO;
	}

	public void setStatusDAO(RegistrationStatusDAO statusDAO) {
		this.statusDAO = statusDAO;
	}	

}
