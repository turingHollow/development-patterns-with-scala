package gof.pattern
package gof

object FactoryMethod {

  // common interface for all products
  sealed trait Animal {
    def speak: Unit
  }

  sealed trait Dog extends Animal

  private class LittleDog extends Dog {
    def speak = println("arf")
  }

  private class GenericDog extends Dog {
    def speak = println("woof")
  }

  private class BigDog extends Dog {
    def speak = println("WOOF!")
  }

  sealed trait Cat extends Animal

  private class GenericCat extends Cat {
    def speak = println("meow")
  }

  private class GrumpyCat extends Cat {
    def speak = println(":(")
  }

  private class SmellyCat extends Cat {
    def speak = println("..oOo..")
  }


  // common interface for all factory
  trait AnimalFactory {
    def letAnimalSpeak(criteria: String): Unit = createAnimal(criteria).speak

    def createAnimal(criteria: String): Animal
  }

  object DogFactory extends AnimalFactory {
    def createAnimal(criteria: String): Animal = criteria match {
      case "small" | "little" => new LittleDog
      case "big" | "large" => new BigDog
      case _ => new GenericDog
    }
  }

  object CatFactory extends AnimalFactory {
    def createAnimal(criteria: String) = criteria match {
      case "smells" | "smelly" => new SmellyCat
      case "grumpy" => new GrumpyCat
      case _ => new GenericCat
    }
  }

  def main(args: Array[String]): Unit = {
    val mainFactory = if (true) CatFactory else DogFactory
    mainFactory.letAnimalSpeak("small")
    mainFactory.letAnimalSpeak("grumpy")
  }

}
