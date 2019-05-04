package ruuvitag.scanner

import ruuvitag.scanner.device.RuuviTagSensor
import ruuvitag.scanner.util.ConfigUtil
import tinyb._

object RuuviScannerApp {

  val manager = BluetoothManager.getBluetoothManager
  manager.getAdapters.get(0).setRssiDiscoveryFilter(0)

  val ruuviTagSensor = new RuuviTagSensor

  //for some reason notification stop working after a while...
  //Going to extract data from every discovery

  /*        device.enableManufacturerDataNotifications((t: util.Map[lang.Short, Array[Byte]]) => {
            try {
              t.entrySet().forEach(x => {
                val key = x.getKey
                val data = x.getValue
                val meas = new MeasurementsV5(data)

                println(s"key: $key - ${meas.toString}")
              })
            } catch {
              case e: Exception => println(e)
            }

          })
          */

  def handleDevice(device: BluetoothDevice): Unit = {

    device.getManufacturerData.entrySet().forEach(entry => {
      if (!device.getManufacturerData().isEmpty) {
        try {
          if (ruuviTagSensor.isSensor(entry.getKey)) {
            ruuviTagSensor.processData(device.getRSSI, entry.getValue)
          }
        } catch {
          case e: Exception => println(e)
        }
      }
    })
  }

  def main(args: Array[String]): Unit = {

    sys.addShutdownHook(if (manager.getDiscovering) manager.stopDiscovery())
    var count = 0

    println("Starting RuuviScannerApp")
    println(s"Printout: ${ConfigUtil.isPrint()}")

    while (true) {

      if (!manager.getDiscovering) manager.startDiscovery()
      manager.getAdapters.get(0).setRssiDiscoveryFilter(-100)


      val list = manager.getDevices
      if (list != null)
        list.forEach(device => handleDevice(device))
      else println("No devices found")

      count += 1
      Thread.sleep(10000)
      println(s"Discovery[Iteration: $count, Status: ${manager.getDiscovering}] ")
    }
  }

}

