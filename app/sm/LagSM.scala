package lila.ws

import ipc.LilaIn

object LagSM {

  case class State(
      lags: Map[User.ID, Int] = Map.empty,
      emit: Option[LilaIn] = None
  )

  def apply(state: State, input: Input): State = input match {

    case Set(user, lag) => state.copy(
      lags = state.lags + (user.id -> lag),
      emit = None
    )

    case Publish => state.copy(
      lags = Map.empty,
      emit = Some(LilaIn.Lags(state.lags))
    )
  }

  sealed trait Input
  case class Set(user: User, lag: Int) extends Input
  case object Publish extends Input
}