package com.curso_simulaciones.mitrigesimasextaapp.datos;

/**
 * Almacenamiento temporal en memoria (estado compartido entre actividades).
 * Todas las variables son estáticas para acceso global sin instanciación.
 */
public class AlmacenDatosRAM {

    // ── Resolución de pantalla ────────────────────────────────────────────────
    public static int ancho;
    public static int alto;
    public static int dimensionReferencia;
    public static int tamanoLetraResolucionIncluida;

    // ── Estado Bluetooth ──────────────────────────────────────────────────────
    public static String conexion_bluetooth = "NO CONECTADO";

    /** Dirección MAC del dispositivo Bluetooth seleccionado por el usuario. */
    public static String direccion = "NO CONECTADO";

    /** Rol elegido por el usuario: "SOY EL CLIENTE" o "SOY EL SERVIDOR". */
    public static String rol = "";

    // ── Componente de sensor seleccionado ─────────────────────────────────────
    /** Componente de aceleración activo: 1=ax, 2=ay, 3=az, 4=|a| */
    public static int componente_aceleracion = 4;

    /** Componente de campo magnético activo: 1=bx, 2=by, 3=bz, 4=|b| */
    public static int componente_gaussimetro = 4;
}