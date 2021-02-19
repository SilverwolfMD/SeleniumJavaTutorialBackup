package Tests;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class CalendarObjTutorial {
	/*
	 * The previous CalendarTutorial covered the Calendar data class, which was used for parsing date information.
	 * This one deals with calendar elements in web pages.
	 */

	public static void main(String[] args) {
		//variables section
		WebDriver driver;
		String testSite = "https://www.path2usa.com/travel-companions";
		String siteContinueXpath = "//a[@class='ad_pi__skip']";
		String travelDateFldCSS = "#travel_date";
		String travelCalendarXpath = "//div[contains(@class,'datepicker-dropdown')]";
		String allDisplayedDaysXpath = "//td[contains(@class,'day')]"; // used for locating all days displayed on the table
		String currentMonthXpath = travelCalendarXpath + "//th[@class='datepicker-switch']"; // current month
		String nextMonthXpath = "//div[@class='datepicker-days']//table[contains(@class,'table-condensed')]//thead//tr//th[@class='next'][contains(text(),'»')]"; // 
		//begin main
		TestToolkit.InitProps();
		driver = TestToolkit.InitWebDrv("chrome");
		driver.get(testSite);
		
		/*
		 * The problem with using a preset Xpath on a calendar is that it addresses a table, not a date.  So we need something
		 * internal to Selenium to find a future date...in this case, August 23.
		 */
		try {
			driver.findElement(By.xpath(siteContinueXpath)).click();
		}
		catch(NoSuchElementException pageClearExc) {
			System.out.println("Loading page cleared");
		}
		
		driver.findElement(By.cssSelector(travelDateFldCSS)).click();
		
		// Advance to next upcoming August if not current month
		while (!driver.findElement(By.xpath(currentMonthXpath)).getText().contains("August")) {
			driver.findElement(By.xpath(nextMonthXpath)).click();
		}
		
		List<WebElement> dates = driver.findElements(By.xpath(allDisplayedDaysXpath));
		
		for(int i = 0; i < dates.size(); ++i) {
			
			String dateStr = dates.get(i).getText();
			if(dateStr.equalsIgnoreCase("23")) {
				dates.get(i).click();
				break;
			}
			
		}

	}//end main

}
