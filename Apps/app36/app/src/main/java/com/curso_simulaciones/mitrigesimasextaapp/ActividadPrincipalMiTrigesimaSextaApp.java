package com.curso_simulaciones.mitrigesimasextaapp;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;

import com.curso_simulaciones.mitrigesimasextaapp.actividades_secundarias.ActividadComoClienteBluetooth;
import com.curso_simulaciones.mitrigesimasextaapp.actividades_secundarias.ActividadComoServidorBluetooth;
import com.curso_simulaciones.mitrigesimasextaapp.datos.AlmacenDatosRAM;

public class ActividadPrincipalMiTrigesimaSextaApp extends Activity {
    private Button botonCliente, botonServidor, botonSalir;
    private int tamanoLetraResolucionIncluida;

    @Override
    public void onCreate(Bundle icicle) {
        super.onCreate(icicle);

        gestionarResolucion();
        creacionElementosGUI();
        setContentView(crearGUI());
        eventos();
    }

    private void gestionarResolucion() {
        DisplayMetrics displayMetrics = this.getApplicationContext().getResources().getDisplayMetrics();
        AlmacenDatosRAM.alto = displayMetrics.heightPixels;
        AlmacenDatosRAM.ancho = displayMetrics.widthPixels;

        int dimensionReferencia;
        if (AlmacenDatosRAM.alto > AlmacenDatosRAM.ancho) {
            dimensionReferencia = AlmacenDatosRAM.ancho;
        } else {
            dimensionReferencia = AlmacenDatosRAM.alto;
        }

        AlmacenDatosRAM.dimensionReferencia = dimensionReferencia;
        int tamanoLetra = dimensionReferencia / 25;
        AlmacenDatosRAM.tamanoLetraResolucionIncluida = (int) (tamanoLetra / displayMetrics.scaledDensity);
        tamanoLetraResolucionIncluida = AlmacenDatosRAM.tamanoLetraResolucionIncluida;
    }

    private void creacionElementosGUI() {
        // Botón Cliente
        botonCliente = new Button(this);
        botonCliente.setText("CLIENTE");
        botonCliente.setTextSize(TypedValue.COMPLEX_UNIT_SP, tamanoLetraResolucionIncluida);
        botonCliente.getBackground().setColorFilter(Color.rgb(100, 200, 255), PorterDuff.Mode.MULTIPLY);

        // Botón Servidor
        botonServidor = new Button(this);
        botonServidor.setText("SERVIDOR");
        botonServidor.setTextSize(TypedValue.COMPLEX_UNIT_SP, tamanoLetraResolucionIncluida);
        botonServidor.getBackground().setColorFilter(Color.rgb(255, 180, 100), PorterDuff.Mode.MULTIPLY);

        // Botón Salir
        botonSalir = new Button(this);
        botonSalir.setText("SALIR");
        botonSalir.setTextSize(TypedValue.COMPLEX_UNIT_SP, tamanoLetraResolucionIncluida);
        botonSalir.getBackground().setColorFilter(Color.rgb(255, 100, 100), PorterDuff.Mode.MULTIPLY);
    }

    private LinearLayout crearGUI() {
        LinearLayout linearLayoutPrincipal = new LinearLayout(this);
        linearLayoutPrincipal.setOrientation(LinearLayout.VERTICAL);
        linearLayoutPrincipal.setBackgroundColor(Color.rgb(30, 40, 60));
        linearLayoutPrincipal.setWeightSum(10f);

        // Fila 1 - Espacio superior
        LinearLayout linearLayoutFilaUno = new LinearLayout(this);
        linearLayoutFilaUno.setBackgroundColor(Color.rgb(30, 40, 60));

        // Fila 2 - Botones
        LinearLayout linearLayoutFilaDos = new LinearLayout(this);
        linearLayoutFilaDos.setOrientation(LinearLayout.HORIZONTAL);
        linearLayoutFilaDos.setBackgroundColor(Color.rgb(30, 40, 60));
        linearLayoutFilaDos.setWeightSum(3f);

        // Parámetros de las filas
        LinearLayout.LayoutParams parametrosFilaUno = new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT, 0);
        parametrosFilaUno.weight = 3.0f;
        linearLayoutFilaUno.setLayoutParams(parametrosFilaUno);

        LinearLayout.LayoutParams parametrosFilaDos = new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT, 0);
        parametrosFilaDos.weight = 7.0f;
        linearLayoutFilaDos.setLayoutParams(parametrosFilaDos);

        linearLayoutPrincipal.addView(linearLayoutFilaUno);
        linearLayoutPrincipal.addView(linearLayoutFilaDos);

        // Pegar botones en fila 2
        LinearLayout.LayoutParams parametrosPegadoBoton = new LinearLayout.LayoutParams(
                0, ViewGroup.LayoutParams.MATCH_PARENT);
        parametrosPegadoBoton.weight = 1.0f;
        parametrosPegadoBoton.setMargins(20, 20, 20, 20);

        linearLayoutFilaDos.addView(botonCliente, parametrosPegadoBoton);
        linearLayoutFilaDos.addView(botonServidor, parametrosPegadoBoton);
        linearLayoutFilaDos.addView(botonSalir, parametrosPegadoBoton);

        return linearLayoutPrincipal;
    }

    private void eventos() {
        botonCliente.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                AlmacenDatosRAM.rol = "CLIENTE";
                lanzarCliente();
            }
        });

        botonServidor.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                AlmacenDatosRAM.rol = "SERVIDOR";
                lanzarServidor();
            }
        });

        botonSalir.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void lanzarCliente() {
        Intent intent = new Intent(this, ActividadComoClienteBluetooth.class);
        startActivity(intent);
    }

    private void lanzarServidor() {
        Intent intent = new Intent(this, ActividadComoServidorBluetooth.class);
        startActivity(intent);
    }
}