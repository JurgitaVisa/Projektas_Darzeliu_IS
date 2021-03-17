package specialistTests;

import org.testng.annotations.Test;

import generalMethods.GeneralMethods;

public class UpdateSpecialistDetails extends GeneralMethods {
	
	/**
	 * Test scenario:
	 * Change user details (kindergarten specialist)
	 * 
	 * Preconditions:
	 * User admin@admin.lt is already created. User manager123@manager.lt is created during this test.
	 * 
	 * Test steps:
	 * 1. Login as admin
	 * 2. Create new kindergarten specialist for the test
	 * 3. Logout. 
	 * 4. Login as newly created user
	 * 5. Go to "Mano paskyra" page
	 * 6. Change user details (email, name, surname)
	 */
	
	@Test (groups = "regression", priority = 1) 
	public void successfullyChangeSpecialistDetails() {
		// create a new user (kindergarten specialist) for this test
		createNewKindergartenSpecialist(1);
		doLogout();
		doLogin(createNewUserSpecialistEmail, createNewUserSpecialistEmail);
			  
		// go to "Mano paskyra" page
		clickNavButtonSpecialistMyAccount();
		      
		// change user details
		inputUserDetails();
		clickChangeUserDetails();
	}
	
	/**
	 * Test scenario:
	 * Change user password
	 * 
	 * Preconditions:
	 * Users admin@admin.lt and user123@parent.lt are already created. User manager123@manager.lt is logged in.
	 * New password must have at least 8 symbols, have one uppercase and one lowercase letters and at least one number.
	 * 
	 * Test steps:
	 * 1. Press "Keisti" button
	 * 2. Enter old password
	 * 3. Enter new password
	 * 4. Enter new password again
	 * 5. Click "Issaugoti"
	 * 6. Logout
	 * 7. Login with new password
	 * 8. Logout
	 */
	
    @Test (groups = "regression", priority = 2) 
	public void successfullyChangeSpecialistPassword() {
		changeUserPassword(createNewUserSpecialistEmail);
	}
    
    /**
  	 * Test scenario:
  	 * Reset user (parent) password.
  	 * 
  	 * Preconditions:
  	 * Users admin@admin.lt and manager123@manager.lt are already created.
  	 * If a user asks to reset his password, button "Atkurti" (admin page "Naudotojai") becomes grey.
  	 * 
  	 * Test steps:
  	 * 1. Go to login page
  	 * 2. Click button "Pamirsau slaptazodi"
  	 * 3. Enter user email (username)
  	 * 4. Login as admin
  	 * 5. Click "Atkurti"
  	 * 6. Logout
  	 * 7. Login as the test user, using the reset password
  	 * 8. Logout
  	 */
    
    @Test (groups = "regression", priority = 3) 
    public void successfullyResetSpecialistPasswordToOriginal () {	  
	    resetUserPassword(createNewUserSpecialistEmail);	
	    
	   	// delete user after successful user details change
	   	doLoginAsAdmin();
	   	deleteNewUser();
    }
    
}
