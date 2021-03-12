package it.akademija.kindergarten;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import it.akademija.App;

@SpringBootTest(classes = { App.class,
		KindergartenController.class }, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class KindergartenControllerTest {

	@Autowired
	private KindergartenController controller;

	@Test
	public void contextLoads() {
		assertNotNull(controller);
	}

}