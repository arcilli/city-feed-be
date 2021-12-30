package cityfeed

import akka.actor._
import cakemix._
import com.typesafe.config.Config
import pureconfig._
import pureconfig.generic.auto._

object Settings extends ExtensionId[Settings] with ExtensionIdProvider {
  override def createExtension(system: ExtendedActorSystem): Settings = new Settings(system)

  override def lookup: ExtensionId[Settings] = Settings

  def load(rootConfig: Config): Settings = {
    ConfigSource.fromConfig(rootConfig.getConfig("cityfeed"))
      .loadOrThrow[Settings]
  }
}

case class Settings(database: DatabaseConfig, grpc: GrpcSettings) extends Extension {
  def this(rootConfig: Config) = this(
    Settings.load(rootConfig).database,
    Settings.load(rootConfig).grpc
  )

  def this(system: ExtendedActorSystem) = this(system.settings.config)
}

case class DatabaseConfig(url: String, user: String, password: String, maxPoolSize: Int, schema: String)

case class GrpcSettings(port: Int)

trait SettingsProvider {
  val settings: Settings
}

trait DefaultSettingsProvider extends SettingsProvider with ActorRefFactoryProvider {
  lazy val settings: Settings = Settings(actorSystem)
}
