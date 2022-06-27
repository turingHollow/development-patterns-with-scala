package gof.pattern
package scalaSpecific

object PhantomTypes {

  sealed trait DoorState
  sealed trait Open extends DoorState
  sealed trait Closed extends DoorState

  case class Door[State <: DoorState](){
    // compiler provide us is an evidence of a certain relationship between two types
    def open(implicit ev: State =:= Closed) = Door[Open]()
    def close(implicit ev: State =:= Open) = Door[Closed]()
  }

  def main(argv: Array[String]) {
    val closedDoor = Door[Closed]()
    val openDoor = closedDoor.open
//    openDoor.open -- will not compile
    openDoor.close
  }

}
