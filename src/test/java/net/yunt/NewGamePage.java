package net.yunt;

import java.util.List;
import static org.junit.Assert.assertTrue;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class NewGamePage {
	private WebDriver driver;
	
    @FindBy(xpath = "//*[@id=\"game_board\"]/form/input[1]")
    private WebElement startGameButton;

	public NewGamePage(WebDriver driver) {
		this.driver = driver;
        PageFactory.initElements(driver, this);
        long maxWait = 3000;
        boolean containsText = false;
        long startTime = System.currentTimeMillis();
        while(!containsText && System.currentTimeMillis()-startTime < maxWait) {
        	String bodyText = driver.findElement(By.tagName("body")).getText();
        	if(bodyText.contains("New Game")) {
        		containsText = true;
        		break;
        	}
        }
        assertTrue("Expected to find New Game", containsText);
	}
	
	public GameInProgressPage startGameButton() throws Exception {
		startGameButton.click();

	    return new GameInProgressPage(driver);
	}
}