package battlefight

class MainLocation(player: Hero) extends Location(player) {
  val shop = new Shop(player)
  lazy val murdersMark = new murdersmark.Start(player)
  val sf = new StringFormatter

  def run: Unit = {
    menu
  }

  def description: String = {
    "Welcome to the world of Roshar! Here lies many dangers but even more treasure. " +
    "Be wary though, for fortune only favors the brave, cunning and strong. " +
    "You're currently at home, pondering deep things, when you realize life's too short for too much pondering. " +
    "These are the rumors you're currently aware of: \n"
  }

  def menu: Unit = {
    var exit: Boolean = false;
    while(exit == false) {
      
      println(sf.toString(description) + 
        "[1] Visit the newly arrived Circus\n" +
        "[2] Realm of the Fellnight Queen\n" +
        "[3] Visit a shop\n" +
        "[4] View Hero\n" +
        "[5] Retire")

      val userInput = scala.io.StdIn.readLine()

      userInput match {
        case "1" => murdersMark.main(player)
        case "2" => wilderness; println("Wilderness")
        case "3" => toShop
        case "4" => Character.view(player)
        case "5" => exit = true
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
