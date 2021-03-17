package smokeTests;

import org.testng.annotations.Test;

import generalMethods.GeneralMethods;
import smokeTestPages.CheckIfAllUsersPagesLoad;

public class CheckIfParentPagesWork extends GeneralMethods{
	
	/**
	 * Test scenario:
	 * Open all parent/ guardian pages to check if everything loads successfully.
	 * 
	 * Preconditions:
	 * User "user@user.lt" is already created. User is not logged in.
	 * 
	 * Test steps:
	 * 1. Login
	 * 2. Check all pages (click and assert page titles)
	 * 3. Logout
	 */
	
  @Test (groups = "smoke")
  public void openAndAssertAllParentPages () {	  
	  doLogin(parentLogins, parentLogins);
	  
	  // check if Mano prasymai page loads
	  CheckIfAllUsersPagesLoad checkPages =  new CheckIfAllUsersPagesLoad(driver);
	  checkPages.assertManoPrasymaiPageTitle();
	  
	  // check Sukurti prasyma page
	  clickNavButtonNewApplication();
	  checkPages.assertSukurtiPrasymaPageLoaded();
	  
	  // check Mano pazymos page
	  clickNavButtonMyDocumentsParent();
	  assertThatMyDocumentsPageLoaded();
	  
	  // check Prasymu statistika page
	  checkPages.clickNavPrasymuStatistikaParent();
	  checkPages.assertPrasymuStatistikaPageTitle();
	  
	  // check Mano paskyra page
	  clickNavButtonMyAccountParent();
	  assertThatMyAccountPageHasLoaded();
	  
	  doLogout();	  
  }
}
