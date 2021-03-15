package it.akademija.application;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.domain.Sort.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Async;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import it.akademija.application.management.RegistrationStatusService;

@RestController
@Api(value = "application")
@RequestMapping(path = "/api/prasymai")
public class ApplicationController {

	private static final Logger LOG = LoggerFactory.getLogger(ApplicationController.class);

	@Autowired
	private ApplicationService service;

	@Autowired
	private RegistrationStatusService statusService;

	/**
	 * 
	 * Create new application for logged user
	 * 
	 * @param data
	 * @return message
	 */
	@Secured({ "ROLE_USER" })
	@PostMapping("/user/new")
	@Async
	@ApiOperation(value = "Create new application")
	public ResponseEntity<String> createNewApplication(
			@ApiParam(value = "Application", required = true) @Valid @RequestBody ApplicationDTO data) {

		String currentUsername = SecurityContextHolder.getContext().getAuthentication().getName();

		String childPersonalCode = data.getChildPersonalCode();

		if (!statusService.getStatus().isRegistrationActive()) {

			LOG.warn("Naudotojas [{}] bandė registruoti prašymą esant neaktyviai registracijai", currentUsername);
			return new ResponseEntity<String>("Šiuo metu registracija nevykdoma.", HttpStatus.METHOD_NOT_ALLOWED);

		} else if (service.existsByPersonalCode(childPersonalCode)) {

			LOG.warn("Naudotojas [{}] bandė registruoti prašymą jau registruotam vaikui su asmens kodu [{}]",
					currentUsername, data.getChildPersonalCode());

			return new ResponseEntity<String>("Prašymas vaikui su tokiu asmens kodu jau yra registruotas",
					HttpStatus.CONFLICT);

		} else {

			LOG.info("**ApplicationController: kuriamas prasymas vaikui AK [{}] **", data.getChildPersonalCode());

			return service.createNewApplication(currentUsername, data);
		}
	}

	/**
	 * Get list of all applications for logged user
	 * 
	 * @return list applications
	 */
	@Secured({ "ROLE_USER" })
	@GetMapping("/user")
	@ResponseStatus(HttpStatus.OK)
	@ApiOperation(value = "Get all user applications")
	public Set<ApplicationInfoUser> getAllUserApplications() {

		String currentUsername = SecurityContextHolder.getContext().getAuthentication().getName();

		return service.getAllUserApplications(currentUsername);
	}

	/**
	 *
	 * Get page of unsorted applications
	 *
	 * @param page
	 * @param size
	 * @return page of applications
	 */
	@Secured({ "ROLE_MANAGER" })
	@GetMapping("/manager")
	@ResponseStatus(HttpStatus.OK)
	@ApiOperation(value = "Get a page from all submitted applications")
	public Page<ApplicationInfo> getPageFromSubmittedApplications(@RequestParam("page") int page,
			@RequestParam("size") int size) {

		List<Order> orders = new ArrayList<>();
		orders.add(new Order(Direction.ASC, "childSurname").ignoreCase());
		orders.add(new Order(Direction.ASC, "childName").ignoreCase());

		Pageable pageable = PageRequest.of(page, size, Sort.by(orders));

		return service.getPageFromSubmittedApplications(pageable);
	}

	/**
	 * Get page of unsorted applications filtered by child personal code
	 * 
	 * @param childPersonalCode
	 * @param page
	 * @param size
	 * @return page of applications
	 */
	@Secured({ "ROLE_MANAGER" })
	@GetMapping("/manager/page/{childPersonalCode}")
	@ApiOperation(value = "Get a page from all submitted applications with specified child personal code")
	public ResponseEntity<Page<ApplicationInfo>> getApplicationnPageFilteredById(@PathVariable String childPersonalCode,
			@RequestParam("page") int page, @RequestParam("size") int size) {

		List<Order> orders = new ArrayList<>();
		orders.add(new Order(Direction.ASC, "childSurname").ignoreCase());
		orders.add(new Order(Direction.ASC, "childName").ignoreCase());

		Pageable pageable = PageRequest.of(page, size, Sort.by(orders));

		return new ResponseEntity<>(service.getApplicationnPageFilteredById(childPersonalCode, pageable),
				HttpStatus.OK);
	}

	/**
	 * 
	 * Delete user application by id
	 * 
	 * @param id
	 * @return message
	 */

	@Secured({ "ROLE_USER" })

	@DeleteMapping("/user/delete/{id}")
	@ApiOperation("Delete user application by id")
	public ResponseEntity<String> deleteApplication(
			@ApiParam(value = "Application id", required = true) @PathVariable Long id) {

		LOG.info("**ApplicationController: trinamas prasymas [{}] **", id);

		return service.deleteApplication(id);

	}

	/**
	 * 
	 * Manager sets user application status to inactive
	 * 
	 * @param id
	 * @return message
	 */
	@Secured({ "ROLE_MANAGER" })
	@PostMapping("/manager/deactivate/{id}")
	@ApiOperation("Delete user application by id")
	public ResponseEntity<String> deactivateApplication(
			@ApiParam(value = "Application id", required = true) @PathVariable Long id) {

		LOG.info("**ApplicationController: deaktyvuojamas prasymas [{}] **", id);

		return service.deactivateApplication(id);

	}

	public ApplicationService getService() {
		return service;
	}

	public void setService(ApplicationService service) {
		this.service = service;
	}

}
