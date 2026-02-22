package com.curso_simulaciones.mitrigesimacuartaapp;

import android.Manifest;
import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;

import com.curso_simulaciones.mitrigesimacuartaapp.actividades_secundarias.ActividadConfiguracion;
import com.curso_simulaciones.mitrigesimacuartaapp.actividades_secundarias.ActividadDesplegadoraDatos;
import com.curso_simulaciones.mitrigesimacuartaapp.datos.AlmacenDatosRAM;
import com.curso_simulaciones.mitrigesimacuartaapp.gui_auxiliares.DialogoSalir;
import com.curso_simulaciones.mitrigesimacuartaapp.utilidades.Boton;

import java.io.File;

public class ActividadPrincipalMiTrigesimaCuartaApp extends Activity {
    private Boton botonDatos, botonConfigurar, botonSalir;
    private DialogoSalir dialogoSalir;
    private String ruta = null;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        gestionarResolucion();
        crearDirectorioAlmacenamientoDatos();
        crearElementosGUI();
        verificacionPermisos();

        ViewGroup.LayoutParams parametroLayoutPrincipal = new ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT);

        this.setContentView(crearGUI(), parametroLayoutPrincipal);

        dialogoSalir = new DialogoSalir(this);
        existenciaSensor();
        eventos();
    }

    private void gestionarResolucion() {
        DisplayMetrics displayMetrics = this.getApplicationContext().getResources().getDisplayMetrics();
        AlmacenDatosRAM.alto = displayMetrics.heightPixels;
        AlmacenDatosRAM.ancho = displayMetrics.widthPixels;

        int dimensionReferencia;
        if (AlmacenDatosRAM.alto > AlmacenDatosRAM.ancho) {
            dimensionReferencia = AlmacenDatosRAM.ancho;
        } else {
            dimensionReferencia = AlmacenDatosRAM.alto;
        }

        AlmacenDatosRAM.dimensionReferencia = dimensionReferencia;
        int tamanoLetra = dimensionReferencia / 25;
        AlmacenDatosRAM.tamanoLetraResolucionIncluida = (int) (tamanoLetra / displayMetrics.scaledDensity);
    }

    private void crearElementosGUI() {
        botonDatos = new Boton(this);
        botonDatos.setImagen(R.drawable.datos);

        botonConfigurar = new Boton(this);
        botonConfigurar.setImagen(R.drawable.configurar);

        botonSalir = new Boton(this);
        botonSalir.setImagen(R.drawable.salir);
    }

    private LinearLayout crearGUI() {
        LinearLayout linearLayoutPrincipal = new LinearLayout(this);
        linearLayoutPrincipal.setOrientation(LinearLayout.VERTICAL);
        linearLayoutPrincipal.setGravity(Gravity.CENTER_HORIZONTAL | Gravity.FILL);
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
            Drawable fondo = getResources().getDrawable(R.drawable.imagen_entrada_app_34);
            linearLayoutPrimeraFila.setBackgroundDrawable(fondo);
        } catch (Exception e) {
            linearLayoutPrimeraFila.setBackgroundColor(Color.rgb(30, 40, 60));
        }

        // LinearLayout segunda fila
        LinearLayout linearLayoutSegundaFila = new LinearLayout(this);
        linearLayoutSegundaFila.setOrientation(LinearLayout.HORIZONTAL);
        linearLayoutSegundaFila.setGravity(Gravity.FILL);

        LinearLayout.LayoutParams parametrosSegundaFila = new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT, 0);
        parametrosSegundaFila.weight = 2.0f;
        linearLayoutSegundaFila.setWeightSum(3.0f);
        linearLayoutSegundaFila.setLayoutParams(parametrosSegundaFila);

        LinearLayout.LayoutParams parametrosPegadoBoton = new LinearLayout.LayoutParams(
                0, ViewGroup.LayoutParams.MATCH_PARENT);
        parametrosPegadoBoton.weight = 1.0f;

        botonDatos.setLayoutParams(parametrosPegadoBoton);
        botonConfigurar.setLayoutParams(parametrosPegadoBoton);
        botonSalir.setLayoutParams(parametrosPegadoBoton);

        linearLayoutSegundaFila.addView(botonDatos);
        linearLayoutSegundaFila.addView(botonConfigurar);
        linearLayoutSegundaFila.addView(botonSalir);

        linearLayoutPrincipal.addView(linearLayoutPrimeraFila);
        linearLayoutPrincipal.addView(linearLayoutSegundaFila);

        return linearLayoutPrincipal;
    }

    private void crearDirectorioAlmacenamientoDatos() {
        File path = null;
        ruta = "almacen_mis_datos/acelerometro/";

        if (android.os.Build.VERSION.SDK_INT < android.os.Build.VERSION_CODES.Q) {
            // Versiones con android menores a 10
            path = new File(Environment.getExternalStorageDirectory(), ruta);
            if (!path.exists()) {
                path.mkdirs();
            }
        } else {
            // Para versiones de 10 en adelante
            path = new File(getExternalFilesDir(null), ruta);
            if (!path.exists()) {
                path.mkdirs();
            }
        }

        AlmacenDatosRAM.path = path.getAbsolutePath();
    }

    private void eventos() {
        botonDatos.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                lanzarDatos();
            }
        });

        botonConfigurar.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                lanzarConfiguracion();
            }
        });

        botonSalir.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                dialogoSalir.mostrarPopMenu();
            }
        });
    }

    private void lanzarDatos() {
        Intent intent = new Intent(this, ActividadDesplegadoraDatos.class);
        startActivity(intent);
    }

    private void lanzarConfiguracion() {
        Intent intent = new Intent(this, ActividadConfiguracion.class);
        startActivity(intent);
    }

    private boolean existenciaSensor() {
        boolean existe = false;
        SensorManager sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);

        if (sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER) != null) {
            existe = true;
        } else {
            desplegarAviso();
        }

        return existe;
    }

    private void desplegarAviso() {
        Toast toast = Toast.makeText(getApplicationContext(),
                "SU DISPOSITIVO NO POSEE ACELERÓMETRO",
                Toast.LENGTH_SHORT);
        toast.show();
    }

    @SuppressLint("GestureBackNavigation")
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            dialogoSalir.mostrarPopMenu();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    private void verificacionPermisos() {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
            Toast.makeText(this, "This version is not Android 6 or later " + Build.VERSION.SDK_INT,
                    Toast.LENGTH_LONG).show();
        } else {
            int hasReadWritePermission = checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE);
            if (hasReadWritePermission != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(ActividadPrincipalMiTrigesimaCuartaApp.this,
                        new String[]{
                                Manifest.permission.READ_EXTERNAL_STORAGE,
                                Manifest.permission.WRITE_EXTERNAL_STORAGE
                        }, 100);
            }
        }
    }

    @TargetApi(23)
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        switch (requestCode) {
            case 100:
                if (grantResults.length > 0
                        || grantResults[0] == PackageManager.PERMISSION_GRANTED
                        || grantResults[1] == PackageManager.PERMISSION_GRANTED) {
                } else {
                    Toast.makeText(ActividadPrincipalMiTrigesimaCuartaApp.this,
                            "Permission is denied.", Toast.LENGTH_SHORT).show();
                    finish();
                }
                return;
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        finish();
    }
}