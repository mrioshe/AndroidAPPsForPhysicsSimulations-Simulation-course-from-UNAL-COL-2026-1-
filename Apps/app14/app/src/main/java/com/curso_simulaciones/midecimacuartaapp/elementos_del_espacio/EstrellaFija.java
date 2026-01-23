package com.curso_simulaciones.midecimacuartaapp.elementos_del_espacio;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;

public class EstrellaFija extends ObjetoEspacial {

    private float posicionX, posicionY;
    private float radioExterior;
    private float radioInterior;
    private int color;
    private float rotacion;

    /**
     * Constructor por defecto
     * Estrella centrada en (0,0) con radio exterior 50f
     */
    public EstrellaFija() {
        this.posicionX = 0;
        this.posicionY = 0;
        this.radioExterior = 50f;
        this.radioInterior = 20f;
        this.color = Color.CYAN;
        this.rotacion = 5f; // ligera rotación antihoraria
    }

    /**
     * Constructor de EstrellaFija
     * @param posicionX
     * @param posicionY
     * @param radioExterior
     */
    public EstrellaFija(float posicionX, float posicionY, float radioExterior) {
        this.posicionX = posicionX;
        this.posicionY = posicionY;
        this.radioExterior = radioExterior;
        this.radioInterior = radioExterior * 0.4f; // proporción 0.18/0.45
        this.color = Color.CYAN;
        this.rotacion = 5f;
    }

    /**
     * Modifica el color de la estrella
     * @param color
     */
    public void setColor(int color) {
        this.color = color;
    }

    /**
     * Devuelve el color de la estrella
     * @return
     */
    public int getColor() {
        return color;
    }

    /**
     * Modifica la posición de la estrella
     * @param posicionX
     * @param posicionY
     */
    public void setPosicion(float posicionX, float posicionY) {
        this.posicionX = posicionX;
        this.posicionY = posicionY;
    }

    /**
     * Modifica el radio exterior de la estrella
     * @param radioExterior
     */
    public void setRadioExterior(float radioExterior) {
        this.radioExterior = radioExterior;
        this.radioInterior = radioExterior * 0.4f;
    }

    @Override
    public void dibujese(Canvas canvas, Paint pincel) {

        canvas.save();

        // Trasladar al centro de la estrella
        canvas.translate(posicionX, posicionY);
        // Rotar ligeramente
        canvas.rotate(rotacion);

        // Crear el path de la estrella de 5 puntas
        Path pathEstrella = new Path();

        int numPuntas = 5;
        float angulo = (float) (2 * Math.PI / (numPuntas * 2));

        for (int i = 0; i < numPuntas * 2; i++) {
            float radio = (i % 2 == 0) ? radioExterior : radioInterior;
            float x = (float) (radio * Math.cos(i * angulo - Math.PI / 2));
            float y = (float) (radio * Math.sin(i * angulo - Math.PI / 2));

            if (i == 0) {
                pathEstrella.moveTo(x, y);
            } else {
                pathEstrella.lineTo(x, y);
            }
        }
        pathEstrella.close();

        // Dibujar la estrella
        pincel.setStyle(Paint.Style.FILL);
        pincel.setColor(color);
        canvas.drawPath(pathEstrella, pincel);

        // Contorno opcional (muy fino)
        pincel.setStyle(Paint.Style.STROKE);
        pincel.setStrokeWidth(1f);
        pincel.setColor(Color.argb(100, 0, 0, 0));
        canvas.drawPath(pathEstrella, pincel);

        canvas.restore();
    }
}