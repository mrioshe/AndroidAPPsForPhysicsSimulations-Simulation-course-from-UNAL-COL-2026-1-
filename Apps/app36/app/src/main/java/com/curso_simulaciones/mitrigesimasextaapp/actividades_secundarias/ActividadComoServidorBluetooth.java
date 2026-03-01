package com.curso_simulaciones.mitrigesimasextaapp.actividades_secundarias;

import android.app.Activity;
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

import com.curso_simulaciones.mitrigesimasextaapp.R;
import com.curso_simulaciones.mitrigesimasextaapp.comunicaciones.ServidorBluetooth;
import com.curso_simulaciones.mitrigesimasextaapp.datos.AlmacenDatosRAM;
import com.curso_simulaciones.mitrigesimasextaapp.utilidades.Acelerometro;
import com.curso_simulaciones.mitrigesimasextaapp.utilidades.Boton;
import com.curso_simulaciones.mitrigesimasextaapp.utilidades.Gaussimetro;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Pantalla/actividad que actúa como servidor Bluetooth.
 *
 * Mide con los sensores del dispositivo:
 *   • Acelerómetro → ax, ay, az, |a| (m/s²)
 *   • Gaussímetro  → bx, by, bz, |b| (µT)
 *
 * Envía los valores al CLIENTE cada 100 ms mediante un JSON serializado.
 *
 * JSON enviado:
 * { "comp_acel": int, "medida_acel": double,
 *   "comp_mag":  int, "medida_mag":  double }
 */
public class ActividadComoServidorBluetooth extends Activity implements Runnable {

    // ── Instrumentos ─────────────────────────────────────────────────────────
    private Acelerometro acelerometro;
    private Gaussimetro  gaussimetro;

    // ── Botones de componente aceleración ─────────────────────────────────────
    private Boton botonAx, botonAy, botonAz, botonA;
    // ── Botones de componente campo magnético ─────────────────────────────────
    private Boton botonBx, botonBy, botonBz, botonB;

    // ── Controles UI ──────────────────────────────────────────────────────────
    private TextView textviewRol, textviewAviso;
    private Button   botonEmpezar;

    // ── Config ────────────────────────────────────────────────────────────────
    private int tamanoLetra;
    private final int COLOR_SERVIDOR = Color.rgb(156, 220, 80);

    // ── Hilo ──────────────────────────────────────────────────────────────────
    private final Handler myHandler = new Handler();
    private Thread  hilo;
    private boolean corriendo;
    private final long PERIODO_MS = 100;

    // ── Bluetooth ─────────────────────────────────────────────────────────────
    private ServidorBluetooth servidor;

    // ── Componentes seleccionados ─────────────────────────────────────────────
    private int compAcel = 4;
    private int compMag  = 4;

    // ── Ciclo de vida ─────────────────────────────────────────────────────────
    @Override
    public void onCreate(Bundle icicle) {
        super.onCreate(icicle);
        tamanoLetra = (int)(0.8 * AlmacenDatosRAM.tamanoLetraResolucionIncluida);
        creacionElementosGUI();
        setContentView(crearGUI());
        eventos();
        hilo = new Thread(this);
    }

    // ── GUI ───────────────────────────────────────────────────────────────────
    private void creacionElementosGUI() {

        acelerometro = new Acelerometro(this);
        acelerometro.setAngulosSectores(50, 100, 100);
        acelerometro.setColorFranjaDinámica(Color.rgb(0, 255, 0));
        acelerometro.captarSensor(this);

        gaussimetro = new Gaussimetro(this);
        gaussimetro.setAngulosSectores(80, 90, 80);
        gaussimetro.setColorFranjaDinámica(Color.rgb(0, 180, 255));
        gaussimetro.captarSensor(this);

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

        textviewRol = labelRol("SERVIDOR");

        textviewAviso = new TextView(this);
        textviewAviso.setTextSize(TypedValue.COMPLEX_UNIT_SP, tamanoLetra);
        textviewAviso.setBackgroundColor(Color.YELLOW);
        textviewAviso.setTextColor(Color.RED);
        textviewAviso.setGravity(Gravity.CENTER);
        textviewAviso.setText(AlmacenDatosRAM.conexion_bluetooth);

        botonEmpezar = new Button(this);
        botonEmpezar.setTextSize(TypedValue.COMPLEX_UNIT_SP, tamanoLetra);
        botonEmpezar.setText("EMPEZAR");
        botonEmpezar.getBackground().setColorFilter(COLOR_SERVIDOR, PorterDuff.Mode.MULTIPLY);

        crearServidor();
    }

