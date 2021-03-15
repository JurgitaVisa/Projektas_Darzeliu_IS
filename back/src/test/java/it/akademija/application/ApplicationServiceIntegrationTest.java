package it.akademija.application;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.LocalDate;

import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import it.akademija.application.priorities.PrioritiesDTO;
import it.akademija.application.queue.ApplicationQueueInfo;
import it.akademija.application.queue.ApplicationQueueService;
import it.akademija.kindergartenchoise.KindergartenChoiseDTO;
import it.akademija.user.ParentDetailsDTO;
import it.akademija.user.UserDTO;
import it.akademija.user.UserService;

@TestMethodOrder(OrderAnnotation.class)
@SpringBootTest
public class ApplicationServiceIntegrationTest {

	@Autowired
	private ApplicationService service;

	@Autowired
	private ApplicationQueueService queueService;

	@Autowired
	private UserService userService;

	@Test
	@Order(1)
	public void testGetQueueInformation() {

		ApplicationQueueInfo queueInfo = new ApplicationQueueInfo(123L, "39902254789", "Test", "Test", "Kindergarten 1",
				ApplicationStatus.Patvirtintas, 0);
		PageRequest page = PageRequest.of(1, 10);
		Page<ApplicationQueueInfo> info = queueService.getApplicationQueueInformation(page);
		assertTrue(info.getSize() != 0);
		assertEquals(123L, queueInfo.getId());

	}

	@Test
	@Order(2)
	public void testCreateNewApplication() {

		/*
		 * if (null != userService.findByUsername("user1@user.lt")) {
		 * userService.deleteUser("user1@user.lt"); }
		 */

		UserDTO newUser = new UserDTO("USER", "firstuser", "firstuser", "22345678989", "Address 1", "+37061398876",
				"user1@user.lt", "user1@user.lt", "user1@user.lt");
		userService.createUser(newUser);

		ParentDetailsDTO secondGuardian = new ParentDetailsDTO("48702241234", "seconduser", "seconduser",
				"user2@user.lt", "Address 1", "+37061398876");

		PrioritiesDTO priorities = new PrioritiesDTO();
		priorities.setLivesInVilnius(true);

		KindergartenChoiseDTO choices = new KindergartenChoiseDTO();
		choices.setKindergartenId1("190028324");

		ApplicationDTO application = new ApplicationDTO("Test", "Test", "49702253898", LocalDate.of(2019, 5, 5),
				priorities, newUser, secondGuardian, choices);

		service.createNewApplication("user1@user.lt", application);

		assertEquals("firstuser", userService.findByUsername("user1@user.lt").getName());

		assertTrue(service.existsByPersonalCode("49702253898"));

		assertNotNull(userService.findByUsername("user1@user.lt"));

		// service.deleteApplication(newApplication.getId());

		assertEquals(1, service.getAllUserApplications("user1@user.lt").size());
		userService.deleteUser("user1@user.lt");

	}

}