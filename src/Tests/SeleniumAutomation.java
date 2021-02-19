package Tests;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;



public class SeleniumAutomation {

	public static void main(String[] args) {
		//variables section
		String statTestURL = "https://www.rahulshettyacademy.com/angularpractice/";
		String dynTestURL = "https://rahulshettyacademy.com/dropdownsPractise/";
		TestIdentity person = new TestIdentity();
		String testPass = TestToolkit.RandString(10, true);
		WebDriver driver;
		
		//begin main
		person = TestIdentity.GenerateUSID(person);
		System.out.println(person.toString());
		TestToolkit.InitProps();  // initialize the properties file
		driver = TestToolkit.InitWebDrv("chrome");  // initialize webdriver to selection
		driver.get(statTestURL);
		assert driver.getCurrentUrl() == statTestURL;  // for safety, make sure we didn't get a redirect
		
		// just like with the SeleniumLocators exercise, we can fill out the form...
		driver.findElement(By.id("exampleInputPassword1")).sendKeys(testPass);
		driver.findElement(By.id("exampleCheck1")).click();
		driver.findElement(By.name("name")).sendKeys(person.firstName + " " + person.lastName);
		driver.findElement(By.cssSelector("input[name='email']")).sendKeys(person.email);
		
		// Static dropdowns:
		/*
		 * Selenium has a class for that.  We saw this in Python, and thanks to cross-language support, we have the equivalent
		 * in Java.  We need to adjust for syntax, but we create a Select object and initialize it with the WebElement corresponding
		 * to the menu itself:
		 */
		Select dropdown = new Select(driver.findElement(By.id("exampleFormControlSelect1")));
		
		// now we have some options to work with.
		dropdown.selectByVisibleText("Female"); // selects "female," the value at index 1
		try {
			Thread.sleep(1000);  // this is a static wait method in Java.
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		dropdown.selectByIndex(0); // selects "male," the value at index 0
		//dropdown.selectByValue("key name"); // this selects the value corresponding to a key in this menu.
		try {
			Thread.sleep(1000); 
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		driver.findElement(By.xpath("//input[@type='submit']")).click();
		System.out.println(driver.findElement(By.className("alert-success")).getText());
		
		// Dynamic dropdowns:
		// In this case we need to know locators for the input and the result list.  For this one, we're selecting the country of origin:
		String countryFieldCSS = "input#autosuggest";  // the field where we enter our query.  Alternatively this could be:
		// String countryFieldCSS = "#autosuggest"; // CSS based off of an ID
		// String countryFieldID = "autosuggest"; // since we have By.Id as an option
		String countryQuery = "un"; // the query, in this case, a partial string
		String countryResultCSS = "li.ui-menu-item";  // a locator for each one of the results returned.
		
		// data acquisition:
		driver.get(dynTestURL);
		driver.manage().window().maximize();
		try {
			Thread.sleep(2000); 
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		driver.findElement(By.cssSelector(countryFieldCSS)).sendKeys(countryQuery);  // this manifests the list.
		try {
			Thread.sleep(2000); // this is a static wait function, analogous to Time.sleep in python.
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		List<WebElement> countryResults = driver.findElements(By.cssSelector(countryResultCSS)); // this retrieves all of the elements.
		System.out.println(countryResults.size());  // just to show that we've got something.  If we messed up, this will return 0.
		
		// Now, we can find the element we're looking for.  "un" was enough to narrow it down, but we want something more specific.
		for(WebElement country: countryResults) { // we don't necessarily need the size or index, we just need to search the list.
			if(country.getText().contains("United States")) {
				country.click();
				break;
			}  // this is almost exactly like what we did with Python, we just adjusted for syntax.
		}
		try {
			Thread.sleep(2000); 
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println(driver.findElement(By.cssSelector(countryFieldCSS)).getAttribute("value")); // even though there's no value key,
		// it will return the string from the field.
		Assert.assertEquals(driver.findElement(By.cssSelector(countryFieldCSS)).getAttribute("value"), "United States (USA)");
				
		String originCityDropDownCSS = "#ctl00_mainContent_ddl_originStation1_CTXT";
		String originCityMenuCSS = "table[id='citydropdown'] li";
		
		driver.findElement(By.cssSelector(originCityDropDownCSS)).click();
		try {
			Thread.sleep(2000); 
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		
		Assert.assertNotNull(TestToolkit.DynMenuSelect(driver, By.cssSelector(originCityMenuCSS), "(DEL)"));
		/*
		 * this uses a function written in our toolkit in order to minimize the amount of memory used.  This funciton takes 3 arguments:
		 * drv: the webdriver, in this case, driver
		 * locator: type By, which allows us to use any of the locators available
		 * query: a string which matches the text in an element.  In future versions, this will be replaced in function overloads for different
		 * types of matches (key/value, index, etc)
		 * 
		 * If there is >=1 WebElement matching the query, then the first element matching that query will be returned.
		 * If the element is not found, the method returns null.
		 * 
		 */
		
		// If we want to count the number of a particular element, we need a common locator:
		String typeCSS = "input[type ='checkbox']";
		
		// And since we don't need the contents of another list, let's just get the size of it:
		System.out.println(driver.findElements(By.cssSelector(typeCSS)).size());
		
		try {
			Thread.sleep(3000); 
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		
		driver.quit();
	}//end main

}
