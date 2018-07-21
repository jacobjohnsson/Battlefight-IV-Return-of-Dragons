package battlefight

class TickingEffect(name: String,
                    val attr: String,
                    bonus: Int,
                    time: Int) extends CombatEffect(name) {

  var timer = if (time == 0) -1 else time

  def activate(stats: Stats, weapon: Weapon): Unit = {
    additionalEffect match {
      case e: Some[CombatEffect] => e.get.activate(stats, weapon)
      case _ =>
    }
  }

  def deActivate(stats: Stats, weapon : Weapon): Unit = {
    additionalEffect match {
      case e: Some[CombatEffect] => e.get.deActivate(stats, weapon)
      case _ =>
    }
  }

  def extendEffect(effect: CombatEffect): CombatEffect = {
    additionalEffect match {
      case e: Some[CombatEffect] => e.get.extendEffect(effect)
      case _ => additionalEffect = Some(effect)
    }
    this
  }

  def tickTime(stats: Stats): Boolean = {
    additionalEffect match {
      case e: Some[CombatEffect] => e.get.tickTime(stats)
      case _ =>
    }
    stats.addRawBonus(attr, bonus)

    timer -= 1
    if (timer > 0) true
    else false
  }
}
