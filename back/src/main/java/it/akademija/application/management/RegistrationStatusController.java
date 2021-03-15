package it.akademija.application.management;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import it.akademija.application.queue.ApplicationQueueService;
import it.akademija.journal.JournalService;
import it.akademija.journal.ObjectType;
import it.akademija.journal.OperationType;

@RestController
@Api(value = "status-manager")
@RequestMapping(path = "/api")
public class RegistrationStatusController {

	private static final Logger LOG = LoggerFactory.getLogger(RegistrationStatusController.class);

	@Autowired
	private RegistrationStatusService statusService;

	@Autowired
	private ApplicationQueueService queueService;

	@Autowired
	private JournalService journalService;

	/**
	 * Changes registration status
	 * 
	 * @return registration status
	 * @param status
	 */
	@Secured({ "ROLE_MANAGER" })
	@PostMapping("/status/{registrationActive}")
	@ResponseStatus(HttpStatus.OK)
	@ApiOperation(value = "Set application status")
	public RegistrationStatus setStatus(@PathVariable boolean registrationActive) {

		if (registrationActive) {
			journalService.newJournalEntry(OperationType.REGISTRATION_STARTED, null, ObjectType.REGISTRATION,
					"Prašymų registracija pradėta");

		} else {
			journalService.newJournalEntry(OperationType.REGISTRATION_ENDED, null, ObjectType.REGISTRATION,
					"Prašymų registracija sustabdyta");
		}

		return statusService.setStatus(registrationActive);
	}

	/**
	 * Returns registration status
	 * 
	 * @return registration status
	 */
	@Secured({ "ROLE_USER", "ROLE_ADMIN", "ROLE_MANAGER" })
	@GetMapping("/status")
	@ResponseStatus(HttpStatus.OK)
	@ApiOperation(value = "Get application status")
	public RegistrationStatus getStatus() {

		return statusService.getStatus();
	}

	/**
	 * Start queue processing
	 * 
	 * @return message
	 */

	@Secured({ "ROLE_MANAGER" })
	@PostMapping("/queue/process")
	@ApiOperation(value = "Process queue")
	public ResponseEntity<String> processQueue() {

		String currentUsername = SecurityContextHolder.getContext().getAuthentication().getName();
		queueService.processApplicationsToQueue();

		LOG.info("Naudotojas [{}] pradeda eilių formavimą", currentUsername);

		return new ResponseEntity<String>("Eilė suformuota", HttpStatus.OK);
	}

	/**
	 * Confirm queue
	 * 
	 * @return message
	 */

	@Secured({ "ROLE_MANAGER" })
	@PostMapping("/queue/confirm")
	@ApiOperation(value = "Confirm queue")
	public ResponseEntity<String> confirmQueue() {

		String currentUsername = SecurityContextHolder.getContext().getAuthentication().getName();

		LOG.info("Naudotojas [{}] tvirtina eiles.", currentUsername);

		return queueService.confirmApplicationsInQueue();
	}

	/**
	 * Lock queue editing for Manager
	 * 
	 * @return message
	 */

	@Secured({ "ROLE_ADMIN" })
	@PostMapping("/queue/lock")
	@ApiOperation(value = "Lock queue editing for Manager")
	public void lockQueueEditing() {

		String currentUsername = SecurityContextHolder.getContext().getAuthentication().getName();

		LOG.info("Naudotojas [{}] užrakina eilių redagavimą.", currentUsername);

		statusService.lockQueueEditing();

	}

	/**
	 * Unlock queue editing for Manager
	 * 
	 * @return message
	 */

	@Secured({ "ROLE_ADMIN" })
	@PostMapping("/queue/unlock")
	@ApiOperation(value = "Unlock queue editing for Manager")
	public void unlockQueueEditing() {

		String currentUsername = SecurityContextHolder.getContext().getAuthentication().getName();

		LOG.info("Naudotojas [{}] atrakina eilių redagavimą.", currentUsername);

		statusService.unlockQueueEditing();
	}

	public RegistrationStatusService getStatusService() {
		return statusService;
	}

	public void setStatusService(RegistrationStatusService statusService) {
		this.statusService = statusService;
	}

	public ApplicationQueueService getQueueService() {
		return queueService;
	}

	public void setQueueService(ApplicationQueueService queueService) {
		this.queueService = queueService;
	}

}
