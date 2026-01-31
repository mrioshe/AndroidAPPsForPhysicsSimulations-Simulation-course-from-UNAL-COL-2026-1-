package com.curso_simulaciones.miexamenpracticoapp.objetos_laboratorio;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

/**
 * Clase Rodillo
 * Representa un emblema circular compuesto de elementos concéntricos y radiales
 * Implementa la interface Dibujable para poder ser dibujado en un Canvas
 */
public class Rodillo implements Dibujable {

    // Posición inicial del centro del rodillo
    protected float posicionInicialCentroX;
    protected float posicionInicialCentroY;

    // Posición actual del centro del rodillo
    protected float posicionCentroX;
    protected float posicionCentroY;

    // Eje de rotación y posición angular
    protected float posicionEjeRotacionX;
    protected float posicionEjeRotacionY;
    protected float posicionAngularRotacionEjeXY;

    // Radio principal del rodillo
    protected float R;

    // Colores parametrizables
    protected int colorCirculoExterior;     // Color del círculo exterior (rojo por defecto)
    protected int colorAnilloInterno;       // Color del anillo interno (naranja por defecto)
    protected int colorDiscosYLineas;       // Color de disco central, discos periféricos y líneas (amarillo por defecto)
    protected int colorPuntoCentral;        // Color del punto central (negro por defecto)

    // Anchos de trazo parametrizables
    protected float grosorCirculoExterior;  // Grosor del círculo exterior
    protected float grosorAnilloInterno;    // Grosor del anillo interno
    protected float grosorLineasRadiales;   // Grosor de las líneas radiales

    // Número de radios (8 por defecto)
    protected int numeroRadios;

    // Proporciones de radios (constantes geométricas)
    protected final float PROPORCION_ANILLO = 0.70f;        // Radio del anillo interno
    protected final float PROPORCION_DISCO_CENTRAL = 1/5f;  // Radio del disco central amarillo
    protected final float PROPORCION_PUNTO_CENTRAL = 1/8f;  // Radio del punto central negro
    protected final float PROPORCION_DISCOS_PERIFERICOS = 0.85f; // Radio de la circunferencia donde se ubican los discos periféricos
    protected final float PROPORCION_RADIO_DISCOS_PEQUENOS = 1/7f; // Radio de cada disco periférico

    /**
     * Constructor por defecto
     * Rodillo centrado en (0, 0) con R = 100 y colores predeterminados
     */
    public Rodillo() {
        this.posicionInicialCentroX = 0;
        this.posicionInicialCentroY = 0;
        this.posicionCentroX = 0;
        this.posicionCentroY = 0;
        this.posicionEjeRotacionX = 0;
        this.posicionEjeRotacionY = 0;
        this.posicionAngularRotacionEjeXY = 0;
        this.R = 100f;

        // Colores por defecto según la imagen de ejemplo
        this.colorCirculoExterior = Color.RED;
        this.colorAnilloInterno = Color.rgb(255, 165, 0); // Naranja
        this.colorDiscosYLineas = Color.YELLOW;
        this.colorPuntoCentral = Color.BLACK;

        // Anchos de trazo
        this.grosorCirculoExterior = 6f;   // Trazo bien marcado
        this.grosorAnilloInterno = 4f;     // Trazo menos pronunciado
        this.grosorLineasRadiales = 6f;    // Trazo similar al círculo exterior

        // Número de radios
        this.numeroRadios = 8;
    }

    /**
     * Constructor parametrizado
     * @param posicionInicialCentroX Posición inicial X del centro
     * @param posicionInicialCentroY Posición inicial Y del centro
     * @param R Radio principal del rodillo
     */
    public Rodillo(float posicionInicialCentroX, float posicionInicialCentroY, float R) {
        this();
        this.posicionInicialCentroX = posicionInicialCentroX;
        this.posicionInicialCentroY = posicionInicialCentroY;
        this.posicionCentroX = posicionInicialCentroX;
        this.posicionCentroY = posicionInicialCentroY;
        this.posicionEjeRotacionX = posicionInicialCentroX;
        this.posicionEjeRotacionY = posicionInicialCentroY;
        this.R = R;
    }

