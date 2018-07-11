package battlefight
import scala.math

class Hero private(n: String, s: Stats, race: String) extends Character(n, s) {
  var gold: Int = 30
  var xp = 0
  var level: Int = 1
  def levelUpable: Boolean = xp > 100 * math.pow(1.3, level)
  def armorDefense: Int = armor match {
    case Some(a) => a.defense
    case none => 0
  }

  override def toString: String = {
    "Hero: " + name + ", " + race + ", level: " + level
  }
  
  def giveXP(xpValue: Int): Unit = {
    xp += xpValue
  }
  
  def getGold: Int = gold
  def addGold(g: Int): Unit = gold += g
  def removeGold(g: Int): Unit = gold -= g
  
  override def levelUp: Unit = {
    xp = 0
    level += 1
    println("You've leveled up! Here are your options: \n" + 
        "\t [1] Raise an attribute\n" + 
        "\t [2] Add a dice roll to your hp\n" + 
        "\t [3] Add a dice roll to your mana\n" + 
        "\t [4] Choose a Skill\n")
    
    val userInput = scala.io.StdIn.readLine()
    
    userInput match {
      case "1" => raiseAttribute
      case "2" => raiseHP
      case "3" => raiseMana
      case "4" => chooseSkill
      case _ => println("That's not an option!")
    }    
  }
  
  def raiseAttribute: Unit = {
    println("Which attribute do you want to raise?\n" + 
        "\t [1] Warrior\n" + 
        "\t [2] Rogue\n" + 
        "\t [3] Mage")
    val userInput = scala.io.StdIn.readLine() 
    
    userInput match {
      case "1" => stats.w.addBaseValue(1)
      case "2" => stats.r.addBaseValue(1)
      case "3" => stats.m.addBaseValue(1)
    }
    
    combatEffects.reCalculate
  }
  
  def raiseHP: Unit = {
    combatEffects.addRawEffect(new RawBonusEffect("", "hp", Dice.roll(6), 0))
  }
  
  def raiseMana: Unit = {
    combatEffects.addRawEffect(new RawBonusEffect("", "mana", Dice.roll(6), 0))
  }

  def chooseSkill: Unit = {
    println("Choose a new skill: \n")
    Skill.list.indices.foreach(i => print("[" + (i+1) + "] " + Skill.list(i).name))
    
    val userInput = scala.io.StdIn.readLine()
    addSkill(Skill.list(userInput.toInt - 1))
  }
  
  def hit(hit: Int, damage: Int): Unit = {
    if (hit > (stats.defense + armorDefense)) {
      stats.currentHP -= damage
      println("You took " + damage + " damage.")
    } else {
      println("You dodged")
    }
  }
  
  override def description: String = {
    val sb: StringBuilder = new StringBuilder()
    sb.append("\t Character: " + name + ", " + race + ", Level: " + level + ", Gold: " + gold + "\n\n" +
    "\t Warrior:   " + stats.w.value + " (+ " + stats.w.bonus + ")" +
    "\t HP:      " + stats.currentHP + "/" + stats.maxHP + "\n" +
    "\t Rogue:     " + stats.r.value + " (+ " + stats.r.bonus + ")" +
    "\t Defense: " + (stats.defense + armorDefense) + "\n" +
    "\t Mage:      " + stats.m.value + " (+ " + stats.m.bonus + ")" +
    "\t Mana:    " + stats.currentMana + "/" + stats.maxMana + "\n\n" +
    "\t Weapon:    " + weapon.name + "\n" + 
    "\t Armor:     " + armor.getOrElse("none") + "\n" +
    "\t Skills:    ")
    skills.foreach(s => sb.append(s.name + ", "))
    sb.append("\n\t Modifiers: ")
    combatEffects.toList.foreach(s => if (s.name != "") sb.append(s.name + ", "))
    sb.append("\n")
    sb.toString
  }
}

object Hero {
  def create(name: String, stats: Stats, race: String): Hero = {
    val hero = new Hero(name, stats, race)
    race match {
        case "Dwarf" => {
          hero.addEffect(new AttributeEffect("Exceptional Attribute Warrior", "w", 2, 0))
          hero.addEffect(new RawBonusEffect("No talent for magic", "mana", -1000, 0))
        }
        case "Elf" => { 
          hero.addEffect(new AttributeEffect("Exceptional Attribute Mage", "m", 2, 0))
          hero.addEffect(new RawBonusEffect("Weak", "hp", -2, 0))
        }
        case "Halfling" => {
          hero.addEffect(new AttributeEffect("Exceptional Attribute Rogue", "r", 2, 0))
          hero.addEffect(new RawBonusEffect("Weak", "hp", -2, 0))
        }
        case _ => println("Please choose one of the options")
    }
    hero
  }
}
