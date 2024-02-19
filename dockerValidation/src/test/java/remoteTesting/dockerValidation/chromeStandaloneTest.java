package remoteTesting.dockerValidation;

import java.net.URL;

import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.annotations.Test;

public class chromeStandaloneTest extends baseTest {
	
	@Test
	public void loginToApplication() throws Exception {
		
		System.out.println("Started executing test chromeStandaloneTest");
		
		DesiredCapabilities oDC = new DesiredCapabilities();
		oDC.setBrowserName("chrome");
		
		URL s_url = new URL("http://localhost:4444/wd/hub");
		RemoteWebDriver oDriver = new RemoteWebDriver(s_url,oDC);
		
		oDriver.get("https://demo.guru99.com/selenium/newtours/");
		System.out.println(oDriver.getTitle());
	}
	
	public static void main(String[] args) throws Exception {
		DesiredCapabilities oDC = new DesiredCapabilities();
		oDC.setBrowserName("chrome");
		
		URL s_url = new URL("http://localhost:4444/wd/hub");
		RemoteWebDriver oDriver = new RemoteWebDriver(s_url,oDC);
		
		oDriver.get("https://demo.guru99.com/selenium/newtours/");
		System.out.println(oDriver.getTitle());
	}

}
