
package it.akademija.user;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import it.akademija.role.Role;

@DataJpaTest

public class UserRepositoryTest {

	@Autowired
	private TestEntityManager entityManager;

	@Autowired
	private UserDAO userDAO;

	@Test
	public void itShoulSaveUser() {
		User user = new User();
		user.setRole(Role.MANAGER);
		user.setName("test");
		user.setSurname("test");
		user.setPassword("test@test.lt");
		user.setEmail("test@test.lt");
		user.setUsername("test@test.lt");

		user = entityManager.persistAndFlush(user);
		assertTrue(userDAO.findByRole(Role.MANAGER).size() != 0);

		assertTrue(userDAO.findById(user.getUserId()).get().getEmail().equalsIgnoreCase("test@test.lt"));

		assertEquals("test@test.lt", userDAO.findByUsername("test@test.lt").getUsername());
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

		assertTrue(userDAO.findAll().isEmpty());
	}
}
