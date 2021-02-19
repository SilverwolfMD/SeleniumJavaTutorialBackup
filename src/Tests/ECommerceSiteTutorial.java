package Tests;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class ECommerceSiteTutorial {

	public static void main(String[] args) {
		// variables section
		WebDriver driver;
		String testSite = "https://www.rahulshettyacademy.com/seleniumPractise////";
		String discountCode = "rahulshettyacademy";

		String searchFldCSS = "input.search-keyword";
		String productBtnXpath = "//div[@class='products']//button"; // For getting all buttons within the products
																		// class
		String productNameXpath = "parent::div/parent::div/h4"; 	// Concatenated to button xpath or used with WebElement to find the name
		String productPriceXpath = "parent::div/parent::div//p"; // concatenated with button xpath or used with WebElement to find the price
		String productQtyXpath = "parent::div/parent::div//input";
		String qtyDownXpath = "parent::div/parent::div//a[@class='decrement']";
		String qtyUpXpath = "parent::div/parent::div//a[@class='increment']";
		// While these were quickly obtained by a quick trip through the developer
		// console (didn't even need to use ChroPath!)
		// there's another way to handle this since we're using an Xpath.
		String cartToggleCSS = "a.cart-icon"; // alternatively, there's an alt-type which allows accession, so alt.Cart
												// would work.
		String cartPreviewXpath = "//div[@class='cart-preview active']";  // Check for visibility on an explicit wait.
		String checkoutXpathAppend = "//button"; // Alternatively, we can use the button text: //button[text()='PROCEED
													// TO CHECKOUT']
		// is an acceptable Xpath.
		String cartURL = "https://www.rahulshettyacademy.com/seleniumPractise////cart";
		String cartTableXpath = "//table[@id='productCartTables']/tbody";
		// Cart table elements: //table[@id='productCartTables']/tbody/tr[n]/td[m]/p
		// where n is the table row and m is the
		// column.

		String cartPromoFldCSS = "input.promoCode";
		String cartPromoApplyCSS = "button.promoBtn";
		String promoSuccessCSS = "span.promoInfo";
		String promoSuccessStr = "Code applied ..!";
		String subTotalAmtCSS = "span.totAmt";
		String totalAmtCSS = "span.discountAmt";

		// begin main
		// Initialize properties and webdriver
		TestToolkit.InitProps();
		driver = TestToolkit.InitWebDrv("chrome");
		driver.get(testSite);
		driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
		WebDriverWait holdIt = new WebDriverWait(driver, 3);

		// Site data acquisition
		List<WebElement> prodBtns = driver.findElements(By.xpath(productBtnXpath)); // obtain list of all web elements
																					// which have a product button

		for (WebElement btn : prodBtns) {

			System.out.print(btn.findElement(By.xpath(productNameXpath)).getText() + "\t");
			System.out.print(Integer
					.parseInt(btn.findElement(By.xpath(productQtyXpath)).getAttribute("value")) + "\t");
			System.out.println(Integer.parseInt(btn.findElement(By.xpath(productPriceXpath)).getText()));
			// this should return lists which correspond to each other by index.

		} // end for(btn)
		
		BuyProduct(prodBtns, "Cucumber", 3);
		BuyProduct(prodBtns, "Beetroot", 1);
		BuyProduct(prodBtns, "Brocolli", 1);
		
		driver.findElement(By.cssSelector(cartToggleCSS)).click();
		driver.findElement(By.xpath(cartPreviewXpath + checkoutXpathAppend)).click();
		driver.findElement(By.cssSelector(cartPromoFldCSS)).sendKeys(discountCode);
		driver.findElement(By.cssSelector(cartPromoApplyCSS)).click();
		holdIt.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(promoSuccessCSS)));
		
		System.out.println(driver.findElement(By.cssSelector(promoSuccessCSS)).getText());

		// driver.quit();

	}// end main

	static void BuyProduct(List<WebElement> stock, String item, int qty) {
		
		//variables section
		// these strings are re-declared because of scope.
		String productNameXpath = "parent::div/parent::div/h4"; 	
		String qtyUpXpath = "parent::div/parent::div//a[@class='increment']";
		
		//begin BuyProduct
		for (WebElement product : stock) {
			
			if(product.findElement(By.xpath(productNameXpath)).getText().contains(item)) {
				if(qty > 1) {
					for(int i = 2; i <= qty; ++i) { // executes loop for quantities of 2 or greater
						product.findElement(By.xpath(qtyUpXpath)).click();
					}
				}
				product.click(); // add to cart
			}//end if

		}//end for

	}//end BuyProduct

}//end ECommerceSiteTutorial
