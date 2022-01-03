package cityfeed

import akka.actor.ActorSystem
import akka.grpc.scaladsl.WebHandler
import akka.http.scaladsl.Http
import akka.http.scaladsl.model.{HttpRequest, HttpResponse}
import akka.http.scaladsl.settings.ServerSettings
import cakemix.ExecutionContextProvider
import database.{Migration, PostingRepository, PostingRepositoryProvider, TransactorProvider, UserRepository, UserRepositoryProvider}
import cats.effect.IO
import cityfeed.application.grpc.{LoginServiceHandler, PostingService, PostingServiceHandler, RegisterServiceHandler}
import cityfeed.grpc.{LoginImpl, PostingImpl, RegisterImpl}
import com.typesafe.scalalogging.LazyLogging

import scala.concurrent.{ExecutionContextExecutor, Future}

object Main extends App
  with SettingsProvider
  with ExecutionContextProvider
  with TransactorProvider
  with UserRepositoryProvider[IO]
  with PostingRepositoryProvider[IO]
  with LazyLogging {

  implicit val system: ActorSystem = ActorSystem("cityfeed")
  implicit override val executionContext: ExecutionContextExecutor = system.dispatcher
  implicit lazy val settings: Settings = Settings(system)
  override implicit val transactor = TransactorProvider.instance(settings, executionContext)
  override val userRepository = UserRepository.instance(transactor)
  override val postingRepository = PostingRepository.instance(transactor)

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

      val loginService: PartialFunction[HttpRequest, Future[HttpResponse]] =
        LoginServiceHandler.partial(new LoginImpl())

      val createPostService: PartialFunction[HttpRequest, Future[HttpResponse]] =
        PostingServiceHandler.partial(new PostingImpl())

      val grpcWebServiceHandlers = WebHandler.grpcWebHandler(registerService, loginService, createPostService)
      Http()
        .newServerAt(interface = "0.0.0.0", port = port)
        .bind(grpcWebServiceHandlers)
      logger.info(s"Starting up gRPC server on $port port")
  }
}
