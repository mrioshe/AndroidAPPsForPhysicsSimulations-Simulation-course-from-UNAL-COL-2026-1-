package com.curso_simulaciones.midecimaterceraapp.objetos_laboratorio;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Color;

/**
 * Clase abstracta que representa un cuerpo rígido.
 */
public abstract class CuerpoRigido extends ObjetoLaboratorio {

    protected float posicionInicialCentroMasaX, posicionInicialCentroMasaY;
    protected float posicionCentroMasaX, posicionCentroMasaY;
    protected float posicionEjeRotacionX, posicionEjeRotacionY;
    protected float posicionAngularRotacionEjeXY;
    protected int color = Color.RED;

    public CuerpoRigido() {
        // valores por defecto: posiciones en 0
    }

    public CuerpoRigido(float posicionInicialCentroMasaX, float posicionInicialCentroMasaY) {
        this.posicionCentroMasaX = posicionInicialCentroMasaX;
        this.posicionCentroMasaY = posicionInicialCentroMasaY;
        this.posicionInicialCentroMasaX = posicionInicialCentroMasaX;
        this.posicionInicialCentroMasaY = posicionInicialCentroMasaY;
    }

    public void setColor(int color) {
        this.color = color;
    }

    public int getColor() {
        return color;
    }

    public float getPosicionX() {
        return posicionCentroMasaX;
    }

    public float getPosicionY() {
        return posicionCentroMasaY;
    }

    public void mover(float desplazamientoCentroMasaX, float desplazamientoCentroMasaY) {
        this.posicionCentroMasaX = this.posicionInicialCentroMasaX + desplazamientoCentroMasaX;
        this.posicionCentroMasaY = this.posicionInicialCentroMasaY + desplazamientoCentroMasaY;
    }

    public void mover(float posicionAngular) {
        this.posicionEjeRotacionX = this.posicionInicialCentroMasaX;
        this.posicionEjeRotacionY = this.posicionInicialCentroMasaY;
        this.posicionAngularRotacionEjeXY = posicionAngular;
    }

    public void mover(float desplazamientoCentroMasaX, float desplazamientoCentroMasaY, float posicionAngular) {
        this.posicionCentroMasaX = this.posicionInicialCentroMasaX + desplazamientoCentroMasaX;
        this.posicionCentroMasaY = this.posicionInicialCentroMasaY + desplazamientoCentroMasaY;
        this.posicionEjeRotacionX = this.posicionCentroMasaX;
        this.posicionEjeRotacionY = this.posicionCentroMasaY;
        this.posicionAngularRotacionEjeXY = posicionAngular;
    }

    public void rotar(float posicionEjeRotacionX, float posicionEjeRotacionY, float posicionAngular) {
        this.posicionEjeRotacionX = posicionEjeRotacionX;
        this.posicionEjeRotacionY = posicionEjeRotacionY;
        this.posicionAngularRotacionEjeXY = posicionAngular;
    }

    // dibujese es abstracto y lo implementan las subclases
    @Override
    public abstract void dibujese(Canvas canvas, Paint pincel);
}
