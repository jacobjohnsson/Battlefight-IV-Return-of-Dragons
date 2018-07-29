package battlefight

abstract class CombatEffect(val name: String){
  var additionalEffect: Option[CombatEffect] = None
  var dl: Int = Integer.MAX_VALUE
  def activate(stats: Stats, weapon: Weapon): Unit
  def deActivate(stats: Stats, weapon: Weapon): Unit
  def extendEffect(effect: CombatEffect): CombatEffect


  override def equals(that: Any): Boolean = {
    def canEqual(that: Any): Boolean = that.isInstanceOf[CombatEffect]
    that match {
      case ce: CombatEffect => canEqual(that) && this.name == ce.name
      case _ => false
    }
  }

  // returns TRUE when it is done!
  def tickTime(stats: Stats): Boolean
}

object CombatEffect {
  val list: List[CombatEffect] = List(
      new TickingEffect("Mild poison", "hp", -1, 4),
      new RawBonusEffect("Lethal poison", "hp", -2, 6)
          .extendEffect(new TickingEffect("Lethal poison", "hp", -2, 6))
          .extendEffect(new RawBonusEffect("Lethal poison", "mana", -1, 6)),
      new AttributeEffect("Cold", "w", -2, 10))
}

// Exempel på hur man kombinerar effekter!
//    val effect = new AttributeEffect("W Buff with health removal", "w", 4, 4)
//    effect.extendEffect(new TickingEffect("HealthRemover", "hp", -3, 4))
//    hero.addEffect(effect)
