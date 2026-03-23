package com.curso_simulaciones.micuadragesimasegundaapp.actividades_secundarias;

import android.app.Activity;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.curso_simulaciones.micuadragesimasegundaapp.R;
import com.curso_simulaciones.micuadragesimasegundaapp.comunicaciones.ClientePubSubMQTT;
import com.curso_simulaciones.micuadragesimasegundaapp.datos.AlmacenDatosRAM;
import com.curso_simulaciones.micuadragesimasegundaapp.utilidades.Acelerometro;
import com.curso_simulaciones.micuadragesimasegundaapp.utilidades.Boton;
import com.curso_simulaciones.micuadragesimasegundaapp.utilidades.Gaussimetro;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Actividad PUBLICADOR (PUB) MQTT para MiCuadragesimaSegundaApp.
 *
 * Captura con los sensores del dispositivo:
 *   • Acelerómetro  → ax, ay, az, |a|  (m/s²)
 *   • Gaussímetro   → bx, by, bz, |b|  (µT)
 *
 * Publica en el broker MQTT un JSON cada 200 ms:
 * {
 *   "comp_acel":   int,
 *   "medida_acel": double,
 *   "comp_mag":    int,
 *   "medida_mag":  double
 * }
 *
 * El operador puede seleccionar qué componente mostrar en cada tacómetro
 * mediante los botones ax/ay/az/a y bx/by/bz/b.
 */
public class ActividadComoClientePubMQTT extends Activity implements Runnable {

    // ── Instrumentos ─────────────────────────────────────────────────────────
    private Acelerometro acelerometro;
    private Gaussimetro  gaussimetro;

    // ── Botones de componente ─────────────────────────────────────────────────
    private Boton botonAx, botonAy, botonAz, botonA;
    private Boton botonBx, botonBy, botonBz, botonB;

    // ── UI ───────────────────────────────────────────────────────────────────
    private TextView textviewRol, textviewAviso;
    private Button   botonConectar;

    // ── Config ────────────────────────────────────────────────────────────────
    private int tamanoLetraResolucionIncluida;
    private final int COLOR_PUB = Color.rgb(220, 156, 80);

    // ── MQTT ──────────────────────────────────────────────────────────────────
    private ClientePubSubMQTT cliente;

    // ── Componentes activos ───────────────────────────────────────────────────
    private int comp_acel = 4;
    private int comp_mag  = 4;

    // ── Hilo ──────────────────────────────────────────────────────────────────
    private Thread hilo;

    // ── Ciclo de vida ─────────────────────────────────────────────────────────
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        gestionarResolucion();
        crearElementosGUI();
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
    private void crearElementosGUI() {

        // Tacómetros con captura local de sensor
        acelerometro = new Acelerometro(this);
        acelerometro.captarSensor(this);
        acelerometro.setAngulosSectores(50, 100, 100);
        acelerometro.setColorFranjaDinámica(Color.rgb(0, 255, 0));

        gaussimetro = new Gaussimetro(this);
        gaussimetro.captarSensor(this);
        gaussimetro.setAngulosSectores(80, 90, 80);
        gaussimetro.setColorFranjaDinámica(Color.rgb(0, 180, 255));

        // Botones aceleración
        botonAx = boton(R.drawable.ax);
        botonAy = boton(R.drawable.ay);
        botonAz = boton(R.drawable.az);
        botonA  = boton(R.drawable.a);

        // Botones campo magnético
        botonBx = boton(R.drawable.bx);
        botonBy = boton(R.drawable.by);
        botonBz = boton(R.drawable.bz);
        botonB  = boton(R.drawable.b);

        textviewRol = new TextView(this);
        textviewRol.setTextSize(TypedValue.COMPLEX_UNIT_SP,
                (int)(2f * tamanoLetraResolucionIncluida));
        textviewRol.setBackgroundColor(Color.YELLOW);
        textviewRol.setText("PUB");
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
        botonConectar.getBackground().setColorFilter(COLOR_PUB, PorterDuff.Mode.MULTIPLY);
    }

    private Boton boton(int drawable) {
        Boton b = new Boton(this);
        b.setImagen(drawable);
        return b;
    }

