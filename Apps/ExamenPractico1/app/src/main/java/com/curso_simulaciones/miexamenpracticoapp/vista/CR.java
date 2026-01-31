package com.curso_simulaciones.miexamenpracticoapp.vista;

/**
 * Clase CR (Conversión Responsiva)
 * Permite convertir entre porcentajes y píxeles para lograr diseño responsivo
 * en diferentes tamaños de pantalla
 */
public class CR {

    public static float anchoPizarra;
    public static float altoPizarra;

    public CR() {
    }

    /**
     * Método para convertir porcentaje de posición en X a píxeles
     * @param pcX Porcentaje de la posición en X
     * @return Posición en píxeles en X
     */
    public static float pcApxX(float pcX) {
        float pxX = pcX * anchoPizarra / 100f;
        return pxX;
    }

    /**
     * Método para convertir porcentaje de posición en Y a píxeles
     * @param pcY Porcentaje de la posición en Y
     * @return Posición en píxeles en Y
     */
    public static float pcApxY(float pcY) {
        float pxY = pcY * altoPizarra / 100f;
        return pxY;
    }

    /**
     * Dada una longitud pcL en porcentaje referido al menor entre el ancho
     * y el alto de Pizarra, la convierte a una longitud en píxeles
     * @param pcL Porcentaje de longitud
     * @return Longitud en píxeles
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

    /**
     * Convierte píxeles de una posición en X a porcentaje
     * @param pxX Posición en píxeles en X
     * @return Porcentaje de la posición en X
     */
    public static float pxXApc(float pxX) {
        float pcX = pxX * 100f / anchoPizarra;
        return pcX;
    }

    /**
     * Convierte píxeles de una posición en Y a porcentaje
     * @param pxY Posición en píxeles en Y
     * @return Porcentaje de la posición en Y
     */
    public static float pxYApc(float pxY) {
        float pcY = pxY * 100f / altoPizarra;
        return pcY;
    }

    /**
     * Dada una longitud pxL en píxeles, la convierte a porcentaje
     * referido al menor entre el ancho y el alto de Pizarra
     * @param pxL Longitud en píxeles
     * @return Porcentaje de longitud
     */
    public static float pxApcL(float pxL) {
        float pcL = 0;

        if (anchoPizarra > altoPizarra) {
            pcL = pxL * 100f / altoPizarra;
        } else {
            pcL = pxL * 100f / anchoPizarra;
        }

        return pcL;
    }
}