package it.akademija.application;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
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

import it.akademija.application.priorities.Priorities;
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

		assertEquals(1, service.getAllUserApplications("user1@user.lt").size());

		assertEquals(1, userService.findByUsername("user1@user.lt").getUserApplications().size());
		Long id = userService.findByUsername("user1@user.lt").getUserApplications().stream()
				.filter(a -> a.getChildName().equals("Test")).findFirst().get().getId();
		service.deactivateApplication(id);
		assertEquals(ApplicationStatus.Neaktualus, userService.findByUsername("user1@user.lt").getUserApplications()
				.stream().filter(a -> a.getChildName().equals("Test")).findFirst().get().getStatus());

		userService.deleteUser("user1@user.lt");

	}

	@Test
	@Order(3)
	public void testApplicationInfoAndPriorities() {

		ApplicationInfo app = new ApplicationInfo(123L, "49902261456", "Test", "Test", ApplicationStatus.Laukiantis,
				"123456789", "123456749", "133456789", "123446789", "128456789");
		assertTrue(app.getChildSurname().equals("Test"));
		assertTrue(app.getChildName().equals("Test"));
		assertTrue(app.getChildPersonalCode().equals("49902261456"));

		Application applic = new Application();
		Priorities prior = new Priorities();
		prior.setChildIsAdopted(false);
		prior.setFamilyHasThreeOrMoreChildrenInSchools(true);
		prior.setGuardianDisability(false);
		prior.setGuardianInSchool(true);
		prior.setLivesInVilnius(true);
		prior.setApplication(applic);

		assertEquals(12, prior.getScore());
		assertFalse(prior.isChildIsAdopted());
		assertTrue(prior.isFamilyHasThreeOrMoreChildrenInSchools());
		assertFalse(prior.isGuardianDisability());
		assertTrue(prior.isLivesInVilnius());
		assertTrue(prior.isGuardianInSchool());
		assertEquals(applic, prior.getApplication());
	}

}