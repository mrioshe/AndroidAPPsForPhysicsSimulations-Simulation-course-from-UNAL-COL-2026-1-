package com.curso_simulaciones.miquintaapp;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Gravity;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import com.curso_simulaciones.miquintaapp.componentes.GaugeCircular;

public class ActividadPrincipalMiQuintaApp extends Activity {
    private GaugeCircular gauge1, gauge2, gauge3;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Crear elementos de la GUI
        crearElementosGui();

        // Configurar parámetros de layout principal
        ViewGroup.LayoutParams parametroLayoutPrincipal = new ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT
        );

        // Establecer la GUI
        this.setContentView(crearGui(), parametroLayoutPrincipal);
    }

    private void crearElementosGui() {
        // GAUGE 1: Campo magnético en Gauss
        gauge1 = new GaugeCircular(this);
        gauge1.setBackgroundColor(Color.rgb(40, 40, 40));
        gauge1.setRango(-20, 40);
        gauge1.setMedida(32f);
        gauge1.setUnidades("Gauss");
        gauge1.setColorSectores(
                Color.BLUE,
                Color.YELLOW,
                Color.GREEN
        );
        gauge1.setColorFondo(Color.BLACK);
        gauge1.setColorBorde(Color.RED);
        gauge1.setColorAguja(Color.RED);
        gauge1.setColorNumeros(Color.WHITE);
        gauge1.setColorEscala(Color.YELLOW);

        // GAUGE 2: Presión en bar
        gauge2 = new GaugeCircular(this);
        gauge2.setBackgroundColor(Color.rgb(40, 40, 40));
        gauge2.setRango(0, 100);
        gauge2.setMedida(65f);
        gauge2.setUnidades("bar");
        gauge2.setColorSectores(
                Color.rgb(0, 255, 0),
                Color.rgb(255, 200, 0),
                Color.RED
        );
        gauge2.setColorFondo(Color.rgb(20, 20, 40));
        gauge2.setColorBorde(Color.CYAN);
        gauge2.setColorAguja(Color.CYAN);
        gauge2.setColorNumeros(Color.rgb(200, 255, 200));
        gauge2.setColorEscala(Color.CYAN);

        // GAUGE 3: Velocidad en km/h
        gauge3 = new GaugeCircular(this);
        gauge3.setBackgroundColor(Color.rgb(40, 40, 40));
        gauge3.setRango(0, 200);
        gauge3.setMedida(120f);
        gauge3.setUnidades("km/h");
        gauge3.setColorSectores(
                Color.rgb(50, 255, 50),
                Color.rgb(255, 200, 0),
                Color.rgb(255, 50, 50)
        );
        gauge3.setColorFondo(Color.rgb(10, 10, 30));
        gauge3.setColorBorde(Color.rgb(255, 100, 0));
        gauge3.setColorAguja(Color.rgb(255, 100, 0));
        gauge3.setColorNumeros(Color.rgb(255, 255, 150));
        gauge3.setColorEscala(Color.rgb(150, 150, 255));
    }

    private LinearLayout crearGui() {
        // Layout principal horizontal
        LinearLayout linearPrincipal = new LinearLayout(this);
        linearPrincipal.setOrientation(LinearLayout.HORIZONTAL);
        linearPrincipal.setGravity(Gravity.CENTER);
        linearPrincipal.setBackgroundColor(Color.rgb(30, 30, 30));
        linearPrincipal.setWeightSum(3);

        // Layout izquierdo
        LinearLayout linearIzquierdo = new LinearLayout(this);
        linearIzquierdo.setOrientation(LinearLayout.VERTICAL);
        linearIzquierdo.setGravity(Gravity.CENTER);
        linearIzquierdo.setBackgroundColor(Color.rgb(50, 50, 50));

        // Layout centro
        LinearLayout linearCentro = new LinearLayout(this);
        linearCentro.setOrientation(LinearLayout.VERTICAL);
        linearCentro.setGravity(Gravity.CENTER);
        linearCentro.setBackgroundColor(Color.rgb(45, 45, 45));

        // Layout derecho
        LinearLayout linearDerecho = new LinearLayout(this);
        linearDerecho.setOrientation(LinearLayout.VERTICAL);
        linearDerecho.setGravity(Gravity.CENTER);
        linearDerecho.setBackgroundColor(Color.rgb(50, 50, 50));

        // Parámetros para pegar los gauges
        LinearLayout.LayoutParams parametrosGauge = new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                0
        );
        parametrosGauge.setMargins(20, 20, 20, 20);
        parametrosGauge.weight = 1.0f;

        // Pegar gauges a sus layouts
        linearIzquierdo.addView(gauge1, parametrosGauge);
        linearCentro.addView(gauge2, parametrosGauge);
        linearDerecho.addView(gauge3, parametrosGauge);

        // Parámetros para pegar layouts al principal
        LinearLayout.LayoutParams parametrosLinear = new LinearLayout.LayoutParams(
                0,
                ViewGroup.LayoutParams.MATCH_PARENT
        );
        parametrosLinear.setMargins(10, 10, 10, 10);
        parametrosLinear.weight = 1.0f;

        // Pegar layouts al principal
        linearPrincipal.addView(linearIzquierdo, parametrosLinear);
        linearPrincipal.addView(linearCentro, parametrosLinear);
        linearPrincipal.addView(linearDerecho, parametrosLinear);

        return linearPrincipal;
    }
}