package com.clovellytech.sendgrid
package providers.sendgrid

import cats.effect.ConcurrentEffect
import cats.effect.ContextShift
import cats.effect.Resource
import cats.effect.Timer
import cats.implicits._
import Codecs._
import org.http4s.circe.CirceEntityCodec._
import org.http4s.client._
import org.http4s.client.blaze._
import org.http4s.client.dsl.Http4sClientDsl
import org.http4s.dsl.Http4sDsl
import org.http4s.Status
import io.chrisdavenport.log4cats.Logger
import scala.concurrent.ExecutionContext

class Emails[F[_]: ConcurrentEffect: ContextShift: Timer: Logger](
    client: Client[F],
    config: EmailsConfig
) extends Http4sDsl[F]
    with EmailAlgebra[F]
    with Http4sClientDsl[F] {

  val settings = SendgridSettings.default(config.apiKey)

  def sendEmail(email: EmailData): F[Status] =
    for {
      send <- implicitly[ConcurrentEffect[F]].fromEither(settings.sendEndpoint)
      req <- POST.apply(email, send, settings.requestHeadersList.toList: _*)
      _ <- Logger[F].info("Attempting to send email")
      resp <- client.run(req).use { resp =>
        if (resp.status == Status.Accepted) {
          Logger[F].info("sending successful") *> ConcurrentEffect[F].delay(resp.status)
        } else {
          resp.as[SendgridErrors].flatMap { e =>
            val error = new Error(e.errors.toString)
            val logmessage = s"sending failed\n\n${email.toString}"
            Logger[F].error(error)(logmessage) *> ConcurrentEffect[F].raiseError[Status](error)
          }
        }
      }
    } yield resp
}

object Emails {
  def apply[F[_]: ConcurrentEffect: ContextShift: Timer: Logger](
      ec: ExecutionContext,
      emailsConfig: EmailsConfig
  ): Resource[F, Emails[F]] =
    BlazeClientBuilder(ec).resource.map(c => new Emails[F](c, emailsConfig))
}
