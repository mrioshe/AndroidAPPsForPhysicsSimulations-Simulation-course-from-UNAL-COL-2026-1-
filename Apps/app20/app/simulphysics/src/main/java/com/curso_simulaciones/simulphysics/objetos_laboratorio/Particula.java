package com.curso_simulaciones.simulphysics.objetos_laboratorio;

import android.graphics.Canvas;
import android.graphics.Paint;

public class Particula extends ObjetoLaboratorio {

    protected float radio = 5f;

    public Particula() {
    }

    public Particula(float x, float y, float radio) {
        this.posicionX = x;
        this.posicionY = y;
        this.radio = radio;
    }

    public void setRadio(float radio) {
        this.radio = radio;
    }

    public float getRadio() {
        return radio;
    }

    public void dibujese(Canvas canvas, Paint pincel) {
        canvas.save();
        pincel.setStyle(Paint.Style.FILL);
        pincel.setColor(color);
        canvas.drawCircle(posicionX, posicionY, radio, pincel);
        canvas.restore();
        pincel.reset();
    }
}