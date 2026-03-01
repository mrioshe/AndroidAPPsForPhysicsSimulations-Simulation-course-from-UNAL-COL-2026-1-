package com.curso_simulaciones.mitrigesimasextaapp;

import android.Manifest;
import android.annotation.TargetApi;
import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresPermission;
import androidx.core.app.ActivityCompat;

import com.curso_simulaciones.mitrigesimasextaapp.actividades_secundarias.ActividadComunicacion;
import com.curso_simulaciones.mitrigesimasextaapp.datos.AlmacenDatosRAM;
import com.curso_simulaciones.mitrigesimasextaapp.utilidades.Boton;

/**
 * Actividad/entry point principal de la aplicación MiTrigesimaSextaApp.
 *
 * Responsabilidades:
 *  1. Calcular resolución de pantalla y almacenarla en AlmacenDatosRAM.
 *  2. Solicitar permisos Bluetooth en tiempo de ejecución (Android 12+).
 *  3. Activar el adaptador Bluetooth.
 *  4. Lanzar ActividadComunicacion para elegir rol (CLIENTE / SERVIDOR).
 */
public class ActividadPrincipalMiTrigesimaSextaApp extends Activity {

    private Boton entrar, salir;
    private BluetoothAdapter BA;
    private LinearLayout linear_layout_segunda_fila;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        gestionarResolucion();
        crearElementosGUI();
        setContentView(crearGUI(),
                new ViewGroup.LayoutParams(
                        ViewGroup.LayoutParams.MATCH_PARENT,
                        ViewGroup.LayoutParams.MATCH_PARENT));
        eventos();
        verificacionPermisos();
    }

    // ── Resolución ────────────────────────────────────────────────────────────
    private void gestionarResolucion() {
        DisplayMetrics dm = getApplicationContext().getResources().getDisplayMetrics();
        int alto  = dm.heightPixels;
        int ancho = dm.widthPixels;

        AlmacenDatosRAM.alto  = alto;
        AlmacenDatosRAM.ancho = ancho;

        int dimRef = (alto > ancho) ? ancho : alto;
        AlmacenDatosRAM.dimensionReferencia = dimRef;

        int tamanoLetra = dimRef / 20;
        AlmacenDatosRAM.tamanoLetraResolucionIncluida =
                (int)(tamanoLetra / dm.scaledDensity);
    }

    // ── GUI ───────────────────────────────────────────────────────────────────
    private void crearElementosGUI() {
        entrar = new Boton(this);
        entrar.setImagen(R.drawable.entrar);
        salir  = new Boton(this);
        salir.setImagen(R.drawable.salir);
    }

    private LinearLayout crearGUI() {
        LinearLayout llPrincipal = new LinearLayout(this);
        llPrincipal.setOrientation(LinearLayout.VERTICAL);
        llPrincipal.setGravity(Gravity.FILL);
        llPrincipal.setBackgroundColor(Color.WHITE);
        llPrincipal.setWeightSum(10f);

        // Fila 1: imagen fondo
        LinearLayout llPrimera = new LinearLayout(this);
        llPrimera.setOrientation(LinearLayout.HORIZONTAL);
        llPrimera.setGravity(Gravity.FILL);
        Drawable fondo = getResources().getDrawable(R.drawable.comunicacion_cliente_servidor);
        llPrimera.setBackgroundDrawable(fondo);
        LinearLayout.LayoutParams p1 = new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT, 0);
        p1.weight = 8f;
        llPrimera.setLayoutParams(p1);

        // Fila 2: botones
        linear_layout_segunda_fila = new LinearLayout(this);
        linear_layout_segunda_fila.setOrientation(LinearLayout.HORIZONTAL);
        linear_layout_segunda_fila.setGravity(Gravity.FILL);
        linear_layout_segunda_fila.setWeightSum(1f);
        LinearLayout.LayoutParams p2 = new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT, 0);
        p2.weight = 2f;
        linear_layout_segunda_fila.setLayoutParams(p2);

        LinearLayout.LayoutParams pBtn = new LinearLayout.LayoutParams(
                0, ViewGroup.LayoutParams.MATCH_PARENT);
        pBtn.weight = 1f;
        entrar.setLayoutParams(pBtn);
        salir.setLayoutParams(pBtn);
        linear_layout_segunda_fila.addView(entrar);

        llPrincipal.addView(llPrimera);
        llPrincipal.addView(linear_layout_segunda_fila);

        return llPrincipal;
    }

    // ── Eventos ───────────────────────────────────────────────────────────────
    private void eventos() {
        entrar.setOnClickListener(v -> {
            activarBluetooth();
            startActivity(new Intent(this, ActividadComunicacion.class));
            linear_layout_segunda_fila.removeAllViews();
            linear_layout_segunda_fila.addView(salir);
        });

        salir.setOnClickListener(v -> finish());
    }

    // ── Bluetooth ─────────────────────────────────────────────────────────────
    @RequiresPermission(Manifest.permission.BLUETOOTH_CONNECT)
    private void activarBluetooth() {
        BA = BluetoothAdapter.getDefaultAdapter();
        if (!BA.isEnabled()) BA.enable();
    }

    // ── Ciclo de vida ─────────────────────────────────────────────────────────
    @Override protected void onPause() { super.onPause(); }

    @RequiresPermission(Manifest.permission.BLUETOOTH_CONNECT)
    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (BA != null) BA.disable();
        AlmacenDatosRAM.conexion_bluetooth = " ";
        finish();
    }

    // ── Permisos Bluetooth (Android 12+) ──────────────────────────────────────
    private void verificacionPermisos() {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.S) {
            Toast.makeText(this,
                    "Android < 12  (API " + Build.VERSION.SDK_INT + ")",
                    Toast.LENGTH_LONG).show();
        } else {
            int hasBT   = checkSelfPermission(Manifest.permission.BLUETOOTH);
            int hasScan = checkSelfPermission(Manifest.permission.BLUETOOTH_SCAN);

            if (hasBT   != PackageManager.PERMISSION_GRANTED ||
                    hasScan != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(this,
                        new String[]{
                                Manifest.permission.BLUETOOTH_ADMIN,
                                Manifest.permission.BLUETOOTH_ADVERTISE,
                                Manifest.permission.BLUETOOTH_SCAN,
                                Manifest.permission.BLUETOOTH_CONNECT
                        }, 100);
            }
        }
    }

    @TargetApi(23)
    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        if (requestCode == 100) {
            boolean ok = grantResults.length >= 4
                    && grantResults[0] == PackageManager.PERMISSION_GRANTED
                    && grantResults[1] == PackageManager.PERMISSION_GRANTED
                    && grantResults[2] == PackageManager.PERMISSION_GRANTED
                    && grantResults[3] == PackageManager.PERMISSION_GRANTED;
            if (!ok) {
                Toast.makeText(this, "Permiso denegado.", Toast.LENGTH_SHORT).show();
                finish();
            }
        }
    }
}