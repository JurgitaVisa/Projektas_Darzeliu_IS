package it.akademija.user;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import it.akademija.role.Role;

@Component
public class FirstUser {

	@Autowired
	UserDAO userDao;

	@Autowired
	UserService userService;

	/**
	 * Add first users (ADMIN, MANAGER, USER) to the User repository for testing
	 * purposes
	 * 
	 * @throws Exception
	 * 
	 */
	@PostConstruct
	public void addFirstUser() throws Exception {

		if (userDao.findByRole(Role.ADMIN).size() == 0) {

			UserDTO firstAdmin = new UserDTO("ADMIN", "admin", "admin", "admin@admin.lt", "admin@admin.lt",
					"admin@admin.lt");

			UserDTO firstUser = new UserDTO("USER", "user", "user", "12345678987", "Address 1", "+37061398876",
					"user@user.lt", "user@user.lt", "user@user.lt");

			UserDTO firstManager = new UserDTO("MANAGER", "manager", "manager", "manager@manager.lt",
					"manager@manager.lt", "manager@manager.lt");

			userService.createUser(firstAdmin);
			userService.createUser(firstUser);
			userService.createUser(firstManager);

		}

	}
}
