package cityfeed.database

import cats.effect.{Blocker, ContextShift, IO}
import cityfeed.Settings
import com.typesafe.scalalogging.LazyLogging
import com.zaxxer.hikari.{HikariConfig, HikariDataSource}
import doobie.hikari.HikariTransactor
import doobie.util.transactor.Transactor

import scala.concurrent.ExecutionContext

trait TransactorProvider {
  implicit val transactor: Transactor[IO]
}

object TransactorProvider extends LazyLogging {
  implicit def instance(implicit settings: Settings, ec: ExecutionContext): Transactor[IO] = {
    implicit val contextShift: ContextShift[IO] = IO.contextShift(ec)
    HikariTransactor(new HikariDataSource(getDatabaseConfig(settings)), ec, Blocker.liftExecutionContext(ec))
  }

  def getDatabaseConfig(settings: Settings): HikariConfig = {
    logger.debug(s"$settings")
    val config = new HikariConfig()
    config.setJdbcUrl(settings.database.url)
    config.setUsername(settings.database.user)
    config.setPassword(settings.database.password)
    config.setMaximumPoolSize(settings.database.maxPoolSize)
    config.setSchema(settings.database.schema)
    config
  }
}
