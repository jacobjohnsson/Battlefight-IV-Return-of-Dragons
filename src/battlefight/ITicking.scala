package battlefight

trait ITicking {
  def tickTime(stats: Stats): Boolean
}