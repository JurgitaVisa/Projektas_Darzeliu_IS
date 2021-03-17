package smokeTests;

import org.testng.annotations.Test;

import generalMethods.GeneralMethods;
import smokeTestPages.CheckIfAllUsersPagesLoad;

public class CheckIfAdminPagesWork  extends GeneralMethods {
	
	/**
	 * Test scenario:
	 * Open all admin pages to check if everything loads successfully.
	 * 
	 * Preconditions:
	 * User "admin@admin.lt" is already created. User is not logged in.
	 * 
	 * Test steps:
	 * 1. Login
	 * 2. Check all pages (click and assert page titles)
	 * 3. Logout
	 */
	
  @Test (groups = "smoke")
  public void openAndAssertAllAdminPages () {
	  doLoginAsAdmin();
	  
	  // check Naudotojai page
	  verifyIfAdminIsLoggedIn();
	  CheckIfAllUsersPagesLoad checkPages =  new CheckIfAllUsersPagesLoad(driver);
	  checkPages.assertNaudotojaiPageTitle();
	  
	  // check Prasymu statistika page
	  checkPages.clickNavPrasymuStatistikaAdmin();
	  checkPages.assertPrasymuStatistikaPageTitle();
	  
	  // check Prasymu redagavimas page
	  checkPages.clickNavPrasymuRedagavimas();
	  checkPages.assertPrasymuRedagavimasPageTitle();
	  
	  // check Ivykiu zurnalas page
	  checkPages.clickNavIvykiuZurnalas();
	  checkPages.assertIvykiuZurnalasPageTitle();
	  
	  // check Mano paskyra page
	  clickNavButtonAdminMyAccount();
	  assertThatMyAccountPageHasLoaded();
	  
	  doLogout();
  }
}
