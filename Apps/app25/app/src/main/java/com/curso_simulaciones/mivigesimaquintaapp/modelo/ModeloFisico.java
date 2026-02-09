package com.curso_simulaciones.mivigesimaquintaapp.modelo;

import com.curso_simulaciones.mivigesimaquintaapp.datos.AlmacenDatosRAM;
import com.curso_simulaciones.mivigesimaquintaapp.vista.CR;

/**
 * Modelo físico del sistema de poleas con tres masas
 * CONFIGURACIÓN A: Cuerdas independientes para m2 y m3
 * CON MOVIMIENTO RELATIVO entre m2 y m3 respecto a la polea P
 */
public class ModeloFisico {

    // Gravedad
    private float g = 9.8f; // en m/s²

    private float desplazamiento_m1_en_pixeles, desplazamiento_m2_en_pixeles;
    private float desplazamiento_m3_en_pixeles, desplazamiento_P_en_pixeles;
    private float factorConversion_metroApixel, factorConversion_pixelAmetro;
    private float y1_en_pixeles, y2_en_pixeles, y3_en_pixeles, yP_en_pixeles;

    public ModeloFisico() {

    }

    /**
     * Dados los valores de las masas calcula posiciones,
     * aceleraciones, tensiones, desplazamientos y velocidades
     *
     * CONFIGURACIÓN A: Las cuerdas de m2 y m3 son independientes
     * Ambas cuelgan desde la polea móvil P
     *
     * MOVIMIENTO RELATIVO: Si m2 ≠ m3, hay movimiento diferencial
     * - Si m3 > m2: m3 baja más rápido que P, m2 sube más lento que P
     * - Si m2 > m3: m2 baja más rápido que P, m3 sube más lento que P
     */
    public void setCalculos(float tiempo, float m1, float m2, float m3) {

        factorConversion();

        float suma_masas = m1 + m2 + m3;

        // Aceleración de la polea móvil P (Ecuación del documento LaTeX)
        // aP = g * (m2 + m3 - m1) / (m1 + m2 + m3)
        float aP = g * (m2 + m3 - m1) / suma_masas;

        // MOVIMIENTO RELATIVO entre m2 y m3 respecto a la polea P
        // En el marco de referencia de P, hay un mini-sistema de polea con m2 y m3
        // Aceleración relativa: a_rel = g * (m3 - m2) / (m2 + m3)
        float a_rel = 0;
        if ((m2 + m3) > 0) {
            a_rel = g * (m3 - m2) / (m2 + m3);
        }

        // Aceleraciones absolutas de las masas
        float a1 = -aP;           // m1 se mueve opuesto a P
        float a2 = aP - a_rel;    // m2 tiene movimiento relativo respecto a P
        float a3 = aP + a_rel;    // m3 tiene movimiento relativo respecto a P

        // Tensiones según ecuación del documento
        // Estas son las tensiones en las cuerdas principales
        float T = (2.0f * m1 * (m2 + m3) / suma_masas) * g;

        // Tensiones en las cuerdas de m2 y m3 ajustadas por el movimiento relativo
        // T2 = m2 * (g - a2)
        // T3 = m3 * (g - a3)
        float T2 = m2 * (g - a2);
        float T3 = m3 * (g - a3);

        // Velocidades (v = v0 + a*t, con v0 = 0)
        float v1 = a1 * tiempo;
        float v2 = a2 * tiempo;
        float v3 = a3 * tiempo;
        float vP = aP * tiempo;

        // Desplazamiento de las masas en metros (s = 0.5 * a * t²)
        float desplazamiento_m1_en_metros = 0.5f * a1 * tiempo * tiempo;
        float desplazamiento_m2_en_metros = 0.5f * a2 * tiempo * tiempo;
        float desplazamiento_m3_en_metros = 0.5f * a3 * tiempo * tiempo;
        float desplazamiento_P_en_metros = 0.5f * aP * tiempo * tiempo;

        // Almacenar valores para que pizarra los reporte
        AlmacenDatosRAM.desplazamiento_m1_en_metros = desplazamiento_m1_en_metros;
        AlmacenDatosRAM.desplazamiento_m2_en_metros = desplazamiento_m2_en_metros;
        AlmacenDatosRAM.desplazamiento_m3_en_metros = desplazamiento_m3_en_metros;
        AlmacenDatosRAM.desplazamiento_P_en_metros = desplazamiento_P_en_metros;

        // Convertir m a píxeles
        desplazamiento_m1_en_pixeles = factorConversion_metroApixel * desplazamiento_m1_en_metros;
        desplazamiento_m2_en_pixeles = factorConversion_metroApixel * desplazamiento_m2_en_metros;
        desplazamiento_m3_en_pixeles = factorConversion_metroApixel * desplazamiento_m3_en_metros;
        desplazamiento_P_en_pixeles = factorConversion_metroApixel * desplazamiento_P_en_metros;

        AlmacenDatosRAM.desplazamiento_m1_en_pixeles = desplazamiento_m1_en_pixeles;
        AlmacenDatosRAM.desplazamiento_m2_en_pixeles = desplazamiento_m2_en_pixeles;
        AlmacenDatosRAM.desplazamiento_m3_en_pixeles = desplazamiento_m3_en_pixeles;
        AlmacenDatosRAM.desplazamiento_P_en_pixeles = desplazamiento_P_en_pixeles;

        // Posiciones iniciales en píxeles
        float yi1_en_pixeles = AlmacenDatosRAM.yi1_en_pixeles;
        float yi2_en_pixeles = AlmacenDatosRAM.yi2_en_pixeles;
        float yi3_en_pixeles = AlmacenDatosRAM.yi3_en_pixeles;
        float yiP_en_pixeles = AlmacenDatosRAM.yiP_en_pixeles;

        // Posiciones de las masas en píxeles
        y1_en_pixeles = yi1_en_pixeles + desplazamiento_m1_en_pixeles;
        y2_en_pixeles = yi2_en_pixeles + desplazamiento_m2_en_pixeles;
        y3_en_pixeles = yi3_en_pixeles + desplazamiento_m3_en_pixeles;
        yP_en_pixeles = yiP_en_pixeles + desplazamiento_P_en_pixeles;

        AlmacenDatosRAM.y1_en_pixeles = y1_en_pixeles;
        AlmacenDatosRAM.y2_en_pixeles = y2_en_pixeles;
        AlmacenDatosRAM.y3_en_pixeles = y3_en_pixeles;
        AlmacenDatosRAM.yP_en_pixeles = yP_en_pixeles;

        // Cálculo de ángulos de rotación de las poleas

        // La polea azul izquierda rota por el desplazamiento de m1
        float teta_azul_izq = (float) (Math.toDegrees(desplazamiento_m1_en_pixeles / AlmacenDatosRAM.radio));

        // La polea azul derecha rota por el desplazamiento de P
        float teta_azul_der = (float) (Math.toDegrees(desplazamiento_P_en_pixeles / AlmacenDatosRAM.radio));

        // ROTACIÓN DE LA POLEA VERDE P
        // La polea P rota debido al movimiento relativo entre m2 y m3
        // Si m3 > m2: m3 baja más y hace rotar la polea en un sentido
        // Si m2 > m3: m2 baja más y hace rotar la polea en el otro sentido

        // Desplazamiento relativo de m2 respecto a P
        float desplaz_relativo_m2 = desplazamiento_m2_en_pixeles - desplazamiento_P_en_pixeles;

        // Desplazamiento relativo de m3 respecto a P
        float desplaz_relativo_m3 = desplazamiento_m3_en_pixeles - desplazamiento_P_en_pixeles;

        // La rotación de la polea P se debe a la diferencia entre los movimientos de m2 y m3
        // Si m3 baja más que m2, la polea rota en un sentido
        // Usamos la diferencia de desplazamientos relativos
        float diferencia_desplaz = desplaz_relativo_m3 - desplaz_relativo_m2;

        float radio_verde = AlmacenDatosRAM.radio_verde;
        float teta_P = (float) (Math.toDegrees(diferencia_desplaz / (2.0f * radio_verde)));

        // Convertir posiciones a metros (referenciadas desde el origen)
        AlmacenDatosRAM.y1_en_metros = (y1_en_pixeles - AlmacenDatosRAM.origenY_en_pixeles) * factorConversion_pixelAmetro;
        AlmacenDatosRAM.y2_en_metros = (y2_en_pixeles - AlmacenDatosRAM.origenY_en_pixeles) * factorConversion_pixelAmetro;
        AlmacenDatosRAM.y3_en_metros = (y3_en_pixeles - AlmacenDatosRAM.origenY_en_pixeles) * factorConversion_pixelAmetro;
        AlmacenDatosRAM.yP_en_metros = (yP_en_pixeles - AlmacenDatosRAM.origenY_en_pixeles) * factorConversion_pixelAmetro;

        // Enviar resultados a AlmacenDatosRAM
        AlmacenDatosRAM.a1 = a1;
        AlmacenDatosRAM.a2 = a2;
        AlmacenDatosRAM.a3 = a3;
        AlmacenDatosRAM.aP = aP;

        AlmacenDatosRAM.v1 = v1;
        AlmacenDatosRAM.v2 = v2;
        AlmacenDatosRAM.v3 = v3;
        AlmacenDatosRAM.vP = vP;

        AlmacenDatosRAM.teta_azul_izq = teta_azul_izq;
        AlmacenDatosRAM.teta_azul_der = teta_azul_der;
        AlmacenDatosRAM.teta_P = teta_P;

        AlmacenDatosRAM.T = T;
        AlmacenDatosRAM.T2 = T2;
        AlmacenDatosRAM.T3 = T3;

        AlmacenDatosRAM.tiempo = tiempo;
    }

    private void factorConversion() {
        /*
        Para dar una equivalencia de píxeles
        en metros se asumirá que 2 m equivalen
        al ALTO de la pantalla (en posición
        LANDSCAPE) en píxeles. Con base en esto
        el factor de conversión de metros a píxeles es:

        factorConversion_metroApixel = (ALTO en píxeles / 2 metros)
        factorConversion_pixelAmetro = (2 metros / ALTO en píxeles)
        */

        factorConversion_metroApixel = CR.pcApxY(100f) / 2;
        factorConversion_pixelAmetro = 2 / CR.pcApxY(100f);
    }
}