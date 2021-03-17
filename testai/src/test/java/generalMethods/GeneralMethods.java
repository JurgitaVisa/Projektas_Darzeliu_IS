package generalMethods;
import java.io.IOException;
import java.util.List;
import java.util.concurrent.TimeUnit;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import adminPages.CreateAndDeleteNewUserPage;
import basetest.BaseTest;
import pages.ChangeAndResetUserAccountFieldsAndPasswordPage;
import pages.LoginPage;
import parentPages.SubmitNewApplicationPage;
import parentPages.UploadMedicalDocumentPDFPage;
import specialistPages.CreateAndDeleteNewKindergartenPage;
import utilities.FileReaderUtils;

public class GeneralMethods extends BaseTest {
	
	protected static String adminLogins = "admin@admin.lt";
	protected static String specialistLogins = "manager@manager.lt";
	protected static String parentLogins = "user@user.lt";
	protected String createNewUserAdminEmail = "admin123@admin.lt";
	protected String createNewUserSpecialistEmail = "manager123@manager.lt";
	protected String createNewUserParentEmail = "user123@parent.lt";
	private String newUserName = "Jonas";
	private String newUserSurname = "Jonaitis";
	private String newPassword = "Naujas321";
	private String changedUserName = "Pakeistas";
	private String changedUserSurname = "Pakeistas";
	private String changedUserEmail = "pakeistas@email.lt";
	private String expectedErrorMessage= "Neteisingas prisijungimo vardas ir/arba slaptažodis!";
	private String pdfFileLocation = "C:\\Users\\Vardas\\git\\TestNG_testai_INTL\\darzelis\\src\\test\\resources\\Testas.pdf";
	
	// LOGIN/ LOGOUT METHODS
	
	public void doLogin(String username, String password) {
		LoginPage loginPage = new LoginPage(driver);
		waitForLoginToLoad();
		loginPage.enterUsername(username);
		loginPage.enterPassword(password);
		loginPage.clickLoginButton();
	 }
	
	public void doLoginAsAdmin() {
		LoginPage loginPage = new LoginPage(driver);
		waitForLoginToLoad();
		loginPage.enterUsername(adminLogins);
		loginPage.enterPassword(adminLogins);
		loginPage.clickLoginButton();
	 }
	
	public void doLogout() {
		  WebDriverWait wait = new WebDriverWait(driver, 10);
		  WebElement logoutElement = wait.until(
				  ExpectedConditions.elementToBeClickable(By.id("btnLogout")));
		  logoutElement.click();  
	 }
	
	public Boolean verifyIfAdminIsLoggedIn() {
		  WebDriverWait wait = new WebDriverWait(driver, 10);
		  return wait.until(ExpectedConditions.textToBe(By.id("navAdminUserList"), "Naudotojai"));
		}
	
	public Boolean verifyIfSpecialistIsLoggedIn() {
		  WebDriverWait wait = new WebDriverWait(driver, 10);
		  return wait.until(ExpectedConditions.textToBe(By.id("navManagerKindergartenList"), "Darželių sąrašas"));
		}
	
	// CREATE AND DELETE NEW USER
	
	// create new admin
	public void createNewAdmin (int index) {
		doLoginAsAdmin();
		verifyIfAdminIsLoggedIn();
		
		// select user role
		Select dropdownUserRole = new Select(driver.findElement(By.id("selRole")));
		dropdownUserRole.selectByIndex(index);
		
		CreateAndDeleteNewUserPage createNewUserPage = new CreateAndDeleteNewUserPage(driver);
		createNewUserPage.enterEmail(createNewUserAdminEmail);
		createNewUserPage.enterName(newUserName);
		createNewUserPage.enterSurname(newUserSurname);
		
		createNewUserPage.clickCreateButton();
		// check success message
		userIsCreatedMessage();
		createNewUserPage.clickOKButtonUserIsCreated();
	}
	
