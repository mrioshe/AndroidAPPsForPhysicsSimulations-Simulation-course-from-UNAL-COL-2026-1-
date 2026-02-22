package com.curso_simulaciones.mitrigesimacuartaapp.utilidades;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.view.View;

public class GaugeSimple extends View {
    private float largo;
    private float minimo = 0;
    private float maximo = 100f;
    private float medida = 0.0f;
    private String unidades = "UNIDADES";
    private int colorPrimerTercio = Color.rgb(200, 200, 0);
    private int colorSegundoTercio = Color.rgb(0, 180, 0);
    private int colorTercerTercio = Color.RED;
    private int colorLineas = Color.BLACK;
    private int colorFondo = Color.WHITE;
    private int colorNumerosDespliegue = Color.BLACK;
    private int colorFranjaDinamica = Color.rgb(0, 0, 255);
    private int angPrimerTercio = 100;
    private int angSegundoTercio = 100;
    private int angTercerTercio = 50;

    public GaugeSimple(Context context) {
        super(context);
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.HONEYCOMB) {
            this.setLayerType(View.LAYER_TYPE_SOFTWARE, null);
        }
    }

    public void setRango(float minimo, float maximo) {
        this.minimo = minimo;
        this.maximo = maximo;
    }

    public void setMedida(float medida) {
        this.medida = medida;
    }

    public float getMedida() {
        return medida;
    }

    public void setUnidades(String unidades) {
        this.unidades = unidades;
    }

    public void setColorSectores(int colorPrimerTercio, int colorSegundoTercio, int colorTercerTercio) {
        this.colorPrimerTercio = colorPrimerTercio;
        this.colorSegundoTercio = colorSegundoTercio;
        this.colorTercerTercio = colorTercerTercio;
    }

    public void setAngulosSectores(int angPrimerTercio, int angSegundoTercio, int angTercerTercio) {
        this.angPrimerTercio = angPrimerTercio;
        this.angSegundoTercio = angSegundoTercio;
        this.angTercerTercio = angTercerTercio;
    }

    public void setColorFranjaDinámica(int colorFranjaDinamica) {
        this.colorFranjaDinamica = colorFranjaDinamica;
    }

    public void setColorFondoTacometro(int colorFondo) {
        this.colorFondo = colorFondo;
    }

    public void setColorLineasTacometro(int colorLineas) {
        this.colorLineas = colorLineas;
    }

    public void setColorNumeroDespliegue(int colorNumerosDespliegue) {
        this.colorNumerosDespliegue = colorNumerosDespliegue;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        canvas.save();

        float ancho = this.getWidth();
        float alto = this.getHeight();

        if (ancho > alto) {
            largo = 0.8f * alto;
        } else {
            largo = 0.8f * ancho;
        }

        canvas.translate(0.5f * ancho, 0.5f * alto);

        Paint pincel = new Paint();
        pincel.setAntiAlias(true);
        pincel.setTextSize(0.05f * largo);
        pincel.setLinearText(true);
        pincel.setFilterBitmap(true);
        pincel.setDither(true);

        float esquinaSuperiorIzquierdaX = -0.5f * largo;
        float esquinaSuperiorIzquierdaY = -0.5f * largo;
        float esquinaInferiorDerechaX = 0.5f * largo;
        float esquinaInferiorDerechaY = 0.5f * largo;

        // Dibujar los tres segmentos circulares
        RectF rect = new RectF(esquinaSuperiorIzquierdaX, esquinaSuperiorIzquierdaY,
                esquinaInferiorDerechaX, esquinaInferiorDerechaY);

        pincel.setColor(colorPrimerTercio);
        canvas.drawArc(rect, 145, angPrimerTercio, true, pincel);

        pincel.setColor(colorSegundoTercio);
        canvas.drawArc(rect, 145 + angPrimerTercio, angSegundoTercio, true, pincel);

        pincel.setColor(colorTercerTercio);
        canvas.drawArc(rect, 145 + angPrimerTercio + angSegundoTercio, angTercerTercio, true, pincel);

        float indent = (float) (0.05 * largo);
        float posicionY = (float) (0.5 * largo);

        // Franja dinámica
        float anguloRotacionMedida = 235 + (250f / (maximo - minimo)) * (medida - minimo);
        float a = (float) 0.01 * largo;

        rect = new RectF(esquinaSuperiorIzquierdaX + a, esquinaSuperiorIzquierdaY + a,
                esquinaInferiorDerechaX - a, esquinaInferiorDerechaY - a);

        pincel.setColor(colorFranjaDinamica);
        canvas.drawArc(rect, 145, anguloRotacionMedida - 235, true, pincel);

        // Dibujar el tacómetro sin la aguja
        float radio = (float) (0.48 * largo);
        pincel.setColor(colorFondo);
        canvas.drawCircle(0, 0, radio, pincel);

        pincel.setColor(colorLineas);
        pincel.setStyle(Paint.Style.STROKE);
        canvas.drawCircle(0, 0, radio, pincel);
        pincel.setStrokeWidth(1f);
        canvas.drawCircle(0, 0, 0.5f * largo, pincel);

        pincel.setStrokeWidth(0.01f * largo);

        // Divisiones grandes
        pincel.setStyle(Paint.Style.FILL);
        for (int i = 0; i < 6; i = i + 1) {
            float anguloRotacion = 235 + 50 * i;
            canvas.rotate(anguloRotacion, 0, 0);
            canvas.drawLine(0, -posicionY, 0, -posicionY + indent, pincel);
            canvas.rotate(-anguloRotacion, 0, 0);

            // Dibujar los números
            int valorIncrementoMarcas = (int) ((maximo - minimo) / 5f);
            int valorMarca = (int) (minimo + valorIncrementoMarcas * i);
            String numero = "" + valorMarca;
            float anchoCadenaNumero = pincel.measureText(numero);

            canvas.save();
            canvas.rotate(anguloRotacion, 0, 0);
            canvas.rotate(-anguloRotacion, 0, -posicionY + 2.5f * indent);
            canvas.drawText(numero, -0.5f * anchoCadenaNumero, -posicionY + 2.5f * indent, pincel);
            canvas.restore();
        }

        // Divisiones pequeñas
        pincel.setStyle(Paint.Style.STROKE);
        pincel.setStrokeWidth(0.005f * largo);

        for (int i = 0; i < 26; i = i + 1) {
            float anguloRotacion = 235 + 10 * i;
            canvas.rotate(anguloRotacion, 0, 0);
            canvas.drawLine(0, -posicionY, 0, -posicionY + (float) (0.6 * indent), pincel);
            canvas.rotate(-anguloRotacion, 0, 0);
        }

        // Dibujar la aguja
        pincel.setStrokeWidth(0.005f * largo);
        pincel.setColor(Color.RED);
        canvas.rotate(anguloRotacionMedida, 0, 0);
        float b = (float) (1.5f * indent);
        canvas.drawLine(0, -posicionY, 0, b, pincel);
        canvas.rotate(-anguloRotacionMedida, 0, 0);

        pincel.setStyle(Paint.Style.FILL);
        pincel.setColor(colorFondo);
        canvas.drawCircle(0, 0, (float) (0.4 * indent), pincel);

        pincel.setColor(Color.RED);
        pincel.setStyle(Paint.Style.STROKE);
        canvas.drawCircle(0, 0, (float) (0.4 * indent), pincel);

        // Dibujar las unidades
        pincel.setStyle(Paint.Style.FILL);
        pincel.setColor(colorLineas);
        pincel.setTextSize(0.08f * largo);
        float anchoCadenaUnidades = pincel.measureText(unidades);
        canvas.drawText(unidades, -0.5f * anchoCadenaUnidades, -0.15f * largo, pincel);

        // Despliegue de la medida
        pincel.setTextSize(0.1f * largo);
        float anchoCadenaNumero = pincel.measureText("" + medida);
        pincel.setColor(colorNumerosDespliegue);
        canvas.drawText("" + medida, -0.5f * anchoCadenaNumero, 0.2f * largo, pincel);

        // Marcar empresa
        String empresa = "IoT.PhysicsSensor";
        pincel.setTextSize(0.05f * largo);
        float anchoCadenaNombreEmpresa = pincel.measureText(empresa);
        canvas.drawText(empresa, -0.5f * anchoCadenaNombreEmpresa, 0.35f * largo, pincel);

        canvas.restore();
        invalidate();
    }
}