package com.curso_simulaciones.midecimacuartaapp.vista;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.view.View;

import com.curso_simulaciones.midecimacuartaapp.elementos_del_espacio.ObjetoEspacial;

public class Pizarra extends View {

    private ObjetoEspacial objetosEspaciales[];



    /**
     * Constructor
     *
     * @param context
     */
    public Pizarra(Context context) {
        super(context);

    }


    public void setEstadoEscena(ObjetoEspacial[] objetosEspaciales) {

        this.objetosEspaciales = objetosEspaciales;

    }


    //Método para dibujar la escena
    private void dibujarEscena(Canvas canvas, Paint pincel) {

        //dibujar los objetos espaciales
        for (int i = 0; i < objetosEspaciales.length; i++) {
            if (objetosEspaciales[i] != null) {
                objetosEspaciales[i].dibujese(canvas, pincel);
            }
        }


    }


    //método para dibujar
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);


        Paint pincel = new Paint();
        //evita efecto sierra
        pincel.setAntiAlias(true);

        if (objetosEspaciales != null)
            dibujarEscena(canvas, pincel);


        //necesario para actualizar los dibujos en animaciones
        invalidate();

    }
}