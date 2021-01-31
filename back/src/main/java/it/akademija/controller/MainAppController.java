package it.akademija.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.ApiOperation;
import it.akademija.user.User;
import it.akademija.user.UserDAO;

@RestController
public class MainAppController {

	@Autowired
	private UserDAO userDao;

	@GetMapping("/api/loggedUserName")
	@ApiOperation(value = "Get username of logged in user")
	public String getLoggedInUserName() {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		if (!(authentication instanceof AnonymousAuthenticationToken)) {
			String currentUserName = authentication.getName();

			return currentUserName;
		}

		return "not logged";
	}

	@GetMapping("/api/loggedUser")
	@ApiOperation(value = "Get name and surname of logged in user")
	public String getLoggedInUser() {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		if (!(authentication instanceof AnonymousAuthenticationToken)) {
			String currentUserName = authentication.getName();
			User currentUser = userDao.findByUsername(currentUserName);

			return currentUser.getName() + " " + currentUser.getSurname();
		}

		return "not logged";
	}

	@GetMapping("/api/loggedUserRole")
	@ApiOperation(value = "Get role of logged in user")
	public String getLoggedInUserRole() {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		if (!(authentication instanceof AnonymousAuthenticationToken)) {
			String currentUserName = authentication.getName();
			String currentUserRole = userDao.findByUsername(currentUserName).getRole().name();

			return currentUserRole;
		}

		return "not logged";
	}

}
