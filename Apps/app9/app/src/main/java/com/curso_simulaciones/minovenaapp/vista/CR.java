package com.curso_simulaciones.minovenaapp.vista;

public class CR {

    public static float anchoPizarra;
    public static float altoPizarra;

    public CR(){

    }

    /**
     * Método para convertir porcentaje de posición en X a pixeles.
     */
    public static float pcApxX(float pcX) {
        float pxX = pcX * anchoPizarra / 100f;
        return pxX;
    }

    /**
     * Método para convertir porcentaje de posición en Y a pixeles.
     */
    public static float pcApxY(float pcY) {
        float pxY = pcY * altoPizarra / 100f;
        return pxY;
    }

    /**
     * Dada una longitud pcL en porcentaje referido al menor entre
     * el ancho y el alto de Pizarra la convierte a pixeles.
     */
    public static float pcApxL(float pcL) {
        float pxL = 0;

        if (anchoPizarra > altoPizarra) {
            pxL = pcL * altoPizarra / 100f;
        } else {
            pxL = pcL * anchoPizarra / 100f;
        }

        return pxL;
    }
}