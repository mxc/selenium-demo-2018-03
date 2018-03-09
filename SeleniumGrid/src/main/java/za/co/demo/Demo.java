package za.co.demo;

import java.net.MalformedURLException;
import java.net.URL;

import org.openqa.selenium.Platform;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;

public class Demo {

	public static void main(String[] args) throws MalformedURLException {
		//System.setProperty("webdriver.gecko.driver", "/home/mark/bin/geckodriver");
//		System.setProperty("webdriver.ie.driver","c:/IEDriverServer.exe");
//		System.setProperty("webdriver.ie.driver.port","5556");
//		System.setProperty("webdriver.ie.driver.host","192.168.122.84");
		//System.setProperty("webdriver.gecko.driver", "/home/mark/bin/geckodriver");
        DesiredCapabilities capabilities = DesiredCapabilities.internetExplorer();
        capabilities.setPlatform(Platform.LINUX);
        WebDriver driver = new RemoteWebDriver(new URL("http://localhost:4444/wd/hub"), capabilities);
        driver.get("http://google.com");
        System.out.println(driver.getTitle());
	}

}
