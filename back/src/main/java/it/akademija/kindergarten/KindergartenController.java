package it.akademija.kindergarten;

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
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import it.akademija.journal.JournalService;
import it.akademija.journal.ObjectType;
import it.akademija.journal.OperationType;

@RestController
@Api(value = "kindergarten")
@RequestMapping(path = "/api/darzeliai")
public class KindergartenController {

	private static final Logger LOG = LoggerFactory.getLogger(KindergartenController.class);

	@Autowired
	private KindergartenService kindergartenService;

	@Autowired
	private JournalService journalService;

	/**
	 * Get list of all Kindergarten names and addresses with capacity of more than
	 * zero
	 * 
	 * @return list of kindergarten
	 */
	@Secured({ "ROLE_ADMIN", "ROLE_MANAGER", "ROLE_USER" })
	@GetMapping	
	@ApiOperation(value = "Get all kindergarten names and addresses with available places > 0")
	public List<KindergartenInfo> getAllWithNonZeroCapacity() {

		return kindergartenService.getAllWithNonZeroCapacity();
	}

	/**
	 * Get list of all elderates
	 * 
	 * @return list of kindergarten
	 */
	@Secured({ "ROLE_MANAGER" })
	@GetMapping("/manager/elderates")	
	@ApiOperation(value = "Get all elderates")
	public Set<String> getAllElderates() {

		return kindergartenService.getAllElderates();
	}

	/**
	 * Get specified Kindergarten information page
	 * 
	 * @return page of kindergarten information
	 */
	@Secured({ "ROLE_MANAGER" })
	@GetMapping("/manager/page")
	@ApiOperation(value = "Get kindergarten information pages")
	public ResponseEntity<Page<Kindergarten>> getKindergartenPage(

			@RequestParam("page") int page, @RequestParam("size") int size) {

		Sort.Order order = new Sort.Order(Sort.Direction.ASC, "name").ignoreCase();

		Pageable pageable = PageRequest.of(page, size, Sort.by(order));

		return new ResponseEntity<>(kindergartenService.getKindergartenPage(pageable), HttpStatus.OK);
	}

	/**
	 * Get specified Kindergarten information page filtered by name
	 * 
	 * @return page of kindergarten information
	 */
	@Secured({ "ROLE_MANAGER" })
	@GetMapping("/manager/page/{name}")
	@ApiOperation(value = "Get kindergarten information pages")
	public ResponseEntity<Page<Kindergarten>> getKindergartenPageFilteredByName(@PathVariable String name,
			@RequestParam("page") int page, @RequestParam("size") int size) {

		Sort.Order order = new Sort.Order(Sort.Direction.ASC, "name").ignoreCase();

		Pageable pageable = PageRequest.of(page, size, Sort.by(order));

		return new ResponseEntity<>(kindergartenService.getKindergartenPageFilteredByName(name, pageable),
				HttpStatus.OK);
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
			@ApiParam(value = "Kindergarten", required = true) @Valid @RequestBody KindergartenDTO kindergarten) {

		String id = kindergarten.getId();

		if (kindergartenService.findById(id) != null) {

			LOG.warn("Kuriamas darželis su jau egzistuojančiu įstaigos kodu [{}]", id);

			return new ResponseEntity<String>("Darželis su tokiu įstaigos kodu jau yra", HttpStatus.CONFLICT);

		} else if (kindergartenService.nameAlreadyExists(kindergarten.getName().trim(), id)) {

			LOG.warn("Kuriamas darželis su jau egzistuojančiu įstaigos pavadinimu [{}]", kindergarten.getName().trim());

			return new ResponseEntity<String>("Darželis su tokiu įstaigos pavadinimu jau yra", HttpStatus.CONFLICT);

		} else {

			kindergartenService.createNewKindergarten(kindergarten);

			LOG.info("**KindergartenController: kuriamas darzelis pavadinimu [{}] **", kindergarten.getName());

			journalService.newJournalEntry(OperationType.KINDERGARTEN_CREATED, Long.parseLong(id),
					ObjectType.KINDERGARTEN, "Sukurtas naujas darželis");

			return new ResponseEntity<String>("Darželis sukurtas sėkmingai", HttpStatus.OK);
		}

	}

	/**
	 * 
	 * Delete kindergarten entity with specified id
	 * 
	 * @param id
	 * @return message if entity was deleted or if it does not exist in the database
	 */
	@Secured({ "ROLE_MANAGER" })
	@DeleteMapping("/manager/delete/{id}")
	@ApiOperation(value = "Delete kindergarten by ID")
	public ResponseEntity<String> deleteKindergarten(
			@ApiParam(value = "Kindergarten id", required = true) @PathVariable String id) {

		journalService.newJournalEntry(OperationType.KINDERGARTEN_DELETED, Long.parseLong(id), ObjectType.KINDERGARTEN,
				"Ištrintas darželis");

		return kindergartenService.deleteKindergarten(id);
	}

	@Secured({ "ROLE_MANAGER" })
	@PutMapping("/manager/update/{id}")
	@ApiOperation(value = "Update kindergarten by ID")
	public ResponseEntity<String> updateKindergarten(
			@ApiParam(value = "Kindergarten", required = true) @Valid @RequestBody KindergartenDTO updated,
			@PathVariable String id) {

		if (kindergartenService.findById(id) == null) {

			LOG.warn("Darželio įstaigos kodu [{}] nėra", id);

			return new ResponseEntity<String>("Darželis su tokiu įstaigos kodu nerastas", HttpStatus.NOT_FOUND);

		} else if (kindergartenService.nameAlreadyExists(updated.getName().trim(), id)) {

			LOG.warn("Darželis pavadinimu [{}] jau egzituoja", updated.getName().trim());

			return new ResponseEntity<String>("Darželis su tokiu įstaigos pavadinimu jau yra", HttpStatus.CONFLICT);

		} else {

			kindergartenService.updateKindergarten(id, updated);

			LOG.info("** Usercontroller: atnaujinamas darželis ID [{}] **", id);

			journalService.newJournalEntry(OperationType.KINDERGARTEN_UPDATED, Long.parseLong(id),
					ObjectType.KINDERGARTEN, "Atnaujinti darželio duomenys");

			return new ResponseEntity<String>("Darželio duomenys atnaujinti sėkmingai", HttpStatus.OK);
		}

	}

	/**
	 * Get Kindergarten statistics
	 * 
	 * @return kindergarten statistics
	 */
	@Secured({ "ROLE_ADMIN", "ROLE_MANAGER", "ROLE_USER" })
	@GetMapping("/statistics")	
	@ApiOperation(value = "Get kindergarten statistics")
	public Page<KindergartenStatistics> getKindergartenStatistics(@RequestParam("page") int page,
			@RequestParam("size") int size) {

		Sort.Order order = new Sort.Order(Sort.Direction.ASC, "name").ignoreCase();

		Pageable pageable = PageRequest.of(page, size, Sort.by(order));

		return kindergartenService.getKindergartenStatistics(pageable);
	}

	public KindergartenService getGartenService() {
		return kindergartenService;
	}

	public void setGartenService(KindergartenService gartenService) {
		this.kindergartenService = gartenService;
	}

}
