package com.curso_simulaciones.simulphysics.metodos_numericos;

/**
 * Clase simple para representar el estado de un sistema
 * (por ejemplo para integradores numéricos).
 */
public class Estado {
    public double t;
    public double[] y;

    public Estado() {
    }

    public Estado(double t, double[] y) {
        this.t = t;
        this.y = y;
    }

    public Estado copy() {
        double[] yc = null;
        if (y != null) {
            yc = new double[y.length];
            System.arraycopy(y, 0, yc, 0, y.length);
        }
        return new Estado(t, yc);
    }
}