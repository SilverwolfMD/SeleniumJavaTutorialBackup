package Tests;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Properties;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
// to be removed to a different class based on preference of logging methods
import org.apache.commons.io.FileUtils;
import org.apache.logging.log4j.core.Logger;
import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;

public class TestToolkit {
	
	/*
	 * By Christopher T. Rosenbaum, MD
	 * For Qualitest Group
	 * 
	 * The Test Toolkit is a cumulative practical toolkit compiled from lessons in Selenium Java which includes commonly used code
	 * and methods in an easy-to-access class.  These methods are universal to Selenium Java and commonly used methods by Maven. As methods
	 * are public and static, they are usable by any class in the same package, and allow the user to extend inheritance from other 
	 * classes as desired.
	 * 
	 */
	
		
	//global variables
	public static Properties props = new Properties(); // properties file object
	public FileInputStream readStream; // I/O stream object which negotiates the link between the .properties file and the properties file object
	public static Logger log1; // even though this is global, we want it persistent through the runtime.
	public static JavascriptExecutor js; // used for page manipulation
	// time enumerators: soon to be deprecated in favor of a date-to-ordinal method.
	public enum Months {JANUARY, FEBRUARY, MARCH, APRIL, MAY, JUNE, JULY, AUGUST, SEPTEMBER, OCTOBER, NOVEMBER, DECEMBER};
	public enum ShortMonths {JAN, FEB, MAR, APR, MAY, JUN, JUL, AUG, SEP, OCT, NOV, DEC};
	public enum Browser {CHROME, FIREFOX, MSIE, EDGE, SAFARI, OPERA, REMOTE, PROP}; // allows for switch selection of a web browser, or PROP for use of the properties file
	public static WebDriver gDriver;  // Instantiated if the user requests one global webdriver
	
	public static void InitProps () { // initializes the properties file
		
		//variables section
		String propsFilePath = "./"; // file path to your properties file here
		String propsFileName = "SeleniumTutorialProps.properties";  // file name may be changed prior to use
		
		
		//begin InitProps
		FileInputStream readStream = null;  // initialized here for exception handling
		try {
			readStream = new FileInputStream(propsFilePath + propsFileName); // Initialize the file input stream's directory
			props.load(readStream);
		}
		// Do not include any working code between the try and catch blocks, lest the code produce a fatal error or throw an uncaught exception.
		// You can include multiple catch blocks for specific errors that may occur in your catch block.
		catch (FileNotFoundException expBadPath) {
			// In case the file path does not lead to the properties file
			System.out.println("Properties file " + propsFileName + " not found in directory " + propsFilePath);
			expBadPath.printStackTrace();
		} 
		catch (IOException expBadFile) {
			// This exception occurs if the file was in the correct location, but could not be read.
			System.out.println("Properties file " + propsFileName + " cannot be read.");
			expBadFile.printStackTrace();
		}
		
	} //end InitProps

	
public static WebDriver InitWebDrv (String choice, DesiredCapabilities c, String remURL, boolean isHeadless) { // function overload of InitWebDrv to allow for web driver options. Must be updated to include declarations for other browser options profiles.
		
		//variables section
		String setPropPath; // strings for web driver key and path parameters, concatenated based on user choice
		Browser exeChoice = Browser.valueOf(choice.toUpperCase()); // parses the browser choice to enumeration
		
		//begin webDriverSetup
		// Determine whether the user wants to use the properties file preset
		if(exeChoice == Browser.PROP) {
			exeChoice = Browser.valueOf(props.getProperty("browserChoice").toUpperCase()); // use the properties file preset
		}
		
		switch (exeChoice){
		case CHROME:{
			setPropPath = props.getProperty("driversDir") + "\\chromedriver.exe";
			System.setProperty("webdriver.chrome.driver", setPropPath);
			if(c != null || isHeadless) { // if either of the modification parameters is implemented
				ChromeOptions chromeOpts = new ChromeOptions();
				if(c != null) {
					chromeOpts.merge(c);
				}
				if(isHeadless) {
					chromeOpts.addArguments("headless");
				}
				return new ChromeDriver(chromeOpts);
			}
			else {
				return new ChromeDriver();
			}
		}
		case FIREFOX:{
			setPropPath = props.getProperty("driversDir") + "\\geckodriver.exe"; // this reads the file path from the properties file and concatenates the filename for the Firefox Gecko driver, and assembles the path string.
			System.setProperty("webdriver.gecko.driver", setPropPath); // This sets the java system property that connects the key name for the system property for the webdriver to a string that refers to the path for that webdriver, say "C:\\Users\\Username\\Documents\\Webdriver".  Once this is set...
			return new FirefoxDriver();  // ..then we can just use this assignment to instantiate and initialize the driver object.
		}
		case EDGE:{
			setPropPath = props.getProperty("driversDir") + "\\MicrosoftWebDriver.exe";
			System.setProperty("webdriver.edge.driver", setPropPath); 
			return new EdgeDriver();
		}
		case MSIE:{
			setPropPath = props.getProperty("driversDir") + "\\IEDriverServer.exe"; // execute this code block.
			System.setProperty("webdriver.ie.driver", setPropPath);
			return new InternetExplorerDriver();
		}
		case REMOTE:{
			if(c == null || remURL == null) {
				throw new IllegalArgumentException("One or more required parameters (desired capabilities, remote system URL, isHeadless) is missing.");
			}
			else {
				try {
					return new RemoteWebDriver(new URL(remURL), c);
				} catch (MalformedURLException e) {
					// TODO Auto-generated catch block
					System.out.println("Unable to parse string \"" + remURL + "\" to URL.");
					e.printStackTrace();
				}
			}
		}
		// Other web drivers will be programmed in later, but for now there will be an exception throw.
		default:{
			throw new IllegalArgumentException("No valid browser choice in argument or properties file.");
		}
		}//end switch(exeChoice)
			
		//log1.info("driver initialized for " + props.getProperty("browserChoice"));
	} //end InitWebDrv

