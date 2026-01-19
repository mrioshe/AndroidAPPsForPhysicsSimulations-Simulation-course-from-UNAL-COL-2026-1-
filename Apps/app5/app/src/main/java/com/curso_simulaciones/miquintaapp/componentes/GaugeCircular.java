package com.curso_simulaciones.miquintaapp.componentes;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.os.Build;
import android.view.View;

public class GaugeCircular extends View {
    private float largo;
    private float minimo = -20f;
    private float maximo = 40f;
    private float medida = 32.0f;
    private String unidades = "Gauss";

    // Colores de los sectores del arco (270 grados divididos)
    private int colorSector1 = Color.BLUE;      // primer sector (ej. 180°)
    private int colorSector2 = Color.YELLOW;    // segundo sector (45°)
    private int colorSector3 = Color.GREEN;     // tercer sector (45°)

    private int colorFondo = Color.BLACK;
    private int colorBorde = Color.RED;
    private int colorAguja = Color.RED;
    private int colorNumeros = Color.WHITE;
    private int colorEscala = Color.YELLOW;
    private int colorValorActual = Color.YELLOW;
    private int colorCero = Color.RED;

    // Ángulo inicial y barrido (Canvas: 0=3h, 90=6h; sweep positivo => sentido horario)
    private final float startAngle = 90f;   // empieza en abajo (6h)
    private final float sweepAngle = 270f;  // barrido horario (excluye 0..90 => cuadrante 4)

