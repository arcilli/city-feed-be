package cityfeed.database

import cats.effect.IO
import org.flywaydb.core.Flyway

object Migration {
  def migrate(schema: String, jdbcUrl: String, user: String, password: String): IO[Unit] = {
    IO {
      Flyway.configure()
        .dataSource(jdbcUrl, user, password)
        .schemas(schema)
        .defaultSchema(schema)
        .baselineOnMigrate(true)
        .load().migrate()
    } *> IO.unit
  }
}
