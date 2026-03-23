package com.curso_simulaciones.micuadragesimasegundaapp.actividades_secundarias;

import android.app.Activity;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.os.Handler;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.curso_simulaciones.micuadragesimasegundaapp.comunicaciones.ClientePubSubMQTT;
import com.curso_simulaciones.micuadragesimasegundaapp.datos.AlmacenDatosRAM;
import com.curso_simulaciones.micuadragesimasegundaapp.utilidades.Acelerometro;
import com.curso_simulaciones.micuadragesimasegundaapp.utilidades.Gaussimetro;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Actividad SUSCRIPTOR (SUB) MQTT para MiCuadragesimaSegundaApp.
 *
 * Recibe del broker el JSON publicado por el PUB y muestra los valores
 * en dos tacómetros: Acelerómetro y Gaussímetro.
 *
 * JSON recibido:
 * { "comp_acel": int, "medida_acel": double,
 *   "comp_mag":  int, "medida_mag":  double }
 */
public class ActividadComoClienteSubMQTT extends Activity implements Runnable {

    // ── Instrumentos ─────────────────────────────────────────────────────────
    private Acelerometro acelerometro;
    private Gaussimetro  gaussimetro;

    // ── UI ───────────────────────────────────────────────────────────────────
    private Button   botonConectar;
    private TextView textviewRol, textviewAviso;

    // ── Config ────────────────────────────────────────────────────────────────
    private int tamanoLetraResolucionIncluida;
    private final int COLOR_SUB = Color.rgb(100, 200, 250);

    // ── MQTT ──────────────────────────────────────────────────────────────────
    private ClientePubSubMQTT cliente;
    private JSONObject obj;

    // ── Hilo ──────────────────────────────────────────────────────────────────
    private Thread  hilo;
    private final Handler myHandler = new Handler();

    // ── Ciclo de vida ─────────────────────────────────────────────────────────
    @Override
    public void onCreate(Bundle icicle) {
        super.onCreate(icicle);
        gestionarResolucion();
        creacionElementosGUI();
        setContentView(crearGUI(),
                new ViewGroup.LayoutParams(
                        ViewGroup.LayoutParams.MATCH_PARENT,
                        ViewGroup.LayoutParams.MATCH_PARENT));
        eventos();
        crearCliente();
        hilo = new Thread(this);
    }

    private void gestionarResolucion() {
        tamanoLetraResolucionIncluida =
                (int)(0.8 * AlmacenDatosRAM.tamanoLetraResolucionIncluida);
    }

    // ── GUI ───────────────────────────────────────────────────────────────────
    private void creacionElementosGUI() {

        // Tacómetros sin captura de sensor local (reciben datos vía MQTT)
        acelerometro = new Acelerometro(this);
        acelerometro.setUnidades("|a| (m/s²)");
        acelerometro.setAngulosSectores(50, 100, 100);
        acelerometro.setColorFranjaDinámica(Color.rgb(0, 255, 0));

        gaussimetro = new Gaussimetro(this);
        gaussimetro.setUnidades("|b| (µT)");
        gaussimetro.setAngulosSectores(80, 90, 80);
        gaussimetro.setColorFranjaDinámica(Color.rgb(0, 180, 255));

        textviewRol = new TextView(this);
        textviewRol.setTextSize(TypedValue.COMPLEX_UNIT_SP,
                (int)(2 * tamanoLetraResolucionIncluida));
        textviewRol.setBackgroundColor(Color.YELLOW);
        textviewRol.setText("SUB");
        textviewRol.setTextColor(Color.RED);
        textviewRol.setGravity(Gravity.CENTER);
        textviewRol.setEnabled(false);

        textviewAviso = new TextView(this);
        textviewAviso.setTextSize(TypedValue.COMPLEX_UNIT_SP,
                (int)(0.8 * tamanoLetraResolucionIncluida));
        textviewAviso.setBackgroundColor(Color.YELLOW);
        textviewAviso.setTextColor(Color.RED);
        textviewAviso.setGravity(Gravity.CENTER);

        botonConectar = new Button(this);
        botonConectar.setTextSize(TypedValue.COMPLEX_UNIT_SP, tamanoLetraResolucionIncluida);
        botonConectar.setText("CONECTAR");
        botonConectar.getBackground().setColorFilter(COLOR_SUB, PorterDuff.Mode.MULTIPLY);
    }

