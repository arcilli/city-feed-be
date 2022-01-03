package cityfeed.database

import cats.effect.IO
import cityfeed.errors.Errors.{NonExistentAccount, WrongCredentials}
import cityfeed.model.{User, UserCredentials}
import doobie.ConnectionIO
import doobie.implicits._
import doobie.postgres.implicits._
import doobie.util.transactor.Transactor
import simulacrum.typeclass
import sun.security.util.Password

import java.util.UUID

@typeclass
trait UserRepository[F[_]] {
  def insertUser(user: User): F[Int]
  def checkCredentials(email_address: String, password: String): F[Option[UserCredentials]]
  def getUserInfoById(userId: UUID): F[User]
  def getUserProfileIdBasedOnAccountId(accountId: UUID): F[UUID]
}

object UserRepository {
  implicit def instance(implicit xa: Transactor[IO]): UserRepository[IO] = new UserRepository[IO] {
    override def insertUser(user: User): IO[Int] = {
      val queries = for {
        _ <- insertUserCredentials(user.credentials.get)
        rows <- insertUserInfo(user)
      } yield rows
      queries.transact(xa)
    }

    private def insertUserInfo(user: User): ConnectionIO[Int] = {
      sql"""
           | INSERT INTO users_info(
           | id,
           | full_name,
           | city,
           | home_address,
           | neighborhood,
           | credentials_key
           |) VALUES (
           | ${user.id},
           | ${user.fullName},
           | ${user.city},
           | ${user.homeAddress},
           | ${user.neighborhood},
           | ${user.credentials.get.id}
           |)
           |""".stripMargin.update.run
    }

    private def insertUserCredentials(credentials: UserCredentials): ConnectionIO[Int] = {
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

    override def checkCredentials(emailAddress: String, password: String): IO[Option[UserCredentials]] = {
      val queries = for {
        unique_id <- checkForEmailAddress(emailAddress)
        _ <- IO.raiseWhen(unique_id.isEmpty)(NonExistentAccount(emailAddress))
        user <- checkForCorrectCredentials(unique_id, password)
        _ <- IO.raiseWhen(user.isEmpty)(WrongCredentials(emailAddress))
      } yield user
      queries
    }

    private def checkForEmailAddress(emailAddress: String): IO[Option[UUID]] = {
      sql"""
           | SELECT id
           | FROM users_credentials
           | WHERE email_address = $emailAddress
           """
        .stripMargin
        .query[UUID]
        .option
        .transact(xa)
    }

    private def checkForCorrectCredentials(id: Option[UUID], password: String): IO[Option[UserCredentials]] = {
      sql"""
           | SELECT id, username, email_address, password
           | FROM users_credentials
           | WHERE id = $id
           | AND password = $password
           """
        .stripMargin
        .query[UserCredentials]
        .option
        .transact(xa)
    }

    override def getUserInfoById(userId: UUID): IO[User] = {
      sql"""
            | SELECT id, full_name, city, home_address, neighborhood
            | FROM users_info
            | WHERE id = $userId
           """
        .stripMargin
        .query[(Option[UUID], String, String, String, String)]
        .map {
          case(id, fullname, city, home_address, neighborhood) =>
            User(
              id = id,
              fullName = fullname,
              city = city,
              homeAddress = home_address,
              neighborhood = neighborhood,
              credentials = None
            )
        }
        .unique
        .transact(xa)
    }

    override def getUserProfileIdBasedOnAccountId(accountId: UUID): IO[UUID] =
      sql"""
            | SELECT id FROM users_info
            | WHERE users_info.credentials_key = $accountId
           """
        .stripMargin
        .query[UUID]
        .unique
        .transact(xa)

  }
}

trait UserRepositoryProvider[F[_]] {
  val userRepository: UserRepository[F]
}
