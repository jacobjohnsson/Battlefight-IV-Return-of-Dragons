package battlefight

case class Spell(name: String, circle: Int) {
  var dl: Int = circle match {
    case 4 => 13
    case _ => 3 + 2*dl
  }
  override def toString: String = name
  var manaCost: Int = Math.pow(2, (circle - 1)).toInt
  private var effect: Option[CombatEffect] = None
  private var damage: Option[() => Int] = None
  var beneficial = false
  def setBeneficial(b: Boolean): Spell = {beneficial = b; this}

  def addEffect(e: CombatEffect): Spell = {effect = Some(e); this}
  def getEffect: Option[CombatEffect] = effect
  def addDamage(d: Option[() => Int]): Spell = {damage = d; this}
  def getDamage: Int = {
    var result = 0
    damage match {
      case d: Some[() => Int] => result = d.get()
      case _ =>
    }
    if (beneficial) result = result * -1
    result
  }

  def canEqual(that: Any): Boolean = that.isInstanceOf[CombatEffect]
  override def equals(that: Any): Boolean = {
    that match {
      case s: Spell => canEqual(that) && this.name == s.name
      case _ => false
    }
  }
}

object Spell {
  val list: List[Spell] = updateFromFile

  def updateFromFile: List[Spell] = {
    var result: List[Spell] = List()

    val fileVector = scala.io.Source.fromFile("resources/Spells.txt").getLines.toVector.map(_.split(",")).tail

    val spellName = fileVector.map(a => a(0)).map(s => s.trim)
    val spellCircle = fileVector.map(a => a(1)).map(s => s.trim.toInt)
    val spellDamage = fileVector.map(a => a(2)).map(s => s.trim)
    val spellEffect = fileVector.map(a => a(3)).map(s => s.trim)

    for (i <- spellCircle.indices){
      var spell: Spell = create("none", 1)
      spellDamage(i) match {
        case "d6" => spell = create(spellName(i), spellCircle(i), Dice.d6)
        case "d6minus2" => spell = create(spellName(i), spellCircle(i), Dice.d6minus2)
        case "d6by2" => spell = create(spellName(i), spellCircle(i), Dice.d6by2)
        case "d6plus2" => spell = create(spellName(i), spellCircle(i), Dice.d6plus2)
        case "2d6" => spell = create(spellName(i), spellCircle(i), Dice.d62)
        case "d6plus4" => spell = create(spellName(i), spellCircle(i), Dice.d6plus4)
        case "3d6" => spell = create(spellName(i), spellCircle(i), Dice.d63)
        case _ => spell = create(spellName(i), spellCircle(i), Dice.d6)
      }
      spellEffect(i) match {
        case "Mild poison" => spell.addEffect(CombatEffect.list.find(e => e.name == "Mild poison").get)
        case "Lethal poison" => spell.addEffect(CombatEffect.list.find(e => e.name == "Lethal poison").get)
        case "Cold" => spell.addEffect(CombatEffect.list.find(e => e.name == "Cold").get)
        case "Heal" => spell.setBeneficial(true)
        case _ =>
      }
      result = result :+ spell
    }
    result.toList
  }

  def create(name: String, circle: Int): Spell = {
    new Spell(name, circle)
  }
  def create(name: String, circle: Int, effect: CombatEffect): Spell = {
    val spell = new Spell(name, circle)
    spell.addEffect(effect)
    spell
  }
  def create(name: String, circle: Int, damage: () => Int) = {
    val spell = new Spell(name, circle)
    spell.addDamage(Some(damage))
    spell
  }
}
