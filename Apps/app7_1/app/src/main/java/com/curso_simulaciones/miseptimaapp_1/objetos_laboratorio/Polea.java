package com.curso_simulaciones.miseptimaapp_1.objetos_laboratorio;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

public class Polea {

    private float radio;
    private float posicionX, posicionY, posicionAngular;
    private int color = Color.rgb(255, 0, 0); // rojo

    /**
     * Constructor por defecto
     * Polea centrada en (0,0),
     * con posición angular 0,
     * de color rojo
     * y de radio 100f
     */
    public Polea() {
        this.radio = 100f;
        this.posicionX = 0f;
        this.posicionY = 0f;
        this.posicionAngular = 0f;
    }

    /**
     * Constructor de Polea centrada
     * en (posicionX, posicionY),
     * con posición angular cero,
     * de color rojo
     * y diámetro igual a 2*radio
     */
    public Polea(float posicionX, float posicionY, float radio) {
        this.posicionX = posicionX;
        this.posicionY = posicionY;
        this.radio = radio;
        this.posicionAngular = 0f;
    }

    /**
     * Modifica el valor del radio de la polea
     *
     * @param radio nuevo radio
     */
    public void setRadioPolea(float radio) {
        this.radio = radio;
    }

    /**
     * Devuelve el valor del radio de la polea
     *
     * @return radio
     */
    public float getRadioPolea() {
        return radio;
    }

    /**
     * Modifica el color de la polea
     *
     * @param color color en formato ARGB
     */
    public void setColorPolea(int color) {
        this.color = color;
    }

    /**
     * Devuelve el color de la polea
     *
     * @return color
     */
    public int getColorPolea() {
        return color;
    }

    /**
     * Modifica la posición (x,y) de la polea
     *
     * @param posicionX nueva posición X
     * @param posicionY nueva posición Y
     */
    public void trasladarPolea(float posicionX, float posicionY) {
        this.posicionX = posicionX;
        this.posicionY = posicionY;
    }

    /**
     * Modifica la posición angular en grados de la polea
     *
     * @param posicionAngular ángulo en grados
     */
    public void rotarPolea(float posicionAngular) {
        this.posicionAngular = posicionAngular;
    }

    /**
     * Dibuja la polea sobre el canvas
     *
     * @param canvas superficie de dibujo
     * @param pincel pincel configurado externamente
     */
    public void dibujese(Canvas canvas, Paint pincel) {

        // estilo del pincel
        pincel.setStyle(Paint.Style.STROKE);
        // grosor del pincel
        pincel.setStrokeWidth(2f);
        // color del pincel
        pincel.setColor(color);

        canvas.save();

        // rotar la polea respecto a su centro
        canvas.rotate(posicionAngular, posicionX, posicionY);

        // dibujar circunferencia de la polea
        canvas.drawCircle(posicionX, posicionY, radio, pincel);

        // dibujar las líneas radiales de la polea
        for (int i = 0; i < 12; i = i + 1) {
            // rotar de a 36 grados
            canvas.rotate(i * 36f, posicionX, posicionY);
            canvas.drawLine(
                    posicionX,
                    posicionY,
                    posicionX,
                    posicionY - radio,
                    pincel
            );
            // retroceder la rotación
            canvas.rotate(-i * 36f, posicionX, posicionY);
        }

        // restaurar estado del canvas
        canvas.restore();
    }
}

