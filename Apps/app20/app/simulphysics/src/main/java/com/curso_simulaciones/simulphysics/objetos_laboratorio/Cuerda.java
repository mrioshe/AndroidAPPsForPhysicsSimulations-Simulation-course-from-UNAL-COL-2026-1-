package com.curso_simulaciones.simulphysics.objetos_laboratorio;

import android.graphics.Canvas;
import android.graphics.Paint;

public class Cuerda extends ObjetoLaboratorio {

    private float x2, y2;

    public Cuerda(float x1, float y1, float x2, float y2) {
        this.posicionX = x1;
        this.posicionY = y1;
        this.x2 = x2;
        this.y2 = y2;
    }

    public void setExtremo(float x2, float y2) {
        this.x2 = x2;
        this.y2 = y2;
    }

    public float[] getExtremos() {
        return new float[]{posicionX, posicionY, x2, y2};
    }

    public void dibujese(Canvas canvas, Paint pincel) {
        canvas.save();
        pincel.setStyle(Paint.Style.STROKE);
        pincel.setStrokeWidth(grosorLinea);
        pincel.setColor(color);
        canvas.drawLine(posicionX, posicionY, x2, y2, pincel);
        canvas.restore();
        pincel.reset();
    }
}