package com.curso_simulaciones.mivigesimaquintaapp.datos;

/**
 * Clase para almacenar datos globales de la aplicación
 */
public class AlmacenDatosRAM {

    public static float ancho_pantalla, alto_pantalla;
    public static int tamanoLetraResolucionIncluida;

    // Masas en kg
    public static float m1, m2, m3;

    // Radio de las poleas en píxeles
    public static float radio;
    public static float radio_verde;  // AGREGADO: radio de la polea verde

    public static float tiempo;

    // Aceleraciones de m1, m2, m3 y P en m/s²
    public static float a1, a2, a3, aP;

    // Tensiones en la cuerda en N
    public static float T = 0, T2 = 0, T3 = 0;

    // Desplazamiento angular (radianes) de rotación de las poleas
    public static float teta_azul_izq, teta_azul_der, teta_P;

    // Origen
    public static float origenY_en_pixeles;
    public static float origenY_en_metros;

    // Posiciones
    public static float x1_en_pixeles, x2_en_pixeles, x3_en_pixeles;
    public static float xP_en_pixeles;
    public static float y1_en_pixeles, y2_en_pixeles, y3_en_pixeles, yP_en_pixeles;
    public static float yi1_en_metros, yi2_en_metros, yi3_en_metros, yiP_en_metros;
    public static float y1_en_metros, y2_en_metros, y3_en_metros, yP_en_metros;

    // Posiciones en Y iniciales
    public static float yi1_en_pixeles, yi2_en_pixeles, yi3_en_pixeles, yiP_en_pixeles;

    public static float desplazamiento_m1_en_metros, desplazamiento_m2_en_metros;
    public static float desplazamiento_m3_en_metros, desplazamiento_P_en_metros;
    public static float desplazamiento_m1_en_pixeles, desplazamiento_m2_en_pixeles;
    public static float desplazamiento_m3_en_pixeles, desplazamiento_P_en_pixeles;

    // Velocidades en m/s
    public static float v1, v2, v3, vP;
}