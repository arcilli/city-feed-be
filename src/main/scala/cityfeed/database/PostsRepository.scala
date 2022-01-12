package cityfeed.database

import cats.effect.IO
import cityfeed.application.grpc.{FetchedPosts, PostRequest}
import cityfeed.model.User
import doobie.{Fragments, Update}
import doobie.implicits._
import doobie.util.transactor.Transactor
import doobie.postgres.implicits._
import cats.implicits._
import doobie.implicits._
import doobie.implicits.javasql._
import doobie.implicits.javatime._
import doobie.util.compat.FactoryCompat
import cityfeed.database.MetaMappings._
import com.typesafe.scalalogging.LazyLogging

import java.sql.Timestamp
import java.util.UUID

trait PostsRepository[F[_]] {
  def savePostInDB(post: PostRequest, userInfo: User): F[Int]
  def fetchPosts(user: User, amount: Int): F[List[FetchedPosts]]
}

object PostsRepository extends LazyLogging{
  implicit def instance(implicit xa: Transactor[IO]): PostsRepository[IO] = new PostsRepository[IO] {
    override def savePostInDB(post: PostRequest, user: User): IO[Int] = {
      val userTags = post.tags.split(",").map(_.trim).toList
      sql"""
           |INSERT INTO posts (
           | text,
           | encodedimg,
           | tags,
           | city,
           | neighborhood,
           | user_id,
           | owner_full_name) VALUES (
           | ${post.message},
           | ${post.base64Image},
           | $userTags,
           | ${user.city},
           | ${user.neighborhood},
           | ${user.id.get},
           | ${user.fullName}
           |)
           |""".stripMargin.update.run.transact(xa)
    }

    override def fetchPosts(user: User, amount: Int): IO[List[FetchedPosts]] = {
      user.seenPosts.toNel match {
        case Some(seenPostsNel) =>
          (fr""" SELECT user_id, text, encodedimg, neighborhood, edited, created_date, owner_full_name
                           | FROM posts
                           | WHERE city = ${user.city}
                           | AND neighborhood = ${user.neighborhood}
                           | AND """ ++ Fragments.notIn(fr"id", seenPostsNel) ++
            fr"""ORDER BY created_date DESC LIMIT $amount""")
            .stripMargin
            .query[(UUID, String, String, String, Boolean, Timestamp, String)]
            .map {
              case (userId, message, base64Img, neighborhood, edited, createdDate, fullName) =>
                logger.debug(s"$fullName")
                FetchedPosts(
                  ownerUser = userId.toString,
                  username = fullName,
                  message = message,
                  base64Image = base64Img,
                  neighborhood = neighborhood,
                  timestamp = createdDate.toString,
                  edited = edited
                )
            }
            .stream
            .compile
            .toList
            .transact(xa)
        case None =>
          sql"""SELECT user_id, text, encodedimg, neighborhood, edited, created_date, owner_full_name
            | FROM posts
            | WHERE city = ${user.city}
            | AND neighborhood = ${user.neighborhood}
            | ORDER BY created_date DESC LIMIT $amount"""
            .stripMargin
          .query[(UUID, String, String, String, Boolean, Timestamp, String)]
          .map {
            case (userId, message, base64Img, neighborhood, edited, createdDate, fullName) =>
              FetchedPosts(
                ownerUser = userId.toString,
                username = fullName,
                message = message,
                base64Image = base64Img,
                neighborhood = neighborhood,
                edited = edited,
                timestamp = createdDate.toString
              )
          }
          .stream
          .compile
          .toList
          .transact(xa)
      }
    }
  }
}

trait PostingRepositoryProvider[F[_]] {
  val postingRepository: PostsRepository[IO]
}
