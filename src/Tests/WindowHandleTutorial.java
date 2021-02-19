package Tests;

import java.util.Iterator;
import java.util.Set;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class WindowHandleTutorial {

	public static void main(String[] args) {
		
		//variables section
		WebDriver driver;
		String testSite = "https://www.rahulshettyacademy.com/";
		String loginPagePath = "loginpagePractise/#"; // parent window URL
		String docsReqPath = "#/documents-request"; // child window URL
		String pageReqCSS = "a[class='blinkingText']"; // on the parent page, there's only one element which does this.
		String usernameFldCSS = "#username"; // this field requires an input.
		String usernameInfoXpath = "//p[@class='im-para red']/strong/a"; // location of information on the child page
		String username = new String(); // memory location which carries the string
		
		//begin main
		// initialize
		TestToolkit.InitProps();
		driver = TestToolkit.InitWebDrv("chrome");
		driver.get(testSite + loginPagePath);
		
		// Interact with the page request link
		driver.findElement(By.cssSelector(pageReqCSS)).click();
		
		/*
		 * This opens the page...in a new tab.  There's information on this new tab which is needed on the original tab.
		 * Whether a link opens a new window or a new tab, Selenium treats it the same.  So the script needs to capture the
		 * information from the child window and pass it into the parent window.  This means that Selenium needs some way to
		 * navigate between individual windows.
		 * 
		 * The driver object is still interacting with the parent page, so even though the child page is showing, Selenium
		 * cannot interact with any of its elements.
		 */
		
		// Get the page handles:
		Set<String> windowSet = driver.getWindowHandles(); // [parentID, childID].  Remember that sets aren't necessarily ordered, so we 
														   // need a map of our map.
		Iterator<String> windows = windowSet.iterator(); // assigns indices to the map.
		String parentID = windows.next(); // Even though the set isn't ordered, the window indices, are, so we can get away with this.
		String childID = windows.next();
		
		// Switch to the child window and obtain the necessary information
		driver.switchTo().window(childID); // now Selenium sees what the user sees.
		username = driver.findElement(By.xpath(usernameInfoXpath)).getText();
		System.out.println(username); // just a quick test.
		driver.switchTo().window(parentID); // switch control back to the parent window...
		driver.findElement(By.cssSelector(usernameFldCSS)).sendKeys(username);
		
	}

}
