package com.curso_simulaciones.midecimaprimeraapp.objetos_laboratorio; // reemplaza por el paquete real de tu proyecto
import android.graphics.Color;

/**
 * Clase CuerpoRigido organizada: se añadieron imports y se corrigió formato.
 * No se cambió la lógica original.
 */
public class CuerpoRigido {

    protected float posicionInicialCentroMasaX, posicionInicialCentroMasaY;
    protected float posicionCentroMasaX, posicionCentroMasaY;
    protected float posicionEjeRotacionX, posicionEjeRotacionY;
    protected float posicionAngularRotacionEjeXY;
    protected int color = Color.RED;

    /**
     * Constructor por defecto del cuerpo rígido.
     * Su centro de masa está ubicado en la
     * posición (0,0). La posición angular es cero
     * y su color es rojo
     */
    public CuerpoRigido() {
        // Los campos ya tienen valores por defecto (0 y Color.RED)
    }

    /**
     * Constructor de CuerpoRigido
     * cuyo centro de masa está
     * ubicado en (posicionInicialX,posicionInicialY)
     */
    public CuerpoRigido(float posicionInicialCentroMasaX, float posicionInicialCentroMasaY) {
        this.posicionCentroMasaX = posicionInicialCentroMasaX;
        this.posicionCentroMasaY = posicionInicialCentroMasaY;

        this.posicionInicialCentroMasaX = posicionInicialCentroMasaX;
        this.posicionInicialCentroMasaY = posicionInicialCentroMasaY;
    }

    /**
     * Modifica el color del cuerpo rígido
     *
     * @param color
     */
    public void setColor(int color) {
        this.color = color;
    }

    /**
     * Devuelve el color del cuerpo rígido
     *
     * @return
     */
    public int getColor() {
        return color;
    }

    /*
      Se podría hacer un método setPosicion(x,y)
      para cambiar la posición del centro de masa, pero más adelante
      está el método mover(x,y) que hace esto
    */

    /**
     * Retorna la posicion en X
     * del centro de masa
     *
     * @return
     */
    public float getPosicionX() {
        return posicionCentroMasaX;
    }

    /**
     * Retorna posicion en Y
     * del centro de masa
     *
     * @return
     */
    public float getPosicionY() {
        return posicionCentroMasaY;
    }

    /**
     * Modifica la posición (posicionCentroMasaX,posicionCentroMasaY)
     * del centro de masa del cuerpo rígido
     *
     * @param desplazamientoCentroMasaX
     * @param desplazamientoCentroMasaY
     */
    public void mover(float desplazamientoCentroMasaX, float desplazamientoCentroMasaY) {
        this.posicionCentroMasaX = this.posicionInicialCentroMasaX + desplazamientoCentroMasaX;
        this.posicionCentroMasaY = this.posicionInicialCentroMasaY + desplazamientoCentroMasaY;
    }

    /**
     * Modifica la posición angular del cuerpo rígido
     * alrededor de un eje que pasa por su centro
     * de masa
     *
     * @param posicionAngular
     */
    /**
     * Mueve el centro de masa y además establece una rotación
     * alrededor del eje (se usa el centro de masa como eje por defecto).
     *
     * @param desplazamientoCentroMasaX desplazamiento en X (relativo a posicionInicialCentroMasaX)
     * @param desplazamientoCentroMasaY desplazamiento en Y (relativo a posicionInicialCentroMasaY)
     * @param posicionAngular           ángulo en grados para la rotación
     */
    public void mover(float desplazamientoCentroMasaX, float desplazamientoCentroMasaY, float posicionAngular) {
        // actualizar posición del centro de masa
        this.posicionCentroMasaX = this.posicionInicialCentroMasaX + desplazamientoCentroMasaX;
        this.posicionCentroMasaY = this.posicionInicialCentroMasaY + desplazamientoCentroMasaY;

        // usar el centro de masa actual como eje de rotación por defecto
        this.posicionEjeRotacionX = this.posicionCentroMasaX;
        this.posicionEjeRotacionY = this.posicionCentroMasaY;

        // actualizar ángulo de rotación (en grados, coherente con el resto del código)
        this.posicionAngularRotacionEjeXY = posicionAngular;
    }


}