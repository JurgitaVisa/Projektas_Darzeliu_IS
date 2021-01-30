package it.akademija.user;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

@RestController
@Api(value = "user")
@RequestMapping(path = "/api/users")
public class UserController {

	private static final Logger LOG = LoggerFactory.getLogger(UserController.class);

	@Autowired
	private UserService userService;

	/**
	 * 
	 * Create new user. Method only accessible to ADMIN users
	 * 
	 * @param userInfo
	 */
	@Secured({ "ROLE_ADMIN" })
	@PostMapping(path = "/admin/createuser")
	@ResponseStatus(HttpStatus.CREATED)
	@ApiOperation(value = "Create user", notes = "Creates user with data")
	public void createUser(@RequestBody UserDTO userInfo) {

		LOG.info("** Usercontroller: kuriamas naujas naudotojas **");

		userService.createUser(userInfo);

	}

	/**
	 * 
	 * Deletes user with specified username. Method only accessible to ADMIN users
	 * 
	 * @param username
	 */
	@Secured({ "ROLE_ADMIN" })
	@DeleteMapping(path = "/admin/delete/{username}")
	@ResponseStatus(HttpStatus.OK)
	@ApiOperation(value = "Delete user", notes = "Deletes user by username")
	public ResponseEntity<String> deleteUser(
			@ApiParam(value = "Username", required = true) @PathVariable final String username) {
		if (userService.findByUsername(username) != null) {
			userService.deleteUser(username);
			LOG.info("** Usercontroller: trinamas naudotojas vardu [{}] **", username);
			return new ResponseEntity<String>("Naudotojas panaikintas sėkmingai", HttpStatus.OK);
		}

		return new ResponseEntity<String>("Naudotojas tokiu vardu nerastas", HttpStatus.NOT_FOUND);
	}

	/**
	 * Returns a list of users. Method only accessible to ADMIN users
	 * 
	 * @return list of users
	 */
	@Secured({ "ROLE_ADMIN" })
	@GetMapping(path = "/admin/allusers")
	@ResponseStatus(HttpStatus.OK)
	@ApiOperation(value = "Show all users", notes = "Showing all users")
	public @ResponseBody Iterable<User> getAll() {

		return userService.getAll();
	}

	/**
	 * 
	 * Restores password to initial value for the user with specified username.
	 * Method only accessible to ADMIN users
	 * 
	 * @param username
	 */
	@Secured({ "ROLE_ADMIN" })
	@PutMapping(path = "/admin/delete/{username}")
	@ResponseStatus(HttpStatus.OK)
	@ApiOperation(value = "Restore user password", notes = "Restore user password to initial value")
	public ResponseEntity<String> restorePassword(
			@ApiParam(value = "Username", required = true) @PathVariable final String username) {
		
		if (userService.findByUsername(username) != null) {
			userService.restorePassword(username);
			LOG.info("** Usercontroller: keiciamas slaptazodis vartotojui vardu [{}] **", username);
			return new ResponseEntity<String>("Slaptažodis atstatytas sėkmingai", HttpStatus.OK);
		}

		return new ResponseEntity<String>("Naudotojas tokiu vardu nerastas", HttpStatus.NOT_FOUND);
	}

	public UserService getUserService() {
		return userService;
	}

	public void setUserService(UserService userService) {
		this.userService = userService;
	}

}
