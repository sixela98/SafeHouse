#include <IOXhop_FirebaseESP32.h>
#include <WiFi.h>

const char* ssid = "PolluWatch";
//"PolluWatch";
const char* password = "hackatown";
//"hackatown";
const int sensorPin = 23;
const int LED_ONBOARD = 2;

bool value = false;//water sensor parameter

void setup() {
  Serial.begin(115200);
  
  //connect to WiFi
  WiFi.begin(ssid, password);
  //while not connected
  while (WiFi.status() != WL_CONNECTED) {
    delay(500);
    Serial.print(".");
  }
  Serial.print("IP:");
  Serial.println(WiFi.localIP());
  
  //Connect to Firebase
  Firebase.begin("elec390-80d64.firebaseio.com","VwZ3szBG63HAu3wYv7SWe7oLYyAusqMQ1PygF0Dq");
  Firebase.setInt("IP address", WiFi.localIP()); 
  //Set up inputs and outputs
  pinMode(sensorPin,INPUT); 
  pinMode (LED_ONBOARD, OUTPUT);
  Serial.println("Setup Completed OK");
}

void loop() {  
  //if WIFI connected, turn on the blue LED
  if(WiFi.status() == WL_CONNECTED) {    
    digitalWrite(LED_ONBOARD, HIGH);
  }
  
  //Read water sensor
  value = digitalRead(sensorPin);
  if(value == true){
    //if wet change data
    Firebase.set("Property1/Water sensor 1/State", true); 
    Serial.print("Firebase sensor set true");
    delay(5000);
  } 
  else Firebase.setBool("Property1/Water sensor 1/State", false);
  if(Firebase.failed()){
    Serial.print(Firebase.error());
  }
  delay(1000);
  Serial.println("____");
    
}
