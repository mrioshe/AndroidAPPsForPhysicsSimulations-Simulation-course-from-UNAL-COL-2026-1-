package com.curso_simulaciones.micuadragesimasegundaapp.actividades_secundarias;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.curso_simulaciones.micuadragesimasegundaapp.R;
import com.curso_simulaciones.micuadragesimasegundaapp.utilidades.Boton;

/**
 * Pantalla que permite elegir el rol del dispositivo: PUB (publicador) o SUB (suscriptor).
 * Muestra la imagen del modelo IoT de fondo y dos botones de acceso.
 */
public class ActividadComunicacion extends Activity {

    private Boton pub, sub;

    @Override
    public void onCreate(Bundle icicle) {
        super.onCreate(icicle);
        creacionElementosGUI();
        setContentView(crearGUI());
        eventos();
    }

    private void creacionElementosGUI() {
        pub = new Boton(this);
        pub.setImagen(R.drawable.wifi_subida);

        sub = new Boton(this);
        sub.setImagen(R.drawable.wifi_bajada);
    }

    private LinearLayout crearGUI() {
        LinearLayout principal = new LinearLayout(this);
        principal.setOrientation(LinearLayout.VERTICAL);
        principal.setBackgroundColor(Color.WHITE);
        principal.setWeightSum(10f);

        // Fila 1: imagen de fondo (modelo IoT)
        LinearLayout fila1 = new LinearLayout(this);
        fila1.setOrientation(LinearLayout.VERTICAL);
        Drawable fondo = getResources().getDrawable(R.drawable.modelo);
        fila1.setBackgroundDrawable(fondo);
        LinearLayout.LayoutParams p1 = new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT, 0);
        p1.weight = 9f;
        fila1.setLayoutParams(p1);

        // Fila 2: botones PUB / SUB
        LinearLayout fila2 = new LinearLayout(this);
        fila2.setOrientation(LinearLayout.HORIZONTAL);
        fila2.setBackgroundColor(Color.WHITE);
        fila2.setWeightSum(2f);
        LinearLayout.LayoutParams p2 = new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT, 0);
        p2.weight = 1f;
        fila2.setLayoutParams(p2);

        principal.addView(fila1);
        principal.addView(fila2);

        LinearLayout.LayoutParams pBtn = new LinearLayout.LayoutParams(
                0, ViewGroup.LayoutParams.MATCH_PARENT);
        pBtn.weight = 1f;
        fila2.addView(pub, pBtn);
        fila2.addView(sub, pBtn);

        return principal;
    }

    private void eventos() {
        pub.setOnClickListener(v ->
                startActivity(new Intent(this, ActividadComoClientePubMQTT.class)));

        sub.setOnClickListener(v ->
                startActivity(new Intent(this, ActividadComoClienteSubMQTT.class)));
    }
}