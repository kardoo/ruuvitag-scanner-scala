package ruuvitag.scanner.util

import com.typesafe.config.{Config, ConfigFactory}

object ConfigUtil {

  val defaultConfig: Config = ConfigFactory.load();

  def isPrint(): Boolean = {
    defaultConfig.getBoolean("ruuvitag-scanner.printValues")
  }

  def isInfluxEnabled(): Boolean = {
    defaultConfig.getBoolean("influxdb.enable")
  }

  def getInfluxHost(): String = {
    defaultConfig.getString("influxdb.host")
  }

  def getInfluxPort(): Int = {
    defaultConfig.getInt("influxdb.port")
  }

  def getInfluxUser(): String = {
    defaultConfig.getString("influxdb.user")
  }


  def getInfluxPass(): String = {
    defaultConfig.getString("influxdb.pass")
  }


  def getMacTag(mac: String): String = {
    val path =s"ruuvitag-scanner.macTag.$mac"
    val exists = defaultConfig.hasPath(path)
    if(exists)  defaultConfig.getString(path) else mac
  }
}
