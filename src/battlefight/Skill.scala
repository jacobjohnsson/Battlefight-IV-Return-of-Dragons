package battlefight

class Skill(val name: String, val effect: CombatEffect) {
  def canEqual(s: Any): Boolean = s.isInstanceOf[Skill]

  override def equals(that: Any): Boolean = {
    that match {
      case that: Skill => that.canEqual(this) && that.name == this.name
      case _ => false
    }
  }

  override def toString: String = {
    name
  }
}

object Skill {
  val list: List[Skill] = List(
    new Skill("Unarmed", new WeaponEffect("Unarmed", 2)),
    new Skill("Axes", new WeaponEffect("Axes", 2)),
    new Skill("Bows", new WeaponEffect("Bows", 2)),
    new Skill("Daggers", new WeaponEffect("Daggers", 2)),
    new Skill("Blunt", new WeaponEffect("Blunt", 2)),
    new Skill("Polearms", new WeaponEffect("Polearms", 2)),
    new Skill("Firearms", new WeaponEffect("Firearms", 2)),
    new Skill("Swords", new WeaponEffect("Swords", 2)))
}
