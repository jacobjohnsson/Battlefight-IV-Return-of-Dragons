package battlefight

import scala.io.StdIn
import scala.util.{Try, Success, Failure}

object Battlefight{
  
  def main(args: Array[String]): Unit = {
    mainMenu()
  }
  
  var heroes: Set[Hero] = Set()
  val weaponList: List[Weapon] = Weapon.updateFromFile
  Monster.updateFromFile

  // Main menu loop.
  def mainMenu(): Unit = {
    var exit = false;

    while(exit == false){

      println("\n\tBattle Fight IV: Return of Dragons." +
      "\n\n\t[1] New Game" +
      "\n\t[2] Hall of Fame" +
      "\n\t[3] Exit Game" + '\n')

      var userinput = readLine

      userinput match {
        case "1" => newGame()
        case "2" => viewHeroes()
        case "3" => println("Exit Game"); exit = true
        case _ => println("Please choose one of the options.")
      }
    }
  }

  // Sets off the game loop
  def newGame(): Unit = {
    val game: Location = new MainLocation(createHero)
    game.run
  }

  def createHero(): Hero = {
    var points = 10
    var w, r, m = 0
    var name = readLine("Name: ")
    var weapon: Weapon = Weapon.createUnarmed
    var race: String = "Human"
    var skill: Skill = new Skill("none", new WeaponEffect("none", 0))

    while (points > 0){
      println("Distribute your attribute points!\n" +
              "\n Remaining points: " + points +
              "\n[1] Warrior: " + w +
              "\n[2] Rogue: " + r +
              "\n[3] Mage: " + m)
      var userInput = readLine
      userInput match {
        case "1" => w = addPoint(w)
        case "2" => r = addPoint(r)
        case "3" => m = addPoint(m)
        case _ => println("\n\tInvalid input.\n")
      }
    }

    // makes sure the points are distibuted correctly
    def addPoint(input: Int): Int = {
      var result = input
      if (result > 5) {
        println("Attribute is full.")
      } else {
        result += 1; points -= 1
      }
      result
    }

    var legitchoice = false
    while(!legitchoice){

      println("Choose your starting weapon: \n")

      weaponList.indices.foreach(x => println("[" + (x + 1) + "] " + weaponList(x).name + ",\t " + weaponList(x).skill))

      var userInput = scala.io.StdIn.readLine()

      var weaponChoice: Int = 0
      var intConvert = false
      while(!intConvert) {
        Try(weaponChoice = userInput.toInt) match {
          case Success(int) => {
            intConvert = true
            if (weaponList.indices.contains(weaponChoice - 1)) {
              weapon = weaponList(weaponChoice - 1)
              legitchoice = true
            }
          }
          case _ => println("not a choice")
        }
      }
    }

    legitchoice = false
    while(!legitchoice) {
      println("Choose a race: \n" +
            "[1] Human\n" +
            "[2] Dwarf\n" +
            "[3] Elf\n" +
            "[4] Halfling\n")
      var userInput = scala.io.StdIn.readLine()

      userInput match {
        case "1" => legitchoice = true
        case "2" => race = "Dwarf"; legitchoice = true
        case "3" => race = "Elf"; legitchoice = true
        case "4" => race = "Halfling"; legitchoice = true
        case _ => println("Please choose one of the options")
      }

    }

    legitchoice = false
    while(!legitchoice) {
      println("Choose a skill: \n" +
            "[1] Unarmed\n" +
            "[2] Axes\n" +
            "[3] Bows\n" +
            "[4] Daggers\n" + 
	          "[5] Blunt\n" + 
	          "[6] PoleArms\n" +
	          "[7] Swords\n")
      var userInput = scala.io.StdIn.readLine()

      userInput match {
        case "1" => skill = (new Skill("Unarmed", new WeaponEffect("Unarmed", 2))); legitchoice = true
        case "2" => skill = (new Skill("Axes", new WeaponEffect("Axes", 2))); legitchoice = true
        case "3" => skill = (new Skill("Bows", new WeaponEffect("Bows", 2))); legitchoice = true
        case "4" => skill = (new Skill("Daggers", new WeaponEffect("Daggers", 2))); legitchoice = true
        case "5" => skill = (new Skill("Blunt", new WeaponEffect("Blunt", 2))); legitchoice = true
        case "6" => skill = (new Skill("Polearms", new WeaponEffect("Polearms", 2))); legitchoice = true
        case "7" => skill = (new Skill("Swords", new WeaponEffect("Swords", 2))); legitchoice = true
        case _ => println("Please choose one of the options")
        }
    }

    println("Well chosen.")

    val hero = Hero.create(name, Stats.create(new Attribute(w),
                                    new Attribute(r),
                                    new Attribute(m)),
                                    race)
    hero.addItem(weapon)
    hero.equipWeapon(weapon)
    hero.addSkill(skill)
    heroes = heroes + hero
    hero
  }

  // prints the name of all characters
  // TODO: make this a separate menu to allow the player to select the
  // character he/she wishes to use.
  def viewHeroes(): Unit = {
    println("All characters: ")
    heroes.foreach(c => println(c.toString))
  }
}
