package it.akademija.document;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

@Service
public class DocumentService {

	@Autowired
	DocumentDAO documentDao;
	
	@Transactional
	public DocumentEntity getDocumentById(long id) {
		DocumentEntity doc = documentDao.getDocumentById(id);
		if(doc != null) {
			return doc;
		}
		else {
			return null;
		}
	}
	
	@Transactional
	public List<DocumentEntity> getDocumentsByUploaderId(long id) {
		return documentDao.findAll().stream()
									.filter(x -> x.getUploaderId()==id)
									.collect(Collectors.toList());
	}
	
	@Transactional
	public Boolean uploadDocument(MultipartFile file, String name, long uploaderId) {
		if(file.getSize()<=1024000 && file.getContentType().equals("application/pdf")) {
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
	
	@Transactional
	public void deleteDocument(long id) {
		documentDao.delete(getDocumentById(id));
	}
	
}