	public void userNotLoggedInPopUp () {
		CreateAndDeleteNewUserPage newUser = new CreateAndDeleteNewUserPage(driver);
		LoginPage loginPage = new LoginPage(driver);
		
		if (newUser.userNotLoggedInButton.isDisplayed()) {
			newUser.clickOkUserNotLoggedInButton();
			loginPage.clickLoginButton();
		} else {
			loginPage.clickLoginButton();
		}}
	
	// create new kindergarten specialist
	public void createNewKindergartenSpecialist (int index) {
		doLoginAsAdmin();
		verifyIfAdminIsLoggedIn();
			
		// select user role
		Select dropdownUserRole = new Select(driver.findElement(By.id("selRole")));
		dropdownUserRole.selectByIndex(index);
			
		CreateAndDeleteNewUserPage createNewUserPage = new CreateAndDeleteNewUserPage(driver);
		createNewUserPage.enterEmail(createNewUserSpecialistEmail);
		createNewUserPage.enterName(newUserName);
		createNewUserPage.enterSurname(newUserSurname);
			
		createNewUserPage.clickCreateButton();
		// check success message
		userIsCreatedMessage();
		createNewUserPage.clickOKButtonUserIsCreated();
		}
	
	// create new parent/ guardian
	public void createNewParent (int index) {
		CreateAndDeleteNewUserPage createNewUserPage = new CreateAndDeleteNewUserPage(driver);
		verifyIfAdminIsLoggedIn();
		
		// select user role
		Select dropdownUserRole = new Select(driver.findElement(By.id("selRole")));
		dropdownUserRole.selectByIndex(index);
		
		createNewUserPage.enterEmail(createNewUserParentEmail);
		createNewUserPage.enterName(newUserName);
		createNewUserPage.enterSurname(newUserSurname);
		createNewUserPage.enterPersonalCode("12345678911");
		createNewUserPage.enterPhoneNumber("+37061212123");
		createNewUserPage.enterAddress("Adreso g. 8");
		
		createNewUserPage.clickCreateButton();
		
		// check success message
		userIsCreatedMessage();
		createNewUserPage.clickOKButtonUserIsCreated();
	}
	
	public void deleteNewUser () {
		clickDeleteUserButton();
		// agree to delete user (pop up)
		waitToAgreePopUp();
		waitToPressOKPopUp();
		
		// logout after deleting the user
		doLogout();
	}
	
	// CHANGE USER DETAILS (MANO PASKYRA PAGE)
	
	public void inputUserDetails () {	   
		// change kindergarten specialist details	
		ChangeAndResetUserAccountFieldsAndPasswordPage changeAccountDetails = new ChangeAndResetUserAccountFieldsAndPasswordPage(driver);
		assertThatMyAccountPageHasLoaded();
		changeAccountDetails.changeUserName(changedUserName);
		changeAccountDetails.changeUserSurname(changedUserSurname);
		changeAccountDetails.changeUserEmail(changedUserEmail);
	}
	
	public void changeUserPassword (String userLogin) {  
		ChangeAndResetUserAccountFieldsAndPasswordPage changeAccountDetails = new ChangeAndResetUserAccountFieldsAndPasswordPage(driver);
		// click button "Keisti"
		changeAccountDetails.clickChangeUserPasswordButton();
			  
		// enter old and new password
		changeAccountDetails.enterOldPassword(userLogin);
		changeAccountDetails.enterNewPassword(newPassword);
		changeAccountDetails.enterRepeatedNewPassword(newPassword);
			  
		// save the new password
		changeAccountDetails.clickButtonSaveChangedPassword();
			  
		// assert that password was changed
		assertThatUserPasswordWasUpdated();
		changeAccountDetails.clickOkButtonPasswordChanged();
			  
		doLogout();
		      
		// check if user can login with changed password
		waitForLoginToLoad();
		doLogin(userLogin, newPassword);
		      
		// logout
		doLogout();
	}	
	
