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
    val user = User(None, request.fullName, request.city, request.address, request.neighborhood, credentials)

    val responseIO = for {
      insertedRows <- userRepository.insertUser(user)
    } yield insertedRows

    responseIO.attempt.map {
      case Right(rows) =>
        logger.info(s"Inserted $rows rows")
        new RegisterResponse(true)
      case Left(e) =>
        logger.error(s"User registration failed because of error: ${e.getMessage}")
        new RegisterResponse(false)
    }.unsafeToFuture()
  }
}
