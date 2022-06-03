package game.enemyai

import game.lo4_data_structures.graphs._
import game.enemyai.Queue

object BFS {

  def bfs[A](graph: Graph[A], startID: Int, endID: Int): Int = {

    var explored: Set[Int] = Set(startID)

    val toExplore: Queue[Int] = new Queue()
    toExplore.enqueue(startID)

    var map: Map[Int, Int] = Map()

    var list:List[Int] = List()

    while (!toExplore.empty()){
      val nodeToExplore = toExplore.dequeue()
      for (node <- graph.adjacencyList(nodeToExplore)) {
        if(!explored.contains(node)){
          toExplore.enqueue(node)
          explored = explored + node
          map = map + (node -> nodeToExplore.toInt)
        }
      }
    }
    list = list :+ endID
    while (list.last != startID) {
      list = list :+ map(list.last)
    }
    list.length - 1
  }


}