	public void resetUserPassword (String userLogin) {
		ChangeAndResetUserAccountFieldsAndPasswordPage changeAccountDetails = new ChangeAndResetUserAccountFieldsAndPasswordPage(driver);
		clickUserForgotPasswordButton();
			      
		// enter user email
		enterUserEmail(userLogin);
		changeAccountDetails.clickOkResetPasswordButton();
		clickDoneButtonForgotPassword();
			      
		// login as admin
		doLoginAsAdmin();
			      
		// reset password that needs to be reset (the button "Atkurti" becomes grey when it needs to be reset)
		clickResetPasswordButton();
		changeAccountDetails.clickAgreeToResetUserPasswordButton();
			      
		// assert message that user password was reset
		assertThatPasswordWasReset();
		changeAccountDetails.clickOkButtonPasswordIsReset();
	
		// logout and check if user can login with original password
		doLogout();
		waitForLoginToLoad();
		doLogin(userLogin, userLogin);
		doLogout();  
	}
	
	public void clickChangeUserDetails () {	  
		ChangeAndResetUserAccountFieldsAndPasswordPage changeAccountDetails = new ChangeAndResetUserAccountFieldsAndPasswordPage(driver);
		changeAccountDetails.clickButtonUpdateUserDetails();      
		// assert that information was updated 
		changeAccountDetails.assertThatUserInformationWasUpdated();
		changeAccountDetails.clickOkButtonUserDetailsUpdated(); 
	}
	
	// CREATE AND DELETE NEW KINDERGARTEN
	
	public void successfullyCreateNewKindergarten () {
		  
		  // login as kindergarten specialist
	      doLogin(specialistLogins, specialistLogins);
	      
	      // wait for the page to load and check if the kindergarten specialist is logged in
	      verifyIfSpecialistIsLoggedIn();
	      
	      // input new kindergarten details     
	      CreateAndDeleteNewKindergartenPage createNewKindergarten = new CreateAndDeleteNewKindergartenPage(driver);
	      createNewKindergarten.inputKindergartenID("000000001");
	      createNewKindergarten.inputkindergartenName("123 Testinis");
	      createNewKindergarten.inputkindergartenAddress("Adreso g. 5");
	      Select dropdownUserRole = new Select(driver.findElement(By.id("elderate")));
		  dropdownUserRole.selectByIndex(5);
		  createNewKindergarten.inputcapacityAgeGroup2to3("1");
		  createNewKindergarten.inputcapacityAgeGroup3to6("1");
		  
		  // save and create new kindergarten
		  createNewKindergarten.clickButtonSaveKindergarten();
		  createNewKindergarten.clickOKPopUp();
		  
		  // search for the newly created kindergarten
		  createNewKindergarten.searchForTheNewlyCreatedKindergarten("123 Testinis");
		  
		  // assert that the new kindergarten is found in the searched list
		  createNewKindergarten.newKindergartenSearchResult();
		  
		  // update and save the kindergarten details
		  createNewKindergarten.clickButtonUpdateKindergarten();
		  createNewKindergarten.updateNewKindergartenName("123 Testinis darželis");
		  createNewKindergarten.updateKindergartenNumberCapacity3to6("0");
		  createNewKindergarten.clickSaveUpdatedKindergarten();
	  }
	
	public void deleteNewKindergarten() {
		CreateAndDeleteNewKindergartenPage createNewKindergarten = new CreateAndDeleteNewKindergartenPage(driver);
		createNewKindergarten.clickButtonDeleteKindergarten();
		createNewKindergarten.clickButtonAgreeToDeleteKindergarten();
		createNewKindergarten.assertKindergartenWasDeletedSuccesfully();
		waitToPressOKPopUp();
	}
	
	// REGISTRATION TO KINDERGARTEN METHODS
	
