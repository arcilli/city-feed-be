package cityfeed.database

import cats.effect.IO
import cityfeed.model.{User, UserCredentials}
import doobie.ConnectionIO
import doobie.implicits._
import doobie.postgres.implicits._
import doobie.util.transactor.Transactor
import simulacrum.typeclass

@typeclass
trait UserRepository[F[_]] {
  def insertUser(user: User): F[Int]
}

object UserRepository {
  implicit def instance(implicit xa: Transactor[IO]): UserRepository[IO] = new UserRepository[IO] {
    override def insertUser(user: User): IO[Int] = {
      val queries = for {
        _ <- insertUserInfo(user)
        rows <- insertUserCredentials(user.credentials)
      } yield rows
      queries.transact(xa)
    }

    private def insertUserInfo(user: User): ConnectionIO[Int] = {
      sql"""
           | INSERT INTO users_info(
           | full_name,
           | city,
           | home_address,
           | neighborhood,
           | credentials_key
           |) VALUES (
           | ${user.fullName},
           | ${user.city},
           | ${user.homeAddress},
           | ${user.neighborhood},
           | ${user.credentials.id}
           |)
           |""".stripMargin.update.run
    }

    def insertUserCredentials(credentials: UserCredentials): ConnectionIO[Int] = {
      sql"""
           | INSERT INTO users_credentials(
           | id,
           | username,
           | email_address,
           | password
           |) VALUES (
           | ${credentials.id},
           | ${credentials.username},
           | ${credentials.email_address},
           | ${credentials.password}
           |)
           |""".stripMargin.update.run
    }
  }
}

trait UserRepositoryProvider[F[_]] {
  val userRepository: UserRepository[F]
}
