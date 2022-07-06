package gof.pattern
package scalaSpecific

import cats.Monoid
import cats.implicits._

object Aux {

  // in this case for instance the result of our type level computation will be stored in B.
  trait Foo[A] {
    type B

    def value: B
  }

  object Foo {

    // let’s define some instances
    implicit val fi = new Foo[Int] {
      type B = String
      val value = "Foo"
    }

    // one more
    implicit val fs = new Foo[String] {
      type B = Int
      val value = 444
    }

    // defining a type alias where A0 is mapped to Foo A and B0 is mapped to type B
    type Aux[A0, B0] = Foo[A0] {type B = B0}
  }

  // we can use parameter dependent types to access the type defined inside a class/trait (path dependent type)
  // so if we want to use our type B in a function, as a return type, we can do that
  def foo[T](t: T)(implicit f: Foo[T]): f.B = f.value

  // With help of Aux type alias we can use dependent type as parameter in the next parameter
  def fooWithMonoid[T, R](t: T)(implicit f: Foo.Aux[T, R], m: Monoid[R]): R = m.empty

  def main(argv: Array[String]) {
    println(foo("someString"))
    println(foo(2))
    println(fooWithMonoid("someString"))
  }
}
