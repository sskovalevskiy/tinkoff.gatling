package load

import io.gatling.core.Predef._
import io.gatling.http.Predef._

object Select {

  val selectById = exec(
    http("Select")
      .get("/computers/${id}")
      .check(status.is(200))
  )
    .pause(1)
}
