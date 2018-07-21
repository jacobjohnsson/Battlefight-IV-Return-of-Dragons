package battlefight

class WeaponEffect(name: String, bonus: Int) extends CombatEffect(name){

  def activate(stats: Stats, weapon: Weapon): Unit = {
    weapon.addBonus(bonus)
    additionalEffect match {
      case we: Some[CombatEffect] => we.get.activate(stats, weapon)
      case _ =>
    }
  }

  def deActivate(stats: Stats, weapon: Weapon): Unit = {
    weapon.addBonus(-bonus)
    additionalEffect match {
      case we: Some[CombatEffect] => we.get.deActivate(stats, weapon)
      case _ =>
    }
  }

def extendEffect(effect: CombatEffect): CombatEffect = {
  additionalEffect match {
    case we: Some[CombatEffect] => we.get.extendEffect(effect)
    case _ => additionalEffect = Some(effect)
  }
  this
}

  def tickTime(stats: Stats): Boolean = {
    additionalEffect match {
      case e: Some[CombatEffect] => e.get.tickTime(stats)
      case _ => true
    }
  }
}
