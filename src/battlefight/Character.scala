package battlefight
import scala.collection.mutable.ListBuffer

abstract class Character(val name: String, val stats: Stats) {
  val inventory: List[Item] = List()
  var weapon: Weapon = Weapon.createUnarmed
  var armor: Option[Armor] = None
  final val combatEffects: CombatEffectManager = new CombatEffectManager(stats, weapon)
  val skills: ListBuffer[Skill] = ListBuffer()
  
  def attackValue: Int = weapon.damage() + weapon.bonus
  def currentHP: Int = stats.currentHP
  def currentMana: Int = stats.currentMana
  def mainAttr: Int = stats.mainAttr
  def isDead: Boolean = stats.currentHP <= 0

  override def toString: String = {
    "Character: " + name
  }

  def addEffect(effect: CombatEffect) : Unit = {
    effect match {
      case a: AttributeEffect => combatEffects.addAttrEffect(a)
      case r: RawBonusEffect  => combatEffects.addRawEffect(r)
      case w: WeaponEffect  => combatEffects.addWeaponEffect(w)
    }
  }
  
  def removeEffect(effect: CombatEffect) : Unit = {    
    effect match {
      case a: AttributeEffect => combatEffects.removeAttrEffect(a)
      case r: RawBonusEffect  => combatEffects.removeRawEffect(r)
      case w: WeaponEffect  => combatEffects.removeWeaponEffect(w)
    }
  }
  
  def addSkill(skill: Skill): Unit = {
    skills += skill
    
    skill.effect match {
      case a: AttributeEffect => combatEffects.addAttrEffect(a)
      case r: RawBonusEffect => combatEffects.addRawEffect(r)
      case w: WeaponEffect => combatEffects.addWeaponEffect(w)
    }
    println(skill.name + " was just added!")
  }
  
  def removeSkill(skill: Skill): Unit = {
    if (skills.contains(skill)) {
      skill.effect match {
        case a: AttributeEffect => combatEffects.removeAttrEffect(a)
        case r: RawBonusEffect => combatEffects.removeRawEffect(r)
        case w: WeaponEffect => combatEffects.removeWeaponEffect(w)
      }
      skills -= skill
    } else {
      println("That skill was not in the list")  
    }
  }
  
  def proc: Unit = combatEffects.proc

  def addItem(item: Item): Unit = inventory :+ item
  
  def equipWeapon(w: Weapon): Unit = {
    weapon = w
    combatEffects.weapon = w
  }
  
  def equipArmor(a: Armor): Unit = armor = Some(a)
  
  def levelUp: Unit = println("You can't level up a monster!")

  def reset: Unit = stats.reset  

  def description: String = {
    "\t Character: " + name + "\n" +
    "\t Warrior:   " + stats.w.value + " (+ " + stats.w.bonus + ")" +
    "\t HP:      " + stats.currentHP + "/" + stats.maxHP + "\n" +
    "\t Rogue:     " + stats.r.value + " (+ " + stats.r.bonus + ")" +
    "\t Defense: " + stats.defense + "\n" +
    "\t Mage:      " + stats.m.value + " (+ " + stats.m.bonus + ")" +
    "\t Mana:    " + stats.currentMana + "/" + stats.maxMana + "\n\n" +
    "\t Weapon:    " + weapon.name + "\n" + 
    "\t Armor:     " + armor.getOrElse("none") + "\n"
  }

  def mainRoll: Int = {stats.mainAttr + Dice.explodingRoll(6)}
  def wRoll: Int = {stats.w.value + Dice.explodingRoll(6)}
  def rRoll: Int = {stats.r.value + Dice.explodingRoll(6)}
  def mRoll: Int = {stats.m.value + Dice.explodingRoll(6)}
}

object Character {
  def view(character: Character): Unit = {
    println(character.description + "\n" +
        "\t[1] View Inventory" +
        "\t[2] Return")
    
    val userInput = scala.io.StdIn.readLine()

    userInput match {
      case "1" => printInventory(character)
      case "2" =>
      case _ => println("Not an Option!\n")
    }
  }
  
  def printInventory(character: Character): Unit = {
    character.inventory.indices.foreach(i => println("[" + (i+1) + "] " + character.inventory(i)))
  }
}
