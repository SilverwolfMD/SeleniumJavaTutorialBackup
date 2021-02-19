package Tests;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class SeleniumLocators {

	public static void main(String args[]) {
		
		//constants section
		
		
		//variables section
		TestIdentity person = new TestIdentity();  // We're going to need test values for field entries.
		String testURL = "https://www.rahulshettyacademy.com/angularpractice/"; // This is convenient for our purposes.
		String testPass = TestToolkit.RandString(10, true); // generate a random string for use as a password.
		
		//begin main
		person = TestIdentity.GenerateUSID(person);
		System.out.println(person.toString());
		TestToolkit.InitProps();  // remember, TestToolkit and the methods are defined in another file, but they use the same selenium code we learned earlier.  We're using the props file because it holds the path for our webdrivers.
		WebDriver driver = TestToolkit.InitWebDrv("chrome");
		driver.get(testURL);
		assert driver.getCurrentUrl() == testURL;  // for safety, make sure we didn't get a redirect
		
		// ID locator
		/*
		 * This has some particularly interesting properties when it comes to locator syntax, as we saw in Python.
		 * However, ID has a dependency...the tag must contain an ID value.  Unlike XPath or CSS, which can be approximated and
		 * then localized.
		 * Many sites also change the ID number in order to foil malicious bots, and this code doesn't discriminate tests from
		 * blackhat hackers.
		 */
		driver.findElement(By.id("exampleInputPassword1")).sendKeys(testPass);
		driver.findElement(By.id("exampleCheck1")).click();
		// Ordinarily I like to keep variables consolidated, but sometimes they must be declared a la carte, and fortunately, C++ based languages allow for that.
		//dropdown = Select(driver.findElement(By.id("exampleFormControlSelect1")));
		
		
		// Name locator
		driver.findElement(By.name("name")).sendKeys(person.firstName + " " + person.lastName);
		
		// Linktext locator
		/*
		 * this makes use of the text in the actual hyperlink.
		 */
		//driver.findElement(By.linkText("Contact Us")).click();
		
		// CSS locator
		driver.findElement(By.cssSelector("input[name='email']")).sendKeys(person.email);
		
		// XPath locator
		/*
		 * Much like CSS selectors, an XPath can have an absolute and a relative form.  Unlike CSS selectors, XPaths can be navigated back towards parents
		 */
		driver.findElement(By.xpath("//input[@type='submit']")).click();
		
		// ClassName locator
		/*
		 * This is the identity of the class used, and is used if the program uses a particular class in unique situations.
		 * The ClassName locator is similar to the Name locator.
		 * Classes should not have spaces.  If spaces are present, this will throw an error (Compound classes cannot be accepted).
		 * If this is the case, then you need to use XPath or CSS instead.
		 */
		System.out.println(driver.findElement(By.className("alert-success")).getText());
		
		// Another example of a CSS selector, using a quick detour:
		driver.get("https://rahulshettyacademy.com/AutomationPractice/");
		System.out.println(driver.findElement(By.cssSelector("#autocomplete")).getAttribute("placeholder"));
		// In this case, the # denotes a CSS selector constructed by the ID of an element.
		// the method "getAttribute" returns the string associated with the key specified in the argument.
		driver.get("https://www.rediff.com");
		driver.findElement(By.linkText("Sign in")).click();
		driver.findElement(By.cssSelector("#login1")).sendKeys("Some_user");
		
		// XPath Parent Child Traverse relationship
		driver.get(testURL);
		driver.findElement(By.cssSelector("a[href*='shop']")).click(); // remember this from Python?
		String testProduct = "Blackberry";
		// First, we need some unique identifier.  We can concatenate the testProduct string into a link-based xpath:
		String productDetailLinkXpath = "//a[contains(text(),'" + testProduct + "')]";
		// Then, we need the traverse to move back up the tree by the parent nodes (parent-child traverse):
		String parentChildTraverseXpath = "/parent::h4/parent::div/parent::div";
		// Now that we have moved up far enough to find where we need, we can use a relative Xpath from this node to the button:
		String purchaseButtonXpath = "//button";
		// Finally, we concatenate the entire xpath in a method call to our webdriver:
		driver.findElement(By.xpath(productDetailLinkXpath + parentChildTraverseXpath + purchaseButtonXpath)).click();
		
		driver.navigate().back();

		driver.quit();
		
		
		
	}
	
}
