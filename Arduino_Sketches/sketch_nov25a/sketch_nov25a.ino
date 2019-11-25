#include <WebSocketsServer.h>

#include <IOXhop_FirebaseESP32.h>
#include <WiFi.h>

#include <ESPmDNS.h>

const char* ssid = "Safehouse_ESP";
const char* password = "Safehouse";

const int sensorPin = 23;
const int LED_ONBOARD = 2;

bool value = false;//water sensor parameter

WebSocketsServer webSocket(81);    // create a websocket server on port 81

void mDnsInitializer(){
  if (!MDNS.begin("water1")) {             // Start the mDNS responder for water.local
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
  
  //connect to WiFi
  WiFi.begin(ssid, password);
  //while not connected
  while (WiFi.status() != WL_CONNECTED) {
    delay(500);
    Serial.print(".");
  }
  Serial.print("IP:");
  Serial.println(WiFi.localIP());
  
  //Set up inputs and outputs
  pinMode(sensorPin,INPUT); 
  pinMode (LED_ONBOARD, OUTPUT);

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
   
  //if WIFI connected, turn on the blue LED
  if(WiFi.status() == WL_CONNECTED) {    
    digitalWrite(LED_ONBOARD, HIGH);
  } else digitalWrite(LED_ONBOARD, LOW);
  
  //Read water sensor
  value = digitalRead(sensorPin);

  Serial.println(String(value));
  
  sendMessageToHub(String(value));
    
}
