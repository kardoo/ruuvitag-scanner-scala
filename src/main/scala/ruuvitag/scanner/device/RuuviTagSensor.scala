package ruuvitag.scanner.device

import ruuvitag.scanner.format.DataFormatV5
import ruuvitag.scanner.measurement.{RuuviAnalytics, RuuviMeasurement}
import ruuvitag.scanner.persistence.InfluxDB
import ruuvitag.scanner.util.ConfigUtil

class RuuviTagSensor {

  final val RUUVI_MANUFACTURER_KEY = "499"


  val influxDB: InfluxDB = new InfluxDB

  def processData(rssi: Short, manufacturerData: Array[Byte]) {

    val df5 = new DataFormatV5(manufacturerData)

    val rMeas = new RuuviMeasurement

    rMeas.rssi = rssi

    rMeas.temperature = df5.getTemperature()
    rMeas.humidity = df5.getHumidity()
    rMeas.pressure = df5.getPressure()
    rMeas.accelerationX = df5.getAccelerationX
    rMeas.accelerationY = df5.getAccelerationY
    rMeas.accelerationZ = df5.getAccelerationZ

    val (volts, power) = df5.getBatteryVoltageAndPowerInfo
    rMeas.batteryVoltage = volts
    rMeas.txPower = power.toInt
    rMeas.movementCounter = df5.getMovement
    rMeas.measurementSequenceNumber = df5.getMeasurementIndex
    rMeas.mac = df5.getMacAdress
    rMeas.name = ConfigUtil.getMacTag(rMeas.mac.replaceAll(":",""))
    val measAnalytics = new RuuviAnalytics
    measAnalytics.calculateAllValues(rMeas)

    if(ConfigUtil.isPrint())
      println(rMeas)

    if(ConfigUtil.isInfluxEnabled())
      influxDB.insertMeasurement(rMeas)


  }

  def isSensor(key: Short): Boolean = {
    // 1177 to hex = 0499 - manufacture code
    key.toHexString.equals(RUUVI_MANUFACTURER_KEY)
  }
}
