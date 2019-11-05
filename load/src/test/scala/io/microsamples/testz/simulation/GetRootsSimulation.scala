package io.microsamples.testz.simulation

import io.gatling.core.Predef._
import io.gatling.http.Predef._
import io.microsamples.testz.scenarios.GetRoots
import io.microsamples.testz.util.{Environment, Headers}

import scala.concurrent.duration._

class GetRootsSimulation extends Simulation {
  val httpConf = http.baseUrl(Environment.baseURL)
    .headers(Headers.commonHeader)

  val quadraticEquasionMicroserviceScenarios = List(

    GetRoots.getRoots.inject(
//      rampUsersPerSec(1) to 100 during (10 seconds) // 6
       constantUsersPerSec(10) during (10 seconds)
//      , constantUsersPerSec(50) during (20 seconds)
    )
  )

  setUp(quadraticEquasionMicroserviceScenarios)
    .protocols(httpConf)
    .maxDuration(5 minutes)

    .assertions(
      global.responseTime.max.lt(Environment.maxResponseTime.toInt)
    )

}
