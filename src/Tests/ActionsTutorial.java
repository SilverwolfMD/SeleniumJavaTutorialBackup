package Tests;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;

public class ActionsTutorial {
	
	public static void main(String[] args) {
		
		//variables section
		String testSiteURL = "https://www.amazon.com";
		String acctDropDownXPath = "//span[@id='nav-link-accountList-nav-line-1']";
		
		//begin main
		// initialize
		TestToolkit.InitProps();
		WebDriver driver = TestToolkit.InitWebDrv("chrome");
		Actions digiHand = new Actions(driver); // requires org.openqa.selenium.interactions.Actions, instantiated with webDriver
		
		// open web page
		driver.get(testSiteURL);
		digiHand.moveToElement(driver.findElement(By.xpath(acctDropDownXPath))).build().perform();  
		/* 
		 * Tells the Webdriver to move the mouse to the target element, not just interact with it directly. However, in order to make 
		 * something that works through the webdriver, it needs to be built (compiled), then performed (run).
		 * 
		 * Actions performed through the Actions class can be composited, i.e., multiple actions can be set in sequence between the 
		 * Actions object (here, digiHand) and the build and perform methods.
		 */
		WebElement acctDropDownElement = driver.findElement(By.xpath(acctDropDownXPath)); // simplifies future calls to Actions
		
		// for accession of the search box:
		driver.manage().window().maximize();
		WebElement searchFieldElement = driver.findElement(By.xpath("//input[@id='twotabsearchtextbox']")); // yes, we're hard-coding this in here, remember that this is still valid in Java.
		digiHand.moveToElement(searchFieldElement).click().keyDown(Keys.SHIFT).sendKeys("hello").build().perform(); // composite action
		/*
		 * So, we break this composite action down as follows:
		 * digiHand: The actions object receiving, building, and executing the command chain in order.
		 * .moveToElement(searchFieldElement): Move the mouse to this element.
		 * .click(): Left mouse button down and release.
		 * .keyDown(Keys.SHIFT): Either shift key (left or right) is depressed. Some web apps are more specific, and the Keys class has
		 * 	left vs. right shift options.
		 * .sendKeys("hello"): type the key sequence in the argument. With the keyDown method executed earlier, this should show up as
		 * 	HELLO.
		 * .build(): build the instructions into an action.
		 * .perform(); execute the action.
		 */
		
		// contextual click
		digiHand.moveToElement(acctDropDownElement).contextClick().build().perform();
		
		try {
			Thread.sleep(10000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		driver.quit();
		
	}

}
