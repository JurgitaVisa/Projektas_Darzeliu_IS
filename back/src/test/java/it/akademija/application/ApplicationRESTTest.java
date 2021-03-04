package it.akademija.application;

import static org.junit.Assert.assertEquals;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

import org.junit.Test;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jayway.restassured.RestAssured;

import it.akademija.App;
import it.akademija.application.priorities.Priorities;
import it.akademija.kindergarten.Kindergarten;
import it.akademija.kindergartenchoise.KindergartenChoise;
import it.akademija.user.ParentDetails;
import it.akademija.user.User;
import it.akademija.user.UserService;

@RunWith(SpringJUnit4ClassRunner.class)

@SpringBootTest(classes = { App.class,
		ApplicationController.class }, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)

@TestMethodOrder(OrderAnnotation.class)

@AutoConfigureMockMvc
public class ApplicationRESTTest {

	@Value("${local.server.port}")
	int port;

	@Autowired
	private MockMvc mvc;

	@Autowired
	private ObjectMapper mapper;

	@Autowired
	private WebApplicationContext context;

	@Autowired
	private UserService service;

	@BeforeAll
	public void setup() throws Exception {
		RestAssured.port = port;
		mvc = MockMvcBuilders.webAppContextSetup(context).apply(springSecurity()).build();

	}

	@Test
	@Order(1)
	@WithMockUser(username = "user", roles = { "USER" })
	public void testPostDeleteApplicationMethod() throws Exception {

		/*
		 * ParentDetails parentDetails = new ParentDetails("48602257896", "Test",
		 * "Test", "test@test.lt", "Adresas 1", "+37063502254");
		 */

		// User mainGuard = userDAO.findByUsername("user@user.lt");

		/*
		 * UserDTO mainGuardian = new
		 * UserDTO(userDAO.findByUsername("user@user.lt").getRole().name(),
		 * userDAO.findByUsername("user@user.lt").getName(),
		 * userDAO.findByUsername("user@user.lt").getSurname(),
		 * userDAO.findByUsername("user@user.lt").getParentDetails().getPersonalCode(),
		 * userDAO.findByUsername("user@user.lt").getParentDetails().getAddress(),
		 * userDAO.findByUsername("user@user.lt").getParentDetails().getPhone(),
		 * userDAO.findByUsername("user@user.lt").getEmail(),
		 * userDAO.findByUsername("user@user.lt").getUsername(),
		 * userDAO.findByUsername("user@user.lt").getPassword());
		 */

		/*
		 * UserDTO mainGuardian = new UserDTO(Role.USER.toString(), "Test", "Test",
		 * "48602257896", "Adresas 1", "+37063502254", "testu@test.lt", "testu@test.lt",
		 * "testu@test.lt");
		 */

		/*
		 * PrioritiesDTO prioritiesDTO = new PrioritiesDTO(true, false, false, false,
		 * false);
		 */

		/*
		 * KindergartenChoiseDTO choicesDTO = new KindergartenChoiseDTO("kindergarten1",
		 * "kindergarten2", "kindergarten3", "kindergarten4", "kindergarten5");
		 * 
		 * ParentDetailsDTO secondGuardian = new ParentDetailsDTO("48703146987", "Test",
		 * "Test", "other@other.lt", "Adresas 1", "+37063728896");
		 */

		/*
		 * ApplicationDTO applicationDTO = new ApplicationDTO("Test", "Test",
		 * "49902256546", LocalDate.of(2019, 5, 5), prioritiesDTO, mainGuardian,
		 * secondGuardian, choicesDTO);
		 */
		/*
		 * UserDTO userData = new UserDTO("USER", "user", "user", "12345678987",
		 * "Address 1", "+37061398876", "user@user.lt", "user@user.lt", "user@user.lt");
		 * User mainGuardian = service.updateUserData(userData, "user@user.lt");
		 */

		User mainGuardian = service.findByUsername("user@user.lt");
		ParentDetails details = mainGuardian.getParentDetails();

		details.setAddress("Address 1");
		details.setPersonalCode("12345678987");
		details.setPhone("+37061398876");
		details.setEmail("user@user.lt");
		details.setName("user");
		details.setSurname("user");

		mainGuardian.setName("user");
		mainGuardian.setSurname("user");
		mainGuardian.setEmail("user@user.lt");

		Application application = new Application();
		Priorities priorities = new Priorities(true, false, false, false, false);

		Kindergarten first = new Kindergarten("123456789", "Pavadinimas 1", "Adresas 2", "Seniunija", 12, 12);
		Kindergarten second = new Kindergarten("146325698", "Pavadinimas 2", "Adresas 3", "Seniunija", 12, 12);
		Kindergarten third = new Kindergarten("369258147", "Pavadinimas 3", "Adresas 4", "Seniunija", 12, 12);
		Kindergarten fourth = new Kindergarten("469258147", "Pavadinimas 4", "Adresas 5", "Seniunija", 12, 12);
		Kindergarten fifth = new Kindergarten("569258147", "Pavadinimas 5", "Adresas 6", "Seniunija", 12, 12);

		Set<KindergartenChoise> choices = new HashSet<>();
		choices.add(new KindergartenChoise(first, application, 1));
		choices.add(new KindergartenChoise(second, application, 2));
		choices.add(new KindergartenChoise(third, application, 3));
		choices.add(new KindergartenChoise(fourth, application, 4));
		choices.add(new KindergartenChoise(fifth, application, 5));

		application.setId(123L);
		application.setChildName("Test");
		application.setChildSurname("Test");
		application.setChildPersonalCode("49902556547");
		application.setBirthdate(LocalDate.of(2019, 5, 5));
		application.setAdditionalGuardian(null);
		application.setPriorities(priorities);
		application.setPriorityScore(priorities.getScore());
		application.setSubmitedAt();
		application.setStatus(ApplicationStatus.Pateiktas);
		application.setMainGuardian(mainGuardian);
		application.setAdditionalGuardian(null);
		application.setNumberInWaitingList(0);
		application.setApprovedKindergarten(first);
		application.setKindergartenChoises(choices);

		String jsonRequest = mapper.writeValueAsString(application);

		MvcResult postNew = mvc.perform(post("/api/prasymai/user/new", "user@user.lt").content(jsonRequest)
				.contentType(MediaType.APPLICATION_JSON)).andExpect(status().isOk()).andReturn();
		assertEquals(200, postNew.getResponse().getStatus());

	}

}