package pages

import geb.Page


class NewGamePage extends Page {
  static atCheckWaiting = [10, 0.5]
  static url = "game"
  static at = { header.isDisplayed() && startGameButton.isDisplayed() }
  static content = {
    header(required: true) { $('h1') }
    startGameButton(required: true, to: GameInProgressPage) { $('input', class: 'btn btn-primary') }

  }
}
