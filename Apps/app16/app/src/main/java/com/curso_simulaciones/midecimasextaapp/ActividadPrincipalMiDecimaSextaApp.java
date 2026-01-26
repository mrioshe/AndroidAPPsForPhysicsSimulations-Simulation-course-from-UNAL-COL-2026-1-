package com.curso_simulaciones.midecimasextaapp;

import android.app.Activity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.widget.Button;
import android.widget.LinearLayout;

public class ActividadPrincipalMiDecimaSextaApp extends Activity {

    // Tamaño de letra calculado según resolución
    private int tamanoLetraResolucionIncluida;

    // Elementos de la GUI (botones)
    private Button botonUno, botonDos, botonTres, botonCuatro, botonCinco;

    // LinearLayout izquierdo (debe ser variable de instancia para cambiar su color desde eventos)
    private LinearLayout linearIzquierdo;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Calcular tamaño de letra responsivo
        gestionarResolucion();

        // Crear objetos GUI (botones)
        crearElementosGUI();

        // Preparar parámetros para pegar la vista principal
        ViewGroup.LayoutParams parametro_layout_principal =
                new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);

        // Pegar la GUI creada programáticamente
        this.setContentView(crearGUI(), parametro_layout_principal);

        // Asociar eventos a los botones
        eventos();
    }

    @Override
    protected void onDestroy() {
        this.finish();
        super.onDestroy();
    }

    /* método responsable de administrar el diseño de la GUI */
    private LinearLayout crearGUI() {

        // LinearLayout principal (amarillo), orientación horizontal
        LinearLayout linearPrincipal = new LinearLayout(this);
        linearPrincipal.setBackgroundColor(Color.YELLOW);
        linearPrincipal.setOrientation(LinearLayout.HORIZONTAL);
        linearPrincipal.setWeightSum(10.0f);

        // LinearLayout izquierdo (verde) - variable de instancia
        linearIzquierdo = new LinearLayout(this);
        linearIzquierdo.setBackgroundColor(Color.GREEN);

        // LinearLayout derecho (gris)
        LinearLayout linearDerecho = new LinearLayout(this);
        linearDerecho.setBackgroundColor(Color.GRAY);

        // Parámetros para pegar izquierdo (70%) y derecho (30%)
        LinearLayout.LayoutParams parametros_pegado_linear_izquierdo =
                new LinearLayout.LayoutParams(0, ViewGroup.LayoutParams.MATCH_PARENT);
        parametros_pegado_linear_izquierdo.weight = 7.0f;
        parametros_pegado_linear_izquierdo.setMargins(10, 10, 10, 10);

        LinearLayout.LayoutParams parametros_pegado_linear_derecho =
                new LinearLayout.LayoutParams(0, ViewGroup.LayoutParams.MATCH_PARENT);
        parametros_pegado_linear_derecho.weight = 3.0f;
        parametros_pegado_linear_derecho.setMargins(10, 10, 10, 10);

        // Agregar izquierdo y derecho al principal
        linearPrincipal.addView(linearIzquierdo, parametros_pegado_linear_izquierdo);
        linearPrincipal.addView(linearDerecho, parametros_pegado_linear_derecho);

        // --- Crear los 4 LinearLayout dentro del derecho (cada uno 25% del derecho) ---
        LinearLayout linearUno = new LinearLayout(this);
        linearUno.setBackgroundColor(Color.RED);

        LinearLayout linearDos = new LinearLayout(this);
        linearDos.setBackgroundColor(Color.BLUE);

        LinearLayout linearTres = new LinearLayout(this);
        linearTres.setBackgroundColor(Color.CYAN);

        LinearLayout linearCuatro = new LinearLayout(this);
        linearCuatro.setBackgroundColor(Color.MAGENTA);

        // Configurar el contenedor derecho para apilar verticalmente y repartir 4 partes iguales
        linearDerecho.setWeightSum(4.0f);
        linearDerecho.setOrientation(LinearLayout.VERTICAL);

        // Parámetros para pegar cada uno de los 4 LinearLayout dentro del derecho
        LinearLayout.LayoutParams parametros_pegado_linear_a_derecho =
                new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 0);
        parametros_pegado_linear_a_derecho.weight = 1.0f;
        parametros_pegado_linear_a_derecho.setMargins(10, 5, 10, 5);

        // Agregar los 4 contenedores al derecho
        linearDerecho.addView(linearUno, parametros_pegado_linear_a_derecho);
        linearDerecho.addView(linearDos, parametros_pegado_linear_a_derecho);
        linearDerecho.addView(linearTres, parametros_pegado_linear_a_derecho);
        linearDerecho.addView(linearCuatro, parametros_pegado_linear_a_derecho);

        // --- Pegar los botones en los contenedores de la derecha ---
        // Configurar cada sub-LinearLayout para contener botones horizontalmente
        linearUno.setWeightSum(2f);
        linearUno.setOrientation(LinearLayout.HORIZONTAL);

        linearDos.setWeightSum(1f);
        linearDos.setOrientation(LinearLayout.HORIZONTAL);

        linearTres.setWeightSum(1f);
        linearTres.setOrientation(LinearLayout.HORIZONTAL);

        linearCuatro.setWeightSum(1f);
        linearCuatro.setOrientation(LinearLayout.HORIZONTAL);

        // Parámetros para pegar botones dentro de los sub-containers (cada botón ocupa 1 peso)
        LinearLayout.LayoutParams parametros_pegado_botones =
                new LinearLayout.LayoutParams(0, ViewGroup.LayoutParams.MATCH_PARENT);
        parametros_pegado_botones.weight = 1.0f;
        parametros_pegado_botones.setMargins(10, 5, 10, 5);

        // Agregar botones a los contenedores
        linearUno.addView(botonUno, parametros_pegado_botones);
        linearUno.addView(botonDos, parametros_pegado_botones);

        linearDos.addView(botonTres, parametros_pegado_botones);

        linearTres.addView(botonCuatro, parametros_pegado_botones);

        linearCuatro.addView(botonCinco, parametros_pegado_botones);

        return linearPrincipal;
    }

    /* método responsable de la creación de los elementos de la GUI */
    private void crearElementosGUI() {

        // Botón UNO
        botonUno = new Button(this);
        botonUno.setTextSize(TypedValue.COMPLEX_UNIT_SP, tamanoLetraResolucionIncluida);
        botonUno.setText("UNO");
        // color de fondo con filtro (compatible)
        botonUno.getBackground().setColorFilter(Color.rgb(220, 156, 80), PorterDuff.Mode.MULTIPLY);

        // Botón DOS
        botonDos = new Button(this);
        botonDos.setTextSize(TypedValue.COMPLEX_UNIT_SP, tamanoLetraResolucionIncluida);
        botonDos.setText("DOS");
        botonDos.getBackground().setColorFilter(Color.rgb(220, 156, 80), PorterDuff.Mode.MULTIPLY);

        // Botón TRES
        botonTres = new Button(this);
        botonTres.setTextSize(TypedValue.COMPLEX_UNIT_SP, tamanoLetraResolucionIncluida);
        botonTres.setText("TRES");
        botonTres.getBackground().setColorFilter(Color.rgb(220, 156, 80), PorterDuff.Mode.MULTIPLY);

        // Botón CUATRO
        botonCuatro = new Button(this);
        botonCuatro.setTextSize(TypedValue.COMPLEX_UNIT_SP, tamanoLetraResolucionIncluida);
        botonCuatro.setText("CUATRO");
        botonCuatro.getBackground().setColorFilter(Color.rgb(220, 156, 80), PorterDuff.Mode.MULTIPLY);

        // Botón CINCO
        botonCinco = new Button(this);
        botonCinco.setTextSize(TypedValue.COMPLEX_UNIT_SP, tamanoLetraResolucionIncluida);
        botonCinco.setText("CINCO");
        botonCinco.getBackground().setColorFilter(Color.rgb(220, 156, 80), PorterDuff.Mode.MULTIPLY);
    }

    /* Administra los eventos de la GUI */
    private void eventos() {

        // evento del boton UNO -> pone el linear izquierdo en negro
        botonUno.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                linearIzquierdo.setBackgroundColor(Color.BLACK);
            }
        });

        // evento del boton DOS -> pone el linear izquierdo en blanco
        botonDos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                linearIzquierdo.setBackgroundColor(Color.WHITE);
            }
        });

        // evento del boton TRES -> rojo
        botonTres.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                linearIzquierdo.setBackgroundColor(Color.RED);
            }
        });

        // evento del boton CUATRO -> color RGB aproximado (200,200,50)
        botonCuatro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                linearIzquierdo.setBackgroundColor(Color.rgb(200, 200, 50));
            }
        });

        // evento del boton CINCO -> verde
        botonCinco.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                linearIzquierdo.setBackgroundColor(Color.GREEN);
            }
        });
    }

    /* Independencia de la resolución de la pantalla: calcula tamaño de letra */
    private void gestionarResolucion() {
        DisplayMetrics displayMetrics = this.getApplicationContext().getResources().getDisplayMetrics();
        int alto = displayMetrics.heightPixels;
        int ancho = displayMetrics.widthPixels;
        int dimensionReferencia;

        // tomar el menor valor entre alto y ancho de pantalla
        if (alto > ancho) {
            dimensionReferencia = ancho;
        } else {
            dimensionReferencia = alto;
        }

        // una estimación de un buen tamaño
        int tamanoLetra = dimensionReferencia / 20;

        // tamano de letra para usar acomodado a la resolución de pantalla
        tamanoLetraResolucionIncluida = (int) (tamanoLetra / displayMetrics.scaledDensity);
    }
}