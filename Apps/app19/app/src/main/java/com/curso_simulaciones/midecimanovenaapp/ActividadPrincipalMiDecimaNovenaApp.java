package com.curso_simulaciones.midecimanovenaapp;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;

import com.curso_simulaciones.midecimanovenaapp.actividades_secundarias.ActividadSecundaria_1;
import com.curso_simulaciones.midecimanovenaapp.actividades_secundarias.ActividadSecundaria_2;
import com.curso_simulaciones.midecimanovenaapp.actividades_secundarias.ActividadSecundaria_3;
import com.curso_simulaciones.midecimanovenaapp.datos.AlmacenDatosRAM;

public class ActividadPrincipalMiDecimaNovenaApp extends Activity {

    private Button botonUno, botonDos, botonTres, botonCuatro;
    private int tamanoLetraResolucionIncluida;

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

        //independencia de la resolución de la pantalla
        DisplayMetrics displayMetrics = this.getApplicationContext().getResources().getDisplayMetrics();
        int alto = displayMetrics.heightPixels;
        int ancho = displayMetrics.widthPixels;
        int dimensionReferencia;

        //tomar el menor valor entre alto y ancho de pantalla
        if (alto > ancho) {
            dimensionReferencia = ancho;
        } else {
            dimensionReferencia = alto;
        }

        //una estimación de un buen tamaño
        int tamanoLetra = dimensionReferencia / 20;

        //tamano de letra para usar acomodado a la resolución de pantalla
        tamanoLetraResolucionIncluida = (int) (tamanoLetra / displayMetrics.scaledDensity);

        //guardar en el almacen de datos para que otras clases la accedan fácilmente
        AlmacenDatosRAM.tamanoLetraResolucionIncluida = tamanoLetraResolucionIncluida;

    }//fin método gestionarResolucion()

    /*método responsable de la creación de los elementos de la GUI*/
    private void crearElementosGUI() {

        /*
         1. El tamaño a usar de la letra tiene corrección de
            resolución y tamaño de pantalla.

         2. Se usa un diseño de color de botón especial
            PorterDuff.Mode.MULTIPLY.
        */

        botonUno = new Button(this);
        botonUno.setTextSize(TypedValue.COMPLEX_UNIT_SP, tamanoLetraResolucionIncluida);
        botonUno.setText("UNO");
        botonUno.getBackground().setColorFilter(Color.rgb(220, 156, 80), PorterDuff.Mode.MULTIPLY);

        botonDos = new Button(this);
        botonDos.setTextSize(TypedValue.COMPLEX_UNIT_SP, tamanoLetraResolucionIncluida);
        botonDos.setText("DOS");
        botonDos.getBackground().setColorFilter(Color.rgb(220, 156, 80), PorterDuff.Mode.MULTIPLY);

        botonTres = new Button(this);
        botonTres.setTextSize(TypedValue.COMPLEX_UNIT_SP, tamanoLetraResolucionIncluida);
        botonTres.setText("TRES");
        botonTres.getBackground().setColorFilter(Color.rgb(220, 156, 80), PorterDuff.Mode.MULTIPLY);
        botonTres.setEnabled(false);

        botonCuatro = new Button(this);
        botonCuatro.setTextSize(TypedValue.COMPLEX_UNIT_SP, tamanoLetraResolucionIncluida);
        botonCuatro.setText("CUATRO");
        botonCuatro.getBackground().setColorFilter(Color.rgb(220, 156, 80), PorterDuff.Mode.MULTIPLY);
        botonCuatro.setEnabled(false);

    }//fin método crearElementosGUI

    /*método responsable de administrar el diseño de la GUI*/
    private LinearLayout crearGUI() {

        LinearLayout linearPrincipal = new LinearLayout(this);

        //los componentes se agregarán verticalmente
        linearPrincipal.setOrientation(LinearLayout.VERTICAL);
        //para definir los pesos de las filas que se agregaran
        linearPrincipal.setWeightSum(4.0f);

        //las cuatro filas
        LinearLayout linearUno = new LinearLayout(this);
        LinearLayout linearDos = new LinearLayout(this);
        LinearLayout linearTres = new LinearLayout(this);
        LinearLayout linearCuatro = new LinearLayout(this);

        //pegado de filas a linear principal
        /*
          Pegado vertical (el ancho se acomoda en todo el contenedor
          cada fila se pega con un peso de 1.0. Son cuatro filas,
          la suma da 4.0.
          Como el pegado es vertical el 0 debe ir como segundo argumento
        */
        LinearLayout.LayoutParams parametros_pegado_filas = new LinearLayout.LayoutParams(
                android.view.ViewGroup.LayoutParams.MATCH_PARENT, 0);
        parametros_pegado_filas.weight = 1.0f;
        linearPrincipal.addView(linearUno, parametros_pegado_filas);
        linearPrincipal.addView(linearDos, parametros_pegado_filas);
        linearPrincipal.addView(linearTres, parametros_pegado_filas);
        linearPrincipal.addView(linearCuatro, parametros_pegado_filas);

        //pegado de botones a cada fila
        /*
          Cada fila tiene un solo botón que ocupa todo el espacio
        */
        LinearLayout.LayoutParams parametros_pegado_botones = new LinearLayout.LayoutParams(
                android.view.ViewGroup.LayoutParams.MATCH_PARENT,
                android.view.ViewGroup.LayoutParams.MATCH_PARENT);
        linearUno.addView(botonUno, parametros_pegado_botones);
        linearDos.addView(botonDos, parametros_pegado_botones);
        linearTres.addView(botonTres, parametros_pegado_botones);
        linearCuatro.addView(botonCuatro, parametros_pegado_botones);

        return linearPrincipal;

    }//fin método crearGUI

    /*Administra los eventos de la GUI*/
    private void eventos() {

        //evento del boton con etiqueta UNO
        botonUno.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {

                lanzarActividadSecundaria_1();
            }
        });

        //evento del boton con etiqueta DOS
        botonDos.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {

                lanzarActividadSecundaria_2();
            }
        });

        //evento del boton con etiqueta TRES
        botonTres.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {

                lanzarActividadSecundaria_3();
            }
        });

    }//fin método eventos

    //métodos que lanzar las actividades secundarias
    private void lanzarActividadSecundaria_1() {

        Intent intent = new Intent(this, ActividadSecundaria_1.class);
        startActivity(intent);

    }

    private void lanzarActividadSecundaria_2() {

        Intent intent = new Intent(this, ActividadSecundaria_2.class);
        startActivity(intent);

    }

    private void lanzarActividadSecundaria_3() {

        Intent intent = new Intent(this, ActividadSecundaria_3.class);
        startActivity(intent);
    }

    /* Métodos automáticos*/

    protected void onResume() {
        super.onResume();

        if (AlmacenDatosRAM.habilitar_boton_tres == true) {
            botonTres.setEnabled(true);
        } else {
            botonTres.setEnabled(false);
        }
    }

    protected void onDestroy() {

        //Volver los valores de los datos a su estado por defecto
        AlmacenDatosRAM.nombreImagen1 = "xxxx";
        AlmacenDatosRAM.nombreImagen2 = "xxxx";
        AlmacenDatosRAM.habilitar_boton_tres = false;

        this.finish();

        super.onDestroy();

    }//fin método onDestroy

}//fin Actividad
