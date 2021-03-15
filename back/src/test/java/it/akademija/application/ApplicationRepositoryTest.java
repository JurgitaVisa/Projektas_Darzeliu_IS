package it.akademija.application;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import it.akademija.application.priorities.Priorities;
import it.akademija.kindergarten.Kindergarten;
import it.akademija.kindergarten.KindergartenDAO;
import it.akademija.kindergartenchoise.KindergartenChoise;
import it.akademija.kindergartenchoise.KindergartenChoiseDAO;
import it.akademija.role.Role;
import it.akademija.user.ParentDetails;
import it.akademija.user.User;
import it.akademija.user.UserDAO;

@DataJpaTest
public class ApplicationRepositoryTest {

	@Autowired
	private TestEntityManager entityManager;

	@Autowired
	private ApplicationDAO applicationDAO;

	@Autowired
	private KindergartenChoiseDAO choiseDAO;

	@Autowired
	private KindergartenDAO kindergartenDAO;

	@Autowired
	private UserDAO userDAO;

	@Test
	public void itShouldProcessApplications() {
		Application application1 = new Application();

		ParentDetails parentDetails = new ParentDetails("48602257896", "Test", "Test", "test@test.lt", "Adresas 1",
				"+37063502254");

		User mainGuardian = userDAO.save(
				new User(Role.USER, "Test", "Test", "test@test.lt", parentDetails, "test@test.lt", "test@test.lt"));

		Priorities priorities = new Priorities(true, true, false, false, false);

		Kindergarten first = kindergartenDAO
				.save(new Kindergarten("123456789", "Pavadinimas 1", "Adresas 2", "Seniunija", 12, 12));
		Kindergarten second = kindergartenDAO
				.save(new Kindergarten("146325698", "Pavadinimas 2", "Adresas 3", "Seniunija", 12, 12));
		Kindergarten third = kindergartenDAO
				.save(new Kindergarten("369258147", "Pavadinimas 3", "Adresas 4", "Seniunija", 12, 12));

		Set<KindergartenChoise> choices = new HashSet<>();
		choices.add(choiseDAO.save(new KindergartenChoise(first, application1, 1)));
		choices.add(choiseDAO.save(new KindergartenChoise(second, application1, 2)));
		choices.add(choiseDAO.save(new KindergartenChoise(third, application1, 3)));

		application1.setAdditionalGuardian(null);
		application1.setPriorities(priorities);
		application1.setPriorityScore(priorities.getScore());
		application1.setSubmitedAt();
		application1.setStatus(ApplicationStatus.Patvirtintas);
		application1.setChildName("Test");
		application1.setChildSurname("Test");
		application1.setChildPersonalCode("49902256547");
		application1.setBirthdate(LocalDate.of(2019, 5, 5));
		application1.setMainGuardian(mainGuardian);
		application1.setKindergartenChoises(choices);
		application1.setApprovedKindergarten(first);

		application1 = entityManager.persistAndFlush(application1);

		Application application2 = new Application();

		ParentDetails parentDetail2 = new ParentDetails("48702257896", "Test", "Test", "test1@test.lt", "Adresas 1",
				"+37063502254");

		User mainGuardian2 = userDAO.save(
				new User(Role.USER, "Test", "Test", "test1@test.lt", parentDetail2, "test1@test.lt", "test1@test.lt"));

		Priorities priorities2 = new Priorities(true, false, false, false, false);

		Kindergarten first2 = kindergartenDAO
				.save(new Kindergarten("123456789", "Pavadinimas 1", "Adresas 2", "Seniunija", 12, 12));
		Kindergarten second2 = kindergartenDAO
				.save(new Kindergarten("146325698", "Pavadinimas 2", "Adresas 3", "Seniunija", 12, 12));
		Kindergarten third2 = kindergartenDAO
				.save(new Kindergarten("369258147", "Pavadinimas 3", "Adresas 4", "Seniunija", 12, 12));

		Set<KindergartenChoise> choices2 = new HashSet<>();
		choices2.add(choiseDAO.save(new KindergartenChoise(first2, application2, 1)));
		choices2.add(choiseDAO.save(new KindergartenChoise(second2, application2, 2)));
		choices2.add(choiseDAO.save(new KindergartenChoise(third2, application2, 3)));

		application2.setAdditionalGuardian(null);
		application2.setPriorities(priorities2);
		application2.setPriorityScore(priorities2.getScore());
		application2.setSubmitedAt();
		application2.setStatus(ApplicationStatus.Pateiktas);
		application2.setChildName("Test");
		application2.setChildSurname("Test");
		application2.setChildPersonalCode("49903256547");
		application2.setBirthdate(LocalDate.of(2019, 5, 5));
		application2.setMainGuardian(mainGuardian2);
		application2.setKindergartenChoises(choices2);

		application2 = entityManager.persistAndFlush(application2);

		assertEquals(application1.getChildName(), applicationDAO.findById(application1.getId()).get().getChildName());
		assertTrue(application1.getApprovedKindergarten().getId().equals("123456789"));
		assertTrue(applicationDAO.existsApplicationByChildPersonalCode("49903256547"));
		assertEquals(1, applicationDAO.findNumberOfUnprocessedApplications());
		assertEquals(1, applicationDAO.findAllApplicationsWithStatusSubmitted().size());
		applicationDAO.deleteById(application1.getId());
		applicationDAO.deleteById(application2.getId());
		assertTrue(applicationDAO.findAll().isEmpty());

	}

}
