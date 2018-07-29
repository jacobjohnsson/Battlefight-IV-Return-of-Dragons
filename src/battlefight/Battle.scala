package battlefight
import scala.collection.mutable.ListBuffer
import scala.util.{Try,Success,Failure}

class Battle(player: Hero, monster: Monster) {
  var fighting: Boolean = true

  def run: Unit = {
    println("You've encountered a " + monster.name + ". Fight!\n")

    round

    def round: Unit = {
      procAll
      
      playerAct
      
      if (monster.isDead) {
        fighting = false
        won
      } else {
        monster.act match {
          case "flee" => println(monster.name + " fled!"); fighting = false
          case "attack" => monsterAttack
          case "spell" => monster.castSpell(monster.spells(0), player)
        }
      }

      if (fighting) round
    }
    
    def playerAct: Unit = {
      var hasActed = false
      
      displayStatus

      println("Your options are: \n\n" +
      "\t[1] Attack " +
      "\t\t[4] View Hero \n" +
      "\t[2] Cast Spell " +
      "\t\t[5] View Monster \n" +
      "\t[3] Run")


      scala.io.StdIn.readLine() match {
        case "1" => playerAttack
        case "2" => if (!castSpell) playerAct
        case "3" => playerRun; fighting = false
        case "4" => Character.view(player); playerAct 
        case "5" => Character.view(monster); playerAct
        case _ => println("Please choose one of the options next time."); playerAct
      }
    }
  }
  
  def procAll: Unit = monster.proc; player.proc

  def playerAttack: Unit = {
    val hit = player.mainRoll
    val damage = player.attackValue

    monster.hitWithWeapon(hit, damage)
  }

  def castSpell: Boolean = {

    def chooseSpell: Spell = {
      var index = 0
      
      def choose: Unit = {
        println("Choose: ")
        val userInput = scala.io.StdIn.readLine()
        Try(index = userInput.toInt) match { 
          case Failure(f) => println(f + "\nTry again!"); choose
          case _ => 
        }
      }
      
      choose
      player.spells(index)
    }

    var result: Boolean = false
    
    if (!player.spells.isEmpty) {

      Character.printSpells(player)
      println("Choose a spell: ")
      
      val spell: Spell = chooseSpell
      val target: Character = if (spell.beneficial) player else monster
  
      if (player.currentMana >= spell.manaCost) {
        if (player.castSpell(spell, target)){
          println("You casted a " + spell.name)
          result = true
        }
        else {
          println("You failed to cast a " + spell.name)
          result = true
        }
      } else
        println("You dont have enough mana to cast that spell.")
    } else {
      println("You don't have any spells!")
    }
    result
  }

  def won: Unit = {
    loot
    println("You've defeated your enemy!\n" +
          "You've gained " + monster.xpValue + " XP!\n")
    player.giveXP(monster.xpValue)
    fighting = false;
  }

  def playerRun: Unit = {
    println("You've escaped!")
  }

  def monsterAttack: Unit = {
    val hit = monster.mainRoll
    val damage = monster.attackValue
    player.hitWithWeapon(hit, damage)
  }

  def loot: Unit = {

    val lootItem: Option[Item] = {
      var list: ListBuffer[Item] = ListBuffer()
      if (monster.weapon.name.trim != "Unarmed") list = list :+ monster.weapon
      if (monster.armor.get.name.trim != "None") list = list :+ monster.armor.get
      if (!monster.inventory.isEmpty) list = list :+ monster.inventory(scala.util.Random.nextInt(monster.inventory.length))

      if (list.size == 0) None
      else Some(list(scala.util.Random.nextInt(list.length)))
    }

    val lootGold: Int = scala.util.Random.nextInt(monster.mainRoll)

    lootItem match {
      case s: Some[Item] => itemPickUp
      case _ => println("The " + monster.name + " dropped " + lootGold + " gold." )
    }

    player.addGold(lootGold)

    def itemPickUp: Unit = {

        println("The " + monster.name + " dropped " + lootGold + " gold and \n<" +

        lootItem.get.name + ">\n Pick it up?\n" +
        "\t[1] Yes\n" +
        "\t[2] No")
        val userInput = scala.io.StdIn.readLine()
        userInput match {
          case "1" => {
            player.addItem(lootItem.get)
            println("You picked up " + lootItem.get.name)
          }
          case "2" => println("You discarded the " + lootItem.get.name)
          case _ => println("Not an option")
        }
      }
    }

  def displayStatus: Unit = {
    println("-------------------------------------------------")
    println("-PLAYER- HP: \t" + (player.currentHP) + "\tMANA: \t" + player.currentMana
        + "\n" + monster.name.toUpperCase + "- HP: \t" + (monster.currentHP))
    println("-------------------------------------------------")
  }
}
