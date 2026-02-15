package com.curso_simulaciones.mivigesimanovenaapp.actividades_secundarias;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.curso_simulaciones.mivigesimanovenaapp.R;
import com.curso_simulaciones.mivigesimanovenaapp.datos.AlmacenDatosRAM;
import com.curso_simulaciones.mivigesimanovenaapp.utilidades.Boton;
import com.curso_simulaciones.mivigesimanovenaapp.utilidades.Gaussimetro;
import com.curso_simulaciones.mivigesimanovenaapp.utilidades.Graficador;

public class ActividadDesplegadoraDatos extends Activity {
    private Boton bx, by, bz, b;
    private Gaussimetro gaussimetro;
    public Graficador graficador;

    // Hilo responsable de la animación
    private HiloAnimacion hilo;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Crear elementos de la GUI
        crearElementosGUI();

        // Para informar cómo se debe pegar el administrador de diseño
        ViewGroup.LayoutParams parametroLayoutPrincipal = new ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT);

        // Pegar el contenedor con la GUI
        this.setContentView(crearGUI(), parametroLayoutPrincipal);

        eventos();

        hilo = new HiloAnimacion(this);
        hilo.start();
    }

    private void crearElementosGUI() {
        // Botones
        bx = new Boton(this);
        bx.setImagen(R.drawable.bx);

        by = new Boton(this);
        by.setImagen(R.drawable.b);  // Usar b.png para By

        bz = new Boton(this);
        bz.setImagen(R.drawable.bz);

        b = new Boton(this);
        b.setImagen(R.drawable.b);   // Magnitud total en naranja

        // Gauge - Gaussímetro con diseño aesthetic
        gaussimetro = new Gaussimetro(this);

        // Graficador
        graficador = new Graficador(this);
        graficador.setTituloEjeX("Tiempo (s)");
        graficador.setTituloEjeY("Campo Magnético Bx (µT)");
        graficador.setGrosorLinea(2.5f);
        graficador.setColorLinea(Color.rgb(0, 200, 100));
        graficador.setColorValores(Color.rgb(255, 215, 0));
        graficador.setColorMarcadores(Color.rgb(100, 200, 255));
        graficador.setColorFondo(Color.rgb(20, 20, 30));
        graficador.setColorTextoEjes(Color.WHITE);
    }

    private LinearLayout crearGUI() {
        LinearLayout linearLayoutPrincipal = new LinearLayout(this);
        linearLayoutPrincipal.setOrientation(LinearLayout.HORIZONTAL);
        linearLayoutPrincipal.setGravity(Gravity.CENTER_HORIZONTAL | Gravity.FILL);
        linearLayoutPrincipal.setBackgroundColor(Color.rgb(15, 15, 25));
        linearLayoutPrincipal.setPadding(15, 15, 15, 15);
        linearLayoutPrincipal.setWeightSum(10);

        // Primera columna (50%) - Gauge
        LinearLayout linearLayoutPrimeraColumna = new LinearLayout(this);
        linearLayoutPrimeraColumna.setOrientation(LinearLayout.HORIZONTAL);
        linearLayoutPrimeraColumna.setGravity(Gravity.FILL | Gravity.CENTER);
        linearLayoutPrimeraColumna.setBackgroundColor(Color.rgb(25, 25, 35));

        LinearLayout.LayoutParams parametrosPrimeraColumna = new LinearLayout.LayoutParams(
                0, ViewGroup.LayoutParams.MATCH_PARENT);
        parametrosPrimeraColumna.weight = 5.0f;
        parametrosPrimeraColumna.setMargins(10, 10, 5, 10);
        linearLayoutPrimeraColumna.setLayoutParams(parametrosPrimeraColumna);

        // Segunda columna (40%) - Gráfica
        LinearLayout linearLayoutSegundaColumna = new LinearLayout(this);
        linearLayoutSegundaColumna.setOrientation(LinearLayout.HORIZONTAL);
        linearLayoutSegundaColumna.setGravity(Gravity.FILL);
        linearLayoutSegundaColumna.setBackgroundColor(Color.rgb(25, 25, 35));
        linearLayoutSegundaColumna.setWeightSum(1.0f);

        LinearLayout.LayoutParams parametrosSegundaColumna = new LinearLayout.LayoutParams(
                0, ViewGroup.LayoutParams.MATCH_PARENT);
        parametrosSegundaColumna.weight = 4.0f;
        parametrosSegundaColumna.setMargins(5, 10, 5, 10);
        linearLayoutSegundaColumna.setLayoutParams(parametrosSegundaColumna);

        // Tercera columna (10%) - Botones verticales
        LinearLayout linearLayoutTerceraColumna = new LinearLayout(this);
        linearLayoutTerceraColumna.setOrientation(LinearLayout.VERTICAL);
        linearLayoutTerceraColumna.setGravity(Gravity.FILL | Gravity.CENTER_VERTICAL);
        linearLayoutTerceraColumna.setBackgroundColor(Color.rgb(30, 30, 40));
        linearLayoutTerceraColumna.setWeightSum(4.0f);

        LinearLayout.LayoutParams parametrosTerceraColumna = new LinearLayout.LayoutParams(
                0, ViewGroup.LayoutParams.MATCH_PARENT);
        parametrosTerceraColumna.weight = 1.0f;
        parametrosTerceraColumna.setMargins(5, 10, 10, 10);
        linearLayoutTerceraColumna.setLayoutParams(parametrosTerceraColumna);

        // Pegar las tres columnas al principal
        linearLayoutPrincipal.addView(linearLayoutPrimeraColumna);
        linearLayoutPrincipal.addView(linearLayoutSegundaColumna);
        linearLayoutPrincipal.addView(linearLayoutTerceraColumna);

        // Pegar gauge en primera columna
        linearLayoutPrimeraColumna.addView(gaussimetro);

        // Pegar gráfico en segunda columna
        LinearLayout.LayoutParams parametrosGrafica = new LinearLayout.LayoutParams(
                0, ViewGroup.LayoutParams.MATCH_PARENT);
        parametrosGrafica.weight = 1.0f;
        linearLayoutSegundaColumna.addView(graficador, parametrosGrafica);

        // Pegar botones en tercera columna
        LinearLayout.LayoutParams parametrosPegadoBoton = new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT, 0);
        parametrosPegadoBoton.weight = 1.0f;
        parametrosPegadoBoton.setMargins(5, 5, 5, 5);

        linearLayoutTerceraColumna.addView(bx, parametrosPegadoBoton);
        linearLayoutTerceraColumna.addView(by, parametrosPegadoBoton);
        linearLayoutTerceraColumna.addView(bz, parametrosPegadoBoton);
        linearLayoutTerceraColumna.addView(b, parametrosPegadoBoton);

        return linearLayoutPrincipal;
    }

    private void eventos() {
        bx.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                lanzarDatosBx();
            }
        });

        by.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                lanzarDatosBy();
            }
        });

        bz.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                lanzarDatosBz();
            }
        });

        b.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                lanzarDatosB();
            }
        });
    }

    private void lanzarDatosBx() {
        resetear();
        gaussimetro.setComponenteGaussimetro(1);
        gaussimetro.setRango(-4000, 4000);
        graficador.setTituloEjeY("Campo Magnético Bx (µT)");
        graficador.setColorLinea(Color.rgb(0, 200, 100));
        hilo.corriendo = true;
    }

    private void lanzarDatosBy() {
        resetear();
        gaussimetro.setComponenteGaussimetro(2);
        gaussimetro.setRango(-4000, 4000);
        graficador.setTituloEjeY("Campo Magnético By (µT)");
        graficador.setColorLinea(Color.rgb(100, 255, 100));
        hilo.corriendo = true;
    }

    private void lanzarDatosBz() {
        resetear();
        gaussimetro.setComponenteGaussimetro(3);
        gaussimetro.setRango(-4000, 4000);
        graficador.setTituloEjeY("Campo Magnético Bz (µT)");
        graficador.setColorLinea(Color.rgb(50, 200, 150));
        hilo.corriendo = true;
    }

    private void lanzarDatosB() {
        resetear();
        gaussimetro.setComponenteGaussimetro(4);
        gaussimetro.setRango(0, 4000);
        graficador.setTituloEjeY("Campo Magnético B (µT)");
        graficador.setColorLinea(Color.rgb(255, 150, 50));
        hilo.corriendo = true;
    }

    protected void onPause() {
        hilo.corriendo = false;
        AlmacenDatosRAM.datos.clear();
        hilo.contador = 0;
        super.onPause();
    }

    @Override
    public void onRestart() {
        super.onRestart();
        hilo.corriendo = true;
    }

    private void resetear() {
        hilo.corriendo = false;
        AlmacenDatosRAM.datos.clear();
        hilo.tiempo = 0;
        hilo.contador = 0;
    }
}