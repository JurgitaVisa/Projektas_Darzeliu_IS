package it.akademija.application;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import it.akademija.application.priorities.Priorities;
import it.akademija.kindergarten.Kindergarten;
import it.akademija.kindergarten.KindergartenDAO;
import it.akademija.kindergartenchoise.KindergartenChoise;
import it.akademija.kindergartenchoise.KindergartenChoiseDAO;
import it.akademija.role.Role;
import it.akademija.user.ParentDetails;
import it.akademija.user.User;
import it.akademija.user.UserDAO;

@RunWith(SpringJUnit4ClassRunner.class)

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
	public void itShoulCreateFindDeleteApplication() {
		Application application = new Application();

		ParentDetails parentDetails = new ParentDetails("48602257896", "Test", "Test", "test@test.lt", "Adresas 1",
				"+37063502254");

		User mainGuardian = userDAO.save(
				new User(Role.USER, "Test", "Test", "test@test.lt", parentDetails, "test@test.lt", "test@test.lt"));

		Priorities priorities = new Priorities(true, false, false, false, false);

		Kindergarten first = kindergartenDAO
				.save(new Kindergarten("123456789", "Pavadinimas 1", "Adresas 2", "Seniunija", 12, 12));
		Kindergarten second = kindergartenDAO
				.save(new Kindergarten("146325698", "Pavadinimas 2", "Adresas 3", "Seniunija", 12, 12));
		Kindergarten third = kindergartenDAO
				.save(new Kindergarten("369258147", "Pavadinimas 3", "Adresas 4", "Seniunija", 12, 12));

		Set<KindergartenChoise> choices = new HashSet<>();
		choices.add(choiseDAO.save(new KindergartenChoise(first, application, 1)));
		choices.add(choiseDAO.save(new KindergartenChoise(second, application, 2)));
		choices.add(choiseDAO.save(new KindergartenChoise(third, application, 3)));

		application.setAdditionalGuardian(null);
		application.setPriorities(priorities);
		application.setPriorityScore(priorities.getScore());
		application.setSubmitedAt();
		application.setStatus(ApplicationStatus.Pateiktas);
		application.setChildName("Test");
		application.setChildSurname("Test");
		application.setChildPersonalCode("49902256547");
		application.setBirthdate(LocalDate.of(2019, 5, 5));
		application.setMainGuardian(mainGuardian);
		application.setKindergartenChoises(choices);

		application = entityManager.persistAndFlush(application);

		assertThat(applicationDAO.findById(application.getId()).get()).isEqualTo(application);
		assertThat(applicationDAO.existsApplicationByChildPersonalCode("49902256547")).isEqualTo(true);
		applicationDAO.deleteById(application.getId());
		assertThat(applicationDAO.findAll().isEmpty());

	}

}
