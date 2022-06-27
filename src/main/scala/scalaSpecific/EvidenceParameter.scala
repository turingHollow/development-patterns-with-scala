package gof.pattern
package scalaSpecific

object EvidenceParameter {

  // class with no methods
  case class Bar(value: String)

  // a trait the class Bar had not implemented
  trait WithFoo[A] {
    def foo(x: A): String
  }

  // object that attaches an implementation of the trait for Bar - for methods
  // willing to play along with this kind of trait attachment - see immediately below
  implicit object MakeItFoo extends WithFoo[Bar] {
    def foo(x: Bar) = x.value
  }

  // method willing to recognize anything as having trait WithFoo,
  // as long as it has evidence that it does - the evidence being the previous object
  def callFoo[A](thing: A)(implicit evidence: WithFoo[A]) = evidence.foo(thing)

  def main(argv: Array[String]) {
    println(callFoo(Bar("hi"))) // and it works
  }
}
