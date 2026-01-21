package com.curso_simulaciones.minovenaapp.objetos_laboratorio;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

/**
 * Clase Rueda que representa una rueda con diseño específico:
 * - Borde negro exterior
 * - Disco rojo principal
 * - Círculo central blanco
 * - 4 círculos pequeños blancos en posiciones cardinales
 * - 4 líneas radiales (spokes) blancas conectando el centro con los círculos
 */
public class Rueda {

    // Radio del disco rojo principal
    private float radio;

    // Posición inicial (al crear la rueda)
    private float posicionInicialX, posicionInicialY;

    // Posición actual (puede cambiar con movimientos)
    private float posicionX, posicionY, posicionAngular;

    // Colores de la rueda
    private int colorBorde = Color.BLACK;
    private int colorDisco = Color.RED;
    private int colorElementosBlancos = Color.WHITE;

    /**
     * Constructor por defecto
     * Rueda centrada en (0,0), con posición angular 0,
     * y de radio 20f
     */
    public Rueda() {
        this.radio = 20f;
    }

    /**
     * Constructor de Rueda centrada en (posicionInicialX, posicionInicialY),
     * con posición angular cero y radio especificado
     *
     * @param posicionInicialX Coordenada X inicial del centro
     * @param posicionInicialY Coordenada Y inicial del centro
     * @param radio Radio del disco rojo principal
     */
    public Rueda(float posicionInicialX, float posicionInicialY, float radio) {
        this.posicionX = posicionInicialX;
        this.posicionY = posicionInicialY;

        this.posicionInicialX = posicionInicialX;
        this.posicionInicialY = posicionInicialY;

        this.radio = radio;
    }

    /**
     * Modifica el valor del radio de la rueda
     *
     * @param radio Nuevo radio
     */
    public void setRadioRueda(float radio) {
        this.radio = radio;
    }

    /**
     * Devuelve el valor del radio de la rueda
     *
     * @return radio actual
     */
    public float getRadioRueda() {
        return radio;
    }

    /**
     * Modifica el color del disco principal de la rueda
     *
     * @param color Nuevo color del disco
     */
    public void setColorDisco(int color) {
        this.colorDisco = color;
    }

    /**
     * Devuelve el color del disco de la rueda
     *
     * @return color del disco
     */
    public int getColorDisco() {
        return colorDisco;
    }

    /**
     * Modifica la posición (x,y) de la rueda mediante desplazamiento
     * (Polimorfismo - versión con 2 parámetros)
     *
     * @param desplazamientoX Desplazamiento en X desde posición inicial
     * @param desplazamientoY Desplazamiento en Y desde posición inicial
     */
    public void moverRueda(float desplazamientoX, float desplazamientoY) {
        this.posicionX = this.posicionInicialX + desplazamientoX;
        this.posicionY = this.posicionInicialY + desplazamientoY;
    }

    /**
     * Modifica la posición angular en grados de la rueda
     * (Polimorfismo - versión con 1 parámetro)
     *
     * @param posicionAngular Nueva posición angular en grados
     */
    public void moverRueda(float posicionAngular) {
        this.posicionX = this.posicionInicialX;
        this.posicionY = this.posicionInicialY;

        this.posicionAngular = posicionAngular;
    }

    /**
     * Modifica la posición (x,y) y genera una rotación alrededor del eje
     * que pasa por (x,y)
     * (Polimorfismo - versión con 3 parámetros)
     *
     * @param desplazamientoX Desplazamiento en X desde posición inicial
     * @param desplazamientoY Desplazamiento en Y desde posición inicial
     * @param posicionAngular Posición angular en grados
     */
    public void moverRueda(float desplazamientoX, float desplazamientoY, float posicionAngular) {
        this.posicionX = this.posicionInicialX + desplazamientoX;
        this.posicionY = this.posicionInicialY + desplazamientoY;

        this.posicionAngular = posicionAngular;
    }

    /**
     * Dibuja la rueda en el canvas con todos sus elementos
     *
     * @param canvas Canvas donde se dibujará
     * @param pincel Paint para dibujar
     */
    public void dibujese(Canvas canvas, Paint pincel) {

        // Guardar estado del canvas
        canvas.save();

        // Aplicar rotación alrededor del centro de la rueda
        canvas.rotate(posicionAngular, posicionX, posicionY);

        // Calcular dimensiones basadas en el radio
        float anchoBorde = 0.05f * radio;  // Ancho del borde negro
        float radioCentro = 0.2f * radio;  // Radio del círculo central blanco
        float radioCirculosPequeños = 0.08f * radio;  // Radio de círculos pequeños
        float distanciaCirculosPequeños = 0.55f * radio;  // Distancia del centro
        float anchoSpoke = radioCirculosPequeños;  // Grosor de las líneas radiales

        // 1. Dibujar borde negro exterior
        pincel.setStyle(Paint.Style.FILL);
        pincel.setColor(colorBorde);
        canvas.drawCircle(posicionX, posicionY, radio + anchoBorde, pincel);

        // 2. Dibujar disco rojo principal
        pincel.setColor(colorDisco);
        canvas.drawCircle(posicionX, posicionY, radio, pincel);

        // 3. Dibujar las 4 líneas radiales (spokes) blancas
        pincel.setColor(colorElementosBlancos);
        pincel.setStyle(Paint.Style.STROKE);
        pincel.setStrokeWidth(anchoSpoke);
        pincel.setStrokeCap(Paint.Cap.ROUND);  // Extremos redondeados

        // Ángulos para las 4 posiciones cardinales: 0°, 90°, 180°, 270°
        float[] angulos = {0f, 90f, 180f, 270f};

        for (float angulo : angulos) {
            // Convertir ángulo a radianes
            double anguloRad = Math.toRadians(angulo);

            // Calcular punto inicial (desde el borde del círculo central)
            float x1 = posicionX + (float)(radioCentro * Math.cos(anguloRad));
            float y1 = posicionY + (float)(radioCentro * Math.sin(anguloRad));

            // Calcular punto final (hasta donde están los círculos pequeños)
            float x2 = posicionX + (float)(distanciaCirculosPequeños * Math.cos(anguloRad));
            float y2 = posicionY + (float)(distanciaCirculosPequeños * Math.sin(anguloRad));

            // Dibujar línea radial
            canvas.drawLine(x1, y1, x2, y2, pincel);
        }

        // 4. Dibujar los 4 círculos pequeños blancos
        pincel.setStyle(Paint.Style.FILL);

        for (float angulo : angulos) {
            // Convertir ángulo a radianes
            double anguloRad = Math.toRadians(angulo);

            // Calcular posición del círculo pequeño
            float xCirculo = posicionX + (float)(distanciaCirculosPequeños * Math.cos(anguloRad));
            float yCirculo = posicionY + (float)(distanciaCirculosPequeños * Math.sin(anguloRad));

            // Dibujar círculo pequeño
            canvas.drawCircle(xCirculo, yCirculo, radioCirculosPequeños, pincel);
        }

        // 5. Dibujar círculo central blanco (encima de todo para tapar cruces)
        canvas.drawCircle(posicionX, posicionY, radioCentro, pincel);

        // Restaurar estado del canvas
        canvas.restore();
    }
}