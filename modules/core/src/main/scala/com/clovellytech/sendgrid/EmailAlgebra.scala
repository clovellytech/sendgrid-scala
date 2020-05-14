package com.clovellytech.sendgrid

import cats.effect.Resource
import org.http4s.Status

trait EmailAlgebra[F[_]] {
  def sendEmail(email: EmailData): F[Status]
}
