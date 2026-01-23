package com.curso_simulaciones.midecimacuartaapp.elementos_del_espacio;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

public class Marciano extends Extraterrestre {


    /**
     * Constructor por defecto
     * del marciano con "centroide" en (0,0)
     */
    public Marciano() {
        super();
        this.color = Color.rgb(0, 200, 0); // Verde brillante
    }


    /**
     * Constructor del marciano con "centroide"
     * en (posicionCentroideX,posicionCentroideY)
     */

    public Marciano(float posicionCentroideX, float posicionCentroideY) {
        super(posicionCentroideX, posicionCentroideY);
        this.color = Color.rgb(0, 200, 0); // Verde brillante
    }




    //se implementó este método que en su clase madre es abstracto
    public void dibujese(Canvas canvas, Paint pincel) {

        //estilo del pincel
        pincel.setStyle(Paint.Style.STROKE);
        //grosor del pincel
        pincel.setStrokeWidth(2f);
        //color del pincel
        pincel.setColor(color);


        canvas.save();

        //magnificar
        canvas.scale(magnificacion, magnificacion, posicionCentroideX, posicionCentroideY);
        //rotar
        canvas.rotate(posicionAngularRotacionEjeXY, posicionEjeRotacionX, posicionEjeRotacionY);

        // Radio de la cabeza
        float radio = 50f;

        //dibujar círculo de la cara del marciano
        pincel.setStyle(Paint.Style.FILL);
        canvas.drawCircle(posicionCentroideX, posicionCentroideY, radio, pincel);
        pincel.setColor(Color.BLACK);
        //dibujar circunferencia de la cara del marciano
        pincel.setStyle(Paint.Style.STROKE);
        canvas.drawCircle(posicionCentroideX, posicionCentroideY, radio, pincel);


        //dibujar los ojitos del marcianito
        float separacion = 0.32f * radio;
        float posicionOjoCentroIzquierdoX = posicionCentroideX - separacion;
        float posicionOjoCentroIzquierdoY = posicionCentroideY - 0.15f * radio;
        float posicionOjoCentroDerechoX = posicionCentroideX + separacion;
        float posicionOjoCentroDerechoY = posicionCentroideY - 0.15f * radio;

        pincel.setStyle(Paint.Style.FILL);
        pincel.setColor(Color.WHITE);
        canvas.drawCircle(posicionOjoCentroIzquierdoX, posicionOjoCentroIzquierdoY, 0.18f * radio, pincel);
        canvas.drawCircle(posicionOjoCentroDerechoX, posicionOjoCentroDerechoY, 0.18f * radio, pincel);

        //dibujar la pupila de los ojitos
        pincel.setColor(Color.BLACK);
        canvas.drawCircle(posicionOjoCentroIzquierdoX, posicionOjoCentroIzquierdoY, 0.06f * radio, pincel);
        canvas.drawCircle(posicionOjoCentroDerechoX, posicionOjoCentroDerechoY, 0.06f * radio, pincel);

        //dibujar la boquita del marcianito
        float altoBoca = 0.12f * radio;
        float anchoBoca = 0.4f * radio;
        pincel.setColor(Color.WHITE);
        float descenso = 0.30f * radio;
        float posicionBocaCentroX = posicionCentroideX;
        float posicionBocaCentroY = posicionCentroideY + descenso;
        float xs_izquierda = posicionBocaCentroX - 0.5f * anchoBoca;
        float ys_izquierda = posicionBocaCentroY - 0.5f * altoBoca;
        float xi_derecha = posicionBocaCentroX + 0.5f * anchoBoca;
        float yi_derecha = posicionBocaCentroY + 0.5f * altoBoca;
        canvas.drawRect(xs_izquierda, ys_izquierda, xi_derecha, yi_derecha, pincel);

        //dibujar las orejitas/antenas del marcianito
        pincel.setColor(color);
        pincel.setStrokeWidth(12f);
        pincel.setStrokeCap(Paint.Cap.ROUND);

        //antena izquierda
        float x_i_antena_izq = posicionCentroideX - 0.45f * radio;
        float y_i_antena_izq = posicionCentroideY - radio;
        float x_f_antena_izq = x_i_antena_izq - 0.5f * radio;
        float y_f_antena_izq = y_i_antena_izq - 0.7f * radio;
        canvas.drawLine(x_i_antena_izq, y_i_antena_izq, x_f_antena_izq, y_f_antena_izq, pincel);
        canvas.drawCircle(x_f_antena_izq, y_f_antena_izq, 0.12f * radio, pincel);

        //antena derecha
        float x_i_antena_der = posicionCentroideX + 0.45f * radio;
        float y_i_antena_der = posicionCentroideY - radio;
        float x_f_antena_der = x_i_antena_der + 0.5f * radio;
        float y_f_antena_der = y_i_antena_der - 0.7f * radio;
        canvas.drawLine(x_i_antena_der, y_i_antena_der, x_f_antena_der, y_f_antena_der, pincel);
        canvas.drawCircle(x_f_antena_der, y_f_antena_der, 0.12f * radio, pincel);


        //regresa la rotación
        canvas.restore();

    }
}