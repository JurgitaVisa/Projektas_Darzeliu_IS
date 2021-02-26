
package it.akademija.user;

import static org.junit.Assert.assertEquals;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.Before;
import org.junit.Test;
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
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jayway.restassured.RestAssured;

import it.akademija.App;
import it.akademija.role.Role;

@RunWith(SpringJUnit4ClassRunner.class)

@SpringBootTest(classes = { App.class,
		UserController.class }, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)

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

	@Before
	public void setup() throws Exception {
		RestAssured.port = port;
		mvc = MockMvcBuilders.webAppContextSetup(context).apply(springSecurity()).build();

	}

	@Test
	@WithMockUser(username = "admin", roles = { "ADMIN" })
	public void testPostNewUserMethod() throws Exception {
		User newUser = new User(Role.MANAGER, "Test", "Test", "test@test.lt", null, "test@test.lt", "test@test.lt");

		String jsonRequest = mapper.writeValueAsString(newUser);

		MvcResult postNew = mvc.perform(
				post("/api/users/admin/createuser").content(jsonRequest).contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isCreated()).andReturn();
		assertEquals(201, postNew.getResponse().getStatus());

	}

	@Test
	@WithMockUser(username = "admin", roles = { "ADMIN" })
	public void testDeleteUserMethod() throws Exception {

		MvcResult deleteUser = mvc.perform(delete("/api/users/admin/delete/{username}", "test@test.lt"))
				.andExpect(status().isOk()).andReturn();
		assertEquals(200, deleteUser.getResponse().getStatus());

	}

	@Test
	@WithMockUser(username = "user", roles = { "USER" })
	public void shouldRejectDeletingWhenNotAdmin() throws Exception {
		MvcResult deleteUser = mvc.perform(delete("/api/users/admin/delete/{username}", "test@test.lt"))
				.andExpect(status().isForbidden()).andReturn();
		assertEquals(403, deleteUser.getResponse().getStatus());
	}

	@Test
	@WithMockUser(username = "manager", roles = { "MANAGER" })
	public void testGetOneUser() throws Exception {

		MvcResult getOneUser = mvc.perform(get("/api/users/user", "test@test.lt")).andExpect(status().isOk())
				.andReturn();

		assertEquals(200, getOneUser.getResponse().getStatus());

	}

	@Test
	@WithMockUser(username = "manager", roles = { "MANAGER" })
	public void testUpdateUserData() throws Exception {

		/*
		 * User updateUser = userService.findByUsername("test@test.lt");
		 * updateUser.setName("Testas"); updateUser.setSurname("test");
		 * updateUser.setEmail("test@test.lt");
		 */

		User updatedUser = new User(Role.MANAGER, "Testas", "Test", "test@test.lt", null, "test@test.lt",
				"test@test.lt");

		String jsonRequest = mapper.writeValueAsString(updatedUser);

		/*
		 * MvcResult postUpdated = mvc.perform( put("/api/users/update",
		 * "test@test.lt").content(jsonRequest).contentType(MediaType.APPLICATION_JSON))
		 * .andExpect(status().isOk()).andReturn(); assertEquals(200,
		 * postUpdated.getResponse().getStatus());
		 */

		mvc.perform(MockMvcRequestBuilders.put("/api/users/update", "test@test.lt").content(jsonRequest)
				.contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON)).andExpect(status().isOk())
				.andExpect(MockMvcResultMatchers.jsonPath("$.role").value("MANAGER"))
				.andExpect(MockMvcResultMatchers.jsonPath("$.name").value("Testas"))
				.andExpect(MockMvcResultMatchers.jsonPath("$.surname").value("Test"))
				.andExpect(MockMvcResultMatchers.jsonPath("$.email").value("test@test.lt"))
				.andExpect(MockMvcResultMatchers.jsonPath("$.parentDetails").value(null))
				.andExpect(MockMvcResultMatchers.jsonPath("$.username").value("test@test.lt"))
				.andExpect(MockMvcResultMatchers.jsonPath("$.password").value("test@test.lt"));

	}
}
