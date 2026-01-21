package com.curso_simulaciones.myapplication.vista; // reemplaza por el paquete real de tu proyecto

/**
 * Utilidad para convertir porcentajes relativos a la Pizarra
 * en píxeles y viceversa.
 */
public class CR {

    public static float anchoPizarra;
    public static float altoPizarra;

    public CR() {
        // constructor vacío
    }

    /**
     * Convierte porcentaje en X a píxeles.
     * @param pcX porcentaje en X (0..100)
     * @return píxeles en X
     */
    public static float pcApxX(float pcX) {
        if (anchoPizarra == 0f) return 0f;
        return pcX * anchoPizarra / 100f;
    }

    /**
     * Convierte porcentaje en Y a píxeles.
     * @param pcY porcentaje en Y (0..100)
     * @return píxeles en Y
     */
    public static float pcApxY(float pcY) {
        if (altoPizarra == 0f) return 0f;
        return pcY * altoPizarra / 100f;
    }

    /**
     * Convierte una longitud dada en porcentaje (referida al menor
     * entre ancho y alto) a píxeles.
     * @param pcL porcentaje de longitud
     * @return longitud en píxeles
     */
    public static float pcApxL(float pcL) {
        if (anchoPizarra == 0f || altoPizarra == 0f) return 0f;
        if (anchoPizarra > altoPizarra) {
            return pcL * altoPizarra / 100f;
        } else {
            return pcL * anchoPizarra / 100f;
        }
    }

    /**
     * Convierte píxeles en X a porcentaje relativo al ancho de la pizarra.
     * @param pxX píxeles en X
     * @return porcentaje en X (0..100)
     */
    public static float pxXApc(float pxX) {
        if (anchoPizarra == 0f) return 0f;
        return pxX * 100f / anchoPizarra;
    }

    /**
     * Convierte píxeles en Y a porcentaje relativo al alto de la pizarra.
     * @param pxY píxeles en Y
     * @return porcentaje en Y (0..100)
     */
    public static float pxYApc(float pxY) {
        if (altoPizarra == 0f) return 0f;
        return pxY * 100f / altoPizarra;
    }

    /**
     * Convierte una longitud en píxeles a porcentaje referido
     * al menor entre ancho y alto de la pizarra.
     * @param pxL longitud en píxeles
     * @return porcentaje de longitud
     */
    public static float pxApcL(float pxL) {
        if (anchoPizarra == 0f || altoPizarra == 0f) return 0f;
        if (anchoPizarra > altoPizarra) {
            return pxL * 100f / altoPizarra;
        } else {
            return pxL * 100f / anchoPizarra;
        }
    }
}