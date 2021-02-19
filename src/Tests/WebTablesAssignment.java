package Tests;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class WebTablesAssignment {

	public static void main(String[] args) {
		//variables section
		WebDriver driver;
		String testSite = "https://rahulshettyacademy.com/AutomationPractice/";
		String webTableXpath = "//div[@class='left-align']//fieldset//table[@id='product']";
		String webTableRowXpath = "/tbody/tr"; // concatenate to webTableXpath, use a number in brackets for a specific
												// row
		String webTableColXpath = "/td"; // concatenate to webTableRowXpath, use a number in brackets for a specific
											// column

		//begin main
		/*
		 * TestToolkit is a user-defined class which contains general-purpose code
		 * needed to access and concatenate file paths and initialize drivers.
		 */
		// initialize webDriver
		TestToolkit.InitProps(); // initialize the properties file reader
		driver = TestToolkit.InitWebDrv("chrome"); // Initialize the webdriver for the Chrome browser

		// open test page
		driver.get(testSite);

		// number of rows, including header:
		System.out.println("Number of rows: " + driver.findElements(By.xpath(webTableXpath + webTableRowXpath)).size());
		// for a larger program, we might use a string variable and do some string
		// arithmetic to keep the code lines shorter, but for the purposes of this
		// exercise we're just going to cut and paste.

		// number of columns:
		System.out.println("Number of columns: "
				+ driver.findElements(By.xpath(webTableXpath + webTableRowXpath + "[2]" + webTableColXpath)).size());
		// The second row is chosen so that the driver.findElements call finds elements
		// with the td tag. Position 1, the header, has the /th tag.

		// print second row of data (table position 3)
		for (int i = 1; i <= 3; ++i) {
			System.out.print(driver
					.findElement(By.xpath(webTableXpath + webTableRowXpath + "[3]" + webTableColXpath + "[" + i + "]"))
					.getText() + "\t");
		}

		// cleanup
		driver.quit();

	}//end main

}
