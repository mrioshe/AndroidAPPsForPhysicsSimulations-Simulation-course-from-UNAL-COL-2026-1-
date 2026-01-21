package com.curso_simulaciones.myapplication.objetos_laboratorio;

import android.graphics.Canvas;
import android.graphics.Paint;

/**
 * Polea hereda de CuerpoRigido e implementa dibujese.
 */
public class Polea extends CuerpoRigido {

    protected float radio;

    public Polea() {
        super();
        this.radio = 50f;
    }

    public Polea(float posicionInicialCentroMasaX, float posicionInicialCentroMasaY, float radio) {
        super(posicionInicialCentroMasaX, posicionInicialCentroMasaY);
        this.radio = radio;
    }

    public void setRadio(float radio) {
        this.radio = radio;
    }

    public float getRadio() {
        return radio;
    }

    @Override
    public void dibujese(Canvas canvas, Paint pincel) {
        pincel.setStyle(Paint.Style.STROKE);
        pincel.setStrokeWidth(2f);
        pincel.setColor(color);

        canvas.save();
        canvas.rotate(posicionAngularRotacionEjeXY, posicionEjeRotacionX, posicionEjeRotacionY);

        // circunferencia de la polea
        canvas.drawCircle(posicionCentroMasaX, posicionCentroMasaY, radio, pincel);

        // líneas radiales
        for (int i = 0; i < 12; i++) {
            canvas.rotate(i * 36f, posicionCentroMasaX, posicionCentroMasaY);
            canvas.drawLine(posicionCentroMasaX, posicionCentroMasaY,
                    posicionCentroMasaX, posicionCentroMasaY - radio, pincel);
            canvas.rotate(-i * 36f, posicionCentroMasaX, posicionCentroMasaY);
        }

        canvas.restore();
    }
}