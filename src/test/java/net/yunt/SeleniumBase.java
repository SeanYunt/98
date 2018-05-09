package net.yunt;

import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.extension.ParameterResolver;
import org.junit.platform.runner.JUnitPlatform;
import org.junit.rules.TestName;
import org.junit.runner.RunWith;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.ElementNotVisibleException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.Wait;

import java.io.File;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class SeleniumBase {
	  
  protected static final boolean useVisibleChrome = true;//Boolean.parseBoolean(System.getenv("USE_VISIBLE_CHROME"));
  private static final String OS = System.getProperty("os.name").toLowerCase();
  protected WebDriver driver;
  
  @Before
  public void setUp() throws Exception {
		  if(OS.indexOf("mac") >= 0) createLocalChromeDriverMac();
		  if(OS.indexOf("win") >= 0) createLocalChromeDriverWindows();
  }

  @After
  public void tearDown() throws Exception {
	  try {
		  driver.quit();
	  } catch(Exception e) {
		  
	  }
  }
  
  private void createLocalChromeDriverWindows() {
      System.setProperty("webdriver.chrome.driver", System.getProperty("user.dir") + "\\windows\\chromedriver.exe");
      ChromeOptions chromeOptions = new ChromeOptions();
      if(!useVisibleChrome) {
    	  chromeOptions.addArguments("--headless");
    	  //chromeOptions.addArguments("window-size=1280,1024");
      }
      DesiredCapabilities capabilities = DesiredCapabilities.chrome();
      capabilities.setCapability("marionette", true);
      capabilities.setCapability(ChromeOptions.CAPABILITY, chromeOptions);
      this.driver = new ChromeDriver(capabilities);
      driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);

  }

  private void createLocalChromeDriverMac() {
      System.setProperty("webdriver.chrome.driver", System.getProperty("user.dir") + "/mac/chromedriver");
      ChromeOptions chromeOptions = new ChromeOptions();
      if(!useVisibleChrome) {
    	  chromeOptions.addArguments("--headless");
      }
      DesiredCapabilities capabilities = DesiredCapabilities.chrome();
      capabilities.setCapability("marionette", true);
      capabilities.setCapability(ChromeOptions.CAPABILITY, chromeOptions);
      this.driver = new ChromeDriver(capabilities);
      driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
  }  
  
  public WebDriver get(String url) throws Exception {
      long start = System.currentTimeMillis();
      driver.get(url);
      long end = System.currentTimeMillis();
      return driver;
  }
}