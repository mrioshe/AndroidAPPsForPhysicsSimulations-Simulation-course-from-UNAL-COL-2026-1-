package com.curso_simulaciones.mitrigesimacuartaapp.utilidades;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.widget.ImageView;

public class Boton extends androidx.appcompat.widget.AppCompatImageView {
    private Bitmap imagen;
    private Bitmap imagenEscalada = null;
    private float tamanoLetra;
    private String cadena = "";

    public Boton(Context context) {
        super(context);
    }

    public void setText(String cadena) {
        this.cadena = cadena;
    }

    public String getText() {
        return cadena;
    }

    public void setImagen(int imagenImportada) {
        imagen = BitmapFactory.decodeResource(getResources(), imagenImportada);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        Paint pincel = new Paint();
        pincel.setAntiAlias(true);
        pincel.setLinearText(true);

        float ancho = getWidth();
        float alto = getHeight();
        float escala = Math.min(ancho, alto);

        if (imagen != null) {
            imagenEscalada = escalarImagen(imagen, (int) (0.9f * escala), (int) (0.9f * escala));
        }

        if (imagenEscalada != null) {
            int ex = (getWidth() - imagenEscalada.getWidth()) / 2;
            int ey = (getHeight() - imagenEscalada.getHeight()) / 2;

            tamanoLetra = 0.08f * getWidth();
            canvas.drawBitmap(imagenEscalada, ex, ey, null);

            pincel.setTextSize(tamanoLetra);
            float anchoCadenaUnidades = pincel.measureText(cadena);
            float posicionXLetra = (getWidth() - anchoCadenaUnidades) / 2;
            float posicionYLetra = ey + imagenEscalada.getHeight() + tamanoLetra;
            pincel.setColor(Color.BLACK);
            canvas.drawText(cadena, posicionXLetra, posicionYLetra, pincel);
        }

        invalidate();
    }

    /**
     * Se escala con Matrix para no dañar la resolución
     */
    public Bitmap escalarImagen(Bitmap bitmap, int newWidth, int newHeight) {
        if (bitmap == null) return null;

        Bitmap scaledBitmap = Bitmap.createBitmap(newWidth, newHeight, bitmap.getConfig());

        float scaleX = newWidth / (float) bitmap.getWidth();
        float scaleY = newHeight / (float) bitmap.getHeight();

        Matrix scaleMatrix = new Matrix();
        scaleMatrix.setScale(scaleX, scaleY, 0, 0);

        Canvas canvas = new Canvas(scaledBitmap);
        canvas.setMatrix(scaleMatrix);

        Paint paint = new Paint(Paint.FILTER_BITMAP_FLAG);
        paint.setAntiAlias(true);
        paint.setDither(true);
        paint.setFilterBitmap(true);

        canvas.drawBitmap(bitmap, 0, 0, paint);

        return scaledBitmap;
    }
}