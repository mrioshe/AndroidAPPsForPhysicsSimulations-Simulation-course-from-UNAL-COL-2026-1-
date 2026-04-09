#include <WiFi.h>
#include <PubSubClient.h>
#include <ArduinoJson.h>

StaticJsonDocument<300> doc;

// Pines RGB
int pinRed   = 23;
int pinGreen = 22;
int pinBlue  = 21;

int frecuencia = 5000;
int resolucion = 8;

int r = 0, g = 0, b = 0;

// WiFi
const char* ssid     = "FAMILIA_HERNANDEZ";
const char* password = "1001446317";

// MQTT
const char* mqtt_server  = "45.56.74.248";
const int   mqttPort     = 1883;
const char* mqttUser     = "fisica";
const char* mqttPassword = "iotfisica";
const char* topico       = "iot/simulaciones/RGB/equipoMM";

WiFiClient espCliente;
PubSubClient mqttCliente(espCliente);

// Prototipos
void conectarToWiFi();
void setupMQTT();
void reconnect();
void callback(char* topic, byte* payload, unsigned int length);
bool configurarPines();
void establecerColor(int R, int G, int B);

// Setup
void setup() {
  Serial.begin(115200);
  delay(1000);

  if (!configurarPines()) {
    Serial.println("Error configurando PWM");
    while (true) delay(1000);
  }

  conectarToWiFi();
  setupMQTT();
}

// Loop
void loop() {
  if (!mqttCliente.connected()) {
    reconnect();
  }
  mqttCliente.loop();

  // Actualiza el color con los últimos valores recibidos
  establecerColor(r, g, b);
}

// WiFi
void conectarToWiFi() {
  Serial.println();
  Serial.print("Conectando a WiFi: ");
  Serial.println(ssid);

  WiFi.begin(ssid, password);

  while (WiFi.status() != WL_CONNECTED) {
    delay(500);
    Serial.print(".");
  }

  Serial.println();
  Serial.println("WiFi conectado");
  Serial.print("IP: ");
  Serial.println(WiFi.localIP());
}

// MQTT
void setupMQTT() {
  mqttCliente.setServer(mqtt_server, mqttPort);
  mqttCliente.setCallback(callback);
  mqttCliente.setBufferSize(256);
}

// Reconexión MQTT
void reconnect() {
  while (!mqttCliente.connected()) {
    Serial.print("Conectando a MQTT... ");

    String clientId = "ESP32Client-";
    clientId += String((uint32_t)ESP.getEfuseMac(), HEX);

    if (mqttCliente.connect(clientId.c_str(), mqttUser, mqttPassword)) {
      Serial.println("conectado");
      mqttCliente.subscribe(topico);
      Serial.print("Suscrito a: ");
      Serial.println(topico);
    } else {
      Serial.print("falló, rc=");
      Serial.print(mqttCliente.state());
      Serial.println(" reintentando en 3 s");
      delay(3000);
    }
  }
}

// Callback MQTT
void callback(char* topic, byte* payload, unsigned int length) {
  Serial.print("Mensaje recibido en [");
  Serial.print(topic);
  Serial.print("]: ");

  for (unsigned int i = 0; i < length; i++) {
    Serial.print((char)payload[i]);
  }
  Serial.println();

  DeserializationError err = deserializeJson(doc, payload, length);
  if (err) {
    Serial.print("Error JSON: ");
    Serial.println(err.c_str());
    return;
  }

  r = doc["r"] | 0;
  g = doc["g"] | 0;
  b = doc["b"] | 0;

  Serial.print("RGB recibido -> R:");
  Serial.print(r);
  Serial.print(" G:");
  Serial.print(g);
  Serial.print(" B:");
  Serial.println(b);
}

// Pines PWM
bool configurarPines() {
  bool ok1 = ledcAttach(pinRed, frecuencia, resolucion);
  bool ok2 = ledcAttach(pinGreen, frecuencia, resolucion);
  bool ok3 = ledcAttach(pinBlue, frecuencia, resolucion);

  Serial.print("PWM Red: ");
  Serial.println(ok1 ? "OK" : "FALLO");
  Serial.print("PWM Green: ");
  Serial.println(ok2 ? "OK" : "FALLO");
  Serial.print("PWM Blue: ");
  Serial.println(ok3 ? "OK" : "FALLO");

  return ok1 && ok2 && ok3;
}

// Escribir color
void establecerColor(int R, int G, int B) {
  R = constrain(R, 0, 255);
  G = constrain(G, 0, 255);
  B = constrain(B, 0, 255);

  ledcWrite(pinRed, R);
  ledcWrite(pinGreen, G);
  ledcWrite(pinBlue, B);
}