package gof.pattern
package gof

object AbstractFactory {

  trait Button {
    def paint: String
  }

  class WinButton extends Button {
    def paint: String = "I'm a WinButton"
  }

  class OSXButton extends Button {
    def paint: String = "I'm a OSXButton"
  }

  trait GUIFactory {
    def createButton: Button
  }

  class WinFactory extends GUIFactory {
    def createButton: Button = new WinButton
  }

  class OSXFactory extends GUIFactory {
    def createButton: Button = new OSXButton
  }

  class Application(factory: GUIFactory) {
    val button = factory.createButton
    println(button.paint)
  }


  def main(argv: Array[String]) {
    val os_type = "OSX"
    val factory_type = os_type match {
      case "Win" => new WinFactory
      case "OSX" => new OSXFactory
    }
    new Application(factory_type)
  }
}