    /**
     * Constructor completo con todos los parámetros
     */
    public Rodillo(float posicionInicialCentroX, float posicionInicialCentroY, float R,
                   int colorCirculoExterior, int colorAnilloInterno,
                   int colorDiscosYLineas, int colorPuntoCentral,
                   float grosorCirculoExterior, float grosorAnilloInterno,
                   float grosorLineasRadiales, int numeroRadios) {
        this.posicionInicialCentroX = posicionInicialCentroX;
        this.posicionInicialCentroY = posicionInicialCentroY;
        this.posicionCentroX = posicionInicialCentroX;
        this.posicionCentroY = posicionInicialCentroY;
        this.posicionEjeRotacionX = posicionInicialCentroX;
        this.posicionEjeRotacionY = posicionInicialCentroY;
        this.posicionAngularRotacionEjeXY = 0;
        this.R = R;
        this.colorCirculoExterior = colorCirculoExterior;
        this.colorAnilloInterno = colorAnilloInterno;
        this.colorDiscosYLineas = colorDiscosYLineas;
        this.colorPuntoCentral = colorPuntoCentral;
        this.grosorCirculoExterior = grosorCirculoExterior;
        this.grosorAnilloInterno = grosorAnilloInterno;
        this.grosorLineasRadiales = grosorLineasRadiales;
        this.numeroRadios = numeroRadios;
    }

    // ==================== GETTERS Y SETTERS ====================

    public float getPosicionX() {
        return posicionCentroX;
    }

    public float getPosicionY() {
        return posicionCentroY;
    }

    public float getR() {
        return R;
    }

    public void setR(float r) {
        R = r;
    }

    public int getColorCirculoExterior() {
        return colorCirculoExterior;
    }

    public void setColorCirculoExterior(int colorCirculoExterior) {
        this.colorCirculoExterior = colorCirculoExterior;
    }

    public int getColorAnilloInterno() {
        return colorAnilloInterno;
    }

    public void setColorAnilloInterno(int colorAnilloInterno) {
        this.colorAnilloInterno = colorAnilloInterno;
    }

    public int getColorDiscosYLineas() {
        return colorDiscosYLineas;
    }

    public void setColorDiscosYLineas(int colorDiscosYLineas) {
        this.colorDiscosYLineas = colorDiscosYLineas;
    }

    public int getColorPuntoCentral() {
        return colorPuntoCentral;
    }

    public void setColorPuntoCentral(int colorPuntoCentral) {
        this.colorPuntoCentral = colorPuntoCentral;
    }

    public float getGrosorCirculoExterior() {
        return grosorCirculoExterior;
    }

    public void setGrosorCirculoExterior(float grosorCirculoExterior) {
        this.grosorCirculoExterior = grosorCirculoExterior;
    }

    public float getGrosorAnilloInterno() {
        return grosorAnilloInterno;
    }

    public void setGrosorAnilloInterno(float grosorAnilloInterno) {
        this.grosorAnilloInterno = grosorAnilloInterno;
    }

    public float getGrosorLineasRadiales() {
        return grosorLineasRadiales;
    }

    public void setGrosorLineasRadiales(float grosorLineasRadiales) {
        this.grosorLineasRadiales = grosorLineasRadiales;
    }

    public int getNumeroRadios() {
        return numeroRadios;
    }

    public void setNumeroRadios(int numeroRadios) {
        this.numeroRadios = numeroRadios;
    }

    // ==================== MÉTODOS DE MOVIMIENTO ====================

