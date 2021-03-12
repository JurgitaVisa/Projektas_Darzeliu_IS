package it.akademija.kindergarten;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.ArrayList;
import java.util.List;

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
		assertThat(doesKindergartenExists).isTrue();
	}

	@Test
	public void testFindById() {
		Kindergarten kindergarten = service.findById("190033652");
		assertThat(kindergarten).isNotNull();
	}

	@Test
	public void testGetAllElderates() {
		List<String> elderates = new ArrayList<String>();
		elderates.add("Justiniškių");
		elderates.add("Naujininkų");

		assertThat(service.getAllElderates()).containsAll(elderates);
	}

}