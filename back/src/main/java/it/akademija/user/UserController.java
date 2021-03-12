package it.akademija.user;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import it.akademija.user.gdprservice.JsonExporter;

@RestController
@Api(value = "user")
@RequestMapping(path = "/api/users")
public class UserController {

	private static final Logger LOG = LoggerFactory.getLogger(UserController.class);

	@Autowired
	private UserService userService;

	@Autowired
	private JsonExporter jsonExporter;

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
	public ResponseEntity<String> createUser(@Valid @RequestBody UserDTO userInfo) {

		LOG.info("** Usercontroller: kuriamas naujas naudotojas **");

		if (userService.findByUsername(userInfo.getUsername()) != null) {
			return new ResponseEntity<String>("Toks naudotojas jau egzistuoja!", HttpStatus.BAD_REQUEST);
		} else {

			userService.createUser(userInfo);
			return new ResponseEntity<String>("Naudotojas sukurtas sėkmingai!", HttpStatus.CREATED);
		}
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
			return new ResponseEntity<String>("Naudotojas ištrintas sėkmingai", HttpStatus.OK);
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
	public Page<UserInfo> getAllUsers(@RequestParam("page") int page, @RequestParam("size") int size) {

		Sort.Order order = new Sort.Order(Sort.Direction.DESC, "userId");

		Pageable pageable = PageRequest.of(page, size, Sort.by(order));

		return userService.getAllUsers(pageable);
	}

	/**
	 * Get detail for logged in user
	 * 
	 * @return user details
	 */
	@Secured({ "ROLE_ADMIN", "ROLE_MANAGER", "ROLE_USER" })
	@GetMapping(path = "/user")
	@ApiOperation(value = "Get details for logged in user")
	public UserInfo getOneUser() {

		String username = SecurityContextHolder.getContext().getAuthentication().getName();

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
			return new ResponseEntity<String>("Slaptažodis atkurtas sėkmingai", HttpStatus.OK);
		}

		return new ResponseEntity<String>("Naudotojas tokiu vardu nerastas", HttpStatus.NOT_FOUND);
	}

	/**
	 * 
	 * Updata user data
	 * 
	 * @param userData
	 * @return message
	 */
	@Secured({ "ROLE_ADMIN", "ROLE_MANAGER", "ROLE_USER" })
	@PutMapping(path = "/update")
	@ApiOperation(value = "Update logged in user details")
	public ResponseEntity<String> updateUserData(@RequestBody UserDTO userData) {

		String currentUserName = SecurityContextHolder.getContext().getAuthentication().getName();
		userService.updateUserData(userData, currentUserName);
		LOG.info("** Usercontroller: keiciami duomenys naudotojui vardu [{}] **", currentUserName);

		return new ResponseEntity<String>("Duomenys pakeisti sėkmingai", HttpStatus.OK);

	}

	/**
	 * Change user password for logged in user
	 * 
	 * @param oldPassword
	 * @param newPassword
	 * @return message
	 */
	@Secured({ "ROLE_ADMIN", "ROLE_MANAGER", "ROLE_USER" })
	@PutMapping(path = "/updatepassword/{oldPassword}/{newPassword}")
	@ApiOperation(value = "Update logged in user password")
	public ResponseEntity<String> updateUserPassword(@PathVariable(value = "oldPassword") final String oldPassword,
			@PathVariable(value = "newPassword") final String newPassword) {
		String currentUsername = SecurityContextHolder.getContext().getAuthentication().getName();
		if (userService.changePassword(currentUsername, oldPassword, newPassword)) {
			return new ResponseEntity<String>("Slaptažodis pakeistas sėkmingai", HttpStatus.OK);
		} else {
			return new ResponseEntity<String>("Neteisingas senas slaptažodis", HttpStatus.BAD_REQUEST);
		}
	}
	
	/**
	 * Get GDPR user data zip archive
	 *  	
	 * @param response
	 * @throws IOException
	 */
	@Secured({ "ROLE_USER" })
	@GetMapping(path = "/user/zip")
	@ApiOperation(value = "Get GDPR user data zip archive")
	public void zipUserInformation(HttpServletResponse response) throws IOException {

		String currentUsername = SecurityContextHolder.getContext().getAuthentication().getName();

		User user = userService.findByUsername(currentUsername);

		String userJsonString = jsonExporter.export(user);

		byte[] jsonBytes = userJsonString.getBytes("UTF8");

		response.setContentType("application/zip");
		response.setStatus(HttpServletResponse.SC_OK);
		response.setHeader("Content-Disposition", "attachment; filename=naudotojas.zip");

		ZipOutputStream zipOutputStream = new ZipOutputStream(response.getOutputStream());

		InputStream inputStream = new ByteArrayInputStream(jsonBytes);

		zipOutputStream.putNextEntry(new ZipEntry("naudotojas.json"));

		IOUtils.copy(inputStream, zipOutputStream);

		inputStream.close();

		zipOutputStream.closeEntry();

		zipOutputStream.close();
	}
	
	/**
	 * "Forget me" functionality which deletes all user related entries from database
	 * 
	 */
	@Secured({"ROLE_USER"})
	@DeleteMapping(path="/user/deletemydata")
	@ApiOperation(value="Forget me - delete all user data")
	public void deleteMyUserData() {
		
		userService.deleteMyUserData();
	}
	

	public UserService getUserService() {
		return userService;
	}

	public void setUserService(UserService userService) {
		this.userService = userService;
	}

	public JsonExporter getJsonExporter() {
		return jsonExporter;
	}

	public void setJsonExporter(JsonExporter jsonExporter) {
		this.jsonExporter = jsonExporter;
	}

}
