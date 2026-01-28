package com.curso_simulaciones.simulphysics.objetos_laboratorio;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;

public class Flecha extends ObjetoLaboratorio {

    private float longitud;

    /**
     * Constructor de flecha con punto inicial en
     * (posicionX, posicionY) y largo longitud
     */
    public Flecha(float posicionX, float posicionY, float longitud) {
        this.posicionX = posicionX;
        this.posicionY = posicionY;
        this.longitud = longitud;
    }

    public void setLongitud(float longitud) {
        this.longitud = longitud;
    }

    public float getLongitud() {
        return longitud;
    }

    public void setPosicion(float posicionX, float posicionY) {
        this.posicionX = posicionX;
        this.posicionY = posicionY;
    }

    public float[] getPosicion() {
        float[] coordenadas = new float[2];
        coordenadas[0] = posicionX;
        coordenadas[1] = posicionY;
        return coordenadas;
    }

    public void setGrosorLinea(float grosorLinea) {
        this.grosorLinea = grosorLinea;
    }

    public float getGrosorLinea() {
        return grosorLinea;
    }

    /**
     * Genera rotación de la flecha a la
     * posición angular posicionAngular
     * alrededor de eje que pasa por
     * (posicionX,posicionY)
     */
    public void rotar(float posicionAngular) {
        this.posicionAngular = posicionAngular;
    }

    //implementa el método de la Interface Dibujable
    public void dibujese(Canvas canvas, Paint pincel) {
        //estado del canvas para luego restaurarlo a como estaba
        canvas.save();
        pincel.setStrokeWidth(grosorLinea);
        pincel.setColor(color);
        //traslada la flecha a la posición (posicionX, posicionY)
        canvas.translate(posicionX, posicionY);
        //rota la flecha alrededor de eje que pasa por (posicionX, posicionY)
        canvas.rotate(posicionAngular);
        float cabeza = 0.1f * longitud;
        Path path = new Path();
        path.moveTo(0, 0);
        path.lineTo(longitud - cabeza, 0);
        path.lineTo(longitud - cabeza, -0.5f * cabeza);
        path.lineTo(longitud, 0);
        path.lineTo(longitud - cabeza, 0.5f * cabeza);
        path.lineTo(longitud - cabeza, 0);
        pincel.setStyle(Paint.Style.STROKE);
        canvas.drawPath(path, pincel);
        //restaurar el canvas
        canvas.restore();
        //restaurar el pincel
        pincel.reset();
    }

}