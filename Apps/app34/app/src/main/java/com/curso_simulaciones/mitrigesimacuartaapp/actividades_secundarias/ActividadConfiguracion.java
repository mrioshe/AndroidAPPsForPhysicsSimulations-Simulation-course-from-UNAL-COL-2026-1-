package com.curso_simulaciones.mitrigesimacuartaapp.actividades_secundarias;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.text.method.DigitsKeyListener;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.curso_simulaciones.mitrigesimacuartaapp.datos.AlmacenDatosRAM;

public class ActividadConfiguracion extends Activity {
    private EditText periodoMuestreo, numeroDatos;
    private TextView textPeriodoMuestreo, textNumeroDatos, espacio1, espacio2;
    private int tamanoLetraResolucionIncluida;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        gestionarResolucion();
        crearElementosGUI();

        ViewGroup.LayoutParams parametroLayoutPrincipal = new ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT);

        this.setContentView(crearGUI(), parametroLayoutPrincipal);
    }

    private void gestionarResolucion() {
        tamanoLetraResolucionIncluida = (int) (0.6f * AlmacenDatosRAM.tamanoLetraResolucionIncluida);
    }

    private void crearElementosGUI() {
        espacio1 = new TextView(this);
        espacio1.setTextSize(tamanoLetraResolucionIncluida);
        espacio1.setText("    ");

        espacio2 = new TextView(this);
        espacio2.setTextSize(tamanoLetraResolucionIncluida);
        espacio2.setText("    ");

        textPeriodoMuestreo = new TextView(this);
        textPeriodoMuestreo.setGravity(Gravity.FILL_VERTICAL);
        textPeriodoMuestreo.setBackgroundColor(Color.YELLOW);
        textPeriodoMuestreo.setTextSize(tamanoLetraResolucionIncluida);
        textPeriodoMuestreo.setText("  PERIODO MUESTREO EN ms (Mínimo 50)");
        textPeriodoMuestreo.setTextColor(Color.BLACK);

        textNumeroDatos = new TextView(this);
        textNumeroDatos.setGravity(Gravity.FILL_VERTICAL);
        textNumeroDatos.setBackgroundColor(Color.argb(100, 220, 156, 80));
        textNumeroDatos.setTextSize(tamanoLetraResolucionIncluida);
        textNumeroDatos.setText("  NÚMERO DE DATOS (Máximo 2 000)");
        textNumeroDatos.setTextColor(Color.BLACK);

        numeroDatos = new EditText(this);
        numeroDatos.setKeyListener(DigitsKeyListener.getInstance(false, false));
        numeroDatos.setTextSize(tamanoLetraResolucionIncluida);
        numeroDatos.setText("" + AlmacenDatosRAM.nDatos);

        periodoMuestreo = new EditText(this);
        periodoMuestreo.setKeyListener(DigitsKeyListener.getInstance(false, false));
        periodoMuestreo.setTextSize(tamanoLetraResolucionIncluida);
        periodoMuestreo.setText("" + AlmacenDatosRAM.periodoMuestreo);
    }

    private LinearLayout crearGUI() {
        LinearLayout linearPrincipal = new LinearLayout(this);
        linearPrincipal.setOrientation(LinearLayout.VERTICAL);
        linearPrincipal.setBackgroundColor(Color.WHITE);

        // Fila uno
        LinearLayout linearFilaUno = new LinearLayout(this);
        linearFilaUno.setOrientation(LinearLayout.HORIZONTAL);
        linearFilaUno.setWeightSum(1.0f);

        // Fila dos
        LinearLayout linearFilaDos = new LinearLayout(this);
        linearFilaDos.setOrientation(LinearLayout.HORIZONTAL);
        linearFilaDos.setWeightSum(1.0f);

        // Fila tres
        LinearLayout linearFilaTres = new LinearLayout(this);
        linearFilaTres.setOrientation(LinearLayout.HORIZONTAL);
        linearFilaTres.setWeightSum(3.0f);

        // Fila cuatro
        LinearLayout linearFilaCuatro = new LinearLayout(this);
        linearFilaCuatro.setOrientation(LinearLayout.HORIZONTAL);
        linearFilaCuatro.setWeightSum(3.0f);

        linearPrincipal.addView(linearFilaUno);
        linearPrincipal.addView(linearFilaDos);
        linearPrincipal.addView(linearFilaTres);
        linearPrincipal.addView(linearFilaCuatro);

        // Pegado de elementos a fila uno
        LinearLayout.LayoutParams parametrosPegadoElementosFilaUno = new LinearLayout.LayoutParams(
                0, android.view.ViewGroup.LayoutParams.MATCH_PARENT);
        parametrosPegadoElementosFilaUno.weight = 1.0f;
        linearFilaUno.addView(espacio1, parametrosPegadoElementosFilaUno);

        // Pegado de elementos a fila dos
        LinearLayout.LayoutParams parametrosPegadoElementosFilaDos = new LinearLayout.LayoutParams(
                0, android.view.ViewGroup.LayoutParams.MATCH_PARENT);
        parametrosPegadoElementosFilaDos.weight = 1.0f;
        linearFilaDos.addView(espacio2, parametrosPegadoElementosFilaDos);

        // Pegado de elementos a fila tres
        LinearLayout.LayoutParams parametrosPegadoElementosFilaTresIzquierda = new LinearLayout.LayoutParams(
                0, android.view.ViewGroup.LayoutParams.MATCH_PARENT);
        parametrosPegadoElementosFilaTresIzquierda.weight = 2.0f;
        linearFilaTres.addView(textPeriodoMuestreo, parametrosPegadoElementosFilaTresIzquierda);

        LinearLayout.LayoutParams parametrosPegadoElementosFilaTresDerecha = new LinearLayout.LayoutParams(
                0, android.view.ViewGroup.LayoutParams.MATCH_PARENT);
        parametrosPegadoElementosFilaTresDerecha.weight = 1.0f;
        linearFilaTres.addView(periodoMuestreo, parametrosPegadoElementosFilaTresDerecha);

        // Pegado de elementos a fila cuatro
        LinearLayout.LayoutParams parametrosPegadoElementosFilaCuatroIzquierda = new LinearLayout.LayoutParams(
                0, android.view.ViewGroup.LayoutParams.MATCH_PARENT);
        parametrosPegadoElementosFilaCuatroIzquierda.weight = 2.0f;
        linearFilaCuatro.addView(textNumeroDatos, parametrosPegadoElementosFilaCuatroIzquierda);

        LinearLayout.LayoutParams parametrosPegadoElementosFilaCuatroDerecha = new LinearLayout.LayoutParams(
                0, android.view.ViewGroup.LayoutParams.MATCH_PARENT);
        parametrosPegadoElementosFilaCuatroDerecha.weight = 1.0f;
        linearFilaCuatro.addView(numeroDatos, parametrosPegadoElementosFilaCuatroDerecha);

        return linearPrincipal;
    }

    @Override
    protected void onPause() {
        super.onPause();

        AlmacenDatosRAM.configurar = true;

        String valorMuestreo = periodoMuestreo.getText().toString();
        String valorN = numeroDatos.getText().toString();

        if (!valorMuestreo.isEmpty()) {
            AlmacenDatosRAM.periodoMuestreo = Integer.parseInt(valorMuestreo);
        }

        if (!valorN.isEmpty()) {
            AlmacenDatosRAM.nDatos = Integer.parseInt(valorN);
        }
    }
}