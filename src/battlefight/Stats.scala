package battlefight

class Stats(val w: Attribute, val r: Attribute, val m: Attribute,
            var maxHP: Int, var maxMana: Int, var defense: Int) {
  var currentHP = maxHP
  var currentMana = maxMana
  var damageReduction: Int = 0

  def mainAttr: Int = {
    Vector(w.value, r.value, m.value).max
  }

  def addAttrBonus(attr: String, bonus: Int): Unit = {
    attr match {
      case "w" => w.addBonus(bonus); currentHP += bonus
      case "r" => r.addBonus(bonus)
      case "m" => m.addBonus(bonus); currentMana += bonus
    }
  }

  def addRawBonus(attr: String, bonus: Int):  Unit = {
    attr match {
      case "hp" => maxHP += bonus; currentHP += bonus
      case "mana" => {
        maxMana += bonus
        currentMana += bonus
        if (maxMana < 0) currentMana = 0
      }
      case "defense" => defense += bonus
      case "dr" => damageReduction += bonus
    }
  }

  def reCalculateAttr: Unit = {
    maxHP = w.value + 6
    defense = (r.value + w.value) / 2 + 4
    maxMana = {
      if (m.value * 2 < 0) 0
      else m.value * 2
    }
  }

  def takeDamage(damage: Int): Int = {
    var totalDamage = 0
    if (damage > damageReduction) {
      totalDamage = damage - damageReduction 
    } else if (damage < 0) {
      totalDamage = damage
    }
    currentHP -= totalDamage
    
    if (currentHP > maxHP) currentHP = maxHP
    totalDamage
  }

  def reset: Unit = {
    currentHP = maxHP
    currentMana = maxMana
  }
}

object Stats {
  def create(w: Attribute, r: Attribute, m: Attribute): Stats = {
    new Stats(w, r, m, w.value + 6, m.value * 2, (w.value + r.value) / 2 + 4)
  }

  def copy(stats: Stats): Stats = {
    new Stats(stats.w, stats.r, stats.m,
        stats.w.value + 6, stats.m.value * 2, (stats.w.value + stats.r.value) / 2 + 4)
  }
}
