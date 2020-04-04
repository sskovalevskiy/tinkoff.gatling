package load

import io.gatling.core.scenario.Simulation
import io.gatling.core.Predef._
import io.gatling.core.structure.ScenarioBuilder
import io.gatling.http.Predef._

import scala.concurrent.duration._
import scala.util.Random

/*
Для сайта http://computer-database.gatling.io/ разработать проект нагрузочного тестирования в виде sbt проекта,
который должен содержать в себе:
1.    Профиль нагрузки
2.    Скрипты, покрывающие основной функционал (просмотр страницы, добавление, удаление записей)
3.    Несколько сценариев тестирования (поиск нормальной/максимальной производительности, тестирование стабильности), хотя бы через функцию «pace»
4.    Показать умение пользоваться:
      a.checks
      b.xpath/regex/jsonpath экстракторами
      c.Фидеры (Feeder)
      d.Параметризация и корреляции
      e.Добавление кастомных header
      f.Добавление кастомных cookies
 */

class LoadScript extends Simulation {

  val feeder = Iterator.continually(Map("session" -> (Random.alphanumeric.take(20).mkString)))

  val httpConf = http
    .baseUrl("http://computer-database.gatling.io")
    .acceptHeader("text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8")
    .doNotTrackHeader("1")
    .acceptLanguageHeader("ru-RU,ru;q=0.5")
    .acceptEncodingHeader("gzip, deflate")
    .userAgentHeader("Mozilla/5.0 (Macintosh; Intel Mac OS X 10.8; rv:16.0) Gecko/20100101 Firefox/16.0")
    .header("Custom Header", "Custom Header Value")

  def scn(scenarioName: String): ScenarioBuilder = scenario(scenarioName)
    .feed(feeder)
    .exec(addCookie(Cookie("SESSION", "${session}")))
    .repeat(Int.MaxValue) {
      exec(Main.mainPage)
        .randomSwitch(
          (20, Create.createComputer),
          (20, Browse.browse),
          (60, Search.searchByName.exec(Select.selectById).randomSwitch(
            (50, Edit.editComputer),
            (50, Delete.deleteComputer)
          ))
        )
    }

  val findMaxUsers = Seq(
    rampUsers(10) during (15 seconds),
    nothingFor(60 seconds),
    rampUsers(10) during (15 seconds),
    nothingFor(60 seconds),
    rampUsers(10) during (15 seconds),
    nothingFor(60 seconds)
  )

  val stabilityUsers = Seq(
    rampUsers(10) during (1 minute),
    nothingFor(24 hour)
  )

  setUp(scn("Find Max Test").inject(findMaxUsers).protocols(httpConf)).maxDuration(240 seconds)
  //  setUp(scn("Stability Test").inject(stabilityUsers).protocols(httpConf)).maxDuration(1441 minute)

}
