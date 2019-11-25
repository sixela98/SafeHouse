#include <WiFi.h>
#include <Wire.h>
#include "Zanshin_BME680.h" // Include the BME680 Sensor library

#include <ESPmDNS.h>
#include <WebSocketsServer.h>


BME680_Class BME680; 

//connect SDA/SCL of BH680 sensor with GPIO22-23
#define SDA_PIN 22
#define SCL_PIN 23


const char* ssid =  "Safehouse_ESP";
//"PolluWatch";
//Alex's Data
const char* password = "Safehouse";
//"hackatown";
//Data1234
const int LED_ONBOARD = 2;

WebSocketsServer webSocket(81);    // create a websocket server on port 81

void mDnsInitializer(){
  if (!MDNS.begin("airquality1")) {             // Start the mDNS responder for water.local
    Serial.println("Error setting up MDNS responder!");
  }
  Serial.println("mDNS responder started");

  // Add service to MDNS
  MDNS.addService("http", "tcp", 80);
  MDNS.addService("ws", "tcp", 81);
}

void websocketServerInitializer(){
  webSocket.begin();                          // start the websocket server
  webSocket.onEvent(webSocketEvent);          // if there's an incomming websocket message, go to function 'webSocketEvent'
  Serial.println("WebSocket server started.");
}

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
  
  mDnsInitializer();  

  websocketServerInitializer();
  
  Serial.println("Setup Completed OK");
}

void webSocketEvent(uint8_t num, WStype_t type, uint8_t *payload, size_t lenght){
  switch(type){
    case WStype_DISCONNECTED:
      Serial.printf("[%u] Disconnected!\n", num);
      break;

    case WStype_CONNECTED:{
      IPAddress ip = webSocket.remoteIP(num);
      Serial.printf("[%u] Connected from %d.%d.%d.%d url: %s\n", num, ip[0], ip[1], ip[2], ip[3], payload);
    }
      break;

    case WStype_TEXT:{
      String _payload = String((char *) &payload[0]);
      String idLed = (_payload.substring(0,4));
      String intensity = (_payload.substring(_payload.indexOf(":")+1,_payload.length()));
      int intLed = intensity.toInt();
      
      }   
      break;
  }
}

void sendMessageToHub(String message){
  delay(300);
  webSocket.broadcastTXT(message);
}

void loop() {
  webSocket.loop();
  static int32_t temperature, humidity, pressure, gas; 
  
  //if WIFI connected, turn on the blue LED
  if(WiFi.status() == WL_CONNECTED) {    
    pinMode (LED_ONBOARD, OUTPUT);
    digitalWrite(LED_ONBOARD, HIGH);
  }

  temperature /= 100.0;
  humidity /= 1000.0;
  gas /= 100.0;
  
  BME680.getSensorData(temperature,humidity,pressure,gas); // Get new readings
  String dataString = String(temperature) + " " + String(humidity) + " " + String(gas);

  sendMessageToHub(dataString);
  
  Serial.println("Air Quality Set in Firebase");
    
}

void BMEsetup(){
  Serial.println(F("- Setting 8x oversampling"));
  BME680.setOversampling(TemperatureSensor,Oversample8);
  BME680.setOversampling(HumiditySensor,Oversample8);
  BME680.setOversampling(PressureSensor,SensorOff);//not used
  BME680.setIIRFilter(IIR16);
  Serial.println("Sensor Setup Complete____");
}
