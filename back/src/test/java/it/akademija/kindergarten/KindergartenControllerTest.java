package it.akademija.kindergarten;

import static org.junit.Assert.assertEquals;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.*;

import org.junit.jupiter.api.Test;
import org.junit.Before;
import org.junit.runner.RunWith;
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
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jayway.restassured.RestAssured;

import it.akademija.App;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = { App.class,
		KindergartenController.class }, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
class KindergartenControllerTest {

	@Value("${local.server.port}")
	int port;

	@Autowired
	private MockMvc mvc;

	@Autowired
	ObjectMapper mapper;

	@Autowired
	private WebApplicationContext context;

	@Before
	public void setUp() throws Exception {
		RestAssured.port = port;
		mvc = MockMvcBuilders.webAppContextSetup(context).apply(springSecurity()).build();

	}

	@WithMockUser(username = "manager", roles = { "MANAGER" })
	@Test
	public void testKindergartenControllerGetMethodsAccessibleForManager() throws Exception {
		MvcResult getAll = mvc.perform(get("/api/darzeliai"))
				.andExpect(status().isOk()).andReturn();
		assertEquals(200, getAll.getResponse().getStatus());

		MvcResult getPage = mvc.perform(get("/api/darzeliai/manager/page")
				.param("page", "1").param("size", "10"))
				.andExpect(status().isOk()).andReturn();
		assertEquals(200, getPage.getResponse().getStatus());

	}

	@WithMockUser(username = "manager", roles = { "MANAGER" })
	@Test
	public void testPostNewKindergartenMethod() throws Exception {
		
		Kindergarten newKindergarten= new Kindergarten("111111111", "Test", "Test", "Test",  10,10);
		
		String jsonRequest = mapper.writeValueAsString(newKindergarten);
		
		MvcResult postNew = mvc.perform(post("/api/darzeliai/manager/createKindergarten")
				.content(jsonRequest)
				.contentType(MediaType.APPLICATION_JSON))				
				.andExpect(status().isOk()).andReturn();
		assertEquals(200, postNew.getResponse().getStatus());

	}

	@WithMockUser(username = "manager", roles = { "MANAGER" })
	@Test
	public void testDeleteKindergartenMethod() throws Exception {

		MvcResult deleteOne = mvc.perform(delete("/api/darzeliai/manager/delete/{id}", "111111111"))
				.andExpect(status().isOk()).andReturn();
		assertEquals(200, deleteOne.getResponse().getStatus());

	}

}
