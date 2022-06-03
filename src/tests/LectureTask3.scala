package tests

import game.enemyai._
import org.scalatest._
import game.lo4_data_structures.linkedlist._
import game.maps.GridLocation

class LectureTask3 extends FunSuite {

  //Temp
  //val test: AIPlayer = new AIPlayer()
  //val start: GridLocation = new GridLocation()
  //val end: GridLocation = new GridLocation()
  //val ans: List[List[Int]] = List(List(), List(), List(), List(), List(), List(), List())


  test("") {
    val test: AIPlayer = new AIPlayer("p")
    val start: GridLocation = new GridLocation(5, 5)
    val end: GridLocation = new GridLocation(2, 2)
    val ans: List[List[Int]] = List(List(5, 5), List(5, 4), List(5, 3), List(5, 2), List(4, 2), List(3, 2), List(2, 2))
    val outcome: LinkedListNode[GridLocation] = test.computePath(start, end)
    assert(ans.length == outcome.size())
    assert(outcome(6).value.x == end.x)
    assert(outcome(6).value.y == end.y)

    val test1: AIPlayer = new AIPlayer("x")
    val start1: GridLocation = new GridLocation(0, 0)
    val end1: GridLocation = new GridLocation(5, 6)
    val ans1: List[List[Int]] = List(List(0, 0), List(0, 1), List(0, 2), List(0, 3), List(0, 4), List(0, 5), List(0, 6), List(1, 6),
      List(2, 6), List(3, 6), List(4, 6), List(5, 6))
    val outcome1: LinkedListNode[GridLocation] = test1.computePath(start1, end1)
    assert(ans1.length == outcome1.size())
    assert(outcome1(11).value.x == end1.x)
    assert(outcome1(11).value.y == end1.y)

  }
}
