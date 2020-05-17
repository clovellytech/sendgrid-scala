package com.clovellytech.sendgrid.providers.sendgrid

import io.circe.Decoder
import io.circe.generic.semiauto._

trait Codecs {
  implicit val errorDecoder: Decoder[SendgridError] = deriveDecoder
  implicit val errorsDecoder: Decoder[SendgridErrors] = deriveDecoder
}
