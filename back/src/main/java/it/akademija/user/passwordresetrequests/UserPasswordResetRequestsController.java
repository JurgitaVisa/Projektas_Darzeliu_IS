package it.akademija.user.passwordresetrequests;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;

import io.swagger.annotations.ApiOperation;

@Controller
public class UserPasswordResetRequestsController {
	@Autowired
	UserPasswordResetRequestsService userPasswordResetRequestsService;
	
	@PutMapping(path = "/requestPasswordReset/{email}")
	@ApiOperation(value = "Request password reset")
	public ResponseEntity<String> requestPasswordReset(@PathVariable(value = "email") final String email) {
		if(userPasswordResetRequestsService.requestPasswordReset(email)) {
			return new ResponseEntity<String>("Pranesimas administratoriui sekmingai issiustas", HttpStatus.OK);
		}
		else {
			return new ResponseEntity<String>("Ivyko klaida, patikrinkite ivestus duomenis", HttpStatus.BAD_REQUEST);
		}
	}
}
