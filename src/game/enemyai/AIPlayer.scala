package game.enemyai

import game.enemyai.decisiontree.{ActionNode, DecisionNode, DecisionTreeValue}
import game.lo4_data_structures.linkedlist.LinkedListNode
import game.lo4_data_structures.trees.BinaryTreeNode
import game.maps.GridLocation
import game.physics.PhysicsVector
import game.{AIAction, Fire, FireWithDirection, MovePlayer, StopPlayer}
import game.lo4_data_structures.graphs._

class AIPlayer(val id: String) {


  // TODO: LT1
  def locatePlayer(playerId: String, playerLocations: LinkedListNode[PlayerLocation]): PlayerLocation = {
    var ans: PlayerLocation = new PlayerLocation(0,0,"")
    for (i <- playerLocations){
      if (i.playerId == playerId){
        ans = new PlayerLocation(i.x,i.y,i.playerId)
      }
    }
    ans
  }

  def closestPlayer(playerLocations: LinkedListNode[PlayerLocation]): PlayerLocation = {
    val player1: PlayerLocation = locatePlayer(id,playerLocations)
    var count: Int = 0
    var distance: Double = 0.0
    var mindistance: Double = 0.0
    var mindplayer: PlayerLocation = new PlayerLocation(0,0,"")
    for (i <- playerLocations){
      if (i.playerId != id){
        def calculator(x:PlayerLocation,y:PlayerLocation): Double = {
          val ans: Double = Math.sqrt(Math.pow(y.x-x.x,2)+Math.pow(y.y-x.y,2))
          ans
        }
        distance = calculator(player1,i)
        if (count == 0){
          mindistance=distance
          mindplayer=i
          count+=1
        } else if (distance < mindistance){
          mindistance=distance
          mindplayer=i
        }
      }
    }
    mindplayer
  }

  // TODO: LT2
  def pathToDirection(playerLocations: LinkedListNode[PlayerLocation], path: LinkedListNode[GridLocation]): PhysicsVector = {
    val pathlength: Int = path.size()
    val player: PlayerLocation = locatePlayer(id,playerLocations)
    var ans = new PhysicsVector(0.0,0.0)
    if (pathlength == 1){
      val xy = path.value.centerAsVector()
      ans = new PhysicsVector(xy.x-player.x,xy.y-player.y)
    }
    if (pathlength > 1 ){
      val xy = path.next.value.centerAsVector()
      ans = new PhysicsVector(xy.x-player.x, xy.y-player.y)
    }
    ans
  }


  // TODO: LT3
  def computePath(start: GridLocation, end: GridLocation): LinkedListNode[GridLocation] = {

    var outcome: LinkedListNode[GridLocation] = new LinkedListNode[GridLocation](end, null)

      val different1: Int = end.x-start.x
      val different2: Int = end.y-start.y
      if (different1 < 0){
        for (i <- -1 to different1 by -1){
          outcome = new LinkedListNode[GridLocation](new GridLocation(outcome.value.x+1,end.y),outcome)
        }
      }
      if (different1 > 0){
        for (i <-  1 to different1 by 1){
          outcome = new LinkedListNode[GridLocation](new GridLocation(outcome.value.x-1,end.y),outcome)
        }
      }
      if (outcome.value.x == start.x){
        if (different2 < 0){
          for (i <- -1 to different2 by -1){
            outcome = new LinkedListNode[GridLocation](new GridLocation(start.x,outcome.value.y+1),outcome)
          }
        }
        if(different2 > 0){
          for (i <- 1 to different2 by 1){
            outcome = new LinkedListNode[GridLocation](new GridLocation(start.x,outcome.value.y-1),outcome)
          }
        }
      }
    outcome
  }


  // TODO: LT4
  def makeDecision(gameState: AIGameState, decisionTree: BinaryTreeNode[DecisionTreeValue]): AIAction = {
    val number = decisionTree.value.check(gameState)
    if (number < 0){
      makeDecision(gameState,decisionTree.left)
    } else if (number > 0){
      makeDecision(gameState,decisionTree.right)
    } else {
      decisionTree.value.action(gameState)
    }
  }



  // TODO: LT5
  def distanceAvoidWalls(gameState: AIGameState, locate1: GridLocation, locate2: GridLocation): Int = {

    var beginid: Int = 0
    var endid: Int = 0
    val graph:Graph[GridLocation] = gameState.levelAsGraph()
    for ((key,value) <- graph.nodes) {
      if (graph.nodes(key).x == locate1.x && graph.nodes(key).y == locate1.y) {
        beginid = key
      }
      if (graph.nodes(key).x == locate2.x && graph.nodes(key).y == locate2.y) {
        endid = key
      }
    }
    BFS.bfs(graph,beginid,endid)
  }




