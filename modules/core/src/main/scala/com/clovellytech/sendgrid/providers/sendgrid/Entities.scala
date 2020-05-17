package com.clovellytech.sendgrid.providers.sendgrid

final case class SendgridError(
    message: String,
    field: Option[String],
    help: Option[String]
)

final case class SendgridErrors(
    errors: List[SendgridError]
)
