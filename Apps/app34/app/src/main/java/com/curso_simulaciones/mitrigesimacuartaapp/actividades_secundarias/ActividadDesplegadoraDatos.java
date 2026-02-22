package com.curso_simulaciones.mitrigesimacuartaapp.actividades_secundarias;

import android.app.Activity;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.os.Handler;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;

import com.curso_simulaciones.mitrigesimacuartaapp.datos.AlmacenDatosRAM;
import com.curso_simulaciones.mitrigesimacuartaapp.datos.GuardarDatosPersistentesTXT;
import com.curso_simulaciones.mitrigesimacuartaapp.gui_auxiliares.DialogoSalir;
import com.curso_simulaciones.mitrigesimacuartaapp.utilidades.Acelerometro;
import com.curso_simulaciones.mitrigesimacuartaapp.utilidades.TablaSimple;

public class ActividadDesplegadoraDatos extends Activity {
    private Acelerometro acelerometro;
    private TablaSimple tabla;
    private Button botonGuardar, botonEmpezar;
    private HiloAnimacion hilo;
    private final Handler myHandler = new Handler();
    public boolean activarBotones = false;
    private int nDatos = -1;
    private GuardarDatosPersistentesTXT archivoTxt;
    private int tamanoLetraResolucionIncluida;
    private DialogoSalir dialogoSalir;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        gestionandoResolucion();
        crearElementosGUI();

