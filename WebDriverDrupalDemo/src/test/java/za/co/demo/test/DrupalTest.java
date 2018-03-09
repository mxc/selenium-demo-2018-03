package za.co.demo.test;

import static org.junit.Assert.*;

import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.JavascriptExecutor;

import org.junit.Assert;

public class DrupalTest {

	private static WebDriver driver;

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		System.setProperty("webdriver.gecko.driver", "/home/student/bin/geckodriver");
		driver = new FirefoxDriver();
		driver.manage().timeouts().implicitlyWait(3, TimeUnit.SECONDS);
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
		driver.close();
	}

	@Test
	public void loginSuccessTest() throws InterruptedException {
		driver.get("http://127.0.0.1:8080/index.php");
		WebElement link = driver.findElement(By.linkText("Log in"));
		link.click();
		driver.findElement(By.id("edit-name")).sendKeys("admin");
		driver.findElement(By.id("edit-pass")).sendKeys("admin");
		driver.findElement(By.id("user-login-form")).submit();
		Actions action = new Actions(driver);
		WebElement elm = driver.findElement(By.linkText("Log out"));
		action.moveToElement(elm).click().build();
		//driver.get(elm.getAttribute("href"));
	}

	@Test
	public void loginFailureTest() {
		driver.get("http://127.0.0.1:8080/index.php");
		WebElement link = driver.findElement(By.linkText("Log in"));
		link.click();
		driver.findElement(By.id("edit-name")).sendKeys("admin");
		driver.findElement(By.id("edit-pass")).sendKeys("fail");
		driver.findElement(By.id("user-login-form")).submit();
		String result = driver.findElement(By.cssSelector(".messages--error > div")).getText();
		Assert.assertTrue(result.indexOf("Unrecognized username or password.") != -1);
	}

	@Test
	public void blankPasswordTest() throws InterruptedException {
		driver.get("http://127.0.0.1:8080/index.php");
		WebElement link = driver.findElement(By.linkText("Log in"));
		Thread.sleep(1000);
		link.click();
		driver.findElement(By.id("edit-name")).sendKeys("admin");
		driver.findElement(By.id("edit-pass")).sendKeys("");
		// driver.findElement(By.id("user-login-form")).submit();
		Thread.sleep(1000);
		String text = driver.switchTo().alert().getText();
		System.out.println("=====" + text);
		Thread.sleep(1000);

	}

}
