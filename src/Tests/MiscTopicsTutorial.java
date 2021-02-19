package Tests;

import java.io.File;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.testng.Assert;
import org.testng.asserts.SoftAssert;

public class MiscTopicsTutorial {

	public static void main(String[] args) {
		//variables section
		WebDriver driver;
		DesiredCapabilities opts;
		String testSite = "http://google.com";
		String testSite2 = "https://rahulshettyacademy.com/AutomationPractice/";
				
		//begin main
		// initialize web browser profile
		opts = DesiredCapabilities.chrome(); // generates a capabilities profile for Chrome out of the DesiredCapabilities class
		opts.acceptInsecureCerts(); // adds the capability to accept insecure certifications
		opts.setCapability(CapabilityType.ACCEPT_SSL_CERTS, true); // adds the capability to accept SSL certifications
		
		// initialize webDriver
		TestToolkit.InitProps();
		driver = TestToolkit.InitWebDrv("chrome", opts); // initialize webdriver with the specific options.
		driver.manage().window().maximize(); // maximize window
		driver.manage().deleteAllCookies(); // purge the cookies from the web browser.  Or, you can use deleteCookieNamed(CookieName) which will delete a specific cookie.
		// driver.manage().addCookie(CookieObject); // adds a specific cookie.
		driver.get(testSite);
		
		// in order to capture screenshots, the WebDriver must be cast to an object called TakesScreenshot.
		// File requires java.io.File package, all others derive from the org.openqa.selenium library.
		File screenShot = ((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);
		// once captured, the screenshot needs to be stored to the HD.  The FileUtils comes from apache.commons.io:
		try {
			FileUtils.copyFile(screenShot, new File(".\\screenshot.png")); // the '.' refers to the current directory, in this case, the SeleniumJavaTutorial directory.
		} catch (IOException e) {
			// TODO Auto-generated catch block
			System.out.println("Unable to store screenshot file.");
			e.printStackTrace();
		}
		
		// this could be a useful function to add to the test toolkit, and can be executed as:
		TestToolkit.ScreenShot(driver, ".\\" + TestToolkit.TimeStamp() + ".png");
		
		// Broken links
		driver.get(testSite2);
		// Get locators for the links to be tested. 
		List<WebElement> URLs = driver.findElements(By.xpath("//a"));
		boolean brokenLink = false;
		
		// Convert to URL list
		ArrayList<HttpURLConnection> conn = new ArrayList<HttpURLConnection>(); // seems like a waste of memory, but we need to get around the exception throws.
		for(int i = 0; i < URLs.size(); ++i) {
			try {
				if (URLs.get(i).isDisplayed()) { // ensure that URL's meeting valid protocols are converted
					conn.add((HttpURLConnection) new URL(URLs.get(i).getAttribute("href")).openConnection());
				}
			} catch (MalformedURLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		SoftAssert softAss = new SoftAssert();
		for (int i = 0; i < conn.size(); ++i) {
			try {
				conn.get(i).setRequestMethod("HEAD");
			} catch (ProtocolException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			try {
				conn.get(i).connect();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		
			try {
				softAss.assertTrue(conn.get(i).getResponseCode() <= 400, "URL " + conn.get(i).toString().substring(conn.get(i).toString().indexOf(":http") + 1) + " is defective with response code of " + conn.get(i).getResponseCode());   // the soft assertion softAss receives all of the failed assertions.
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		// Assert.assertFalse(brokenLink);
		softAss.assertAll(); // this takes all of the accumulated assertions, and if there was an assertion failure, now it outputs the messages in the second parameter and relays the failure to the test framework.		
		
		// cleanup
		driver.quit();
		
	}//end main

}
