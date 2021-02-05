package it.akademija.user;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import it.akademija.role.Role;

@Component
public class FirstUser {

	private static final Logger LOG = LoggerFactory.getLogger(FirstUser.class);

	@Autowired
	UserDAO userDao;

	@Autowired
	UserService userService;

	/**
	 * Add first ADMIN user to the User repository if no ADMIN users are in the
	 * database
	 * 
	 * @throws Exception
	 * 
	 */
	@PostConstruct
	public void addFirstUser() throws Exception {

		// LocalDate date = LocalDate.of(1998, 1, 8);

		// Nepamirsti istrinti firstUser ir firstManager!

		if (userDao.findByRole(Role.ADMIN).size() == 0) {

			UserDTO firstAdmin = new UserDTO("ADMIN", "admin", "admin", "admin@admin.lt", "admin@admin.lt",
					"admin@admin.lt");

			UserDTO firstUser = new UserDTO("USER", "user", "user", "38209230998", "Taikog g. 8", "61399876",
					"user@user.lt", "user@user.lt", "user@user.lt");

			UserDTO firstManager = new UserDTO("MANAGER", "manager", "manager", "manager@manager.lt",
					"manager@manager.lt", "manager@manager.lt");

			userService.createUser(firstAdmin);
			userService.createUser(firstUser);
			userService.createUser(firstManager);

			LOG.warn("Pirmas ADMIN naudotojas sukurtas");

		}

	}
}
