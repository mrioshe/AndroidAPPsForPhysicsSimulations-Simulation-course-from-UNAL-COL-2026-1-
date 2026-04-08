//para comunicación WiFi
#include <WiFi.h>
//para el protocolo MQTT de IoT
#include <PubSubClient.h>
//para manejar JSON
#include <ArduinoJson.hpp>
#include <ArduinoJson.h>

StaticJsonDocument<300> doc; //300 bytes

int pinRed   = 23;
int pinGreen = 22;
int pinBlue  = 21;
int frecuencia = 5000;
int resolucion = 8;

int r, g, b;

//Variables para conexiones WiFi
const char* ssid        = "FAMILIA_HERNANDEZ";
const char* password    = "1001446317";

//URL del Broker MQTT
const char* mqtt_server  = "168.176.136.61";
const int   mqttPort     = 1883;
const char* mqttUser     = " ";
const char* mqttPassword = " ";
const char* topico       = "MauricioRGB";

WiFiClient    espCliente;
PubSubClient  mqttCliente(espCliente);

// ─── Prototipos ───────────────────────────────────────────
void conectarToWiFi();
void setupMQTT();
void reconnect();
void callback(char* topic, byte* payload, unsigned int length);
void configurarPines();
void establecerColor(int R, int G, int B);
void DeserializeObject(byte* payload);

// ─── Setup ────────────────────────────────────────────────
void setup() {
  Serial.begin(115200);
  configurarPines();
  conectarToWiFi();
  setupMQTT();
}

// ─── Loop ─────────────────────────────────────────────────
void loop() {
  if (!mqttCliente.connected())
    reconnect();
  mqttCliente.loop();

  establecerColor(r, g, b);
}

// ─── WiFi ─────────────────────────────────────────────────
void conectarToWiFi() {
  delay(10);
  Serial.println();
  Serial.print("Conectando a...");
  Serial.println(ssid);
  WiFi.begin(ssid, password);

  while (WiFi.status() != WL_CONNECTED) {
    delay(500);
    Serial.print(".");
  }

  Serial.println("");
  Serial.println("WiFi conectado");
  Serial.print("IP address: ");
  Serial.println(WiFi.localIP());
}

// ─── MQTT setup ───────────────────────────────────────────
void setupMQTT() {
  mqttCliente.setServer(mqtt_server, mqttPort);
  mqttCliente.setCallback(callback);
}

// ─── Reconexión MQTT ──────────────────────────────────────
void reconnect() {
  Serial.println("Conectando a Broker MQTT...");

  while (!mqttCliente.connected()) {
    Serial.println("Reconectando al Broker MQTT...");

    String clientId = "ESP32Client-";
    clientId += String(random(0xffff), HEX);

    if (mqttCliente.connect(clientId.c_str())) {
      Serial.println("Conectado");
      mqttCliente.subscribe(topico);
    }
  }
}

// ─── Callback MQTT ────────────────────────────────────────
void callback(char* topic, byte* payload, unsigned int length) {
  Serial.print("Callback - ");
  Serial.print("Message:");

  for (int i = 0; i < length; i++) {
    Serial.print((char)payload[i]);
  }
  Serial.println();

  DeserializeObject(payload);
}

// ─── Pines PWM  (API nueva core v3.x) ────────────────────
void configurarPines() {
  ledcAttach(pinRed,   frecuencia, resolucion);
  ledcAttach(pinGreen, frecuencia, resolucion);
  ledcAttach(pinBlue,  frecuencia, resolucion);
}

// ─── Escribir color ───────────────────────────────────────
void establecerColor(int R, int G, int B) {
  ledcWrite(pinRed,   R);
  ledcWrite(pinGreen, G);
  ledcWrite(pinBlue,  B);
}

// ─── Deserializar JSON ────────────────────────────────────
void DeserializeObject(byte* payload) {
  deserializeJson(doc, payload);

  r = doc["r"];
  g = doc["g"];
  b = doc["b"];
}