package com.curso_simulaciones.mivigesimaprimeraapp;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.DisplayMetrics;

import com.curso_simulaciones.mivigesimaprimeraapp.datos.AlmacenDatosRAM;
import com.curso_simulaciones.mivigesimaprimeraapp.controlador.ActividadControladora;

public class ActividadPrincipalMiVigesimaPrimeraApp extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        gestionarResolucion();
        lanzarActividadControladora();
    }

    /*Método auxiliar para asuntos de resolución*/
    private void gestionarResolucion() {
        DisplayMetrics displayMetrics = this.getApplicationContext().getResources().getDisplayMetrics();
        int alto = displayMetrics.heightPixels;
        int ancho = displayMetrics.widthPixels;
        AlmacenDatosRAM.ancho_pantalla = ancho;
        AlmacenDatosRAM.alto_pantalla = alto;
    }

    private void lanzarActividadControladora() {
        Intent intent = new Intent(this, ActividadControladora.class);
        startActivity(intent);
    }
}