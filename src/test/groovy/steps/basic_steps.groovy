package steps

import static cucumber.api.groovy.EN.Then


Then(~/^player (\d+) has (\d+) move(s) in the (first|column) $/) { int player, int numMoves, String column ->
//this is just a stub for future use, change as needed

}

Then(~/^there is an "([^"]*)" in position (\d+),(\d+)$/) { String value, int row, int column ->
  page.verifyPlay(row, column, value)
}

Then(~/^the player clicks the (\d)(st|nd|rd) play button$/) { int column, String ordinal ->
  switch(column){
    case 1 :
      page.firstPlayButton.click()
      break
    case 2:
      page.secondPlayButton.click()
      break
    case 3:
      page.thirdPlayButton.click()
      break
    case 4:
      page.fourthPlayButton.click()
      break
  }
}