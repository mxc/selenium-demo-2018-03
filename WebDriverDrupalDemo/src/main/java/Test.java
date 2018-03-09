import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;

public class Test {

	public static void main(String[] args) throws InterruptedException {
		System.setProperty("webdriver.gecko.driver", "/home/student/bin/geckodriver");
		WebDriver driver = new FirefoxDriver();
		driver.manage().timeouts().implicitlyWait(3, TimeUnit.SECONDS);
		driver.get("http://127.0.0.1:8080/index.php");
		WebElement link = driver.findElement(By.linkText("Log in"));
		link.click();
		driver.findElement(By.id("edit-name")).sendKeys("admin");
		driver.findElement(By.id("edit-pass")).sendKeys("");
		driver.findElement(By.id("edit-submit")).click();
		Thread.sleep(1000);
		String text = driver.switchTo().alert().getText();
		System.out.println("=====" + text);
		Thread.sleep(1000);
	}

}