    /**
     * Modifica la posición (posicionCentroX, posicionCentroY)
     * del centro del rodillo (TRASLACIÓN)
     *
     * @param desplazamientoCentroX Desplazamiento en X desde la posición inicial
     * @param desplazamientoCentroY Desplazamiento en Y desde la posición inicial
     */
    public void mover(float desplazamientoCentroX, float desplazamientoCentroY) {
        this.posicionCentroX = this.posicionInicialCentroX + desplazamientoCentroX;
        this.posicionCentroY = this.posicionInicialCentroY + desplazamientoCentroY;
    }

    /**
     * Modifica la posición angular del rodillo
     * alrededor de un eje que pasa por su centro (ROTACIÓN)
     *
     * @param posicionAngular Ángulo de rotación en grados
     */
    public void mover(float posicionAngular) {
        this.posicionEjeRotacionX = this.posicionInicialCentroX;
        this.posicionEjeRotacionY = this.posicionInicialCentroY;
        this.posicionAngularRotacionEjeXY = posicionAngular;
    }

    /**
     * Modifica la posición (posicionCentroX, posicionCentroY)
     * del centro del rodillo y genera una rotación
     * alrededor del eje que pasa por éste (TRASLACIÓN + ROTACIÓN)
     *
     * @param desplazamientoCentroX Desplazamiento en X desde la posición inicial
     * @param desplazamientoCentroY Desplazamiento en Y desde la posición inicial
     * @param posicionAngular Ángulo de rotación en grados
     */
    public void mover(float desplazamientoCentroX, float desplazamientoCentroY, float posicionAngular) {
        this.posicionCentroX = this.posicionInicialCentroX + desplazamientoCentroX;
        this.posicionCentroY = this.posicionInicialCentroY + desplazamientoCentroY;

        this.posicionEjeRotacionX = this.posicionCentroX;
        this.posicionEjeRotacionY = this.posicionCentroY;

        this.posicionAngularRotacionEjeXY = posicionAngular;
    }

    /**
     * Genera rotación del rodillo a la
     * posición angular posicionAngularRotacionEjeXY
     * alrededor de eje que pasa por
     * (posicionEjeRotacionX, posicionEjeRotacionY)
     *
     * @param posicionEjeRotacionX Posición X del eje de rotación
     * @param posicionEjeRotacionY Posición Y del eje de rotación
     * @param posicionAngular Ángulo de rotación en grados
     */
    public void rotar(float posicionEjeRotacionX, float posicionEjeRotacionY, float posicionAngular) {
        this.posicionEjeRotacionX = posicionEjeRotacionX;
        this.posicionEjeRotacionY = posicionEjeRotacionY;
        this.posicionAngularRotacionEjeXY = posicionAngular;
    }

    // ==================== MÉTODO DIBUJESE ====================

