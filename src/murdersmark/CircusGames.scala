package murdersmark

import battlefight.{Battle, Hero, Monster, StringFormatter, Location}

object CircusGames {
  val sf = new StringFormatter
  def run(player: Hero) = {
    menu(player)
  }

  def menu(player: Hero): Unit = {
    var legitChoice = false
    val carnivalKing: GameCarnivalKing = new GameCarnivalKing(player)
    val madameMasque: GameMadameMasque = new GameMadameMasque(player)
    val swiftSwines: GameSwiftSwines = new GameSwiftSwines(player)

    while(!legitChoice) {
      scala.io.StdIn.readLine(sf.toString("Before you children are miserably failing " +
      "at the popular games and challenges. How silly it seems. The games looks " +
      "trivial to you. The only real question is, which do you begin with?") + "\n" +
      "\t[1] Carnival King\t [2] Madame Masque\t [3] Swift Swines") match {
        case "1" => carnivalKing.run;  legitChoice = true
        case "2" => madameMasque.run;  legitChoice = true
        case "3" => swiftSwines.run;   legitChoice = true
        case _ =>
      }
    }
  }
}