  // TODO: LT6
  def closestPlayerAvoidWalls(Game:AIGameState): PlayerLocation={
    val player1: PlayerLocation = locatePlayer(id,Game.playerLocations)
    var mindplayer: PlayerLocation = new PlayerLocation(0,0,"")
    var ans: Map[Int,PlayerLocation] = Map()
    for (i <- Game.playerLocations) {
      if (i.playerId != id) {
        ans = ans + (distanceAvoidWalls(Game, player1.asGridLocation(), i.asGridLocation()) -> i)
      }
    }
    val small: Int = ans.keys.toList.sortWith(_<_).head
    mindplayer = ans(small)
    mindplayer
  }


  // TODO: AO1
  def getPath(Game:AIGameState): LinkedListNode[GridLocation]={
    val targetplay: PlayerLocation = locatePlayer(id,Game.playerLocations)
    val closestplayer: PlayerLocation = closestPlayerAvoidWalls(Game)
    var ans: LinkedListNode[GridLocation] = new LinkedListNode[GridLocation](closestplayer.asGridLocation(),null)
    val gridID: GridLocation => Int = location => location.x + location.y * Game.levelWidth
    val beginid: Int = gridID(targetplay.asGridLocation())
    val endit: Int = gridID(closestplayer.asGridLocation())
    val list: List[Int] = BFS2.bfs(Game.levelAsGraph(),beginid,endit)
    val newlist: List[GridLocation] = list.map((f:Int)=> Game.levelAsGraph().nodes(f))
    for (i <- newlist){
      ans = new LinkedListNode[GridLocation](new GridLocation(i.x,i.y),ans)
    }
    ans
  }

  // TODO: AO2
  def decisionTree(referencePlayer: AIPlayer): BinaryTreeNode[DecisionTreeValue] = {
    //距离 大于10
    val stop = new BinaryTreeNode[DecisionTreeValue](new ActionNode((gameState: AIGameState) => {
      StopPlayer(referencePlayer.id)
    }), null, null)
    //像最近player的方向移动 距离大于5 小于10
    val huntClosestPlayer = new BinaryTreeNode[DecisionTreeValue](new ActionNode((gameState: AIGameState) => {
      val path = referencePlayer.getPath(gameState)
      val direction: PhysicsVector = referencePlayer.pathToDirection(gameState.playerLocations, path)
      MovePlayer(referencePlayer.id, direction.x, direction.y)
    }), null, null)
    //射击 距离小于5
    val fire = new BinaryTreeNode[DecisionTreeValue](new ActionNode((gameState: AIGameState) => {
      val myLocation: PlayerLocation = referencePlayer.locatePlayer(referencePlayer.id, gameState.playerLocations)
      val closestPlayer: PlayerLocation = referencePlayer.closestPlayer(gameState.playerLocations)
      val xdistance: Double = closestPlayer.x - myLocation.x
      val ydistance: Double = closestPlayer.y - myLocation.y
      FireWithDirection(referencePlayer.id, xdistance, ydistance)
    }), null, null)
    //距离
    val distance: AIGameState => Double = dis => {
      val myLocation: PlayerLocation = referencePlayer.locatePlayer(referencePlayer.id, dis.playerLocations)
      val closestPlayer: PlayerLocation = referencePlayer.closestPlayer(dis.playerLocations)
      referencePlayer.distanceAvoidWalls(dis, myLocation.asGridLocation(), closestPlayer.asGridLocation())
    }
    //答案
    val decider = new BinaryTreeNode[DecisionTreeValue](
      new DecisionNode((gameState: AIGameState) =>
        if (distance(gameState) < 5) -1
        else if (distance(gameState) > 5 && distance(gameState) < 10) 1
        else 0),
      fire, huntClosestPlayer
    )
    val hi = new BinaryTreeNode[DecisionTreeValue](
      new DecisionNode((gameState: AIGameState) =>
        if (distance(gameState) > 10) -1 else 1),stop,decider
    )
    hi
  }



  //val fireProbability = 0.1
  //val decider = new BinaryTreeNode[DecisionTreeValue](
  //  new DecisionNode((gameState: AIGameState) => if (Math.random() < fireProbability) -1 else 1), fire, huntClosestPlayer
  //)

  //decider


    //找一个player的位置 ^
    //然后呢 用getpath这个method去找去从reference player到最近player的路径 ^
    //顺便再用distanceaviodwalls这个method去找reference player到最近player的距离 ^
    //if (距离 > 5 && 距离 < 10) => 把reference player按照路径移动到最近player的位置 ^
    //if (距离 > 10) => reference player就停下来不动
    //if (距离 < 5) => reference player. FireWithDirection(reference player的id, direction就是用最近player的location的xy - reference player的xy) ^


}

