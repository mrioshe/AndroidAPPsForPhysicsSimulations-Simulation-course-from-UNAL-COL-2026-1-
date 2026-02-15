package com.curso_simulaciones.mivigesimanovenaapp.utilidades;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RadialGradient;
import android.graphics.RectF;
import android.graphics.Shader;
import android.util.DisplayMetrics;
import android.view.View;

public class GaugeSimple extends View {
    protected float medida = 0;
    protected float rangoMinimo = -4000;
    protected float rangoMaximo = 4000;
    protected String unidades = " µT";

    // Colores aesthetic modernos
    private int colorFondo = Color.rgb(20, 20, 30);
    private int colorAroExterior = Color.rgb(60, 60, 80);
    private int colorZonaSegura = Color.rgb(100, 200, 100);
    private int colorZonaAdvertencia = Color.rgb(255, 200, 50);
    private int colorZonaPeligro = Color.rgb(255, 80, 80);
    private int colorAguja = Color.rgb(255, 255, 255);
    private int colorTexto = Color.rgb(240, 240, 255);
    private int colorVisorNumerico = Color.rgb(0, 255, 200);

    // Parámetros de resolución
    protected int tamanoLetraResolucionIncluida;
    private float centroX, centroY, radio;

    public GaugeSimple(Context context) {
        super(context);
        gestionarResolucion(context);
    }

    private void gestionarResolucion(Context context) {
        DisplayMetrics displayMetrics = context.getResources().getDisplayMetrics();
        int alto = displayMetrics.heightPixels;
        int ancho = displayMetrics.widthPixels;

        int dimensionReferencia = Math.min(alto, ancho);
        int tamanoLetra = dimensionReferencia / 28;
        tamanoLetraResolucionIncluida = (int) (tamanoLetra / displayMetrics.scaledDensity);
    }

    public void setMedida(float medida) {
        this.medida = medida;
        invalidate();
    }

    public void setRango(float minimo, float maximo) {
        this.rangoMinimo = minimo;
        this.rangoMaximo = maximo;
        invalidate();
    }

    public void setUnidades(String unidades) {
        this.unidades = unidades;
        invalidate();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        int ancho = getWidth();
        int alto = getHeight();

        centroX = ancho / 2.0f;
        centroY = alto / 2.0f;
        radio = Math.min(ancho, alto) * 0.40f;

        // Dibujar fondo con gradiente radial
        dibujarFondoGradiente(canvas);

        // Dibujar aro exterior fino
        dibujarAroExterior(canvas);

        // Dibujar sectores cromáticos
        dibujarSectores(canvas);

        // Dibujar marcas de escala
        dibujarMarcasEscala(canvas);

        // Dibujar etiquetas de valores
        dibujarEtiquetasValores(canvas);

        // Dibujar aguja
        dibujarAguja(canvas);

        // Dibujar visor numérico centrado
        dibujarVisorNumerico(canvas);
    }

    private void dibujarFondoGradiente(Canvas canvas) {
        Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);

        RadialGradient gradient = new RadialGradient(
                centroX, centroY, radio * 1.1f,
                new int[]{Color.rgb(40, 40, 55), Color.rgb(15, 15, 25), Color.rgb(10, 10, 20)},
                new float[]{0.0f, 0.7f, 1.0f},
                Shader.TileMode.CLAMP
        );

