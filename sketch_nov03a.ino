#include <IOXhop_FirebaseESP32.h>
#include <WiFi.h>
#include <Wire.h>
#include "Zanshin_BME680.h" // Include the BME680 Sensor library

BME680_Class BME680; 

//connect SDA/SCL of BH680 sensor with GPIO18/GPIO19
#define SDA_PIN 22
#define SCL_PIN 23

const char* ssid = "";
const char* password = "";
const int sensorPin = 34;
const int LED_ONBOARD = 2;
int value = 0;

void setup() {
  Serial.begin(115200);
  Wire.begin(SDA_PIN, SCL_PIN); //Initiate the Wire library and join the I2C bus 
  while (!BME680.begin()) // Start using hardware SPI protocol
  {
    Serial.println(F("-  Unable to find BME680. Waiting 3 seconds."));
    delay(3000);
  } 
  
  BMEsetup();
 
  //connect to WiFi
  WiFi.begin(ssid, password);
  //while not connected
  while (WiFi.status() != WL_CONNECTED) {
    delay(500);
    Serial.print(".");
  }
  Serial.print("IP:");
  Serial.println(WiFi.localIP());
  
  Firebase.begin("elec390-80d64.firebaseio.com", "VwZ3szBG63HAu3wYv7SWe7oLYyAusqMQ1PygF0Dq");
  Firebase.setInt("IP address", WiFi.localIP()); 
 
}

void loop() {
  static int32_t temperature, humidity, pressure, gas; 
  
  //if WIFI connected, turn on the board LED
  if(WiFi.status() == WL_CONNECTED) {    
    pinMode (LED_ONBOARD, OUTPUT);
    digitalWrite(LED_BUILTIN, HIGH);
  }
  
  BME680.getSensorData(temperature,humidity,pressure,gas); // Get new readings
  Firebase.setInt("Air quality/Temperature", temperature/100.0);
  Firebase.setInt("Air quality/Humidity", humidity/1000.0);// Humidity in mili-percent
  Firebase.setInt("Air quality/Gas",gas/100.0); //gas resistance in mOhm
  Serial.println("Air Qualiity Set in Firebase");
  
  // If resistance is low (short circuit to 3.3V)
  value = analogRead(sensorPin);
  if(value > 3000){
    //if wet change data
    Firebase.set("sensor", true); 
    Serial.print("Firebase sensor set true");
  } 
  else Firebase.set("sensor", false);
  Serial.println("____");
    
}
void BMEsetup(){
  Serial.println(F("- Setting 16x oversampling for all sensors"));
  BME680.setOversampling(TemperatureSensor,Oversample16);
  BME680.setOversampling(HumiditySensor,   Oversample16);
  BME680.setOversampling(PressureSensor,   Oversample16);//not used
  Serial.println(F("- Setting IIR filter to maximum value of 16 samples"));
  BME680.setIIRFilter(IIR16);
}
