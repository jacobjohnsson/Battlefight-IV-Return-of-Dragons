package battlefight
import scala.collection.mutable.ListBuffer

class CombatEffectManager(stats: Stats, var weapon: Weapon) {
  val attributeEffects = ListBuffer[AttributeEffect]()
  val rawBonusEffects = ListBuffer[RawBonusEffect]()
  val weaponEffects = ListBuffer[WeaponEffect]()
  val tickingEffects = ListBuffer[TickingEffect]()
  val stat = stats
  
  def changeWeapon(w: Weapon): Unit = {
    weapon = w
  }
  
  def reCalculate: Unit = {
    rawBonusEffects.foreach(e => e.deActivate(stats, weapon))
    stat.reCalculateAttr
    rawBonusEffects.foreach(e => e.activate(stats, weapon))
  }
  
  def addEffect(effect: CombatEffect): Unit = {
    effect match {
      case a: AttributeEffect => {
        attributeEffects += a
        a.activate(stats, weapon)
        reCalculate
      }
      case r: RawBonusEffect => {
        r.activate(stats, weapon)
        rawBonusEffects += r
        reCalculate
      }
      case w: WeaponEffect => weaponEffects += w; w.activate(stats, weapon)
      case t: TickingEffect => tickingEffects += t;
      }
  }
  
  def removeEffect(effect: CombatEffect): Unit = {
    effect match {
      case a : AttributeEffect => {
        if (attributeEffects.contains(a)) {
          a.deActivate(stats, weapon)
          attributeEffects -= a
          reCalculate
        }
      }
      case r: RawBonusEffect => {
        if (rawBonusEffects.contains(r)) {
          r.deActivate(stats, weapon)
          rawBonusEffects -= r
        }
      }
      case w: WeaponEffect => {
        if (weaponEffects.contains(w)) {
          w.deActivate(stats, weapon)
          weaponEffects -= w
        }
      }
    }
  }
  
  def toList: List[CombatEffect] = {
    (attributeEffects ++ rawBonusEffects ++ weaponEffects ++ tickingEffects).toList
  }
  
  def proc(wRoll: Int): Unit = {
    tickingEffects.foreach(t => if (t.tickTime(stats)) removeEffect(t))    
    attributeEffects.foreach(e => if (e.tickTime(stats)) removeEffect(e))    
    rawBonusEffects.foreach(e => if (e.tickTime(stats)) removeEffect(e))
  }
}
