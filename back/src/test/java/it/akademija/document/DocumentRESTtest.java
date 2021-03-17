package it.akademija.document;

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
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jayway.restassured.RestAssured;

import it.akademija.App;
import it.akademija.user.UserService;

@TestInstance(Lifecycle.PER_CLASS)
@SpringBootTest(classes = { App.class,
		DocumentController.class }, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestMethodOrder(OrderAnnotation.class)
@AutoConfigureMockMvc
public class DocumentRESTtest {

	@Value("${local.server.port}")
	int port;

	@Autowired
	private MockMvc mvc;

	@Autowired
	private ObjectMapper mapper;

	@Autowired
	private WebApplicationContext context;

	@Autowired
	private DocumentService documentService;

	@Autowired
	private UserService userService;

	@BeforeAll
	public void setup() throws Exception {
		RestAssured.port = port;
		mvc = MockMvcBuilders.webAppContextSetup(context).apply(springSecurity()).build();

	}

	@Test
	@Order(1)
	@WithMockUser(username = "user", roles = { "USER" })
	public void testGetDocument() throws Exception {

		MvcResult getDocuments = mvc.perform(get("/documents")).andExpect(status().isNotFound()).andReturn();

		assertEquals(404, getDocuments.getResponse().getStatus());

	}

}
