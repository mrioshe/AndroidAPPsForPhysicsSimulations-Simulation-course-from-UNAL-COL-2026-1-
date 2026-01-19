package com.curso_simulaciones.micuartaapp;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Gravity;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import com.curso_simulaciones.micuartaapp.componentes.GaugeSimple;

public class ActividadPrincipalMiCuartaApp extends Activity {

    private GaugeSimple tacometro_1, tacometro_2, tacometro_3;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Llamada al método para crear los elementos de la GUI
        crearElementosGui();

        // Parámetros para adaptar la GUI a la pantalla
        ViewGroup.LayoutParams parametro_layout_principal = new ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT
        );

        // Pegar al contenedor la GUI
        this.setContentView(crearGui(), parametro_layout_principal);
    }

    // Crear los objetos de la GUI
    private void crearElementosGui() {
        // Crear objeto GaugeSimple
        tacometro_1 = new GaugeSimple(this);
        // Cambiar atributos (propiedades)
        // Darle color blanco al lienzo antes de pegar
        tacometro_1.setBackgroundColor(Color.WHITE);
        // Asignar las unidades
        tacometro_1.setUnidades("HR %");
        // Asignar rangos
        tacometro_1.setRango(0, 100);
        // Asignar la medida
        tacometro_1.setMedida(78f);

        // Crear objeto GaugeSimple
        tacometro_2 = new GaugeSimple(this);
        // Cambiar atributos (propiedades)
        // Darle color blanco al lienzo antes de pegar
        tacometro_2.setBackgroundColor(Color.WHITE);
        // Darle color a los sectores
        tacometro_2.setColorSectores(Color.RED, Color.BLUE, Color.rgb(200, 200, 20));
        // Asignar las unidades
        tacometro_2.setUnidades("lx");
        // Asignar rangos
        tacometro_2.setRango(0, 1000);
        // Asignar la medida
        tacometro_2.setMedida(120f);

        // Crear objeto GaugeSimple
        tacometro_3 = new GaugeSimple(this);
        // Cambiar atributos (propiedades)
        // Darle color blanco al lienzo antes de pegar
        tacometro_3.setBackgroundColor(Color.WHITE);
        // Darle color a los sectores
        tacometro_3.setColorSectores(Color.GREEN, Color.GREEN, Color.rgb(255, 100, 0));
        // Asignar las unidades
        tacometro_3.setUnidades("ax en m/s.s");
        // Asignar rangos
        tacometro_3.setRango(0, 10);
        // Asignar la medida
        tacometro_3.setMedida(6.5f);
    }

    // Organizar la distribución de los objetos de la GUI
    private LinearLayout crearGui() {
        // Administrador de diseño principal (horizontal)
        LinearLayout linear_principal = new LinearLayout(this);
        linear_principal.setOrientation(LinearLayout.HORIZONTAL);
        linear_principal.setGravity(Gravity.CENTER_HORIZONTAL);
        linear_principal.setBackgroundColor(Color.rgb(250, 150, 50));
        linear_principal.setWeightSum(3);

        // Layout izquierdo
        LinearLayout linear_izquierdo = new LinearLayout(this);
        linear_izquierdo.setOrientation(LinearLayout.VERTICAL);
        linear_izquierdo.setGravity(Gravity.CENTER_HORIZONTAL);
        linear_izquierdo.setBackgroundColor(Color.RED);
        linear_izquierdo.setWeightSum(1);

        // Layout central
        LinearLayout linear_centro = new LinearLayout(this);
        linear_centro.setOrientation(LinearLayout.VERTICAL);
        linear_centro.setGravity(Gravity.CENTER_HORIZONTAL);
        linear_centro.setBackgroundColor(Color.BLUE);
        linear_centro.setWeightSum(1);

        // Layout derecho
        LinearLayout linear_derecho = new LinearLayout(this);
        linear_derecho.setOrientation(LinearLayout.VERTICAL);
        linear_derecho.setGravity(Gravity.CENTER_HORIZONTAL);
        linear_derecho.setBackgroundColor(Color.GREEN);
        linear_derecho.setWeightSum(1);

        // Parámetro para pegar los gauges en los layouts secundarios
        LinearLayout.LayoutParams parametrosPegadaGauges = new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                0
        );
        parametrosPegadaGauges.setMargins(20, 20, 20, 20);
        parametrosPegadaGauges.weight = 1.0f;

        // Pegar gauges a los layouts secundarios
        linear_izquierdo.addView(tacometro_1, parametrosPegadaGauges);
        linear_centro.addView(tacometro_2, parametrosPegadaGauges);
        linear_derecho.addView(tacometro_3, parametrosPegadaGauges);

        // Parámetro para pegar los layouts secundarios al principal
        LinearLayout.LayoutParams parametrosPegadaLinear = new LinearLayout.LayoutParams(
                0,
                ViewGroup.LayoutParams.MATCH_PARENT
        );
        parametrosPegadaLinear.setMargins(20, 20, 20, 20);
        parametrosPegadaLinear.weight = 1.0f;

        // Pegar los layouts secundarios al principal
        linear_principal.addView(linear_izquierdo, parametrosPegadaLinear);
        linear_principal.addView(linear_centro, parametrosPegadaLinear);
        linear_principal.addView(linear_derecho, parametrosPegadaLinear);

        return linear_principal;
    }
}