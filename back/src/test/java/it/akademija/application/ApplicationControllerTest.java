package it.akademija.application;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import it.akademija.App;

@RunWith(SpringJUnit4ClassRunner.class)

@SpringBootTest(classes = { App.class,
		ApplicationController.class }, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ApplicationControllerTest {

	@Autowired
	private ApplicationController controller;

	@Test
	public void contextLoads() {
		assertThat(controller).isNotNull();
	}

}