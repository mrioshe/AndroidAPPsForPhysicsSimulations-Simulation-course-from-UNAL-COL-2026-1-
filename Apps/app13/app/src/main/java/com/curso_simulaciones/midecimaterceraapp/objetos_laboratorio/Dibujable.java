package com.curso_simulaciones.midecimaterceraapp.objetos_laboratorio;

import android.graphics.Canvas;
import android.graphics.Paint;

/**
 * Interface para objetos dibujables en la Pizarra.
 */
public interface Dibujable {
    /**
     * método que deben implementar las clases cuyos objetos
     * serán dibujables en Pizarra que herede de View.
     * @param canvas
     * @param pincel
     */
    void dibujese(Canvas canvas, Paint pincel);
}
