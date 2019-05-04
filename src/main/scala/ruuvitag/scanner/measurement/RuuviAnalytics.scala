package ruuvitag.scanner.measurement

/**
  * MIT License
  *
  * Copyright (c) 2017 Scrin
  *
  * Migrated by kardoo 2019
  */
class RuuviAnalytics {

  /**
    * Calculates and populates values that can be calculated based on other
    * values, such as total acceleration and absolute humidity
    *
    * @param measurement the measurement to populate
    * @return the supplied measurement, for convenient use in streams
    */
  def calculateAllValues(measurement: RuuviMeasurement): RuuviMeasurement = {
    measurement.accelerationTotal = totalAcceleration(measurement.accelerationX, measurement.accelerationY, measurement.accelerationZ)
    measurement.absoluteHumidity = absoluteHumidity(measurement.temperature, measurement.humidity)
    measurement.dewPoint = dewPoint(measurement.temperature, measurement.humidity)
    measurement.equilibriumVaporPressure = equilibriumVaporPressure(measurement.temperature)
    measurement.airDensity = airDensity(measurement.temperature, measurement.humidity, measurement.pressure)
    measurement.accelerationAngleFromX = angleBetweenVectorComponentAndAxis(measurement.accelerationX, measurement.accelerationTotal)
    measurement.accelerationAngleFromY = angleBetweenVectorComponentAndAxis(measurement.accelerationY, measurement.accelerationTotal)
    measurement.accelerationAngleFromZ = angleBetweenVectorComponentAndAxis(measurement.accelerationZ, measurement.accelerationTotal)
    measurement
  }

  /**
    * Calculates the total acceleration strength
    *
    * @param accelerationX
    * @param accelerationY
    * @param accelerationZ
    * @return The total acceleration strength
    */
  def totalAcceleration(accelerationX: Double, accelerationY: Double, accelerationZ: Double): Double = {
    if (accelerationX.isNaN|| accelerationY.isNaN|| accelerationZ.isNaN) return 0.0
    Math.sqrt(accelerationX * accelerationX + accelerationY * accelerationY + accelerationZ * accelerationZ)
  }

  /**
    * Calculates the angle between a vector component and the corresponding
    * axis
    *
    * @param vectorComponent Vector component
    * @param vectorLength    Vector length
    * @return Angle between the components axis and the vector, in degrees
    */
  def angleBetweenVectorComponentAndAxis(vectorComponent: Double, vectorLength: Double): Double = {
    if (vectorComponent.isNaN|| vectorLength.isNaN|| (vectorLength == 0)) return 0.0
    Math.toDegrees(Math.acos(vectorComponent / vectorLength))
  }

  /**
    * Calculates the absolute humidity
    *
    * @param temperature      Temperature in Celsius
    * @param relativeHumidity Relative humidity % (range 0-100)
    * @return The absolute humidity in g/m^3
    **/
  def absoluteHumidity(temperature: Double, relativeHumidity: Double): Double = {
    if (temperature.isNaN|| relativeHumidity.isNaN) return 0.0
    equilibriumVaporPressure(temperature) * relativeHumidity * 0.021674 / (273.15 + temperature)
  }

  /**
    * Calculates the dew point
    *
    * @param temperature      Temperature in Celsius
    * @param relativeHumidity Relative humidity % (range 0-100)
    * @return The dew point in Celsius
    */
  def dewPoint(temperature: Double, relativeHumidity: Double): Double = {
    if (temperature.isNaN|| relativeHumidity.isNaN ) return 0.0
    val v = Math.log(relativeHumidity / 100 * equilibriumVaporPressure(temperature) / 611.2)
    -243.5 * v / (v - 17.67)
  }

  /**
    * Calculates the equilibrium vapor pressure of water
    *
    * @param temperature Temperature in Celsius
    * @return The vapor pressure in Pa
    */
  def equilibriumVaporPressure(temperature: Double): Double = {
    if (temperature.isNaN) return 0.0
    611.2 * Math.exp(17.67 * temperature / (243.5 + temperature))
  }

  /**
    * Calculates the air density
    *
    * @param temperature      Temperature in Celsius
    * @param relativeHumidity Relative humidity % (range 0-100)
    * @param pressure         Pressure in pa
    * @return The air density in kg/m^3
    **/
  def airDensity(temperature: Double, relativeHumidity: Double, pressure: Double): Double = {
    if (temperature.isNaN|| relativeHumidity.isNaN|| pressure.isNaN) return 0.0
    1.2929 * 273.15 / (temperature + 273.15) * (pressure - 0.3783 * relativeHumidity / 100 * equilibriumVaporPressure(temperature)) / 101300
  }
}
