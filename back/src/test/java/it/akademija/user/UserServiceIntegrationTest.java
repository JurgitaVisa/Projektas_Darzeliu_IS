package it.akademija.user;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import it.akademija.journal.JournalEntryDAO;
import it.akademija.journal.JournalService;
import it.akademija.security.DatabaseUserDetailPasswordService;
import it.akademija.user.gdprservice.JsonEcporterImpl;

@SpringBootTest
public class UserServiceIntegrationTest {

	@Autowired
	private UserService service;

	@Autowired
	private JsonEcporterImpl jsonService;

	@Autowired
	private JournalService journalService;

	@Autowired
	private JournalEntryDAO journalDAO;

	@Autowired
	private DatabaseUserDetailPasswordService pswService;

	@Test
	@Order(1)
	public void testGetAllUsers() {

		PageRequest page = PageRequest.of(1, 10);
		Page<UserInfo> users = service.getAllUsers(page);
		assertTrue(users.getSize() != 0);

	}

	@Test
	@Order(2)
	public void testCreateDeleteUser() {

		UserDTO newUser = new UserDTO("MANAGER", "stest", "stest", "stest@test.lt", "stest@test.lt", "stest@test.lt");
		service.createUser(newUser);
		assertEquals("stest", service.findByUsername("stest@test.lt").getName());

		service.changePassword("stest@test.lt", "stest@test.lt", "saltpeppeR45!");
		assertFalse(service.findByUsername("stest@test.lt").getPassword().equals("stest@test.lt"));
		service.restorePassword("stest@test.lt");

		assertThrows(UsernameNotFoundException.class, () -> {
			service.loadUserByUsername("p@1.op");
		});

		UserDetails details = new org.springframework.security.core.userdetails.User(newUser.getUsername(),
				newUser.getPassword(),
				AuthorityUtils.createAuthorityList(new String[] { "ROLE_" + newUser.getRole().toString() }));

		assertEquals(details, service.loadUserByUsername("stest@test.lt"));

		pswService.updatePassword(details, "akops145!");
		assertFalse(details.getPassword().equals("akops145!"));

		service.loadUserByUsername("stest@test.lt");

		assertEquals("stest", service.getUserDetails("stest@test.lt").getName());

		service.deleteUser("stest@test.lt");
		assertNull(service.findByUsername("stest@test.lt"));

	}

	@Test
	@Order(3)
	public void testCreateJSON() {

		UserDTO newUser = new UserDTO("USER", "stest", "stest", "12345898987", "Address 1", "+37061398876",
				"stest@test.lt", "stest@test.lt", "stest@test.lt");

		service.createUser(newUser);

		assertEquals(0, service.getUserApplications("stest@test.lt").size());

		assertEquals("12345898987", service.getUserDetails("stest@test.lt").getPersonalCode());

		assertFalse(jsonService.export(service.findByUsername("stest@test.lt")).isEmpty());

		service.deleteUser("stest@test.lt");
		assertNull(service.findByUsername("stest@test.lt"));

	}

	@Test
	@Order(4)
	public void testJournal() {

		PageRequest page = PageRequest.of(1, 10);

		assertTrue(journalService.getAllJournalEntries(page).getSize() != 0);

		UserDTO newUser = new UserDTO("USER", "stest", "stest", "12345898987", "Address 1", "+37061398876",
				"stest@test.lt", "stest@test.lt", "stest@test.lt");

		service.createUser(newUser);

		journalService.depersonalizeUserLogs("stest");

		PageRequest page2 = PageRequest.of(1, 10);
		assertTrue(journalDAO.getAllJournalEntries(page2).getSize() != 0);

		service.deleteUser("stest@test.lt");
		assertNull(service.findByUsername("stest@test.lt"));

	}

	@Test
	@Order(5)
	public void compareUsers() {
		UserDTO newUser = new UserDTO("USER", "stest", "stest", "12345898987", "Address 1", "+37061398876",
				"stest@test.lt", "stest@test.lt", "stest@test.lt");
		service.createUser(newUser);

		UserDTO newUser2 = new UserDTO("USER", "stest", "stest", "12445898987", "Address 1", "+37061398876",
				"stest@stest.lt", "stest@stest.lt", "stest@stest.lt");
		service.createUser(newUser2);

		assertFalse(newUser.equals(newUser2));

		service.deleteUser("stest@test.lt");
		assertNull(service.findByUsername("stest@test.lt"));

		service.deleteUser("stest@stest.lt");
		assertNull(service.findByUsername("stest@stest.lt"));
	}

}