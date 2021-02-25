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

		if (!statusDAO.existsById(1)) {
			return new RegistrationStatus();
		}
		return statusDAO.findById(1).get();

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
}
