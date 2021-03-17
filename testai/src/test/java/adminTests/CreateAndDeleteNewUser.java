package adminTests;

import org.testng.annotations.Test;
import generalMethods.GeneralMethods;

public class CreateAndDeleteNewUser extends GeneralMethods {
	
	/**
	 * Test scenario:
	 * Create new admin and delete it afterwards.
	 * 
	 * Test steps:
	 * 1. Login as admin
	 * 2. Create new admin user
	 * 3. Delete the user
	 * 4. Logout
	 */
	
  @Test (groups = {"smoke", "regression"}) 
  public void successfullyCreateAndDeleteNewAdmin () {
	  createNewAdmin(0);
	  deleteNewUser();
  }
  
  /**
	 * Test scenario:
	 * Create new kindergarten specialist and delete it afterwards.
	 * 
	 * Test steps:
	 * 1. Login as admin
	 * 2. Create new kindergarten specialist
	 * 3. Delete the user
	 * 4. Logout
	 */
  
  @Test (groups = {"smoke", "regression"})
  public void successfullyCreateAndDeleteNewKindergartenSpecialist () {
	  createNewKindergartenSpecialist(1); 
	  deleteNewUser();
  }
  
  /**
	 * Test scenario:
	 * Create new parent/ guardian and delete it afterwards.
	 * 
	 * Test steps:
	 * 1. Login as admin
	 * 2. Create new parent
	 * 3. Delete the user
	 * 4. Logout
	 */
  
  @Test (groups = {"smoke", "regression"})
  public void successfullyCreateAndDeleteNewParent () {
	  doLoginAsAdmin();
	  createNewParent(2); 
	  deleteNewUser();
  }
  
}
