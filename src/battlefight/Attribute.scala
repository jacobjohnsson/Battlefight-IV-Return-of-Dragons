package battlefight

class Attribute(var base: Int) extends BaseAttribute{
  var bonus: Int = 0
  
  def addBonus(mod: Int): Unit = {
    bonus += mod
  }
  
  def addBaseValue(value: Int): Unit = base += value

  def value: Int = base + bonus
}
