package com.curso_simulaciones.midecimacuartaapp.elementos_del_espacio;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;

public class Venusiano extends Extraterrestre {

    /**
     * Constructor por defecto del venusiano
     */
    public Venusiano() {
        super();
        this.color = Color.BLUE;
    }

    /**
     * Constructor del venusiano con centroide
     * en (posicionCentroideX, posicionCentroideY)
     */
    public Venusiano(float posicionCentroideX, float posicionCentroideY) {
        super(posicionCentroideX, posicionCentroideY);
        this.color = Color.BLUE;
    }

    @Override
    public void dibujese(Canvas canvas, Paint pincel) {

        canvas.save();

        //magnificar
        canvas.scale(magnificacion, magnificacion, posicionCentroideX, posicionCentroideY);
        //rotar
        canvas.rotate(posicionAngularRotacionEjeXY, posicionEjeRotacionX, posicionEjeRotacionY);

        // Radio de la cabeza
        float R = 50f;

        // Sombrero/casco superior (elipse horizontal)
        float centro_elipse_y = posicionCentroideY - 0.8f * R;
        float semieje_mayor = 0.90f * R; // ancho total ≈ 1.8*R
        float semieje_menor = 0.22f * R;

        pincel.setStyle(Paint.Style.FILL);
        pincel.setColor(color);
        RectF ovalCasco = new RectF(
                posicionCentroideX - semieje_mayor,
                centro_elipse_y - semieje_menor,
                posicionCentroideX + semieje_mayor,
                centro_elipse_y + semieje_menor
        );
        canvas.drawOval(ovalCasco, pincel);

        // Cabeza (círculo)
        canvas.drawCircle(posicionCentroideX, posicionCentroideY, R, pincel);

        // Contorno de la cabeza
        pincel.setStyle(Paint.Style.STROKE);
        pincel.setStrokeWidth(0.02f * R);
        pincel.setColor(Color.rgb(0, 0, 150)); // Azul oscuro
        canvas.drawCircle(posicionCentroideX, posicionCentroideY, R, pincel);

        // Ojos (dos círculos blancos grandes con pupila)
        float separacion_ojos = 0.30f * R;
        float pos_y_ojos = posicionCentroideY - 0.05f * R;
        float radio_ojo = 0.18f * R;
        float radio_pupila = 0.05f * R;

        pincel.setStyle(Paint.Style.FILL);
        pincel.setColor(Color.WHITE);
        // Ojo izquierdo
        canvas.drawCircle(posicionCentroideX - separacion_ojos, pos_y_ojos, radio_ojo, pincel);
        // Ojo derecho
        canvas.drawCircle(posicionCentroideX + separacion_ojos, pos_y_ojos, radio_ojo, pincel);

        // Pupilas
        pincel.setColor(Color.BLACK);
        canvas.drawCircle(posicionCentroideX - separacion_ojos, pos_y_ojos, radio_pupila, pincel);
        canvas.drawCircle(posicionCentroideX + separacion_ojos, pos_y_ojos, radio_pupila, pincel);

        // Boca (línea horizontal o rectángulo bajo)
        float longitud_boca = 0.30f * R;
        float grosor_boca = 0.03f * R;
        float pos_y_boca = posicionCentroideY + 0.35f * R;

        pincel.setColor(Color.WHITE);
        canvas.drawRect(
                posicionCentroideX - longitud_boca / 2,
                pos_y_boca - grosor_boca / 2,
                posicionCentroideX + longitud_boca / 2,
                pos_y_boca + grosor_boca / 2,
                pincel
        );

        canvas.restore();
    }
}