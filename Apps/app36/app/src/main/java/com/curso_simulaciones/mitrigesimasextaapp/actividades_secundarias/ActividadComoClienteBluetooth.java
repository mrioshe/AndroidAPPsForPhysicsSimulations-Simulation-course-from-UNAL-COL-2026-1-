package com.curso_simulaciones.mitrigesimasextaapp.actividades_secundarias;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.os.Handler;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.curso_simulaciones.mitrigesimasextaapp.comunicaciones.ClienteBluetooth;
import com.curso_simulaciones.mitrigesimasextaapp.datos.AlmacenDatosRAM;
import com.curso_simulaciones.mitrigesimasextaapp.utilidades.Acelerometro;
import com.curso_simulaciones.mitrigesimasextaapp.utilidades.Gaussimetro;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Pantalla/actividad que actúa como cliente Bluetooth.
 *
 * Recibe el JSON del SERVIDOR con los valores de aceleración y campo magnético,
 * y los visualiza en dos tacómetros (Acelerometro + Gaussimetro).
 *
 * JSON recibido:
 * { "comp_acel": int, "medida_acel": double,
 *   "comp_mag":  int, "medida_mag":  double }
 */
public class ActividadComoClienteBluetooth extends Activity implements Runnable {

    // ── Instrumentos ─────────────────────────────────────────────────────────
    private Acelerometro acelerometro;
    private Gaussimetro  gaussimetro;

    // ── Controles UI ──────────────────────────────────────────────────────────
    private TextView textviewRol, textviewAviso;
    private Button   botonBuscar, botonConectar;

    // ── Config ────────────────────────────────────────────────────────────────
    private int tamanoLetra;
    private final int COLOR_CLIENTE = Color.rgb(220, 156, 80);

    // ── Hilo ──────────────────────────────────────────────────────────────────
    private final Handler myHandler = new Handler();
    private Thread  hilo;
    private boolean corriendo;
    private final long PERIODO_MS = 50;

    // ── Bluetooth ─────────────────────────────────────────────────────────────
    private ClienteBluetooth cliente;
    private JSONObject obj;

    // ── Ciclo de vida ─────────────────────────────────────────────────────────
    @Override
    public void onCreate(Bundle icicle) {
        super.onCreate(icicle);
        tamanoLetra = (int)(0.8 * AlmacenDatosRAM.tamanoLetraResolucionIncluida);
        creacionElementosGUI();
        setContentView(crearGUI());
        eventos();
    }

    // ── GUI ───────────────────────────────────────────────────────────────────
    private void creacionElementosGUI() {

        // Los tacómetros NO capturan sensor local; reciben datos vía BT
        acelerometro = new Acelerometro(this);
        acelerometro.setAngulosSectores(50, 100, 100);
        acelerometro.setColorFranjaDinámica(Color.rgb(0, 255, 0));

        gaussimetro = new Gaussimetro(this);
        gaussimetro.setAngulosSectores(80, 90, 80);
        gaussimetro.setColorFranjaDinámica(Color.rgb(0, 180, 255));

        textviewRol = labelRol("CLIENTE");

        textviewAviso = new TextView(this);
        textviewAviso.setTextSize(TypedValue.COMPLEX_UNIT_SP, tamanoLetra);
        textviewAviso.setBackgroundColor(Color.YELLOW);
        textviewAviso.setTextColor(Color.RED);
        textviewAviso.setGravity(Gravity.CENTER);
        textviewAviso.setText(AlmacenDatosRAM.conexion_bluetooth);

        botonBuscar = btn("BUSCAR",  true);
        botonConectar = btn("CONECTAR", false);
    }

    private TextView labelRol(String texto) {
        TextView tv = new TextView(this);
        tv.setTextSize(TypedValue.COMPLEX_UNIT_SP, tamanoLetra);
        tv.setBackgroundColor(Color.YELLOW);
        tv.setText(texto);
        tv.setTextColor(Color.RED);
        tv.setGravity(Gravity.CENTER);
        tv.setEnabled(false);
        return tv;
    }

    private Button btn(String texto, boolean enabled) {
        Button b = new Button(this);
        b.setTextSize(TypedValue.COMPLEX_UNIT_SP, tamanoLetra);
        b.setText(texto);
        b.getBackground().setColorFilter(COLOR_CLIENTE, PorterDuff.Mode.MULTIPLY);
        b.setEnabled(enabled);
        return b;
    }

