
package it.akademija.kindergarten;

import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

@DataJpaTest

public class KindergartenRepositoryTest {

	@Autowired
	private TestEntityManager entityManager;

	@Autowired
	private KindergartenDAO kindergartenDAO;

	@Test
	public void itShoulSaveFindDeleteKindergarten() {

		Kindergarten kindergarten = new Kindergarten();
		kindergarten.setId("123456789");
		kindergarten.setName("test");
		kindergarten.setAddress("Adresas 3");
		kindergarten.setElderate("Seniunija");
		kindergarten.setCapacityAgeGroup2to3(12);
		kindergarten.setCapacityAgeGroup3to6(12);
		kindergarten = entityManager.persistAndFlush(kindergarten);

		assertTrue(kindergartenDAO.findById("123456789").isPresent());

		kindergartenDAO.deleteByName("test");
		assertTrue(kindergartenDAO.findById("123456789").isEmpty());

	}
}
