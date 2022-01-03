package cityfeed.grpc

import akka.stream.Materializer
import cityfeed.Main.userRepository
import cityfeed.application.grpc.{RegisterRequest, RegisterResponse, RegisterService}
import cityfeed.model.{User, UserCredentials}
import com.typesafe.scalalogging.LazyLogging

import java.util.UUID
import scala.concurrent.Future

class RegisterImpl(implicit mat: Materializer) extends RegisterService with LazyLogging {
  override def registerUser(request: RegisterRequest): Future[RegisterResponse] = {
    val credentials = UserCredentials(UUID.randomUUID(), request.username, request.emailAddress, request.password)
    val user = User(Some(UUID.randomUUID()), request.fullName, request.city, request.address, request.neighborhood, Some(credentials))

    val responseIO = for {
      insertedRows <- userRepository.insertUser(user)
    } yield insertedRows

    // do extra checks in future e.g check if the account exists already before actually trying to insert
    responseIO.attempt.map {
      case Right(_) =>
        logger.info(s"User ${request.username} registered with success!")
        RegisterResponse(registerSuccess = true)
      case Left(e) =>
        logger.error(s"User registration failed because of error: ${e.getMessage}")
        new RegisterResponse(registerSuccess = false)
    }.unsafeToFuture()
  }
}
