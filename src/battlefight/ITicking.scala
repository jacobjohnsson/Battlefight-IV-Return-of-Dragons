package battlefight

trait ITicking {
  def tick(stats: Stats): Boolean
}