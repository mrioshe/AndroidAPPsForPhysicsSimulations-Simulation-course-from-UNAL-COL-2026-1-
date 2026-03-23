package com.curso_simulaciones.micuadragesimasegundaapp.utilidades;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;

/**
 * Tacómetro que captura y visualiza la aceleración del dispositivo.
 * Usa Sensor.TYPE_ACCELEROMETER. Unidad: m/s².
 *
 * Componentes seleccionables:
 *   1 = ax  (m/s²) — rango simétrico con autorango
 *   2 = ay  (m/s²) — rango simétrico con autorango
 *   3 = az  (m/s²) — rango simétrico con autorango
 *   4 = |a| (m/s²) — rango positivo con autorango  (defecto)
 *
 * Autorango: la escala del tacómetro se ajusta dinámicamente
 * cuando la medición supera el límite actual, evitando que
 * la aguja gire libremente fuera de rango.
 */
public class Acelerometro extends GaugeSimple implements SensorEventListener {

    private SensorManager sensorManager;
    private int componenteAceleracion = 4;

    public Acelerometro(Context context) {
        super(context);
        setComponenteAcelerometro(componenteAceleracion);
    }

    // ── Selección de componente ───────────────────────────────────────────────

    /** Cambia la componente visible y reinicia el rango base. */
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

    // ── Sensor ────────────────────────────────────────────────────────────────

    public void captarSensor(Context context) {
        sensorManager = (SensorManager) context.getSystemService(Context.SENSOR_SERVICE);
        sensorManager.registerListener(this,
                sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER),
                SensorManager.SENSOR_DELAY_FASTEST);
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        float ax  = event.values[SensorManager.DATA_X];
        float ay  = event.values[SensorManager.DATA_Y];
        float az  = event.values[SensorManager.DATA_Z];
        float mag = (float) Math.sqrt(ax * ax + ay * ay + az * az);

        float medida;
        switch (componenteAceleracion) {
            case 1: medida = ax;  setUnidades("ax (m/s²)");  break;
            case 2: medida = ay;  setUnidades("ay (m/s²)");  break;
            case 3: medida = az;  setUnidades("az (m/s²)");  break;
            default: medida = mag; setUnidades("|a| (m/s²)"); break;
        }

        // Un decimal
        medida = (float)(Math.round(medida * 10) / 10.0f);
        setMedida(medida);

        // Ajustar escala automáticamente según componente
        if (componenteAceleracion == 4) {
            ajustarEscalaMagnitud(medida);    // solo valores positivos
        } else {
            ajustarEscalaComponente(medida);  // valores positivos y negativos
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) { }

    // ── Autorango ─────────────────────────────────────────────────────────────

    /**
     * Autorango para la MAGNITUD |a| (siempre >= 0).
     * Escala por tramos: 20 → 50 → 100 → 200 → 500 m/s²
     */
    public void ajustarEscalaMagnitud(float medida) {
        float maximo;
        if      (medida <=  20) maximo =  20f;
        else if (medida <=  50) maximo =  50f;
        else if (medida <= 100) maximo = 100f;
        else if (medida <= 200) maximo = 200f;
        else                    maximo = 500f;

        setRango(0, maximo);
    }

    /**
     * Autorango para COMPONENTES (ax, ay, az) que pueden ser negativas.
     * Escala simetrica: +-20 → +-50 → +-100 → +-200 → +-500 m/s²
     */
    public void ajustarEscalaComponente(float medida) {
        float absVal = Math.abs(medida);
        float limite;
        if      (absVal <=  20) limite =  20f;
        else if (absVal <=  50) limite =  50f;
        else if (absVal <= 100) limite = 100f;
        else if (absVal <= 200) limite = 200f;
        else                    limite = 500f;

        setRango(-limite, limite);
    }
}