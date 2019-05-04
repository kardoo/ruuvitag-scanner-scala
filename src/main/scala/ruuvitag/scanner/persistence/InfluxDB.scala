package ruuvitag.scanner.persistence

import com.paulgoldbaum.influxdbclient._
import ruuvitag.scanner.measurement.RuuviMeasurement
import ruuvitag.scanner.util.ConfigUtil

import scala.concurrent.Await
import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.duration._

class InfluxDB {


  val host = ConfigUtil.getInfluxHost()
  val port = ConfigUtil.getInfluxPort()
  val user = ConfigUtil.getInfluxUser()
  val pass = ConfigUtil.getInfluxPass()

  val influxdb = InfluxDB.connect(
    host,
    port,
    user,
    pass
  )


  //val w = influxdb.showDatabases()
  //val result = Await.result(w, 5000 millis)
//result.foreach(x => println(s"db: $x"))

  val database = influxdb.selectDatabase("ruuvi")

  def insertMeasurement(meas: RuuviMeasurement) = {
    val point = Point("ruuvi_measurements")
      .addTag("mac", meas.mac)
      .addTag("name", meas.name)
      .addField("absoluteHumidity", meas.absoluteHumidity)
      .addField("accelerationAngleFromX", meas.accelerationAngleFromX)
      .addField("accelerationAngleFromY", meas.accelerationAngleFromY)
      .addField("accelerationAngleFromZ", meas.accelerationAngleFromZ)
      .addField("accelerationTotal", meas.accelerationTotal)
      .addField("accelerationX", meas.accelerationX)
      .addField("accelerationY", meas.accelerationY)
      .addField("accelerationZ", meas.accelerationZ)
      .addField("airDensity", meas.airDensity)
      .addField("batteryVoltage", meas.batteryVoltage)
      .addField("dewPoint", meas.dewPoint)
      .addField("equilibriumVaporPressure", meas.equilibriumVaporPressure)
      .addField("humidity", meas.humidity)
      .addField("measurementSequenceNumber", meas.measurementSequenceNumber)
      .addField("movementCounter", meas.movementCounter)
      .addField("pressure", meas.pressure)
      .addField("rssi", meas.rssi)
      .addField("temperature", meas.temperature)
      .addField("txPower", meas.txPower)
    database.write(point)
  }

}
