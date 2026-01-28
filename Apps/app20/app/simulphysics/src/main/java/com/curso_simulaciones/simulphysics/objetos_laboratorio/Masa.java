package com.curso_simulaciones.simulphysics.objetos_laboratorio;

import android.graphics.Canvas;
import android.graphics.Paint;

public class Masa extends CuerpoRectangular {

    private String marca;
    private int colorMarca;

    public Masa(float posicionCentroMasaX, float posicionCentroMasaY, float largo, float alto) {
        super(posicionCentroMasaX, posicionCentroMasaY, largo, alto);
    }

    public void setMarca(String marca) {
        this.marca = marca;
    }

    public void setColorMarca(int colorMarca) {
        this.colorMarca = colorMarca;
    }

    //se implementó este método que en su clase madre es abstracto
    public void dibujese(Canvas canvas, Paint pincel) {
        super.dibujese(canvas, pincel);
        canvas.save();
        canvas.rotate(posicionAngularRotacionEjeXY, posicionEjeRotacionX, posicionEjeRotacionY);
        pincel.setStyle(Paint.Style.FILL);
        if (marca != null) {
            pincel.setColor(colorMarca);
            // dibujar la marca como texto centrado (implementación simple)
            pincel.setTextSize(Math.min(largo, alto) * 0.2f);
            float textX = posicionCentroMasaX - (pincel.measureText(marca) / 2f);
            float textY = posicionCentroMasaY + (pincel.getTextSize() / 2f);
            canvas.drawText(marca, textX, textY, pincel);
        }
        canvas.restore();
        pincel.reset();
    }
}