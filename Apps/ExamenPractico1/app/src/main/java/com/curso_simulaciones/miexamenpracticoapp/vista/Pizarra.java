package com.curso_simulaciones.miexamenpracticoapp.vista;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.view.View;

import com.curso_simulaciones.miexamenpracticoapp.objetos_laboratorio.Dibujable;

/**
 * Clase Pizarra
 * Extiende View para poder dibujar objetos Dibujable en un Canvas
 */
public class Pizarra extends View {

    private Dibujable[] objetosDibujables;

    /**
     * Constructor
     * @param context Contexto de la aplicación
     */
    public Pizarra(Context context) {
        super(context);
    }

    /**
     * Establece el estado de la escena con los objetos a dibujar
     * @param objetosDibujables Array de objetos que implementan Dibujable
     */
    public void setEstadoEscena(Dibujable[] objetosDibujables) {
        this.objetosDibujables = objetosDibujables;
    }

    /**
     * Método para dibujar la escena
     * @param canvas Canvas donde se dibujará
     * @param pincel Paint con las propiedades de dibujo
     */
    private void dibujarEscena(Canvas canvas, Paint pincel) {
        // Dibujar todos los objetos dibujables
        if (objetosDibujables != null) {
            for (int i = 0; i < objetosDibujables.length; i++) {
                if (objetosDibujables[i] != null) {
                    objetosDibujables[i].dibujese(canvas, pincel);
                }
            }
        }
    }

    /**
     * Método onDraw sobrescrito para dibujar en el canvas
     * @param canvas Canvas donde se dibujará
     */
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        Paint pincel = new Paint();
        // Evitar efecto sierra
        pincel.setAntiAlias(true);

        if (objetosDibujables != null) {
            dibujarEscena(canvas, pincel);
        }

        // Necesario para actualizar los dibujos en animaciones
        invalidate();
    }
}