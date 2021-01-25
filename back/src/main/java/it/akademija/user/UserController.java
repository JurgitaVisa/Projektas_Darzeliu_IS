package it.akademija.user;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@RestController
@Api(value = "user")

public class UserController {
	
	private static final Logger LOG = LoggerFactory.getLogger(UserController.class);


	@Autowired
	private UserService userService;
	
	/* Kaip gauti prisijungusį vartotoją servise (12 pamoka, 29 skaidrė): */

//	@RequestMapping(path = "/loggedUsername", method = RequestMethod.GET)
//	public String getLoggedInUsername() {
//		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//		if (!(authentication instanceof AnonymousAuthenticationToken)) {
//			String currentUserName = authentication.getName();
//			return currentUserName;
//		}
//		return "not logged";
//	}

	/* Sukurs vartotoją ir grąžins atsakymą su HTTP statusu 201 */
	@RequestMapping(path = "/createuser", method = RequestMethod.POST)
	@ResponseStatus(HttpStatus.CREATED)
	@ApiOperation(value = "Create user", notes = "Creates user with data")
	public void createUser(@RequestBody UserDTO userInfo) {
		LOG.info("** Usercontroller: kuriamas naujas vartotojas **");
		userService.createUser(userInfo);
	}

	/* Apdoros užklausas: DELETE /api/users/<vartotojas> */
	@RequestMapping(path = "/api/admin/users/delete/{username}", method = RequestMethod.DELETE)
	@ResponseStatus(HttpStatus.NO_CONTENT)
	@ApiOperation(value = "Delete user", notes = "Deletes user by username")
	public void deleteUser(@PathVariable final String username) {

		userService.deleteUser(username);
	}

	public UserService getUserService() {
		return userService;
	}

	public void setUserService(UserService userService) {
		this.userService = userService;
	}

}
