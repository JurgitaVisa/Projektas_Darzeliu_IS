
package it.akademija.user;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

@DataJpaTest

public class UserRepositoryTest {

	@Autowired
	private TestEntityManager entityManager;

	@Autowired
	private UserDAO userDAO;

	@Test
	public void itShoulSaveUser() {
		User user = new User();
		user.setName("test");
		user.setSurname("test");
		user.setPassword("test@test.lt");
		user.setEmail("test@test.lt");
		user.setUsername("test@test.lt");

		user = entityManager.persistAndFlush(user);

		assertThat(userDAO.findByUsername("test@test.lt")).isEqualTo(user);
	}

	@Test
	public void itShouldDeleteUserByUsername() {
		User user = new User();
		user.setName("test");
		user.setSurname("test");
		user.setPassword("test@test.lt");
		user.setEmail("test@test.lt");
		user.setUsername("test@test.lt");

		user = entityManager.persistAndFlush(user);
		userDAO.deleteByUsername("test@test.lt");

		assertThat(userDAO.findAll().isEmpty());
	}
}
