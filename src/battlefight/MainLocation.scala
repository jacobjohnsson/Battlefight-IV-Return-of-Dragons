package battlefight

class MainLocation(player: Hero) extends Location(player) {
  var x: Int = 0
  val shop = new Shop(player)
  
  def run: Unit = {
    menu
  }
  
  def description: String = {
    "Welcome to the world of Roshar! Here lies many dangers but even more treasure." +
          " Be wary though, for fortune only favors the brave, cunning and strong. What do you want to do?\n"
  }

  def menu: Unit = {
    var exit: Boolean = false;
    while(exit == false) {
      println(description +
        "[1] Head to the wilderness\n" +
        "[2] Visit a shop\n" +
        "[3] View Hero\n" +
        "[4] Retire")

      val userInput = scala.io.StdIn.readLine()

      userInput match {
        case "1" => wilderness
        case "2" => toShop
        case "3" => Character.view(player)
        case "4" => exit = true
        case _ => println("Please choose one of the options!")
      }
    }
  }

  def wilderness {
    var exit: Boolean = false;

    while (exit == false) {
      println("After about half a day's journey you're standing on the edge " +
      "the wilderness.\n" +
      "[1] Look for foes.\n" +
      "[2] Rest\n" +
      "[3] View Hero\n" +
      "[4] Return to the village.")

      val userInput = scala.io.StdIn.readLine()

      userInput match {
        case "1" => wildBattle
        case "2" => rest
        case "3" => Character.view(player)
        case "4" => returntoVillage; exit = true
        case _ => println("Nope.")
      }
    }

    def wildBattle: Unit = {
      val battle = new Battle(player, Monster.getRandom)
      battle.run
    }

    def returntoVillage: Unit = {
      println("Spent enough time in the wilderness, you decide to head back")
    }

    def rest: Unit = {
      player.reset
    }
  }

  def viewHero: Unit = {
      Character.view(player)
    }

  def toShop: Unit = {
    
    shop.run    
  }
}
