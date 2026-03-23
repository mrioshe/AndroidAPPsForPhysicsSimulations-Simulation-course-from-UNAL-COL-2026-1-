package com.curso_simulaciones.micuadragesimasegundaapp.utilidades;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;

/**
 * Tacómetro que captura y visualiza el campo magnético del dispositivo.
 * Usa Sensor.TYPE_MAGNETIC_FIELD. Unidad: microteslas (µT).
 *
 * Componentes seleccionables:
 *   1 = bx  (µT)  — rango simétrico con autorango
 *   2 = by  (µT)  — rango simétrico con autorango
 *   3 = bz  (µT)  — rango simétrico con autorango
 *   4 = |b| (µT)  — rango positivo con autorango  (defecto)
 *
 * Autorango: la escala del tacómetro se ajusta dinámicamente
 * cuando la medición supera el límite actual, evitando que
 * la aguja gire libremente fuera de rango.
 */
public class Gaussimetro extends GaugeSimple implements SensorEventListener {

    private SensorManager sensorManager;
    private int componenteGaussimetro = 4;

    public Gaussimetro(Context context) {
        super(context);
        setComponenteGaussimetro(componenteGaussimetro);
    }

    // ── Selección de componente ───────────────────────────────────────────────

    /** Cambia la componente visible y reinicia el rango base. */
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

    // ── Sensor ────────────────────────────────────────────────────────────────

    public void captarSensor(Context context) {
        sensorManager = (SensorManager) context.getSystemService(Context.SENSOR_SERVICE);
        sensorManager.registerListener(this,
                sensorManager.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD),
                SensorManager.SENSOR_DELAY_FASTEST);
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        float bx  = event.values[SensorManager.DATA_X];
        float by  = event.values[SensorManager.DATA_Y];
        float bz  = event.values[SensorManager.DATA_Z];
        float mag = (float) Math.sqrt(bx * bx + by * by + bz * bz);

        float medida;
        switch (componenteGaussimetro) {
            case 1: medida = bx;  setUnidades("bx (µT)"); break;
            case 2: medida = by;  setUnidades("by (µT)"); break;
            case 3: medida = bz;  setUnidades("bz (µT)"); break;
            default: medida = mag; setUnidades("|b| (µT)"); break;
        }

        // Dos decimales
        medida = (float)(Math.round(medida * 100) / 100.0f);
        setMedida(medida);

        // Ajustar escala automáticamente según componente
        if (componenteGaussimetro == 4) {
            ajustarEscalaMagnitud(medida);    // solo valores positivos
        } else {
            ajustarEscalaComponente(medida);  // valores positivos y negativos
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) { }

    // ── Autorango ─────────────────────────────────────────────────────────────

    /**
     * Autorango para la MAGNITUD |b| (siempre >= 0).
     * Escala por tramos: 100 → 200 → 500 → 1000 → 2000 → 5000 µT
     */
    public void ajustarEscalaMagnitud(float medida) {
        float maximo;
        if      (medida <=  100) maximo =  100f;
        else if (medida <=  200) maximo =  200f;
        else if (medida <=  500) maximo =  500f;
        else if (medida <= 1000) maximo = 1000f;
        else if (medida <= 2000) maximo = 2000f;
        else                     maximo = 5000f;

        setRango(0, maximo);
    }

    /**
     * Autorango para COMPONENTES (bx, by, bz) que pueden ser negativas.
     * Escala simetrica: +-100 → +-200 → +-500 → +-1000 → +-2000 → +-5000 µT
     */
    public void ajustarEscalaComponente(float medida) {
        float absVal = Math.abs(medida);
        float limite;
        if      (absVal <=  100) limite =  100f;
        else if (absVal <=  200) limite =  200f;
        else if (absVal <=  500) limite =  500f;
        else if (absVal <= 1000) limite = 1000f;
        else if (absVal <= 2000) limite = 2000f;
        else                     limite = 5000f;

        setRango(-limite, limite);
    }
}