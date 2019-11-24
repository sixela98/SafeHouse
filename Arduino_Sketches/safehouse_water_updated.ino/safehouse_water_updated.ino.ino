#include <WebSocketsServer.h>

#include <ESP8266WiFi.h>
#include <WiFiClient.h>
#include <ESP8266mDNS.h>

#include <DHT.h>

#ifndef STASSID
#define STASSID "Safehouse_ESP" // The SSID (name) of the Wi-Fi network you want to connect to
#define STAPSK  "Safehouse"     // The password of the Wi-Fi network
#endif

#define DATA_PIN 2
#define DHTTYPE DHT22

const char* ssid = STASSID;
const char* password = STAPSK;

WebSocketsServer webSocket(81);    // create a websocket server on port 81

long previousMillis = 0;                // Timer to get rid of delay, will store the last time something was updated
const int ledPin = 14;
int ledState = LOW;             // ledState used to set the LED

int chk;
float hum;  //Stores humidity value
float temp; //Stores temperature value

DHT dht(DATA_PIN, DHTTYPE);

void wifiInitializer(){
  WiFi.mode(WIFI_STA);          // Put Wifi chip in station mode
  WiFi.begin(ssid, password);   // Connect to the network
  Serial.println("");             
  Serial.print("Connecting to ");
  Serial.print(ssid); Serial.println(" ...");

  int i = 0;                    // Wifi connection waiting counter
  
  while (WiFi.status() != WL_CONNECTED) { // Wait for the Wi-Fi to connect
    delay(500);
    // if the LED is off turn it on and vice-versa:
      
    unsigned long currentMillis = millis();

//  Delay of 1s between each count
    if(currentMillis - previousMillis > 500) {
      if (ledState == LOW)
        ledState = HIGH;
      else
        ledState = LOW;
        
      previousMillis = currentMillis;     // Update the timer  
      Serial.print(++i); Serial.print(" ");
      digitalWrite(ledPin, ledState);
    }
  }

  digitalWrite(ledPin, LOW);

  Serial.println("\n");
  Serial.println("Connection established!");  
  Serial.print("IP address:\t");
  Serial.println(WiFi.localIP());         // Send the IP address of the ESP8266 to the computer
}

void mDnsInitializer(){
  if (!MDNS.begin("water")) {             // Start the mDNS responder for water.local
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

  pinMode(ledPin, OUTPUT); // external LED test 
  
  Serial.begin(115200);         // Start the Serial communication to send messages to the computer
  delay(10);
  Serial.println('\n');
  
  wifiInitializer();

  mDnsInitializer();  

  websocketServerInitializer();

  dht.begin();
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
      
      updateLed (idLed, intLed);
      }   
      break;
  }
}

void updateLed(String idLed, int intLed){
  int valPWM = map(intLed, 0, 99, 0, 254);

  if(valPWM != 0){
    sendMessageToHub(String(valPWM));
    Serial.println(valPWM);
  }
  
  analogWrite(ledPin, valPWM);
}

void sendMessageToHub(String message){
//  delay(1000);
  webSocket.broadcastTXT(message);
}

void loop() {
  MDNS.update();
  webSocket.loop();

  hum = dht.readHumidity();
  temp= dht.readTemperature();

  String tempStr = "Temperature: " + String(temp) + "C";

  sendMessageToHub(String(temp));
}
