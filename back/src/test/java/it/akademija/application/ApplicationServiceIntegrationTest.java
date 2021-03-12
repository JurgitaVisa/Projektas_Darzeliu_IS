package it.akademija.application;

import static org.assertj.core.api.Assertions.assertThat;

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
	public void testGetAllUserApplications() {

		assertThat(service.getAllUserApplications("user@user.lt")).isNotNull();
	}

	@Test
	public void testGetPageFromSubtmittedApplications() {

		PageRequest page = PageRequest.of(1, 10);
		Page<ApplicationInfo> applications = service.getPageFromSubmittedApplications(page);
		assertThat(applications).isNotNull();
	}

	@Test
	public void testCreateNewApplication() {

	}

}