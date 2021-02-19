package Tests;

import java.util.List;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.testng.Assert;

public class AutoCompleteAssignment {

	public static void main(String[] args) {
		//variables section
		WebDriver driver;
		String testSite = "https://rahulshettyacademy.com/AutomationPractice/";
		String inputFieldCSS = "#autocomplete";
		String inputQuery = "uni";
		String fullQuery = "United States (USA)";
		String suggestionCSS = ".ui-menu-item-wrapper";
		boolean isFound = false; // boolean flag which, when the specific fullQuery is encountered, is set true

		//begin main
		/*
		 * TestToolkit is a user-defined class which contains general-purpose code
		 * needed to access and concatenate file paths and initialize drivers.
		 */
		// initialize webDriver and Actions
		TestToolkit.InitProps(); // initialize the properties file reader
		driver = TestToolkit.InitWebDrv("chrome"); // Initialize the webdriver for the Chrome browser
		Actions digiHand = new Actions(driver);
		driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);

		// open test page
		driver.get(testSite);

		// input test query and get suggestions list
		digiHand.moveToElement(driver.findElement(By.cssSelector(inputFieldCSS))).click().sendKeys(inputQuery).build()
				.perform();

		// locate full query
		List<WebElement> suggestions = driver.findElements(By.cssSelector(suggestionCSS));

		// test whether each suggestion auto-populates the field and the desired query
		// is in the set of suggestions
		for (int i = 0; i < suggestions.size(); ++i) {
			digiHand.sendKeys(Keys.ARROW_DOWN).build().perform();
			String suggestStr = driver.findElement(By.cssSelector(inputFieldCSS)).getAttribute("value");
			Assert.assertEquals(suggestStr, suggestions.get(i).getText()); // Tests whether the field is auto-populated
																			// correctly
			if (suggestStr.equalsIgnoreCase(fullQuery)) {
				isFound = true;
			} //end if
		} //end for(i)
		Assert.assertTrue(isFound); // Tests whether the desired query is in the suggestions list

	}//end main

}
