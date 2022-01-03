package cityfeed.grpc

import akka.stream.Materializer
import cityfeed.Main.{postingRepository, userRepository}
import cityfeed.application.grpc.{PostRequest, PostResponse, PostingService}
import com.typesafe.scalalogging.LazyLogging

import java.util.UUID
import scala.concurrent.Future

class PostingImpl(implicit mat: Materializer) extends PostingService with LazyLogging {
  override def createPost(request: PostRequest): Future[PostResponse] = {
    logger.info(s"received request: $request")
    val responseIO = for {
      userInfo <- userRepository.getUserInfoById(UUID.fromString(request.userToken))
      _ <- postingRepository.savePostInDB(request, userInfo)
    } yield userInfo.id.get


    responseIO.attempt.map{
      case Right(id) =>
        logger.info(s"Saved post created by user $id with success")
        PostResponse(postCreated = true)
      case Left(e) =>
        logger.error(s"Couldn't save post: ${e.getMessage}")
        PostResponse()
    }.unsafeToFuture()
  }
}
