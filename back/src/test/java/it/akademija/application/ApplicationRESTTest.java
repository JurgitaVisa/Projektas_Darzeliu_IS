package it.akademija.application;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.time.LocalDate;
import java.util.Set;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
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
import it.akademija.application.management.RegistrationStatusService;
import it.akademija.application.priorities.PrioritiesDTO;
import it.akademija.kindergartenchoise.KindergartenChoiseDTO;
import it.akademija.user.UserDTO;
import it.akademija.user.UserService;

@TestInstance(Lifecycle.PER_CLASS)
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
	private UserService userService;

	@MockBean
	private ApplicationDAO applicationDAO;

	@Autowired
	private RegistrationStatusDAO statusDAO;

	@Autowired
	private RegistrationStatusService statusService;

	@BeforeAll
	public void setup() throws Exception {
		RestAssured.port = port;
		mvc = MockMvcBuilders.webAppContextSetup(context).apply(springSecurity()).build();
	}

	@Test
	@Order(1)
	@WithMockUser(username = "manager@manager.lt", roles = { "MANAGER" })
	public void testChangeStatusMethod() throws Exception {

		RegistrationStatus status = new RegistrationStatus();
		status.setStatus(false);
		statusDAO.save(status);

		MvcResult changeStatus = mvc.perform(post("/api/status/{status}", status)).andExpect(status().isOk())
				.andReturn();
		assertEquals(200, changeStatus.getResponse().getStatus());

		MvcResult getStatus = mvc.perform(get("/api/status/")).andExpect(status().isOk()).andReturn();
		assertEquals(200, getStatus.getResponse().getStatus());

	}

	@Test
	@Order(2)
	@WithMockUser(username = "user@user.lt", roles = { "USER" })
	public void testPostDeleteApplicationMethod() throws Exception {

		RegistrationStatus status = new RegistrationStatus();
		status.setStatus(true);
		statusService.saveStatus(status);

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

		status.setStatus(false);
		statusService.saveStatus(status);

		MvcResult postNewNotAllowed = mvc.perform(post("/api/prasymai/user/new")).andExpect(status().isBadRequest())
				.andReturn();
		assertEquals(400, postNewNotAllowed.getResponse().getStatus());

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

		Set<ApplicationInfoUser> applicationsOfUser = applicationService.getAllUserApplications("user@user.lt");

		mvc.perform(get("/api/prasymai/user/")).andExpect(result -> assertTrue(applicationsOfUser.isEmpty()));

	}

	@Test
	@Order(5)
	@WithMockUser(username = "admin@admin.lt", roles = { "ADMIN" })
	public void testLockUnlock() throws Exception {

		MvcResult lock = mvc.perform(post("/api/queue/lock")).andExpect(status().isOk()).andReturn();
		assertEquals(200, lock.getResponse().getStatus());

		MvcResult unlock = mvc.perform(post("/api/queue/unlock")).andExpect(status().isOk()).andReturn();
		assertEquals(200, unlock.getResponse().getStatus());

	}

	@Test
	@Order(6)
	@WithMockUser(username = "manager@manager.lt", roles = { "MANAGER" })
	public void testProcessConfirmQueue() throws Exception {

		MvcResult process = mvc.perform(post("/api/queue/process")).andExpect(status().isOk()).andReturn();
		assertEquals(200, process.getResponse().getStatus());

		MvcResult confirm = mvc.perform(post("/api/queue/confirm")).andExpect(status().isOk()).andReturn();
		assertEquals(200, confirm.getResponse().getStatus());

	}

}