package tests

import game.enemyai._
import org.scalatest._
import game.lo4_data_structures.linkedlist._

class LectureTask1 extends FunSuite {


  test("your test") {
    val test1 : AIPlayer = new AIPlayer("p1")
    var myList: LinkedListNode[PlayerLocation] = new LinkedListNode[PlayerLocation](new PlayerLocation(2,3,"p1"), null)
    myList = new LinkedListNode[PlayerLocation](new PlayerLocation(2,4,"p12"), myList)
    myList = new LinkedListNode[PlayerLocation](new PlayerLocation(2,5,"p10"), myList)

    val ans: PlayerLocation = test1.locatePlayer("p1",myList)
    assert(ans.x == 2,(ans.x))
    assert(ans.y == 3,(ans.y))
    assert(ans.playerId == "p1",test1.id)

    val ans2: PlayerLocation = test1.closestPlayer(myList)
    val outcome: PlayerLocation = new PlayerLocation(2,4,"p12")
    assert(ans2.x == outcome.x)
    assert(ans2.y == outcome.y)
    assert(ans2.playerId == outcome.playerId)


    // TODO









  }

}
