package smokeTestPages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import pages.AbstractObjectPage;

public class CheckIfAllUsersPagesLoad extends AbstractObjectPage {

	// nav buttons
	@FindBy (id= "navAdminApplicationStats")
	public WebElement navPrasymuStatistikaAdmin;
	
	@FindBy (id= "navManagerApplicationAdmin")
	public WebElement navPrasymuRedagavimas;
	
	@FindBy (id= "navAdminEventLog")
	public WebElement navIvykiuZurnalas;
	
	@FindBy (id= "navManagerApplicationStats")
	public WebElement navPrasymuStatistikaKindergartenSpecialist;
	
	@FindBy (id= "navUserApplicationStats")
	public WebElement navPrasymuStatistikaParent;
	
	public void clickNavPrasymuStatistikaAdmin () {
		navPrasymuStatistikaAdmin.click();
	}
	
	public void clickNavPrasymuStatistikaSpecialist () {
		navPrasymuStatistikaKindergartenSpecialist.click();
	}
	
	public void clickNavPrasymuStatistikaParent () {
		navPrasymuStatistikaParent.click();
	}
	
	public void clickNavPrasymuRedagavimas () {
		navPrasymuRedagavimas.click();
	}
	
	public void clickNavIvykiuZurnalas () {
		navIvykiuZurnalas.click();
	}
	
	public Boolean assertNaudotojaiPageTitle () {
		  WebDriverWait wait = new WebDriverWait(driver, 10);
		  return wait.until(ExpectedConditions.textToBe(By.xpath("//*/div/h6"), "Naujo naudotojo sukūrimas"));
		}
	
	public Boolean assertPrasymuStatistikaPageTitle () {
		  WebDriverWait wait = new WebDriverWait(driver, 10);
		  return wait.until(ExpectedConditions.textToBe(By.xpath("//*/h6"), "Prašymų statistika"));
		}
	
	public Boolean assertPrasymuRedagavimasPageTitle () {
		  WebDriverWait wait = new WebDriverWait(driver, 10);
		  return wait.until(ExpectedConditions.textToBe(By.xpath("//*/h6"), "Prašymų sąrašo redagavimo administravimas"));
		}
	
	public Boolean assertIvykiuZurnalasPageTitle () {
		  WebDriverWait wait = new WebDriverWait(driver, 10);
		  return wait.until(ExpectedConditions.textToBe(By.xpath("//*/h6"), "Sistemos įvykių žurnalas"));
		}
	
	public Boolean assertDarzeliuSarasasPageTitle () {
		  WebDriverWait wait = new WebDriverWait(driver, 10);
		  return wait.until(ExpectedConditions.textToBe(By.xpath("//*//form/h6[1]"), "Pridėti naują darželį"));
		}
	
	public Boolean assertPrasymuEilePageTitle () {
		  WebDriverWait wait = new WebDriverWait(driver, 10);
		  return wait.until(ExpectedConditions.textToBe(By.xpath("//*/h6"), "Prašymų eilė"));
		}
	
	public Boolean assertManoPrasymaiPageTitle () {
		  WebDriverWait wait = new WebDriverWait(driver, 10);
		  return wait.until(ExpectedConditions.textToBe(By.xpath("//*/h6"), "Mano prašymai"));
		}
	
	public Boolean assertSukurtiPrasymaPageLoaded () {
		  WebDriverWait wait = new WebDriverWait(driver, 10);
		  return wait.until(ExpectedConditions.textToBe(By.xpath("//*/form/div[1]/div[1]/div/h6"), "Atstovas 1"));
		}
	
	// constructor
	public CheckIfAllUsersPagesLoad(WebDriver driver) {
		super(driver);
	}

}
