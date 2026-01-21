package com.curso_simulaciones.midecimaterceraapp.objetos_laboratorio;

import android.graphics.Canvas;
import android.graphics.Paint;

/**
 * Resorte: representación simple tipo zig-zag entre dos puntos.
 */
public class Resorte extends ObjetoLaboratorio {

    private float x1, y1, x2, y2;
    private int segmentos = 8;
    private float grosor = 3f;

    public Resorte() {
        this(0,0,100,0);
    }

    public Resorte(float x1, float y1, float x2, float y2) {
        this.x1 = x1; this.y1 = y1; this.x2 = x2; this.y2 = y2;
    }

    public void setSegmentos(int s) { this.segmentos = Math.max(2, s); }
    public void setGrosor(float g) { this.grosor = g; }

    @Override
    public void dibujese(Canvas canvas, Paint pincel) {
        pincel.setStyle(Paint.Style.STROKE);
        pincel.setStrokeWidth(grosor);

        canvas.save();

        float dx = (x2 - x1) / segmentos;
        float dy = (y2 - y1) / segmentos;

        float px = x1, py = y1;
        for (int i = 1; i <= segmentos; i++) {
            float nx = x1 + dx * i;
            float ny = y1 + dy * i;
            // offset alternado para zig-zag
            float offset = (i % 2 == 0) ? 8f : -8f;
            // desplazar perpendicular simple
            float perpX = -dy;
            float perpY = dx;
            float norm = (float)Math.hypot(perpX, perpY);
            if (norm > 0.001f) {
                perpX = perpX / norm * offset;
                perpY = perpY / norm * offset;
            }
            canvas.drawLine(px, py, nx + perpX, ny + perpY, pincel);
            px = nx + perpX;
            py = ny + perpY;
        }

        canvas.restore();
    }
}
