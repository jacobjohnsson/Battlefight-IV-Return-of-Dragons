package battlefight

class Weapon(val n: String,
            val skill: String,
            inputdamage: () => Int,
            cost: Int,
            var bonus: Int) extends Item(n, cost){

  def damage = inputdamage

  val description: String = skill + ",\t" + getDamageString + ",\tcost:" + cost + ""
  
  
  def getDamageString: String = {
    var damageString = "damage"
    
    damage match {
      case d: (() => Int) if (d == Dice.d6) => damageString = "d6"
      case d: (() => Int) if (d == Dice.d6plus2) => damageString = "d6+2"
      case d: (() => Int) if (d == Dice.d6plus4) => damageString = "d6+4"
      case d: (() => Int) if (d == Dice.d62) => damageString = "d6 + d6"
      case d: (() => Int) if (d == Dice.d6by2) => damageString = "d6/2"
      case d: (() => Int) if (d == Dice.d6minus2) => damageString = "d6-2"
      case _ => damageString = "damage not found"
    }
    damageString
  }

  def addBonus(mod: Int): Unit = {
    bonus += mod
  }
  
  override def toString: String = {
    name
  }
}

object Weapon {
  val weaponList: List[Weapon] = updateFromFile

  def updateFromFile: List[Weapon] = {

    val fileVector =
      scala.io.Source.fromFile("resources/Weapons.txt").getLines.toVector.map(_.split(","))

    val weaponNames = fileVector.map(a => a(0)).map(s => s.trim).tail
    val weaponSkill = fileVector.map(a => a(1)).map(s => s.trim).tail
    val weaponDamage = fileVector.map(a => a(2)).tail.map(s => s.trim)
    val weaponCosts = fileVector.map(a => a(3)).tail.map(s => s.trim.toInt)
    val damage: List[() => Int] = weaponDamage.toList.map(i =>
      i match {
        case "1d6" => Dice.d6
        case "1d6/2" => Dice.d6by2
        case "1d6-2" => Dice.d6minus2
        case "1d6+2" => Dice.d6plus2
        case "1d6+4" => Dice.d6plus4
        case "2d6" => Dice.d62
        case _ => Dice.d6
      })

    val result = for (i <- 0 until weaponNames.length)
              yield create(weaponNames(i), weaponSkill(i), damage(i), weaponCosts(i))
    result.toList
  }

  def create(name: String, skill: String, damage: () => Int, cost: Int): Weapon =
    new Weapon(name, skill, damage, cost, 0)
  
  def createAxe: Weapon = {
    new Weapon("Axe", "Axes", Dice.d6, 5, 0)
  }
  def createSword: Weapon = {
    new Weapon("Sword", "Swords", Dice.d6, 5, 0)
  }
  def createBow: Weapon = {
    new Weapon("Bow", "Bows", Dice.d6, 4 , 0)
  }
  def createDagger: Weapon = {
    new Weapon("Dagger", "Daggers", Dice.d6minus2, 2, 0)
  }
  def createMace: Weapon = {
    new Weapon("Mace", "Maces", Dice.d6, 5, 0)
  }
  def createSpear: Weapon = {
    new Weapon("Spear", "Spears", Dice.d6, 3, 0)
  }
  def createUnarmed: Weapon = {
    new Weapon("Unarmed", "Unarmed", Dice.d6by2, 0, 0)
  }
  def Plus2Staff: Weapon = new Weapon("Staff (+2)", "Blunt", Dice.d6, 25, 2)
}
