package com.curso_simulaciones.mivigesimaquintaapp.controlador;

import com.curso_simulaciones.mivigesimaquintaapp.datos.AlmacenDatosRAM;

/**
 * Hilo de animación que actualiza la simulación a intervalos regulares
 */
public class HiloAnimacion extends Thread {

    private ActividadControladora actividad;
    private boolean ejecutando;

    // Tiempo entre actualizaciones en milisegundos
    private static final long INTERVALO_ACTUALIZACION = 50; // ~20 FPS

    // Delta de tiempo para la física en segundos (reducido para simulación más lenta)
    private float dt = 0.03f; // Reducido de 0.05f a 0.01f

    /**
     * Constructor del hilo de animación
     */
    public HiloAnimacion(ActividadControladora actividad) {
        this.actividad = actividad;
        this.ejecutando = false;
    }

    /**
     * Inicia el hilo de animación
     */
    public void iniciar() {
        ejecutando = true;
        start();
    }

    /**
     * Detiene el hilo de animación
     */
    public void detener() {
        ejecutando = false;
        try {
            join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        while (ejecutando) {
            try {
                // Actualizar tiempo transcurrido
                AlmacenDatosRAM.tiempo += dt;

                // Actualizar el modelo físico y la vista desde el thread UI
                actividad.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        actividad.actualizarSimulacion();
                    }
                });

                // Esperar antes de la próxima actualización
                Thread.sleep(INTERVALO_ACTUALIZACION);

            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}