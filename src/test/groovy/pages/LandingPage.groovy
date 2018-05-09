package pages

import geb.Page


class LandingPage extends Page {
  static atCheckWaiting = [10, 0.5]
  static url = ""
  static at = { playButton.isDisplayed() }
  static content = {
    playButton(required: true, to: NewGamePage) { $('a', href: '/game') }
  }
}