    private LinearLayout crearGUI() {
        /*
         * Layout vertical (weightSum=10):
         *  Fila 1 (1.0) — etiqueta PUB
         *  Fila 2 (3.75)— Acelerómetro (80%) | botones ax/ay/az/a (20%)
         *  Fila 3 (3.75)— Gaussímetro  (80%) | botones bx/by/bz/b (20%)
         *  Fila 4 (0.5) — aviso estado IoT
         *  Fila 5 (1.0) — botón CONECTAR
         */
        LinearLayout principal = new LinearLayout(this);
        principal.setOrientation(LinearLayout.VERTICAL);
        principal.setBackgroundColor(Color.BLACK);
        principal.setWeightSum(10f);

        // Fila 1 — ROL
        LinearLayout f1 = hl(Color.WHITE, Gravity.CENTER);
        row(principal, f1, 1.0f);
        LinearLayout.LayoutParams pRol = new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        f1.addView(textviewRol, pRol);

        // Fila 2 — Acelerómetro + botones
        LinearLayout f2 = hl(Color.WHITE, 0);
        f2.setWeightSum(10f);
        row(principal, f2, 3.75f);

        LinearLayout colAcelIzq = new LinearLayout(this);
        LinearLayout colAcelDer = new LinearLayout(this);
        colAcelDer.setOrientation(LinearLayout.VERTICAL);
        colAcelDer.setWeightSum(4f);
        f2.addView(colAcelIzq, wh(0, ViewGroup.LayoutParams.MATCH_PARENT, 8f));
        f2.addView(colAcelDer, wh(0, ViewGroup.LayoutParams.MATCH_PARENT, 2f));
        colAcelIzq.addView(acelerometro);
        for (Boton b : new Boton[]{botonAx, botonAy, botonAz, botonA}) {
            LinearLayout.LayoutParams p = new LinearLayout.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT, 0);
            p.weight = 1f;
            colAcelDer.addView(b, p);
        }

        // Fila 3 — Gaussímetro + botones
        LinearLayout f3 = hl(Color.WHITE, 0);
        f3.setWeightSum(10f);
        row(principal, f3, 3.75f);

        LinearLayout colMagIzq = new LinearLayout(this);
        LinearLayout colMagDer = new LinearLayout(this);
        colMagDer.setOrientation(LinearLayout.VERTICAL);
        colMagDer.setWeightSum(4f);
        f3.addView(colMagIzq, wh(0, ViewGroup.LayoutParams.MATCH_PARENT, 8f));
        f3.addView(colMagDer, wh(0, ViewGroup.LayoutParams.MATCH_PARENT, 2f));
        colMagIzq.addView(gaussimetro);
        for (Boton b : new Boton[]{botonBx, botonBy, botonBz, botonB}) {
            LinearLayout.LayoutParams p = new LinearLayout.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT, 0);
            p.weight = 1f;
            colMagDer.addView(b, p);
        }

        // Fila 4 — Aviso
        LinearLayout f4 = hl(Color.WHITE, 0);
        row(principal, f4, 0.5f);
        f4.addView(textviewAviso, mp_mp());

        // Fila 5 — CONECTAR
        LinearLayout f5 = hl(Color.WHITE, 0);
        row(principal, f5, 1.0f);
        f5.addView(botonConectar, wh(0, ViewGroup.LayoutParams.MATCH_PARENT, 1f));

        return principal;
    }

    // ── Eventos ───────────────────────────────────────────────────────────────
    private void eventos() {
        // Botones aceleración
        botonAx.setOnClickListener(v -> { acelerometro.setComponenteAcelerometro(1); comp_acel = 1; });
        botonAy.setOnClickListener(v -> { acelerometro.setComponenteAcelerometro(2); comp_acel = 2; });
        botonAz.setOnClickListener(v -> { acelerometro.setComponenteAcelerometro(3); comp_acel = 3; });
        botonA .setOnClickListener(v -> { acelerometro.setComponenteAcelerometro(4); comp_acel = 4; });

        // Botones campo magnético
        botonBx.setOnClickListener(v -> { gaussimetro.setComponenteGaussimetro(1); comp_mag = 1; });
        botonBy.setOnClickListener(v -> { gaussimetro.setComponenteGaussimetro(2); comp_mag = 2; });
        botonBz.setOnClickListener(v -> { gaussimetro.setComponenteGaussimetro(3); comp_mag = 3; });
        botonB .setOnClickListener(v -> { gaussimetro.setComponenteGaussimetro(4); comp_mag = 4; });

        // Botón conectar / empezar
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

    // ── Hilo de publicación ───────────────────────────────────────────────────
    @Override
    public void run() {
        while (true) {
            try { Thread.sleep(200); } catch (InterruptedException e) { e.printStackTrace(); }
            escribir();
            actualizarAviso();
        }
    }

    /** Construye el JSON y lo publica en el broker. */
    private void escribir() {
        String dato = getStringJSON();
        if (dato != null) {
            cliente.setEnviarMensajes(dato.getBytes());
        }
    }

    /**
     * JSON publicado:
     * { "comp_acel": int, "medida_acel": double,
     *   "comp_mag":  int, "medida_mag":  double }
     */
    private String getStringJSON() {
        JSONObject obj = new JSONObject();
        try {
            obj.put("comp_acel",   comp_acel);
            obj.put("medida_acel", acelerometro.getMedida());
            obj.put("comp_mag",    comp_mag);
            obj.put("medida_mag",  gaussimetro.getMedida());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return obj.toString();
    }

    private void actualizarAviso() {
        runOnUiThread(() ->
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

    private LinearLayout.LayoutParams wh(int w, int h, float peso) {
        LinearLayout.LayoutParams p = new LinearLayout.LayoutParams(w, h);
        p.weight = peso;
        return p;
    }

    private LinearLayout.LayoutParams mp_mp() {
        return new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT);
    }
}