package cityfeed.database

import cats.effect.IO
import cityfeed.application.grpc.PostRequest
import cityfeed.model.User
import doobie.Update
import doobie.implicits._
import doobie.util.transactor.Transactor
import doobie.postgres.implicits._

import java.util.UUID

trait PostingRepository[F[_]] {
  def savePostInDB(post: PostRequest, userInfo: User): F[Int]
}

object PostingRepository {
  implicit def instance(implicit xa: Transactor[IO]): PostingRepository[IO] = new PostingRepository[IO] {
    override def savePostInDB(post: PostRequest, user: User): IO[Int] = {
      sql"""
           |INSERT INTO posts (
           | text,
           | encodedimg,
           | tags,
           | city,
           | neighborhood,
           | user_id ) VALUES (
           | ${post.message},
           | ${post.base64Image},
           | ${post.tags},
           | ${user.city},
           | ${user.neighborhood},
           | ${user.id.get}
           |)
           |""".stripMargin.update.run.transact(xa)
    }
  }
}

trait PostingRepositoryProvider[F[_]] {
  val postingRepository: PostingRepository[IO]
}
