package gof.pattern
package gof

object Adapter {

  case class Celsius(degrees: Double)

  case class Fahrenheit(degrees: Double)

  trait ToCelsius[From] {
    def convert(source: From): Celsius
  }

  object ToCelsius {
    implicit val celsiusToFahrenheit = new ToCelsius[Fahrenheit] {
      override def convert(source: Fahrenheit): Celsius = Celsius((source.degrees - 32) * 5 / 9)
    }

    implicit val celsiusIdentity = new ToCelsius[Celsius] {
      override def convert(source: Celsius): Celsius = source
    }

  }

  class AirConditioner {
    def setTemperature[T](degrees: T)(implicit ev: ToCelsius[T]): Unit = {
      val asCelsius = ev.convert(degrees)
      println(s"Set to $asCelsius")
    }
  }


  val airConditioner = new AirConditioner()

  def main(args: Array[String]): Unit = {
    println(
      airConditioner.setTemperature(new Fahrenheit(75))
    )
    println(
      airConditioner.setTemperature(new Celsius(23))
    )
  }


}
