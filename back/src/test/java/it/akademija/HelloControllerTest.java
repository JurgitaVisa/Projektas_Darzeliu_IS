package it.akademija;

import static org.junit.Assert.assertEquals;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.*;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import com.jayway.restassured.RestAssured;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = App.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class HelloControllerTest {

	@Value("${local.server.port}")
	int port;
	
	private MockMvc mvc;
	
	@Autowired
    private WebApplicationContext context;

	@Before
	public void setUp() throws Exception {
		RestAssured.port = port;
		 mvc = MockMvcBuilders
		          .webAppContextSetup(context)
		          .apply(springSecurity())
		          .build();

	}

	@WithMockUser("spring")
	@Test
	public void testHelloGetMapping() throws Exception {
	MvcResult result=  mvc.perform(get("/hello"))
	          .andExpect(status().isOk()).andReturn();
	
	assertEquals(200, result.getResponse().getStatus());

	}
	
	@WithMockUser
	@Test
	public void testHello() throws Exception {
	String message = mvc.perform(get("/hello")).andReturn().getResponse().getContentAsString();	
	assertEquals("Hello World!", message);

	}

	

}