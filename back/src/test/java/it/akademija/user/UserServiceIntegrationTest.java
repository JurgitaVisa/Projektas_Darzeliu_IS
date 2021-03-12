package it.akademija.user;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Order;
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
	@Order(1)
	public void testGetUserByUsername() {

		assertEquals("admin", service.getUserDetails("admin@admin.lt").getName());
	}

	@Test
	@Order(2)
	public void testGetAllUsers() {

		PageRequest page = PageRequest.of(1, 10);
		Page<UserInfo> users = service.getAllUsers(page);
		assertTrue(users.getSize() != 0);
	}

	@Test
	@Order(3)
	public void testCreateDeleteUser() {

		UserDTO newUser = new UserDTO("MANAGER", "stest", "stest", "stest@test.lt", "stest@test.lt", "stest@test.lt");
		service.createUser(newUser);
		assertEquals("stest", service.findByUsername("stest@test.lt").getName());

		service.deleteUser("stest@test.lt");
		assertNull(service.findByUsername("stest@test.lt"));

	}

}