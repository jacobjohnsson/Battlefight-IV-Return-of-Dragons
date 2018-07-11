package battlefight

class Battle(player: Hero, monster: Monster) {
  var fighting: Boolean = true

  def run: Unit = {
    println(player.name + "! You've encountered a " + monster.name + ". Fight!\n")

    while(fighting) {
      if (!player.isDead && !monster.isDead) {

        var hasActed = false
        while(!hasActed) {
        
          displayStatus
  
          println("Your options are: \n\n" +
          "\t[1] Attack " +
          "\t\t[2] View Hero \n" +
          "\t[3] View Monster " + 
          "\t[4] Run")
          
  
          scala.io.StdIn.readLine() match {
            case "1" => playerAttack; hasActed = true
            case "2" => Character.view(player)
            case "3" => Character.view(monster)
            case "4" => playerRun; fighting = false; hasActed = true
            case _ => println("Please choose one of the options next time.")
          }
        }

        if (monster.currentHP == 1 && fighting == true) {
          println(monster.name + " ran away!")
          fighting = false
        } else if (fighting == true){
          monsterAttack
        }
      } else {
        fighting = false
      }
      
      monster.proc
      player.proc
    }
  }

  def playerAttack: Unit = {
    val hit = player.mainRoll
    val damage = player.attackValue

    monster.hit(hit, damage)

    if (monster.isDead) {
      won
    }
  }

  def won: Unit = {
    loot
    if (player.levelUpable) player.levelUp
    println("You've defeated your enemy!\n" +
          "You've gained " + monster.xpValue + " XP!")
    player.giveXP(monster.xpValue)
    fighting = false;
  }

  def playerRun: Unit = {
    println("You've escaped!")
  }

  def monsterAttack: Unit = {
    val hit = monster.mainRoll
    val damage = monster.attackValue

    player.hit(hit, damage)
  }
  
  def loot: Unit = {
    println("The " + monster.name + " dropped " + monster.loot.gold + " gold pieces and " + 
      monster.loot.get + ". Pick it up?\n" +
      "\t[1] Yes\n" +
      "\t[2] No")
  
    val userInput = scala.io.StdIn.readLine()
    userInput match {
      case "1" => {
        player.equipWeapon(monster.loot.get)
        println("You picked up and equipped " + monster.loot.get)
      }
      case "2" => println("You discarded the " + monster.loot.get)
      case _ => println("Not an option")
    }
    
    player.addGold(monster.loot.gold)
  }

  def displayStatus: Unit = {
    println("-PLAYER- HP: " + (player.currentHP) + "\t-" + monster.name.toUpperCase + "- HP: " + (monster.currentHP))
  }
}
