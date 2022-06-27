package gof.pattern
package `type`

object PhantomTypes {

  sealed trait DoorState
  sealed trait Open extends DoorState
  sealed trait Closed extends DoorState

  case class Door[State <: DoorState](){
    // compiler provide us is an evidence of a certain relationship between two types
    def open(implicit ev: State =:= Closed) = Door[Open]()
    def close(implicit ev: State =:= Open) = Door[Closed]()
  }

}
