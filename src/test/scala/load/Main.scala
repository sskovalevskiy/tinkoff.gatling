package load

import io.gatling.core.Predef._
import io.gatling.http.Predef._

object Main {

  val mainPage = exec(
    http("Home Page")
      .get("/computers")
      .check(
        status.is(200),
        regex("""\d+ computers found"""))
  ).pause(1)
}
