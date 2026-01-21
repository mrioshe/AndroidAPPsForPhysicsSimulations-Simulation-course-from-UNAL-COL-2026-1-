package com.curso_simulaciones.midecimaprimeraapp.vista; // reemplaza por el paquete real de tu proyecto

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

import com.curso_simulaciones.midecimaprimeraapp.objetos_laboratorio.CuerpoRigido;

/**
 * Vista Pizarra para dibujar un arreglo de CuerpoRigido.
 * Se asume que CuerpoRigido define el método dibujese(Canvas, Paint).
 */
public class Pizarra extends View {

    private CuerpoRigido[] cuerpos;

    /**
     * Constructor usado desde código
     *
     * @param context
     */
    public Pizarra(Context context) {
        super(context);
    }

    /**
     * Constructor usado por el sistema al inflar desde XML (opcional)
     *
     * @param context
     * @param attrs
     */
    public Pizarra(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    /**
     * Establece el estado de la escena con un arreglo de cuerpos rígidos.
     *
     * @param cuerpos arreglo de CuerpoRigido (puede contener nulls)
     */
    public void setEstadoEscena(CuerpoRigido[] cuerpos) {
        this.cuerpos = cuerpos;
    }

    // Método para dibujar la escena
    private void dibujarEscena(Canvas canvas, Paint pincel) {
        if (cuerpos == null) return;

        for (int i = 0; i < cuerpos.length; i++) {
            if (cuerpos[i] != null) {
                cuerpos[i].dibujese(canvas, pincel);
            }
        }
    }

    // método para dibujar
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        Paint pincel = new Paint();
        // evita efecto sierra
        pincel.setAntiAlias(true);

        if (cuerpos != null) {
            dibujarEscena(canvas, pincel);
        }

        // necesario para actualizar los dibujos en animaciones
        invalidate();
    }
}