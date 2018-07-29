package battlefight

class Shop(player: Hero) extends Location(player) {
  def description: String = "The Shop!\n"

  val money: Int = 400
  val inventory: List[Item] = {
    Armor.list ++ Weapon.weaponList :+ new SpellBook("The Sapphire Grimoire", 20, Spell.list)
  }

  def run: Unit = {
    println("Two-flower: Welcome to my shop! How may I help?")

    println("\n\t [1] Armor\t[2] Weapons\t[3] Other\t[4] Return")

    scala.io.StdIn.readLine() match {
      case "1" => printArmor
      case "2" => printWeapons
      case "3" => printOther
      case _ =>
    }
  }
  
  def printOther: Unit = {
    val items = inventory.filterNot(x => (x.isInstanceOf[Weapon] || x.isInstanceOf[Armor]))
    
    println("Name \t\t\t\t\tCost")
    
    items.indices.foreach( i => {
      printf("[" + "%2d", (i+1))
      print("] ")
      printf("%-30s", items(i).name)
      println("\t" + items(i).cost + "g")
    })
    
    println("\nSee anything you like?\n" +
    "\t [1] Buy" +
    "\t [2] Return")
    
    scala.io.StdIn.readLine() match {
      case "1" => buy(items)
      case _ =>
    }
  }

  def printArmor: Unit = {
    val armor = inventory collect {
      case a: Armor => a
    }
    
    println("Name \t\t\t\tDef \tAP \tCost")
    
    armor.indices.foreach(i => {
      printf("[" + "%2d", (i+1))
      print("] ")
      printf("%-20s", armor(i).name)
      println("\t" +  armor(i).defense + "\t" + armor(i).ap + "\t" + armor(i).cost + "g")
    })
    println("\nSee anything you like?\n" +
        "\t [1] Buy" +
        "\t [2] Return")

    scala.io.StdIn.readLine() match {
      case "1" => buy(armor)
      case _ =>
    }
  }

  def printWeapons: Unit = {
    val weapons: List[Weapon] = inventory collect {
      case w: Weapon => w
    }
    
    println("Name \t\t\t Skill \t   Damage   Cost")
    
    weapons.indices.foreach(i => {
      printf("[" +  "%2d", (i+1))
      print("] ")
      printf("%-20s", weapons(i).name)
      printf( "%-10s", weapons(i).skill)
      printf("%-9s", weapons(i).getDamageString)
      print(weapons(i).cost + "g\n")  
    })
    println("\nSee anything you like?\n" +
        "\t [1] Buy" +
        "\t [2] Return")

    scala.io.StdIn.readLine() match {
      case "1" => buy(weapons)
      case _ =>
    }
  }

  def buy(items: List[Item]): Unit = {
    println("Enter the number of the item you wish to buy")
    val userInput = scala.io.StdIn.readLine()

    val piece = items(userInput.toInt - 1)

    if (piece.cost <= player.getGold){
      piece match {
        case a: Armor => player.equip(a); player.addItem(a); player.removeGold(piece.cost)
        case w: Weapon => player.equip(w); player.addItem(w); player.removeGold(piece.cost)
        case i: Item => player.addItem(i); player.removeGold(piece.cost)
      }
    } else println("You can't afford that item!")
  }
}
