package it.akademija.application.queue;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.domain.Sort.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@RestController
@Api(value = "application queue")
@RequestMapping(path = "/api/eile")
public class ApplicationQueueController {

	@Autowired
	private ApplicationQueueService queueService;

	/**
	 * Get application queue
	 * 
	 * @return list of sorted applications
	 */
	@Secured({ "ROLE_MANAGER" })
	@GetMapping("/manager/queue")
	@ResponseStatus(HttpStatus.OK)
	@ApiOperation(value = "Get application queue processed")
	public ResponseEntity<Page<ApplicationQueueInfo>> getApplicationQueueInformation(@RequestParam("page") int page,
			@RequestParam("size") int size) {

		List<Order> orders = new ArrayList<>();
		orders.add(new Order(Direction.ASC, "childSurname").ignoreCase());
		orders.add(new Order(Direction.ASC, "childName").ignoreCase());

		Pageable pageable = PageRequest.of(page, size, Sort.by(orders));

		return new ResponseEntity<>(queueService.getApplicationQueueInformation(pageable), HttpStatus.OK);
	}

	/**
	 * Get application queue filtered by Child personal code
	 * 
	 * @return filtered list of sorted applications
	 */
	@Secured({ "ROLE_MANAGER" })
	@GetMapping("/manager/queue/{childPersonalCode}")
	@ResponseStatus(HttpStatus.OK)
	@ApiOperation(value = "Get application queue processed")
	public ResponseEntity<Page<ApplicationQueueInfo>> getApplicationQueueInformationFilteredByChildId(
			@PathVariable String childPersonalCode, @RequestParam("page") int page, @RequestParam("size") int size) {

		List<Order> orders = new ArrayList<>();
		orders.add(new Order(Direction.ASC, "childSurname").ignoreCase());
		orders.add(new Order(Direction.ASC, "childName").ignoreCase());
		
		Pageable pageable = PageRequest.of(page, size, Sort.by(orders));

		return new ResponseEntity<>(
				queueService.getApplicationQueueInformationFilteredByChildId(childPersonalCode, pageable),
				HttpStatus.OK);
	}

	public ApplicationQueueService getQueueService() {
		return queueService;
	}

	public void setQueueService(ApplicationQueueService queueService) {
		this.queueService = queueService;
	}

}
