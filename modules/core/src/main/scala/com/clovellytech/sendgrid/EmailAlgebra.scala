package com.clovellytech.sendgrid

import org.http4s.Status
import simulacrum.typeclass

@typeclass
trait EmailAlgebra[F[_]] {
  def sendEmail(email: EmailData): F[Status]
}
