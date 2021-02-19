package Tests;

import java.net.MalformedURLException;
import java.net.URL;

import org.openqa.selenium.Platform;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;

public class SeleniumGridTutorial {

	
	
	public static void main(String[] args) throws MalformedURLException {
		// variables section
		DesiredCapabilities opts = new DesiredCapabilities(); // for selenium grid, DesiredCapabilities is a required class.
		String nodeAddr = "http://localhost:4444/wd/hub"; // the IP, port, and path to the hub server.
		String testSite = "https://rahulshettyacademy.com/AutomationPractice/";
		
		//begin main
		// initialize the webdriver
		opts.setBrowserName("chrome"); // This tells the object we're looking for a chrome browser.
		opts.setPlatform(Platform.WIN10); // This is the OS operating on the node.
		// opts.setVersion("88.0.4324.104"); // if we have multiple versions of the web browser for testing, we specify with this method.
		
		WebDriver driver = new RemoteWebDriver(new URL(nodeAddr), opts);
	
		/*
		 * The RemoteWebDriver class behaves the same as WebDriver for the purposes of the test code.  However, the webdriver is executed remotely.
		 * For cross-browser testing (multiple browsers), the desired capabilities must be configured for each browser, and a driver needs to be initialized for that set of capabilities.
		 */
		
		// do something with the webDriver
		driver.get(testSite);
		
		
	}

}
