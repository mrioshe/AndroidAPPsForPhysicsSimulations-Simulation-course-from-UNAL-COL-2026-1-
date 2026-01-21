package com.curso_simulaciones.myapplication.vista;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

import com.curso_simulaciones.myapplication.objetos_laboratorio.CuerpoRectangular;
import com.curso_simulaciones.myapplication.objetos_laboratorio.Polea;
import com.curso_simulaciones.myapplication.objetos_laboratorio.Rueda;

/**
 * Vista que dibuja arreglos de Polea, Rueda y CuerpoRectangular.
 */
public class Pizarra extends View {

    private Polea[] poleas;
    private Rueda[] ruedas;
    private CuerpoRectangular[] cuerposRect;

    public Pizarra(Context context) {
        super(context);
    }

    public Pizarra(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public void setEstadoEscena(Polea[] poleas, Rueda[] ruedas, CuerpoRectangular[] cuerposRect) {
        this.poleas = poleas;
        this.ruedas = ruedas;
        this.cuerposRect = cuerposRect;
    }

    private void dibujarEscena(Canvas canvas, Paint pincel) {
        if (poleas != null) {
            for (int i = 0; i < poleas.length; i++) {
                if (poleas[i] != null) poleas[i].dibujese(canvas, pincel);
            }
        }

        if (ruedas != null) {
            for (int i = 0; i < ruedas.length; i++) {
                if (ruedas[i] != null) ruedas[i].dibujese(canvas, pincel);
            }
        }

        if (cuerposRect != null) {
            for (int i = 0; i < cuerposRect.length; i++) {
                if (cuerposRect[i] != null) cuerposRect[i].dibujese(canvas, pincel);
            }
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        Paint pincel = new Paint();
        pincel.setAntiAlias(true);

        dibujarEscena(canvas, pincel);

        // actualizar animación
        invalidate();
    }
}