        ViewGroup.LayoutParams parametroLayoutPrincipal = new ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT);

        this.setContentView(crearGUI(), parametroLayoutPrincipal);

        archivoTxt = new GuardarDatosPersistentesTXT();
        dialogoSalir = new DialogoSalir(this);
        eventos();
    }

    private void gestionandoResolucion() {
        tamanoLetraResolucionIncluida = AlmacenDatosRAM.tamanoLetraResolucionIncluida;
    }

    private void crearElementosGUI() {
        // Gauge - Acelerómetro
        acelerometro = new Acelerometro(this);
        acelerometro.setUnidades("m/s²");
        acelerometro.setAngulosSectores(20, 190, 40);
        acelerometro.setColorSectores(Color.GREEN, Color.argb(100, 200, 200, 0), Color.YELLOW);
        acelerometro.setColorFranjaDinámica(Color.RED);

        // Tabla con 6 columnas
        tabla = new TablaSimple(this);
        tabla.setEtiquetaColumnas("# Dato", "Tiempo (s)", "ax (m/s²)", "ay (m/s²)", "az (m/s²)", "a (m/s²)");

        botonGuardar = new Button(this);
        botonGuardar.setTextSize(TypedValue.COMPLEX_UNIT_SP, tamanoLetraResolucionIncluida);
        botonGuardar.getBackground().setColorFilter(Color.rgb(255, 255, 100), PorterDuff.Mode.MULTIPLY);
        botonGuardar.setText("GUARDAR");
        botonGuardar.setEnabled(false);

        botonEmpezar = new Button(this);
        botonEmpezar.setTextSize(TypedValue.COMPLEX_UNIT_SP, tamanoLetraResolucionIncluida);
        botonEmpezar.getBackground().setColorFilter(Color.rgb(255, 255, 100), PorterDuff.Mode.MULTIPLY);
        botonEmpezar.setText("EMPEZAR");
    }

    private LinearLayout crearGUI() {
        LinearLayout linearLayoutPrincipal = new LinearLayout(this);
        linearLayoutPrincipal.setOrientation(LinearLayout.HORIZONTAL);
        linearLayoutPrincipal.setGravity(Gravity.CENTER_HORIZONTAL | Gravity.FILL);
        linearLayoutPrincipal.setBackgroundColor(Color.WHITE);
        linearLayoutPrincipal.setWeightSum(10.0f);

        // LinearLayout primera columna (Gauge)
        LinearLayout linearLayoutPrimeraColumna = new LinearLayout(this);
        linearLayoutPrimeraColumna.setOrientation(LinearLayout.HORIZONTAL);
        linearLayoutPrimeraColumna.setGravity(Gravity.FILL);
        linearLayoutPrimeraColumna.setBackgroundColor(Color.rgb(245, 245, 245));

        // LinearLayout segunda columna (Tabla y botones)
        LinearLayout linearLayoutSegundaColumna = new LinearLayout(this);
        linearLayoutSegundaColumna.setOrientation(LinearLayout.VERTICAL);
        linearLayoutSegundaColumna.setGravity(Gravity.FILL);
        linearLayoutSegundaColumna.setBackgroundColor(Color.rgb(245, 245, 245));
        linearLayoutSegundaColumna.setWeightSum(10.0f);

        // LinearLayout primera fila segunda columna (Tabla)
        LinearLayout linearLayoutPrimeraFilaSegundaColumna = new LinearLayout(this);
        linearLayoutPrimeraFilaSegundaColumna.setOrientation(LinearLayout.VERTICAL);
        linearLayoutPrimeraFilaSegundaColumna.setGravity(Gravity.FILL);

        // LinearLayout segunda fila segunda columna (Botones)
        LinearLayout linearLayoutSegundaFilaSegundaColumna = new LinearLayout(this);
        linearLayoutSegundaFilaSegundaColumna.setOrientation(LinearLayout.HORIZONTAL);
        linearLayoutSegundaFilaSegundaColumna.setGravity(Gravity.FILL);
        linearLayoutSegundaFilaSegundaColumna.setWeightSum(2.0f);

        // Pegar columna primera al principal
        LinearLayout.LayoutParams parametrosPrimeraColumna = new LinearLayout.LayoutParams(
                0, ViewGroup.LayoutParams.MATCH_PARENT);
        parametrosPrimeraColumna.weight = 5.0f;
        parametrosPrimeraColumna.setMargins(20, 20, 20, 20);
        linearLayoutPrimeraColumna.setLayoutParams(parametrosPrimeraColumna);
        linearLayoutPrincipal.addView(linearLayoutPrimeraColumna);

        // Pegar columna segunda al principal
        LinearLayout.LayoutParams parametrosSegundaColumna = new LinearLayout.LayoutParams(
                0, ViewGroup.LayoutParams.MATCH_PARENT);
        parametrosSegundaColumna.weight = 5.0f;
        parametrosSegundaColumna.setMargins(20, 20, 20, 20);
        linearLayoutSegundaColumna.setLayoutParams(parametrosSegundaColumna);
        linearLayoutPrincipal.addView(linearLayoutSegundaColumna);

        // Pegar acelerómetro en primera columna
        linearLayoutPrimeraColumna.addView(acelerometro);

        // Pegar primera fila en segunda columna
        LinearLayout.LayoutParams parametrosPrimeraFilaSegundaColumna = new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT, 0);
        parametrosPrimeraFilaSegundaColumna.weight = 8.0f;
        parametrosPrimeraFilaSegundaColumna.setMargins(20, 20, 20, 20);
        linearLayoutPrimeraFilaSegundaColumna.setLayoutParams(parametrosPrimeraFilaSegundaColumna);
        linearLayoutSegundaColumna.addView(linearLayoutPrimeraFilaSegundaColumna);

        // Pegar segunda fila en segunda columna
        LinearLayout.LayoutParams parametrosSegundaFilaSegundaColumna = new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT, 0);
        parametrosSegundaFilaSegundaColumna.weight = 2.0f;
        parametrosSegundaFilaSegundaColumna.setMargins(20, 20, 20, 20);
        linearLayoutSegundaFilaSegundaColumna.setLayoutParams(parametrosSegundaFilaSegundaColumna);
        linearLayoutSegundaColumna.addView(linearLayoutSegundaFilaSegundaColumna);

        // Pegar tabla en la primera fila de la segunda columna
        linearLayoutPrimeraFilaSegundaColumna.addView(tabla);

        // Pegar los botones en la segunda fila de la segunda columna
        LinearLayout.LayoutParams parametrosBotonesSegundaFilaSegundaColumna = new LinearLayout.LayoutParams(
                0, ViewGroup.LayoutParams.MATCH_PARENT);
        parametrosBotonesSegundaFilaSegundaColumna.weight = 1.0f;
        botonEmpezar.setLayoutParams(parametrosBotonesSegundaFilaSegundaColumna);
        botonGuardar.setLayoutParams(parametrosBotonesSegundaFilaSegundaColumna);
        linearLayoutSegundaFilaSegundaColumna.addView(botonEmpezar);
        linearLayoutSegundaFilaSegundaColumna.addView(botonGuardar);

        return linearLayoutPrincipal;
    }

    private void eventos() {
        botonGuardar.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                guardar();
            }
        });

        botonEmpezar.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                empezar();
            }
        });
    }

    private void empezar() {
        if (hilo != null)
            hilo = null;

        nDatos = -1;
        AlmacenDatosRAM.tiempo = 0;
        tabla.borrar();
        archivoTxt.borrarDatos();
        botonEmpezar.setEnabled(false);
        botonGuardar.setEnabled(false);
        activarBotones = false;

        hilo = new HiloAnimacion(this);
        hilo.start();
    }

    private void guardar() {
        archivoTxt.guardar(this, "almacen_mis_datos/acelerometro/");
        activarBotones = false;
        botonEmpezar.setEnabled(true);
        botonGuardar.setEnabled(false);
    }

    public void hacerTrabajoDuro() {
        myHandler.post(updateRunnable);
    }

    final Runnable updateRunnable = new Runnable() {
        public void run() {
            if (activarBotones) {
                botonGuardar.setEnabled(true);
                botonEmpezar.setEnabled(true);
            }

            if (nDatos < AlmacenDatosRAM.nDatos) {
                nDatos++;
                tabla.enviarDatos(
                        nDatos,
                        AlmacenDatosRAM.tiempo,
                        AlmacenDatosRAM.aceleracionX,
                        AlmacenDatosRAM.aceleracionY,
                        AlmacenDatosRAM.aceleracionZ,
                        AlmacenDatosRAM.aceleracionTotal
                );

                if (nDatos > 0) {
                    archivoTxt.llenarDatos(
                            nDatos,
                            AlmacenDatosRAM.tiempo,
                            AlmacenDatosRAM.aceleracionX,
                            AlmacenDatosRAM.aceleracionY,
                            AlmacenDatosRAM.aceleracionZ,
                            AlmacenDatosRAM.aceleracionTotal
                    );
                }
            }
        }
    };

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            dialogoSalir.mostrarPopMenu();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
}