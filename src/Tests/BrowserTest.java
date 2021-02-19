package Tests;

import java.io.IOException;

import org.openqa.selenium.WebDriver;

public class BrowserTest {

	public static void main(String[] args) {
		//Variables section
		TestIdentity person = new TestIdentity(); // public, non-static class, must be instantiated as needed
		WebDriver driver = TestToolkit.InitWebDrv("chrome");
		
		driver.get(TestToolkit.props.getProperty("homeURL")); // google.com URL is stored in the tutorial properties file.
		// the getCurrentUrl() method returns the URL read by the webdriver.
		assert driver.getCurrentUrl() == TestToolkit.props.getProperty("homeURL");
		// getTitle() returns the page title (as it would appear in the tab title). We can use another assertion here.
		assert driver.getTitle() == "Google";
		// getPageSource() returns the page source code code.  This can be useful to format changing locators.  This is useful especially when trying to get the page source where the right click button is disabled.
//		System.out.println(driver.getPageSource());
		
		driver.get("https://www.yahoo.com/");  // this follow-up "get" call with an already open web browser directs the browser to the new URL.
		assert driver.getTitle() == "Yahoo"; // quick test...
		// ...then we can use this method to effectively use the "back" button.
		driver.navigate().back();
		assert driver.getTitle() == "Google"; // this assertion shows that we are indeed "back."
		
		driver.close(); // closes current browser window.
		driver.quit(); // closes all browser windows opened by selenium script.
		
	}

}
