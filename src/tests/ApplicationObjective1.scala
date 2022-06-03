package tests

import game.enemyai.{AIGameState, AIPlayer, PlayerLocation}
import game.lo4_data_structures.linkedlist.LinkedListNode
import game.maps.GridLocation
import org.scalatest._

class ApplicationObjective1 extends FunSuite {

  test(""){

    val test: AIPlayer = new AIPlayer("p1")
    val wall1: GridLocation = new GridLocation(3, 10)
    val wall2: GridLocation = new GridLocation(3, 9)
    val wall3: GridLocation = new GridLocation(3, 8)
    val wall4: GridLocation = new GridLocation(3, 7)
    val game: AIGameState = new AIGameState
    game.levelHeight = 30
    game.levelWidth = 30
    game.playerLocations = new LinkedListNode[PlayerLocation](new PlayerLocation(2,9,"p1"),null)
    game.playerLocations = new LinkedListNode[PlayerLocation](new PlayerLocation(4,9,"p2"),game.playerLocations)
    game.playerLocations = new LinkedListNode[PlayerLocation](new PlayerLocation(2,5,"p3"),game.playerLocations)

    val outcome: LinkedListNode[GridLocation] = test.getPath(game)
    var ans: LinkedListNode[GridLocation] = new LinkedListNode[GridLocation](new GridLocation(4,9),null)
    ans = new LinkedListNode[GridLocation](new GridLocation(3,9),ans)
    ans = new LinkedListNode[GridLocation](new GridLocation(2,9),ans)

    var check1: List[GridLocation] = List()
    outcome.foreach((f:GridLocation) => check1 = check1 :+ f)
    var check2: List[GridLocation] = List()
    ans.foreach((f:GridLocation) => check2 = check2 :+ f)

    assert(check1.length == check2.length)
    var count: Int = 0
    for (i <- check1){
      assert(check1(count).x == check2(count).x)
      assert(check1(count).y == check2(count).y)
      count+=1
    }

    game.wallLocations = List(wall1,wall2,wall3,wall4)
    val outcome1: LinkedListNode[GridLocation] = test.getPath(game)
    var ans1: LinkedListNode[GridLocation] = new LinkedListNode[GridLocation](new GridLocation(2,5),null)
    ans1 = new LinkedListNode[GridLocation](new GridLocation(2,6),ans1)
    ans1 = new LinkedListNode[GridLocation](new GridLocation(2,7),ans1)
    ans1 = new LinkedListNode[GridLocation](new GridLocation(2,8),ans1)
    ans1 = new LinkedListNode[GridLocation](new GridLocation(2,9),ans1)

    var check3: List[GridLocation] = List()
    outcome1.foreach((f:GridLocation) => check3 = check3 :+ f)
    var check4: List[GridLocation] = List()
    ans1.foreach((f:GridLocation) => check4 = check4 :+ f)

    assert(check3.length == check4.length)
    var count1: Int = 0
    for (i <- check3){
      assert(check3(count).x == check4(count).x)
      assert(check3(count).y == check4(count).y)
      count1+=1
    }

  }
}
