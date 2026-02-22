package com.curso_simulaciones.mitrigesimacuartaapp.datos;

public class AlmacenDatosRAM {
    public static int dimensionReferencia, alto, ancho;
    public static int tamanoLetraResolucionIncluida;

    // Variables para el acelerómetro
    public static float aceleracionX = 0.0f;      // ax
    public static float aceleracionY = 0.0f;      // ay
    public static float aceleracionZ = 0.0f;      // az
    public static float aceleracionTotal = 0.0f;  // a (magnitud)

    public static boolean configurar = false;
    public static int periodoMuestreo = 500;  // en ms
    public static float tiempo = 0.0f;
    public static int nDatos = 50;
    public static String path;
}