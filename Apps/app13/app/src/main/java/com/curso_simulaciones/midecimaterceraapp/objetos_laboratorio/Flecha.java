package com.curso_simulaciones.midecimaterceraapp.objetos_laboratorio;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Color;

/**
 * Flecha: dibuja una flecha entre dos puntos. No hereda de CuerpoRigido.
 */
public class Flecha extends ObjetoLaboratorio {

    private float x1, y1, x2, y2;
    private int color = Color.BLACK;
    private float grosor = 4f;

    public Flecha() {
        this(0,0,100,0);
    }

    public Flecha(float x1, float y1, float x2, float y2) {
        this.x1 = x1; this.y1 = y1; this.x2 = x2; this.y2 = y2;
    }

    public void setColor(int color) { this.color = color; }
    public void setGrosor(float g) { this.grosor = g; }

    @Override
    public void dibujese(Canvas canvas, Paint pincel) {
        pincel.setStyle(Paint.Style.STROKE);
        pincel.setStrokeWidth(grosor);
        pincel.setColor(color);

        canvas.save();
        // línea principal
        canvas.drawLine(x1, y1, x2, y2, pincel);

        // triángulo de punta simple
        float dx = x2 - x1;
        float dy = y2 - y1;
        float len = (float)Math.hypot(dx, dy);
        if (len > 0.001f) {
            float ux = dx / len;
            float uy = dy / len;
            float size = 10f;
            // dos líneas formando la punta
            canvas.drawLine(x2, y2, x2 - ux * size - uy * (size*0.5f), y2 - uy * size + ux * (size*0.5f), pincel);
            canvas.drawLine(x2, y2, x2 - ux * size + uy * (size*0.5f), y2 - uy * size - ux * (size*0.5f), pincel);
        }
        canvas.restore();
    }
}
