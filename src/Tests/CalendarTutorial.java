package Tests;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class CalendarTutorial {

	public static void main(String[] args) {
		//variables section
		String dynTestURL = "https://rahulshettyacademy.com/dropdownsPractise/"; // The mock flight booking page
		String countryFieldCSS = "input#autosuggest";  // the field where we enter our query.  Alternatively this could be:
		String countryQuery = "un"; // the query, in this case, a partial string
		String countryResultCSS = "li.ui-menu-item";  // a locator for each one of the results returned.
		String roundTripRadioCSS = "#ctl00_mainContent_rbtnl_Trip_1";
		String originMenuCSS = "#ctl00_mainContent_ddl_originStation1_CTXTaction";
		// The following limits the elements to those options under the origin menu:
		String originOptionXpath = "//div[@id='glsctl00_mainContent_ddl_originStation1_CTNR']/table/tbody/tr/td/div/div/div/ul/li";
		String originAirptCode = "(DEL)"; // Airport codes are unique, this is used for the string query.
		// The following limits the elements to those options under the destination menu.
		String destMenuCSS = "#ctl00_mainContent_ddl_destinationStation1_CTXTaction";
		String destOptionXpath = "//div[@id='glsctl00_mainContent_ddl_destinationStation1_CTNR']/table/tbody/tr/td/div/div/div/ul/li";
		String destAirptCode = "(BOM)";
		String departCalendarCSS = "#ctl00_mainContent_view_date1";  // locator for departure calendar dropdown
		String returnCalendarCSS = "#ctl00_mainContent_view_date2";  // locator for departure calendar dropdown
		
		// A calendar has some unique locators that may help.  We can use an XPath for a parent-child traverse.
		String calendarPopupCSS = "#ui-datepicker-div";
		String currentDayXpath = "//a[contains(@class,'ui-state-highlight')]"; // this only occurs with the current date if no other date is selected.
		String selectedDayXpath = "//a[contains(@class,'ui-state-active')]"; // this only occurs with a future date that is selected.
		String dayElementXpath = "//a[contains(@class,'ui-state-default')]"; // this occurs within any date on the calendar, even past dates.
		// Alternatively, we can create CSS selectors:
		String currentDayNotSelectedCSS = ".ui-state-default.ui-state-highlight"; // Note that the space must be filled with a period.
		String currentDaySelectedCSS = ".ui-state-default.ui-state-highlight.ui-state-active";
		WebDriver driver;
				
		//begin main
		// Initialize Webdriver
		TestToolkit.InitProps();  // initialize the properties file
		driver = TestToolkit.InitWebDrv("chrome");  // initialize webdriver to selection
		
		// Get target page
		driver.get(dynTestURL);
		driver.manage().window().maximize();
		// Set the dynamic dropdowns
		driver.findElement(By.cssSelector(countryFieldCSS)).sendKeys(countryQuery);
		// time delay to permit loading
		try {
			Thread.sleep(3000); 
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		TestToolkit.DynMenuSelect(driver, By.cssSelector(countryResultCSS), "United States").click();
		driver.findElement(By.cssSelector(roundTripRadioCSS)).click();
		driver.findElement(By.cssSelector(originMenuCSS)).click();
		// time delay to permit loading
		try {
			Thread.sleep(2000); 
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		TestToolkit.DynMenuSelect(driver, By.xpath(originOptionXpath), originAirptCode).click();
		driver.findElement(By.cssSelector(destMenuCSS)).click();
		// time delay to permit loading
		try {
			Thread.sleep(2000); 
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		TestToolkit.DynMenuSelect(driver, By.xpath(destOptionXpath), destAirptCode).click();
		try {
			Thread.sleep(2000); 
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		// Now for the calendar.
		System.out.println(driver.findElement(By.cssSelector(calendarPopupCSS)).isDisplayed());
		if(!driver.findElement(By.cssSelector(calendarPopupCSS)).isDisplayed()) { // we anticipate that the calendar should automatically appear...
			driver.findElement(By.cssSelector(departCalendarCSS)).click(); // ...but just in case, if it doesn't, we can manually invoke it.
		}
		driver.findElement(By.xpath(currentDayXpath)).click(); // returns the current day number
		
		
		// Again, I like to keep the commonly used local variables in a section, but for the notes, I'll just take advantage of C++ linguistics
		// and declare them a la carte.
		String monthNextCSS = "a[class*='ui-datepicker-next']";
		String monthPrevCSS = "a[class*='ui-datepicker-prev']";
		
		driver.findElement(By.cssSelector(returnCalendarCSS)).isEnabled();
		System.out.println(driver.findElement(By.cssSelector(returnCalendarCSS)).isEnabled());
		
		// what we need is the attribute which changes:
		String retCalendarBlockCSS = "#Div1";  // Within this element there's a style key containing the data: "display: block; opacity: 0.5" when grayed out.
		if(driver.findElement(By.cssSelector(retCalendarBlockCSS)).getAttribute("style").contains("opacity: 0.5")) {
			System.out.println("The element is grayed out (disabled).");
		}
		else {
			System.out.println("The element is enabled.");
		}
		
	}

}
