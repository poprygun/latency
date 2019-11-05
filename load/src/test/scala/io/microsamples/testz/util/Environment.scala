package io.microsamples.testz.util

object Environment {
//  val baseURL = scala.util.Properties.envOrElse("baseURL", "http://localhost:8082")
  val baseURL = scala.util.Properties.envOrElse("baseURL", "http://rest-api-client.cfapps.io")
  val users = scala.util.Properties.envOrElse("numberOfUsers", "5")
  val maxResponseTime = scala.util.Properties.envOrElse("maxResponseTime", "10000") // in milliseconds

}
