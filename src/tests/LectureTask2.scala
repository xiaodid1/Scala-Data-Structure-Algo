package tests

import game.enemyai._
import org.scalatest._
import game.lo4_data_structures.linkedlist._
import game.maps.GridLocation

class LectureTask2 extends  FunSuite {
  test(""){

    def compare(a : Double , b: Double): Boolean={
      Math.abs(a-b)< 0.001
    }
    val myList: LinkedListNode[PlayerLocation] = new LinkedListNode[PlayerLocation](new PlayerLocation(2,3,"p1"), null)
    var path: LinkedListNode[GridLocation] = new LinkedListNode[GridLocation](new GridLocation(2,3),null)
    path = new LinkedListNode[GridLocation](new GridLocation(2,4),path)
    path = new LinkedListNode[GridLocation](new GridLocation(2,7),path)
    var path2: LinkedListNode[GridLocation] = new LinkedListNode[GridLocation](new GridLocation(2,3),null)
    val myList2: LinkedListNode[PlayerLocation] = new LinkedListNode[PlayerLocation](new PlayerLocation(0,0,"p2"), null)
    var path3: LinkedListNode[GridLocation] = new LinkedListNode[GridLocation](new GridLocation(1,5),null)
    val tests: Map[List[Double],LinkedListNode[GridLocation]] = Map(
      List(0.5,1.5) -> path,
      List(0.5,0.5) -> path2
    )
    val test1: AIPlayer = new AIPlayer("p1")
    val test2: AIPlayer = new AIPlayer("p2")
    val test3: AIPlayer = new AIPlayer("p3")
    for ((i , ans) <- tests){
      val outcome = test1.pathToDirection(myList,ans)
      assert(compare(outcome.x,i(0)))
      assert(compare(outcome.y,i(1)))
    }
    val outcome2 = test2.pathToDirection(myList2,path3)
    assert(compare(outcome2.x,1.5))
    assert(compare(outcome2.y,5.5))
    val myList3: LinkedListNode[PlayerLocation] = new LinkedListNode[PlayerLocation](new PlayerLocation(10,11,"p3"), null)
    var path4: LinkedListNode[GridLocation] = new LinkedListNode[GridLocation](new GridLocation(0,1),null)
    path4 = new LinkedListNode[GridLocation](new GridLocation(0,1),path4)
    val outcome3 = test3.pathToDirection(myList3,path4)
    assert(compare(outcome3.x,-9.5))
    assert(compare(outcome3.y,-9.5))
    // TODO
  }

}
