package com.curso_simulaciones.mitrigesimasextaapp.utilidades;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;

/**
 * Manejador/lector del sensor acelerómetro.
 * Extiende GaugeSimple e implementa SensorEventListener para
 * capturar y visualizar la aceleración del dispositivo.
 *
 * Componentes seleccionables:
 *   1 = ax  (m/s²)
 *   2 = ay  (m/s²)
 *   3 = az  (m/s²)
 *   4 = |a| (m/s²)  — valor por defecto
 */
public class Acelerometro extends GaugeSimple implements SensorEventListener {

    private SensorManager sensorManager;
    private int componenteAceleracion = 4;

    public Acelerometro(Context context) {
        super(context);
        setComponenteAcelerometro(componenteAceleracion);
    }

    /** Cambia la componente visible y ajusta rango + unidades. */
    public void setComponenteAcelerometro(int componente) {
        this.componenteAceleracion = componente;
        switch (componente) {
            case 1: setUnidades("ax (m/s²)");  setRango(-20, 20); break;
            case 2: setUnidades("ay (m/s²)");  setRango(-20, 20); break;
            case 3: setUnidades("az (m/s²)");  setRango(-20, 20); break;
            default: setUnidades("|a| (m/s²)"); setRango(0,  20); break;
        }
    }

    public int getComponenteAcelerometro() { return componenteAceleracion; }

    /** Registra el listener para comenzar la captura del sensor. */
    public void captarSensor(Context context) {
        sensorManager = (SensorManager) context.getSystemService(Context.SENSOR_SERVICE);
        sensorManager.registerListener(this,
                sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER),
                SensorManager.SENSOR_DELAY_FASTEST);
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        float ax = event.values[SensorManager.DATA_X];
        float ay = event.values[SensorManager.DATA_Y];
        float az = event.values[SensorManager.DATA_Z];
        float mag = (float) Math.sqrt(ax * ax + ay * ay + az * az);

        float medida;
        switch (componenteAceleracion) {
            case 1: medida = ax;  setUnidades("ax (m/s²)");  setRango(-20, 20); break;
            case 2: medida = ay;  setUnidades("ay (m/s²)");  setRango(-20, 20); break;
            case 3: medida = az;  setUnidades("az (m/s²)");  setRango(-20, 20); break;
            default: medida = mag; setUnidades("|a| (m/s²)"); setRango(0,  20); break;
        }

        // Un decimal
        setMedida((float)(Math.round(medida * 10) / 10.0));
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) { }
}