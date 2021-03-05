package it.akademija.application.management;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import it.akademija.application.queue.ApplicationQueueService;

@RestController
@Api(value = "status-manager")
@RequestMapping(path = "/api")
public class RegistrationStatusController {

	private static final Logger LOG = LoggerFactory.getLogger(RegistrationStatusController.class);

	@Autowired
	private RegistrationStatusService statusService;

	@Autowired
	private ApplicationQueueService queueService;

	/**
	 * Changes registration status
	 * 
	 * @return registration status
	 */
	@Secured({ "ROLE_MANAGER" })
	@PostMapping("/status/{status}")
	@ResponseStatus(HttpStatus.OK)
	@ApiOperation(value = "Set application status")
	public @ResponseBody String setStatus(@PathVariable boolean status) {

		RegistrationStatus registrationStatus = statusService.getStatus();

		registrationStatus.setStatus(status);
		registrationStatus = statusService.saveStatus(registrationStatus);

		LOG.info("** Statuscontroller: keičiamas registracijos statusas. **");

		return registrationStatus.toString();

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
	public boolean getStatus() {

		RegistrationStatus registrationStatus = statusService.getStatus();
		LOG.info("** Statuscontroller: gaunamas registracijos statusas. **");

		return registrationStatus.isStatus();

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
		
		queueService.processApplicationsToQueue();

		LOG.info("** Statuscontroller: pradedamas eilių formavimas. **");

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

		LOG.info("** Statuscontroller: suformuotos eilės patvirtinimas. **");

		return new ResponseEntity<String>("Eilė patvirtinta", HttpStatus.OK);
	}

	/**
	 * Lock queue editing for Manager
	 * 
	 * @return message
	 */

	@Secured({ "ROLE_ADMIN" })
	@PostMapping("/queue/lock")
	@ApiOperation(value = "Lock queue editing for Manager")
	public ResponseEntity<String> lockQueueEditing() {

		LOG.info("** Statuscontroller: užrakinamas prašymų iš eilių trynimas. **");

		return new ResponseEntity<String>("Eilės redagavimas užrakintas", HttpStatus.OK);
	}

	/**
	 * Unlock queue editing for Manager
	 * 
	 * @return message
	 */

	@Secured({ "ROLE_ADMIN" })
	@PostMapping("/queue/unlock")
	@ApiOperation(value = "Unlock queue editing for Manager")
	public ResponseEntity<String> unlockQueueEditing() {

		LOG.info("** Statuscontroller: atrakinamas prašymų iš eilių trynimas. **");

		return new ResponseEntity<String>("Eilės redagavimas atrakintas", HttpStatus.OK);
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
