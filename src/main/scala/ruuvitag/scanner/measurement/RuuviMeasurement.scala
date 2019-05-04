package ruuvitag.scanner.measurement

/**
  * This class contains all the possible fields/data acquirable from a RuuviTag
  * in a "human format", for example the temperature as a decimal number rather
  * than an Int meaning one 200th of a degree. Not all fields are necessarily
  * present depending on the data format and implementations.
  *
  * MIT License
  *
  * Copyright (c) 2017 Scrin
  *
  * Migrated by kardoo 2019
  *
  */

class RuuviMeasurement {
  ////////////////////
  // Special values //
  ////////////////////
  /**
    * Timestamp in milliseconds, normally not populated to use local time
    */
  var time = 0L

  /**
    * Friendly name for the tag
    */
  var name: String = "na"

  //////////////////////////////
  // Actual (received) values //

  /**
    * MAC address of the tag as seen by the receiver
    */
  var mac: String = "na"
  /**
    * Ruuvi Data format, see: https://github.com/ruuvi/ruuvi-sensor-protocols
    */
  var dataFormat: Int = 0
  /**
    * Temperature in Celsius
    */
  var temperature = .0
  /**
    * Relative humidity in percentage (0-100)
    */
  var humidity = .0
  /**
    * Pressure in Pa
    */
  var pressure = .0
  /**
    * Acceleration of X axis in G
    */
  var accelerationX = .0
  /**
    * Acceleration of Y axis in G
    */
  var accelerationY = .0
  /**
    * Acceleration of Z axis in G
    */
  var accelerationZ = .0
  /**
    * Battery voltage in volts
    */
  var batteryVoltage = .0
  /**
    * TX power in dBm
    */
  var txPower: Int = 0
  /**
    * Movement counter (incremented by interrupts from the accelerometer)
    */
  var movementCounter: Int = 0
  /**
    * Measurement sequence number (incremented every time a new measurement is
    * made). Useful for measurement de-duplication.
    */
  var measurementSequenceNumber: Int = 0
  /**
    * Tag ID as sent by the tag. In general this should be the same as the MAC.
    */
  var tagID: String = "na"

  /**
    * The RSSI at the receiver
    */
  var rssi: Int = 0

  /////////////////////////////////////////////////////////////////////
  // Calculated values (on the receiving side)                       //
  // All of these can be calculated based on the Actual values above //

  /**
    * Total acceleration
    */
  var accelerationTotal = .0
  /**
    * The angle between the acceleration vector and X axis
    */
  var accelerationAngleFromX = .0
  /**
    * The angle between the acceleration vector and Y axis
    */
  var accelerationAngleFromY = .0
  /**
    * The angle between the acceleration vector and Z axis
    */
  var accelerationAngleFromZ = .0
  /**
    * Absolute humidity in g/m^3
    **/
  var absoluteHumidity = .0
  /**
    * Dew point in Celsius
    */
  var dewPoint = .0
  /**
    * Vapor pressure of water
    */
  var equilibriumVaporPressure = .0
  /**
    * Density of air
    */
  var airDensity = .0

  override def toString: String = "RuuviMeasurement{" + "time=" + time + ", name=" + name + ", mac=" + mac + ", dataFormat=" + dataFormat + ", temperature=" + temperature + ", humidity=" + humidity + ", pressure=" + pressure + ", accelerationX=" + accelerationX + ", accelerationY=" + accelerationY + ", accelerationZ=" + accelerationZ + ", batteryVoltage=" + batteryVoltage + ", txPower=" + txPower + ", movementCounter=" + movementCounter + ", measurementSequenceNumber=" + measurementSequenceNumber + ", tagID=" + tagID + ", rssi=" + rssi + ", accelerationTotal=" + accelerationTotal + ", accelerationAngleFromX=" + accelerationAngleFromX + ", accelerationAngleFromY=" + accelerationAngleFromY + ", accelerationAngleFromZ=" + accelerationAngleFromZ + ", absoluteHumidity=" + absoluteHumidity + ", dewPoint=" + dewPoint + ", equilibriumVaporPressure=" + equilibriumVaporPressure + ", airDensity=" + airDensity + '}'
}
