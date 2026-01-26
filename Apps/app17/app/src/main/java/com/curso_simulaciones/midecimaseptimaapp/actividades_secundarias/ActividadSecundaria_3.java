package com.curso_simulaciones.midecimaseptimaapp.actividades_secundarias;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.curso_simulaciones.midecimaseptimaapp.datos.AlmacenDatosRAM;

public class ActividadSecundaria_3 extends Activity {

    private int tamanoLetra;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        tamanoLetra = AlmacenDatosRAM.tamanoLetraResolucionIncluida;

        LinearLayout root = new LinearLayout(this);
        root.setOrientation(LinearLayout.VERTICAL);
        root.setBackgroundColor(Color.WHITE);
        root.setPadding(16, 16, 16, 16);

        TextView titulo = new TextView(this);
        titulo.setText("Datos ingresados:");
        titulo.setTextSize(TypedValue.COMPLEX_UNIT_SP, tamanoLetra + 2);

        // Mostrar la información almacenada en un EditText (según la guía)
        EditText salida = new EditText(this);
        salida.setTextSize(TypedValue.COMPLEX_UNIT_SP, tamanoLetra);
        salida.setText(
                "Campo1: " + AlmacenDatosRAM.campo1 + "\n" +
                        "Campo2: " + AlmacenDatosRAM.campo2 + "\n" +
                        "Campo3: " + AlmacenDatosRAM.campo3 + "\n" +
                        "Campo4: " + AlmacenDatosRAM.campo4
        );
        salida.setKeyListener(null); // hacerlo no editable (solo para mostrar)

        root.addView(titulo, new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        root.addView(salida, new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));

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