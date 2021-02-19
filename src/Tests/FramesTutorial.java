package Tests;

import java.util.concurrent.TimeUnit;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;

public class FramesTutorial {

	public static void main(String[] args) {
		// variables section
		WebDriver driver;
		String testSite = "https://jqueryui.com/";
		String droppableDemoXpath = "//a[contains(text(),'Droppable')]";
		String dragElementCSS = "#draggable";
		String dropElementCSS = "#droppable";
		Actions digiHand;

		// begin main
		// Initialize from TestToolkit
		TestToolkit.InitProps();
		driver = TestToolkit.InitWebDrv("chrome");
		driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS); // just in case.
		digiHand = new Actions(driver); // we'll need this for the drag and drop later.

		// go to site under test
		driver.get(testSite);
		driver.findElement(By.xpath(droppableDemoXpath)).click();

		/*
		 * A frame is a container which leads to an independent instance of web code,
		 * much like a window within a window. This is a problem here as the draggable
		 * item indicated by dragElementCSS is inside a frame.
		 * 
		 * Fortunately, Selenium has a method in the WebDriver class which makes this
		 * easier.
		 */

		WebElement demoFrame = driver.findElement(By.cssSelector("iframe[class='demo-frame']")); // the frame is located
																									// thusly.
		driver.switchTo().frame(demoFrame); // now Selenium should have access to the frame contents.

		System.out.println(driver.findElement(By.cssSelector(dragElementCSS)).isDisplayed());

		// now we can execute the drag-and-drop with a method from the Actions class.
		WebElement dragFrom = driver.findElement(By.cssSelector(dragElementCSS)); // this is the web element to be
																					// dragged.
		WebElement dropTo = driver.findElement(By.cssSelector(dropElementCSS)); // this is the web element which serves
																				// as a location reference for the drop.

		System.out.println(dropTo.getText());
		digiHand.dragAndDrop(dragFrom, dropTo).build().perform();
		System.out.println(dropTo.getText());

		// Frames can also be identified by an integer index. The main window is not
		// indexed, but the first iframe has index of 0.
		driver.switchTo().parentFrame(); // back to root frame by default
		System.out.println(driver.findElement(By.xpath("//h1[@class='entry-title']")).getText()); // expected:
																									// "Droppable"
		System.out.println(driver.findElements(By.tagName("iframe")).size()); // number of frame elements

		// Cleanup
		driver.quit();

	}// end main

}
