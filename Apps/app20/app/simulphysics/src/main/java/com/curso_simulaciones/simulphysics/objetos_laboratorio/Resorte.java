package com.curso_simulaciones.simulphysics.objetos_laboratorio;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;

public class Resorte extends ObjetoLaboratorio {

    private float longitudNatural, longitud;
    private float ancho;
    private int numeroEspiras = 12;

    /**
     * Constructor de resorte que comienza en
     * (posicionX,posicionY) de longitudNatural
     * y ancho
     */
    public Resorte(float posicionX, float posicionY, float longitudNatural, float ancho) {
        this.posicionX = posicionX;
        this.posicionY = posicionY;
        this.longitud = longitudNatural;
        this.ancho = ancho;
    }

    public void setLongitud(float longitud) {
        this.longitud = longitud;
    }

    public float getLongitud() {
        return longitud;
    }

    public void setLongitudNatural(float longitudNatural) {
        this.longitudNatural = longitudNatural;
        this.longitud = longitudNatural;
    }

    public float getLongitudNatural() {
        return longitudNatural;
    }

    public void setNumeroEspiras(int numeroEspiras) {
        this.numeroEspiras = numeroEspiras + 1;
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

    public void setColor(int color) {
        this.color = color;
    }

    public int getColor() {
        return color;
    }

    public void setAncho(float ancho) {
        this.ancho = ancho;
    }

    public float getAncho() {
        return ancho;
    }

    /**
     * Genera rotación del resorte a la
     * posición angular posicionAngular
     * alrededor de eje que pasa por
     * (posicionX,posicionY)
     */
    public void rotar(float posicionAngular) {
        this.posicionAngular = posicionAngular;
    }

    public void dibujese(Canvas canvas, Paint pincel) {
        //estado del canvas para luego restaurarlo a como estaba
        canvas.save();
        pincel.setStrokeWidth(grosorLinea);
        pincel.setColor(color);
        //traslada la resorte a la posición (posicionX, posicionY)
        canvas.translate(posicionX, posicionY);
        //rota la resorte alrededor de eje que pasa por (posicionX, posicionY)
        canvas.rotate(posicionAngular);
        float paso = longitud / numeroEspiras;
        pincel.setStyle(Paint.Style.STROKE);
        Path path = new Path();
        path.moveTo(0, 0);
        path.lineTo(0, 0);
        for (int i = 1; i < numeroEspiras; i = i + 1) {
            path.lineTo(i * paso, (float) Math.pow(-1, i) * ancho);
        }
        path.lineTo(numeroEspiras * paso, 0);
        canvas.drawPath(path, pincel);
        //restaurar el canvas
        canvas.restore();
        //restaurar el pincel
        pincel.reset();
    }

}