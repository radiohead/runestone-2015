//Runestone 2015
#include <math.h>
#include <stdio.h>
#include <SoftwareSerial.h>// import the serial library
#include "DHT.h" // Humidity sensorlib
#include <Wire.h>
#include "Adafruit_SI1145.h" // Import for lightsensorlib

//#define SENSORNAME "SENSOR1"
//#define SENSORNAME "SENSOR2"
#define SENSORNAME "SENSOR3"


  #define DHTPIN 7     // What pin we're connected to with the Humidity sensor
  // The type you're using!
  #define DHTTYPE DHT22   // DHT 22  (AM2302)
  DHT dht(DHTPIN, DHTTYPE);
  char lText[25];
  char hText[25];
  char tText[25];

// SI1145 sensor is connected to SDA and SCL. Pins 20 & 21.
Adafruit_SI1145 lightsensor = Adafruit_SI1145();

  SoftwareSerial SensorBT(10, 11); // RX, TX. 10 and 11 are pins on the board. RX from BT goed to 10
  float lightLevel = 0;
  float temperature = 0;
  float humidity = 0;
  
  char buffer[100]; //UART buffer




void setup() {
  Serial.begin(9600);                //Start the Serial connection
  SensorBT.begin(9600);              //Start Bluetooth
  dht.begin();
}

void loop() {
  lightLevel = lightsensor.readVisible();
  temperature = readTemp();
  humidity = readHum();
  
  //If connection is available and any message has been received from master, then send to master.
  if(SensorBT.available() /*&& (SensorBT.read() > 0)*/ ){
    Serial.print(" NAME: [");
    Serial.print(SENSORNAME);
    Serial.print("] LIGHT: [");
    Serial.print(lightLevel);
    Serial.print("] TEMPERATURE: [");
    Serial.print(temperature);
    Serial.print("] HUMIDITY: [");
    Serial.print(humidity);
    Serial.print("]\r\n");
    sendOverBT();

  }
  delay(5000);
}

void sendOverBT(){
  //Make the message, name depends which device is used. To set name, the module needs to be configured and that cannot be
  //Done on the fly
  dtostrf(humidity,4,2,hText);
  dtostrf(lightLevel,4,2,lText);
  dtostrf(temperature,4,2,tText);
  sprintf(buffer," NAME: [SENSOR1] LIGHT: [%s] TEMPERATURE: [%s] HUMIDITY: [%s]\r\n",lText ,tText, hText); // sprintf cannto handle floats.
  SensorBT.write(buffer);  
}


float readTemp(){
  // Reading temperature or humidity takes about 250 milliseconds!
  // Sensor readings may also be up to 2 seconds 'old' (its a very slow sensor)
  float t = dht.readTemperature();
  // check if returns are valid, if they are NaN (not a number) then something went wrong!
  if (isnan(t)) {
    Serial.println("Failed to read temperature from DHT22");
    return temperature;
  } else {
    return t;
  }
 }
float readHum(){
  // Reading temperature or humidity takes about 250 milliseconds!
  // Sensor readings may also be up to 2 seconds 'old' (its a very slow sensor)
  float h = dht.readHumidity();
  // check if returns are valid, if they are NaN (not a number) then something went wrong!
  if (isnan(h)) {
    Serial.println("Failed to read humidity from DHT22");
    return humidity;
  } else {
    return h;
  }
 }
