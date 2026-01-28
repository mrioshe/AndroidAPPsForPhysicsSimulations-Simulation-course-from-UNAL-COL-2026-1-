package com.curso_simulaciones.mivigesimaapp.vista;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import com.curso_simulaciones.mivigesimaapp.datos.AlmacenDatosRAM;
import com.curso_simulaciones.simulphysics.objetos_laboratorio.Dibujable;
import com.curso_simulaciones.simulphysics.objetos_laboratorio.ObjetoLaboratorio;

import java.util.ArrayList;
import java.util.List;

/**
 * Clase Pizarra
 *
 * Vista responsable de desplegar los objetos Dibujables
 * y de manejar la responsividad básica (tamaño y toques).
 *
 * Implementación conservadora y coherente con el estilo del material.
 */
public class Pizarra extends View {

    private ObjetoLaboratorio objetosLab[];



    /**
     * Constructor
     *
     * @param context
     */
    public Pizarra(Context context) {
        super(context);

    }


    public void setEstadoEscena(ObjetoLaboratorio[] cuerpos) {

        this.objetosLab = cuerpos;

    }


    //Método para dibujar la escena
    private void dibujarEscena(Canvas canvas, Paint pincel) {

        //dibujar las objetos de laboratorio
        for (int i = 0; i < objetosLab.length; i++) {
            if (objetosLab[i] != null) {
                objetosLab[i].dibujese(canvas, pincel);
            }
        }



    }


    //método para dibujar
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);


        Paint pincel = new Paint();
        //evita efecto sierra
        pincel.setAntiAlias(true);

        if (objetosLab != null)
            dibujarEscena(canvas, pincel);



        //necesario para actualizar los dibujos en animaciones
        invalidate();

    }

}
