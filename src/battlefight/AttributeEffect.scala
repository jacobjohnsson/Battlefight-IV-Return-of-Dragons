package battlefight

class AttributeEffect(name: String,
                      val attr: String,
                      bonus: Int,
                      time: Int)
                      extends CombatEffect(name) with ITicking {
  var timer = {if (time == 0) -1 else time}

  def activate(stats: Stats, weapon: Weapon): Unit = {
    stats.addAttrBonus(attr, bonus)
    additionalEffect match {
      case ae: Some[CombatEffect] => ae.get.activate(stats, weapon)
      case _ =>
    }
  }

  def deActivate(stats: Stats, weapon: Weapon): Unit = {
    stats.addAttrBonus(attr, bonus * (-1))
    additionalEffect match {
      case ae: Some[CombatEffect] => ae.get.deActivate(stats, weapon)
      case _ =>
    }
  }

  def extendEffect(effect: CombatEffect): CombatEffect = {
    additionalEffect match {
      case ae: Some[CombatEffect] => ae.get.extendEffect(effect)
      case _ => additionalEffect = Some(effect)
    }
    this
  }

  def tickTime(stats: Stats): Boolean = {
    
    additionalEffect match {
      case ae: Some[CombatEffect] => ae.get.tickTime(stats)
      case _ => 
    }
    
    timer -= 1
    if (timer == 0) true
    else false

  }
}
