package load

import io.gatling.core.Predef._
import io.gatling.http.Predef._

object Create {

  val createComputer = exec(
    http("Open new computer form")
      .get("/computers/new")
  )
    .pause(1)
    .exec(
      http("Create computer")
        .post("/computers")
        .formParam("name", "Beautiful Computer")
        .formParam("introduced", "2012-05-30")
        .formParam("discontinued", "")
        .formParam("company", "37")
    )
    .pause(1)
}
