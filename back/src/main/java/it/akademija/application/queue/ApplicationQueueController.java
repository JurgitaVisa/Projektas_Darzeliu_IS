package it.akademija.application.queue;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.GetMapping;
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
	 * @return list of application pre-sorted
	 */
	@Secured({ "ROLE_MANAGER" })
	@GetMapping("/manager/queue")
	@ResponseStatus(HttpStatus.OK)
	@ApiOperation(value = "Get application queue processed")
	public ResponseEntity<Page<ApplicationQueueInfo>> getApplicationQueueInformation(
			@RequestParam("page") int page, @RequestParam("size") int size){
		
		Pageable pageable = PageRequest.of(page, size);

		return new ResponseEntity<>(queueService.getApplicationQueueInformation(pageable), HttpStatus.OK);
	}

	public ApplicationQueueService getQueueService() {
		return queueService;
	}

	public void setQueueService(ApplicationQueueService queueService) {
		this.queueService = queueService;
	}
	
	
}
