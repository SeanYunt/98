package pages

import geb.Page
import org.openqa.selenium.By

class GameInProgressPage extends Page {
  static atCheckWaiting = [10, 0.5]
  static at = { header.isDisplayed() && passButton.isDisplayed() }
  static content = {
    header(required: true) { $('h1') }
    passButton(required: true) { $('input', value: 'Pass') }
    playButtons(required: true) {$(By.xpath("//input[@class='btn btn-primary' and @value='Play']"))}
    resetButton(required: true) {$(By.xpath("//input[@class='btn btn-primary' and @value='Reset']"))}
    firstPlayButton(required: true) {playButtons[0]}
    secondPlayButton(required: true) {playButtons[1]}
    thirdPlayButton(required: true) {playButtons[2]}
    fourthPlayButton(required: true) {playButtons[3]}
    rows(required: true) {$(By.xpath("//div[@class='level']"))}
  }

  def verifyPlay(row,column, value){
    waitFor(5){
      rows[row].children()[column].text() != ""
    }
    def columnSelected = rows[row].children()[column]
    assert columnSelected.text() == value
  }
}
