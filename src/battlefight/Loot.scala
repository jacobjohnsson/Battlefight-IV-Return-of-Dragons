package battlefight

class Loot(item: Weapon, val gold: Int) {
  def get: Weapon = item
}

object Loot {
  def create: Loot = {
    val random = scala.util.Random.nextInt(Weapon.weaponList.length)
    new Loot(Weapon.weaponList(random), random * 2)
  }
}