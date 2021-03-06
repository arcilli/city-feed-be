
// Generated by Akka gRPC. DO NOT EDIT.
package cityfeed.application.grpc

import scala.concurrent.ExecutionContext

import akka.actor.ClassicActorSystemProvider

import akka.grpc.GrpcClientSettings

import akka.grpc.scaladsl.AkkaGrpcClient

import akka.grpc.internal.NettyClientUtils
import akka.grpc.internal.ClientState

import akka.grpc.scaladsl.SingleResponseRequestBuilder
import akka.grpc.internal.ScalaUnaryRequestBuilder

// Not sealed so users can extend to write their stubs
trait LoginServiceClient extends LoginService with LoginServiceClientPowerApi with AkkaGrpcClient

object LoginServiceClient {
  def apply(settings: GrpcClientSettings)(implicit sys: ClassicActorSystemProvider): LoginServiceClient =
    new DefaultLoginServiceClient(settings)
}

final class DefaultLoginServiceClient(settings: GrpcClientSettings)(implicit sys: ClassicActorSystemProvider) extends LoginServiceClient {
  import LoginService.MethodDescriptors._

  private implicit val ex: ExecutionContext = sys.classicSystem.dispatcher
  private val options = NettyClientUtils.callOptions(settings)
  private val clientState = new ClientState(settings, akka.event.Logging(sys.classicSystem, classOf[DefaultLoginServiceClient]))

  
  private def checkCredentialsRequestBuilder(channel: akka.grpc.internal.InternalChannel) =
  
    new ScalaUnaryRequestBuilder(checkCredentialsDescriptor, channel, options, settings)
  
  

  
  /**
   * Lower level "lifted" version of the method, giving access to request metadata etc.
   * prefer checkCredentials(cityfeed.application.grpc.LoginRequest) if possible.
   */
  
  override def checkCredentials(): SingleResponseRequestBuilder[cityfeed.application.grpc.LoginRequest, cityfeed.application.grpc.LoginResponse] =
    checkCredentialsRequestBuilder(clientState.internalChannel)
  

  /**
   * For access to method metadata use the parameterless version of checkCredentials
   */
  def checkCredentials(in: cityfeed.application.grpc.LoginRequest): scala.concurrent.Future[cityfeed.application.grpc.LoginResponse] =
    checkCredentials().invoke(in)
  

  override def close(): scala.concurrent.Future[akka.Done] = clientState.close()
  override def closed: scala.concurrent.Future[akka.Done] = clientState.closed()

}

object DefaultLoginServiceClient {

  def apply(settings: GrpcClientSettings)(implicit sys: ClassicActorSystemProvider): LoginServiceClient =
    new DefaultLoginServiceClient(settings)
}

trait LoginServiceClientPowerApi {
  
  /**
   * Lower level "lifted" version of the method, giving access to request metadata etc.
   * prefer checkCredentials(cityfeed.application.grpc.LoginRequest) if possible.
   */
  
  def checkCredentials(): SingleResponseRequestBuilder[cityfeed.application.grpc.LoginRequest, cityfeed.application.grpc.LoginResponse] = ???
  
  

}
