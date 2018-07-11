package battlefight

abstract class BaseAttribute {
  
  def value: Int
  def addBonus(mod: Int): Unit
  def addBaseValue(value: Int): Unit  
  
}
