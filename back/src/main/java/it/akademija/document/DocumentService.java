package it.akademija.document;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

@Service
public class DocumentService {

	@Autowired
	DocumentDAO documentDao;
	
	@Transactional
	public Optional<DocumentEntity> getDocumentById(Long id) {
		return documentDao.findById(id);
	}
	
	@Transactional
	public Boolean uploadDocument(MultipartFile file, long uploaderId) {
		if(file.getSize()<=128000) {
			try {
				DocumentEntity doc = new DocumentEntity(file.getName(), file.getContentType(), file.getBytes(), uploaderId);
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
