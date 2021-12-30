//package cityfeed.grpc
//
//import akka.actor.ActorSystem
//import akka.grpc.scaladsl.WebHandler
//import akka.http.scaladsl.Http
//import akka.http.scaladsl.model.{HttpRequest, HttpResponse}
//import akka.http.scaladsl.settings.ServerSettings
//import cityfeed.Main.port
//import cityfeed.application.grpc.RegisterServiceHandler
//
//import scala.concurrent.{ExecutionContext, Future}
//
//case class AuthenticationServer(system: ActorSystem) {
//  def run(): Future[Http.ServerBinding] = {
//    implicit val sys: ActorSystem = system
//    implicit val ec: ExecutionContext = sys.dispatcher
//    val registerService: HttpRequest => Future[HttpResponse] =
//      RegisterServiceHandler(new RegisterImpl())
//
//    val binding = Http()
//      .newServerAt("localhost", port)
//      .withSettings(ServerSettings(system))
//      .bind(registerService)
//
//    binding.foreach {binding => println(s"gRPC server bound to: ${binding.localAddress}") }
//    binding
//  }
//}
