package Tests;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;

public class EndToEndToEnd {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		//variables section
		String dynTestURL = "https://rahulshettyacademy.com/dropdownsPractise/"; // The mock flight booking page
		
		// Country of user
		String countryFieldCSS = "input#autosuggest";  // the field where we enter our query.  Alternatively this could be:
		String countryQuery = "un"; // the query, in this case, a partial string
		String countryResultCSS = "li.ui-menu-item";  // a locator for each one of the results returned.
		
		// Trip radio buttons
		//String roundTripRadioCSS = "#ctl00_mainContent_rbtnl_Trip_1";
		
		// Return calendar
		String retCalendarBlockCSS = "#Div1";
		
		String seniorDiscountCheckCSS = "input[id*='SeniorCitizenDiscount']";
		
		String passengerMenuCSS = "#divpaxinfo";
		String addAdultBtnCSS = "#hrefIncAdt";
		String adultPsngrsCSS = "#spanAudlt";
		String closePassengerMnuCSS = "#btnclosepaxoption";
		
		String currencyDropDownCSS = "#ctl00_mainContent_DropDownListCurrency";
		Select currencyDropdown;
		String currencyChoice = "USD";
		
		String originMenuCSS = "#ctl00_mainContent_ddl_originStation1_CTXTaction";
		// The following limits the elements to those options under the origin menu:
		String originOptionXpath = "//div[@id='glsctl00_mainContent_ddl_originStation1_CTNR']/table/tbody/tr/td/div/div/div/ul/li";
		String originAirptCode = "(DEL)"; // Airport codes are unique, this is used for the string query.
		// The following limits the elements to those options under the destination menu.
		
		String destMenuCSS = "#ctl00_mainContent_ddl_destinationStation1_CTXTaction";
		String destOptionXpath = "//div[@id='glsctl00_mainContent_ddl_destinationStation1_CTNR']/table/tbody/tr/td/div/div/div/ul/li";
		String destAirptCode = "(BOM)";
		
		String departCalendarCSS = "#ctl00_mainContent_view_date1";  // locator for departure calendar dropdown
		//String returnCalendarCSS = "#ctl00_mainContent_view_date2";  // locator for departure calendar dropdown
		
		// A calendar has some unique locators that may help.  We can use an XPath for a parent-child traverse.
		String calendarPopupCSS = "#ui-datepicker-div";
		String currentDayXpath = "//a[contains(@class,'ui-state-highlight')]"; // this only occurs with the current date if no other date is selected.
		//String selectedDayXpath = "//a[contains(@class,'ui-state-active')]"; // this only occurs with a future date that is selected.
		//String dayElementXpath = "//a[contains(@class,'ui-state-default')]"; // this occurs within any date on the calendar, even past dates.
		
		String searchBtnCSS = "#ctl00_mainContent_btn_FindFlights";

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
		
		if(driver.findElement(By.cssSelector(retCalendarBlockCSS)).getAttribute("style").contains("opacity: 0.5")) {
			System.out.println("The element is grayed out (disabled).");
		}
		else {
			System.out.println("The element is enabled.");
		}
		Assert.assertTrue(driver.findElement(By.cssSelector(retCalendarBlockCSS)).getAttribute("style").contains("opacity: 0.5"));

		driver.findElement(By.cssSelector(seniorDiscountCheckCSS)).click();
		
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
			e.printStackTrace();
		} 
		// Now for the calendar.
		System.out.println(driver.findElement(By.cssSelector(calendarPopupCSS)).isDisplayed());
		if(!driver.findElement(By.cssSelector(calendarPopupCSS)).isDisplayed()) { // we anticipate that the calendar should automatically appear...
			driver.findElement(By.cssSelector(departCalendarCSS)).click(); // ...but just in case, if it doesn't, we can manually invoke it.
		}
		driver.findElement(By.xpath(currentDayXpath)).click(); // use the current day, we're leaving NOW
		
		// Passengers
		driver.findElement(By.cssSelector(passengerMenuCSS)).click();
		try {
			Thread.sleep(2000); 
		} catch (InterruptedException e) {
			e.printStackTrace();
		} 
		for(int i = 0; i < 4; ++i) {
			driver.findElement(By.cssSelector(addAdultBtnCSS)).click();
		}
		Assert.assertEquals(driver.findElement(By.cssSelector(adultPsngrsCSS)).getText(), "5");
		driver.findElement(By.cssSelector(closePassengerMnuCSS)).click();
		System.out.println(driver.findElement(By.cssSelector(passengerMenuCSS)).getText());
		Assert.assertEquals(driver.findElement(By.cssSelector(passengerMenuCSS)).getText(), "5 Adult");
		
		// Currency choice
		currencyDropdown = new Select(driver.findElement(By.cssSelector(currencyDropDownCSS)));
		currencyDropdown.selectByValue(currencyChoice);
		Assert.assertEquals(currencyDropdown.getFirstSelectedOption().getText(), currencyChoice);
		
		driver.findElement(By.cssSelector(searchBtnCSS)).click();
		
		try {
			Thread.sleep(2000); 
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		driver.quit();
		
	}

}
