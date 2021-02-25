package it.akademija.user.passwordresetrequests;

import java.util.List;

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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import it.akademija.user.UserService;

@RestController
@Api(value = "Password Reset Requests")
@RequestMapping(path = "/passwordresetrequests")
public class UserPasswordResetRequestsController {
	@Autowired
	UserPasswordResetRequestsService userPasswordResetRequestsService;
	@Autowired
	UserService userService;
	
	private static final Logger LOG = LoggerFactory.getLogger(UserPasswordResetRequestsController.class);
	
	@Secured({ "ROLE_ADMIN" })
	@GetMapping(path = "/getAllRequests")
	@ApiOperation(value = "Returns a list of userId that want their password reset.")
	public List<UserPasswordResetRequestsEntity> getAllPasswordResetRequests() {
		return userPasswordResetRequestsService.getAllRequests();
	}
	
	@PostMapping(path = "/request/{username}")
	@ApiOperation(value = "Request password reset")
	public ResponseEntity<String> requestPasswordReset(@PathVariable(value = "username") final String username) {
		LOG.info("** " + this.getClass().getName() + ": Naujas prasymas atstatyti slaptazodi naudotojo: " + username + " **");
		userPasswordResetRequestsService.requestPasswordReset(username);
		return new ResponseEntity<String>("Pranešimas administratoriui sėkmingai išsiųstas", HttpStatus.OK);
	}
	
	@Secured({ "ROLE_ADMIN" })
	@DeleteMapping(path = "/delete/{username}")
	@ApiOperation(value = "Delete password request")
	public ResponseEntity<String> deletePasswordResetRequest(@PathVariable(value = "username") final String username) {
		LOG.info("** " + this.getClass().getName() + ": Isstrinamas naudotojo: " + username + " slaptazodzio atstatymo prasymas **");
		userPasswordResetRequestsService.deletePasswordRequest(username);
		return new ResponseEntity<String>(HttpStatus.OK);
	}
	
}
