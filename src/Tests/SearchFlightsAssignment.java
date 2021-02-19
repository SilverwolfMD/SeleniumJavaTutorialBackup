package Tests;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.Select;

public class SearchFlightsAssignment {

	public static void main(String[] args) {
		//variables section
		WebDriver driver;
		String testURL = "https://www.cleartrip.com";
		String adultNumCSS = "#Adults";
		String childNumCSS = "#Childrens";
		String departDateCSS = "#DepartDate";
		String currentDateCSS = "a[class*='ui-state-highlight']";
		String moreOptionsCSS = "#MoreOptionsLink";
		String airlineChoiceCSS = "#AirlineAutocomplete";
		String airline = "Delta";
		String searchBtn = "#SearchBtn";
		String errorMsgCss = "#homeErrorMessage";
		
		// open the page under test
		/*
		 * This uses TestToolkit, a user-defined static class which draws the WebDriver paths from a properties file and has useful tools
		 * including a random number generator.  TestToolkit is a utility file written for use with the Selenium Java class.
		 * 
		 * Make sure you opened the window manually at least once and block notifications from this page, because Chrome will throw an alert about 
		 * notifications which driver.switchTo() will not reach.
		 * 
		 */
		TestToolkit.InitProps();
		driver = TestToolkit.InitWebDrv("chrome");
		driver.get(testURL);
		
		// Select a # of adults 
		Select adultNum = new Select(driver.findElement(By.cssSelector(adultNumCSS)));
		adultNum.selectByIndex(TestToolkit.getRandomNumberInRange(0, 8)); // each of the menus has 9 elements, indexed 0 to 8
		
		// Select a # of children
		Select childNum = new Select(driver.findElement(By.cssSelector(childNumCSS)));
		childNum.selectByIndex(TestToolkit.getRandomNumberInRange(0, 8)); // each of the menus has 9 elements, indexed 0 to 8
		
		// Choose the highlighted (today's) date
		driver.findElement(By.cssSelector(departDateCSS)).click();
		driver.findElement(By.cssSelector(currentDateCSS)).click();
		
		// open more options
		driver.findElement(By.cssSelector(moreOptionsCSS)).click();
		
		// Select an airline
		driver.findElement(By.cssSelector(airlineChoiceCSS)).sendKeys(airline);
		
		// Search flights
		driver.findElement(By.cssSelector(searchBtn)).click();
		
		// Print the error message to the console
		System.out.println(driver.findElement(By.cssSelector(errorMsgCss)).getText());
		
		// cleanup
		driver.quit();

	}

}