    /**
     * Implementación del método dibujese de la interface Dibujable
     * Dibuja el rodillo siguiendo el orden específico para garantizar la apariencia correcta
     * Orden de dibujo: círculos concéntricos (con relleno y contorno),
     * líneas radiales, discos periféricos, disco central contorno, punto central
     */
    @Override
    public void dibujese(Canvas canvas, Paint pincel) {

        // Guardar el estado del canvas
        canvas.save();

        // Aplicar rotación si existe
        canvas.rotate(posicionAngularRotacionEjeXY, posicionEjeRotacionX, posicionEjeRotacionY);

        // Activar antialiasing para mejor calidad visual
        pincel.setAntiAlias(true);

        // 1. CÍRCULO EXTERIOR (Radio R) - PRIMERO RELLENO, LUEGO CONTORNO NEGRO
        // Dibujar relleno del color
        pincel.setStyle(Paint.Style.FILL);
        pincel.setColor(colorCirculoExterior);
        canvas.drawCircle(posicionCentroX, posicionCentroY, R, pincel);

        // Dibujar contorno negro
        pincel.setStyle(Paint.Style.STROKE);
        pincel.setStrokeWidth(grosorCirculoExterior);
        pincel.setColor(Color.BLACK);
        canvas.drawCircle(posicionCentroX, posicionCentroY, R, pincel);

        // 2. ANILLO INTERNO (Radio 0.70*R) - PRIMERO RELLENO, LUEGO CONTORNO NEGRO
        float radioAnillo = PROPORCION_ANILLO * R;

        // Dibujar relleno del color
        pincel.setStyle(Paint.Style.FILL);
        pincel.setColor(colorAnilloInterno);
        canvas.drawCircle(posicionCentroX, posicionCentroY, radioAnillo, pincel);

        // Dibujar contorno negro
        pincel.setStyle(Paint.Style.STROKE);
        pincel.setStrokeWidth(grosorAnilloInterno);
        pincel.setColor(Color.BLACK);
        canvas.drawCircle(posicionCentroX, posicionCentroY, radioAnillo, pincel);

        // 3. LÍNEAS RADIALES AMARILLAS (con mayor grosor)
        // Desde el centro hasta los centros de los discos periféricos
        float radioCircunferenciaCentros = PROPORCION_DISCOS_PERIFERICOS * R;
        float anguloSeparacion = 360f / numeroRadios;

        pincel.setStyle(Paint.Style.STROKE);
        pincel.setStrokeWidth(grosorLineasRadiales); // Ahora con grosor similar al círculo exterior
        pincel.setColor(colorDiscosYLineas);

        for (int i = 0; i < numeroRadios; i++) {
            // Calcular el ángulo en radianes
            float anguloGrados = i * anguloSeparacion;
            float anguloRadianes = (float) Math.toRadians(anguloGrados);

            // Calcular la posición del centro del disco periférico
            float xDiscoPeriferico = posicionCentroX + radioCircunferenciaCentros * (float) Math.cos(anguloRadianes);
            float yDiscoPeriferico = posicionCentroY + radioCircunferenciaCentros * (float) Math.sin(anguloRadianes);

            // Dibujar línea radial desde el centro global hasta el centro del disco periférico
            canvas.drawLine(posicionCentroX, posicionCentroY, xDiscoPeriferico, yDiscoPeriferico, pincel);
        }

        // 4. DISCOS PERIFÉRICOS AMARILLOS (rellenos)
        float radioDiscoPequeño = PROPORCION_RADIO_DISCOS_PEQUENOS * R;

        pincel.setStyle(Paint.Style.FILL);
        pincel.setColor(colorDiscosYLineas);

        for (int i = 0; i < numeroRadios; i++) {
            // Calcular el ángulo en radianes
            float anguloGrados = i * anguloSeparacion;
            float anguloRadianes = (float) Math.toRadians(anguloGrados);

            // Calcular la posición del centro del disco periférico
            float xDiscoPeriferico = posicionCentroX + radioCircunferenciaCentros * (float) Math.cos(anguloRadianes);
            float yDiscoPeriferico = posicionCentroY + radioCircunferenciaCentros * (float) Math.sin(anguloRadianes);

            // Dibujar el disco periférico
            canvas.drawCircle(xDiscoPeriferico, yDiscoPeriferico, radioDiscoPequeño, pincel);
        }

        // 5. DISCO CENTRAL AMARILLO (Radio R/7) - COMO CONTORNO para que se vea
        float radioDiscoCentral = PROPORCION_DISCO_CENTRAL * R;
        pincel.setStyle(Paint.Style.FILL);
        pincel.setStrokeWidth(3f); // Grosor moderado para el contorno
        pincel.setColor(colorDiscosYLineas);
        canvas.drawCircle(posicionCentroX, posicionCentroY, radioDiscoCentral, pincel);

        // 6. PUNTO CENTRAL NEGRO (Radio R/8, dibujado al final para quedar sobre los demás)
        pincel.setStyle(Paint.Style.FILL);
        pincel.setColor(colorPuntoCentral);
        float radioPuntoCentral = PROPORCION_PUNTO_CENTRAL * R;
        canvas.drawCircle(posicionCentroX, posicionCentroY, radioPuntoCentral, pincel);

        // Restaurar el estado del canvas
        canvas.restore();
    }
}