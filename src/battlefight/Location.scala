package battlefight

abstract class Location(player: Character) {
  def description: String
  def run: Unit
}