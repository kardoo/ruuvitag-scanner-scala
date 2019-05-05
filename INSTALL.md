pi@raspberrypi:~ $ sudo apt-get update

pi@raspberrypi:~ $ sudo apt-get upgrade

 #java & bluez & doxxygen

pi@raspberrypi:~ $ sudo apt-get install oracle-java8-jdk libglib2.0-dev cmake bluez doxygen

# add the this: export JAVA_HOME=/usr/lib/jvm/jdk-8-oracle-arm32-vfp-hflt/

pi@raspberrypi:~ $ echo "export JAVA_HOME=/usr/lib/jvm/jdk-8-oracle-arm32-vfp-hflt/" &gt;&gt; ~/.bashrc

pi@raspberrypi:~ $ source ~/.bashrc

 #scala

pi@raspberrypi:~ $ wget https://downloads.typesafe.com/scala/2.12.8/scala-2.12.8.tgz

pi@raspberrypi:~ $ sudo mkdir /usr/lib/scala

pi@raspberrypi:~ $ sudo tar -xf scala-2.12.8.tgz -C /usr/lib/scala

pi@raspberrypi:~ $ sudo ln -s /usr/lib/scala/scala-2.12.8/bin/scala /bin/scala

pi@raspberrypi:~ $ sudo ln -s /usr/lib/scala/scala-2.12.8/bin/scalac /bin/scalac

pi@raspberrypi:~ $ rm scala-2.12.8.tgz

 #Update bluez to 5.50 (Optional)

 #https://scribles.net/updating-bluez-on-raspberry-pi-5-43-to-5-48/

pi@raspberrypi:~ $ sudo apt-get update

pi@raspberrypi:~ $ sudo apt-get upgrade

pi@raspberrypi:~ $ sudo apt-get install libdbus-1-dev libglib2.0-dev libudev-dev libical-dev libreadline-dev -y

pi@raspberrypi:~ $ wget www.kernel.org/pub/linux/bluetooth/bluez-5.50.tar.xz

pi@raspberrypi:~ $ sudo mkdir /usr/local/bluez-5.50

pi@raspberrypi:~ $ sudo tar -xvf bluez-5.50.tar.xz -C /usr/local/

pi@raspberrypi:~ $ cd /usr/local/bluez-5.50

pi@raspberrypi:/usr/local/bluez-5.50 $ sudo ./configure --prefix=/usr --mandir=/usr/share/man --sysconfdir=/etc --localstatedir=/var --enable-experimental

pi@raspberrypi:/usr/local/bluez-5.50 $ sudo make -j4

pi@raspberrypi:/usr/local/bluez-5.50 $ sudo make install

pi@raspberrypi:/usr/local/bluez-5.50 $ sudo reboot

 #Install tinyb - bluetooth library 

 #https://github.com/intel-iot-devkit/tinyb

 #http://www.martinnaughton.com/2017/07/install-intel-tinyb-java-bluetooth.html

pi@raspberrypi:~ $ wget https://github.com/intel-iot-devkit/tinyb/archive/master.zip

pi@raspberrypi:~ $ unzip master.zip

pi@raspberrypi:~ $ sudo mv tinyb-master/ /usr/local/tinyb

pi@raspberrypi:~ $ cd /usr/local/tinyb

pi@raspberrypi:/usr/local/tinyb $ mkdir build

pi@raspberrypi:/usr/local/tinyb $ cd build/

pi@raspberrypi:/usr/local/tinyb/build $ sudo -E cmake -DBUILDJAVA=ON -DCMAKE_INSTALL_PREFIX=/usr ..

pi@raspberrypi:/usr/local/tinyb/build $ sudo make

pi@raspberrypi:/usr/local/tinyb/build $ sudo make install

pi@raspberrypi:~ $ rm master.zip

 # start ruuvitag_scanner_scala

 pi@raspberrypi:~ $ mkdir ruuvitag_scanner_scala

 pi@raspberrypi:~ $ cd ruuvitag_scanner_scala

 

 

 pi@raspberrypi:~/ruuvitag_scanner_scala $ cat ruuviscanner.service

[Unit]

Description=Ruuvi Scanner Remote Service

After=network.target

[Service]

Type=simple

User=root

WorkingDirectory=/home/pi/ruuvitag_scanner_scala

ExecStart=/bin/scala  -Djava.library.path=/usr/lib/arm-linux-gnueabihf/ -Dconfig.file=/home/pi/ruuvitag_scanner_scala/application_remote.conf -cp /usr/lib/lib/java/tinyb.jar:ruuvitag-scanner-scala-1.0.jar:lib/* ruuvitag.scanner.RuuviScannerApp &gt; /var/log/ruuvi_scanner_remote.log 2&gt;&1

Restart=always

[Install]

WantedBy=multi-user.target

 sudo cp ruuviscanner.service  /etc/systemd/system/

 sudo systemctl start ruuviscanner

 sudo systemctl status ruuviscanner

 sudo systemctl enable ruuviscanner

 ps -ef | grep ruuvi
