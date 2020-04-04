package load

import io.gatling.core.Predef._
import io.gatling.http.Predef._

object Browse {

  val browse = repeat(4, "i") {
    exec(
      http("Page ${i}")
        .get("/computers?p=${i}")

    ).pause(1)
  }
}
