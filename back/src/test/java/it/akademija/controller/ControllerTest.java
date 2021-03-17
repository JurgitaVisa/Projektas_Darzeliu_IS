package it.akademija.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

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
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jayway.restassured.RestAssured;

import it.akademija.App;
import it.akademija.role.Role;
import it.akademija.user.User;
import it.akademija.user.UserDAO;

@TestInstance(Lifecycle.PER_CLASS)
@SpringBootTest(classes = { App.class,
		MainAppController.class }, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestMethodOrder(OrderAnnotation.class)
@AutoConfigureMockMvc
public class ControllerTest {

	@Value("${local.server.port}")
	int port;

	@Autowired
	private MockMvc mvc;

	@Autowired
	private ObjectMapper mapper;

	@Autowired
	private WebApplicationContext context;

	@Autowired
	private UserDAO userDAO;

	@BeforeAll
	public void setup() throws Exception {
		RestAssured.port = port;
		mvc = MockMvcBuilders.webAppContextSetup(context).apply(springSecurity()).build();

	}

	@Test
	@Order(1)
	@WithMockUser(username = "admin@admin.lt", roles = { "ADMIN" })
	public void testGetLoggedInUser() throws Exception {

		User loggedInUser = userDAO.findByUsername("admin@admin.lt");

		String jsonRequest = mapper.writeValueAsString(loggedInUser);

		MvcResult getUser = mvc
				.perform(get("/api/loggedUser").content(jsonRequest).contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk()).andReturn();

		assertEquals(200, getUser.getResponse().getStatus());

	}

	@Test
	@Order(2)
	@WithMockUser(username = "admin", roles = { "ADMIN" })
	public void testGetLoggedInUserName() throws Exception {

		User newUser = new User(Role.MANAGER, "manager", "manager", "manager1@manager.lt", null, "manager1@manager.lt",
				"manager1@manager.lt");

		String username = newUser.getName();

		String jsonRequest = mapper.writeValueAsString(username);

		MvcResult getUsername = mvc
				.perform(get("/api/loggedUserName").content(jsonRequest).contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk()).andReturn();

		assertEquals(200, getUsername.getResponse().getStatus());

	}

	@Test
	@Order(3)
	@WithMockUser(username = "admin@admin.lt", roles = { "ADMIN" })
	public void testGetLoggedInRole() throws Exception {

		String userRole = userDAO.findByUsername("admin@admin.lt").getRole().name();

		String jsonRequest2 = mapper.writeValueAsString(userRole);

		MvcResult getUserRole = mvc
				.perform(get("/api/loggedUserRole").content(jsonRequest2).contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk()).andReturn();

		assertEquals(200, getUserRole.getResponse().getStatus());
	}

}
