package com.curso_simulaciones.myapplication.objetos_laboratorio;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

/**
 * Bloque rectangular que hereda de CuerpoRigido.
 */
public class CuerpoRectangular extends CuerpoRigido {

    private float largo, alto;

    public CuerpoRectangular() {
        this.largo = 150f;
        this.alto = 100f;
    }

    public CuerpoRectangular(float posicionInicialCentroMasaX, float posicionInicialCentroMasaY, float largo, float alto) {
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

    // Método auxiliar para permitir rotar desde fuera (coherente con el ejemplo)
    public void rotar(float ejeX, float ejeY, float anguloGrados) {
        this.posicionEjeRotacionX = ejeX;
        this.posicionEjeRotacionY = ejeY;
        this.posicionAngularRotacionEjeXY = anguloGrados;
    }

    @Override
    public void dibujese(Canvas canvas, Paint pincel) {
        pincel.setStyle(Paint.Style.STROKE);
        pincel.setStrokeWidth(2f);
        pincel.setColor(color);

        canvas.save();
        canvas.rotate(posicionAngularRotacionEjeXY, posicionEjeRotacionX, posicionEjeRotacionY);

        pincel.setStyle(Paint.Style.FILL);
        canvas.drawRect(posicionCentroMasaX - 0.5f * largo,
                posicionCentroMasaY - 0.5f * alto,
                posicionCentroMasaX + 0.5f * largo,
                posicionCentroMasaY + 0.5f * alto,
                pincel);

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