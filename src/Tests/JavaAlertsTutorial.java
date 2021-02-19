package Tests;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class JavaAlertsTutorial {

	public static void main(String[] args) {
		// Variables section
		String autoPracticeURL = "https://rahulshettyacademy.com/AutomationPractice/";
		WebDriver driver; // instantiate an object of class WebDriver using the ChromeDriver attributes
		String alertBtnCSS = "#alertbtn";
		String nameFldCSS = "#name";
		String confBtnCSS = "#confirmbtn";
		TestIdentity person = new TestIdentity();

		// Begin main
		TestToolkit.InitProps();
		driver = TestToolkit.InitWebDrv("chrome");
		person.GenerateUSID(person);
		driver.get(autoPracticeURL); // this is our test web page.
		System.out.println(person.firstName);
		driver.findElement(By.cssSelector(nameFldCSS)).sendKeys(person.firstName);
		driver.findElement(By.cssSelector(alertBtnCSS)).click(); // this is the test stub, basically a java button.
		System.out.println(driver.switchTo().alert().getText()); // the switchTo() method points Selenium at the alert
																	// box, rather than the page.
		driver.switchTo().alert().accept(); // the java popup has very consistent and standardized code, so this method
											// will hit the OK button.
		driver.findElement(By.cssSelector(nameFldCSS)).sendKeys(person.firstName); // the field clears between uses.
		driver.findElement(By.cssSelector(confBtnCSS)).click();
		driver.switchTo().alert().dismiss(); // used if there is a cancel button.
		driver.findElement(By.cssSelector(nameFldCSS)).sendKeys(person.firstName);
		driver.findElement(By.cssSelector(confBtnCSS)).click();
		System.out.println(driver.switchTo().alert().getText());
		driver.switchTo().alert().accept();
		// driver.switchTo().alert().sendKeys("string"); // Great for text fields, e.g.
		// passwords.

	}

}
