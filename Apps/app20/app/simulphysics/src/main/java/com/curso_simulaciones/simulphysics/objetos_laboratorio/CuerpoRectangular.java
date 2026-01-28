package com.curso_simulaciones.simulphysics.objetos_laboratorio;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Color;

public class CuerpoRectangular extends CuerpoRigido {

    protected float largo, alto;

    /**
     * Constructor de cuerpo rectangular
     * centrado en (posicionCentroMasaX,posicionCentroMasaY)
     * de dimensiones largoxalto
     */
    public CuerpoRectangular(float posicionCentroMasaX, float posicionCentroMasaY, float largo, float alto) {
        super(posicionCentroMasaX, posicionCentroMasaY);
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

    //se implementó este método que en su clase madre es abstracto
    public void dibujese(Canvas canvas, Paint pincel) {
        canvas.save();
        //estilo del pincel
        pincel.setStyle(Paint.Style.STROKE);
        //grosor del pincel
        pincel.setStrokeWidth(grosorLinea);
        //color del pincel
        pincel.setColor(color);
        //rotar
        canvas.rotate(posicionAngularRotacionEjeXY, posicionEjeRotacionX, posicionEjeRotacionY);
        //dibujar el relleno del bloque
        pincel.setStyle(Paint.Style.FILL);
        canvas.drawRect(posicionCentroMasaX - 0.5f * largo, posicionCentroMasaY - 0.5f * alto, posicionCentroMasaX + 0.5f * largo, posicionCentroMasaY + 0.5f * alto, pincel);
        //dibujar el perímetro del bloque
        pincel.setColor(Color.BLACK);
        pincel.setStyle(Paint.Style.STROKE);
        canvas.drawRect(posicionCentroMasaX - 0.5f * largo, posicionCentroMasaY - 0.5f * alto, posicionCentroMasaX + 0.5f * largo, posicionCentroMasaY + 0.5f * alto, pincel);
        //restaurar el canvas
        canvas.restore();
        //restaurar el pincel
        pincel.reset();
    }

}