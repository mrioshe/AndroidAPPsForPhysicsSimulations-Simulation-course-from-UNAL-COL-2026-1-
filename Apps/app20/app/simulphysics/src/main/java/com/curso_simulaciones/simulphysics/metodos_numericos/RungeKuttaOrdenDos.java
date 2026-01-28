package com.curso_simulaciones.simulphysics.metodos_numericos;

/**
 * Implementación base (abstracta) de un integrador
 * de Runge-Kutta de orden dos. El usuario debe
 * implementar el método derivada.
 */
public abstract class RungeKuttaOrdenDos {

    protected double h; // paso

    public RungeKuttaOrdenDos(double paso) {
        this.h = paso;
    }

    /**
     * Derivada del sistema: dy/dt = f(t, y)
     * @param t tiempo
     * @param y vector de estado
     * @return derivada
     */
    public abstract double[] derivada(double t, double[] y);

    /**
     * Realiza un paso de integración desde el estado dado.
     * @param estado estado inicial
     * @return estado después de un paso h
     */
    public Estado paso(Estado estado) {
        double t = estado.t;
        double[] y = estado.y;
        double[] k1 = derivada(t, y);
        double[] yTemp = new double[y.length];
        for (int i = 0; i < y.length; i++) {
            yTemp[i] = y[i] + h * k1[i];
        }
        double[] k2 = derivada(t + h, yTemp);
        double[] yNext = new double[y.length];
        for (int i = 0; i < y.length; i++) {
            yNext[i] = y[i] + h * 0.5 * (k1[i] + k2[i]);
        }
        return new Estado(t + h, yNext);
    }
}