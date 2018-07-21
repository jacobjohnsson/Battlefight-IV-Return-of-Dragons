package battlefight

class StringFormatter {
  def toString(string: String): String = {
    val separator: String = "\n"
    val interval = 75
    val sb = new StringBuilder(string)
    
    for (i <- 1 to string.length / interval) {
      var x = i * interval
      while(sb.charAt(x).toString != " ") x += 1
      sb.insert(x + 1, separator)
    }
    
    sb.toString
  }
}