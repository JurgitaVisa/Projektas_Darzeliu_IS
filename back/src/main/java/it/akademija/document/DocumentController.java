package it.akademija.document;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiParam;
import it.akademija.application.ApplicationController;
import it.akademija.journal.JournalService;
import it.akademija.journal.ObjectType;
import it.akademija.journal.OperationType;
import it.akademija.user.UserService;

@RestController
@Api(value = "Documents")
@RequestMapping(path = "/api/documents")
public class DocumentController {

	private static final Logger LOG = LoggerFactory.getLogger(ApplicationController.class);

	@Autowired
	DocumentService documentService;

	@Autowired
	UserService userService;

	@Autowired
	private JournalService journalService;

	@Secured("ROLE_USER")
	@GetMapping(path = "/get/{id}")
	public byte[] getDocumentFileById(@ApiParam(value = "id") @PathVariable Long id) {

		journalService.newJournalEntry(OperationType.MEDICAL_RECORD_DOWNLOADED, id, ObjectType.MEDICAL_RECORD,
				"Atsisiųsta medicininė pažyma");

		return documentService.getDocumentById(id).getData();
		// return new
		// ByteArrayInputStream(documentService.getDocumentById(id).getData());
	}

	@Secured("ROLE_USER")
	@PostMapping(path = "/upload")
	public ResponseEntity<String> UploadDocument(@RequestParam("file") MultipartFile file,
			@RequestParam("name") String name) {

		if (documentService.uploadDocument(file, name, userService
				.findByUsername(SecurityContextHolder.getContext().getAuthentication().getName()).getUserId())) {

			journalService.newJournalEntry(OperationType.MEDICAL_RECORD_SUBMITED, userService
					.findByUsername(SecurityContextHolder.getContext().getAuthentication().getName()).getUserId(),
					ObjectType.MEDICAL_RECORD, "Įkelta medicininė pažyma");

			return new ResponseEntity<String>("Dokumentas buvo įkeltas sėkmingai", HttpStatus.CREATED);

		} else {

			LOG.warn("Įvyko klaida įkeliant dokumentą");
			return new ResponseEntity<String>("Įvyko klaida", HttpStatus.BAD_REQUEST);
		}
	}

	@Secured("ROLE_USER")
	@DeleteMapping(path = "/delete/{id}")
	public ResponseEntity<String> deleteDocument(@ApiParam(value = "id") @PathVariable final long id) {

		documentService.deleteDocument(id);

		return new ResponseEntity<String>("Dokumentas su tokiu id buvo ištrintas.", HttpStatus.OK);
	}

	@Secured("ROLE_USER")
	@GetMapping(path = "/documents")
	public List<DocumentViewmodel> getLoggedUserDocuments() {

		List<DocumentEntity> docEntityList = documentService.getDocumentsByUploaderId(userService
				.findByUsername(SecurityContextHolder.getContext().getAuthentication().getName()).getUserId());

		List<DocumentViewmodel> docViewmodelList = new ArrayList<>();

		for (DocumentEntity doc : docEntityList) {

			docViewmodelList.add(new DocumentViewmodel(doc.getId(), doc.getName(), doc.getUploadDate()));
		}
		return docViewmodelList;
	}

}
