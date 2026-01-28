package com.curso_simulaciones.simulphysics.objetos_laboratorio;

import android.graphics.Canvas;
import android.graphics.Paint;

public class Regla extends ObjetoLaboratorio {

    private float esquinaIzquierdaX, esquinaIzquierdaY, largo, alto;
    private int colorLetras = android.graphics.Color.BLACK;
    private int numeroDivisiones = 50;

    /**
     * Constructor de regla cuya esquina superior izquierda
     * se encuentra en (esquinaIzquierdaX,esquinaIzquierdaY)
     * y con largo y ancho
     */
    public Regla(float esquinaIzquierdaX, float esquinaIzquierdaY, float largo, float alto) {
        this.esquinaIzquierdaX = esquinaIzquierdaX;
        this.esquinaIzquierdaY = esquinaIzquierdaY;
        this.largo = largo;
        this.alto = alto;
    }

    public void setNumeroDivisiones(int numeroDivisiones) {
        this.numeroDivisiones = numeroDivisiones;
    }

    public void setColorLetras(int colorLetras) {
        this.colorLetras = colorLetras;
    }

    /**
     * Rota la regla alrededor de eje que pasa por
     * la esquina superior izquierda un ángulo igual
     * a posicionAngular
     */
    public void rotar(float posicionAngular) {
        this.posicionAngular = posicionAngular;
    }

    /**
     * Cambia la posición de la regla
     */
    public void setPosicionRegla(float esquinaIzquierdaX, float esquinaIzquierdaY) {
        this.esquinaIzquierdaX = esquinaIzquierdaX;
        this.esquinaIzquierdaY = esquinaIzquierdaY;
    }

    //implementa el método de la Interface Dibujable
    public void dibujese(Canvas canvas, Paint pincel) {
        //estado del canvas para luego restaurarlo a como estaba
        canvas.save();
        //traslada la regla a la posición (esquinaIzquierdaX, esquinaIzquierdaY)
        canvas.translate(esquinaIzquierdaX, esquinaIzquierdaY);
        //rota la regla alrededor de eje que pasa por (0,0)
        canvas.rotate(posicionAngular, 0, 0);
        pincel.setStyle(Paint.Style.FILL);
        pincel.setColor(color);
        canvas.drawRect(0, 0, largo, alto, pincel);
        pincel.setColor(android.graphics.Color.BLACK);
        pincel.setStyle(Paint.Style.STROKE);
        canvas.drawRect(0, 0, largo, alto, pincel);
        pincel.setStrokeWidth(2);
        float paso = (float) ((largo - 0.1 * largo) / numeroDivisiones);
        float inicio = (float) (0.05 * largo);
        for (int i = 0; i < numeroDivisiones + 1; i++) {
            pincel.setColor(android.graphics.Color.BLACK);
            canvas.drawLine(inicio + paso * i, 0, inicio + paso * i, (float) (0.25 * alto), pincel);
            //cada 5 divisiones una división larga
            if (i % 5 == 0) {
                pincel.setColor(android.graphics.Color.RED);
                canvas.drawLine(inicio + paso * i, 0, inicio + paso * i, (float) (0.5 * alto), pincel);
                //marcar
                pincel.setTextSize(0.2f * alto);
                pincel.setStyle(Paint.Style.FILL);
                pincel.setColor(colorLetras);
                canvas.drawText("" + i, inicio + paso * i - 0.01f * largo, 0.75f * alto, pincel);
            }
        }
        //restaurar el canvas
        canvas.restore();
        //restaurar el pincel
        pincel.reset();
    }

}