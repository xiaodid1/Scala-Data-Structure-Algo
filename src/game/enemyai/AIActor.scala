package game.enemyai

import akka.actor.{Actor, ActorRef}
import game.{AIAction, GameStateMessage}

class AIActor(gameActor: ActorRef, id: String) extends Actor {

  val aiPlayer = new AIPlayer(id)

  override def receive: Receive = {
    case gs: GameStateMessage =>

      val gameState: AIGameState = new AIGameState
      gameState.parseJsonState(gs.gameState)

      val action: AIAction = aiPlayer.makeDecision(gameState, aiPlayer.decisionTree(aiPlayer))

      gameActor ! action
  }


}
