package gof.pattern
package gof

object Builder {

  class HttpClientRequest(endpoint: String, method: Method, header: Map[String, String], body: Option[String])

  object HttpClientRequest {
    def builder(): HttpClientRequestBuilder = new HttpClientRequestBuilder("")
  }

  // creating method case class
  sealed trait Method

  case object GET extends Method

  case object POST extends Method

  case object PUT extends Method

  case object DELETE extends Method

  case object PATCH extends Method

  case class HttpClientRequestBuilder(endpoint: String, method: Method = GET, header: Map[String, String] = Map.empty[String, String], body: String = "") {
    def withEndPoint(endpoint: String): HttpClientRequestBuilder = copy(endpoint = endpoint)

    def withMethod(method: Method): HttpClientRequestBuilder = copy(method = method)

    def withHeader(headers: Map[String, String]): HttpClientRequestBuilder = copy(header = headers)

    def withBody(body: String): HttpClientRequestBuilder = copy(body = body)

    def build: HttpClientRequest = new HttpClientRequest(endpoint, method, header, Some(body))
  }

  // =================== a variant of the pattern using phantom types ====================================== //

  case class Food(ingredients: Seq[String])

  object Chef {

    sealed trait Pizza
    object Pizza {
      sealed trait EmptyPizza extends Pizza
      sealed trait Cheese extends Pizza
      sealed trait Topping extends Pizza
      sealed trait Dough extends Pizza

      // define a type alias that is the conjunction of several other types
      type FullPizza = EmptyPizza with Cheese with Topping with Dough
    }
  }

  class Chef[Pizza <: Chef.Pizza](ingredients: Seq[String] = Seq()) {
    import Chef.Pizza._

    def addCheese(cheeseType: String): Chef[Pizza with Cheese] = new Chef(ingredients :+ cheeseType)

    def addTopping(toppingType: String): Chef[Pizza with Topping] = new Chef(ingredients :+ toppingType)

    def addDough: Chef[Pizza with Dough] = new Chef(ingredients :+ "dough")

    def build(implicit ev: Pizza =:= FullPizza): Food = Food(ingredients)
  }

  new Chef[Chef.Pizza.EmptyPizza]()
    .addCheese("mozzarella")
    .addDough
    .addTopping("olives")
    .build

}