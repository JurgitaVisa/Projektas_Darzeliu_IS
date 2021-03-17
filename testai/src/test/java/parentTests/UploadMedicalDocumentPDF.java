package parentTests;
import java.util.concurrent.TimeUnit;

import org.testng.annotations.Test;

import generalMethods.GeneralMethods;
import parentPages.UploadMedicalDocumentPDFPage;

public class UploadMedicalDocumentPDF extends GeneralMethods {
	
	/**
	 * Test scenario:
	 * Upload medical document (pdf) to parent account
	 * 
	 * Preconditions:
	 * admin@admin.lt is already created. New user user123@parent.lt is created during the test
	 * 
	 * Test steps:
	 * 1. Login as admin
	 * 2. Create new parent 
	 * 3. Logout
	 * 4. Login as the newly created parent
	 * 5. Go to "Mano pazymos" page
	 * 6. Upload pdf
	 * 7. Download it
	 * 8. Delete it
	 * 9. Logout
	 * 10. Login as admin
	 * 11. Delete the test user
	 */
  
  @Test  (groups = "regression")
  public void successfullyUploadAndDeletePDF() {
	  // create test user (parent)
	  doLoginAsAdmin();
	  createNewParent(2);
	  doLogout();
	  doLogin(createNewUserParentEmail, createNewUserParentEmail);
	  
	  // go to "Mano pazymos" page
	  clickNavButtonMyDocumentsParent();
	  
	  // assert page
	  assertThatMyDocumentsPageLoaded();
	  
	  // upload document
	  uploadPDF();
	  
	  // download document
	  UploadMedicalDocumentPDFPage uploadDocument = new UploadMedicalDocumentPDFPage(driver);
	  uploadDocument.clickDownloadDocumentButton();
	  driver.manage().timeouts().setScriptTimeout(5, TimeUnit.SECONDS);
	  
	  // delete document
	  deletePDF();
	  doLogout();
	  
	  // delete the user created for this test
	  doLoginAsAdmin();
	  deleteNewUser();
  }
}
