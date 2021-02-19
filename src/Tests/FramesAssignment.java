package Tests;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class FramesAssignment {

	public static void main(String[] args) {
		//variables section
		WebDriver driver;
		String testSite = "https://the-internet.herokuapp.com/";
		String nestFrameXpath = "//a[contains(text(),'Nested Frames')]";
		String tgtTextXpath = "//div[@id='content']"; 
		
		//begin main
		// Initialize properties and webdriver
		/*
		 * This uses TestToolkit, a user-defined static class which draws the WebDriver
		 * paths from a properties file and has useful tools including a random number
		 * generator. TestToolkit is a utility file written for use with the Selenium
		 * Java class.
		 */
		TestToolkit.InitProps();
		driver = TestToolkit.InitWebDrv("chrome");
		
		// Access the site under test
		driver.get(testSite);
		driver.findElement(By.xpath(nestFrameXpath)).click();
		
		// Top frame element, which contains the middle frame:
		WebElement topFrameXpath = driver.findElement(By.xpath("//frame[@src='/frame_top']"));
		driver.switchTo().frame(topFrameXpath);
		
		// switch to middle frame and output contained text to console.
		WebElement midFrameXpath = driver.findElement(By.xpath("//frame[@src='/frame_middle']"));
		driver.switchTo().frame(midFrameXpath);
		System.out.println(driver.findElement(By.xpath(tgtTextXpath)).getText());
		
		// cleanup
		driver.quit();

	}//end main

}
