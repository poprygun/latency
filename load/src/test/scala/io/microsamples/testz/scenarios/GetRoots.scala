package io.microsamples.testz.scenarios

import io.gatling.core.Predef._
import io.gatling.http.Predef._

object GetRoots {

  val getRootsHttp = http("get remote chachkies")
    .get("/remote-chachkies")
    .check(status is 200)

  val getRoots = scenario("Get Remote Chachkies")
    .exec(getRootsHttp)
}
