#include <WiFi.h>
#include <PubSubClient.h>
#include <Wire.h>
#include <BH1750.h>
#include <ArduinoJson.h>

// ----- Sensor -----
BH1750 lightMeter(0x23);   // Si tu pin ADD está a VCC, cambia a 0x5C

StaticJsonDocument<300> doc;

float valor = 0.0;
unsigned long tiempo = 0;
const unsigned long periodo = 200;   // mejor que 100 ms para BH1750

const float minimo = 0;
const float maximo = 100;

// ----- WiFi -----
const char* ssid     = "Comunidad_UNMED";
const char* password = "wifi_med_213";

// ----- MQTT -----
const char* mqtt_server  = "45.56.74.248";
const int   mqttPort     = 1883;
const char* mqttUser     = "fisica";
const char* mqttPassword = "iotfisica";

// Te recomiendo usar un tópico para luz, no el mismo de RGB.
// Si vas a reutilizar el mismo por prueba, déjalo igual.
const char* topico       = "iot/simulaciones/lux/equipoMM";
// Ejemplo mejor: "iot/simulaciones/lux/equipoMM"

WiFiClient espCliente;
PubSubClient mqttCliente(espCliente);

// ----- Funciones -----
void conectarToWiFi();
void setupMQTT();
void reconnect();
void publicarDato();
void configurarI2C();

// ----- Setup -----
void setup() {
  Serial.begin(115200);
  delay(500);

  configurarI2C();

  if (lightMeter.begin(BH1750::CONTINUOUS_HIGH_RES_MODE, 0x23, &Wire)) {
    Serial.println(F("BH1750 inicializado correctamente"));
  } else {
    Serial.println(F("Error inicializando BH1750"));
  }

  conectarToWiFi();
  setupMQTT();
}

// ----- Loop -----
void loop() {
  if (!mqttCliente.connected()) {
    reconnect();
  }
  mqttCliente.loop();

  valor = lightMeter.readLightLevel();
  tiempo = millis();

  publicarDato();

  delay(periodo);
}

// ----- I2C con pines personalizados -----
void configurarI2C() {
  Wire.begin(18, 19, 100000);   // SDA=18, SCL=19, 100 kHz
}

// ----- WiFi -----
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
  Serial.print("IP address: ");
  Serial.println(WiFi.localIP());
}

// ----- MQTT -----
void setupMQTT() {
  mqttCliente.setServer(mqtt_server, mqttPort);
  mqttCliente.setBufferSize(256);
}

void reconnect() {
  while (!mqttCliente.connected()) {
    Serial.print("Conectando a Broker MQTT... ");

    String clientId = "ESP32Client-";
    clientId += String((uint32_t)ESP.getEfuseMac(), HEX);

    if (mqttCliente.connect(clientId.c_str(), mqttUser, mqttPassword)) {
      Serial.println("conectado");
      Serial.print("Publicando en: ");
      Serial.println(topico);
    } else {
      Serial.print("falló, rc=");
      Serial.print(mqttCliente.state());
      Serial.println(" reintentando en 3 s");
      delay(3000);
    }
  }
}

// ----- Publicar JSON -----
void publicarDato() {
  doc.clear();
  doc["maximo"] = maximo;
  doc["minimo"] = minimo;
  doc["unidad"]  = "lx";
  doc["periodo"] = periodo;
  doc["tiempo"]  = tiempo;
  doc["valor"]   = valor;

  char buffer[200];
  size_t n = serializeJson(doc, buffer, sizeof(buffer));

  Serial.println("Enviando mensaje a MQTT:");
  Serial.println(buffer);

  if (mqttCliente.publish(topico, buffer, n)) {
    Serial.println("Envío exitoso");
  } else {
    Serial.println("Error en el envío");
  }

  Serial.println("-------------");
}