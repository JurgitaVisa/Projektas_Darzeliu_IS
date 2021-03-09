package it.akademija.journal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import io.swagger.annotations.ApiOperation;

public class JournalController {
	
	@Autowired
	private JournalService journalService;
	
	@Secured({ "ROLE_ADMIN" })
	@PostMapping(path = "/admin/getjournal")
	@ApiOperation(value = "Show all journal entries", notes = "Showing all journal entries")
	public Page<JournalEntry> getAllJournalEntries(
			@RequestParam("page") int page, 
			  @RequestParam("size") int size) {	
		
		Sort.Order order = new Sort.Order(Sort.Direction.DESC, "eventTime");
						
		Pageable pageable = PageRequest.of(page, size, Sort.by(order));

		return journalService.getAllJournalEntries(pageable);
	}
	
}
