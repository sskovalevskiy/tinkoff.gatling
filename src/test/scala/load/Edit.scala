package load

import io.gatling.core.Predef._
import io.gatling.http.Predef._

object Edit {

  val editComputer = exec(
    http("Open computer form")
      .get("/computers/${id}")
  )
    .pause(1)
    .exec(
      http("Update computer")
        .post("/computers")
        .formParam("name", "Updated Computer")
        .formParam("introduced", "2012-05-30")
        .formParam("discontinued", "")
        .formParam("company", "37")
    )
    .pause(1)
}
