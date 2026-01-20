package com.curso_simulaciones.miseptimaapp_1;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Gravity;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.curso_simulaciones.miseptimaapp_1.objetos_laboratorio.Polea;
import com.curso_simulaciones.miseptimaapp_1.vista.Pizarra;

public class ActividadPrincipalMiSeptimaApp_1 extends Activity {

    // pizarra para dibujar
    private Pizarra pizarra;

    // objetos dibujables para Pizarra
    private Polea polea_1, polea_2, polea_3;

    // arreglo que contiene las tres poleas
    private Polea poleas[] = new Polea[3];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        /* llamada al método para crear los elementos de la interfaz gráfica de usuario (GUI) */
        crearElementosGui();

        /* para informar cómo se debe adaptar la GUI a la pantalla del dispositivo */
        ViewGroup.LayoutParams parametro_layout_principal =
                new ViewGroup.LayoutParams(
                        ViewGroup.LayoutParams.MATCH_PARENT,
                        ViewGroup.LayoutParams.MATCH_PARENT
                );

        /* pegar al contenedor la GUI */
        this.setContentView(crearGui(), parametro_layout_principal);

        // crear objetos de laboratorio
        crearObjetosLaboratorio();
    }

    // crear los objetos de la interfaz gráfica de usuario (GUI)
    private void crearElementosGui() {
        pizarra = new Pizarra(this);
        pizarra.setBackgroundColor(Color.BLACK);
    }

    // organizar la distribución de los objetos de la GUI usando administradores de diseño
    private LinearLayout crearGui() {

        // administrador de diseño principal
        LinearLayout linear_principal = new LinearLayout(this);
        linear_principal.setOrientation(LinearLayout.VERTICAL);
        linear_principal.setGravity(Gravity.FILL);
        linear_principal.setBackgroundColor(Color.rgb(250, 150, 50));

        /* parámetro de pegada */
        LinearLayout.LayoutParams parametrosPegada =
                new LinearLayout.LayoutParams(
                        ViewGroup.LayoutParams.MATCH_PARENT,
                        0
                );
        parametrosPegada.setMargins(50, 50, 50, 50);
        parametrosPegada.weight = 1.0f;

        // pegar la pizarra
        linear_principal.addView(pizarra, parametrosPegada);

        return linear_principal;
    }

    /* Crea los objetos polea con su estado inicial */
    private void crearObjetosLaboratorio() {

        /*
         El constructor por defecto crea una polea
         ubicada en (0,0), de radio 100 y color rojo
         */
        polea_1 = new Polea();
        poleas[0] = polea_1;

        // polea ubicada en (600,200) de radio 150 y color rojo
        polea_2 = new Polea(600f, 200f, 150f);
        poleas[1] = polea_2;

        // polea ubicada en (600,600) de radio 150 y color verde
        polea_3 = new Polea(600f, 600f, 150f);
        polea_3.setColorPolea(Color.GREEN);
        poleas[2] = polea_3;

        // estado inicial de la escena: envía el arreglo de poleas a la pizarra
        pizarra.setEstadoEscena(poleas);
    }
}
