package it.akademija.application;

import java.util.List;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

@RestController
@Api(value = "application")
@RequestMapping(path = "/api/prasymai")
public class ApplicationController {

	private static final Logger LOG = LoggerFactory.getLogger(ApplicationController.class);

	@Autowired
	private ApplicationService service;
	
	
	@Secured({ "ROLE_USER" })
	@PostMapping	
	@ApiOperation(value = "Create new application")
	public ResponseEntity<String> createNewApplication(
			@ApiParam(value = "Application", required = true) 
			@Valid @RequestBody ApplicationDTO data) {
		
		String currentUsername = SecurityContextHolder.getContext().getAuthentication().getName();
		
		if (service.existsByPersonalCode(data.getChildPersonalCode())) {
			return new ResponseEntity<String>("Prašymas vaikui su tokiu asmens kodu jau yra registruotas", HttpStatus.CONFLICT);

		} else {
			service.createNewApplication(currentUsername, data);
			LOG.info("**ApplicationController: kuriamas prasymas vaikui AK [{}] **", data.getChildPersonalCode());

			return new ResponseEntity<String>("Prašymas sukurtas sėkmingai", HttpStatus.OK);
		}
	}
	
	

	/**
	 * Get list of all applications for logged user
	 * 
	 * @return list applications
	 */
	@Secured({ "ROLE_USER" })
	@GetMapping
	@ResponseStatus(HttpStatus.OK)
	@ApiOperation(value = "Get all user applications")
	public List<ApplicationInfo> getAllUserApplications() {

		String currentUsername = SecurityContextHolder.getContext().getAuthentication().getName();

		return service.getAllUserApplications(currentUsername);
	}

	
	
	
	

	public ApplicationService getService() {
		return service;
	}


	public void setService(ApplicationService service) {
		this.service = service;
	}
	
}
