package com.curso_simulaciones.micuadragesimasegundaapp;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.curso_simulaciones.micuadragesimasegundaapp.actividades_secundarias.ActividadComunicacion;
import com.curso_simulaciones.micuadragesimasegundaapp.actividades_secundarias.ActividadConfiguracion;
import com.curso_simulaciones.micuadragesimasegundaapp.datos.AlmacenDatosRAM;
import com.curso_simulaciones.micuadragesimasegundaapp.utilidades.Boton;

/**
 * Actividad principal de MiCuadragesimaSegundaApp.
 *
 * Responsabilidades:
 *  1. Calcular resolución de pantalla → AlmacenDatosRAM.
 *  2. Cargar configuración MQTT persistida en SharedPreferences.
 *  3. Mostrar pantalla de bienvenida con imagen IoT.
 *  4. Lanzar ActividadComunicacion (elección PUB/SUB) o ActividadConfiguracion.
 *
 * Icono lanzador: logo.png (en res/mipmap o res/drawable).
 */
public class ActividadPrincipalMiCuadragesimaSegundaApp extends Activity {

    private Boton entrar, salir, ajustes;
    private LinearLayout linear_layout_segunda_fila;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        gestionarResolucion();
        crearElementosGUI();

        setContentView(crearGUI(),
                new ViewGroup.LayoutParams(
                        ViewGroup.LayoutParams.MATCH_PARENT,
                        ViewGroup.LayoutParams.MATCH_PARENT));

        // Cargar preferencias MQTT persistidas
        actualizarPreferenciasMQTT();

        eventos();
    }

    // ── Resolución ────────────────────────────────────────────────────────────
    private void gestionarResolucion() {
        DisplayMetrics dm =
                getApplicationContext().getResources().getDisplayMetrics();

        int alto  = dm.heightPixels;
        int ancho = dm.widthPixels;
        AlmacenDatosRAM.ancho = ancho;
        AlmacenDatosRAM.alto  = alto;

        int dimRef = (alto > ancho) ? ancho : alto;
        AlmacenDatosRAM.dimensionReferencia = dimRef;

        int tamanoLetra = dimRef / 20;
        AlmacenDatosRAM.tamanoLetraResolucionIncluida =
                (int)(tamanoLetra / dm.scaledDensity);
    }

    // ── GUI ───────────────────────────────────────────────────────────────────
    private void crearElementosGUI() {
        entrar  = new Boton(this); entrar.setImagen(R.drawable.entrar);
        salir   = new Boton(this); salir.setImagen(R.drawable.salir);
        ajustes = new Boton(this); ajustes.setImagen(R.drawable.configuracion);
    }

    private LinearLayout crearGUI() {
        LinearLayout principal = new LinearLayout(this);
        principal.setOrientation(LinearLayout.VERTICAL);
        principal.setGravity(Gravity.FILL);
        principal.setBackgroundColor(Color.WHITE);
        principal.setWeightSum(10f);

        // Fila 1: imagen de fondo IoT
        LinearLayout fila1 = new LinearLayout(this);
        fila1.setOrientation(LinearLayout.HORIZONTAL);
        fila1.setGravity(Gravity.FILL);
        Drawable fondo = getResources().getDrawable(R.drawable.comunicacion_iot);
        fila1.setBackgroundDrawable(fondo);
        LinearLayout.LayoutParams p1 = new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT, 0);
        p1.weight = 9f;
        fila1.setLayoutParams(p1);

        // Fila 2: botones ENTRAR + AJUSTES (luego SALIR + AJUSTES)
        linear_layout_segunda_fila = new LinearLayout(this);
        linear_layout_segunda_fila.setOrientation(LinearLayout.HORIZONTAL);
        linear_layout_segunda_fila.setGravity(Gravity.FILL);
        linear_layout_segunda_fila.setWeightSum(2f);
        LinearLayout.LayoutParams p2 = new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT, 0);
        p2.weight = 1f;
        linear_layout_segunda_fila.setLayoutParams(p2);

        LinearLayout.LayoutParams pBtn = new LinearLayout.LayoutParams(
                0, ViewGroup.LayoutParams.MATCH_PARENT);
        pBtn.weight = 1f;
        entrar.setLayoutParams(pBtn);
        salir.setLayoutParams(pBtn);
        ajustes.setLayoutParams(pBtn);

        linear_layout_segunda_fila.addView(entrar);
        linear_layout_segunda_fila.addView(ajustes);

        principal.addView(fila1);
        principal.addView(linear_layout_segunda_fila);

        return principal;
    }

    // ── Eventos ───────────────────────────────────────────────────────────────
    private void eventos() {

        entrar.setOnClickListener(v -> {
            lanzarActividadComunicacion();
            linear_layout_segunda_fila.removeAllViews();
            linear_layout_segunda_fila.addView(salir);
            linear_layout_segunda_fila.addView(ajustes);
        });

        salir.setOnClickListener(v -> finish());

        ajustes.setOnClickListener(v -> lanzarActividadConfiguracion());
    }

    private void lanzarActividadComunicacion() {
        startActivity(new Intent(this, ActividadComunicacion.class));
    }

    private void lanzarActividadConfiguracion() {
        startActivity(new Intent(this, ActividadConfiguracion.class));
    }

    // ── Preferencias MQTT ─────────────────────────────────────────────────────
    private void actualizarPreferenciasMQTT() {
        SharedPreferences prefs =
                getSharedPreferences("MisPreferencias", Context.MODE_PRIVATE);
        AlmacenDatosRAM.MQTTHOST = prefs.getString("broker",  "");
        AlmacenDatosRAM.USERNAME = prefs.getString("usuario", "");
        AlmacenDatosRAM.PASSWORD = prefs.getString("pasword", "");
        AlmacenDatosRAM.topicStr = prefs.getString("topico",  "");
    }

    // ── Ciclo de vida ─────────────────────────────────────────────────────────
    @Override
    protected void onDestroy() {
        super.onDestroy();
        finish();
    }
}
