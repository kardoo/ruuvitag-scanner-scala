# ruuvitag-scanner-scala

Collects RuuviTag measurements (bluetooth le advertisements). 

Identically to RuuviCollector (https://github.com/Scrin/RuuviCollector) but using tinyb (https://github.com/intel-iot-devkit/tinyb) instead of hcidump/hcitool


Note that this approach can only grab measurements every 10sec... Due to tinyb limitation/gatt use cases(?) 
(As oposed to hcidump that captures all measurements)


This works fine for RAWv2_SLOW. 

For people who dont need such frequency update... and it consumes 10x less data in IndluxDB

TinyB is a java/C library. For this application I choose to use scala.

# Grafana | InfluxDB
You need to instal Grafana and InfluxDB to view your ruuvitag measurements. Please check this link: https://blog.ruuvi.com/rpi-gateway-6e4a5b676510

