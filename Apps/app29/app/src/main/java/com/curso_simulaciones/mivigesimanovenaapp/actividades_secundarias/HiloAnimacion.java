package com.curso_simulaciones.mivigesimanovenaapp.actividades_secundarias;

import com.curso_simulaciones.mivigesimanovenaapp.datos.AlmacenDatosRAM;

public class HiloAnimacion extends Thread {
    private ActividadDesplegadoraDatos actividadDesplegadoraDatos;
    public int contador = 0;
    public float tiempo = 0;
    public boolean corriendo = false;

    // Intervalo de muestreo en milisegundos (1000 ms = 1 segundo)
    private static final int INTERVALO_MUESTREO = 1000;

    public HiloAnimacion(ActividadDesplegadoraDatos actividadDesplegadoraDatos) {
        this.actividadDesplegadoraDatos = actividadDesplegadoraDatos;
    }

    public void run() {
        while (true) {
            try {
                if (corriendo) {
                    // Almacenar dato actual en el ArrayList
                    AlmacenDatosRAM.datos.add(AlmacenDatosRAM.datoActual);

                    // Actualizar gráfica en el hilo principal (UI Thread)
                    actividadDesplegadoraDatos.runOnUiThread(new Runnable() {
                        public void run() {
                            actividadDesplegadoraDatos.graficador.actualizarGrafica(
                                    AlmacenDatosRAM.datos);
                        }
                    });

                    contador++;
                    tiempo = contador;
                }

                // Pausa antes del siguiente muestreo
                Thread.sleep(INTERVALO_MUESTREO);

            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}