#include <IOXhop_FirebaseESP32.h>
#include <WiFi.h>

//Libraries to install:
                      //IOXhop_FirebaseESP32 from ZIP
                      //Arduino JSON from Zip
//include ArduinoJson header file in project folder 

const char* ssid = "PolluWatch";
const char* password = "hackatown";
const int LED_BUILTIN = 2;
const int sensorPin = 34;
int value = 0;

void setup() {
  Serial.begin(115200);
  //connect to WiFi
  WiFi.begin(ssid, password);
  
  //while not connected
  while (WiFi.status() != WL_CONNECTED) {
    
    delay(500);
    Serial.print(".");
  }
  Serial.println(".......");
  Serial.println("WiFi Connected....IP Address:");
  Serial.println(WiFi.localIP());

  
  pinMode (LED_BUILTIN, OUTPUT);
  pinMode (sensorPin, INPUT);

  Firebase.begin("elec390-80d64.firebaseio.com", "VwZ3szBG63HAu3wYv7SWe7oLYyAusqMQ1PygF0Dq");

}
void loop() {
  
  
//  bool initial_state = Firebase.getBool("sensor");
//  if(initial_state == true){
//    Serial.println("TRUE");
////    delay(500);
//  }
//  else if(initial_state == false){
//    Serial.println("FALSE");
////    delay(500);
//  }

// If resistance is low (short circuit to 3.3V)
  value = analogRead(sensorPin);
  if(value > 3000){
//    flash();
    Firebase.set("sensor", true);
  } 
  else Firebase.set("sensor", false);
  
}

void flash(){
  //flash the onboard LED
  digitalWrite(LED_BUILTIN, HIGH);
  delay(1000);
  digitalWrite(LED_BUILTIN, LOW);
  delay(1000);
}
