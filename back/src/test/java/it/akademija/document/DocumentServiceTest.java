package it.akademija.document;

import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.junit4.SpringRunner;

import it.akademija.user.UserDAO;

@RunWith(SpringRunner.class)
@SpringBootTest
class DocumentServiceTest {

	@InjectMocks
	@Autowired
	private DocumentService documentService;

	@MockBean
	private DocumentDAO documentDao;

	@Autowired
	private UserDAO userDao;

	@Test
	void testUploadDocument() {

		MockMultipartFile file = new MockMultipartFile("file", "file.pdf", MediaType.APPLICATION_PDF_VALUE,
				"Hello, World!".getBytes());

		assertTrue(documentService.uploadDocument(file, "file.pdf", 1L));

	}

	@Test
	void getDocumentById() {

		assertTrue(documentService.getDocumentsByUploaderId(userDao.findByUsername("user@user.lt").getUserId())
				.size() == 0);

	}

}
