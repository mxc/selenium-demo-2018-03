package za.co.demo.test;

import static org.junit.Assert.*;

import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.Capabilities;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.interactions.Action;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Wait;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.net.URL;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class DrupalTest {

	private static WebDriver driver;

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		//System.setProperty("webdriver.gecko.driver", "/home/student/bin/geckodriver");
		Capabilities cap = DesiredCapabilities.edge();
		driver = new RemoteWebDriver(new URL("http://localhost:4444/wd/hub"),cap);
		driver.manage().timeouts().implicitlyWait(3, TimeUnit.SECONDS);
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
		driver.quit();
	}

	@Before
	public void setUp() {
		driver.get("http://192.168.122.1:8080");
		WebElement login = driver.findElement(By.linkText("Log in"));
		login.click();
	}

	@Test
	public void openURLTest() {
		driver.get("http://192.168.122.1:8080");
		Assert.assertTrue(driver.getTitle().contains("Welcome to DrupalDemo"));
	}

	@Test
	public void logisuccesTest() {
		WebElement username = driver.findElement(By.id("edit-name"));
		username.sendKeys("admin");
		WebElement pass = driver.findElement(By.id("edit-pass"));
		pass.sendKeys("admin");
		WebElement form = driver.findElement(By.id("user-login-form"));
		form.submit();
		// WebElement logout = driver.findElement(By.linkText("Log out"));
		// Assert.assertTrue("Log out link not found after
		// authentication",logout!=null);
		List<WebElement> logout = driver.findElements(By.linkText("Log out"));

		Assert.assertTrue("Log out link not found after authentication", !logout.isEmpty());
		// driver.get("http://localhost:8080/user/logout");
		/*
		 * Actions actions = new Actions(driver); // Element must be in viewport to be
		 * clickable // Use actions to scroll element into view
		 * actions.moveToElement(logout.get(1)).build().perform(); for(WebElement link
		 * :logout) { System.out.println(link.isDisplayed()); } //logout.get(1).click();
		 * 
		 * // Example of using explicit waits on particular elements. WebDriverWait wait
		 * = new WebDriverWait(driver,1);
		 * wait.until(ExpectedConditions.elementToBeClickable(logout.get(1)));
		 */
		logout.get(1).click();
	}

	@Test
	public void loginFailureTest() {
		WebElement username = driver.findElement(By.id("edit-name"));
		username.sendKeys("unknown");
		WebElement pass = driver.findElement(By.id("edit-pass"));
		pass.sendKeys("admin");
		WebElement form = driver.findElement(By.id("user-login-form"));
		form.submit();
		List<WebElement> errors = driver.findElements(By.className("messages--error"));
		Assert.assertTrue("No errors found on failed login", !errors.isEmpty());
		WebElement error = errors.get(0);
		Assert.assertTrue(error.getText().contains("Unrecognized username or password."));
	}

	@Test
	public void blankPasswordLoginTest() {
		WebElement username = driver.findElement(By.id("edit-name"));
		username.sendKeys("unknown");
		WebElement pass = driver.findElement(By.id("edit-pass"));
		pass.sendKeys("");
		WebElement form = driver.findElement(By.id("user-login-form"));
		form.submit();
		// Thread.sleep(10000);
		List<WebElement> errors = driver.findElements(By.className("messages--error"));
		Assert.assertTrue("No errors found on failed login", !errors.isEmpty());
		WebElement error = errors.get(0);
		Assert.assertTrue(error.getText().contains("Password field is required"));
	}

	@Test
	public void blankUserNameLoginTest() {
		WebElement username = driver.findElement(By.id("edit-name"));
		username.sendKeys("");
		WebElement pass = driver.findElement(By.id("edit-pass"));
		pass.sendKeys("pass");
		WebElement form = driver.findElement(By.id("user-login-form"));
		form.submit();
		List<WebElement> errors = driver.findElements(By.className("messages--error"));
		Assert.assertTrue("No errors found on failed login", !errors.isEmpty());
		WebElement error = errors.get(0);
		Assert.assertTrue(error.getText().contains("Username field is required"));
	}

	@Test
	public void longUserNamePasswordLoginTest() {
		StringBuilder strbuf = new StringBuilder("aaaaa");
		for (int i = 0; i < 100; i++) {
			strbuf.append("aaaaaaaaaaaaaaaaaaaaaa");
		}
		WebElement username = driver.findElement(By.id("edit-name"));
		username.sendKeys(strbuf.toString());
		WebElement pass = driver.findElement(By.id("edit-pass"));
		pass.sendKeys(strbuf.toString());
		WebElement form = driver.findElement(By.id("user-login-form"));
		form.submit();
		List<WebElement> errors = driver.findElements(By.className("messages--error"));
		Assert.assertTrue("No errors found on failed login", !errors.isEmpty());
		WebElement error = errors.get(0);
		Assert.assertTrue(error.getText().contains("Unrecognized username or password."));
	}

	@Test
	public void addNewUserTest() throws InterruptedException {
		login();
		WebElement people = driver.findElement(By.linkText("People"));
		Assert.assertTrue(people.getAttribute("href").contains("/admin/people"));
		if (!people.isDisplayed()) {
			Actions actions = new Actions(driver);
			WebElement manageLink = driver.findElement(By.id("toolbar-item-administration"));
			Assert.assertTrue(manageLink.getText().equals("Manage"));
			actions.click(manageLink).build().perform();
		}
		people.click();
		driver.findElement(By.linkText("Add user")).click();
		driver.findElement(By.id("edit-name")).sendKeys("test1");
		driver.findElement(By.id("edit-pass-pass1")).sendKeys("pass");
		driver.findElement(By.id("edit-pass-pass2")).sendKeys("pass");
		driver.findElement(By.id("edit-submit")).click();
		Assert.assertTrue(driver.findElement(By.className("messages--status"))
				.getText().contains("Created a new user account"));
		driver.get("http://localhost:8080/admin/people");
		String url = driver.findElement(By.linkText("test1")).getAttribute("href");
		driver.get(url+"/cancel");
		driver.findElement(By.id("edit-user-cancel-method-user-cancel-delete")).click();
		driver.findElement(By.id("edit-submit")).click();	
		logout();
	}

	private void login() {
		WebElement username = driver.findElement(By.id("edit-name"));
		username.sendKeys("admin");
		WebElement pass = driver.findElement(By.id("edit-pass"));
		pass.sendKeys("admin");
		WebElement form = driver.findElement(By.id("user-login-form"));
		form.submit();
	}

	private void logout() {
		driver.get("http://localhost:8080/user/logout");
	}

}
