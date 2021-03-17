package parentTests;

import org.testng.annotations.Test;

import generalMethods.GeneralMethods;
import parentPages.UpdateParentDetailsPage;

public class UpdateParentDetails extends GeneralMethods {
	
	/**
	 * Test scenario:
	 * Change user details (admin)
	 * 
	 * Preconditions:
	 * User admin@admin.lt is already created. User user123@parent.lt is created during this test.
	 * 
	 * Test steps:
	 * 1. Login as admin
	 * 2. Create new parent for the test
	 * 3. Logout. 
	 * 4. Login as newly created parent
	 * 5. Go to "Mano paskyra" page
	 * 6. Change user details (email, name, surname, address, phone number, personal code)
	 */
	
	@Test (groups = "regression", priority = 1) 
	public void successfullyChangeParentDetails() {
		// create a new user (parent) for this test
		doLoginAsAdmin();
		createNewParent(2);
		doLogout();
		doLogin(createNewUserParentEmail, createNewUserParentEmail);
			  
		// go to "Mano paskyra" page
		clickNavButtonMyAccountParent();
		      
		// change user details
		inputUserDetails();
		UpdateParentDetailsPage updateParentDetails = new UpdateParentDetailsPage(driver);
		updateParentDetails.inputPersonalCode("37505073214");
		updateParentDetails.inputTelephoneNumber("+37060809123");
		updateParentDetails.inputUserAddress("Adreso g. 99");
		clickChangeUserDetails();
	}
	
	 /**
		 * Test scenario:
		 * Change user password
		 * 
		 * Preconditions:
		 * Users admin@admin.lt and user123@parent.lt are already created. User user123@parent.lt is logged in.
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
	public void successfullyChangeParentPassword() {
		changeUserPassword(createNewUserParentEmail);
	}
    
    /**
	 * Test scenario:
	 * Reset user (parent) password.
	 * 
	 * Preconditions:
	 * Users admin@admin.lt and user123@parent.lt are already created.
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
    public void successfullyResetParentPasswordToOriginal () {	  
    resetUserPassword(createNewUserParentEmail);
   	  
   	// delete user after successful user details change
   	doLoginAsAdmin();
   	deleteNewUser();
    }
    
}
