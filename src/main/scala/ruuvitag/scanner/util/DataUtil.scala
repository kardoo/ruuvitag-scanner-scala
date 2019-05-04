package ruuvitag.scanner.util

object DataUtil {

  def convertBytesToInt16(b1: Byte, b2: Byte): Int = {
    ((b1 & 0xFF) << 8) | (b2 & 0xFF)
  }

  def round(value: Double): Double = {
    BigDecimal(value).setScale(2, BigDecimal.RoundingMode.HALF_UP).toDouble

  }

}
