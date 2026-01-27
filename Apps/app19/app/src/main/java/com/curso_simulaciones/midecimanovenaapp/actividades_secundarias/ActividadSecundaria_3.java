package com.curso_simulaciones.midecimanovenaapp.actividades_secundarias;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.curso_simulaciones.midecimanovenaapp.datos.AlmacenDatosRAM;

public class ActividadSecundaria_3 extends Activity {

    private int tamanoLetraResolucionIncluida;
    private int margenesResolucionIncluida;
    private TextView aviso;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        gestionarResolucion();

        //para crear elementos de la GUI
        crearElementosGUI();

        //para informar cómo se debe pegar el administrador de
        //diseño obtenido con el método GUI
        ViewGroup.LayoutParams parametro_layout_principal = new ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT);

        //pegar el contenedor con la GUI
        this.setContentView(crearGUI(), parametro_layout_principal);

        //para administrar los eventos
        eventos();

    } //fin del método onCreate

    private void gestionarResolucion() {

        tamanoLetraResolucionIncluida = (int) (0.8f * AlmacenDatosRAM.tamanoLetraResolucionIncluida);
        margenesResolucionIncluida = (int) (1.2f * AlmacenDatosRAM.tamanoLetraResolucionIncluida);

    }//fin método gestionarResolucion()

    /*método responsable de la creación de los elementos de la GUI*/
    private void crearElementosGUI() {

        aviso = new TextView(this);
        aviso.setTextSize(TypedValue.COMPLEX_UNIT_SP, tamanoLetraResolucionIncluida);
        aviso.setGravity(Gravity.START);
        aviso.setTextColor(Color.BLACK);

        String nombreImagen1 = AlmacenDatosRAM.nombreImagen1;
        String nombreImagen2 = AlmacenDatosRAM.nombreImagen2;

        String componer_cadena = "La imagen de la AplicacionSecundaria_1 se denomina " +
                nombreImagen1 + " y la de la AplicacionSecundaria_2 se denomina " + nombreImagen2;

        aviso.setText(componer_cadena);

    }//fin método crearElementosGUI

    /*método responsable de administrar el diseño de la GUI*/
    private LinearLayout crearGUI() {

        //LinearLayoutPrincipal
        LinearLayout linearPrincipal = new LinearLayout(this);
        linearPrincipal.setOrientation(LinearLayout.VERTICAL);
        linearPrincipal.setBackgroundColor(Color.WHITE);
        linearPrincipal.setWeightSum(1.0f);
        linearPrincipal.setPadding(margenesResolucionIncluida, margenesResolucionIncluida,
                margenesResolucionIncluida, margenesResolucionIncluida);

        //parámetro de pegado
        LinearLayout.LayoutParams parametros_aviso = new LinearLayout.LayoutParams(
                android.view.ViewGroup.LayoutParams.MATCH_PARENT, 0);
        parametros_aviso.weight = 1.0f;
        parametros_aviso.setMargins(margenesResolucionIncluida, 0, 0, 0);
        linearPrincipal.addView(aviso, parametros_aviso);

        return linearPrincipal;

    }//fin método crearGUI

    /*Administra los eventos de la GUI*/
    private void eventos() {

    }//fin método eventos

}//fin Actividad
