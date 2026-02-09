package com.curso_simulaciones.mivigesimaquintaapp.vista;

import com.curso_simulaciones.mivigesimaquintaapp.datos.AlmacenDatosRAM;

/**
 * Clase para Conversión de Resolución
 * Convierte porcentajes a píxeles según las dimensiones de la pantalla
 */
public class CR {

    // Dimensiones de la pizarra
    public static float anchoPizarra;
    public static float altoPizarra;

    /**
     * Convierte porcentaje del ancho a píxeles en X
     * @param porcentaje Porcentaje del ancho (0-100)
     * @return Coordenada X en píxeles
     */
    public static float pcApxX(float porcentaje) {
        return (porcentaje / 100f) * anchoPizarra;
    }

    /**
     * Convierte porcentaje del alto a píxeles en Y
     * @param porcentaje Porcentaje del alto (0-100)
     * @return Coordenada Y en píxeles
     */
    public static float pcApxY(float porcentaje) {
        return (porcentaje / 100f) * altoPizarra;
    }

    /**
     * Convierte porcentaje de la dimensión menor a píxeles
     * Usado para tamaños que deben mantener proporción
     * @param porcentaje Porcentaje de la dimensión menor (0-100)
     * @return Longitud en píxeles
     */
    public static float pcApxL(float porcentaje) {
        float menor = Math.min(anchoPizarra, altoPizarra);
        return (porcentaje / 100f) * menor;
    }

    /**
     * Convierte metros a píxeles usando el factor de escala
     * Factor: 2 metros = alto de pantalla completo
     * @param metros Distancia en metros
     * @return Distancia en píxeles
     */
    public static float metrosAPx(float metros) {
        float factorConversion_metroApixel = pcApxY(100f) / 2;
        return metros * factorConversion_metroApixel;
    }

    /**
     * Convierte píxeles a metros usando el factor de escala
     * Factor: 2 metros = alto de pantalla completo
     * @param pixeles Distancia en píxeles
     * @return Distancia en metros
     */
    public static float pxAMetros(float pixeles) {
        float factorConversion_pixelAmetro = 2 / pcApxY(100f);
        return pixeles * factorConversion_pixelAmetro;
    }
}