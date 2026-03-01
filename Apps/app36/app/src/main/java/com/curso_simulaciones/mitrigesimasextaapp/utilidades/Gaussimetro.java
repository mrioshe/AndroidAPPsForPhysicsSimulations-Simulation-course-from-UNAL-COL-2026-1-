package com.curso_simulaciones.mitrigesimasextaapp.utilidades;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;

public class Gaussimetro extends GaugeSimple implements SensorEventListener {
    private SensorManager sensorManager;
    private int componenteCampo = 4;  // Por defecto muestra magnitud total

    public Gaussimetro(Context context) {
        super(context);
        setComponenteGaussimetro(componenteCampo);
    }

    public void setComponenteGaussimetro(int componenteCampo) {
        this.componenteCampo = componenteCampo;
        if (componenteCampo == 1) {
            this.setUnidades("Bx (µT)");
            this.setRango(-1000, 1000);
        }
        if (componenteCampo == 2) {
            this.setUnidades("By (µT)");
            this.setRango(-1000, 1000);
        }
        if (componenteCampo == 3) {
            this.setUnidades("Bz (µT)");
            this.setRango(-1000, 1000);
        }
        if (componenteCampo == 4) {
            this.setUnidades("B (µT)");
            this.setRango(0, 1000);
        }
    }

    public void captarSensor(Context context) {
        sensorManager = (SensorManager) context.getSystemService(Context.SENSOR_SERVICE);
        sensorManager.registerListener(
                this,
                sensorManager.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD),
                SensorManager.SENSOR_DELAY_FASTEST);
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        float medida_x = event.values[SensorManager.DATA_X];
        float medida_y = event.values[SensorManager.DATA_Y];
        float medida_z = event.values[SensorManager.DATA_Z];

        float resultado = medida_x * medida_x + medida_y * medida_y + medida_z * medida_z;
        float magnitud = (float) Math.sqrt(resultado);

        float medida = 0;

        if (componenteCampo == 1) {
            medida = medida_x;
            this.setUnidades("Bx (µT)");
            this.setRango(-1000, 1000);
        }
        if (componenteCampo == 2) {
            medida = medida_y;
            this.setUnidades("By (µT)");
            this.setRango(-1000, 1000);
        }
        if (componenteCampo == 3) {
            medida = medida_z;
            this.setUnidades("Bz (µT)");
            this.setRango(-1000, 1000);
        }
        if (componenteCampo == 4) {
            medida = magnitud;
            this.setUnidades("B (µT)");
            this.setRango(0, 1000);
        }

        // Redondear a un decimal
        medida = (float) (Math.round(medida * 10) / 10.0f);
        this.setMedida(medida);
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {
    }
}