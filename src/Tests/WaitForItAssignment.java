package Tests;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class WaitForItAssignment {

	public static void main(String[] args) {
		// variables section
		WebDriver driver;
		String testSite = "http://www.itgeared.com/demo/1506-ajax-loading.html"; // This address does not have the
																					// "http://" part, but ChromeDriver
																					// requires that part in order to
																					// function. Without it, it throws
																					// an ElementNotFound exception.
		String testLinkText = "Click to load get data via Ajax!";
		String successCSS = "#results";

		// begin main
		// Initialize properties and webdriver
		/*
		 * This uses TestToolkit, a user-defined static class which draws the WebDriver
		 * paths from a properties file and has useful tools including a random number
		 * generator. TestToolkit is a utility file written for use with the Selenium
		 * Java class.
		 */
		TestToolkit.InitProps();
		driver = TestToolkit.InitWebDrv("chrome");
		driver.get(testSite);
		WebDriverWait holdIt = new WebDriverWait(driver, 5);

		// Get data
		driver.findElement(By.linkText(testLinkText)).click();
		holdIt.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(successCSS)));
		System.out.println(driver.findElement(By.cssSelector(successCSS)).getText());

	}// end main

}
