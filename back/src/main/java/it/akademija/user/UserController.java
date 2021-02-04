package it.akademija.user;

import java.util.List;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
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
	 * @throws Exception
	 */
	@Secured({ "ROLE_ADMIN" })
	@PostMapping(path = "/admin/createuser")
	@ApiOperation(value = "Create user", notes = "Creates user with data")
	public ResponseEntity<String> createUser(@Valid @RequestBody UserDTO userInfo, BindingResult result)
			throws Exception {

		LOG.info("** Usercontroller: kuriamas naujas naudotojas **");

		if (userService.findByUsername(userInfo.getUsername()) == null && (userInfo.getRole().equals("USER")
				&& userService.findByPersonalCode(userInfo.getPersonalCode()) == null)) {

			userService.createUser(userInfo);
			return new ResponseEntity<String>("Naudotojas sukurtas sėkmingai!", HttpStatus.CREATED);
		}

		return new ResponseEntity<String>("Toks naudotojas jau egzistuoja!", HttpStatus.BAD_REQUEST);
	}

	/**
	 * 
	 * Deletes user with specified username. Method only accessible to ADMIN users
	 * 
	 * @param username
	 */
	@Secured({ "ROLE_ADMIN" })
	@DeleteMapping(path = "/admin/delete/{username}")
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
	@ApiOperation(value = "Show all users", notes = "Showing all users")
	public List<UserInfo> getAllUsers() {

		return userService.getAllUsers();
	}

	@Secured({ "ROLE_ADMIN", "ROLE_MANAGER", "ROLE_USER" })
	@GetMapping(path = "/{username}")
	@ApiOperation(value = "Get details for user with specified username")
	public UserInfo getUserDetails(@ApiParam(value = "Username", required = true) @PathVariable final String username) {
		if (userService.findByUsername(username) != null) {

			LOG.info("** Usercontroller: ieškomas naudotojas vardu [{}] **", username);

			return userService.getUserDetails(username);

		}
		return new UserInfo();
	}

	/**
	 * 
	 * Restores password to initial value for the user with specified username.
	 * Method only accessible to ADMIN users
	 * 
	 * @param username
	 */
	@Secured({ "ROLE_ADMIN" })
	@PutMapping(path = "/admin/password/{username}")
	@ApiOperation(value = "Restore user password", notes = "Restore user password to initial value")
	public ResponseEntity<String> restorePassword(
			@ApiParam(value = "Username", required = true) @PathVariable final String username) {

		if (userService.findByUsername(username) != null) {
			userService.restorePassword(username);
			LOG.info("** Usercontroller: keiciamas slaptazodis naudotojui vardu [{}] **", username);
			return new ResponseEntity<String>("Slaptažodis atstatytas sėkmingai", HttpStatus.OK);
		}

		return new ResponseEntity<String>("Naudotojas tokiu vardu nerastas", HttpStatus.NOT_FOUND);
	}

	@Secured({ "ROLE_ADMIN", "ROLE_MANAGER", "ROLE_USER" })
	@PutMapping(path = "/update/{username}")
	@ApiOperation(value = "Update user datails")
	public ResponseEntity<String> updateUserData(@PathVariable final String username, @RequestBody UserDTO userData) {

		if (userService.findByUsername(username) != null) {
			userService.updateUserData(userData, username);
			LOG.info("** Usercontroller: keiciami duomenys naudotojui vardu [{}] **", username);
			return new ResponseEntity<String>("Duomenys pakeisti sėkmingai", HttpStatus.OK);
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
