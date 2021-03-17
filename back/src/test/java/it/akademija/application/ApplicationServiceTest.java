package it.akademija.application;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.LocalDate;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import it.akademija.application.priorities.PrioritiesDAO;
import it.akademija.application.priorities.PrioritiesDTO;
import it.akademija.application.queue.ApplicationQueueService;
import it.akademija.kindergarten.Kindergarten;
import it.akademija.kindergarten.KindergartenDAO;
import it.akademija.kindergarten.KindergartenService;
import it.akademija.kindergartenchoise.KindergartenChoiseDAO;
import it.akademija.kindergartenchoise.KindergartenChoiseDTO;
import it.akademija.user.ParentDetailsDAO;
import it.akademija.user.UserDAO;
import it.akademija.user.UserDTO;
import it.akademija.user.UserService;

@RunWith(SpringRunner.class)
@SpringBootTest
class ApplicationServiceTest {

	@InjectMocks
	@Autowired
	private ApplicationService applicationService;

	@Mock
	private ApplicationDAO applicationDao;

	@MockBean
	private UserService userService;

	@MockBean
	private KindergartenService gartenService;

	@MockBean
	private ApplicationQueueService queueService;

	@MockBean
	private ParentDetailsDAO parentDetailsDao;

	@Mock
	private PrioritiesDAO prioritiesDao;

	@MockBean
	private KindergartenChoiseDAO choiseDao;

	@MockBean
	private UserDAO userDao;

	@MockBean
	private KindergartenDAO gartenDao;

	@Test
	@Transactional
	void testCreateNewApplication() {

		PrioritiesDTO prioritiesDto = new PrioritiesDTO(true, false, false, false, false);

		UserDTO mainGuardian = new UserDTO("USER", "Testas", "Testauskas", "12345698745", "Adresas 1", "+37068945123",
				"test@test.lt", "test@test.lt", "password");

		userService.createUser(mainGuardian);

		Kindergarten garten = new Kindergarten("111111111", "A darzelis", "Adresas darzelio A", "Antakalnio", 1, 0);
		gartenDao.save(garten);
		Mockito.when(gartenService.findById("111111111")).thenReturn(garten);

		KindergartenChoiseDTO kindergartenChoises = new KindergartenChoiseDTO("111111111", "", "", "", "");

		ApplicationDTO data = new ApplicationDTO("Pirmas", "Prasymas", "11111111111", LocalDate.of(2019, 03, 01),
				prioritiesDto, mainGuardian, null, kindergartenChoises);

		assertNotNull(applicationService.createNewApplication("test@test.lt", data));
		assertTrue(applicationService.existsByPersonalCode("11111111111"));

		PageRequest page = PageRequest.of(1, 10);
		applicationService.getPageFromSubmittedApplications(page);
		assertTrue(applicationService.getPageFromSubmittedApplications(page).getSize() != 0);

		queueService.processApplicationsToQueue();

	}

}
