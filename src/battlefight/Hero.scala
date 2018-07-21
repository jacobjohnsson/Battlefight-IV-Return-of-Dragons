package battlefight
import scala.math

class Hero private(n: String, s: Stats, race: String) extends Character(n, s) {
  var gold: Int = 30
  var xp = 0
  var level: Int = 1
  def levelUpable: Boolean = xp > 100 * math.pow(1.3, level)

  override def toString: String = {
    "Hero: " + name + ", " + race + "\t Level: " + level + "\t Gold: " + gold
  }

  def giveXP(xpValue: Int): Unit = {
    xp += xpValue
    if (levelUpable) levelUp
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
    combatEffects.addEffect(new RawBonusEffect("", "hp", Dice.roll(6), 0))
  }

  def raiseMana: Unit = {
    combatEffects.addEffect(new RawBonusEffect("", "mana", Dice.roll(6), 0))
  }

  def chooseSkill: Unit = {
    println("Choose a new skill: \n")
    Skill.list.indices.foreach(i => print("[" + (i+1) + "] " + Skill.list(i).name))

    val userInput = scala.io.StdIn.readLine()
    addSkill(Skill.list(userInput.toInt - 1))
  }

  def hitWithWeapon(hit: Int, damage: Int): Unit = {
    if (hit > defense) {
      println("You took " + stats.takeDamage(damage) + " damage.")
    } else {
      println("You dodged")
    }
  }

  override def viewSkillsHook: String = {
    val sb: StringBuilder = new StringBuilder("\t Skills:    ")
    skills.foreach(s => sb.append(s.name + ", "))
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


          hero.addSpell(Spell.list(3)).addSpell(Spell.list(6))
              .addSpell(Spell.list(7)).addSpell(Spell.list(9))
              .addSpell(Spell.list(5)).addSpell(Spell.list(4))
          hero
        }
        case "Halfling" => {
          hero.addEffect(new AttributeEffect("Exceptional Attribute Rogue", "r", 2, 0))
          hero.addEffect(new RawBonusEffect("Weak", "hp", -2, 0))
        }
        case "Orc" => {
          hero.addEffect(new AttributeEffect("Exceptional Attribute Warrior", "w", 2, 0))
          val spell = new Spell("Berserk", 1)
          spell.manaCost = 0
          spell.dl = 0
          spell.addEffect(new AttributeEffect("Berserk", "w", 2, 0)).setBeneficial(true)
          hero.addSpell(spell)
        }
        case "Lizard man" => {
          hero.addEffect(new RawBonusEffect("Tough as nails", "dr", 2, 0))
          hero.addEffect(new RawBonusEffect("Natural Armor", "defense", 2, 0))
        }
        case _ => println("Please choose one of the options")
    }
    hero
  }

  def inventoryMenu(hero: Hero): Unit = {
    println("\n\t [1] View item\t [2] Return")
    scala.io.StdIn.readInt() match {
      case 1 => viewItem
      case _ =>
    }

    def viewItem: Unit = {
      println("Enter the number of the item you'd wish to view.")
      val chooseItem = scala.io.StdIn.readInt()
      val item = hero.inventory(chooseItem - 1)
      item match {
        case a : Armor => println("Name \t\tDef \tAP \tCost\n" + item.description)

        case w : Weapon => println("Name \tDamage \tCost\n" + item.name + "\t" + item.description)

        case _ => println(item.description)
      }
      println("\n\t [1] Equip item\t [2] Return")
      scala.io.StdIn.readInt() match {
        case 1 => hero.equip(item); println(item + " has been equipped")
        case _ =>
      }
    }
  }

  def spellMenu( hero: Hero): Unit = {
    scala.io.StdIn.readLine("\t [1] Cast a spell\t [2] Return") match {
      case "1" => {
        println("Choose a spell to cast on yourself.");
        val spell = chooseSpell
        hero.castSpell(spell, hero)
      }
      case _ =>
    }
    def chooseSpell: Spell = {
      val index = scala.io.StdIn.readInt()
      hero.spellBook(index - 1)
    }
  }
}
