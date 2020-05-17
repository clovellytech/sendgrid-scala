package com.clovellytech.sendgrid

import io.circe.Encoder
import io.circe.generic.semiauto._

trait Codecs {
  implicit val emailEncoder: Encoder[Email] = deriveEncoder

  implicit val contentElementEncoder: Encoder[ContentElement] =
    Encoder.forProduct2[ContentElement, String, String]("type", "value")(c => (c.tpe, c.value))

  implicit val personalizationsEncoder: Encoder[Personalizations] = deriveEncoder
  implicit val emailDataEncoder: Encoder[EmailData] = deriveEncoder
}
