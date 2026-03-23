package com.curso_simulaciones.micuadragesimasegundaapp.utilidades;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.widget.ImageView;

/**
 * ImageView personalizado que escala su imagen con Matrix (sin pérdida de
 * resolución) y dibuja una etiqueta de texto debajo de la imagen.
 */
public class Boton extends androidx.appcompat.widget.AppCompatImageView {

    private Bitmap imagen;
    private Bitmap imagen_escalada = null;
    private float  tamamano_letra;
    private String cadena = "";

    public Boton(Context context) { super(context); }

    public void setText(String cadena)  { this.cadena = cadena; }

    public String getText() {
        this.cadena = cadena;
        return cadena;
    }

    public void setImagen(int imagen_importada) {
        imagen = BitmapFactory.decodeResource(getResources(), imagen_importada);
    }

    @Override
    protected void onDraw(Canvas canvas) {

        Paint pincel = new Paint();
        pincel.setAntiAlias(true);
        pincel.setLinearText(true);

        float ancho  = getWidth();
        float alto   = getHeight();
        float escala = (ancho > alto) ? alto : ancho;

        if (imagen != null) {
            imagen_escalada = escalarImagen(imagen, (int)(0.9f * escala), (int)(0.9f * escala));
        }
        if (imagen_escalada == null) return;

        int ex = (getWidth()  - imagen_escalada.getWidth())  / 2;
        int ey = (getHeight() - imagen_escalada.getHeight()) / 2;

        tamamano_letra = 0.08f * getWidth();
        canvas.drawBitmap(imagen_escalada, ex, ey, null);

        pincel.setTextSize(tamamano_letra);
        float wText = pincel.measureText(cadena);
        pincel.setColor(Color.BLACK);
        canvas.drawText(cadena,
                (getWidth() - wText) / 2f,
                ey + imagen_escalada.getHeight() + tamamano_letra,
                pincel);

        invalidate();
    }

    /** Escala con Matrix para no degradar la resolución. */
    public Bitmap escalarImagen(Bitmap bitmap, int newWidth, int newHeight) {
        Bitmap scaledBitmap = Bitmap.createBitmap(newWidth, newHeight, bitmap.getConfig());
        float scaleX = newWidth  / (float) bitmap.getWidth();
        float scaleY = newHeight / (float) bitmap.getHeight();
        Matrix m = new Matrix();
        m.setScale(scaleX, scaleY, 0, 0);
        Canvas c = new Canvas(scaledBitmap);
        c.setMatrix(m);
        Paint p = new Paint(Paint.FILTER_BITMAP_FLAG);
        p.setAntiAlias(true);
        p.setDither(true);
        p.setFilterBitmap(true);
        c.drawBitmap(bitmap, 0, 0, p);
        return scaledBitmap;
    }
}