	public static WebDriver InitWebDrv (String choice, DesiredCapabilities c, boolean isHeadless) {
		
		return InitWebDrv(choice, c, null, isHeadless);
		
	}
	
	public static WebDriver InitWebDrv (String choice) {
		
		return InitWebDrv(choice, null, null, false);
		
	}
	
	public static void InitGlobalDrv(String choice) { // initializes the public static webDriver for common use, instead of using InitWebDrv to instantiate a WebDriver object local to a class.
		
		gDriver = InitWebDrv(choice); // calls webdriver initializer function overload without DesiredCapabilities parameter.
		
	}
	
	public static void InitGlobalDrv(String choice, DesiredCapabilities c, String remURL, boolean isHeadless) { // initializes the public static webDriver for common use, instead of using InitWebDrv to instantiate a WebDriver object local to a class.  Function overload for DesiredCapabilities.
		
		gDriver = InitWebDrv(choice, c, remURL, isHeadless);
		
	}

	//@BeforeSuite
//	public static void Initializer () throws IOException { // opens the properties file and initializes the webdriver for use by other scripts.
//		
//		InitProps();
//		InitWebDrv(Browser.PROP.toString());
//		
//	} //end Initializer
	// Come on, it's two lines of code.

	public static int getRandomNumberInRange(int min, int max) {

		if (min >= max) {
			throw new IllegalArgumentException("max must be greater than min");
		}

		return (int)(Math.random() * ((max - min) + 1)) + min;
	}
	
	
	public String ExcludeChar (String rawStr, char exclusion) {
		
		//variables section
		String proc = new String();
		
		//begin ExcludeChar
		for (int i = 0; i < rawStr.length(); ++i) {
			if (rawStr.charAt(i) != exclusion) {
				proc += rawStr.charAt(i); // any character except for the exclusion is added to the new string
			}
		}
		return proc;
		
	}//end ExcludeChar

	public static String RandString (int length, boolean alphaNumOnly) { // generates a random text string based on the ASCII table.
		
		//variables section
		String garble = new String();
		
		//begin RandString
		for (int i = 0; i < length; ++i) {
				if (alphaNumOnly) {
					switch (getRandomNumberInRange(0,2)) {
					case 0:{
						garble += String.valueOf((char)getRandomNumberInRange(48,57)); // numerals
						break;
					}
					case 1:{
						garble += String.valueOf((char)getRandomNumberInRange(65,90)); // uppercase letters
						break;
					}
					case 2:{
						garble += String.valueOf((char)getRandomNumberInRange(97,122)); // lowercase letters
						break;
					}
					}
				}
				else {
					garble += String.valueOf((char) getRandomNumberInRange(33,126)); // all printable characters in the ASCII table
				}
			}//end for loop
		return garble;
		
	}//end RandString
	
	public static WebElement DynMenuSelect (WebDriver drv, By locator, String query) { // uses a partial or complete string match to locate a webElement.  Only maintains a list long enough to run, reduced memory overhead.
		
		//variables section
		List<WebElement> eleList = drv.findElements(locator);
		
		//begin DynMenuSelect
		for(WebElement element: eleList) {
			if(element.getText().contains(query)) { 
				return element;
			}
		}
		return null; // if the option isn't in the list
		
	}//end DynMenuSelect
	
	public static String TimeStamp () { // generates common time stamp in a DOS-compatible format
		
		Date d = new Date();
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-M-d_k-m-s");
		return dateFormat.format(d);
		
	}
	
	public static void ScreenShot(WebDriver drv, String filePath) { // generic screenshot system, sets up casting and writes file
		
		File shotImg = ((TakesScreenshot)drv).getScreenshotAs(OutputType.FILE);
		try {
			FileUtils.copyFile(shotImg, new File(filePath)); // the '.' refers to the current directory, in this case, the SeleniumJavaTutorial directory.
		} catch (IOException e) {
			// TODO Auto-generated catch block
			System.out.println("Unable to store screenshot file.");
			e.printStackTrace();
		}
		
	}
	
	// Extents reporting methods
	public static ExtentReports GetReportObject(String reportName, String docTitle, String testerID) { // function overload to call GetReportObject with default path of project folder\report folder.
		
		return GetReportObject(reportName, docTitle, testerID, System.getProperty("user.dir") + "\\reports\\index.html");
		
	}
	
	public static ExtentReports GetReportObject(String testerID) { // function overload to call GetReportObject with default path of project folder\report folder, and default labels for the report and document titles.
		
		return GetReportObject("Test Report", "Test Results", testerID, System.getProperty("user.dir") + "\\reports\\index.html");
		
	}
	
	public static ExtentReports GetReportObject(String reportName, String docTitle, String testerID, String path) { // this is written as a utility function which may be called externally, for example, from the Test Toolkit.
		
		//variables section
		ExtentSparkReporter cronkite = new ExtentSparkReporter(path);  // instantiate spark reporter for extent report file generation
		ExtentReports extentRep = new ExtentReports(); // this needs to be instantiated in order to accept the ExtentSparkReporter
		
		//begin getReportObject
		cronkite.config().setReportName(reportName);
		cronkite.config().setDocumentTitle(docTitle);
		extentRep.attachReporter(cronkite);
		extentRep.setSystemInfo("Tester", testerID);
		return extentRep;
		
	}//end getReportObject
		
}
