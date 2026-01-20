package com.curso_simulaciones.misextaapp;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Gravity;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.curso_simulaciones.misextaapp.componentes.Reloj;

public class ActividadPrincipalMiSextaApp extends Activity {

    private Reloj reloj_1, reloj_2, reloj_3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        /* llamada al método para crear los elementos de la interfaz
           gráfica de usuario (GUI) */
        crearElementosGui();

        /* para informar cómo se debe adaptar la GUI a la pantalla del
           dispositivo */
        ViewGroup.LayoutParams parametro_layout_principal =
                new ViewGroup.LayoutParams(
                        ViewGroup.LayoutParams.MATCH_PARENT,
                        ViewGroup.LayoutParams.MATCH_PARENT
                );

        /* pegar al contenedor la GUI */
        this.setContentView(crearGui(), parametro_layout_principal);
    }

    /* crear los objetos de la interfaz gráfica de usuario (GUI) */
    private void crearElementosGui() {

        // crear objeto Reloj
        reloj_1 = new Reloj(this);

        // crear objeto Reloj
        reloj_2 = new Reloj(this);
        reloj_2.setColorAgujaHorario(Color.YELLOW);
        reloj_2.setColorAgujaMinutero(Color.YELLOW);
        reloj_2.setColorAgujaSegundero(Color.YELLOW);
        reloj_2.setColorFondo(Color.rgb(250, 150, 0));

        // crear objeto Reloj
        reloj_3 = new Reloj(this);
        reloj_3.setColorAgujaHorario(Color.GREEN);
        reloj_3.setColorFondo(Color.argb(50, 200, 200, 0));
    }

    /* organizar la distribución de los objetos de la GUI usando
       administradores de diseño */
    private LinearLayout crearGui() {

        // administrador de diseño principal
        LinearLayout linear_principal = new LinearLayout(this);
        linear_principal.setOrientation(LinearLayout.HORIZONTAL);
        linear_principal.setGravity(Gravity.FILL);
        linear_principal.setBackgroundColor(Color.rgb(250, 150, 50));
        linear_principal.setWeightSum(3);

        LinearLayout linear_izquierdo = new LinearLayout(this);
        linear_izquierdo.setOrientation(LinearLayout.VERTICAL);
        linear_izquierdo.setGravity(Gravity.FILL);
        linear_izquierdo.setBackgroundColor(Color.WHITE);
        linear_izquierdo.setWeightSum(1);

        LinearLayout linear_centro = new LinearLayout(this);
        linear_centro.setOrientation(LinearLayout.VERTICAL);
        linear_centro.setGravity(Gravity.FILL);
        linear_centro.setBackgroundColor(Color.WHITE);
        linear_centro.setWeightSum(1);

        LinearLayout linear_derecho = new LinearLayout(this);
        linear_derecho.setOrientation(LinearLayout.VERTICAL);
        linear_derecho.setGravity(Gravity.FILL);
        linear_derecho.setBackgroundColor(Color.WHITE);
        linear_derecho.setWeightSum(1);

        // parámetros para pegar los relojes
        LinearLayout.LayoutParams parametrosPegadaRelojes =
                new LinearLayout.LayoutParams(
                        ViewGroup.LayoutParams.MATCH_PARENT,
                        0
                );
        parametrosPegadaRelojes.setMargins(20, 20, 20, 20);
        parametrosPegadaRelojes.weight = 1.0f;

        // pegar relojes
        linear_izquierdo.addView(reloj_1, parametrosPegadaRelojes);
        linear_centro.addView(reloj_2, parametrosPegadaRelojes);
        linear_derecho.addView(reloj_3, parametrosPegadaRelojes);

        // parámetros para pegar los linear layouts al principal
        LinearLayout.LayoutParams parametrosPegadaLinear =
                new LinearLayout.LayoutParams(
                        0,
                        ViewGroup.LayoutParams.MATCH_PARENT
                );
        parametrosPegadaLinear.setMargins(20, 20, 20, 20);
        parametrosPegadaLinear.weight = 1.0f;

        linear_principal.addView(linear_izquierdo, parametrosPegadaLinear);
        linear_principal.addView(linear_centro, parametrosPegadaLinear);
        linear_principal.addView(linear_derecho, parametrosPegadaLinear);

        return linear_principal;
    }
}
