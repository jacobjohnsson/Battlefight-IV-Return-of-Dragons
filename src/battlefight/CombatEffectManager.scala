package battlefight
import scala.collection.mutable.ListBuffer

class CombatEffectManager(stats: Stats, var weapon: Weapon) {
  val attributeEffects = ListBuffer[AttributeEffect]()
  val rawBonusEffects = ListBuffer[RawBonusEffect]()
  val weaponEffects = ListBuffer[WeaponEffect]()
  val stat = stats
  
  def changeWeapon(w: Weapon): Unit = {
    weapon = w
  }
  
  def reCalculate: Unit = {
    rawBonusEffects.foreach(e => e.deActivate(stats, weapon))
    stat.reCalculateAttr
    rawBonusEffects.foreach(e => e.activate(stats, weapon))
  }

  def addAttrEffect(effect: AttributeEffect): Unit = {
    attributeEffects += effect
    effect.activate(stats, weapon)
    reCalculate
  }
  
  def removeAttrEffect(effect: AttributeEffect): Unit = {
    if (attributeEffects.contains(effect)) {
      effect.deActivate(stats, weapon)
      attributeEffects -= effect
      reCalculate
    }
  }
  
  def addRawEffect(effect: RawBonusEffect): Unit = {
    rawBonusEffects += effect
    effect.activate(stats, weapon)    
  }

  def removeRawEffect(effect: RawBonusEffect): Unit = {
    if (rawBonusEffects.contains(effect)) {
      effect.deActivate(stats, weapon)
      rawBonusEffects -= effect
    }
  }
  
  def addWeaponEffect(effect: WeaponEffect): Unit = {
    weaponEffects += effect
    effect.activate(stats, weapon)
  }
  
  def removeWeaponEffect(effect: WeaponEffect): Unit = {
    if (weaponEffects.contains(effect)) {
      effect.deActivate(stats, weapon)
      weaponEffects -= effect
    }
  }
  
  def toList: List[CombatEffect] = {
    (attributeEffects ++ rawBonusEffects ++ weaponEffects).toList
  }
  
  def proc: Unit = {    
    attributeEffects.foreach(e => if (e.tick(stats)) removeAttrEffect(e))    
    rawBonusEffects.foreach(e => if (e.tick(stats)) removeRawEffect(e))
  }
}
