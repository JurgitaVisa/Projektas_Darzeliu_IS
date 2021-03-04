
package it.akademija.user;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import it.akademija.App;

@RunWith(SpringJUnit4ClassRunner.class)

@SpringBootTest(classes = { App.class,
		UserController.class }, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class UserControllerTest {

	@Autowired
	private UserController controller;

	@Test
	public void contextLoads() {
		assertThat(controller).isNotNull();
	}

}
