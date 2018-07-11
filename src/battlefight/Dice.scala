package battlefight

object Dice {
  def roll(sides: Int): Int = scala.util.Random.nextInt(sides) + 1

  def explodingRoll(sides: Int): Int = {
    var result = 0
    var roll = Dice.roll(sides)

    while (roll == sides) {
      result += roll
      roll = Dice.roll(sides)
    }
    result + roll
  }

  val d6 = () => Dice.explodingRoll(6)
  val d6by2 = () => Dice.explodingRoll(6) / 2
  val d6minus2 = () => Vector(Dice.explodingRoll(6) - 2, 0).max
  val d6plus2 = () => Dice.explodingRoll(6) + 2
  val d6plus4 = () => Dice.explodingRoll(6) + 4
  val d62 = () => Dice.explodingRoll(6) + Dice.explodingRoll(6)
}
