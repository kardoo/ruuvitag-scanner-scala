package ruuvitag.scanner.format

import ruuvitag.scanner.util.DataUtil._

class DataFormatV5(data: Array[Byte]) {

  override def toString = s"(getTemperature=$getTemperature, getHumidity=$getHumidity, getPressure=$getPressure, getAccelerationX=$getAccelerationX, getAccelerationY=$getAccelerationY, getAccelerationZ=$getAccelerationZ, getBatteryVoltageAndPowerInfo=$getBatteryVoltageAndPowerInfo, getMovement=$getMovement, getMeasurementIndex=$getMeasurementIndex, getMacAdress=$getMacAdress)"

  def getTemperature(): Double = {
    (convertBytesToInt16(data(1), data(2)) * 0.005)
  }

  def getHumidity(): Double = {
    round(convertBytesToInt16(data(3), data(4)) * 0.0025)
  }

  def getPressure(): Int = {
    convertBytesToInt16(data(5), data(6)) + 50000
  }

  def getAccelerationX(): Double = {
    round(convertBytesToInt16(data(7), data(8)) * 0.001)
  }

  def getAccelerationY(): Double = {
    round(convertBytesToInt16(data(9), data(10)) * 0.001)
  }

  def getAccelerationZ(): Double = {
    round(convertBytesToInt16(data(11), data(12)) * 0.001)
  }

  def getBatteryVoltageAndPowerInfo(): (Double, Double) = {
    val value = convertBytesToInt16(data(13), data(14))
    val volts = (value >> 5) * 0.001 + 1.6
    //0x1F - 11111 (left most 5 bits)
    val pinfo = (value & 0x1F) * 2 - 40
    (round(volts), round(pinfo))

  }

  def getMovement(): Int = {
    Integer.parseUnsignedInt(data(15).toHexString, 16)
  }

  def getMeasurementIndex(): Int = {
    convertBytesToInt16(data(16), data(17))

  }

  def getMacAdress(): String = {
    (data(18).toHexString + ":" + data(19).toHexString + ":" + data(20).toHexString + ":" + data(21).toHexString + ":" + data(22).toHexString + ":" + data(23).toHexString).replace("ffffff", "").toUpperCase
  }


}
