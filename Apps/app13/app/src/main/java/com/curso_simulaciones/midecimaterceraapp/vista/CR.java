package com.curso_simulaciones.midecimaterceraapp.vista;

/**
 * Clase de utilidades para convertir porcentajes <-> pixeles.
 */
public class CR {

    public static float anchoPizarra;
    public static float altoPizarra;

    public CR() {}

    public static float pcApxX(float pcX) {
        return pcX * anchoPizarra / 100f;
    }

    public static float pcApxY(float pcY) {
        return pcY * altoPizarra / 100f;
    }

    public static float pcApxL(float pcL) {
        float pxL;
        if (anchoPizarra > altoPizarra) {
            pxL = pcL * altoPizarra / 100f;
        } else {
            pxL = pcL * anchoPizarra / 100f;
        }
        return pxL;
    }

    public static float pxXApc(float pxX) {
        return pxX * 100f / anchoPizarra;
    }

    public static float pxYApc(float pxY) {
        return pxY * 100f / altoPizarra;
    }

    public static float pxApcL(float pxL) {
        float pcL;
        if (anchoPizarra > altoPizarra) {
            pcL = pxL * 100f / altoPizarra;
        } else {
            pcL = pxL * 100f / anchoPizarra;
        }
        return pcL;
    }
}
