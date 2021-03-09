package it.akademija.document;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiParam;
import it.akademija.user.UserService;

@RestController
@Api(value = "Documents")
@RequestMapping(path = "/api/documents")
public class DocumentController {
	
	@Autowired
	DocumentService documentService;
	
	@Autowired
	UserService userService;
	
	@Secured("ROLE_USER")
	@GetMapping(path = "/document/{id}")
	public byte[] getDocumentFileById(@ApiParam(value = "id") @PathVariable Long id) {
		return documentService.getDocumentById(id).getData();
		//return new ByteArrayInputStream(documentService.getDocumentById(id).getData());
	}
	
	@Secured("ROLE_USER")
	@PostMapping(path = "/upload")
	public ResponseEntity<String> UploadDocument(@RequestParam("file") MultipartFile file, @RequestParam("name") String name) {
		if(documentService.uploadDocument(file, name, userService.findByUsername(SecurityContextHolder.getContext().getAuthentication().getName()).getUserId())) {
			return new ResponseEntity<String>("Dokumentas buvo ikeltas sekmingai", HttpStatus.CREATED);
		}
		else {
			return new ResponseEntity<String>("Ivyko klaida", HttpStatus.BAD_REQUEST);
		}
	}
	
	@Secured("ROLE_USER")
	@GetMapping(path = "/documents")
	public List<DocumentViewmodel> getLoggedUserDocuments() {
		List<DocumentEntity> docEntityList = documentService.getDocumentsByUploaderId(userService.findByUsername(SecurityContextHolder.getContext().getAuthentication().getName()).getUserId());
		List<DocumentViewmodel> docViewmodelList = new ArrayList<>();
		for(DocumentEntity doc : docEntityList) {
			docViewmodelList.add(new DocumentViewmodel(doc.getId(), doc.getName(), doc.getUploadDate()));
		}
		return docViewmodelList;
	}
	
}
