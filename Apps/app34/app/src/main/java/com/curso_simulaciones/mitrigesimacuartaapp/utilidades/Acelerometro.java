package com.curso_simulaciones.mitrigesimacuartaapp.utilidades;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;

import com.curso_simulaciones.mitrigesimacuartaapp.datos.AlmacenDatosRAM;

public class Acelerometro extends GaugeSimple implements SensorEventListener {

    private SensorManager sensorManager;

    // ── Filtro pasa-bajos ──────────────────────────────────────────────────────
    private static final float ALPHA = 0.15f;
    private float magnitudFiltrada = 0f;

    public Acelerometro(Context context) {
        super(context);
        captarSensor(context);
        this.setRango(0, 50);
    }

    private void captarSensor(Context context) {
        sensorManager = (SensorManager) context.getSystemService(Context.SENSOR_SERVICE);
        sensorManager.registerListener(
                this,
                sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER),
                SensorManager.SENSOR_DELAY_FASTEST);
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        float medidaX = event.values[SensorManager.DATA_X];
        float medidaY = event.values[SensorManager.DATA_Y];
        float medidaZ = event.values[SensorManager.DATA_Z];

        // Magnitud cruda
        float magnitudCruda = (float) Math.sqrt(
                medidaX * medidaX + medidaY * medidaY + medidaZ * medidaZ);

        // ── Filtro pasa-bajos exponencial ─────────────────────────────────────
        magnitudFiltrada = ALPHA * magnitudCruda + (1f - ALPHA) * magnitudFiltrada;

        // Redondear para almacenamiento
        medidaX = Math.round(medidaX * 100) / 100f;
        medidaY = Math.round(medidaY * 100) / 100f;
        medidaZ = Math.round(medidaZ * 100) / 100f;
        float magnitudRedondeada = Math.round(magnitudFiltrada * 100) / 100f;

        AlmacenDatosRAM.aceleracionX     = medidaX;
        AlmacenDatosRAM.aceleracionY     = medidaY;
        AlmacenDatosRAM.aceleracionZ     = medidaZ;
        AlmacenDatosRAM.aceleracionTotal = magnitudRedondeada;

        this.setMedida(magnitudFiltrada);
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) { }
}