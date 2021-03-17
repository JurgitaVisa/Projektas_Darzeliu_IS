package smokeTests;

import org.testng.annotations.Test;

import generalMethods.GeneralMethods;
import smokeTestPages.CheckIfAllUsersPagesLoad;

public class CheckIfKindergartenSpecialistPagesWork extends GeneralMethods {
	
	/**
	 * Test scenario:
	 * Open all kindergarten specialist pages to check if everything loads successfully.
	 * 
	 * Preconditions:
	 * User "manager@manager.lt" is already created. User is not logged in.
	 * 
	 * Test steps:
	 * 1. Login
	 * 2. Check all pages (click and assert page titles)
	 * 3. Logout
	 */
	
	
  @Test (groups = "smoke")
  public void openAndAssertAllSpecialistPages() {
	  doLogin(specialistLogins, specialistLogins);
	  
	  // check if Darzeliu sarasas page loads
	  verifyIfSpecialistIsLoggedIn();
	  CheckIfAllUsersPagesLoad checkPages =  new CheckIfAllUsersPagesLoad(driver);
	  checkPages.assertDarzeliuSarasasPageTitle();
	  
	  // check if Prasymu eile loads
	  clickNavButtonApplicationQueue();
	  checkPages.assertPrasymuEilePageTitle();
	  
	  // check if Prasymu statistika page loads
	  checkPages.clickNavPrasymuStatistikaSpecialist();
	  checkPages.assertPrasymuStatistikaPageTitle();
	  
	  // check if Mano paskyra page loads
	  clickNavButtonSpecialistMyAccount();
	  assertThatMyAccountPageHasLoaded();  
	  
	  doLogout();
  }
}
