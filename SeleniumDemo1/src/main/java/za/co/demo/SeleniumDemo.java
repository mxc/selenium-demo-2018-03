package za.co.demo;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;

public class SeleniumDemo {

	public static void main(String[] args) throws InterruptedException {

		
		System.setProperty("webdriver.gecko.driver", "/home/student/bin/geckodriver");
		WebDriver driver = new FirefoxDriver();
		driver.manage().timeouts().implicitlyWait(3, TimeUnit.SECONDS);
		
		//Navigate to google web site
		driver.get("http://www.google.com");

		
		
		
		WebElement elm = driver.findElement(By.name("q"));
		
		//Submit google search for text
		elm.sendKeys("jumping bean");
		elm.submit();

		// Query page results for stats
		WebElement stat = driver.findElement(By.id("resultStats"));
		String text = stat.getText();

		// Get links to additional pages
		List<WebElement> links = driver.findElements(By.cssSelector("#navcnt a"));
		List<String> stringURL = new ArrayList<String>();
		for(WebElement link : links) {
			stringURL.add(link.getAttribute("href"));
		}

		// String manipulation
		int start = text.indexOf(" ");
		int end = text.indexOf("results", start);
		String result = text.substring(start, end);
		System.out.println("The resutls are: " + result);

		// iterate
		int maxCount = 0;
		Iterator<String> itr = stringURL.iterator();
		itr.next();
		do {
			List<WebElement> elms = null;
			elms = driver.findElements(By.className("r"));
			if (elms.isEmpty()) {
				System.out.println("Unexpected error");
			}
			for (WebElement emlr : elms) {
				try{
					WebElement anchor = emlr.findElement(By.tagName("a"));
					System.out.println(anchor.getAttribute("href"));
				}catch(NoSuchElementException ex) {
					System.out.println("Missing a tag" + ex.getMessage());
				}
			}
			String link = itr.next();
			Thread.sleep(2000);
			driver.navigate().to(link);
			maxCount++;
			System.out.println("++++++++++++++++++++++++++++++++++++");
		} while (itr.hasNext() && maxCount<10);
		
		driver.quit();
	}

}
