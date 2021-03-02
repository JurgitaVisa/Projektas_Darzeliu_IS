package it.akademija.user;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)

@SpringBootTest
public class UserServiceIntegrationTest {

	@Autowired
	private UserService service;

	@Test
	public void testGetUserByUsername() {
		User userByUsername = service.findByUsername("admin@admin.lt");
		assertThat(userByUsername).isNotNull();
	}

}