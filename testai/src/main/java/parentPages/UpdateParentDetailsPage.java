package parentPages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import pages.AbstractObjectPage;

public class UpdateParentDetailsPage extends AbstractObjectPage{

	// input fields
	@FindBy (id= "txtPersonalCode")
	public WebElement personalCode;
	
	@FindBy (id= "txtTelNo")
	public WebElement telephoneNumber;
	
	@FindBy (id= "txtAddress")
	public WebElement userAddress;
	
	public void inputPersonalCode (String value) {
		personalCode.clear();
		personalCode.sendKeys(value);
	}
	
	public void inputTelephoneNumber (String value) {
		telephoneNumber.clear();
		telephoneNumber.sendKeys(value);
	}
	
	public void inputUserAddress (String value) {
		userAddress.clear();
		userAddress.sendKeys(value);
	}
	
	// constructor
	public UpdateParentDetailsPage(WebDriver driver) {
		super(driver);
	}

}
