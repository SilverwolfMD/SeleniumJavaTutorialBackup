package Tests;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.DesiredCapabilities;

public class SauceLabsTutorial {

	public static void main(String[] args) {
		//variables section
		WebDriver driver;
		DesiredCapabilities caps;
		String sauceUser;
		String sauceAccess;
		String sauceDC;
		
		String sauceURL;
		
		//begin main
		
		// initialize properties
		TestToolkit.InitProps();
		
		// concatenate URL string:
		sauceUser = TestToolkit.props.getProperty("sauceUser");
		sauceAccess = TestToolkit.props.getProperty("sauceKey");
		sauceDC = TestToolkit.props.getProperty("sauceDataCenter");				
		sauceURL = "https://" + sauceUser + ":" + sauceAccess + sauceDC;
		
		// initialize desired capabilities for the remote browser environment
		caps = DesiredCapabilities.chrome();
		caps.setCapability("platformName", "Windows 7");
		caps.setCapability("browserVersion", "69.0");

		// initialize WebDriver
		driver = TestToolkit.InitWebDrv("remote", caps, sauceURL, false); // use a RemoteWebDriver with the capabilities we described above, point it at the service at sauceURL, do not set headless mode.
		
	}//end main

}
