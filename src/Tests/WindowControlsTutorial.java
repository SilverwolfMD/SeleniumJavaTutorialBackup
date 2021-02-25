package Tests;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

public class WindowControlsTutorial {

	public static void main(String[] args) {
		String siteURL = "the-internet.herokuapp.com";
		String protocol = "https://";
		String testSite = protocol + siteURL;
		String generiCred = "admin"; // username and password credential string for test site
		WebDriver driver;
		String authPopXpath = "//a[contains(text(),'Basic Auth')]";
		String convSite = "https://altoconvertpdftojpg.com/";
		String selectFileCss = "#dropzoneInput-label";
		String convBtnXpath = "//button[contains(text(),'Convert Now!')]";
		String dlBtnXpath = "//a[@class='g-btn g-btn-secondary g-btn-auto-width']";
		WebDriverWait holdIt;
		Actions digiFinger;
		
		//begin main
		TestToolkit.InitProps();
		String setPropPath = TestToolkit.props.getProperty("driversDir") + "\\chromedriver.exe";
		System.setProperty("webdriver.chrome.driver", setPropPath);
		
		HashMap<String, Object> chromePrefs = new HashMap<String, Object>(); // this is how we modify the prefs file.
		
		// these key-value pairings come from the ChromePrefs documents.
		chromePrefs.put("profile.default_content_settings.popups", 0); // deactivate popUps
		chromePrefs.put("download.default_directory", System.getProperty("user.dir") + "\\"); // change default directory
		
		ChromeOptions options=new ChromeOptions(); // instantiate the options object

		options.setExperimentalOption("prefs", chromePrefs); // set the chrome preferences into the options object using the hashmap
		driver = new ChromeDriver(options);

		holdIt = new WebDriverWait(driver, 5);
		digiFinger = new Actions(driver);
		
		
		driver.get(testSite); // access the site.
		driver.manage().deleteAllCookies();

		driver.get("http://" + generiCred + ":" + generiCred + "@" + siteURL); // concatenates the string, passes the username and password, and places it in memory for the authentication..
		driver.findElement(By.xpath(authPopXpath)).click(); // trigger the pop-up.

		// file conversion: upload the file
		driver.get(convSite); // navigate to converter website
		driver.findElement(By.cssSelector(selectFileCss)).click();
		try {
			Thread.sleep(3000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			Runtime.getRuntime().exec("./"+"fileUpload.exe");
			// this allows Java to execute the AutoIT file.
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		// convert the file
		holdIt.until(ExpectedConditions.elementToBeClickable(By.xpath(convBtnXpath)));
		driver.findElement(By.xpath(convBtnXpath)).click();
		
		// download the file
		holdIt.until(ExpectedConditions.elementToBeClickable(By.xpath(dlBtnXpath)));
		WebElement dlBtn = driver.findElement(By.xpath(dlBtnXpath));
		digiFinger.contextClick(dlBtn).sendKeys(Keys.ARROW_DOWN).sendKeys(Keys.ARROW_DOWN).sendKeys(Keys.ARROW_DOWN).sendKeys(Keys.ARROW_DOWN).sendKeys(Keys.RETURN).build().perform();
		// of course this doesn't work anyway because Chrome keeps defaulting to the downloads folder.  It just ignores the context-click.
				
		try { // explicit wait will not work on the desktop applications.
			Thread.sleep(3000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			Runtime.getRuntime().exec("./" + "fileDownload.exe");
			// this allows Java to execute the AutoIT file.
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		// so this is a moot point.
		
		// verify that the file has been downloaded
		File explorer = new File(System.getProperty("user.dir") + "\\" + "1.jpg");
		Assert.assertTrue(explorer.exists());
		
		// cleanup
		explorer.delete();
		 driver.quit();

	}//end main

}
