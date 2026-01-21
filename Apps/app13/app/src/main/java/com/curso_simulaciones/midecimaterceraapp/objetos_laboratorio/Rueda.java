package com.curso_simulaciones.midecimaterceraapp.objetos_laboratorio;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Color;

/**
 * Rueda - subclase de CuerpoRigido
 */
public class Rueda extends CuerpoRigido {

    protected float radio;

    public Rueda() {
        super();
        this.radio = 50f;
    }

    public Rueda(float posicionInicialCentroMasaX, float posicionInicialCentroMasaY, float radio) {
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

        // relleno de la rueda
        pincel.setStyle(Paint.Style.FILL);
        canvas.drawCircle(posicionCentroMasaX, posicionCentroMasaY, radio, pincel);

        // dibujos negros dentro para ver rotación
        pincel.setColor(Color.BLACK);
        for (int i = 0; i < 6; i++) {
            canvas.save();
            canvas.rotate(i * 60f, posicionCentroMasaX, posicionCentroMasaY);
            canvas.drawCircle(posicionCentroMasaX - 0.2f * radio,
                    posicionCentroMasaY - 0.3f * radio,
                    0.1f * radio, pincel);
            canvas.restore();
        }

        // perímetro
        pincel.setStyle(Paint.Style.STROKE);
        canvas.drawCircle(posicionCentroMasaX, posicionCentroMasaY, radio, pincel);

        canvas.restore();
    }
}
