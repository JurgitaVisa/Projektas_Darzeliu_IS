package it.akademija.application;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import it.akademija.App;

@SpringBootTest(classes = { App.class,
		ApplicationController.class }, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ApplicationControllerTest {

	@Autowired
	private ApplicationController controller;

	@Test
	public void contextLoads() {
		assertNotNull(controller);
	}

}