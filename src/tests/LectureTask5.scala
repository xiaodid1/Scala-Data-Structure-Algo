package tests

import game.maps._
import org.scalatest._
import game.physics._
import game.gameobjects._
import game.enemyai.{AIGameState, AIPlayer, BFS}

class LectureTask5 extends FunSuite {

  test("") {

    val wall1: GridLocation = new GridLocation(3, 10)
    val wall2: GridLocation = new GridLocation(3, 9)
    val wall3: GridLocation = new GridLocation(3, 8)
    val wall4: GridLocation = new GridLocation(3, 7)
    val loca1: GridLocation = new GridLocation(2, 9)
    val loca2: GridLocation = new GridLocation(4, 9)
    val loca3: GridLocation = new GridLocation(2, 3)
    val loca4: GridLocation = new GridLocation(5, 5)

    val game: AIGameState = new AIGameState
    game.levelHeight = 12
    game.levelWidth = 12
    game.wallLocations = List(wall1, wall2, wall3, wall4)
    val Game = game.levelAsGraph()
    val player: AIPlayer = new AIPlayer("p1")
    val ans = 6
    val ans1 = 5
    assert(player.distanceAvoidWalls(game,loca1,loca2)==ans)
    assert(player.distanceAvoidWalls(game,loca3,loca4)==ans1)
  }
}
