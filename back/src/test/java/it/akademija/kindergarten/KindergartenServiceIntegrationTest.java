package it.akademija.kindergarten;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class KindergartenServiceIntegrationTest {

	@Autowired
	private KindergartenService service;

	@Test
	public void testNameAlreadyExist() {
		boolean doesKindergartenExists = service.nameAlreadyExists("Karuselė", "190033652");
		assertTrue(doesKindergartenExists);
	}

	@Test
	public void testFindById() {
		Kindergarten kindergarten = service.findById("190033652");
		assertEquals("Karuselė", kindergarten.getName());
	}

	@Test
	public void testGetAllElderates() {

		assertTrue(service.getAllElderates().size() != 0);
	}

}