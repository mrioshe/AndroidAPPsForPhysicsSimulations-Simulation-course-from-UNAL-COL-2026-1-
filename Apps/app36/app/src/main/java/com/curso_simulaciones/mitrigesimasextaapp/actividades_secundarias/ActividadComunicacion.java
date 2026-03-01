package com.curso_simulaciones.mitrigesimasextaapp.actividades_secundarias;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;

import com.curso_simulaciones.mitrigesimasextaapp.R;
import com.curso_simulaciones.mitrigesimasextaapp.datos.AlmacenDatosRAM;

/**
 * Actividad para manejar/intercambiar el flujo de comunicación.
 * Muestra la imagen del modelo cliente-servidor y ofrece dos botones
 * para que el usuario elija su rol: CLIENTE o SERVIDOR.
 */
public class ActividadComunicacion extends Activity {

    private Button botonCliente, botonServidor;
    private int tamanoLetra;
    private final int COLOR_BTN = Color.rgb(220, 156, 80);

    @Override
    public void onCreate(Bundle icicle) {
        super.onCreate(icicle);
        tamanoLetra = (int)(0.8 * AlmacenDatosRAM.tamanoLetraResolucionIncluida);
        creacionElementosGUI();
        setContentView(crearGUI());
        eventos();
    }

    private void creacionElementosGUI() {
        botonCliente = new Button(this);
        botonCliente.setTextSize(TypedValue.COMPLEX_UNIT_SP, tamanoLetra);
        botonCliente.setText("CLIENTE");
        botonCliente.getBackground().setColorFilter(COLOR_BTN, PorterDuff.Mode.MULTIPLY);

        botonServidor = new Button(this);
        botonServidor.setTextSize(TypedValue.COMPLEX_UNIT_SP, tamanoLetra);
        botonServidor.setText("SERVIDOR");
        botonServidor.getBackground().setColorFilter(COLOR_BTN, PorterDuff.Mode.MULTIPLY);
    }

    private LinearLayout crearGUI() {
        LinearLayout principal = new LinearLayout(this);
        principal.setOrientation(LinearLayout.VERTICAL);
        principal.setBackgroundColor(Color.WHITE);
        principal.setWeightSum(10f);

        // Fila imagen
        LinearLayout filaPrimera = new LinearLayout(this);
        filaPrimera.setOrientation(LinearLayout.VERTICAL);
        Drawable fondo = getResources().getDrawable(R.drawable.modelo);
        filaPrimera.setBackgroundDrawable(fondo);
        LinearLayout.LayoutParams p1 = new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT, 0);
        p1.weight = 9f;
        filaPrimera.setLayoutParams(p1);

        // Fila botones
        LinearLayout filaSegunda = new LinearLayout(this);
        filaSegunda.setOrientation(LinearLayout.HORIZONTAL);
        filaSegunda.setBackgroundColor(Color.WHITE);
        filaSegunda.setWeightSum(2f);
        LinearLayout.LayoutParams p2 = new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT, 0);
        p2.weight = 1f;
        filaSegunda.setLayoutParams(p2);

        principal.addView(filaPrimera);
        principal.addView(filaSegunda);

        LinearLayout.LayoutParams pBtn = new LinearLayout.LayoutParams(
                0, ViewGroup.LayoutParams.MATCH_PARENT);
        pBtn.weight = 1f;
        filaSegunda.addView(botonCliente,  pBtn);
        filaSegunda.addView(botonServidor, pBtn);

        return principal;
    }

    private void eventos() {
        botonCliente.setOnClickListener(v -> {
            botonServidor.setEnabled(false);
            botonCliente.setEnabled(false);
            AlmacenDatosRAM.rol = "SOY EL CLIENTE";
            startActivity(new Intent(this, ActividadComoClienteBluetooth.class));
        });

        botonServidor.setOnClickListener(v -> {
            botonCliente.setEnabled(false);
            botonServidor.setEnabled(false);
            AlmacenDatosRAM.rol = "SOY EL SERVIDOR";
            startActivity(new Intent(this, ActividadComoServidorBluetooth.class));
        });
    }
}