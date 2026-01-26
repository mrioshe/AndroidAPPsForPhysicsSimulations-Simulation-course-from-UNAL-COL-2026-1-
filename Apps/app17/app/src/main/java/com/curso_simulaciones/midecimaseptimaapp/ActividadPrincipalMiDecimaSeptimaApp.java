package com.curso_simulaciones.midecimaseptimaapp;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;

import com.curso_simulaciones.midecimaseptimaapp.datos.AlmacenDatosRAM;
import com.curso_simulaciones.midecimaseptimaapp.actividades_secundarias.ActividadSecundaria_1;
import com.curso_simulaciones.midecimaseptimaapp.actividades_secundarias.ActividadSecundaria_2;
import com.curso_simulaciones.midecimaseptimaapp.actividades_secundarias.ActividadSecundaria_3;

public class ActividadPrincipalMiDecimaSeptimaApp extends Activity {

    // Botones (12)
    private Button botonUno, botonDos, botonTres, botonCuatro,
            botonCinco, botonSeis, botonSiete, botonOcho,
            botonNueve, botonDiez, botonOnce, botonDoce;

    // Tamaño de letra responsivo
    private int tamanoLetraResolucionIncluida;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Calcular tamaño de letra y guardar en AlmacenDatosRAM
        gestionarResolucion();

        // Crear objetos GUI (botones)
        crearElementosGUI();

        // Pegar la vista principal
        ViewGroup.LayoutParams parametro_layout_principal =
                new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        this.setContentView(crearGUI(), parametro_layout_principal);

