package com.curso_simulaciones.midecimaterceraapp.objetos_laboratorio;

import android.graphics.Canvas;
import android.graphics.Paint;

/**
 * Superclase abstracta para todos los objetos del laboratorio.
 */
public abstract class ObjetoLaboratorio implements Dibujable {

    /**
     * Método para que se dibujen los objetos Dibujables,
     * es decir, los objetos que implementan la interface Dibujable
     * @param canvas
     * @param pincel
     */
    @Override
    public abstract void dibujese(Canvas canvas, Paint pincel);
}
