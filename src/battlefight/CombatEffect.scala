package battlefight

abstract class CombatEffect(val name: String){
  var additionalEffect: Option[CombatEffect] = None
  def activate(stats: Stats, weapon: Weapon): Unit
  def deActivate(stats: Stats, weapon: Weapon): Unit
  def extendEffect(effect: CombatEffect): Unit

  def canEqual(that: Any): Boolean = that.isInstanceOf[CombatEffect]
  override def equals(that: Any): Boolean = {
    that match {
      case ce: CombatEffect => canEqual(that) && this.name == ce.name
      case _ => false
    }
  }
  
  // returns TRUE when it is done!
  def tick(stats: Stats): Boolean
}

// Exempel på hur man kombinerar effekter!
//    val effect = new AttributeEffect("W Buff with health removal", "w", 4, 4)
//    effect.extendEffect(new TickingEffect("HealthRemover", "hp", -3, 4))
// hero.addEffect(effect)