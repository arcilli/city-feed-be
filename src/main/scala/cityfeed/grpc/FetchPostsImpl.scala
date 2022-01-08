package cityfeed.grpc

import akka.NotUsed
import akka.stream.Materializer
import akka.stream.scaladsl.{Sink, Source}
import cats.effect.IO
import cityfeed.database.MetaMappings._
import cityfeed.Main.{postingRepository, userRepository}
import cityfeed.application.grpc.{FetchRequest, FetchService, FetchedPosts}
import cityfeed.errors.Errors.NoFetchedPosts
import cityfeed.model.User
import com.typesafe.scalalogging.LazyLogging

import java.util.UUID

class FetchPostsImpl(implicit mat: Materializer) extends FetchService with LazyLogging{
  override def fetchPosts(request: FetchRequest): Source[FetchedPosts, NotUsed] = {
    logger.info(s"$request")
    val responseIO = for {
      userInfo <- userRepository.getUserInfoById(UUID.fromString(request.userId))
      viewedPosts <- userRepository.updateAndGetViewedPostsByUser(userInfo.id.get, request.seenPosts.toList.map(_.toLong))
      postsToDisplay <- postingRepository.fetchPosts(userInfo.copy(seenPosts = viewedPosts), request.amount)
      _ <- IO.raiseWhen(postsToDisplay.isEmpty)(NoFetchedPosts())
    } yield postsToDisplay

    responseIO.attempt.unsafeRunSync() match {
      case Right(fetchedPosts) =>
        Source(fetchedPosts)
      case Left(e: NoFetchedPosts) =>
        logger.error(e.getMessage)
        Source.empty
      case Left(e) =>
        logger.error(s"Error while trying to fetch posts for user ${request.userId}: ${e.getMessage}")
        Source.empty
    }
  }
}
