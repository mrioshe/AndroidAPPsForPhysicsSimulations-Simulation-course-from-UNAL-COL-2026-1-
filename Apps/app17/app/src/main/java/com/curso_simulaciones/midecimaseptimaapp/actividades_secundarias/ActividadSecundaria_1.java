package com.curso_simulaciones.midecimaseptimaapp.actividades_secundarias;

import android.app.Activity;
import android.os.Bundle;
import android.view.ViewGroup;
import android.widget.LinearLayout;

public class ActividadSecundaria_1 extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Layout principal con imagen de fondo gatico.png
        LinearLayout root = new LinearLayout(this);
        root.setOrientation(LinearLayout.VERTICAL);

        // Establecer la imagen de fondo que debe estar en res/drawable/gatico.png
        root.setBackgroundResource(getResources().getIdentifier("gatico", "drawable", getPackageName()));

        ViewGroup.LayoutParams parametro_layout_principal =
                new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        setContentView(root, parametro_layout_principal);
    }

    @Override
    protected void onDestroy() {
        this.finish();
        super.onDestroy();
    }
}