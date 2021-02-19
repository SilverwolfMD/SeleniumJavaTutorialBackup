package Tests;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;

// TestNG: one of the testing frameworks available to Selenium Java.

public class JavaAssertionsTutorial {
	
	public static void main(String[] args) {
		//variables section
		String testURL = "https://rahulshettyacademy.com/AutomationPractice/";
		String checkBox1CSS = "#checkBoxOption1"; // CSS selector for one particular checkbox
		String checkBoxCSS = "input[type='checkbox']";  // CSS selector for any input of type checkbox
		WebDriver driver;
		
		//begin main
		TestToolkit.InitProps();  // TestToolkit is a public static class containing universal test utilities.
		// the InitProps() method opens a properties file which contains the paths to the webdrivers.
		driver = TestToolkit.InitWebDrv("chrome");
		
		driver.get(testURL);
		driver.findElement(By.cssSelector(checkBox1CSS)).click(); // page initializes with cleared checkboxes, so this must be checked
		Assert.assertTrue(driver.findElement(By.cssSelector(checkBox1CSS)).isSelected()); // Verify checked status
		driver.findElement(By.cssSelector(checkBox1CSS)).click(); // Clear the checkbox
		Assert.assertFalse(driver.findElement(By.cssSelector(checkBox1CSS)).isSelected()); // verify cleared status
		System.out.println(driver.findElements(By.cssSelector(checkBoxCSS)).size()); // return the number of checkboxes
		
		driver.quit();		
	}//end main

}
