//Runestone 2015
#include <math.h>


void setup() {
  Serial.begin(9600);                //Start the Serial connection
}
void loop() {
  int lightLevel = 0;
  int temperature = 0;
  
  lightLevel = readLight();
  temperature = readTemp();
  Serial.println(lightLevel);
  Serial.println(temperature);
  delay(1000);
  
}


//http://www.seeedstudio.com/wiki/Grove_-_Light_Sensor

//Analog Sensor is used, it is plugged into A0 port. Calibration values depend on
//which sensors is used
int readLight(){  
  float Rsensor;  //Resistance of sensor in K
  int sensorValue = analogRead(0);  //Read analog port 0, values between 0-1023
  
  Rsensor=(float)(1023-sensorValue)*10/sensorValue;

  //Values are very raw, dont provide useful information
 // Serial.println("the analog read data is ");
  Serial.println(sensorValue);
 // Serial.println("the sensor resistance is ");
 // Serial.println(Rsensor,DEC);//show the light intensity on the serial monitor;
  
  //Quick empiric tests show that if analog read value is 300, its in dark
  //Between 300-600 in shadow, 600-700 light from the windows, above 800 quite bright
  //roughly 4 levels.
  
   //This would need some non-linear mapping
   //sensorValue = map(sensorValue , 0, 1024, 0, 3);
   
   if(sensorValue <= 300){
     sensorValue = 0;
     
   }
   else if(300 < sensorValue && sensorValue <= 600){
     sensorValue = 1;
   }
   else if(600 < sensorValue && sensorValue <= 800){
     sensorValue = 2;
 
   }
   else{
     sensorValue = 3;
   }
   
   return sensorValue;
  
 }
 
 
//Analog Sensor is used, it is plugged into A1 port. Calibration values depend on
//which sensors is used 
int readTemp(){
  float Rsensor; //Resistance of sensor in K
  int sensorValue = analogRead(1);
  float temperature;
  int B = 3795; // Value of the thermistor
  
  Rsensor=(float)(1023-sensorValue)*10000/sensorValue; //get the resistance of the sensor;
  temperature=1/(log(Rsensor/10000)/B+1/298.15)-273.15;//convert to temperature via datasheet ;
 // Serial.print("Current temperature is ");
 // Serial.println(temperature);
  return temperature;
}