    private LinearLayout crearGUI() {
        /*
         * Layout vertical (weightSum=10):
         *  Fila 1 (0.8) — etiqueta ROL
         *  Fila 2 (4.0) — Tacómetro Acelerómetro
         *  Fila 3 (4.0) — Tacómetro Gaussímetro
         *  Fila 4 (0.4) — aviso estado BT
         *  Fila 5 (0.8) — botones BUSCAR / CONECTAR
         */
        LinearLayout principal = new LinearLayout(this);
        principal.setOrientation(LinearLayout.VERTICAL);
        principal.setBackgroundColor(Color.WHITE);
        principal.setWeightSum(10f);

        row(principal, hl(Color.WHITE, Gravity.CENTER),
                0.8f, textviewRol,       ViewGroup.LayoutParams.MATCH_PARENT);
        row(principal, hl(Color.WHITE, Gravity.CENTER),
                4.0f, acelerometro,      ViewGroup.LayoutParams.MATCH_PARENT);
        row(principal, hl(Color.WHITE, Gravity.CENTER),
                4.0f, gaussimetro,       ViewGroup.LayoutParams.MATCH_PARENT);
        row(principal, hl(Color.WHITE, 0),
                0.4f, textviewAviso,     ViewGroup.LayoutParams.MATCH_PARENT);

        // Fila botones (dos columnas)
        LinearLayout f5 = hl(Color.WHITE, 0);
        f5.setWeightSum(2f);
        LinearLayout.LayoutParams p5 = new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT, 0);
        p5.weight = 0.8f;
        f5.setLayoutParams(p5);
        principal.addView(f5);

        LinearLayout.LayoutParams pBtn = new LinearLayout.LayoutParams(
                0, ViewGroup.LayoutParams.MATCH_PARENT);
        pBtn.weight = 1f;
        f5.addView(botonBuscar,   pBtn);
        f5.addView(botonConectar, pBtn);

        return principal;
    }

    // ── Eventos ───────────────────────────────────────────────────────────────
    private void eventos() {
        botonBuscar.setOnClickListener(v -> {
            startActivity(new Intent(this, ActividadEscaneoDispositivos.class));
            botonConectar.setEnabled(true);
            botonBuscar.setEnabled(false);
        });

        botonConectar.setOnClickListener(v -> {
            if (botonConectar.getText().toString().equals("CONECTAR")) {
                botonConectar.setText("EMPEZAR");
                botonBuscar.setEnabled(false);
                inicializarCliente();
            } else {
                botonConectar.setEnabled(false);
                empezarHilo();
            }
        });
    }

    // ── Bluetooth ─────────────────────────────────────────────────────────────
    private void inicializarCliente() {
        cliente = new ClienteBluetooth();
        cliente.abrirSocketCliente(AlmacenDatosRAM.direccion);
        cliente.conectarSocketCliente();
        cliente.abrirFlujoEntrada();
        cliente.abrirFlujoSalida();
    }

    private void terminarComunicacion() {
        cliente.cerrarFlujoEntrada();
        cliente.cerrarFlujoSalida();
        cliente.cerrarSocketCliente();
    }

    // ── Hilo ──────────────────────────────────────────────────────────────────
    private void empezarHilo() {
        hilo = new Thread(this);
        hilo.start();
    }

    @Override
    public void run() {
        corriendo = true;
        while (corriendo) {
            try { Thread.sleep(PERIODO_MS); } catch (InterruptedException e) { e.printStackTrace(); }
            leerYActualizar();
        }
        AlmacenDatosRAM.conexion_bluetooth = " ";
    }

    private void leerYActualizar() {
        String datoString = cliente.leerString();
        if (datoString == null) return;

        try {
            obj = new JSONObject(datoString);

            int    compAcel   = obj.getInt("comp_acel");
            float  medidaAcel = (float) obj.getDouble("medida_acel");
            int    compMag    = obj.getInt("comp_mag");
            float  medidaMag  = (float) obj.getDouble("medida_mag");

            AlmacenDatosRAM.conexion_bluetooth = " Recibiendo datos del servidor ...";

            myHandler.post(() -> {
                acelerometro.setComponenteAcelerometro(compAcel);
                acelerometro.setMedida(medidaAcel);
                gaussimetro.setComponenteGaussimetro(compMag);
                gaussimetro.setMedida(medidaMag);
                textviewAviso.setText(AlmacenDatosRAM.conexion_bluetooth);
            });

        } catch (JSONException e) { e.printStackTrace(); }
    }

    // ── Ciclo de vida ─────────────────────────────────────────────────────────
    @Override protected void onPause()   { super.onPause();   corriendo = false; }
    @Override protected void onDestroy() { super.onDestroy(); terminarComunicacion(); }

    // ── Helpers layout ────────────────────────────────────────────────────────
    private LinearLayout hl(int color, int gravity) {
        LinearLayout l = new LinearLayout(this);
        l.setBackgroundColor(color);
        if (gravity != 0) l.setGravity(gravity);
        return l;
    }

    private void row(LinearLayout padre, LinearLayout fila, float peso,
                     View hijo, int anchoHijo) {
        LinearLayout.LayoutParams pFila = new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT, 0);
        pFila.weight = peso;
        fila.setLayoutParams(pFila);
        padre.addView(fila);
        fila.addView(hijo, new LinearLayout.LayoutParams(
                anchoHijo, ViewGroup.LayoutParams.MATCH_PARENT));
    }
}