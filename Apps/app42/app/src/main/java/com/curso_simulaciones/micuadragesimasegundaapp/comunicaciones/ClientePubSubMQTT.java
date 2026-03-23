package com.curso_simulaciones.micuadragesimasegundaapp.comunicaciones;

/*
 * ==========================================================
 * NOTA IMPORTANTE
 * ==========================================================
 * Se usa MqttAsyncClient (org.eclipse.paho.client.mqttv3)
 * en lugar de MqttAndroidClient, porque a partir de Android 12
 * el uso de AlarmReceiver en la librería android.service
 * produce errores de seguridad.
 *
 * MqttAsyncClient funciona en Android 12, 13 y posteriores,
 * no requiere BroadcastReceiver ni permisos especiales.
 * ==========================================================
 */

import android.app.Activity;
import android.util.Log;

import com.curso_simulaciones.micuadragesimasegundaapp.datos.AlmacenDatosRAM;

import org.eclipse.paho.client.mqttv3.IMqttActionListener;
import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.IMqttToken;
import org.eclipse.paho.client.mqttv3.MqttAsyncClient;
import org.eclipse.paho.client.mqttv3.MqttCallback;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;

/**
 * Cliente MQTT genérico PUB/SUB.
 * Gestiona conexión, publicación y suscripción al broker MQTT.
 *
 * Broker por defecto del curso:
 *   URL  : tcp://45.56.74.248:1883
 *   USER : fisica
 *   PASS : iotfisica
 *   QoS  : 1
 */
public class ClientePubSubMQTT implements MqttCallback, IMqttActionListener {

    private static final String TAG = "ClientePubSubMQTT";

    private final Activity actividad;

    private static String MQTTHOST;
    private static String USERNAME;
    private static String PASSWORD;
    private String topicStr;

    private MqttAsyncClient    client;
    private MqttConnectOptions options;

    // Último mensaje recibido (SUB)
    private String datoString;

    public ClientePubSubMQTT(Activity actividad) {
        this.actividad = actividad;
        MQTTHOST = AlmacenDatosRAM.MQTTHOST;
        USERNAME = AlmacenDatosRAM.USERNAME;
        PASSWORD = AlmacenDatosRAM.PASSWORD;
        topicStr = AlmacenDatosRAM.topicStr;
    }

    // ── 1) Conectar ───────────────────────────────────────────────────────────
    public void conectar() {
        try {
            String clientId = MqttAsyncClient.generateClientId();
            client = new MqttAsyncClient(MQTTHOST, clientId, new MemoryPersistence());
            client.setCallback(this);

            options = new MqttConnectOptions();
            options.setUserName(USERNAME);
            options.setPassword(PASSWORD.toCharArray());
            options.setCleanSession(true);
            options.setAutomaticReconnect(true);
            options.setKeepAliveInterval(60);

            client.connect(options, null, this);
            AlmacenDatosRAM.conectado_PubSub = "Conectando con el broker...";

        } catch (MqttException e) {
            AlmacenDatosRAM.conectado_PubSub = "Falla conexión con el broker...";
            AlmacenDatosRAM.conectado = false;
            e.printStackTrace();
        }
    }

    // Éxito de connect() → suscribir al topic
    @Override
    public void onSuccess(IMqttToken asyncActionToken) {
        try {
            client.subscribe(topicStr, 0);
            AlmacenDatosRAM.conectado_PubSub = "Suscripción al tópico exitosa";
            AlmacenDatosRAM.conectado = true;
        } catch (MqttException e) {
            AlmacenDatosRAM.conectado_PubSub = "Falla la suscripción al tópico...";
            AlmacenDatosRAM.conectado = false;
            e.printStackTrace();
        }
    }

    // Falla de connect()
    @Override
    public void onFailure(IMqttToken asyncActionToken, Throwable exception) {
        Log.d(TAG, "Falla conexión", exception);
        AlmacenDatosRAM.conectado_PubSub = "Falla conexión con el broker...";
        AlmacenDatosRAM.conectado = false;
    }

    // ── Callbacks MQTT ────────────────────────────────────────────────────────
    @Override
    public void connectionLost(Throwable cause) {
        Log.d(TAG, "Conexión perdida", cause);
        AlmacenDatosRAM.conectado = false;
    }

    @Override
    public void messageArrived(String topic, MqttMessage mqttMessage) {
        if (AlmacenDatosRAM.conectado) {
            datoString = new String(mqttMessage.getPayload());
            AlmacenDatosRAM.conectado_PubSub = "Recibiendo datos...";
        }
    }

    @Override
    public void deliveryComplete(IMqttDeliveryToken token) {
        Log.d(TAG, "Mensaje entregado");
    }

    // ── 2) Leer mensaje recibido ──────────────────────────────────────────────
    public String leerString() { return datoString; }

    // ── 3) Publicar mensaje ───────────────────────────────────────────────────
    public void setEnviarMensajes(byte[] datoBytesEnviar) {
        if (!AlmacenDatosRAM.conectado) return;
        try {
            AlmacenDatosRAM.conectado_PubSub = "Enviando datos...";
            MqttMessage message = new MqttMessage(datoBytesEnviar);
            message.setQos(1);
            message.setRetained(false);
            client.publish(topicStr, message);
        } catch (MqttException e) {
            e.printStackTrace();
        }
    }

    // ── 4) Desconectar ────────────────────────────────────────────────────────
    public void desconectar() {
        try {
            if (client != null && client.isConnected()) {
                client.unsubscribe(topicStr);
                client.disconnect();
            }
            AlmacenDatosRAM.conectado = false;
        } catch (MqttException e) {
            e.printStackTrace();
        }
    }
}