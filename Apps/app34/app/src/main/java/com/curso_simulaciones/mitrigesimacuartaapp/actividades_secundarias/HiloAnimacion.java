package com.curso_simulaciones.mitrigesimacuartaapp.actividades_secundarias;

import com.curso_simulaciones.mitrigesimacuartaapp.datos.AlmacenDatosRAM;

public class HiloAnimacion extends Thread {
    private ActividadDesplegadoraDatos actividadDesplegadoraDatos;
    private int contador = 0;

    public HiloAnimacion(ActividadDesplegadoraDatos actividadDesplegadoraDatos) {
        this.actividadDesplegadoraDatos = actividadDesplegadoraDatos;
    }

    public void run() {
        while (contador <= AlmacenDatosRAM.nDatos) {
            try {
                // Actualizar tiempo
                AlmacenDatosRAM.tiempo = (float) (contador * AlmacenDatosRAM.periodoMuestreo / 1000.0f);

                // Actualizar tabla
                actividadDesplegadoraDatos.hacerTrabajoDuro();

                contador++;

                // Pausa según el periodo de muestreo configurado
                Thread.sleep(AlmacenDatosRAM.periodoMuestreo);

            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        // Activar botones cuando termine la recolección
        actividadDesplegadoraDatos.activarBotones = true;
        actividadDesplegadoraDatos.hacerTrabajoDuro();
    }
}