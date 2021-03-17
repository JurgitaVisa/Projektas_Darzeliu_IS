package specialistPages;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import pages.AbstractObjectPage;

public class CheckIfRegistrationIsOpenPage extends AbstractObjectPage{

	@FindBy (id= "btnStartRegistration")
	public WebElement startRegistrationButton;
	
	@FindBy (id= "btnStopRegistration")
	public WebElement stopRegistrationButton;
	
	public void clickStartRegistration() {
		startRegistrationButton.click();
	}
	
	public void clickStopRegistration() {
		stopRegistrationButton.click();
	}

	// constructor
	public CheckIfRegistrationIsOpenPage(WebDriver driver) {
		super(driver);
	}
}