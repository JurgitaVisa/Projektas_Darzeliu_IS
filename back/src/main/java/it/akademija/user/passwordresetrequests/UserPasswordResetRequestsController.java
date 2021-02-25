package it.akademija.user.passwordresetrequests;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import it.akademija.user.UserController;
import it.akademija.user.UserService;

@Controller
@Api(value = "Password Reset Requests")
@RequestMapping(path = "/passwordresetrequests")
public class UserPasswordResetRequestsController {
	@Autowired
	UserPasswordResetRequestsService userPasswordResetRequestsService;
	@Autowired
	UserService userService;
	
	private static final Logger LOG = LoggerFactory.getLogger(UserPasswordResetRequestsController.class);
	
	@GetMapping(path = "/getAllRequests")
	@ApiOperation(value = "Returns a list of userId that want their password reset.")
	public List<UserPasswordResetRequestsEntity> getAllPasswordResetRequests() {
		return userPasswordResetRequestsService.getAllRequests();
	}
	
	@PutMapping(path = "/request/{username}")
	@ApiOperation(value = "Request password reset")
	public ResponseEntity<String> requestPasswordReset(@PathVariable(value = "username") final String username) {
		LOG.info("** " + this.getClass().getName() + ": Naujas prasymas atstatyti slaptazodi naudotojo: " + username + " **");
		if(userPasswordResetRequestsService.requestPasswordReset(username)) {
			return new ResponseEntity<String>("Pranesimas administratoriui sekmingai issiustas", HttpStatus.OK);
		}
		else {
			return new ResponseEntity<String>("Ivyko klaida, patikrinkite ivestus duomenis", HttpStatus.BAD_REQUEST);
		}
	}
	
	@DeleteMapping(path = "/delete/{username}")
	@ApiOperation(value = "Delete password request by email")
	public ResponseEntity<String> deletePasswordResetRequest(@PathVariable(value = "username") final String username) {
		LOG.info("** " + this.getClass().getName() + ": Isstrinamas naudotojo: " + username + " slaptazodzio atstatymo prasymas **");
		userPasswordResetRequestsService.deletePasswordRequest(username);
		return new ResponseEntity<String>(HttpStatus.OK);
	}
	
}
