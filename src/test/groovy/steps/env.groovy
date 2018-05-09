package steps

import geb.Browser
import geb.binding.BindingUpdater
import org.openqa.selenium.OutputType
import org.openqa.selenium.WebDriverException
import org.slf4j.LoggerFactory

import static cucumber.api.groovy.Hooks.Before
import static cucumber.api.groovy.Hooks.After

import io.github.bonigarcia.wdm.ChromeDriverManager
import io.github.bonigarcia.wdm.FirefoxDriverManager

logger = LoggerFactory.getLogger("browser_steps")

def bindingUpdater
theBrowser = null

if (System.getProperty("geb.env").toLowerCase().contains("firefox")) {
  FirefoxDriverManager.getInstance().setup()
} else {
  try {
    ChromeDriverManager.getInstance().setup()
  } catch (RuntimeException e) {
    logger.warn("Unable to setup chrome driver. Will try again later (caused by multithreading).", e)
    def timeToWait = 60 * 1000
    sleep(timeToWait)
    ChromeDriverManager.getInstance().setup()
  }
}

Before() { scenario ->
  theBrowser = new Browser()
  bindingUpdater = new BindingUpdater(binding, new Browser())
  logger.info("Starting WebDriver")
  bindingUpdater.initialize()
  logger.info("Browser is up")
  try {
    theBrowser.driver.manage().window().maximize()
    logger.info("Browser window maximized")
  } catch (Exception ex) {
    logger.debug("Failed to maximize browser, continuing")
  }
}

After() { scenario ->
  bindingUpdater?.remove()
  // embed screenshot into cucumber report
  if (scenario.failed) {
    try {
      scenario.embed(theBrowser.driver.getScreenshotAs(OutputType.BYTES), "image/png")
    } catch (WebDriverException e) {
      // sometime firefox runs out of memory trying to take a screenshot, not a big deal so ignore
    } catch (MissingMethodException e) {
      // HTMLUnit doesn't support screenshots
    }
  }
}