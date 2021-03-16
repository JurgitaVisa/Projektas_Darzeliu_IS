package it.akademija.user;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
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

@TestInstance(Lifecycle.PER_CLASS)
@SpringBootTest(classes = { App.class,
		UserController.class }, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestMethodOrder(OrderAnnotation.class)
@AutoConfigureMockMvc
public class UserRESTTest {

	@Value("${local.server.port}")
	int port;

	@Autowired
	private MockMvc mvc;

	@Autowired
	private ObjectMapper mapper;

	@Autowired
	private WebApplicationContext context;

	@Autowired
	private UserService userService;

	@BeforeAll
	public void setup() throws Exception {
		RestAssured.port = port;
		mvc = MockMvcBuilders.webAppContextSetup(context).apply(springSecurity()).build();

	}

	@Test

	@Order(1)

	@WithMockUser(username = "admin@admin.lt", roles = { "ADMIN" })
	public void testPostDeleteUserMethod() throws Exception {
		ParentDetails details = new ParentDetails();
		details.setPersonalCode("48902230223");
		details.setName("Test");
		details.setSurname("Test");
		details.setEmail("test@test.lt");
		details.setAddress("Adresas 5");
		details.setPhone("+37061502254");
		User newUser = new User(Role.USER, "Test", "Test", "test@test.lt", details, "test@test.lt", "test@test.lt");

		String jsonRequest = mapper.writeValueAsString(newUser);

		MvcResult postNew = mvc.perform(
				post("/api/users/admin/createuser").content(jsonRequest).contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isCreated()).andReturn();
		assertEquals(201, postNew.getResponse().getStatus());

	}

	@Test
	@Order(2)
	@WithMockUser(username = "test@test.lt", roles = { "USER" })
	public void updateUser() throws Exception {

		ParentDetails details = new ParentDetails();
		details.setPersonalCode("48902230223");
		details.setName("Testas");
		details.setSurname("Test");
		details.setEmail("test@test.lt");
		details.setAddress("Adresas 5");
		details.setPhone("+37061502254");
		User newUser = new User(Role.USER, "Test", "Test", "test@test.lt", details, "test@test.lt", "test@test.lt");

		String jsonRequest = mapper.writeValueAsString(newUser);

		MvcResult updateUser = mvc
				.perform(put("/api/users/update").content(jsonRequest).contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk()).andReturn();
		assertEquals(200, updateUser.getResponse().getStatus());

	}

	@Test

	@Order(3)

	@WithMockUser(username = "admin@admin.lt", roles = { "ADMIN" })
	public void deleteUserMethod() throws Exception {

		MvcResult deleteUser = mvc.perform(delete("/api/users/admin/delete/{username}", "test@test.lt"))
				.andExpect(status().isOk()).andReturn();
		assertEquals(200, deleteUser.getResponse().getStatus());

	}

	@Test
	@Order(4)
	@WithMockUser(username = "user", roles = { "USER" })
	public void shouldRejectDeletingWhenNotAdmin() throws Exception {
		MvcResult deleteUser = mvc.perform(delete("/api/users/admin/delete/{username}", "test@test.lt"))
				.andExpect(status().isForbidden()).andReturn();
		assertEquals(403, deleteUser.getResponse().getStatus());
	}

	@Test
	@Order(5)
	@WithMockUser(username = "admin@admin.lt", roles = { "ADMIN" })
	public void userAlreadyExsists() throws Exception {
		User newUser = new User(Role.MANAGER, "manager", "manager", "manager1@manager.lt", null, "manager1@manager.lt",
				"manager1@manager.lt");

		String jsonRequest = mapper.writeValueAsString(newUser);

		MvcResult postNew = mvc.perform(
				post("/api/users/admin/createuser").content(jsonRequest).contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isCreated()).andReturn();
		assertEquals(201, postNew.getResponse().getStatus());

		UserInfo userInfo = userService.getUserDetails("manager1@manager.lt");
		String jsonRequest3 = mapper.writeValueAsString(userInfo);

		MvcResult getOneUser = mvc
				.perform(get("/api/users/user").content(jsonRequest3).contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk()).andReturn();

		assertEquals(200, getOneUser.getResponse().getStatus());

		User newUser2 = new User(Role.MANAGER, "manageris", "manager", "manager1@manager.lt", null,
				"manager1@manager.lt", "manager1@manager.lt");

		String jsonRequest2 = mapper.writeValueAsString(newUser2);

		MvcResult postNew2 = mvc.perform(
				post("/api/users/admin/createuser").content(jsonRequest2).contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isBadRequest()).andReturn();
		assertEquals(400, postNew2.getResponse().getStatus());

		MvcResult deleteUser = mvc.perform(delete("/api/users/admin/delete/{username}", "manager1@manager.lt"))
				.andExpect(status().isOk()).andReturn();
		assertEquals(200, deleteUser.getResponse().getStatus());
	}
}
