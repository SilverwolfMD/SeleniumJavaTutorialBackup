package Tests;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.testng.Assert;

import com.sun.xml.internal.ws.policy.privateutil.PolicyUtils.Collections;

public class TableWithStreamsTutorial {

	public static void main(String[] args) {
		//variables section
		WebDriver driver;
		String testSite = "https://rahulshettyacademy.com/seleniumPractise/#/"; 
		String topDealsXpath = "//a[contains(text(),'Top Deals')]";
		String tableXpath = "//table[@class='table table-bordered']"; // gets to the table
		String tableEntryXpath = "//tbody/tr"; // for each entry in the table
		String entryLabelXpath = "//tbody/tr/td[1]"; // accesses the label for each entry in the table.  For each label, it's //tbody/tr[n]/td[1].  It's that n that we may have trouble with, but even though the nonspecific node is not the end node, it still works with a findElements call and refers to all elements.
		String tableHeaderXpath = "//thead/tr/th[1]"; // specific to the label column.
		String pageFirstXpath = "//a[@aria-label='First']"; 
		String pageNextXpath = "//a[@aria-label='Next']";
		String searchFldCSS = "#search-field";
		
		//begin main
		TestToolkit.InitProps();
		driver = TestToolkit.InitWebDrv("chrome");
		driver.get(testSite);
		driver.findElement(By.xpath(topDealsXpath)).click();
		
		// Get the page handles:
		Set<String> windowSet = driver.getWindowHandles(); // [parentID, childID].  Remember that sets aren't necessarily ordered, so we 
														   // need a map of our map.
		Iterator<String> windows = windowSet.iterator(); // assigns indices to the map.
		String parentID = windows.next(); // Even though the set isn't ordered, the window indices, are, so we can get away with this.
		String childID = windows.next();
		
		// Switch to the child window and obtain the necessary information
		driver.switchTo().window(childID); // now Selenium sees what the user sees.
		
		// Attempt an ascending sort on the table. The label column should already be sorted by default.
		if (driver.findElement(By.xpath(tableXpath + tableHeaderXpath)).getAttribute("aria-sort") != "ascending") {
			driver.findElement(By.xpath(tableXpath + tableHeaderXpath)).click();
		}
		
		// Capture the webElements containing the labels into a list.
		List<WebElement> labelElements = driver.findElements(By.xpath(tableXpath + entryLabelXpath));
		
		// Now that we have a list we can do streaming work.  We want to compare the sorted list of labels from the site to a list of the same elements which were sorted in the same way.  If the site sort was successful, then the result of a second sort should be identical.
		List<String> labels = labelElements.stream().map(e -> e.getText()).collect(Collectors.toList()); // list of labels extracted in order from the site
		List<String> sortedLabels = labels.stream().sorted().collect(Collectors.toList()); // list of sorted labels
		Assert.assertTrue(labels.equals(sortedLabels));
		System.out.println("Sort successful."); // since all the assertion does is not halt the program.
		
		if (driver.findElement(By.xpath(tableXpath + tableHeaderXpath)).getAttribute("aria-sort") != "descending") { // normally we wouldn't expect a list to be so static so we would expect "rice" to always be in the last 5 elements, but we can do that here.
			driver.findElement(By.xpath(tableXpath + tableHeaderXpath)).click();
		}
		
		// Now the xpath for tableEntryXpath would be useful if we didn't already have an xPath.  However, the webElements we isolated are Xpath-based, so we can use parent-child traverses to get the row.
		// Get a new set of labels based on the current list:
		labelElements.clear(); // should be overwritten anyway in the next line, so this is a "just in case" line of code.
		labelElements = driver.findElements(By.xpath(tableXpath + entryLabelXpath));

		List<String> prices = labelElements.stream().filter(e -> e.getText().contains("Rice")).map(e -> getPriceVeggie(e)).collect(Collectors.toList());
		
		prices.forEach(p -> System.out.println(p));
		
		// For paginated data, we need access to all of the data, even though Selenium can only see what's on the page.
		if (driver.findElement(By.xpath(tableXpath + tableHeaderXpath)).getAttribute("aria-sort") != "ascending") {
			driver.findElement(By.xpath(tableXpath + tableHeaderXpath)).click();
		} // just to mix things up a little.  
		
		// ideally, we would maximize the list size to the largest size displayable on the page so we have fewer iterations, but for the test site, that would reduce the list to a single displayed page, which defeats the purpose of this exercise.
		// We're going to find cacao, listed as "Chocolate."
		
		// First we need a common start point...at the beginning of the list.
		if(driver.findElement(By.xpath(pageFirstXpath)).getAttribute("aria-disabled") == "false") {
			driver.findElement(By.xpath(pageFirstXpath)).click();
		}
		
		boolean lastLoop = false;
		List<WebElement> tableResults;
		
		do {
			// check the top of each iteration to see whether there will be a following iteration
			if(driver.findElement(By.xpath(pageNextXpath)).getAttribute("aria-disabled") == "true") {
				lastLoop = true;
			}
			
			// Get a new set of labels based on the current list:
			labelElements = driver.findElements(By.xpath(tableXpath + entryLabelXpath));
			
			tableResults = labelElements.stream().filter(e -> e.getText().contains("Chocolate")).collect(Collectors.toList()); // get all elements containing the text "Chocolate." 
			
			if (tableResults.size() > 0) {
				break;
			} // if results are found, stop the loop.  This is not exactly robust considering that results could be numerous enough to take up more than one page, but we're only expecting one result in this test site.  If "Chocolate" was involved in more string entries across more pages, it would be necessary to do an .add() operation to the tableResults list and run this loop until the data has been scanned.
			
			driver.findElement(By.xpath(pageNextXpath)).click();
			
		}while(!lastLoop);
		
		// now tableResults has the WebElement which references what we want.  If we have the WebElement data, that means we can treat it as necessary:
		tableResults.stream().filter(e -> e.getText().contains("Chocolate")).forEach(e -> {
			System.out.print("Item Found: \"" + e.getText() +"\"\t");
			System.out.println(e.findElement(By.xpath("following-sibling::td[1]")).getText());
		});
		
		// for a website-filtered results example, we're going to use a search query "ch" to look for "cheese." In this case, it returns 3 results.  In reality we'd want to anticipate having more than one page of results, and incorporate the pagination code above.
		driver.findElement(By.cssSelector(searchFldCSS)).sendKeys("ch"); // fortunately the site will change its contents without further interaction.
		tableResults = driver.findElements(By.xpath(tableXpath + entryLabelXpath));

		// do all of the displayed results contain the search query string?
		Assert.assertEquals(tableResults.size(), (tableResults.stream().filter(e -> e.getText().toLowerCase().contains("ch")).collect(Collectors.toList())).size());
		
		// does the table contain the desired result?
		Assert.assertTrue((tableResults.stream().filter(e -> e.getText().contains("Cheese")).collect(Collectors.toList())).size() > 0);
		
		// cleanup
		driver.quit();
		
	}//end main
	
	

	private static String getPriceVeggie(WebElement entryElement) {
		//variables section
		
		//begin getPriceVeggie
		
		return entryElement.findElement(By.xpath("following-sibling::td[1]")).getText();
		// a sibling traverse is when both the referenced node and the target node share a parent.  If they don't, then we're back to parent-child traverses. Remember that when referencing from an existing webElement, the leading '/' is implied.
		
	}//end getPriceVeggie

}
