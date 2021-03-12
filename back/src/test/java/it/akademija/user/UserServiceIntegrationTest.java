package it.akademija.user;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

@SpringBootTest
public class UserServiceIntegrationTest {

	@Autowired
	private UserService service;

	@Test
	public void testGetUserByUsername() {
		User userByUsername = service.findByUsername("admin@admin.lt");
		assertThat(userByUsername).isNotNull();
	}

	@Test
	public void testGetAllUsers() {

		PageRequest page = PageRequest.of(1, 10);
		Page<UserInfo> users = service.getAllUsers(page);
		assertThat(users).isNotNull();
	}

	@Test
	public void testCreateUser() {
		UserDTO newUser = new UserDTO("MANAGER", "stest", "stest", "stest@test.lt", "stest@test.lt", "stest@test.lt");
		service.createUser(newUser);
		assertThat(newUser).isNotNull();
	}

}