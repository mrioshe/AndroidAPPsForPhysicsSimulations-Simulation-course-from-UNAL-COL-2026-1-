package com.curso_simulaciones.myapplication.objetos_laboratorio;

import android.graphics.Color;
import android.graphics.Canvas;
import android.graphics.Paint;

/**
 * Clase base abstracta para todos los cuerpos rígidos.
 * Define atributos comunes y firmas de métodos que las subclases deben implementar.
 */
public abstract class CuerpoRigido {

    protected float posicionInicialCentroMasaX, posicionInicialCentroMasaY;
    protected float posicionCentroMasaX, posicionCentroMasaY;
    protected float posicionEjeRotacionX, posicionEjeRotacionY;
    protected float posicionAngularRotacionEjeXY;
    protected int color = Color.RED;

    public CuerpoRigido() {
        // valores por defecto (0,0) y color rojo
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

    // Traslación relativa respecto a la posición inicial
    public void mover(float desplazamientoCentroMasaX, float desplazamientoCentroMasaY) {
        this.posicionCentroMasaX = this.posicionInicialCentroMasaX + desplazamientoCentroMasaX;
        this.posicionCentroMasaY = this.posicionInicialCentroMasaY + desplazamientoCentroMasaY;
    }

    // Rotación alrededor del centro de masa (ángulo en grados)
    public void mover(float posicionAngular) {
        this.posicionEjeRotacionX = this.posicionInicialCentroMasaX;
        this.posicionEjeRotacionY = this.posicionInicialCentroMasaY;
        this.posicionAngularRotacionEjeXY = posicionAngular;
    }

    // Traslación y rotación simultánea
    public void mover(float desplazamientoCentroMasaX, float desplazamientoCentroMasaY, float posicionAngular) {
        this.posicionCentroMasaX = this.posicionInicialCentroMasaX + desplazamientoCentroMasaX;
        this.posicionCentroMasaY = this.posicionInicialCentroMasaY + desplazamientoCentroMasaY;
        this.posicionEjeRotacionX = this.posicionCentroMasaX;
        this.posicionEjeRotacionY = this.posicionCentroMasaY;
        this.posicionAngularRotacionEjeXY = posicionAngular;
    }

    // Establece eje de rotación y ángulo explícitamente
    public void rotar(float posicionEjeRotacionX, float posicionEjeRotacionY, float posicionAngular) {
        this.posicionEjeRotacionX = posicionEjeRotacionX;
        this.posicionEjeRotacionY = posicionEjeRotacionY;
        this.posicionAngularRotacionEjeXY = posicionAngular;
    }

    // Método abstracto que obliga a las subclases a implementar su dibujo
    public abstract void dibujese(Canvas canvas, Paint pincel);
}