	public void openRegistrationIfNeeded () {
	    // go to Prasymu eile page
	    clickNavButtonApplicationQueue();
	    
	    // check if registration is open
	    if (registrationClosed()) {
	    	driver.findElement(By.id("btnStartRegistration")).click();
	    	doLogout();
		} else {
			doLogout();
		}
	}
	
	public boolean registrationClosed () {
	    try {
	        driver.findElement(By.id("btnStartRegistration")).isDisplayed();
	        return true;
	    } catch (org.openqa.selenium.NoSuchElementException e) {
	        return false;
	    }
	}
	
	public void fillInTheApplication () throws IOException {
		SubmitNewApplicationPage newApplication = new SubmitNewApplicationPage(driver);		
		clickNavButtonNewApplication();
				    
		// add additional parent/ guardian
		newApplication.clickAddAdditionalGuardianButton();
				
		// fill in additional parent/ guardian details into the form
		applicationFormSecondParentDetails();
				
		// fill in child details into the form
		applicationFormChildDetails();
		
		// child priorities and first kindergarten priority
		checkPrioritiesAndChooseAKindergarten();
		
		// submit application
		newApplication.clickButtonSubmitApplication();
		waitToClickSubmitButton();
	}
	
	
	public void applicationFormSecondParentDetails () throws IOException {
		SubmitNewApplicationPage newApplication = new SubmitNewApplicationPage(driver);
		List<String> formData = FileReaderUtils.getTestData("src/test/resources/parentAndChildDetails.txt");
		String secondParentName = formData.get(0);
		String secondParentSurname = formData.get(1);
		String secondParentPersonalCode = formData.get(2);
		String secondParentPhoneNumber = formData.get(3);
		String secondParentEmail = formData.get(4);
		String secondParentAddress = formData.get(5);
		newApplication.inputSecondParentName(secondParentName);
		newApplication.inputSecondParentSurname(secondParentSurname);
		newApplication.inputSecondParentPersonalCode(secondParentPersonalCode);
		newApplication.inputSecondParentPhoneNumber(secondParentPhoneNumber);
		newApplication.inputSecondParentEmail(secondParentEmail);
		newApplication.inputSecondParentAddress(secondParentAddress);
	}
		
	public void applicationFormChildDetails () throws IOException {
		SubmitNewApplicationPage newApplication = new SubmitNewApplicationPage(driver);
		List<String> formData = FileReaderUtils.getTestData("src/test/resources/parentAndChildDetails.txt");
		String childName = formData.get(6);
		String childSurname = formData.get(7);
		String childPersonalCode = formData.get(8);
		String childDateOfBirth = formData.get(9);
		newApplication.inputChildName(childName);
		newApplication.inputChildSurname(childSurname);
		newApplication.inputChildPersonalCode(childPersonalCode);
		newApplication.inputChildDateOfBirth(childDateOfBirth);
	}
	
	public void checkPrioritiesAndChooseAKindergarten () throws IOException {
		SubmitNewApplicationPage newApplication = new SubmitNewApplicationPage(driver); 
		
		// check priorities
		newApplication.clickPriorityOne();
		newApplication.clickPriorityTwo();
		newApplication.clickPriorityThree();
		newApplication.clickPriorityFour();
		newApplication.clickPriorityFive();
		
		// choose a kindergarten from the list
		driver.manage().timeouts().implicitlyWait(30,TimeUnit.SECONDS);
		newApplication.openKindergartenListDropdownPriorityOne();
	}
	
	// UPLOAD USER MEDICAL DOCUMENTS (PDF)
	
	public void uploadPDF() {
		UploadMedicalDocumentPDFPage uploadDocument = new UploadMedicalDocumentPDFPage(driver);
		uploadDocument.clickUploadDocumentButton();
		uploadDocument.buttonInputDocument.sendKeys(pdfFileLocation);
		uploadDocument.clickUploadDocumentButton();
		waitToPressOKPopUp();
	}
	
