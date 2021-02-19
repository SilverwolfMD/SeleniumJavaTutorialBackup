package Tests;

import java.util.Iterator;
import java.util.Set;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class WindowHandlingAssignment {

	public static void main(String[] args) {
		//variables section
		WebDriver driver;
		String testSite = "https://the-internet.herokuapp.com/";
		String windowSiteXpath = "//a[contains(text(),'Multiple Windows')]";
		String windowTextCSS = "div[class='example'] h3"; // this selector is common to the required text in both parent
															// and child windows, so Selenium can differentiate by which
															// window it is focusing on at the time.
		String childWindowCSS = "div[class='example'] a";
		// window handles
		String parentHandle;
		String childHandle;

		// begin main
		// initialize WebDriver
		/*
		 * This uses TestToolkit, a user-defined static class which draws the WebDriver
		 * paths from a properties file and has useful tools including a random number
		 * generator. TestToolkit is a utility file written for use with the Selenium
		 * Java class.
		 */
		TestToolkit.InitProps();
		driver = TestToolkit.InitWebDrv("chrome");
		
		// access website, proceed to window test
		driver.get(testSite);
		driver.findElement(By.xpath(windowSiteXpath)).click();
		
		// open all other windows needed for this test, in this case, the one child window
		driver.findElement(By.cssSelector(childWindowCSS)).click();
		
		// Obtain window handles
		Set<String> windowSet = driver.getWindowHandles(); // Isolates windows in order of opening
		Iterator<String> windows = windowSet.iterator();
		parentHandle = windows.next(); // Even though the set isn't ordered, the window indices, are, so we can get away with this.
		childHandle = windows.next();
		
		// Capture child window text, print to console
		driver.switchTo().window(childHandle); // point Selenium at the child window
		System.out.println(driver.findElement(By.cssSelector(windowTextCSS)).getText());
		
		// Capture parent window text, print to console
		driver.switchTo().window(parentHandle); // point Selenium at the parent window
		System.out.println(driver.findElement(By.cssSelector(windowTextCSS)).getText()); // remember, the selector string is identical, but this will only get the text from the parent window.
		
		// Cleanup
		driver.quit();

	}//end main

}//end class
