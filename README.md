# Battlefight-IV-Return-of-Dragons
A text based rpg I'm working on for fun. 

"Screenshots" from the game: 
_____________________________________________________
	 Hero: Frodo Baggins, Halfling	 Level: 1	 Gold: 15
	 Warrior:   3 (+ 0)	 HP:      7/7
	 Rogue:     8 (+ 2)	 Defense: 11
	 Mage:      1 (+ 0)	 Mana:    2/2

	 Weapon:    Dagger
	 Armor:     Leather armor
	 DR:        0

	 Skills:    Daggers, 
	 Modifiers: Exceptional Attribute Rogue, Weak, Daggers, 
	[1] View Inventory	[2] Return	[3] Open spellbook
_____________________________________________________
You've encountered a Cutthroat. Fight!


PLAYER- HP: 	7	MANA: 	2
CUTTHROAT- HP: 	9
-------------------------------------------------
Your options are: 

	[1] Attack 		    [4] View Hero 
	[2] Cast Spell 		[5] View Monster 
	[3] Run
_____________________________________________________

It runs at about 1 frame per command.

Noteworthy stuff:
  - The way combat effects are implemented using a system similar to decorator pattern. 
  - How the damage value of a weapon is a function that is passed around until it is evaluated by the Hero object.
  - Templeta pattern is used for seperating the descriptions of Hero and Monsters, making it possible to not override their inherited method while still essentially having two different methods. 

Things that needs improvement:
  - How monsters are generated. There should be some kind of template in a json format that allows for some variation at runtime.
  - The inventory and item system.
  
Combat effects:
  - abstract CombatEffect
    - concrete WeaponEffect
    - concrete AttributeEffect
    - concrete RawBonusEffect
    - concrete TickingEffect