	public void deletePDF() {
		UploadMedicalDocumentPDFPage uploadDocument = new UploadMedicalDocumentPDFPage(driver);
		uploadDocument.clickDeleteDocumentButton();
		waitToAgreePopUp();
		waitToPressOKPopUp();
	}
	
	
	// WAIT FOR PAGES TO LOAD
	
	public Boolean waitForLoginToLoad() {
		  WebDriverWait wait = new WebDriverWait(driver, 10);
		  	return wait.until(ExpectedConditions.textToBe(By.xpath("//h3"), "Prisijungti"));
	}
	
	public Boolean assertThatMyAccountPageHasLoaded() {
		  WebDriverWait wait = new WebDriverWait(driver, 10);
		  	return wait.until(ExpectedConditions.textToBe(By.xpath("//div[2]//h6"), "Naudotojo prisijungimo informacija"));
	}
	
	// WAIT TO ASSERT MESSAGE
	
	public Boolean applicationSuccessful() {
		  WebDriverWait wait = new WebDriverWait(driver, 10);
		  	return wait.until(ExpectedConditions.textToBe(By.xpath("/html/body/div[2]/div/div[1]"), "Prašymas sukurtas sėkmingai"));
	}
	
	public Boolean userIsCreatedMessage() {
		  WebDriverWait wait = new WebDriverWait(driver, 10);
		  	return wait.until(ExpectedConditions.textToBe(By.xpath("//body/div[2]/div/div[1]"), "Naujas naudotojas buvo sėkmingai sukurtas."));
	}
	
	public Boolean checkErrorMessage() {
		  WebDriverWait wait = new WebDriverWait(driver, 10);
		  	return wait.until(ExpectedConditions.textToBe(By.id("incorrectLoginData"), expectedErrorMessage));
		}
	
	public Boolean assertThatPasswordWasReset() {
		  WebDriverWait wait = new WebDriverWait(driver, 10);
		  	return wait.until(ExpectedConditions.textToBe(By.xpath("//body/div[2]/div/div[1]"), "Slaptažodis atkurtas sėkmingai"));
	}
	
	public Boolean assertThatUserPasswordWasUpdated() {
		  WebDriverWait wait = new WebDriverWait(driver, 10);
		  	return wait.until(ExpectedConditions.textToBe
		  			(By.xpath("//body/div[2]/div/div[1]"), "Naudotojo slaptažodis atnaujintas sėkmingai"));
	}
	
	public Boolean assertThatMyDocumentsPageLoaded () {
		  WebDriverWait wait = new WebDriverWait(driver, 10);
		  	return wait.until(ExpectedConditions.textToBe
		  			(By.xpath("//*/div[1]//h6"), "Mano pažymos"));
	}
	
	// WAIT TO CLICK BUTTONS
	
	public void waitToClickSubmitButton () {
		WebDriverWait wait = new WebDriverWait(driver, 10);
			WebElement clickButton = wait.until(
				ExpectedConditions.elementToBeClickable(By.xpath("//button[@type='submit']")));
			clickButton.click();
	}
	
	public void clickDeleteApplication () {
		WebDriverWait wait = new WebDriverWait(driver, 10);
			WebElement delete = wait.until(
				ExpectedConditions.presenceOfElementLocated(By.id("btnDeleteApplication")));
			delete.click();
	}
	
	public void clickNavButtonAdminMyAccount () {
		WebDriverWait wait = new WebDriverWait(driver, 10);
			WebElement navMyAccountAdmin = wait.until(
				ExpectedConditions.presenceOfElementLocated(By.id("navAdminMyAccount")));
		navMyAccountAdmin.click();
	}
	
	public void clickNavButtonMyDocumentsParent () {
		WebDriverWait wait = new WebDriverWait(driver, 10);
		WebElement navMyDocuments = wait.until(
			ExpectedConditions.presenceOfElementLocated(By.id("navUserDocuments")));
		navMyDocuments.click();
	}
	
