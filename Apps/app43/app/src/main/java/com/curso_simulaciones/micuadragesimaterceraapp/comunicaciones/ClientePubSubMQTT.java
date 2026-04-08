package com.curso_simulaciones.micuadragesimaterceraapp.comunicaciones;

import static android.content.ContentValues.TAG;

import android.app.Activity;
import android.util.Log;
import android.widget.Toast;

import com.curso_simulaciones.micuadragesimaterceraapp.datos.AlmacenDatosRAM;

import org.eclipse.paho.client.mqttv3.IMqttActionListener;
import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.IMqttToken;
import org.eclipse.paho.client.mqttv3.MqttAsyncClient;
import org.eclipse.paho.client.mqttv3.MqttCallback;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;

public class ClientePubSubMQTT implements MqttCallback, IMqttActionListener {

    private final Activity actividad;
    private static String MQTTHOST;
    private static String USERNAME;
    private static String PASSWORD;
    private String topicStr;
    private MqttAsyncClient client;
    private MqttConnectOptions options;
    private String datoString;

    public ClientePubSubMQTT(Activity actividad){
        this.actividad = actividad;
        MQTTHOST = AlmacenDatosRAM.MQTTHOST;
        USERNAME = AlmacenDatosRAM.USERNAME;
        PASSWORD = AlmacenDatosRAM.PASSWORD;
        topicStr = AlmacenDatosRAM.topicStr;
    }

    // 1) Conectar con validación previa
    public void conectar() {
        // Validar que la configuración no esté vacía
        if (MQTTHOST == null || MQTTHOST.trim().isEmpty()) {
            AlmacenDatosRAM.conectado_PubSub = "ERROR: No has configurado el BROKER. Ve a AJUSTES.";
            AlmacenDatosRAM.conectado = false;
            mostrarToast(AlmacenDatosRAM.conectado_PubSub);
            return;
        }
        if (topicStr == null || topicStr.trim().isEmpty()) {
            AlmacenDatosRAM.conectado_PubSub = "ERROR: No has configurado el TÓPICO. Ve a AJUSTES.";
            AlmacenDatosRAM.conectado = false;
            mostrarToast(AlmacenDatosRAM.conectado_PubSub);
            return;
        }

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
            AlmacenDatosRAM.conectado_PubSub = "Falla conexión con el broker: " + e.getMessage();
            AlmacenDatosRAM.conectado = false;
            e.printStackTrace();
            mostrarToast(AlmacenDatosRAM.conectado_PubSub);
        } catch (Exception e) {
            AlmacenDatosRAM.conectado_PubSub = "Error inesperado: " + e.getMessage();
            AlmacenDatosRAM.conectado = false;
            e.printStackTrace();
            mostrarToast(AlmacenDatosRAM.conectado_PubSub);
        }
    }

    private void mostrarToast(String mensaje) {
        actividad.runOnUiThread(() -> Toast.makeText(actividad, mensaje, Toast.LENGTH_LONG).show());
    }

    @Override
    public void onSuccess(IMqttToken asyncActionToken) {
        try {
            client.subscribe(topicStr, 0);
            AlmacenDatosRAM.conectado_PubSub = "Suscripción al tópico exitosa";
            AlmacenDatosRAM.conectado = true;
            actividad.runOnUiThread(() -> Toast.makeText(actividad, "Conectado al broker", Toast.LENGTH_SHORT).show());
        } catch (MqttException e) {
            AlmacenDatosRAM.conectado_PubSub = "Falla la suscripción al tópico...";
            AlmacenDatosRAM.conectado = false;
            e.printStackTrace();
        }
    }

    @Override
    public void onFailure(IMqttToken asyncActionToken, Throwable exception) {
        Log.d(TAG, "Falla conexión", exception);
        AlmacenDatosRAM.conectado_PubSub = "Falla conexión con el broker: " + exception.getMessage();
        AlmacenDatosRAM.conectado = false;
        mostrarToast(AlmacenDatosRAM.conectado_PubSub);
    }

    @Override
    public void connectionLost(Throwable cause) {
        Log.d(TAG, "Conexión perdida", cause);
        AlmacenDatosRAM.conectado = false;
        AlmacenDatosRAM.conectado_PubSub = "Conexión perdida. Reconectando...";
        actividad.runOnUiThread(() -> Toast.makeText(actividad, "Conexión MQTT perdida", Toast.LENGTH_SHORT).show());
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

    public String leerString() {
        return datoString;
    }

    public void setEnviarMensajes(byte[] datoBytesEnviar) {
        if (!AlmacenDatosRAM.conectado) {
            AlmacenDatosRAM.conectado_PubSub = "No conectado. No se pueden enviar datos.";
            return;
        }
        try {
            AlmacenDatosRAM.conectado_PubSub = "Enviando datos...";
            MqttMessage message = new MqttMessage(datoBytesEnviar);
            message.setQos(1);
            message.setRetained(false);
            client.publish(topicStr, message);
        } catch (MqttException e) {
            e.printStackTrace();
            AlmacenDatosRAM.conectado_PubSub = "Error al enviar: " + e.getMessage();
        }
    }

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