        paint.setShader(gradient);
        canvas.drawCircle(centroX, centroY, radio * 1.05f, paint);
    }

    private void dibujarAroExterior(Canvas canvas) {
        Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(3);
        paint.setColor(colorAroExterior);

        canvas.drawCircle(centroX, centroY, radio, paint);

        // Aro interior delgado
        paint.setStrokeWidth(1.5f);
        paint.setColor(Color.rgb(80, 80, 100));
        canvas.drawCircle(centroX, centroY, radio * 0.95f, paint);
    }

    private void dibujarSectores(Canvas canvas) {
        Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(radio * 0.15f);

        RectF rect = new RectF(
                centroX - radio * 0.85f,
                centroY - radio * 0.85f,
                centroX + radio * 0.85f,
                centroY + radio * 0.85f
        );

        // Ángulo inicial y barrido (180° en la parte inferior)
        float anguloInicial = 180;
        float anguloBarrido = 180;

        // Zona segura: -1000 a 1000 (centro 60%)
        paint.setColor(colorZonaSegura);
        paint.setAlpha(100);
        canvas.drawArc(rect, anguloInicial + anguloBarrido * 0.2f, anguloBarrido * 0.6f, false, paint);

        // Zona advertencia izquierda: -4000 a -1000 (20%)
        paint.setColor(colorZonaAdvertencia);
        paint.setAlpha(100);
        canvas.drawArc(rect, anguloInicial, anguloBarrido * 0.2f, false, paint);

        // Zona advertencia derecha: 1000 a 4000 (20%)
        canvas.drawArc(rect, anguloInicial + anguloBarrido * 0.8f, anguloBarrido * 0.2f, false, paint);

        // Zona peligro extremos (opcional, overlay)
        paint.setColor(colorZonaPeligro);
        paint.setAlpha(50);
        paint.setStrokeWidth(radio * 0.05f);
        canvas.drawArc(rect, anguloInicial, anguloBarrido * 0.05f, false, paint);
        canvas.drawArc(rect, anguloInicial + anguloBarrido * 0.95f, anguloBarrido * 0.05f, false, paint);
    }

    private void dibujarMarcasEscala(Canvas canvas) {
        Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setColor(Color.rgb(150, 150, 170));
        paint.setStrokeWidth(2);

        // Dibujar 9 marcas principales
        for (int i = 0; i <= 8; i++) {
            float angulo = 180 + (i * 180.0f / 8);
            float anguloRad = (float) Math.toRadians(angulo);

            float x1 = centroX + (float) (Math.cos(anguloRad) * radio * 0.90);
            float y1 = centroY + (float) (Math.sin(anguloRad) * radio * 0.90);
            float x2 = centroX + (float) (Math.cos(anguloRad) * radio * 0.95);
            float y2 = centroY + (float) (Math.sin(anguloRad) * radio * 0.95);

            if (i == 0 || i == 4 || i == 8) {
                paint.setStrokeWidth(3);
            } else {
                paint.setStrokeWidth(2);
            }

            canvas.drawLine(x1, y1, x2, y2, paint);
        }
    }

    private void dibujarEtiquetasValores(Canvas canvas) {
        Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setColor(colorTexto);
        paint.setTextSize(tamanoLetraResolucionIncluida * 0.8f);
        paint.setTextAlign(Paint.Align.CENTER);

        // Etiquetas en los extremos y centro
        String[] etiquetas = {
                String.format("%.0f", rangoMinimo),
                "0",
                String.format("%.0f", rangoMaximo)
        };

        float[] angulos = {180, 270, 360};

        for (int i = 0; i < etiquetas.length; i++) {
            float anguloRad = (float) Math.toRadians(angulos[i]);
            float x = centroX + (float) (Math.cos(anguloRad) * radio * 0.75);
            float y = centroY + (float) (Math.sin(anguloRad) * radio * 0.75);

            canvas.drawText(etiquetas[i], x, y + paint.getTextSize() * 0.3f, paint);
        }
    }

    private void dibujarAguja(Canvas canvas) {
        // Calcular ángulo de la aguja según la medida
        float porcentaje = (medida - rangoMinimo) / (rangoMaximo - rangoMinimo);
        porcentaje = Math.max(0, Math.min(1, porcentaje));

        float anguloAguja = 180 + (porcentaje * 180);
        float anguloRad = (float) Math.toRadians(anguloAguja);

        Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setStyle(Paint.Style.FILL);

        // Dibujar aguja como triángulo
        Path path = new Path();

        float longitudAguja = radio * 0.75f;
        float anchoBase = radio * 0.04f;

        float puntoX = centroX + (float) (Math.cos(anguloRad) * longitudAguja);
        float puntoY = centroY + (float) (Math.sin(anguloRad) * longitudAguja);

        float anguloPerp = anguloRad + (float) Math.PI / 2;

        float base1X = centroX + (float) (Math.cos(anguloPerp) * anchoBase);
        float base1Y = centroY + (float) (Math.sin(anguloPerp) * anchoBase);

        float base2X = centroX - (float) (Math.cos(anguloPerp) * anchoBase);
        float base2Y = centroY - (float) (Math.sin(anguloPerp) * anchoBase);

        path.moveTo(puntoX, puntoY);
        path.lineTo(base1X, base1Y);
        path.lineTo(base2X, base2Y);
        path.close();

        // Sombra de la aguja
        paint.setColor(Color.rgb(0, 0, 0));
        paint.setAlpha(80);
        canvas.drawPath(path, paint);

        // Aguja principal
        paint.setColor(colorAguja);
        paint.setAlpha(255);
        canvas.drawPath(path, paint);

        // Centro decorativo
        paint.setStyle(Paint.Style.FILL);
        paint.setColor(Color.rgb(200, 200, 220));
        canvas.drawCircle(centroX, centroY, radio * 0.08f, paint);

        paint.setColor(Color.rgb(100, 100, 120));
        canvas.drawCircle(centroX, centroY, radio * 0.05f, paint);
    }

    private void dibujarVisorNumerico(Canvas canvas) {
        Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);

        // Fondo del visor
        paint.setStyle(Paint.Style.FILL);
        paint.setColor(Color.rgb(30, 30, 45));

        float visorAncho = radio * 0.9f;
        float visorAlto = radio * 0.35f;
        float visorY = centroY + radio * 0.4f;

        RectF rectVisor = new RectF(
                centroX - visorAncho / 2,
                visorY - visorAlto / 2,
                centroX + visorAncho / 2,
                visorY + visorAlto / 2
        );

        canvas.drawRoundRect(rectVisor, 15, 15, paint);

        // Borde del visor
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(2);
        paint.setColor(Color.rgb(80, 80, 100));
        canvas.drawRoundRect(rectVisor, 15, 15, paint);

        // Valor numérico
        paint.setStyle(Paint.Style.FILL);
        paint.setColor(colorVisorNumerico);
        paint.setTextSize(tamanoLetraResolucionIncluida * 1.4f);
        paint.setTextAlign(Paint.Align.CENTER);
        paint.setFakeBoldText(true);

        String valorTexto = String.format("%.1f", medida);
        canvas.drawText(valorTexto, centroX, visorY + paint.getTextSize() * 0.15f, paint);

        // Unidades
        paint.setTextSize(tamanoLetraResolucionIncluida * 0.7f);
        paint.setColor(Color.rgb(180, 180, 200));
        paint.setFakeBoldText(false);
        canvas.drawText(unidades, centroX, visorY + visorAlto * 0.35f + paint.getTextSize(), paint);
    }
}