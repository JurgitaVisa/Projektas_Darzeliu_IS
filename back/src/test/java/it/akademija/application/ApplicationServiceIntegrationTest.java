package it.akademija.application;

import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

@SpringBootTest
public class ApplicationServiceIntegrationTest {

	@Autowired
	private ApplicationService service;

	@Test
	@Order(1)
	public void testGetPageFromSubtmittedApplications() {

		PageRequest page = PageRequest.of(1, 10);
		Page<ApplicationInfo> applications = service.getPageFromSubmittedApplications(page);
		assertTrue(applications.getSize() != 0);
	}

//	@Test
//
//	@Order(2)
//	public void testGetAllUserApplications() {
//		assertTrue(service.getAllUserApplications("user@user.lt").size() == 0);
//	}

}