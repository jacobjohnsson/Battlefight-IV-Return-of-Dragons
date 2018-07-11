package battlefight
import scala.util.Random

case class Monster (n: String, s: Stats, xpValue: Int) extends Character(n, s) {
  val loot: Loot = Loot.create

  override def toString: String = {
    "Monster: " + name + ". Attributes: " + stats.w.value + ", " + stats.r.value + ", " + stats.m.value + "."
  }

  def hit(hit: Int, damage: Int): Unit = {
    if (hit > stats.defense) {
      stats.currentHP -= damage
      println("You did " + damage + " damage.")
    } else {
      println("You missed!")
    }
  }
}

object Monster {
  val monsterList = updateFromFile

  // read from file.
  def updateFromFile: List[Monster] = {

    val fileVector = scala.io.Source.fromFile("resources/Monsters.txt").getLines.toVector.map(_.split(",")).tail

    val monsterNames = fileVector.map(a => a(0)).map(s => s.trim)
    val monsterW = fileVector.map(a => a(1)).map(s => s.trim.toInt)
    val monsterR = fileVector.map(a => a(2)).map(s => s.trim.toInt)
    val monsterM = fileVector.map(a => a(3)).map(s => s.trim.toInt)
    val monsterXP = fileVector.map(a => a(4)).map(s => s.trim.toInt)
    val monsterWeaponVector = fileVector.map(a => a(5)).map(s => s.trim)
    val monsterArmorVector = fileVector.map(a => a(6)).map(s => s.trim)

    val result = {
      for (i <- monsterNames.indices)
      yield create(monsterNames(i), Stats.create(
                  new Attribute(monsterW(i)),
                  new Attribute(monsterR(i)),
                  new Attribute(monsterM(i))),
                  monsterXP(i),
                  Weapon.weaponList.find(w => w.name == monsterWeaponVector(i)).getOrElse(Weapon.createUnarmed),
                  Armor.list.find(a => a.name == monsterArmorVector(i)).getOrElse(Armor.list(0)))    
    }
    result.toList
  }

  def getRandom: Monster = {
    val randomMonster = Random.shuffle(monsterList).head
    Monster.create(randomMonster.name, 
          Stats.copy(randomMonster.stats), 
          randomMonster.xpValue,
          randomMonster.weapon,
          randomMonster.armor.getOrElse(Armor.list(0)))
  }

  def create(name: String, stats: Stats, xpValue: Int, w: Weapon, a: Armor): Monster = {
    val monster = new Monster(name, stats, xpValue)
    monster.equipArmor(a)
    monster.equipWeapon(w)
    monster
  }
}
