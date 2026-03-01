package com.curso_simulaciones.mitrigesimasextaapp.utilidades;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.view.View;

/**
 * Componente visual tipo tacómetro (gauge).
 * Dibuja sectores de color, escala numérica, franja dinámica y aguja animada.
 */
public class GaugeSimple extends View {

    private float  largo;
    private float  minimo  = 0;
    private float  maximo  = 100f;
    private float  medida  = 40.0f;
    private String unidades = "UNIDADES";

    private int colorPrimerTercio       = Color.rgb(200, 200, 0);
    private int colorSegundoTercio      = Color.rgb(0, 180, 0);
    private int colorTercerTercio       = Color.RED;
    private int colorLineas             = Color.BLACK;
    private int colorFondo              = Color.WHITE;
    private int colorNumerosDespliegue  = Color.BLACK;
    private int colorFranjaDinamica     = Color.rgb(0, 0, 255);

    private int angPrimertercio  = 100;
    private int angSegundoTercio = 100;
    private int angTercerTercio  =  50;

    public GaugeSimple(Context context) {
        super(context);
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.HONEYCOMB) {
            setLayerType(View.LAYER_TYPE_SOFTWARE, null);
        }
    }

    // ── Setters / Getters ─────────────────────────────────────────────────────

    public void setRango(float minimo, float maximo) {
        this.minimo = minimo;
        this.maximo = maximo;
    }

    public void setMedida(float medida)  { this.medida  = medida; }
    public float getMedida()             { return medida; }
    public void setUnidades(String u)    { this.unidades = u; }

    public void setColorSectores(int c1, int c2, int c3) {
        this.colorPrimerTercio  = c1;
        this.colorSegundoTercio = c2;
        this.colorTercerTercio  = c3;
    }

    public void setAngulosSectores(int a1, int a2, int a3) {
        this.angPrimertercio  = a1;
        this.angSegundoTercio = a2;
        this.angTercerTercio  = a3;
    }

    public void setColorFranjaDinámica(int c)       { this.colorFranjaDinamica    = c; }
    public void setColorFondoTacometro(int c)       { this.colorFondo             = c; }
    public void setColorLineasTacometro(int c)      { this.colorLineas            = c; }
    public void setColorNumeroDespliegue(int c)     { this.colorNumerosDespliegue = c; }

    // ── Dibujo ────────────────────────────────────────────────────────────────

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.save();

        float ancho = getWidth();
        float alto  = getHeight();
        largo = (ancho > alto) ? 0.8f * alto : 0.8f * ancho;

        canvas.translate(0.5f * ancho, 0.5f * alto);

        Paint pincel = new Paint();
        pincel.setAntiAlias(true);
        pincel.setTextSize(0.05f * largo);
        pincel.setLinearText(true);
        pincel.setFilterBitmap(true);
        pincel.setDither(true);

        final float H = 0.5f * largo;
        RectF rect = new RectF(-H, -H, H, H);

        // Sectores
        pincel.setColor(colorPrimerTercio);
        canvas.drawArc(rect, 145, angPrimertercio, true, pincel);
        pincel.setColor(colorSegundoTercio);
        canvas.drawArc(rect, 145 + angPrimertercio, angSegundoTercio, true, pincel);
        pincel.setColor(colorTercerTercio);
        canvas.drawArc(rect, 145 + angPrimertercio + angSegundoTercio, angTercerTercio, true, pincel);

        float indent   = 0.05f * largo;
        float posY     = 0.50f * largo;
        float angMedida = 235 + (250f / (maximo - minimo)) * (medida - minimo);

        // Franja dinámica
        float a = 0.01f * largo;
        rect = new RectF(-H + a, -H + a, H - a, H - a);
        pincel.setColor(colorFranjaDinamica);
        canvas.drawArc(rect, 145, angMedida - 235, true, pincel);

        // Fondo circular
        pincel.setColor(colorFondo);
        canvas.drawCircle(0, 0, 0.48f * largo, pincel);
        pincel.setColor(colorLineas);
        pincel.setStyle(Paint.Style.STROKE);
        canvas.drawCircle(0, 0, 0.48f * largo, pincel);
        pincel.setStrokeWidth(0.01f * largo);

        // Divisiones grandes + números
        pincel.setStyle(Paint.Style.FILL);
        for (int i = 0; i < 6; i++) {
            float ang = 235 + 50 * i;
            canvas.rotate(ang, 0, 0);
            canvas.drawLine(0, -posY, 0, -posY + indent, pincel);
            canvas.rotate(-ang, 0, 0);

            int val = (int)(minimo + ((maximo - minimo) / 5f) * i);
            String num = "" + val;
            float wNum = pincel.measureText(num);
            canvas.save();
            canvas.rotate(ang, 0, 0);
            canvas.rotate(-ang, 0, -posY + 2.5f * indent);
            canvas.drawText(num, -0.5f * wNum, -posY + 2.5f * indent, pincel);
            canvas.restore();
        }

        // Divisiones pequeñas
        pincel.setStyle(Paint.Style.STROKE);
        pincel.setStrokeWidth(0.005f * largo);
        for (int i = 0; i < 26; i++) {
            float ang = 235 + 10 * i;
            canvas.rotate(ang, 0, 0);
            canvas.drawLine(0, -posY, 0, -posY + 0.6f * indent, pincel);
            canvas.rotate(-ang, 0, 0);
        }

        // Aguja
        pincel.setStrokeWidth(0.005f * largo);
        pincel.setColor(Color.RED);
        canvas.rotate(angMedida, 0, 0);
        canvas.drawLine(0, -posY, 0, 1.5f * indent, pincel);
        canvas.rotate(-angMedida, 0, 0);
        pincel.setStyle(Paint.Style.FILL);
        pincel.setColor(colorFondo);
        canvas.drawCircle(0, 0, 0.4f * indent, pincel);
        pincel.setColor(Color.RED);
        pincel.setStyle(Paint.Style.STROKE);
        canvas.drawCircle(0, 0, 0.4f * indent, pincel);

        // Unidades
        pincel.setStyle(Paint.Style.FILL);
        pincel.setColor(colorLineas);
        pincel.setTextSize(0.08f * largo);
        canvas.drawText(unidades, -0.5f * pincel.measureText(unidades), -0.15f * largo, pincel);

        // Valor numérico
        pincel.setTextSize(0.1f * largo);
        pincel.setColor(colorNumerosDespliegue);
        canvas.drawText("" + medida, -0.5f * pincel.measureText("" + medida), 0.20f * largo, pincel);

        // Marca empresa
        String empresa = "IoT.PhysicsSensor";
        pincel.setTextSize(0.05f * largo);
        canvas.drawText(empresa, -0.5f * pincel.measureText(empresa), 0.35f * largo, pincel);

        canvas.restore();
        invalidate();
    }
}