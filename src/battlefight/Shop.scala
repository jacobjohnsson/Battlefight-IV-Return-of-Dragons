package battlefight

class Shop(player: Hero) extends Location(player) {
  def description: String = "The Shop!\n"
  
  val money: Int = 400
  val inventory: List[Item] = Armor.list ++ Weapon.weaponList
  
  def run: Unit = {
    println("Two-flower: Welcome to my shop! How may I help?")
    
    println("\n\t [1] Armor\t[2] Weapons\t[3] Return")
    
    scala.io.StdIn.readLine() match {
      case "1" => printArmor
      case "2" => printWeapons
      case _ => 
    }
    
  }
  
  def printArmor: Unit = {
    println("Name \t\t\tDef \tAP \tCost")
    val armor = inventory.filter(i => i.isInstanceOf[Armor])
    armor.indices.foreach(i => println("[" + (i+1) + "] " + armor(i).description))
    println("\nSee anything you like?\n" + 
        "\t [1] Buy" + 
        "\t [2] Return")
        
    scala.io.StdIn.readLine() match {
      case "1" => buy(armor)
      case _ => 
    }    
  }
  
  def printWeapons: Unit = {
    println("Name \t\t\tDef \tAP \tCost")
    val weapons = inventory.filter(w => w.isInstanceOf[Weapon])
    weapons.indices.foreach(i => println("[" + (i+1) + "] " + weapons(i)))
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
    
    if (piece.cost < player.getGold){    
      piece match {
        case a: Armor => player.equipArmor(a); player.removeGold(piece.cost)
        case w: Weapon => player.equipWeapon(w); player.removeGold(piece.cost)
        case i: Item => player.addItem(i); player.removeGold(piece.cost)
      }
    } else println("You can't afford that item!")
  }  
}