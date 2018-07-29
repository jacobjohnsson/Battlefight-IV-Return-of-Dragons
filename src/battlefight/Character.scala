package battlefight
import scala.collection.mutable.ListBuffer

abstract class Character(val name: String, val stats: Stats) {
  val inventory: ListBuffer[Item] = ListBuffer()
  var weapon: Weapon = Weapon.createUnarmed
  var armor: Option[Armor] = None
  final val combatEffects: CombatEffectManager = new CombatEffectManager(stats, weapon)
  val skills: ListBuffer[Skill] = ListBuffer()
  var spells: ListBuffer[Spell] = ListBuffer()

  def attackValue: Int = weapon.damage() + weapon.bonus
  def currentHP: Int = stats.currentHP
  def currentMana: Int = stats.currentMana
  def mainAttr: Int = stats.mainAttr
  def isDead: Boolean = stats.currentHP <= 0

  protected def armorDefense: Int = armor match {
    case Some(a) => a.defense
    case none => 0
  }

  def defense: Int = stats.defense + armorDefense

  def hitWithWeapon(hit: Int, damage: Int): Unit

  def hitWithSpell(spell: Spell): Unit = {
    if (spell.getEffect != None) {
      spell.getEffect.get match {
        case c: CombatEffect => addEffect(c)
        case _ =>
      }
    }
    stats.takeDamage(spell.getDamage)
    println("A " + spell.name  + " hit " + name)
  }

  def castSpell(spell: Spell, target: Character): Boolean = {
    var result: Boolean = false

    if (spells.contains(spell) && stats.currentMana >= spell.manaCost) {
      stats.currentMana -= spell.manaCost
      if (mRoll > spell.dl)  {
        target.hitWithSpell(spell)
        result = true
      }
    }
    result
  }

  def addSpell[C](spell: Spell): Character = {
    spells += spell
    this
  }

  def addEffect(effect: CombatEffect) : Unit = {
    combatEffects.addEffect(effect)
  }

  def removeEffect(effect: CombatEffect) : Unit = {
    effect match {
      case a: AttributeEffect => combatEffects.removeEffect(a)
      case r: RawBonusEffect  => combatEffects.removeEffect(r)
      case w: WeaponEffect  => combatEffects.removeEffect(w)
    }
  }

  def addSkill(skill: Skill): Unit = {
    skills += skill
    addEffect(skill.effect)
    println(skill.name + " was just added!")
  }

  def removeSkill(skill: Skill): Unit = {
    if (skills.contains(skill)) {
      skill.effect match {
        case a: AttributeEffect => combatEffects.removeEffect(a)
        case r: RawBonusEffect => combatEffects.removeEffect(r)
        case w: WeaponEffect => combatEffects.removeEffect(w)
      }
      skills -= skill
    } else {
      println("That skill was not in the list")
    }
  }

  def proc: Unit = combatEffects.proc(wRoll)

  def addItem(item: Item): Unit = {
    inventory += item

    item match {
      case sb: SpellBook => spells = (spells ++= sb.spells).distinct
      case _ =>
    }
  }

  def equip(item: Item): Unit = {
    item match {
      case a: Armor => armor = Some(a)
      case w: Weapon => weapon = w; combatEffects.weapon = w
    }
  }

  def levelUp: Unit = println("You can't level up a monster!")

  def reset: Unit = stats.reset

  def description: String = {
    val sb: StringBuilder = new StringBuilder()
    sb.append("\t " + toString + "\n" +
    "\t Warrior:   " + stats.w.value + " (+ " + stats.w.bonus + ")" +
    "\t HP:      " + stats.currentHP + "/" + stats.maxHP + "\n" +
    "\t Rogue:     " + stats.r.value + " (+ " + stats.r.bonus + ")" +
    "\t Defense: " + defense + "\n" +
    "\t Mage:      " + stats.m.value + " (+ " + stats.m.bonus + ")" +
    "\t Mana:    " + stats.currentMana + "/" + stats.maxMana + "\n\n" +
    "\t Weapon:    " + weapon.name + "\n" +
    "\t Armor:     " + armor.getOrElse("none") + "\n" +
    "\t DR:        " + stats.damageReduction + "\n\n" +
    viewSkillsHook)
    sb.append("\n\t Modifiers: ")
    combatEffects.toList.foreach(s => if (s.name != "") sb.append(s.name + ", "))
    sb.toString
  }

  protected def viewSkillsHook: String = ""

  override def toString: String = {
    "Character: " + name
  }

  def mainRoll: Int = stats.mainAttr + Dice.explodingRoll(6)
  def wRoll: Int = stats.w.value + Dice.explodingRoll(6)
  def rRoll: Int = stats.r.value + Dice.explodingRoll(6)
  def mRoll: Int = stats.m.value + Dice.explodingRoll(6)
}

object Character {
  def view(character: Character): Unit = {
    print(character.description + "\n" +
        "\t[1] View Inventory" +
        "\t[2] Return")
   character match {
      case h: Hero => println("\t[3] Open spell book")
      case _ => println()
    }

    val userInput = scala.io.StdIn.readLine()

    userInput match {
      case "1" => printInventory(character)
      case "2" =>
      case "3" => printSpells(character)
      case _ => println("Not an Option!\n")
    }
  }

  def printInventory(character: Character): Unit = {
    println("Inventory of " + character.name + ": ")
    character.inventory.indices.foreach(i => println("[" + (i+1) + "] " + character.inventory(i)))
    character match {
      case h: Hero => Hero.inventoryMenu(h)
      case m: Monster =>
    }
  }

  def printSpells(character: Character): Unit = {
    println("\tSpell book: ")
    character.spells.indices.foreach(i => println("[" + (i+1) + "] " + character.spells(i)))
    if (character.spells.isEmpty) println("\tYou have no spells"); scala.io.StdIn.readLine()
    character match {
      case h:  Hero => Hero.printSpellMenu(h)
      case _ =>
    }
  }
}
