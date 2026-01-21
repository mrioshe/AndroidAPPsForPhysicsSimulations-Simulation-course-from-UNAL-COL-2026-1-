package com.curso_simulaciones.midecimaterceraapp.objetos_laboratorio;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Color;

/**
 * CuerpoRectangular - subclase de CuerpoRigido
 */
public class CuerpoRectangular extends CuerpoRigido {

    private float largo, alto;

    public CuerpoRectangular() {
        this.largo = 150f;
        this.alto = 100f;
    }

    public CuerpoRectangular(float posicionInicialCentroMasaX, float posicionInicialCentroMasaY,
                             float largo, float alto) {
        super(posicionInicialCentroMasaX, posicionInicialCentroMasaY);
        this.largo = largo;
        this.alto = alto;
    }

    public void setLargo(float largo) {
        this.largo = largo;
    }

    public float getLargo() {
        return largo;
    }

    public void setAlto(float alto) {
        this.alto = alto;
    }

    public float getAlto() {
        return alto;
    }

    @Override
    public void dibujese(Canvas canvas, Paint pincel) {
        pincel.setStyle(Paint.Style.STROKE);
        pincel.setStrokeWidth(2f);
        pincel.setColor(color);

        canvas.save();
        canvas.rotate(posicionAngularRotacionEjeXY, posicionEjeRotacionX, posicionEjeRotacionY);

        // relleno
        pincel.setStyle(Paint.Style.FILL);
        canvas.drawRect(posicionCentroMasaX - 0.5f * largo,
                posicionCentroMasaY - 0.5f * alto,
                posicionCentroMasaX + 0.5f * largo,
                posicionCentroMasaY + 0.5f * alto,
                pincel);

        // perímetro en negro
        pincel.setColor(Color.BLACK);
        pincel.setStyle(Paint.Style.STROKE);
        canvas.drawRect(posicionCentroMasaX - 0.5f * largo,
                posicionCentroMasaY - 0.5f * alto,
                posicionCentroMasaX + 0.5f * largo,
                posicionCentroMasaY + 0.5f * alto,
                pincel);

        canvas.restore();
    }
}
