---
layout: docs
title:  "Sendgrid scala"
---

Documentation 
===

# Sending email

This project uses circe to encode emails and decode responses. 

Your services can depend on the availability of an email provider like so:


```scala mdoc
import cats._
import cats.implicits._
import cats.effect._
import com.clovellytech.sendgrid._
import com.clovellytech.sendgrid.providers.sendgrid._
import io.chrisdavenport.log4cats.Logger


class Service[F[_]: Functor: EmailAlgebra] {
  def respond(): F[Unit] = {
    EmailAlgebra[F].sendEmail(
      EmailData.text(
        from = Email("Site", "name@yoursite.com"),
        to = Email("user name", "user@gmail.com"),
        subject = "Registration update",
        text = "Thank you for registering"
      )
    ).as(())
  }
}

```

Now all that is required is to instantiate an email provider given your apikey and configuration:


```scala mdoc 
import scala.concurrent.ExecutionContext.Implicits.global

class Server[F[_]: ConcurrentEffect: ContextShift: Timer: Logger] {
  val config = SendgridSettings.default("your api key", EmailsConfig(fromName = "Company", fromEmail = "info@example.com"))

  SendgridProvider.resource[F](global, config).use{ implicit provider => 
    val service = new Service[F]

    service.respond()
  }


}

```

Here's a mock email service that just logs the email intent:

```scala mdoc
import org.http4s.Status

class MockEmail[F[_]: Applicative: Logger] extends EmailAlgebra[F] {
  def sendEmail(email: EmailData): F[Status] = {
    (
      Logger[F].info("sending email") *> 
      Logger[F].info(email.toString)
    ).as(Status.Ok)
  }
}

```

```scala mdoc:invisible
// Make a logger instance that just printlns it, so it works for docs:

  implicit def consoleLogger: Logger[Id] = new Logger[Id] {
    // Members declared in io.chrisdavenport.log4cats.ErrorLogger
    def debug(t: Throwable)(message: => String): cats.Id[Unit] = println(message)
    def error(t: Throwable)(message: => String): cats.Id[Unit] = println(message)
    def info(t: Throwable)(message: => String): cats.Id[Unit] = println(message)
    def trace(t: Throwable)(message: => String): cats.Id[Unit] = println(message)
    def warn(t: Throwable)(message: => String): cats.Id[Unit] = println(message)

    // Members declared in io.chrisdavenport.log4cats.MessageLogger
    def debug(message: => String): cats.Id[Unit] = println(message)
    def error(message: => String): cats.Id[Unit] = println(message)
    def info(message: => String): cats.Id[Unit] = println(message)
    def trace(message: => String): cats.Id[Unit] = println(message)
    def warn(message: => String): cats.Id[Unit] = println(message)
  }


```


There's a hidden instance of Logger for Id defined, so this works and shows our mock output:

```scala mdoc
import cats._

implicit val mock: EmailAlgebra[Id] = new MockEmail[Id]
val service = new Service[Id]

service.respond()

```


Now your `Service[F]` class is ready to be used with IO inside of an IOApp.


# Future work
* Make the project work for other providers (amazon, G suite, mailchimp, self-managed)
