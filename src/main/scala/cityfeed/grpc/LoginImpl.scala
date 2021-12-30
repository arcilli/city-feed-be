package cityfeed.grpc

import akka.stream.Materializer
import cityfeed.Main.userRepository
import cityfeed.application.grpc.{LoginRequest, LoginResponse, LoginService}
import cityfeed.errors.Errors.{NonExistentAccount, WrongCredentials}
import cityfeed.model.UserCredentials
import com.typesafe.scalalogging.LazyLogging

import scala.concurrent.Future

class LoginImpl(implicit mat: Materializer) extends LoginService with LazyLogging {
  override def checkCredentials(request: LoginRequest): Future[LoginResponse] = {
    val responseIO = for {
      userAccount <- userRepository.checkCredentials(request.emailAddress, request.password)
    } yield userAccount

    responseIO.attempt.map {
      case Right(Some(user)) =>
        logger.info(s"User with email ${user.email_address} logged in successfully.")
        LoginResponse(loginSuccess = true)
      case Left(e: NonExistentAccount) =>
        logger.error(e.getMessage)
        LoginResponse()
      case Left(e: WrongCredentials) =>
        logger.error(e.getMessage)
        LoginResponse()
      case Left(e) =>
      logger.error(s"Internal server error: ${e.getMessage}")
      LoginResponse()
    }.unsafeToFuture()
  }
}
