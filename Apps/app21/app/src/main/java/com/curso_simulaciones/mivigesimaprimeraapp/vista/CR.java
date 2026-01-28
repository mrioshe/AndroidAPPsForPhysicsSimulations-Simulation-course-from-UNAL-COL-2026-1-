package com.curso_simulaciones.mivigesimaprimeraapp.vista;

/**
 * Clase de utilidades para convertir porcentajes a píxeles
 * y viceversa en la Pizarra.
 */
public class CR {

    public static float anchoPizarra = 0f;
    public static float altoPizarra = 0f;

    public CR() {
    }

    public static float pcApxX(float pcX) {
        return pcX * anchoPizarra / 100f;
    }

    public static float pcApxY(float pcY) {
        return pcY * altoPizarra / 100f;
    }

    public static float pcApxL(float pcL) {
        if (anchoPizarra > altoPizarra) {
            return pcL * altoPizarra / 100f;
        } else {
            return pcL * anchoPizarra / 100f;
        }
    }

    public static float pxXApc(float pxX) {
        return pxX * 100f / anchoPizarra;
    }

    public static float pxYApc(float pxY) {
        return pxY * 100f / altoPizarra;
    }

    public static float pxApcL(float pxL) {
        if (anchoPizarra > altoPizarra) {
            return pxL * 100f / altoPizarra;
        } else {
            return pxL * 100f / anchoPizarra;
        }
    }
}