
import org.openqa.selenium.chrome.ChromeDriver
import org.openqa.selenium.chrome.ChromeOptions
import org.openqa.selenium.firefox.FirefoxDriver
import org.openqa.selenium.remote.DesiredCapabilities
import org.openqa.selenium.remote.RemoteWebDriver

if (System.getProperty("test.env") == null) {
  test_environment = "local"
} else {
  test_environment = System.getProperty("test.env")
}

baseUrl = "https://droptoken.herokuapp.com/"

// Run appium locally by default until we get proper integration with some provider
if (["iOS", "Android"].contains(System.getProperty("geb.env"))) {
  if (System.getProperty("geb.selenium") == "remote") {
    //Insert remote appium integration string
  }
} else {
  //default browser handler
  driver = { new ChromeDriver() }
}

environments {
  chrome {
    ChromeOptions options = new ChromeOptions()
    Map<String, Object> prefs = new HashMap<String, Object>()
    prefs.put("credentials_enable_service", false)
    prefs.put("profile.password_manager_enabled", false)
    options.setExperimentalOption("prefs", prefs);
    options.addArguments("use-fake-device-for-media-stream")
    options.addArguments("use-fake-ui-for-media-stream")
    options.addArguments("--disable-notifications")
    options.addArguments("--disable-confirmation")
    options.addArguments("disable-infobars")
    driver = { new ChromeDriver(options) }
  }

  chromeLocalHeadless {
    ChromeOptions options = new ChromeOptions()
    Map<String, Object> prefs = new HashMap<String, Object>()
    prefs.put("credentials_enable_service", false)
    prefs.put("profile.password_manager_enabled", false)
    options.setExperimentalOption("prefs", prefs)
    options.addArguments("no-sandbox")
    options.addArguments("use-fake-device-for-media-stream")
    options.addArguments("use-fake-ui-for-media-stream")
    options.addArguments("disable-notifications")
    options.addArguments("disable-confirmation")
    options.addArguments("disable-infobars")
    options.addArguments("window-size=1600,1200")
    DesiredCapabilities capabilities = DesiredCapabilities.chrome()
    capabilities.setCapability(ChromeOptions.CAPABILITY, options)
    driver = {
      new RemoteWebDriver(new URL("http://127.0.0.1:4444/wd/hub"), capabilities)
    }
  }

  firefox {
    driver = { new FirefoxDriver() }
  }
}

waiting {
  includeCauseInMessage = true
  timeout = 10
  retryInterval = 0.5
  presets {
    slow {
      timeout = 20
      retryInterval = 1
    }
    quick {
      timeout = 5
      retryInterval = 0.25
    }
  }
}