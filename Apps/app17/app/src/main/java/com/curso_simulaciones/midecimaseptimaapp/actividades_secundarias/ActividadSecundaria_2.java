package com.curso_simulaciones.midecimaseptimaapp.actividades_secundarias;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.curso_simulaciones.midecimaseptimaapp.datos.AlmacenDatosRAM;

public class ActividadSecundaria_2 extends Activity {

    private EditText edit1, edit2, edit3, edit4;
    private TextView tv1, tv2, tv3, tv4;
    private Button botonGuardar;
    private int tamanoLetra;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        tamanoLetra = AlmacenDatosRAM.tamanoLetraResolucionIncluida;

        LinearLayout root = new LinearLayout(this);
        root.setOrientation(LinearLayout.VERTICAL);
        root.setBackgroundColor(Color.WHITE);

        LinearLayout columnas = new LinearLayout(this);
        columnas.setOrientation(LinearLayout.HORIZONTAL);
        columnas.setWeightSum(2.0f);

        LinearLayout columnaIzq = new LinearLayout(this);
        columnaIzq.setOrientation(LinearLayout.VERTICAL);
        columnaIzq.setBackgroundColor(Color.LTGRAY);

        LinearLayout columnaDer = new LinearLayout(this);
        columnaDer.setOrientation(LinearLayout.VERTICAL);
        columnaDer.setBackgroundColor(Color.GRAY);

        LinearLayout.LayoutParams paramsCol = new LinearLayout.LayoutParams(0, ViewGroup.LayoutParams.MATCH_PARENT);
        paramsCol.weight = 1.0f;
        paramsCol.setMargins(8, 8, 8, 8);

        LinearLayout.LayoutParams paramsCampo = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 0);
        paramsCampo.weight = 1.0f;
        paramsCampo.setMargins(6, 6, 6, 6);

        edit1 = new EditText(this);
        edit1.setHint("Ingrese texto 1");
        edit1.setTextSize(TypedValue.COMPLEX_UNIT_SP, tamanoLetra);

        edit2 = new EditText(this);
        edit2.setHint("Ingrese texto 2");
        edit2.setTextSize(TypedValue.COMPLEX_UNIT_SP, tamanoLetra);

        edit3 = new EditText(this);
        edit3.setHint("Ingrese texto 3");
        edit3.setTextSize(TypedValue.COMPLEX_UNIT_SP, tamanoLetra);

        edit4 = new EditText(this);
        edit4.setHint("Ingrese texto 4");
        edit4.setTextSize(TypedValue.COMPLEX_UNIT_SP, tamanoLetra);

        tv1 = new TextView(this);
        tv1.setText("Campo 1");
        tv1.setTextSize(TypedValue.COMPLEX_UNIT_SP, tamanoLetra);

        tv2 = new TextView(this);
        tv2.setText("Campo 2");
        tv2.setTextSize(TypedValue.COMPLEX_UNIT_SP, tamanoLetra);

        tv3 = new TextView(this);
        tv3.setText("Campo 3");
        tv3.setTextSize(TypedValue.COMPLEX_UNIT_SP, tamanoLetra);

        tv4 = new TextView(this);
        tv4.setText("Campo 4");
        tv4.setTextSize(TypedValue.COMPLEX_UNIT_SP, tamanoLetra);

        columnaIzq.addView(edit1, paramsCampo);
        columnaIzq.addView(edit2, paramsCampo);
        columnaIzq.addView(edit3, paramsCampo);
        columnaIzq.addView(edit4, paramsCampo);

        columnaDer.addView(tv1, paramsCampo);
        columnaDer.addView(tv2, paramsCampo);
        columnaDer.addView(tv3, paramsCampo);
        columnaDer.addView(tv4, paramsCampo);

        columnas.addView(columnaIzq, paramsCol);
        columnas.addView(columnaDer, paramsCol);

        botonGuardar = new Button(this);
        botonGuardar.setText("Guardar y regresar");
        botonGuardar.setTextSize(TypedValue.COMPLEX_UNIT_SP, tamanoLetra);
        botonGuardar.setBackgroundColor(Color.rgb(100, 180, 120));

        botonGuardar.setOnClickListener(v -> {
            AlmacenDatosRAM.campo1 = edit1.getText().toString();
            AlmacenDatosRAM.campo2 = edit2.getText().toString();
            AlmacenDatosRAM.campo3 = edit3.getText().toString();
            AlmacenDatosRAM.campo4 = edit4.getText().toString();

            AlmacenDatosRAM.datosIngresados = true;

            tv1.setText(AlmacenDatosRAM.campo1);
            tv2.setText(AlmacenDatosRAM.campo2);
            tv3.setText(AlmacenDatosRAM.campo3);
            tv4.setText(AlmacenDatosRAM.campo4);

            finish();
        });

        // Imagen inferior identidad.png ubicada en res/drawable/identidad.png
        ImageView imagenInferior = new ImageView(this);
        int resId = getResources().getIdentifier("identidad", "drawable", getPackageName());
        imagenInferior.setImageResource(resId);
        LinearLayout.LayoutParams imgParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        imgParams.setMargins(10, 10, 10, 10);

        root.addView(columnas, new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 0, 1.0f));
        root.addView(botonGuardar, new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        root.addView(imagenInferior, imgParams);

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