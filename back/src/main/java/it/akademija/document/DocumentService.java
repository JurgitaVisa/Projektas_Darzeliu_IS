package it.akademija.document;

import java.time.LocalDate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

@Service
public class DocumentService {

	@Autowired
	DocumentDAO documentDao;
	
	@Transactional
	public DocumentEntity getDocumentById(Long id) {
		DocumentEntity doc = documentDao.getDocumentById(id);
		if(doc != null) {
			return doc;
		}
		else {
			return null;
		}
	}
	
	@Transactional
	public Boolean uploadDocument(MultipartFile file, String name, long uploaderId) {
		if(file.getSize()<=128000 && file.getContentType().equals("application/pdf")) {
			try {
				DocumentEntity doc = new DocumentEntity(name, file.getContentType(), file.getBytes(), file.getSize(), uploaderId, LocalDate.now());
				documentDao.save(doc);
			} catch(Exception e) {
				e.printStackTrace();
			}
			return true;
		}
		else {
			return false;
		}
	}
	
	
}
