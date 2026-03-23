package com.curso_simulaciones.micuadragesimasegundaapp.datos;

/**
 * Almacenamiento temporal en memoria compartido entre todas las actividades.
 * Guarda credenciales MQTT, estado de conexión y configuración de sensores.
 */
public class AlmacenDatosRAM {

    // ── Resolución de pantalla ────────────────────────────────────────────────
    public static int ancho;
    public static int alto;
    public static int dimensionReferencia;
    public static int tamanoLetraResolucionIncluida;

    // ── Configuración MQTT ────────────────────────────────────────────────────
    public static String MQTTHOST  = "";
    public static String USERNAME  = "";
    public static String PASSWORD  = "";
    public static String topicStr  = "";

    // ── Estado de la conexión IoT ─────────────────────────────────────────────
    public static String  conectado_PubSub = " ";
    public static boolean conectado        = false;

    // ── Componentes de sensor activos ─────────────────────────────────────────
    /** 1=ax, 2=ay, 3=az, 4=|a| */
    public static int componente_aceleracion  = 4;
    /** 1=bx, 2=by, 3=bz, 4=|b| */
    public static int componente_gaussimetro  = 4;
}