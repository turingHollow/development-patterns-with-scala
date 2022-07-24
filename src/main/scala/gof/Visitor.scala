
object Visitor {

  sealed trait Json

  case object JsonNull extends Json

  case class JsonBoolean(value: Boolean) extends Json

  case class JsonBigDecimalNumber(value: BigDecimal) extends Json

  case class JsonLongNumber(value: Long) extends Json

  case class JsonString(value: String) extends Json

  case class JsonArray(value: Vector[Json]) extends Json

  case class JsonObject(value: Vector[(String, Json)]) extends Json

  // We cab use pattern matching to work with ADT but it can be bad for perfomance
  def countValues(json: Json): Int = json match {
    case JsonNull => 0
    case JsonBoolean(_) => 1
    case JsonNumber(_) => 1
    case JsonString(_) => 1
    case JsonArray(js) => js.map(countValues).sum
    case JsonObject(fs) => fs.map(field => countValues(field._2)).sum
  }

  // We can support pattern matching via Scala's extractors but we lose exhaustive checking
  object JsonNumber {
    def unapply(json: Json): Option[BigDecimal] = json match {
      case JsonBigDecimalNumber(value) => Some(value)
      case JsonLongNumber(value) => Some(BigDecimal(value))
      case _ => None
    }
  }

  // lets see external visitor
  sealed trait IntList {
    def accept[A](visitor: (A, (Int, IntList) => A)): A
  }

  case object IntNil extends IntList {
    def accept[A](visitor: (A, (Int, IntList) => A)): A = visitor._1
  }

  case class IntCons(h: Int, t: IntList) extends IntList {
    def accept[A](visitor: (A, (Int, IntList) => A)): A = visitor._2(h, t)
  }

  def sumWith(xs: IntList): Int = xs.accept[Int]((0, _ + sumWith(_)))

  val testIntList: IntCons = IntCons(5, IntCons(8, IntNil))


  // Now lets see internal visitor
  sealed trait InternalIntList {
    def accept[A](visitor: (A, (Int, A) => A)): A
  }

  case object InternalIntNil extends InternalIntList {
    def accept[A](visitor: (A, (Int, A) => A)): A = visitor._1
  }

  case class InternalIntCons(h: Int, t: InternalIntList) extends InternalIntList {
    def accept[A](visitor: (A, (Int, A) => A)): A =
      visitor._2(h, t.accept(visitor))
  }

  val testInternalIntList: InternalIntCons = InternalIntCons(5, InternalIntCons(8, InternalIntNil))

  def sumWithInternal(xs: InternalIntList) = xs.accept[Int]((0, _ + _))

  /* External visitors are like pattern matching, giving the user access to one layer of structure at a time,
   and allowing them to recurse into the next layer as needed, while internal visitors are like folds,
    where the data structure itself drives the recursion.*/

  def main(args: Array[String]): Unit = {
    println(
      sumWith(testIntList)
    )
    //internal visitors are "basically folds"
    println(
      sumWithInternal(testInternalIntList)
    )
  }
}