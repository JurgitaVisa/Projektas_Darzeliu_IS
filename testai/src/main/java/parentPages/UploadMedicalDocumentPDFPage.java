package parentPages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import pages.AbstractObjectPage;

public class UploadMedicalDocumentPDFPage extends AbstractObjectPage{

	// buttons
	@FindBy(id = "btnUploadDocument")
	public WebElement buttonUploadDocument;
	
	@FindBy(id = "inputUploadDocument")
	public WebElement buttonInputDocument;
	
	@FindBy(xpath = "//*/div[3]//button")
	public WebElement buttonDownloadDocument;
	
	@FindBy(xpath = "//*/table//tr[1]/td[4]/button")
	public WebElement buttonDeleteDocument;
	
	public void clickUploadDocumentButton () {
		buttonUploadDocument.click();
	}
	
	public void clickDownloadDocumentButton () {
		buttonDownloadDocument.click();
	}
	
	public void clickDeleteDocumentButton () {
		buttonDeleteDocument.click();
	}
	
	// constructor
	public UploadMedicalDocumentPDFPage(WebDriver driver) {
		super(driver);
	}

}
