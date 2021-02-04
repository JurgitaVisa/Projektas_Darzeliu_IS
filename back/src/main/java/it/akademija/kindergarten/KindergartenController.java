package it.akademija.kindergarten;

import java.util.List;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import it.akademija.user.UserController;

@RestController
@Api(value = "kindergarten")
@RequestMapping(path = "/api/darzeliai")
public class KindergartenController {

	private static final Logger LOG = LoggerFactory.getLogger(UserController.class);

	@Autowired
	private KindergartenService kindergartenService;

	/**
	 * Get list of Kindergarten names and addresses
	 * 
	 * @return list of kindergarten
	 */
	@Secured({ "ROLE_ADMIN", "ROLE_MANAGER", "ROLE_USER" })
	@GetMapping
	@ResponseStatus(HttpStatus.OK)
	@ApiOperation(value = "Get all kindergarten names and addresses")
	public List<KindergartenDTO> getAllKindergarten() {

		return kindergartenService.getAllKindergarten();
	}

	/**
	 * Create new kindergarten entity
	 * 
	 * @param kindergarten entity
	 * @return message
	 */
	@Secured({ "ROLE_MANAGER" })
	@PostMapping("/manager/createKindergarten")
	@ApiOperation(value = "Create new kindergarten")
	public ResponseEntity<String> createNewKindergarten(
			@ApiParam(value = "Kindergarten", required = true) @Valid @RequestBody Kindergarten kindergarten) {

		Kindergarten newKindengarten = kindergartenService.findByName(kindergarten.getName().trim());

		if (newKindengarten != null) {
			return new ResponseEntity<String>("Darželis tokiu pavadinimu jau yra", HttpStatus.CONFLICT);

		} else {
			
			kindergartenService.createNewKindergarten(kindergarten);
			LOG.info("**KindergartenController: kuriamas darzelis pavadinimu [{}] **", kindergarten.getName());

			return new ResponseEntity<String>("Darželis sukurtas sėkmingai", HttpStatus.OK);		
		}

	}
	
	@Secured({ "ROLE_MANAGER" })
	@DeleteMapping("/manager/delete/{id}")
	@ApiOperation(value = "Delete kindergarten by ID")
	public ResponseEntity<String> deleteKindergarten(
			@ApiParam(value = "Kindergarten id", required = true) @PathVariable Long id) {

		if (kindergartenService.findById(id) != null) {

			kindergartenService.deleteKindergarten(id);
			LOG.info("** Usercontroller: trinamas darželis pavadinimu ID [{}] **", id);
			return new ResponseEntity<String>("Darželis panaikintas sėkmingai", HttpStatus.OK);
		}

		return new ResponseEntity<String>("Darželis tokiu pavadinimu nerastas", HttpStatus.NOT_FOUND);
	}

	public KindergartenService getGartenService() {
		return kindergartenService;
	}

	public void setGartenService(KindergartenService gartenService) {
		this.kindergartenService = gartenService;
	}

}
