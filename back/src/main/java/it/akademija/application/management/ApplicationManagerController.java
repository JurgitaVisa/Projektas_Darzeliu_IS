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
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import it.akademija.application.ApplicationService;

@RestController
@Api(value = "application-manager")
@RequestMapping(path = "/api/application-manager")
public class ApplicationManagerController {

	private static final Logger LOG = LoggerFactory.getLogger(ApplicationManagerController.class);

	@Autowired
	private ApplicationService service;

	@Secured({ "ROLE_MANAGER" })
	@PostMapping("/application-status/{status}")
	@ApiOperation(value = "Toggle application status")
	public ResponseEntity<String> setApplicationStatus(
			@ApiParam(value = "Status", required = true) @PathVariable boolean status) {

		service.setStatus(status);

		return new ResponseEntity<String>("ok", HttpStatus.OK);

	}

	@Secured({ "ROLE_USER" })
	@GetMapping("/application-status")
	@ResponseStatus(HttpStatus.OK)
	@ApiOperation(value = "Get application status")
	public ResponseEntity<String> applicationStatus() {

		return new ResponseEntity<String>(service.getStatus(), HttpStatus.OK);

	}

	public ApplicationService getService() {
		return service;
	}

	public void setService(ApplicationService service) {
		this.service = service;
	}

}
