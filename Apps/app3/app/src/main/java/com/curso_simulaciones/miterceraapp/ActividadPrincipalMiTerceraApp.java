package com.curso_simulaciones.miterceraapp;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Gravity;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.curso_simulaciones.miterceraapp.componentes.Pizarra;

public class ActividadPrincipalMiTerceraApp extends Activity {
    private Pizarra lienzo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Llamada al método para crear los elementos de la interfaz gráfica de usuario (GUI)
        crearElementosGui();

        // Para informar cómo se debe adaptar la GUI a la pantalla del dispositivo
        ViewGroup.LayoutParams parametro_layout_principal = new ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT);

        // Pegar al contenedor la GUI: en el argumento se está llamando al método crearGui()
        this.setContentView(crearGui(), parametro_layout_principal);
    }

    // Crear los objetos de la interfaz gráfica de usuario (GUI)
    private void crearElementosGui() {
        // Crear objeto Pizarra
        lienzo = new Pizarra(this);
        // Darle color blanco al lienzo antes de pegar
        lienzo.setBackgroundColor(Color.WHITE);
    }

    // Organizar la distribución de los objetos de la GUI usando administradores de diseño
    private LinearLayout crearGui() {
        // Administrador de diseño LinearLayout
        LinearLayout linear_principal = new LinearLayout(this);
        linear_principal.setOrientation(LinearLayout.VERTICAL);
        linear_principal.setGravity(Gravity.CENTER_HORIZONTAL | Gravity.FILL);
        linear_principal.setBackgroundColor(Color.rgb(250, 150, 50));

        // Parámetro para pegar el lienzo
        LinearLayout.LayoutParams parametroPegadaLienzo = new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT, 0);
        parametroPegadaLienzo.weight = 1.0f;

        // Pegar el lienzo al layout principal
        linear_principal.addView(lienzo, parametroPegadaLienzo);

        return linear_principal;
    }
}