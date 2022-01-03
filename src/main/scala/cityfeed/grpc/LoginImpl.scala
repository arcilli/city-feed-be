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
      userId <- userRepository.getUserProfileIdBasedOnAccountId(userAccount.map(userAccount => userAccount.id).get)
    } yield userId

    responseIO.attempt.map {
      case Right(userId) =>
        logger.info(s"User with id $userId logged in successfully.")
        LoginResponse(loggedUserId = userId.toString)
      case Left(e: NonExistentAccount) =>
        logger.error(e.getMessage)
        LoginResponse(loggedUserId = "notfound")
      case Left(e: WrongCredentials) =>
        logger.error(e.getMessage)
        LoginResponse(loggedUserId = "wrongcreds")
      case Left(e) =>
      logger.error(s"Internal server error: ${e.getMessage}")
      LoginResponse()
    }.unsafeToFuture()
  }
}
