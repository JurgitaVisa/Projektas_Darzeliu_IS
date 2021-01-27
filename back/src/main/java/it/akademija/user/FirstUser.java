package it.akademija.user;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

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
	 * Add first ADMIN user to the User repository if no ADMIN users are in the database
	 * 
	 */
	@PostConstruct
	public void addFirstUser() {
		
		if (userDao.findByRole(Role.ADMIN).size() == 0) {			
			UserDTO firstUser = new UserDTO("admin", "admin", "admin", "laikinas", "ADMIN");
			userService.createUser(firstUser);
			LOG.warn("Pirmas ADMIN naudotojas sukurtas");
		}
	}

	

}
