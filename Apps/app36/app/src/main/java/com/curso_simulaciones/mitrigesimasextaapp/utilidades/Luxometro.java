package com.curso_simulaciones.mitrigesimasextaapp.utilidades;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;

/**
 * Utilitario para lectura/medición de iluminación (luxómetro).
 * Extiende GaugeSimple e implementa SensorEventListener para
 * capturar y visualizar la iluminancia del sensor de luz del dispositivo.
 *
 * Unidad: lux (lx).
 * La escala del tacómetro se ajusta automáticamente según el rango medido.
 */
public class Luxometro extends GaugeSimple implements SensorEventListener {

    private SensorManager sensorManager;

    public Luxometro(Context context) {
        super(context);
        setUnidades("lx");
        setRango(0, 100);
    }

    /** Registra el listener para comenzar la captura del sensor. */
    public void captarSensor(Context context) {
        sensorManager = (SensorManager) context.getSystemService(Context.SENSOR_SERVICE);
        sensorManager.registerListener(this,
                sensorManager.getDefaultSensor(Sensor.TYPE_LIGHT),
                SensorManager.SENSOR_DELAY_FASTEST);
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        float medida = event.values[SensorManager.DATA_X];
        // Dos decimales
        float medida2dec = (float) Math.floor(100 * medida) / 100f;
        setMedida(medida2dec);
        ajustarEscala(medida);
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) { }

    /**
     * Ajusta el rango máximo del tacómetro de forma automática
     * según el valor de iluminancia medido (escala logarítmica por tramos).
     */
    public void ajustarEscala(float medida) {
        float maximo;
        if      (medida <= 100)   maximo =    100f;
        else if (medida <= 500)   maximo =    500f;
        else if (medida <= 1000)  maximo =   1000f;
        else if (medida <= 5000)  maximo =   5000f;
        else if (medida <= 10000) maximo =  10000f;
        else                      maximo =  50000f;

        setRango(0, maximo);
    }
}