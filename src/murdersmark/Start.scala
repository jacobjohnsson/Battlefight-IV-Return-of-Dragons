package murdersmark

class Start(hero: battlefight.Hero) {
  val sf = new battlefight.StringFormatter

  def main(hero: battlefight.Hero): Unit = {
    val player: battlefight.Hero = hero
    menu
    
    def menu: Unit = {    
    
      scala.io.StdIn.readLine(sf.toString(description) + "\n\t" +
          "[1] Background\t [2] Explore the circus\n") match {
        case "1" => println(sf.toString(background)); menu
        case "2" => exploreCircus
        case _ => println("hm..")
      }
    }
    
    def exploreCircus: Unit = {
  
      scala.io.StdIn.readLine(sf.toString(circusDescription) + "\n\t" +
          "[1] South to games\t [2] East to zoo\t [3] North to stage\n") match {
        case "1" => CircusGames.run(player)
        case "2" => exploreCircus
        case _ => println("hm..")
      }
    }
  }
  
  val circusDescription = "You stand at the entrance to the circus. A path runs east here " + 
  "from the midway toward the traveling zoo. To the south are booths with pavilion tent tops " +
  "that provide shade for the games of skill and chance played beneath. Behind them are circus " +
  "wagons, some clearly only for transportation and equipment, while others are attractions " +
  "and portable shops for merchants. To the north is a gigantic tent erected on tall poles " + 
  "with a stage curtain pulled shut across its front. \n"
  
  val description: String = "All of the townsfolk has become aware of the circus when a contingent" + 
  " arrives to restock their food and supply and hang up colorful posters." + 
  " You and a few townspeople wander out to the fields where the Umbra Carnival sets up, hoping to nose " + 
  " around and get a sense of this year's attractions-a common event tolerated by" + 
  " the circus as long as the locals stay out ofthe way." + 
  " Carnival barkers call out to people on the streets, promising thrills, chills, and delights" + 
  " for people of all ages (at the modest price of a few copper coins). \n"
  
  val background: String = "The town of llsurian in Varisia was established by Ilsur, a " +
"First Sword of the Knights of Aroden in the city of Korvosa. " +
"With the crumbling of the Chelish Empire, Ilsur wanted " +
"a militant-leaning meritocracy to replace noble rule in " +
"Korvosa, but had to eventually concede defeat roughly " +
"8o years ago. He marched his troops to the west bank of " +
"the Skull River where it empties into Lake Syrantula, and " +
"awaited a chance to return and seize Korvosa by military " +
"force. He died waiting for the opportunity, and his army " +
"gradually transformed into a strong community of " +
"independent fishers and foresters owing loyalty to neither " +
"Korvosa nor Magnimar. The soldiers brought by Ilsur " +
"and their descendants are ethnic Chelaxians, and most of " +
"them have the strong features, pale skin, and dark hair of " +
"that group. Most of them also harbor prejudice against the " +
"native Varisians, and believe the stereotypes that Varisians " +
"are thieves and layabouts. Since very few permanent " +
"residents of Ilsurian are ethnic Varisians, this racism has " +
"rarely been a problem. Yet all that changed when Ilsurian " +
"became the latest stopping point for a traveling circus " +
"known as the Umbra Carnival." +
"    The adventurer Almara Delisen founded the Umbra" +
"Carnival over a decade ago. Almara had seen her companions" +
"slaughtered during an ill-fated venture, and in grief" +
"turned to entertainment as a means to sustain herself. An" +
"illusionist, Almara used her magic to become the center of" +
"a traveling show that passed through small towns starved for" +
"entertainment and a glimpse of life beyond their borders." +
"With time, other performers, acts, and attractions joined" +
"her circus-lost souls and lonely hearts, eager to see the" +
"world or leave some part of it behind. Years later, the circus" +
"has grown to the size of a small village, with a diverse array" +
"of attractions and amusements from all over A vi stan. The" +
"sincere intent of the carnival is to provide much-needed" +
"entertainment at a price communities can afford. Once it" +
"arrived in Ilsurian, however, this goal became complicated" +
"by the machinations of Ilusarian's thieves' guild, the" +
"Gilded Hands." +
    "The Gilded Hands are run by a married Chelish couple" +
"from Korvosa. Once young hustlers for the Cerulean" +
"Society, they were run out of town by an influential Sczarni" +
"gang leader over a personal grudge. They moved to Ilsurian," +
"and took on the aliases ofBorvius and Robella Monchello" +
"to avoid pursuit and continuation of the vendetta. In" +
"public, Borvius runs Ilsurian Storage &z Hauling, a cartage" +
"company that ships goods and stores cargo, and Robella" +
"owns Mistress Robella's Curiosity Shop just next door." +
"In secret, they jointly control all the serious criminal" +
"activity in Ilsurian. Recently, they have been constrained" +
"by the size of the town itself. The guild has reached a" +
"tipping point where it dare not undertake more profitable" +
"criminal ventures without risking a strong response from" +
"the town's sheriff and the larger community-at least, not" +
"without a scapegoat." +
    "The arrival of the Umbra Carnival presented just such" +
"an opportunity. With the circus in town, the Gilded Hands" +
"quickly planned to perpetrate their boldest schemes and pin" +
"the blame on carnival folk, who are seen by the townspeople" +
"as even less trustworthy than other ethnic Varisians." +
"The day the circus arrived and started to unpack," +
"the Monchellos dispatched spies to circulate among" +
"the curious local onlookers to search for a likely mark" +
"they could set up. Their chosen victim is Jherizhana," +
"a gynosphinx and the carnival's Master of Shows (and" +
"also, unknown to anyone outside the circus, an elaborate" +
"illusion). The thieves' guild rightly as sumes that if the" +
"murders appear to be perpetrated by the sphinx, the" +
"circus will be blamed for the subsequent robberies, and for" +
"bringing such a dangerous and uncontrollable creature to" +
"town. Their first target has been selected, and is slated to" +
"be murdered just after the opening night's big top show. \n"
}
