package ruuvitag.scanner.format

class HexDataFormatV5 {

  def getTemperature(offset1: Byte, offset2: Byte): Double = {
    convertBytesToInt16(offset1, offset2) * 0.005
  }

  def convertBytesToInt16(b1: Byte, b2: Byte): Int = {
    ((b1 & 0xFF) << 8) | (b2 & 0xFF)
  }

  def getHumidity(offset3: Byte, offset4: Byte): Double = {
    convertBytesToInt16(offset3, offset4) * 0.0025
  }

  def getPressure(offset5: String, offset6: String): Int = {
    val hex = (offset5 + offset6)
    //two complements?
    Integer.parseUnsignedInt(hex, 16) + 50000
  }

  def getAcceleration(offset7_9_11: String, offset8_10_12: String): Double = {
    val hex = (offset7_9_11 + offset8_10_12)
    Integer.parseUnsignedInt(hex, 16).toShort * 0.001
  }

  def getBatteryVoltageAndPowerInfo(offset13: String, offset14: String): (Double, Double) = {
    val hex = (offset13 + offset14)
    val value = Integer.parseUnsignedInt(hex, 16)
    val volts = (value >> 5) * 0.001 + 1.6
    //0x1F - 11111 (left most 5 bits)
    val pinfo = (value & 0x1F) * 2 - 40
    (volts, pinfo)

  }

  def getMovement(offset15: String): Int = {
    Integer.parseUnsignedInt(offset15, 16)
  }

  def getMeasurementIndex(offset16: String, offset17: String): Int = {
    val hex = (offset16 + offset17)
    Integer.parseUnsignedInt(hex, 16)
  }

  def getMacAdress(mac: String*): String = {
    mac.mkString(":")
  }

}
