# ruuvitag-scanner-scala

Collects RuuviTag measurements (bluetooth le advertisements). 

Identically to RuuviCollector but using tinyb instead of hcidump/hcitool


Note that this approach can only grab measurements every 10sec... Due to tinyb limitation/gatt use cases(?) 
(As oposed to hcidump that captures all measurements)


This works fine for RAWv2_SLOW. 

For people who dont need such frequency update... and it consumes 10x less data in IndluxDB
