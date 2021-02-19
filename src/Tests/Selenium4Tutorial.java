package Tests;

/*
 * Note: this code is non-operable in the current version of Selenium (3.141.x).
 * Selenium 4.x is currently in Alpha testing.
 * 
 * Original code provided courtesy of Prajakta Salvi, and can be found 
 * at https://www.toolsqa.com/selenium-webdriver/selenium-relative-locators/
 */

//import static org.openqa.selenium.support.locators.RelativeLocator.withTagName;

import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.Rectangle;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.safari.SafariDriver.WindowType;

public class Selenium4Tutorial {
	
	 public static void main(String[] args) {							
		   //variables section
		   WebDriver driver = new FirefoxDriver();
		   String testSite1 = "https://demoqa.com/html-contact-form";
		   String firstNameFldXpath = ".//label[contains(text(),'First Name')]";
		   
		   String testSite2 = "https://rahulshettyacademy.com/angularpractice/";
		   String shopSite = "https://rahulshettyacademy.com/#/index";
		   String firstCourseCSS = "a[href*='https://courses.rahulshettyacademy.com/p']"; // returns the first course in the shop page
		   String courseNameCapture = new String();
		   String nameFieldCSS = ".form-group input[name='name']";
			 
		   // Initialize the web browser and point Selenium on the title.
		   TestToolkit.InitProps();
		   driver = TestToolkit.InitWebDrv("chrome");
		   driver.get(testSite1);
				 
		   // For relative locators to work, we need the webElement data type for some known area on the page.
		   WebElement nameLabel = driver.findElement(By.xpath(firstNameFldXpath));			
				
//		   // Now things get interesting.  The RelativeLocator class needs to know what it's looking for and in relation to what known WebElement.
//		   WebElement nameText = driver.findElement(withTagName("input").toRightOf(nameLabel));//RelativeLocator.withTagName static method		
//		   // or, "I'm looking for the first element with this tag name, located to the right of this known point."
//		   nameText.sendKeys("Mr.Jones");	
//	
//		   // Since we captured the webElement previously, we can use it to find another element relative to the new locator:
//		   WebElement lastName = driver.findElement(withTagName("input").below(nameText));
//		   lastName.sendKeys("D'souza");			
//				
//		   // And so on...
//		   WebElement countryName = driver.findElement(withTagName("input").below(lastName)); 
//	  	   countryName.sendKeys("United States");	
//				
//		   // Seeing a pattern here?
//		   WebElement subject = driver.findElement(withTagName("textarea").below(countryName));
//		   subject.sendKeys("This is for RelativeLocator Demo");
//				
//		   List<WebElement> labels = driver.findElements(withTagName("label").above(subject));
//		   for(WebElement e : labels) {
//			 System.out.println("labels-->"+e.getText());
//		   }
//				 
//		   // Much like other methods in dot chains (like with actions and streams), instructions can be combined sequentially in the same statement.
//		   WebElement label = driver.findElement(withTagName("label").above(subject).toLeftOf(countryName));
//		   System.out.println("Label for Country Textfield--->"+label.getText());			 
//				 
//		   // Click on the Submit button 
//		   WebElement submit = driver.findElement(withTagName("input").below(subject));
//		   submit.click();			
//		   
//		   // Multiple windows
//		   driver.get(testSite2);
//		   driver.switchTo().newWindow(WindowType.TAB); // this new method opens a new tab.  With the new method we can specify whether we want a new window or a new tab to work on.
//		   // Get the page handles:
//		   Set<String> windowSet = driver.getWindowHandles(); // [parentID, childID].  Remember that sets aren't necessarily ordered, so we need a map of our map.
//		   Iterator<String> windows = windowSet.iterator(); // assigns indices to the map.
//			String parentID = windows.next(); 
//			String childID = windows.next();
//			
//			// Switch to the child window and obtain the necessary information
//			driver.switchTo().window(childID); // now Selenium sees what the user sees.
//			courseNameCapture = driver.findElements(By.cssSelector(firstCourseCSS)).get(0).getText();
//			
//			// Switch back to the parent window and put the data in the right field.
//			driver.switchTo().window(parentID);
//			driver.findElement(By.cssSelector(nameFieldCSS)).sendKeys(courseNameCapture);
//			
//			// Partial screenshot based on webElement
//			WebElement nameFld = driver.findElement(By.cssSelector(nameFieldCSS));
//			TestToolkit.ScreenShot(nameFld, "partial " + TestToolkit.TimeStamp());
			
			// Get the dimensions of the web element
//			Dimension rekt = nameFld.getRect().getDimension();
		   
		   // cleanup
		   driver.close();
	 
	 }
}
