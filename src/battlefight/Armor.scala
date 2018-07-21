package battlefight

class Armor(val n: String, val defense: Int, val ap: Int, val c: Int) extends Item(n, c){
  def description: String = name + ", \t" + defense + ", \t" + ap + ", \t" + cost + "g"
  override def toString: String = name
}

object Armor {
  val list: List[Armor] = updateFromFile

  def updateFromFile: List[Armor] = {
    val fileVector = scala.io.Source.fromFile("resources/Armor.txt").getLines.toVector.map(_.split(","))

    val armorNames = fileVector.map(a => a(0)).tail
    val armorDefense = fileVector.map(a => a(1)).tail.map(s => s.trim.toInt)
    val armorPenalty = fileVector.map(a => a(2)).tail.map(s => s.trim.toInt)
    val armorCost = fileVector.map(a => a(3)).tail.map(s => s.trim.toInt)

    val result = for (i <- 0 until armorNames.length) yield {
      new Armor(armorNames(i), armorDefense(i), armorPenalty(i), armorCost(i))
    }
    result.toList
  }
}
