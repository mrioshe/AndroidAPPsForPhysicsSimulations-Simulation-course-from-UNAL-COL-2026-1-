package com.curso_simulaciones.mitrigesimasextaapp.utilidades;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;

/**
 * Utilitario para lectura/medición de campos magnéticos.
 * Extiende GaugeSimple e implementa SensorEventListener para
 * capturar y visualizar el campo geomagnético del dispositivo.
 *
 * Unidad: microteslas (µT). Rango terrestre típico: 25 – 65 µT.
 *
 * Componentes seleccionables:
 *   1 = bx  (µT)
 *   2 = by  (µT)
 *   3 = bz  (µT)
 *   4 = |b| (µT)  — valor por defecto
 */
public class Gaussimetro extends GaugeSimple implements SensorEventListener {

    private SensorManager sensorManager;
    private int componenteGaussimetro = 4;

    public Gaussimetro(Context context) {
        super(context);
        setComponenteGaussimetro(componenteGaussimetro);
    }

    /** Cambia la componente visible y ajusta rango + unidades. */
    public void setComponenteGaussimetro(int componente) {
        this.componenteGaussimetro = componente;
        switch (componente) {
            case 1: setUnidades("bx (µT)");  setRango(-100, 100); break;
            case 2: setUnidades("by (µT)");  setRango(-100, 100); break;
            case 3: setUnidades("bz (µT)");  setRango(-100, 100); break;
            default: setUnidades("|b| (µT)"); setRango(0,   100); break;
        }
    }

    public int getComponenteGaussimetro() { return componenteGaussimetro; }

    /** Registra el listener para comenzar la captura del sensor. */
    public void captarSensor(Context context) {
        sensorManager = (SensorManager) context.getSystemService(Context.SENSOR_SERVICE);
        sensorManager.registerListener(this,
                sensorManager.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD),
                SensorManager.SENSOR_DELAY_FASTEST);
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        float bx = event.values[SensorManager.DATA_X];
        float by = event.values[SensorManager.DATA_Y];
        float bz = event.values[SensorManager.DATA_Z];
        float mag = (float) Math.sqrt(bx * bx + by * by + bz * bz);

        float medida;
        switch (componenteGaussimetro) {
            case 1: medida = bx;  setUnidades("bx (µT)");  setRango(-100, 100); break;
            case 2: medida = by;  setUnidades("by (µT)");  setRango(-100, 100); break;
            case 3: medida = bz;  setUnidades("bz (µT)");  setRango(-100, 100); break;
            default: medida = mag; setUnidades("|b| (µT)"); setRango(0,   100); break;
        }

        // Dos decimales
        setMedida((float)(Math.round(medida * 100) / 100.0));
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) { }
}