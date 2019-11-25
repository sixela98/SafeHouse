#include <IOXhop_FirebaseESP32.h>
#include <WiFi.h>
#include <Wire.h>
#include "Zanshin_BME680.h" // Include the BME680 Sensor library

BME680_Class BME680; 

//connect SDA/SCL of BH680 sensor with GPIO22-23
#define SDA_PIN 22
#define SCL_PIN 23


const char* ssid =  "PolluWatch";
//"PolluWatch";
//Alex's Data
const char* password = "hackatown";
//"hackatown";
//Data1234
const int LED_ONBOARD = 2;

void setup() {
  Serial.begin(115200);
  
  Wire.begin(SDA_PIN, SCL_PIN); //Initiate the Wire library and join the I2C bus 
  while (!BME680.begin()) // Start using hardware SPI protocol
  {
    Serial.println(F("-  Unable to find BME680. Waiting 3 seconds."));
    delay(3000);
  } 
  
  BMEsetup();
  BME680.setGas(320,150); // 320°c for 150 ms
 
  //connect to WiFi
  WiFi.begin(ssid, password);
  //while not connected
  while (WiFi.status() != WL_CONNECTED) {
    delay(500);
    Serial.print(".");
  }
  Serial.print("IP:");
  Serial.println(WiFi.localIP());
  
  Firebase.begin("elec390-80d64.firebaseio.com","VwZ3szBG63HAu3wYv7SWe7oLYyAusqMQ1PygF0Dq");
  //VwZ3szBG63HAu3wYv7SWe7oLYyAusqMQ1PygF0Dq
  Firebase.setInt("IP address", WiFi.localIP()); 
}

void loop() {
  static int32_t temperature, humidity, pressure, gas; 
  
  //if WIFI connected, turn on the blue LED
  if(WiFi.status() == WL_CONNECTED) {    
    pinMode (LED_ONBOARD, OUTPUT);
    digitalWrite(LED_ONBOARD, HIGH);
  }
  
  BME680.getSensorData(temperature,humidity,pressure,gas); // Get new readings
  UpdateData(temperature,humidity,gas); //Store new reading in Firebase
  Serial.println("Air Quality Set in Firebase");
  delay(5000);
  Serial.println("__");
    
}

void BMEsetup(){
  Serial.println(F("- Setting 8x oversampling"));
  BME680.setOversampling(TemperatureSensor,Oversample8);
  BME680.setOversampling(HumiditySensor,Oversample8);
  BME680.setOversampling(PressureSensor,SensorOff);//not used
  BME680.setIIRFilter(IIR16);
  Serial.println("Sensor Setup Complete____");
}
void UpdateData(int32_t temp,int32_t hum,int32_t gasresistance){
  Firebase.setInt("Property1/Air quality 1/Temperature", temp/100.0);
  Firebase.setInt("Property1/Air quality 1/Humidity", hum/1000.0);// Humidity in mili-percent
  Firebase.setInt("Property1/Air quality 1/Gas",gasresistance/100.0); //gas resistance in mOhm
  //If the air quality increases, then the gas resistance reading will increase 
  //If the air quality decreases, then the gas resistance reading will decrease
  //Sensitive to a wide range of different gases,methane, volatile organic compounds (VOCs), carbon monoxide, ethanol, etc 
}
