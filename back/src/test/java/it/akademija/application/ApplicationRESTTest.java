package it.akademija.application;

import static org.junit.Assert.assertEquals;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.time.LocalDate;

import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
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
import it.akademija.application.management.RegistrationStatus;
import it.akademija.application.management.RegistrationStatusController;
import it.akademija.application.management.RegistrationStatusDAO;
import it.akademija.application.priorities.PrioritiesDTO;
import it.akademija.kindergartenchoise.KindergartenChoiseDTO;
import it.akademija.user.UserDTO;

@RunWith(SpringJUnit4ClassRunner.class)

@SpringBootTest(classes = { App.class, ApplicationController.class,
		RegistrationStatusController.class }, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
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

	@MockBean
	private ApplicationService applicationService;

	@MockBean
	private ApplicationDAO applicationDAO;

	@MockBean
	private RegistrationStatusDAO statusDAO;

	@Before
	public void setup() throws Exception {
		RestAssured.port = port;
		mvc = MockMvcBuilders.webAppContextSetup(context).apply(springSecurity()).build();
	}

	@Test
	@Order(1)
	@WithMockUser(username = "manager@manager.lt", roles = { "MANAGER" })
	public void testChangeStatusMethod() throws Exception {

		RegistrationStatus status = new RegistrationStatus();
		status.setStatus(true);
		statusDAO.save(status);

		MvcResult changeStatus = mvc.perform(post("/api/status/{status}", status)).andExpect(status().isOk())
				.andReturn();
		assertEquals(200, changeStatus.getResponse().getStatus());

	}

	@Test
	@Order(2)
	@WithMockUser(username = "user@user.lt", roles = { "USER" })
	public void testPostDeleteApplicationMethod() throws Exception {

		PrioritiesDTO priorities = new PrioritiesDTO();
		priorities.setLivesInVilnius(true);
		KindergartenChoiseDTO choices = new KindergartenChoiseDTO();
		choices.setKindergartenId1("190031797");
		UserDTO mainGuardian = new UserDTO("USER", "user", "user", "12345678988", "Address 1", "+37061398876",
				"user@user.lt", "user@user.lt", "user@user.lt");

		ApplicationDTO application = new ApplicationDTO();
		application.setChildName("test");
		application.setChildSurname("test");
		application.setChildPersonalCode("49902558947");
		application.setBirthdate(LocalDate.of(2019, 5, 5));
		application.setPriorities(priorities);
		application.setMainGuardian(mainGuardian);
		application.setAdditionalGuardian(null);
		application.setKindergartenChoises(choices);

		String jsonRequest = mapper.writeValueAsString(application);

		MvcResult postNew = mvc
				.perform(post("/api/prasymai/user/new").content(jsonRequest).contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk()).andReturn();
		assertEquals(200, postNew.getResponse().getStatus());

	}

	@Test
	@Order(3)
	@WithMockUser(username = "user@user.lt", roles = { "USER" })
	public void testDeleteApplicationMethod() throws Exception {

		Application application = new Application();
		application.setId(123L);
		applicationDAO.save(application);

		MvcResult deleteApplication = mvc.perform(delete("/api/prasymai/user/delete/{id}", 123L))
				.andExpect(status().isOk()).andReturn();
		assertEquals(200, deleteApplication.getResponse().getStatus());

	}

	@Test
	@Order(4)
	@WithMockUser(username = "user@user.lt", roles = { "USER" })
	public void testGetAllUserApplications() throws Exception {

		MvcResult getApplications = mvc.perform(get("/api/prasymai/user/", "user@user.lt")).andExpect(status().isOk())
				.andReturn();
		assertEquals(200, getApplications.getResponse().getStatus());

	}

}