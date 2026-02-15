package com.curso_simulaciones.mivigesimanovenaapp.utilidades;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.widget.ImageButton;

public class Boton extends androidx.appcompat.widget.AppCompatImageButton {

    public Boton(Context context) {
        super(context);
        this.setScaleType(ScaleType.FIT_CENTER);
    }

    public void setImagen(int idImagen) {
        try {
            Drawable imagen = getResources().getDrawable(idImagen);
            this.setImageDrawable(imagen);
            this.setBackgroundDrawable(null);
        } catch (Exception e) {
            // Si no se encuentra la imagen, dejar el botón sin imagen
            e.printStackTrace();
        }
    }
}