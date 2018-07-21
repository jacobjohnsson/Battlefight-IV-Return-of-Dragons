package battlefight
import scala.util.Random

case class Monster (n: String, s: Stats, xpValue: Int) extends Character(n, s) {

  override def toString: String = {
    "Monster: " + name 
  }

  def hitWithWeapon(hit: Int, damage: Int): Unit = {
    if (hit > defense) {
      println("You did " + stats.takeDamage(damage) + " damage.")
    } else {
      println("You missed!")
    }
  }
  override def addSpell[C](spell: Spell): Monster = {
    spellBook += spell
    this
  }
  
  def act: String = {
    if (currentHP == 1) "flee"
    else if (!spellBook.isEmpty) "spell"
    else "attack"
  }
}

object Monster {
  val monsterList: List[Monster] = updateFromFile

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
    val monsterSpellVector = fileVector.map(a => a(7)).map(s => s.trim)

    val result = {
      for (i <- monsterNames.indices)
      yield {
        val monster = create(monsterNames(i), Stats.create(
                        new Attribute(monsterW(i)),
                        new Attribute(monsterR(i)),
                        new Attribute(monsterM(i))),
                        monsterXP(i),
                        Weapon.weaponList.find(w => w.name == monsterWeaponVector(i)).getOrElse(Weapon.createUnarmed),
                        Armor.list.find(a => a.name == monsterArmorVector(i)).getOrElse(Armor.list(0)))
                        
        val monsterSpells = monsterSpellVector(i).split(";").foreach(x => {
            if (Spell.list.filter(s => s.name == x).size > 0)
              monster.addSpell(Spell.list.find(s => s.name == x).get)
            })
        
        monster
      }
    }
    result.toList
  }

  def getRandom: Monster = {
    val randomMonster = Random.shuffle(monsterList).head
    val monster = Monster.create(randomMonster.name,
          Stats.copy(randomMonster.stats),
          randomMonster.xpValue,
          randomMonster.weapon,
          randomMonster.armor.getOrElse(Armor.list(0)))
    randomMonster.spellBook.foreach(monster.addSpell(_))
    monster
  }

  def create(name: String, stats: Stats, xpValue: Int, w: Weapon, a: Armor): Monster = {
    val monster = new Monster(name, stats, xpValue)
    monster.equip(a)
    monster.equip(w)
    monster
  }
}