    public GaugeCircular(Context context) {
        super(context);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
            this.setLayerType(View.LAYER_TYPE_SOFTWARE, null);
        }
    }

    public void setRango(float minimo, float maximo) {
        this.minimo = minimo;
        this.maximo = maximo;
        invalidate();
    }

    public void setMedida(float medida) {
        this.medida = medida;
        invalidate();
    }

    public float getMedida() {
        return medida;
    }

    public void setUnidades(String unidades) {
        this.unidades = unidades;
        invalidate();
    }

    public void setColorSectores(int color1, int color2, int color3) {
        this.colorSector1 = color1;
        this.colorSector2 = color2;
        this.colorSector3 = color3;
        invalidate();
    }

    public void setColorFondo(int colorFondo) {
        this.colorFondo = colorFondo;
        invalidate();
    }

    public void setColorBorde(int colorBorde) {
        this.colorBorde = colorBorde;
        invalidate();
    }

    public void setColorAguja(int colorAguja) {
        this.colorAguja = colorAguja;
        invalidate();
    }

    public void setColorNumeros(int colorNumeros) {
        this.colorNumeros = colorNumeros;
        invalidate();
    }

    public void setColorEscala(int colorEscala) {
        this.colorEscala = colorEscala;
        invalidate();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.save();

        float ancho = this.getWidth();
        float alto = this.getHeight();

        // Determinar el tamaño del gauge (80% del menor lado)
        if (ancho > alto) {
            largo = 0.8f * alto;
        } else {
            largo = 0.8f * ancho;
        }

        // Trasladar al centro
        canvas.translate(0.5f * ancho, 0.5f * alto);

        Paint pincel = new Paint();
        pincel.setAntiAlias(true);
        pincel.setLinearText(true);
        pincel.setFilterBitmap(true);
        pincel.setDither(true);
        pincel.setTextAlign(Paint.Align.LEFT);

        float radioExterior = 0.45f * largo;

        // 1. CÍRCULO BASE
        pincel.setStyle(Paint.Style.FILL);
        pincel.setColor(colorFondo);
        canvas.drawCircle(0, 0, radioExterior, pincel);

        // 2. BORDE
        float grosorBorde = 0.02f * largo;
        pincel.setStyle(Paint.Style.STROKE);
        pincel.setStrokeWidth(grosorBorde);
        pincel.setColor(colorBorde);
        canvas.drawCircle(0, 0, radioExterior, pincel);

        // 3. ARCO DE COLORES (radio = 75% del radioExterior, grosor = grosorBorde)
        float radioArco = 0.75f * radioExterior;
        float grosorArco = grosorBorde;
        RectF rectArco = new RectF(-radioArco, -radioArco, radioArco, radioArco);

        pincel.setStyle(Paint.Style.STROKE);
        pincel.setStrokeWidth(grosorArco);
        pincel.setStrokeCap(Paint.Cap.BUTT);

        // Dividimos el sweep (270°) en 180°,45°,45° como en tu imagen
        float s1 = 180f;
        float s2 = 45f;
        float s3 = 45f;

        // Dibuja sector 1 (desde startAngle, sweep s1) — colorSector1
        pincel.setColor(colorSector1);
        canvas.drawArc(rectArco, startAngle, s1, false, pincel);

        // Sector 2 (startAngle + s1)
        pincel.setColor(colorSector2);
        canvas.drawArc(rectArco, startAngle + s1, s2, false, pincel);

        // Sector 3 (startAngle + s1 + s2)
        pincel.setColor(colorSector3);
        canvas.drawArc(rectArco, startAngle + s1 + s2, s3, false, pincel);

        // 4. MARCAS DE ESCALA (solo donde hay arco: desde startAngle hasta startAngle+sweepAngle)
        pincel.setStrokeWidth(0.004f * largo);
        pincel.setColor(colorEscala);
        pincel.setStrokeCap(Paint.Cap.ROUND);

        // Marcas principales (7 marcas distribuidas en los 270°)
        int numMarcasPrincipales = 7;
        for (int i = 0; i < numMarcasPrincipales; i++) {
            float angDeg = startAngle + (sweepAngle / (numMarcasPrincipales - 1)) * i;
            double angRad = Math.toRadians(angDeg);

            float longitudMarca = grosorArco * 2f;

            float rExt = radioArco + grosorArco / 2f;
            float xStart = (float) (rExt * Math.cos(angRad));
            float yStart = (float) (rExt * Math.sin(angRad));
            float rEnd = rExt - longitudMarca;
            float xEnd = (float) (rEnd * Math.cos(angRad));
            float yEnd = (float) (rEnd * Math.sin(angRad));

            canvas.drawLine(xStart, yStart, xEnd, yEnd, pincel);
        }

        // Marcas secundarias (cada 10° dentro del sweep de 270°)
        pincel.setStrokeWidth(0.002f * largo);
        for (int i = 0; i <= 27; i++) {
            float angEnDial = 10f * i;
            if (angEnDial <= sweepAngle) {
                float angDeg = startAngle + angEnDial;
                double angRad = Math.toRadians(angDeg);

                float longitudMarca = grosorArco * 1.2f;
                float rExt = radioArco + grosorArco / 2f;
                float xStart = (float) (rExt * Math.cos(angRad));
                float yStart = (float) (rExt * Math.sin(angRad));
                float rEnd = rExt - longitudMarca;
                float xEnd = (float) (rEnd * Math.cos(angRad));
                float yEnd = (float) (rEnd * Math.sin(angRad));

                canvas.drawLine(xStart, yStart, xEnd, yEnd, pincel);
            }
        }

        // 5. NÚMEROS DE LA ESCALA (más alejados del centro)
        pincel.setStyle(Paint.Style.FILL);
        pincel.setTextSize(0.08f * largo);

        for (int i = 0; i < numMarcasPrincipales; i++) {
            float valor = minimo + ((maximo - minimo) / (numMarcasPrincipales - 1)) * i;
            String numero = String.format("%.0f", valor);

            if (Math.abs(valor) < 0.1) {
                pincel.setColor(colorCero);
            } else {
                pincel.setColor(colorNumeros);
            }

            float angDeg = startAngle + (sweepAngle / (numMarcasPrincipales - 1)) * i;
            double angRad = Math.toRadians(angDeg);

            float radioTexto = radioArco - grosorArco * 2 - 0.05f * largo; // Más alejados
            float x = (float) (radioTexto * Math.cos(angRad));
            float y = (float) (radioTexto * Math.sin(angRad));

            Rect bounds = new Rect();
            pincel.getTextBounds(numero, 0, numero.length(), bounds);
            float anchoTexto = pincel.measureText(numero);

            canvas.drawText(numero, x - anchoTexto * 0.5f, y + bounds.height() * 0.5f, pincel);
        }

        // 6. AGUJA (INDICADOR)
        // Calcular proporción y ángulo de la aguja según valor medido
        float proporcion = (medida - minimo) / (maximo - minimo);
        if (proporcion < 0f) proporcion = 0f;
        if (proporcion > 1f) proporcion = 1f;

        float anguloDegAguja = startAngle + proporcion * sweepAngle;
        double anguloRadAguja = Math.toRadians(anguloDegAguja);

        // Longitudes para delante y atrás (en coordenadas Cartesianas)
        float longitudFrente = radioArco + grosorArco + 0.03f * largo;
        float longitudAtras = longitudFrente * 0.5f;

        float xFront = (float) (longitudFrente * Math.cos(anguloRadAguja));
        float yFront = (float) (longitudFrente * Math.sin(anguloRadAguja));
        float xBack = (float) (-longitudAtras * Math.cos(anguloRadAguja));
        float yBack = (float) (-longitudAtras * Math.sin(anguloRadAguja));

        pincel.setStrokeWidth(0.006f * largo);
        pincel.setColor(colorAguja);
        pincel.setStrokeCap(Paint.Cap.ROUND);
        canvas.drawLine(xBack, yBack, xFront, yFront, pincel);

        // Círculo central (radio 5% del círculo negro)
        float radioCentral1 = 0.05f * radioExterior;
        pincel.setStyle(Paint.Style.FILL);
        pincel.setColor(colorAguja);
        canvas.drawCircle(0, 0, radioCentral1, pincel);

        // Circunferencia concéntrica (radio 10% del círculo negro)
        float radioCentral2 = 0.10f * radioExterior;
        pincel.setStyle(Paint.Style.STROKE);
        pincel.setStrokeWidth(0.004f * largo);
        pincel.setColor(colorAguja);
        canvas.drawCircle(0, 0, radioCentral2, pincel);

        // 7. CUADRO CON VALOR Y UNIDADES (en el cuadrante 4: derecha-abajo)
        // Importante: la zona del cuadro está fuera del sweep (startAngle..startAngle+sweepAngle),
        // por lo que no hay arco ni marcas en esa zona.
        float xCuadro = 0.24f * largo;
        float yCuadro = 0.20f * largo;
        float anchoCuadro = 0.16f * largo;
        float altoCuadro = 0.11f * largo;
        float radioEsquinas = 0.015f * largo;

        RectF rectCuadro = new RectF(
                xCuadro - anchoCuadro/2,
                yCuadro - altoCuadro/2,
                xCuadro + anchoCuadro/2,
                yCuadro + altoCuadro/2
        );

        pincel.setStyle(Paint.Style.STROKE);
        pincel.setStrokeWidth(0.004f * largo);
        pincel.setColor(colorValorActual);
        canvas.drawRoundRect(rectCuadro, radioEsquinas, radioEsquinas, pincel);

        // Valor numérico en color valorActual
        pincel.setStyle(Paint.Style.FILL);
        pincel.setColor(colorValorActual);
        pincel.setTextSize(0.05f * largo);
        String valorTexto = String.format("%.0f", medida);
        float anchoValor = pincel.measureText(valorTexto);
        canvas.drawText(valorTexto, xCuadro - anchoValor * 0.5f, yCuadro + 0.005f * largo, pincel);

        // Unidades debajo en texto pequeño
        pincel.setTextSize(0.032f * largo);
        float anchoUnidades = pincel.measureText(unidades);
        canvas.drawText(unidades, xCuadro - anchoUnidades * 0.5f, yCuadro + 0.045f * largo, pincel);

        canvas.restore();
    }
}
