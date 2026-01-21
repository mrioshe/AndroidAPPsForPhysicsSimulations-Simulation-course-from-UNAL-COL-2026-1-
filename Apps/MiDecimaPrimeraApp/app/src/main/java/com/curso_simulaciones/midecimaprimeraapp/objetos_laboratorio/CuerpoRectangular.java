package com.curso_simulaciones.midecimaprimeraapp.objetos_laboratorio;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

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

    // Método añadido para permitir rotaciones desde fuera
    public void rotar(float ejeX, float ejeY, float anguloGrados) {
        this.posicionEjeRotacionX = ejeX;
        this.posicionEjeRotacionY = ejeY;
        this.posicionAngularRotacionEjeXY = anguloGrados;
    }

    public void dibujese(Canvas canvas, Paint pincel) {
        // estilo del pincel
        pincel.setStyle(Paint.Style.STROKE);
        // grosor del pincel
        pincel.setStrokeWidth(2f);
        // color del pincel
        pincel.setColor(color);

        canvas.save();
        // rotar usando la posición angular y el eje actuales
        canvas.rotate(posicionAngularRotacionEjeXY, posicionEjeRotacionX, posicionEjeRotacionY);
        // dibujar el relleno del bloque
        pincel.setStyle(Paint.Style.FILL);
        canvas.drawRect(posicionCentroMasaX - 0.5f * largo,
                posicionCentroMasaY - 0.5f * alto,
                posicionCentroMasaX + 0.5f * largo,
                posicionCentroMasaY + 0.5f * alto,
                pincel);
        // dibujar el perímetro del bloque
        pincel.setColor(Color.BLACK);
        pincel.setStyle(Paint.Style.STROKE);
        canvas.drawRect(posicionCentroMasaX - 0.5f * largo,
                posicionCentroMasaY - 0.5f * alto,
                posicionCentroMasaX + 0.5f * largo,
                posicionCentroMasaY + 0.5f * alto,
                pincel);
        // regresar la rotación
        canvas.restore();
    }
}