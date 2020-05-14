package com.clovellytech.sendgrid

final case class Email(name: String, email: String)

final case class ContentElement(
    tpe: String,
    value: String
)

final case class Personalizations(
    to: List[Email]
)

final case class EmailData(
    personalizations: List[Personalizations],
    from: Email,
    subject: String,
    content: List[ContentElement]
)

object EmailData {
  def text(from: Email, to: Email, subject: String, text: String): EmailData = EmailData(
    List(Personalizations(List(to))),
    from,
    subject,
    List(ContentElement("text/plain", text))
  )

  def textHtml(from: Email, to: Email, subject: String, text: String, html: String): EmailData =
    EmailData(
      List(Personalizations(List(to))),
      from,
      subject,
      List(
        ContentElement("text/plain", text),
        ContentElement("text/html", html)
      )
    )
}

final case class EmailsConfig(
    apiKey: String,
    fromName: String,
    fromEmail: String
)

final case class SendgridError(
    message: String,
    field: Option[String],
    help: Option[String]
)

final case class SendgridErrors(
    errors: List[SendgridError]
)
