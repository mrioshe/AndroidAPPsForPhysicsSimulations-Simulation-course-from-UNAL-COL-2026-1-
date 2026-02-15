package com.curso_simulaciones.mivigesimanovenaapp;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.curso_simulaciones.mivigesimanovenaapp.actividades_secundarias.ActividadDesplegadoraDatos;
import com.curso_simulaciones.mivigesimanovenaapp.utilidades.Boton;

public class ActividadPrincipalMiVigesimaNovenaApp extends Activity {
    private Boton consultar, salir;

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

        existenciaSensor();
        eventos();
    }

    private void crearElementosGUI() {
        consultar = new Boton(this);
        consultar.setImagen(R.drawable.entrar);

        salir = new Boton(this);
        salir.setImagen(R.drawable.salir);
    }

    private LinearLayout crearGUI() {
        LinearLayout linearLayoutPrincipal = new LinearLayout(this);
        linearLayoutPrincipal.setOrientation(LinearLayout.VERTICAL);
        linearLayoutPrincipal.setGravity(Gravity.CENTER_HORIZONTAL);
        linearLayoutPrincipal.setGravity(Gravity.FILL);
        linearLayoutPrincipal.setBackgroundColor(Color.WHITE);
        linearLayoutPrincipal.setWeightSum(10);

        // LinearLayout primera fila
        LinearLayout linearLayoutPrimeraFila = new LinearLayout(this);
        linearLayoutPrimeraFila.setOrientation(LinearLayout.HORIZONTAL);
        linearLayoutPrimeraFila.setGravity(Gravity.FILL);
        linearLayoutPrimeraFila.setBackgroundColor(Color.WHITE);

        LinearLayout.LayoutParams parametrosPrimeraFila = new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT, 0);
        parametrosPrimeraFila.weight = 8.0f;
        linearLayoutPrimeraFila.setLayoutParams(parametrosPrimeraFila);

        // Fondo primera fila
        try {
            Drawable fondo = getResources().getDrawable(R.drawable.imagen_entrada_app_29);
            linearLayoutPrimeraFila.setBackgroundDrawable(fondo);
        } catch (Exception e) {
            linearLayoutPrimeraFila.setBackgroundColor(Color.rgb(20, 30, 50));
        }

        // LinearLayout segunda fila
        LinearLayout linearLayoutSegundaFila = new LinearLayout(this);
        linearLayoutSegundaFila.setOrientation(LinearLayout.HORIZONTAL);
        linearLayoutSegundaFila.setGravity(Gravity.FILL);

        LinearLayout.LayoutParams parametrosSegundaFila = new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT, 0);
        parametrosSegundaFila.weight = 2.0f;
        linearLayoutSegundaFila.setWeightSum(2.0f);
        linearLayoutSegundaFila.setLayoutParams(parametrosSegundaFila);

        LinearLayout.LayoutParams parametrosPegadoBoton = new LinearLayout.LayoutParams(
                0, ViewGroup.LayoutParams.MATCH_PARENT);
        parametrosPegadoBoton.weight = 1.0f;

        consultar.setLayoutParams(parametrosPegadoBoton);
        salir.setLayoutParams(parametrosPegadoBoton);

        linearLayoutSegundaFila.addView(consultar);
        linearLayoutSegundaFila.addView(salir);

        linearLayoutPrincipal.addView(linearLayoutPrimeraFila);
        linearLayoutPrincipal.addView(linearLayoutSegundaFila);

        return linearLayoutPrincipal;
    }

    private void eventos() {
        consultar.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                lanzarDatos();
            }
        });

        salir.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void lanzarDatos() {
        Intent intent = new Intent(this, ActividadDesplegadoraDatos.class);
        startActivity(intent);
    }

    // Preguntar si el sensor existe
    private boolean existenciaSensor() {
        boolean existe = false;
        SensorManager sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);

        if (sensorManager.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD) != null) {
            existe = true;
        } else {
            desplegarAviso();
        }

        return existe;
    }

    private void desplegarAviso() {
        Toast toast = Toast.makeText(getApplicationContext(),
                "SU DISPOSITIVO NO POSEE GAUSSIMETRO",
                Toast.LENGTH_SHORT);
        toast.show();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        finish();
    }
}