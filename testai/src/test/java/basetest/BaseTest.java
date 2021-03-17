package basetest;

import org.testng.annotations.BeforeClass;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.AfterClass;

public class BaseTest {
    protected static WebDriver driver;

    @BeforeClass (alwaysRun = true)
    public static void setUp() {
    	System.setProperty("webdriver.chrome.driver", "src/test/resources/chromedriver.exe");
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.get("http://akademijait.vtmc.lt:8181/darzelis/");
    }


    @AfterClass (alwaysRun = true)
    public static void closeBrowser() {
        driver.manage().deleteAllCookies();
        driver.close();
    }
}
