package net.yunt;

import java.util.List;
import java.util.HashMap;
import static org.junit.Assert.assertTrue;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class GameInProgressPage {
	private WebDriver driver;
    
    @FindBy(xpath = "//*[@id=\"game_board\"]/div/div[1]/div[2]/div[1]/div[1]/form/input[1]")
    private WebElement play1Button;
    
    @FindBy(xpath = "//*[@id=\"game_board\"]/div/div[1]/div[2]/div[1]/div[2]/form/input[1]")
    private WebElement play2Button;
    
    @FindBy(xpath = "//*[@id=\"game_board\"]/div/div[1]/div[2]/div[1]/div[3]/form/input[1]")
    private WebElement play3Button;

    @FindBy(xpath = "//*[@id=\"game_board\"]/div/div[1]/div[2]/div[1]/div[4]/form/input[1]")
    private WebElement play4Button;
	
    @FindBy(xpath = "//*[@id=\"game_board\"]/div/div[1]/div[1]/div[2]/form/input[1]")
    private WebElement passResetButton;
    
    public HashMap<String,String> gameStateMap = new HashMap();


	public GameInProgressPage(WebDriver driver) {
		this.driver = driver;
        PageFactory.initElements(driver, this);
        long maxWait = 3000;
        boolean containsText = false;
        long startTime = System.currentTimeMillis();
        while(!containsText && System.currentTimeMillis()-startTime < maxWait) {
        	String bodyText = driver.findElement(By.tagName("body")).getText();
        	if(bodyText.contains("Game in Progress")) {
        		containsText = true;
        		break;
        	}
        }
        assertTrue("Expected to find Game in Progress", containsText);
        //updateCurrentGameState();
	}
	
	
	public GameInProgressPage play1() throws Exception {
		play1Button.click();
		updateCurrentGameState();
		Thread.sleep(1000);
		printCurrentGameState();
	    return this;
	}
	
	public GameInProgressPage play2() throws Exception {
		play2Button.click();
		updateCurrentGameState();
	    return this;
	}
	
	public GameInProgressPage play3() throws Exception {
		play3Button.click();
		updateCurrentGameState();
	    return this;
	}
	
	public GameInProgressPage play4() throws Exception {
		play4Button.click();
		updateCurrentGameState();
	    return this;
	}
	
	public GameInProgressPage clickPassReset() throws Exception {
		passResetButton.click();
		updateCurrentGameState();
	    return this;
	}
	
	
	private void updateCurrentGameState() throws Exception {
		Thread.sleep(1000);
		for(int i=1;i<5;i++) {
			for(int j=1;j<5;j++) {
				String positionValue = null;
				try {
					int x = (i*-1)+6;
					int y = j;
					positionValue = driver.findElement(By.cssSelector("#game_board > div > div.col-sm-8 > div.game_board > div:nth-child("+x+") > div:nth-child("+y+")")).getText();
					//System.out.println("#game_board > div > div.col-sm-8 > div.game_board > div:nth-child("+x+") > div:nth-child("+y+")");
				} catch(Exception ex) {
					ex.printStackTrace();
					throw ex;
				}
				gameStateMap.put(i+":"+j,positionValue);
			}
		}
	}
	
	private void printCurrentGameState( ) {
		System.out.println("----");
		for (String key: gameStateMap.keySet()) {
            String value = gameStateMap.get(key).toString();  
            System.out.println(key + " " + value);  
		} 
		System.out.println("----");
	}
	
	public int getCountInColumn(int column, String value) {
		int count = 0;
		for(int i=1;i<5;i++) {
			if(gameStateMap.get(i+":"+column).contains(value)) {
				count++;
			}
		}
		return count;
	}
	
	public int getCountInRow(int row, String value) {
		int count = 0;
		for(int i=1;i<5;i++) {
			if(gameStateMap.get(row+":"+i).contains(value)) {
				count++;
			}
		}
		return count;
	}
}