    private LinearLayout crearGUI() {
        /*
         * Layout vertical (weightSum=10):
         *  Fila 1 (1.0) — etiqueta SUB
         *  Fila 2 (3.75)— Tacómetro Acelerómetro
         *  Fila 3 (3.75)— Tacómetro Gaussímetro
         *  Fila 4 (0.5) — aviso estado IoT
         *  Fila 5 (1.0) — botón CONECTAR
         */
        LinearLayout principal = new LinearLayout(this);
        principal.setOrientation(LinearLayout.VERTICAL);
        principal.setBackgroundColor(Color.WHITE);
        principal.setWeightSum(10f);

        // Fila 1
        LinearLayout f1 = hl(Color.WHITE, Gravity.CENTER);
        row(principal, f1, 1.0f);
        LinearLayout.LayoutParams pRol = new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        f1.addView(textviewRol, pRol);

        // Fila 2 — acelerómetro
        LinearLayout f2 = hl(Color.WHITE, Gravity.CENTER);
        row(principal, f2, 3.75f);
        f2.addView(acelerometro, new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));

        // Fila 3 — gaussímetro
        LinearLayout f3 = hl(Color.WHITE, Gravity.CENTER);
        row(principal, f3, 3.75f);
        f3.addView(gaussimetro, new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));

        // Fila 4 — aviso
        LinearLayout f4 = hl(Color.WHITE, 0);
        f4.setOrientation(LinearLayout.HORIZONTAL);
        row(principal, f4, 0.5f);
        f4.addView(textviewAviso, new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));

        // Fila 5 — botón
        LinearLayout f5 = hl(Color.WHITE, 0);
        f5.setOrientation(LinearLayout.HORIZONTAL);
        f5.setWeightSum(1f);
        row(principal, f5, 1.0f);
        LinearLayout.LayoutParams pBtn = new LinearLayout.LayoutParams(
                0, ViewGroup.LayoutParams.MATCH_PARENT);
        pBtn.weight = 1f;
        f5.addView(botonConectar, pBtn);

        return principal;
    }

    // ── Eventos ───────────────────────────────────────────────────────────────
    private void eventos() {
        botonConectar.setOnClickListener(v -> {
            if (botonConectar.getText().toString().equals("CONECTAR")) {
                botonConectar.setText("EMPEZAR");
                cliente.conectar();
            } else {
                hilo.start();
                botonConectar.setEnabled(false);
            }
        });
    }

    // ── MQTT ──────────────────────────────────────────────────────────────────
    public void crearCliente() {
        cliente = new ClientePubSubMQTT(this);
    }

    // ── Hilo de suscripción ───────────────────────────────────────────────────
    @Override
    public void run() {
        while (true) {
            try { Thread.sleep(100); } catch (InterruptedException e) { e.printStackTrace(); }
            leer();
            actualizarAviso();
        }
    }

    private void leer() {
        String datoString = cliente.leerString();
        if (datoString != null) {
            convertirStringJson(datoString);
        }
    }

    /**
     * Parsea el JSON y actualiza los tacómetros desde el hilo principal.
     */
    public void convertirStringJson(String datoString) {
        try {
            obj = new JSONObject(datoString);
        } catch (JSONException e) {
            e.printStackTrace();
            return;
        }

        try {
            int   compAcel   = obj.getInt("comp_acel");
            float medidaAcel = (float) obj.getDouble("medida_acel");
            int   compMag    = obj.getInt("comp_mag");
            float medidaMag  = (float) obj.getDouble("medida_mag");

            myHandler.post(() -> {
                acelerometro.setComponenteAcelerometro(compAcel);
                acelerometro.setMedida(medidaAcel);
                gaussimetro.setComponenteGaussimetro(compMag);
                gaussimetro.setMedida(medidaMag);
            });

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void actualizarAviso() {
        myHandler.post(() ->
                textviewAviso.setText("Estado IoT: " + AlmacenDatosRAM.conectado_PubSub));
    }

    // ── Ciclo de vida ─────────────────────────────────────────────────────────
    @Override
    protected void onPause() {
        super.onPause();
        hilo = null;
        if (cliente != null) cliente.desconectar();
    }

    // ── Helpers de layout ─────────────────────────────────────────────────────
    private LinearLayout hl(int color, int gravity) {
        LinearLayout l = new LinearLayout(this);
        l.setBackgroundColor(color);
        if (gravity != 0) l.setGravity(gravity);
        return l;
    }

    private void row(LinearLayout padre, LinearLayout hijo, float peso) {
        LinearLayout.LayoutParams p = new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT, 0);
        p.weight = peso;
        hijo.setLayoutParams(p);
        padre.addView(hijo);
    }
}