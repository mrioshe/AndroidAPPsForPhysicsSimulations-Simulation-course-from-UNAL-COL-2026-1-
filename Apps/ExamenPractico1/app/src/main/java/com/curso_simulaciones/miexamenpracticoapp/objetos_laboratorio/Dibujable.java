package com.curso_simulaciones.miexamenpracticoapp.objetos_laboratorio;

import android.graphics.Canvas;
import android.graphics.Paint;

/**
 * Interface Dibujable
 * Define el contrato para objetos que pueden ser dibujados en un Canvas
 */
public interface Dibujable {

    /**
     * Método para dibujar el objeto en el canvas
     * @param canvas Canvas donde se dibujará el objeto
     * @param pincel Paint con las propiedades de dibujo
     */
    void dibujese(Canvas canvas, Paint pincel);
}