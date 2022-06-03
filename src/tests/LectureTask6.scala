package tests

import game.enemyai.{AIGameState, AIPlayer, PlayerLocation}
import game.lo4_data_structures.linkedlist.LinkedListNode
import game.maps.GridLocation
import org.scalatest._

class LectureTask6 extends FunSuite {

  test(""){

    val playerid: AIPlayer = new AIPlayer("p1")

    val wall1: GridLocation = new GridLocation(3, 10)
    val wall2: GridLocation = new GridLocation(3, 9)
    val wall3: GridLocation = new GridLocation(3, 8)
    val wall4: GridLocation = new GridLocation(3, 7)
    val game: AIGameState = new AIGameState
    game.levelHeight = 12
    game.levelWidth = 12
    game.wallLocations = List(wall1, wall2, wall3, wall4)
    game.playerLocations = new LinkedListNode[PlayerLocation](new PlayerLocation(2,9,"p1"),null)
    game.playerLocations = new LinkedListNode[PlayerLocation](new PlayerLocation(4,9,"p2"),game.playerLocations)
    game.playerLocations = new LinkedListNode[PlayerLocation](new PlayerLocation(2,0,"p3"),game.playerLocations)
    val ans: PlayerLocation = new PlayerLocation(4,9,"p2")
    assert(playerid.closestPlayerAvoidWalls(game).playerId == ans.playerId)

    val Playerid: AIPlayer = new AIPlayer("P1")

    val Wall1: GridLocation = new GridLocation(3, 10)
    val Wall2: GridLocation = new GridLocation(3, 9)
    val Wall3: GridLocation = new GridLocation(3, 8)
    val Wall4: GridLocation = new GridLocation(3, 7)
    val Game: AIGameState = new AIGameState
    Game.levelHeight = 12
    Game.levelWidth = 12
    Game.wallLocations = List(Wall1, Wall2, Wall3, Wall4)
    Game.playerLocations = new LinkedListNode[PlayerLocation](new PlayerLocation(2,9,"P1"),null)
    Game.playerLocations = new LinkedListNode[PlayerLocation](new PlayerLocation(4,9,"P2"),Game.playerLocations)
    Game.playerLocations = new LinkedListNode[PlayerLocation](new PlayerLocation(4,10,"P3"),Game.playerLocations)
    val ans1: PlayerLocation = new PlayerLocation(4,10,"P3")
    assert(Playerid.closestPlayerAvoidWalls(Game).playerId == ans1.playerId)

  }
}
