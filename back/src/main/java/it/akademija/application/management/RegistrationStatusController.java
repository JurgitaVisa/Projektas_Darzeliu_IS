package it.akademija.application.management;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@RestController
@Api(value = "status-manager")
@RequestMapping(path = "/api")
public class RegistrationStatusController {

	private static final Logger LOG = LoggerFactory.getLogger(RegistrationStatusController.class);

	@Autowired
	private RegistrationStatusService statusService;

//	@Autowired
//	private QueueService queueService;

	/**
	 * Changes registration status
	 * 
	 * @return registration status
	 */
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
	@GetMapping("/status")
	@ResponseStatus(HttpStatus.OK)
	@ApiOperation(value = "Get application status")
	public boolean getStatus() {

		RegistrationStatus registrationStatus = statusService.getStatus();
		LOG.info("** Statuscontroller: gaunamas registracijos statusas. **");

		return registrationStatus.isStatus();

	}

	@PostMapping("/queue/process")
	@ApiOperation(value = "Process queue")
	public ResponseEntity<String> processQueue() {

		// queueService.processQueue();

		return new ResponseEntity<String>("Eilė suformuota", HttpStatus.OK);

	}

	@PostMapping("/queue/confirm")
	@ApiOperation(value = "Confirm queue")
	public ResponseEntity<String> confirmQueue() {

		// queueService.confirmQueue();

		return new ResponseEntity<String>("Eilė patvirtinta", HttpStatus.OK);

	}

	public RegistrationStatusService getStatusService() {
		return statusService;
	}

	public void setStatusService(RegistrationStatusService statusService) {
		this.statusService = statusService;
	}

}
