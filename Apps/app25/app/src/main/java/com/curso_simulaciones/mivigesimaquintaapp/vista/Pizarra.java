package com.curso_simulaciones.mivigesimaquintaapp.vista;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.view.View;
import com.curso_simulaciones.simulphysics.objetos_laboratorio.ObjetoLaboratorio;

/**
 * Vista personalizada para renderizar la escena de simulación
 */
public class Pizarra extends View {

    private ObjetoLaboratorio[] estadoEscena;
    private Paint pincel;

    public Pizarra(Context context) {
        super(context);
        // Inicializar el pincel
        pincel = new Paint();
        pincel.setAntiAlias(true);
    }

    /**
     * Establece el estado actual de la escena con todos los objetos
     * @param objetos Array de objetos de laboratorio a dibujar
     */
    public void setEstadoEscena(ObjetoLaboratorio[] objetos) {
        this.estadoEscena = objetos;
        invalidate(); // Solicita redibujado
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        // Dibujar todos los objetos de la escena
        if (estadoEscena != null) {
            for (ObjetoLaboratorio objeto : estadoEscena) {
                if (objeto != null) {
                    objeto.dibujese(canvas, pincel);
                }
            }
        }
    }

    /**
     * Actualiza la escena (redibuja)
     */
    public void actualizar() {
        invalidate();
    }
}