        // Asociar eventos
        eventos();
    }

    @Override
    protected void onResume() {
        super.onResume();
        // Si desde la actividad secundaria se guardaron datos, habilitar botonTres
        if (AlmacenDatosRAM.datosIngresados) {
            botonTres.setEnabled(true);
        }
    }

    @Override
    protected void onDestroy() {
        // Al salir, opcionalmente limpiar datos en RAM (según la guía se recomienda)
        AlmacenDatosRAM.datosIngresados = false;
        AlmacenDatosRAM.campo1 = "";
        AlmacenDatosRAM.campo2 = "";
        AlmacenDatosRAM.campo3 = "";
        AlmacenDatosRAM.campo4 = "";
        this.finish();
        super.onDestroy();
    }

    /* Crear elementos (botones) */
    private void crearElementosGUI() {
        // Usar tamaño de letra calculado
        tamanoLetraResolucionIncluida = AlmacenDatosRAM.tamanoLetraResolucionIncluida;

        // Crear 12 botones con estilo similar
        botonUno = crearBoton("UNO");
        botonDos = crearBoton("DOS");
        botonTres = crearBoton("TRES");
        botonCuatro = crearBoton("CUATRO");
        botonCinco = crearBoton("CINCO");
        botonSeis = crearBoton("SEIS");
        botonSiete = crearBoton("SIETE");
        botonOcho = crearBoton("OCHO");
        botonNueve = crearBoton("NUEVE");
        botonDiez = crearBoton("DIEZ");
        botonOnce = crearBoton("ONCE");
        botonDoce = crearBoton("DOCE");

        // Inicialmente sólo UNO y DOS habilitados, TRES se habilitará cuando haya datos
        botonUno.setEnabled(true);
        botonDos.setEnabled(true);
        botonTres.setEnabled(false);
        botonCuatro.setEnabled(false);
        botonCinco.setEnabled(false);
        botonSeis.setEnabled(false);
        botonSiete.setEnabled(false);
        botonOcho.setEnabled(false);
        botonNueve.setEnabled(false);
        botonDiez.setEnabled(false);
        botonOnce.setEnabled(false);
        botonDoce.setEnabled(false);
    }

    /* Helper para crear un botón con estilo */
    private Button crearBoton(String texto) {
        Button b = new Button(this);
        b.setText(texto);
        b.setTextSize(TypedValue.COMPLEX_UNIT_SP, tamanoLetraResolucionIncluida);
        b.getBackground().setColorFilter(Color.rgb(220, 156, 80), PorterDuff.Mode.MULTIPLY);
        return b;
    }

    /* Crear la GUI principal: dos columnas de 6 botones cada una */
    private LinearLayout crearGUI() {
        LinearLayout linearPrincipal = new LinearLayout(this);
        linearPrincipal.setOrientation(LinearLayout.HORIZONTAL);
        linearPrincipal.setBackgroundColor(Color.WHITE);
        linearPrincipal.setWeightSum(2.0f);

        // Columnas izquierda y derecha
        LinearLayout columnaIzquierda = new LinearLayout(this);
        columnaIzquierda.setOrientation(LinearLayout.VERTICAL);
        columnaIzquierda.setBackgroundColor(Color.LTGRAY);

        LinearLayout columnaDerecha = new LinearLayout(this);
        columnaDerecha.setOrientation(LinearLayout.VERTICAL);
        columnaDerecha.setBackgroundColor(Color.GRAY);

        LinearLayout.LayoutParams paramsColumna = new LinearLayout.LayoutParams(0, ViewGroup.LayoutParams.MATCH_PARENT);
        paramsColumna.weight = 1.0f;
        paramsColumna.setMargins(8, 8, 8, 8);

        // Parámetros para cada botón dentro de la columna (peso para repartir 6 botones)
        LinearLayout.LayoutParams paramsBoton = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 0);
        paramsBoton.weight = 1.0f;
        paramsBoton.setMargins(6, 6, 6, 6);

        // Agregar 6 botones a cada columna (izq: UNO..SEIS, der: SIETE..DOCE)
        columnaIzquierda.addView(botonUno, paramsBoton);
        columnaIzquierda.addView(botonDos, paramsBoton);
        columnaIzquierda.addView(botonTres, paramsBoton);
        columnaIzquierda.addView(botonCuatro, paramsBoton);
        columnaIzquierda.addView(botonCinco, paramsBoton);
        columnaIzquierda.addView(botonSeis, paramsBoton);

        columnaDerecha.addView(botonSiete, paramsBoton);
        columnaDerecha.addView(botonOcho, paramsBoton);
        columnaDerecha.addView(botonNueve, paramsBoton);
        columnaDerecha.addView(botonDiez, paramsBoton);
        columnaDerecha.addView(botonOnce, paramsBoton);
        columnaDerecha.addView(botonDoce, paramsBoton);

        linearPrincipal.addView(columnaIzquierda, paramsColumna);
        linearPrincipal.addView(columnaDerecha, paramsColumna);

        return linearPrincipal;
    }

    /* Asociar eventos a botones */
    private void eventos() {
        // Boton UNO -> lanzar ActividadSecundaria_1
        botonUno.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(ActividadPrincipalMiDecimaSeptimaApp.this, ActividadSecundaria_1.class);
                startActivity(i);
            }
        });

        // Boton DOS -> lanzar ActividadSecundaria_2 (donde se ingresan datos)
        botonDos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(ActividadPrincipalMiDecimaSeptimaApp.this, ActividadSecundaria_2.class);
                startActivity(i);
            }
        });

        // Boton TRES -> lanzar ActividadSecundaria_3 (muestra datos ingresados)
        botonTres.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(ActividadPrincipalMiDecimaSeptimaApp.this, ActividadSecundaria_3.class);
                startActivity(i);
            }
        });

        // Los demás botones pueden tener comportamientos de ejemplo (aquí se dejan sin acción)
    }

    /* Gestionar resolución para tamaño de letra y guardar en AlmacenDatosRAM */
    private void gestionarResolucion() {
        DisplayMetrics displayMetrics = this.getApplicationContext().getResources().getDisplayMetrics();
        int alto = displayMetrics.heightPixels;
        int ancho = displayMetrics.widthPixels;
        int dimensionReferencia = (alto > ancho) ? ancho : alto;
        int tamanoLetra = dimensionReferencia / 20;
        tamanoLetraResolucionIncluida = (int) (tamanoLetra / displayMetrics.scaledDensity);
        AlmacenDatosRAM.tamanoLetraResolucionIncluida = tamanoLetraResolucionIncluida;
    }
}