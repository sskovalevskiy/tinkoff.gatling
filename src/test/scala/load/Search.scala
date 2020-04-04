package load

import io.gatling.core.Predef._
import io.gatling.http.Predef._

object Search {

  val feeder = csv("search.csv").random

  val searchByName = feed(feeder)
    .exec(
      http("Search")
        .get("/computers?f=${searchCriterion}")
        .check(status.is(200))
        .check(css("a:contains('${searchComputerName}')", "href")
          .transform(href  => href.replace("/computers/", "")).saveAs("id"))
    )
    .pause(1)
}
