package load

import io.gatling.core.Predef._
import io.gatling.http.Predef._

object Delete {

  val deleteComputer = exec(
    http("Delete computer")
      .post("/computers/${id}/delete")
      .check(status.is(200), substring("Computer has been deleted"))
  ).pause(1)
}
