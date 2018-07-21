package murdersmark

class GameCarnivalKing(player: battlefight.Hero) extends battlefight.Location(player) {
  val sf = new battlefight.StringFormatter
  
  
  def run: Unit = {
    menu    
  }
  
  def menu: Unit = {
    var legitChoice = false
    
    while (!legitChoice) {
      scala.io.StdIn.readLine(sf.toString("A heavy, long-handled mallet leans against a 3-foot-wide and" + 
        "12-foot- high wooden tower crowned with a stuffed lion's head." + 
        "At the base is a lever attached to a puck that slides up and down" + 
        "a pole running the length of the tower up to the lion's head.\n") + 
        "\t[1] Take a shot\t [2] Return to the midway\n") match {
        case "1" => pay; legitChoice = true
        case "2" => legitChoice = true
        case _ => 
      }
    }
  }
  
  def pay: Unit = {
    scala.io.StdIn.readLine(sf.toString("As you approach the mallet the circus worker steps forward. " + 
        "\" Tha'll be one gold fer e' try eh?\"") + 
        "\t[1] Pay up\t [2] Return to the midway\n") match {
        case "1" => player addGold -1; play
        case "2" => 
        case _ => 
      }
  }
  
  def play: Unit = {
    scala.io.StdIn.readLine(sf.toString("\"See, what you wanna do is hit that puck at the bottom. " + 
        "Ba'h I'm sure you know how this works!\"") + "\n\tPress any buttom to smash the puck")
    if (player.wRoll >= 7) println("You bloody did it!")
    else println("Better luck next time!")
  }
}