	public void clickNavButtonParentApplications () {
		WebDriverWait wait = new WebDriverWait(driver, 10);
			WebElement navMyAccountAdmin = wait.until(
				ExpectedConditions.presenceOfElementLocated(By.id("navUserMyApplications")));
		navMyAccountAdmin.click();
	}
	
	public void clickNavButtonMyAccountParent () {
		  WebDriverWait wait = new WebDriverWait(driver, 10);
			WebElement navMyAccountParent = wait.until(
				ExpectedConditions.presenceOfElementLocated(By.id("navUserMyAccount")));
		  navMyAccountParent.click();
		}
	
	public void waitToAgreePopUp () {
		 WebDriverWait wait = new WebDriverWait(driver, 10);
		  WebElement agreeToDeleteUser = wait.until(
				  ExpectedConditions.presenceOfElementLocated(By.xpath("//*/div[2]/button")));
		agreeToDeleteUser.click();
	}
	
	public void waitToPressOKPopUp() {
		 WebDriverWait wait = new WebDriverWait(driver, 10);
		  WebElement popUpClickOK = wait.until(
				  ExpectedConditions.elementToBeClickable(By.xpath("//button[@class='swal-button swal-button--confirm']")));
		  popUpClickOK.click();
	}
	
	public void clickOkButton() {
		JavascriptExecutor js = (JavascriptExecutor)driver;
		js.executeScript("arguments[0].click();", driver.findElement(By.xpath("//button[@class='swal-button swal-button--confirm']")));
	}
	
	public void clickUserForgotPasswordButton () {
		 WebDriverWait wait = new WebDriverWait(driver, 10);
		  WebElement forgotPassword = wait.until(
				  ExpectedConditions.presenceOfElementLocated(By.className("btn-link")));
		  forgotPassword.click();
	}
	
	public void clickDoneButtonForgotPassword () {
		 WebDriverWait wait = new WebDriverWait(driver, 10);
		  WebElement clickDone = wait.until(
				  ExpectedConditions.presenceOfElementLocated(By.xpath("//div/button")));
		  clickDone.click();
	}
	
	public void clickResetPasswordButton () {
		 WebDriverWait wait = new WebDriverWait(driver, 10);
		  WebElement clickResetPassword = wait.until(
				  ExpectedConditions.elementToBeClickable(By.className("btn-secondary")));
		  clickResetPassword.click();
	}
	
	public void clickNavButtonSpecialistMyAccount () {
		  WebDriverWait wait = new WebDriverWait(driver, 10);
			 WebElement navMyAccountSpecialist = wait.until(
				ExpectedConditions.presenceOfElementLocated(By.id("navManagerMyAccount")));
		  navMyAccountSpecialist.click();
	  }
	
	public void clickDeleteUserButton () {
		 WebDriverWait wait = new WebDriverWait(driver, 10);
		  WebElement deleteUserButton = wait.until(
				  ExpectedConditions.presenceOfElementLocated(By.id("btnDeleteUser")));
		deleteUserButton.click();
	}
	
	public void clickNavButtonNewApplication () {
		WebDriverWait wait = new WebDriverWait(driver, 10);
		WebElement navNewApplication = wait.until(
			ExpectedConditions.presenceOfElementLocated(By.id("navUserNewApplication")));
		navNewApplication.click();
	}
	
	public void clickNavButtonApplicationQueue () {
		WebDriverWait wait = new WebDriverWait(driver, 10);
		WebElement navApplicationQueue = wait.until(
			ExpectedConditions.presenceOfElementLocated(By.id("navManagerApplicationQueue")));
		navApplicationQueue.click();
	}
	
	// WAIT TO ENTER USER EMAIL WHILE RESETTING PASSWORD
	
	public void enterUserEmail (String value) {
		 WebDriverWait wait = new WebDriverWait(driver, 10);
		 WebElement enterUserEmail = wait.until(
			ExpectedConditions.presenceOfElementLocated(By.xpath("//div[3]/input")));
		 enterUserEmail.sendKeys(value);
	}
}
