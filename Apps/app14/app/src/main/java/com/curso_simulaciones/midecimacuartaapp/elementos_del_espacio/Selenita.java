package com.curso_simulaciones.midecimacuartaapp.elementos_del_espacio;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;

public class Selenita extends Extraterrestre {

    /**
     * Constructor por defecto del selenita
     */
    public Selenita() {
        super();
        this.color = Color.RED;
    }

    /**
     * Constructor del selenita con centroide
     * en (posicionCentroideX, posicionCentroideY)
     */
    public Selenita(float posicionCentroideX, float posicionCentroideY) {
        super(posicionCentroideX, posicionCentroideY);
        this.color = Color.RED;
    }

    @Override
    public void dibujese(Canvas canvas, Paint pincel) {

        canvas.save();

        //magnificar
        canvas.scale(magnificacion, magnificacion, posicionCentroideX, posicionCentroideY);
        //rotar
        canvas.rotate(posicionAngularRotacionEjeXY, posicionEjeRotacionX, posicionEjeRotacionY);

        // Dimensiones del cuerpo rectangular
        float W_rect = 60f;
        float H_rect = 1.5f * W_rect; // ≈ 90
        float radio_esquina = 0.06f * W_rect;

        // Cuerpo principal (rectángulo vertical)
        pincel.setStyle(Paint.Style.FILL);
        pincel.setColor(color);
        RectF rectCuerpo = new RectF(
                posicionCentroideX - W_rect / 2,
                posicionCentroideY - H_rect / 2,
                posicionCentroideX + W_rect / 2,
                posicionCentroideY + H_rect / 2
        );
        canvas.drawRoundRect(rectCuerpo, radio_esquina, radio_esquina, pincel);

        // Capuchón superior (pestaña)
        float ancho_cap = 0.18f * W_rect;
        float alto_cap = 0.08f * H_rect;
        RectF rectCapuchon = new RectF(
                posicionCentroideX - ancho_cap / 2,
                posicionCentroideY - H_rect / 2 - alto_cap,
                posicionCentroideX + ancho_cap / 2,
                posicionCentroideY - H_rect / 2
        );
        canvas.drawRect(rectCapuchon, pincel);

        // Ojos (dos cuadrados blancos)
        float lado_ojo = 0.10f * H_rect;
        float separacion_ojos = 0.25f * W_rect;
        float pos_y_ojos = posicionCentroideY - 0.18f * H_rect;

        pincel.setColor(Color.WHITE);
        // Ojo izquierdo
        canvas.drawRect(
                posicionCentroideX - separacion_ojos - lado_ojo / 2,
                pos_y_ojos - lado_ojo / 2,
                posicionCentroideX - separacion_ojos + lado_ojo / 2,
                pos_y_ojos + lado_ojo / 2,
                pincel
        );
        // Ojo derecho
        canvas.drawRect(
                posicionCentroideX + separacion_ojos - lado_ojo / 2,
                pos_y_ojos - lado_ojo / 2,
                posicionCentroideX + separacion_ojos + lado_ojo / 2,
                pos_y_ojos + lado_ojo / 2,
                pincel
        );

        // Destellos en los ojos (opcional)
        float lado_destello = 0.03f * H_rect;
        pincel.setColor(Color.LTGRAY);
        // Destello ojo izquierdo
        canvas.drawRect(
                posicionCentroideX - separacion_ojos - lado_ojo / 2 + lado_destello,
                pos_y_ojos - lado_ojo / 2 + lado_destello,
                posicionCentroideX - separacion_ojos - lado_ojo / 2 + 2 * lado_destello,
                pos_y_ojos - lado_ojo / 2 + 2 * lado_destello,
                pincel
        );
        // Destello ojo derecho
        canvas.drawRect(
                posicionCentroideX + separacion_ojos - lado_ojo / 2 + lado_destello,
                pos_y_ojos - lado_ojo / 2 + lado_destello,
                posicionCentroideX + separacion_ojos - lado_ojo / 2 + 2 * lado_destello,
                pos_y_ojos - lado_ojo / 2 + 2 * lado_destello,
                pincel
        );

        // Tres botones/nariz (pequeños cuadrados sobre la línea media)
        float lado_boton = 0.03f * H_rect;
        float separacion_botones = 0.04f * H_rect;
        float pos_y_boton_central = posicionCentroideY - 0.05f * H_rect;

        pincel.setColor(Color.BLACK);
        for (int i = -1; i <= 1; i++) {
            canvas.drawRect(
                    posicionCentroideX - lado_boton / 2,
                    pos_y_boton_central + i * separacion_botones - lado_boton / 2,
                    posicionCentroideX + lado_boton / 2,
                    pos_y_boton_central + i * separacion_botones + lado_boton / 2,
                    pincel
            );
        }

        // Boca (rectángulo horizontal)
        float ancho_boca = 0.33f * W_rect;
        float alto_boca = 0.10f * H_rect;
        float pos_y_boca = posicionCentroideY + 0.20f * H_rect;

        pincel.setColor(Color.WHITE);
        canvas.drawRect(
                posicionCentroideX - ancho_boca / 2,
                pos_y_boca - alto_boca / 2,
                posicionCentroideX + ancho_boca / 2,
                pos_y_boca + alto_boca / 2,
                pincel
        );

        // Contorno opcional
        pincel.setStyle(Paint.Style.STROKE);
        pincel.setStrokeWidth(0.02f * W_rect);
        pincel.setColor(Color.rgb(150, 0, 0)); // Rojo oscuro
        canvas.drawRoundRect(rectCuerpo, radio_esquina, radio_esquina, pincel);

        canvas.restore();
    }
}