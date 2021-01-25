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
	 * Add first user to the User repository
	 *  
	 */
	@PostConstruct
	public void addFirstUser() {
		if(userDao.findAll().size()==0) {
			UserDTO firstUser= new UserDTO("admin", "admin", "admin", "laikinas", "ADMIN");
		userService.createUser(firstUser);
		}
	}
}
