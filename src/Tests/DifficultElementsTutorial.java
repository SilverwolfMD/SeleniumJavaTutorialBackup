package Tests;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.testng.Assert;

public class DifficultElementsTutorial {

	public static void main(String[] args) {
		//variables section
		WebDriver driver;
		String testSite = "https://rahulshettyacademy.com/AutomationPractice/";
		
		//begin main
		// initialize webDriver
		TestToolkit.InitProps();
		driver = TestToolkit.InitWebDrv("chrome");
		
		// open test page
		driver.get(testSite);
		JavascriptExecutor javaExec = (JavascriptExecutor)driver;  // for this we effectively cast the webdriver to our Javascript Executor.
		javaExec.executeScript("window.scrollBy(0,375)");
		try {
			Thread.sleep(5000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		javaExec.executeScript("document.querySelector(\".tableFixHead\").scrollTop=5000"); // this is a script command, be sure to escape all included double-quotes or replace them with single quotes.
		
		String tableAmtCss = ".tableFixHead td:nth-child(4)"; // this is specific to all 10 elements in the "amount" column.
		int amtTotal = 0;
		List<WebElement> values = driver.findElements(By.cssSelector(tableAmtCss));
		for (WebElement value: values) {
			amtTotal += Integer.parseInt(value.getText());
		}
		System.out.println("Total Amount: " + amtTotal);
		
		String webTotalAmtCss = "div[class='totalAmount']"; // locator for the web page's output
		// If we want the numerical value, we need to parse the string: extract the integer portion of the string and then convert that substring to an integer value.
		String inString = driver.findElement(By.cssSelector(webTotalAmtCss)).getText();
		// Since we know we can parse the integer, and we know that this is pre-formatted to have a delimiter of a colon and a space, we just need to find the colon and then advance the index by 2:
		Assert.assertEquals(Integer.parseInt(inString.substring(inString.indexOf(':') + 2)), amtTotal);
		// this works as long as text preceding the integer of interest remains somewhat consistent.

	}//end main

}
