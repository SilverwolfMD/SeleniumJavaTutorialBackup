package Tests;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;

public class SwitchingUIAssignment {

	public static void main(String[] args) {
		// variables section
		WebDriver driver;
		String testSite = "https://rahulshettyacademy.com/AutomationPractice/"; // the original URL,
																				// qaclickacademy.com/practice.php,
																				// redirects here.
		String checkBoxXpath = "//input[@id='checkBoxOption" + TestToolkit.getRandomNumberInRange(1, 3) + "']"; // random
																												// selection
																												// of a
																												// checkbox
		String dropDownCSS = "#dropdown-class-example";
		String alertExampleTxtCSS = "#name";
		String alertBtnCSS = "#alertbtn";

		// begin main
		// Initialize properties and webdriver using user-defined TestToolkit class for the common code
		TestToolkit.InitProps();
		driver = TestToolkit.InitWebDrv("chrome");

		// navigate to test page, instantiate element-specific objects
		driver.get(testSite);
		Select dropdown = new Select(driver.findElement(By.cssSelector(dropDownCSS)));

		// select randomly chosen checkbox
		driver.findElement(By.xpath(checkBoxXpath)).click();

		// get the label for that checkbox
		String choiceLabel = driver.findElement(By.xpath(checkBoxXpath + "/parent::label")).getText();
		/*
		 * Even though the page code shows that the label should be accessible from the
		 * checkBoxXpath element, it doesn't find the value in the check box itself.
		 * Selenium needs to navigate up one level.
		 */

		// Select the identical option in the Dropdown example using the label string
		dropdown.selectByVisibleText(choiceLabel);

		// write the string for that label into the "Switch To Alert Example" field.
		driver.findElement(By.cssSelector(alertExampleTxtCSS)).sendKeys(choiceLabel);

		// click on "Alert," then verify if text from that label string is present in
		// the pop-up message
		driver.findElement(By.cssSelector(alertBtnCSS)).click();
		Assert.assertTrue(driver.switchTo().alert().getText().contains(choiceLabel));

		// cleanup
		driver.switchTo().alert().accept();
		driver.quit();

	}// end main

}
