package battlefight

class RawBonusEffect(name: String,
                     val attr: String,
                     bonus: Int,
                     time: Int) extends CombatEffect(name) with ITicking{
  var timer = {if (time == 0) -1 else time}

  def activate(stats: Stats, weapon: Weapon): Unit = {
    stats.addRawBonus(attr, bonus)
    additionalEffect match {
      case re: Some[CombatEffect] => re.get.activate(stats, weapon)
      case _ =>
    }
  }

  def deActivate(stats: Stats, weapon: Weapon): Unit = {
    stats.addRawBonus(attr, -bonus)
    additionalEffect match {
      case re: Some[CombatEffect] => re.get.deActivate(stats, weapon)
      case _ =>
    }
  }

  def extendEffect(effect: CombatEffect): Unit = {
    additionalEffect match {
      case re: Some[CombatEffect] => re.get.extendEffect(effect)
      case _ => additionalEffect = Some(effect)
    }
  }

  def tick(stats: Stats): Boolean = {
    additionalEffect match {
      case re: Some[CombatEffect] => re.get.tick(stats)
      case _ =>
    }

    timer -= 1
    if (timer > 0) true
    else false

  }
}
