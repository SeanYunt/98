package net.yunt;

import static org.junit.Assert.assertTrue;

import java.util.List;

import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import com.jayway.restassured.*;
import com.jayway.restassured.http.ContentType;
import static org.hamcrest.Matchers.*;
import static com.jayway.restassured.RestAssured.*;

public class SimpleTest extends SeleniumBase {

	private static String GAME_UI_URL = "https://droptoken.herokuapp.com/game";
	private static String GAME_SERVICE_URL = "https://w0ayb2ph1k.execute-api.us-west-2.amazonaws.com/production";

	
	@Test
	public void oneInFirstColumn() throws Exception {
		WebDriver driver = get(GAME_UI_URL);
		NewGamePage ngp = new NewGamePage(driver);
		GameInProgressPage gipp = ngp.startGameButton();
		gipp.play1();
		gipp.printCurrentGameState();
		assertTrue("expected 1:1 to contain X", gipp.gameStateMap.get("1:1").contains("X"));
		gipp.clickPassReset();
		gipp.printCurrentGameState();
		assertTrue("expected 1:1 to not contain X", !gipp.gameStateMap.get("1:1").contains("X"));
	}
	
	@Test
	public void twoInSecondColumn() throws Exception {
		WebDriver driver = get(GAME_UI_URL);
		NewGamePage ngp = new NewGamePage(driver);
		GameInProgressPage gipp = ngp.startGameButton();
		gipp.play2();
		gipp.printCurrentGameState();
		assertTrue("expected 1:2 to contain X", gipp.gameStateMap.get("1:2").contains("X"));
		gipp.play2();
		gipp.printCurrentGameState();
		assertTrue("expected column 2 to contain > 1 X", gipp.getCountInColumn(2, "X") > 1);
		
	}
	
	@Test
	public void oneInFirstRow() throws Exception {
		WebDriver driver = get(GAME_UI_URL);
		NewGamePage ngp = new NewGamePage(driver);
		GameInProgressPage gipp = ngp.startGameButton();
		gipp.play1();
		gipp.printCurrentGameState();
		assertTrue("expected 1:1 to contain X", gipp.gameStateMap.get("1:1").contains("X"));
		assertTrue("expected row 1 to contain >= 1 X", gipp.getCountInRow(1, "X") >= 1);
	}
	
	@Test
	public void oneInSecondRow() throws Exception {
		WebDriver driver = get(GAME_UI_URL);
		NewGamePage ngp = new NewGamePage(driver);
		GameInProgressPage gipp = ngp.startGameButton();
		gipp.play1();
		gipp.printCurrentGameState();
		assertTrue("expected 1:1 to contain X", gipp.gameStateMap.get("1:1").contains("X"));
		//brute force will ensure at least 2 X are on 2nd row
		gipp.play2();
		gipp.printCurrentGameState();
		gipp.play3();
		gipp.printCurrentGameState();
		gipp.play4();
		gipp.printCurrentGameState();
		assertTrue("expected row 2 to contain >= 1 X", gipp.getCountInRow(2, "X") >= 1);
		gipp.getTallestColumn();
	}
	
	@Test
	public void resetGame() throws Exception {
		WebDriver driver = get(GAME_UI_URL);
		NewGamePage ngp = new NewGamePage(driver);
		GameInProgressPage gipp = ngp.startGameButton();
		gipp.play1();
		gipp.printCurrentGameState();
		assertTrue("expected 1:1 to contain X", gipp.gameStateMap.get("1:1").contains("X"));
		gipp.clickPassReset();
		gipp.printCurrentGameState();
		assertTrue("expected 1:1 to contain nothing", !gipp.gameStateMap.get("1:1").contains("X"));
	}
	
	@Test
	public void chaseTheTallest() throws Exception {
		WebDriver driver = get(GAME_UI_URL);
		NewGamePage ngp = new NewGamePage(driver);
		GameInProgressPage gipp = ngp.startGameButton();
		gipp.play1();
		gipp.printCurrentGameState();
		assertTrue("expected 1:1 to contain X", gipp.gameStateMap.get("1:1").contains("X"));
		assertTrue("expected row 1 to contain >= 1 X", gipp.getCountInRow(1, "X") >= 1);
		while(!gipp.isGameOver()) {
			int nextColumn = gipp.getTallestColumn();
			//end test is column is full
			if(gipp.getValueAt(4,nextColumn).contains("X") || gipp.getValueAt(4,nextColumn).contains("O")) break;
			switch (nextColumn) {
				case 1: gipp.play1();
						gipp.printCurrentGameState();
						break;
				case 2: gipp.play2();
						gipp.printCurrentGameState();
						break;
				case 3: gipp.play3();
						gipp.printCurrentGameState();
						break;
				case 4: gipp.play4();
						gipp.printCurrentGameState();
						break;
				default: gipp.clickPassReset();
						break;
			}
			Thread.sleep(3000);
		}
	}
	
	//@Test
	public void chaseTheEmpty() throws Exception {
		WebDriver driver = get(GAME_UI_URL);
		NewGamePage ngp = new NewGamePage(driver);
		GameInProgressPage gipp = ngp.startGameButton();
		gipp.play1();
		gipp.printCurrentGameState();
		assertTrue("expected 1:1 to contain X", gipp.gameStateMap.get("1:1").contains("X"));
		assertTrue("expected row 1 to contain >= 1 X", gipp.getCountInRow(1, "X") >= 1);
		while(!gipp.isGameOver()) {
			int nextColumn = gipp.getEmptyColumn();
			//end test is column is not empty
			//if(gipp.getValueAt(4,nextColumn).contains("X") || gipp.getValueAt(4,nextColumn).contains("O")) break;
			switch (nextColumn) {
				case 1: gipp.play1();
						gipp.printCurrentGameState();
						break;
				case 2: gipp.play2();
						gipp.printCurrentGameState();
						break;
				case 3: gipp.play3();
						gipp.printCurrentGameState();
						break;
				case 4: gipp.play4();
						gipp.printCurrentGameState();
						break;
				default: gipp.clickPassReset();
						break;
			}
			Thread.sleep(3000);
		}
	}	

}