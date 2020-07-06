package com.clovellytech.sendgrid
package providers.sendgrid

import org.http4s._

final case class SendgridSettings(
    version: String,
    host: String,
    apiKey: String,
    rateLimitRetry: Int,
    rateLimitSleep: Int,
    subUser: String,
    emailsConfig: EmailsConfig
) {
  def userAgent: String = "sendgrid/" + version + ";scala"

  def requestHeaders: Map[String, String] =
    Map(
      "Authorization" -> s"Bearer $apiKey",
      "User-Agent" -> userAgent,
      "Content-Type" -> "application/json"
    )

  def requestHeadersList: Headers =
    Headers.of(
      requestHeaders.toList.map((Header.apply _).tupled): _*
    )

  def sendEndpoint = Uri.fromString(s"https://$host/$version/mail/send")

}

object SendgridSettings {
  val RATE_LIMIT_RESPONSE_CODE = 429

  def default(apiKey: String, emailsConfig: EmailsConfig) =
    SendgridSettings(
      "v3",
      "api.sendgrid.com",
      apiKey,
      10,
      50,
      "",
      emailsConfig
    )
}
