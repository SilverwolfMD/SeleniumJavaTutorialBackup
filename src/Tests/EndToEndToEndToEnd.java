package Tests;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class EndToEndToEndToEnd {

	public static void main(String[] args) {
		//variables section
		WebDriver driver;
		String testSite = "http://qaclickacademy.com/practice.php";
		String footerLinksXpath = "//table[@class='gf-t']//tbody";
		String footerSxnCSS = "#gf-BIG";
		String footerColXpath = "//body/div[@id='gf-BIG']/table[@class='gf-t']/tbody/tr/td[1]";
		
		//begin main
		// initialize webdriver
		TestToolkit.InitProps();
		driver = TestToolkit.InitWebDrv("chrome");
		
		// access test site
		driver.get(testSite);
		// How many links on the page?
		System.out.println(driver.findElements(By.tagName("a")).size());
		
		// how many links on the footer section alone?  The links are contained in a table.
		System.out.println(driver.findElements(By.xpath(footerLinksXpath)).size());
		// This doesn't work...it returns 1, when there are clearly more links in the footer.
		// One solution is to restrict the scope of the webdriver.
		
		WebElement footerDrv = driver.findElement(By.cssSelector(footerSxnCSS));
		// the webElement footerDrv, or any WebElement we specify, is an object, so it contains the methods of any webdriver.  However the only elements it can "see" are those in the footer.
		
		System.out.println(footerDrv.findElements(By.tagName("a")).size());
		
		WebElement footerCol1Drv = footerDrv.findElement(By.xpath(footerColXpath));
		System.out.println(footerCol1Drv.findElements(By.tagName("a")).size());
		
		/*
		 * If we wanted to test the page by clicking every one of those links and going back, there are 2 problems:
		 * 1) There is a loading time delay for re-loading the source page.
		 * 2) The element reference would need to be re-built each time.
		 * 
		 * While this may be necessary if the page code has safeguards preventing tabbed browsing or new windows, there
		 * is a better way to test these links by using tabs (which can be opened by ctrl-click, not just through the context menu): 
		 */
		
				
		// open each of the links in a tab
		for(int i = 1; i < footerCol1Drv.findElements(By.tagName("a")).size(); ++i) {
			// Here, we're skipping the first link in the list, and restricting the test to the sub-links beneath, hence why we're starting at index 1.
			String clickOnLinkTab = Keys.chord(Keys.CONTROL, Keys.ENTER); // instead of instantiating an actions object, we can create a key chord.  The driver will find the element and send the keystrokes to the link, and it will behave like a modified click.
			footerCol1Drv.findElements(By.tagName("a")).get(i).sendKeys(clickOnLinkTab);
			// instead of having to allocate and maintain memory for a list, we just instantiate the list, get the element, interact with it, and let the list go.
			try {
				Thread.sleep(5000L); // loading time for each tab
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}// end for i

		// make a list of the tabs
		Set<String> windowHandles = driver.getWindowHandles(); // this is going to re-build our window handles set every time.
		Iterator<String> tabsIter = windowHandles.iterator();
		
		// retrieve the names for each of the tabs
		while(tabsIter.hasNext()) { // iterate as long as there's another 
			driver.switchTo().window(tabsIter.next()); // this cycles from the first tab to the most recently opened tab.
			System.out.println(driver.getTitle());
		}//end While(tabsIter)

				
		// if we just tried going back without re-building the webdriver scope every time...
		
		WebDriverWait holdIt = new WebDriverWait(driver, 5); // for ensuring that the original page re-loads before the next loop iteration, in order to prevent a StaleElementReferenceException.
		try {
		for(int i = 1; i < footerCol1Drv.findElements(By.tagName("a")).size(); ++i) {
			footerCol1Drv.findElements(By.tagName("a")).get(i).click(); 
			driver.navigate().back(); // return to the previous page
			holdIt.until(ExpectedConditions.visibilityOf(footerDrv));
			
		}//end for i
		}
		catch (StaleElementReferenceException expStaleElement){ // this is the exception that would be thrown.
			System.out.println("Element no longer valid from page.");
			expStaleElement.printStackTrace();
			System.out.println("This is an expected failure/negative test.");
		}
		
		
		// cleanup
		driver.quit();

	}//end main

}
