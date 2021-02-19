package Tests;

import java.time.Duration;
import java.util.NoSuchElementException;
import java.util.function.Function;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.Wait;
import org.testng.Assert;

public class FluentWaitTutorial {

	public static void main(String[] args) {
		// variables section
		WebDriver driver;
		String testSite = "https://the-internet.herokuapp.com/dynamic_loading/1";
		String startBtnCSS = "#start button"; // The #start element, with a child element button.
		String loadBarCSS = "#loading"; // this is a loading animation that occurs in this locator.
		String msgStrCSS = "#finish h4"; // with an explicit wait, this element may not be found.
		String msgStr = "Hello World!"; // the expected string at msgStrCss

		// begin main
		// initialize webdriver and open the web page
		TestToolkit.InitProps();
		driver = TestToolkit.InitWebDrv("chrome"); // initialize webdriver using TestToolkit class
		driver.get(testSite);
		Wait<WebDriver> keepWatch = new FluentWait<WebDriver>(driver).withTimeout(Duration.ofSeconds(30))
				.pollingEvery(Duration.ofSeconds(3)).ignoring(NoSuchElementException.class);
		/*
		 * Fluent Wait is a generic class requiring parameterization. This means that we
		 * need to specify that the Wait class is initialized for a WebDriver object,
		 * which is an instance of a FluentWait object tied to driver.
		 * 
		 * .withTimeout(Duration) is a method call which requires another class,
		 * Duration, to establish a time interval over which to check for the web
		 * element.
		 * 
		 * Duration.ofSeconds(n) is used with this method like By.cssSelector(selector)
		 * is used with findElement.
		 * 
		 * .pollingEvery(Duration) specifies an interval (which should be a factor of
		 * the time given to the withTimeout method) where the fluent wait looks for the
		 * web element.
		 * 
		 * .ignoring(exception) instructs the WebDriver to ignore exception throws at
		 * every poll. Here, the exception specified is the expected exception,
		 * NoSuchElementException.
		 */

		driver.findElement(By.cssSelector(startBtnCSS)).click();

		WebElement succMsg = keepWatch.until(new Function<WebDriver, WebElement>() { // a fluent wait will continue to
																						// execute the function in the
																						// code block until either the
																						// element is found or the
																						// timeout interval is reached.
			public WebElement apply(WebDriver driver) {
				if (driver.findElement(By.cssSelector(msgStrCSS)).isDisplayed()) { // the enclosed fluent wait function
																					// performs the action of looking
																					// for the required element as if it
																					// was expected to be there and
																					// visible. Instead of throwing an
																					// exception, it returns null. The
																					// fluent wait object keepWatch will
																					// hold for its polling interval and
																					// try again.
					return driver.findElement(By.cssSelector(msgStrCSS));
				} else {
					return null;
				}
			}
		});

		System.out.println(succMsg.isDisplayed());
		System.out.println(succMsg.getText());
		Assert.assertEquals(succMsg.getText(), msgStr);
		driver.quit();
	}

}
