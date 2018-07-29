package battlefight

class SpellBook(val n: String, val c: Int, val spells: List[Spell]) extends Item(n, c){
  val description = {
    val sb = new StringBuilder()
    sb.append(name + ", \t" + cost + "g")
    
    spells.foreach(spell => sb.append("\n" + spell))
    
    sb.toString
  }
  
  override def toString: String = name

  
  def addSpell(spell: Spell): SpellBook = {
    new SpellBook(n, c, spells :+ spell)
  }
}