    private Boton boton(int drawable) {
        Boton b = new Boton(this);
        b.setImagen(drawable);
        return b;
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

    private LinearLayout crearGUI() {
        /*
         * Layout vertical (weightSum=10):
         *  Fila 1 (0.8) — etiqueta ROL
         *  Fila 2 (4.0) — Acelerómetro (80%) | botones ax/ay/az/a (20%)
         *  Fila 3 (4.0) — Gaussímetro  (80%) | botones bx/by/bz/b  (20%)
         *  Fila 4 (0.4) — aviso estado BT
         *  Fila 5 (0.8) — botón EMPEZAR
         */
        LinearLayout principal = new LinearLayout(this);
        principal.setOrientation(LinearLayout.VERTICAL);
        principal.setBackgroundColor(Color.BLACK);
        principal.setWeightSum(10f);

        // Fila 1
        LinearLayout f1 = hl(Color.WHITE, Gravity.CENTER);
        row(principal, f1, 0.8f);
        f1.addView(textviewRol, mp_mp());

        // Fila 2 — acelerómetro
        LinearLayout f2 = hl(Color.WHITE, 0);
        f2.setWeightSum(10f);
        row(principal, f2, 4.0f);

        LinearLayout colAcelIzq = new LinearLayout(this);
        LinearLayout colAcelDer = new LinearLayout(this);
        colAcelDer.setOrientation(LinearLayout.VERTICAL);
        colAcelDer.setWeightSum(4f);
        f2.addView(colAcelIzq, wh(0, ViewGroup.LayoutParams.MATCH_PARENT, 8f));
        f2.addView(colAcelDer, wh(0, ViewGroup.LayoutParams.MATCH_PARENT, 2f));
        colAcelIzq.addView(acelerometro);
        for (Boton b : new Boton[]{botonAx, botonAy, botonAz, botonA}) {
            colAcelDer.addView(b, new LinearLayout.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT, 0) {{ weight = 1f; }});
        }

        // Fila 3 — gaussímetro
        LinearLayout f3 = hl(Color.WHITE, 0);
        f3.setWeightSum(10f);
        row(principal, f3, 4.0f);

        LinearLayout colMagIzq = new LinearLayout(this);
        LinearLayout colMagDer = new LinearLayout(this);
        colMagDer.setOrientation(LinearLayout.VERTICAL);
        colMagDer.setWeightSum(4f);
        f3.addView(colMagIzq, wh(0, ViewGroup.LayoutParams.MATCH_PARENT, 8f));
        f3.addView(colMagDer, wh(0, ViewGroup.LayoutParams.MATCH_PARENT, 2f));
        colMagIzq.addView(gaussimetro);
        for (Boton b : new Boton[]{botonBx, botonBy, botonBz, botonB}) {
            colMagDer.addView(b, new LinearLayout.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT, 0) {{ weight = 1f; }});
        }

        // Fila 4
        LinearLayout f4 = hl(Color.WHITE, 0);
        row(principal, f4, 0.4f);
        f4.addView(textviewAviso, mp_mp());

        // Fila 5
        LinearLayout f5 = hl(Color.WHITE, 0);
        row(principal, f5, 0.8f);
        f5.addView(botonEmpezar, wh(0, ViewGroup.LayoutParams.MATCH_PARENT, 1f));

        return principal;
    }

    // ── Eventos ───────────────────────────────────────────────────────────────
    private void eventos() {
        botonEmpezar.setOnClickListener(v -> {
            empezarComunicacionConCliente();
            hilo.start();
            botonEmpezar.setEnabled(false);
        });

        botonAx.setOnClickListener(v -> { acelerometro.setComponenteAcelerometro(1); compAcel = 1; });
        botonAy.setOnClickListener(v -> { acelerometro.setComponenteAcelerometro(2); compAcel = 2; });
        botonAz.setOnClickListener(v -> { acelerometro.setComponenteAcelerometro(3); compAcel = 3; });
        botonA .setOnClickListener(v -> { acelerometro.setComponenteAcelerometro(4); compAcel = 4; });

        botonBx.setOnClickListener(v -> { gaussimetro.setComponenteGaussimetro(1); compMag = 1; });
        botonBy.setOnClickListener(v -> { gaussimetro.setComponenteGaussimetro(2); compMag = 2; });
        botonBz.setOnClickListener(v -> { gaussimetro.setComponenteGaussimetro(3); compMag = 3; });
        botonB .setOnClickListener(v -> { gaussimetro.setComponenteGaussimetro(4); compMag = 4; });
    }

    // ── Bluetooth ─────────────────────────────────────────────────────────────
    private void crearServidor() {
        servidor = new ServidorBluetooth();
        servidor.abrirSocketServidor();
    }

    private void empezarComunicacionConCliente() {
        servidor.abrirSocketCliente();
        servidor.abrirFlujoSalida();
        servidor.abrirFlujoEntrada();
    }

    private void terminarComunicacionConCliente() {
        servidor.cerrarFlujoSalida();
        servidor.cerrarFlujoEntrada();
        servidor.cerrarSocketCliente();
    }

    // ── Hilo de comunicación ──────────────────────────────────────────────────
    @Override
    public void run() {
        corriendo = true;
        while (corriendo) {
            try { Thread.sleep(PERIODO_MS); } catch (InterruptedException e) { e.printStackTrace(); }
            enviarJSON();
            AlmacenDatosRAM.conexion_bluetooth = " Enviando datos al cliente ...";
            myHandler.post(() -> textviewAviso.setText(AlmacenDatosRAM.conexion_bluetooth));
        }
        AlmacenDatosRAM.conexion_bluetooth = " ";
    }

    private void enviarJSON() {
        JSONObject obj = new JSONObject();
        try {
            obj.put("comp_acel",   compAcel);
            obj.put("medida_acel", acelerometro.getMedida());
            obj.put("comp_mag",    compMag);
            obj.put("medida_mag",  gaussimetro.getMedida());
        } catch (JSONException e) { e.printStackTrace(); }
        servidor.escribirBytes(obj.toString().getBytes());
    }

    // ── Ciclo de vida ─────────────────────────────────────────────────────────
    @Override protected void onPause()   { super.onPause();   corriendo = false; }
    @Override protected void onDestroy() { super.onDestroy(); terminarComunicacionConCliente(); }

    // ── Helpers layout ────────────────────────────────────────────────────────
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