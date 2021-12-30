package cityfeed

import akka.actor.ActorSystem
import akka.grpc.scaladsl.WebHandler
import akka.http.scaladsl.Http
import akka.http.scaladsl.model.{HttpRequest, HttpResponse}
import akka.http.scaladsl.settings.ServerSettings
import cakemix.ExecutionContextProvider
import database.{Migration, TransactorProvider, UserRepository, UserRepositoryProvider}
import cats.effect.IO
import cityfeed.application.grpc.RegisterServiceHandler
import cityfeed.grpc.RegisterImpl
import com.typesafe.scalalogging.LazyLogging

import scala.concurrent.{ExecutionContextExecutor, Future}

object Main extends App
  with SettingsProvider
  with ExecutionContextProvider
  with TransactorProvider
  with UserRepositoryProvider[IO]
  with LazyLogging {

  implicit val system: ActorSystem = ActorSystem("cityfeed")
  implicit override val executionContext: ExecutionContextExecutor = system.dispatcher
  implicit lazy val settings: Settings = Settings(system)
  override implicit val transactor = TransactorProvider.instance(settings, executionContext)
  override val userRepository = UserRepository.instance(transactor)

  val port = settings.grpc.port

  val serverSettings = ServerSettings(system)

  system.log.info("Starting up")

  val migrationResult = Migration.migrate(settings.database.schema, settings.database.url, settings.database.user, settings.database.password)
    .attempt
    .unsafeRunSync()

  migrationResult match {
    case Left(exception) => system.log.error(exception, "Exception while migrating schema", exception)
    case Right(_) =>
      system.log.info(s"Schema migrated with success")

      val registerService: PartialFunction[HttpRequest, Future[HttpResponse]] =
        RegisterServiceHandler.partial(new RegisterImpl())

      val grpcWebServiceHandlers = WebHandler.grpcWebHandler(registerService)
      Http()
        .newServerAt(interface = "0.0.0.0", port = 5001)
        .bind(grpcWebServiceHandlers)
      logger.info(s"Starting up gRPC server on $port port")
  }
}
