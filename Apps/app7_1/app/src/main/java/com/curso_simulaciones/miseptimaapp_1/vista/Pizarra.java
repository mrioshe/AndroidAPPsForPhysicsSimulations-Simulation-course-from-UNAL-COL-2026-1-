package com.curso_simulaciones.miseptimaapp_1.vista;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.view.View;

import com.curso_simulaciones.miseptimaapp_1.objetos_laboratorio.Polea;

/**
 * Vista donde se dibujan las poleas (escena)
 */
public class Pizarra extends View {

    private Polea poleas[];

    /**
     * Constructor
     *
     * @param context contexto de la aplicación
     */
    public Pizarra(Context context) {
        super(context);
    }

    /**
     * Método para cambiar la escena
     *
     * @param poleas arreglo de poleas a dibujar
     */
    public void setEstadoEscena(Polea[] poleas) {
        this.poleas = poleas;
        // solicitar redibujado
        invalidate();
    }

    // Método para dibujar la escena
    private void dibujarEscena(Canvas canvas, Paint pincel) {
        if (poleas == null) return;

        for (int i = 0; i < poleas.length; i++) {
            if (poleas[i] != null) {
                poleas[i].dibujese(canvas, pincel);
            }
        }
    }

    // método para dibujar
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        Paint pincel = new Paint();
        // evita efecto sierra
        pincel.setAntiAlias(true);

        dibujarEscena(canvas, pincel);

        // necesario para actualizar los dibujos en animaciones
        invalidate();
    }
}
