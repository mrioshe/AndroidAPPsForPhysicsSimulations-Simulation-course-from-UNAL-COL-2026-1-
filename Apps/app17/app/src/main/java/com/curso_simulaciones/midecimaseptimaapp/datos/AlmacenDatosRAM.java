package com.curso_simulaciones.midecimaseptimaapp.datos;

public class AlmacenDatosRAM {
    // Tamaño de letra calculado por la actividad principal (para compartir)
    public static int tamanoLetraResolucionIncluida = 16;

    // Datos ingresados en ActividadSecundaria_2
    public static String campo1 = "";
    public static String campo2 = "";
    public static String campo3 = "";
    public static String campo4 = "";

    // Indica si ya se ingresaron datos (para habilitar botones)
    public static boolean datosIngresados = false;

    public AlmacenDatosRAM() {
    }
}