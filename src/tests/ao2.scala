package tests

import game.enemyai.{AIGameState, AIPlayer, PlayerLocation}
import game.lo4_data_structures.linkedlist.LinkedListNode
import game.maps.GridLocation
import org.scalatest._

class ao2 extends FunSuite {

  test(""){

    val player: AIPlayer = new AIPlayer("p1")
    val game: AIGameState = new AIGameState
    game.levelHeight = 30
    game.levelWidth = 30
    game.playerLocations = new LinkedListNode[PlayerLocation](new PlayerLocation(2,9,"p1"),null)
    game.playerLocations = new LinkedListNode[PlayerLocation](new PlayerLocation(4,9,"p2"),game.playerLocations)
    //game.playerLocations = new LinkedListNode[PlayerLocation](new PlayerLocation(2,5,"p3"),game.playerLocations)

    println(player.decisionTree(player).value.check(game))
  }

}
