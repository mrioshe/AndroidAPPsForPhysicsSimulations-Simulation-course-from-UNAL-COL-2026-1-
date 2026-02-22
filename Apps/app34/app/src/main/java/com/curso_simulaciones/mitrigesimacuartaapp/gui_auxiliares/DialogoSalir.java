package com.curso_simulaciones.mitrigesimacuartaapp.gui_auxiliares;

import android.app.Activity;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.curso_simulaciones.mitrigesimacuartaapp.datos.AlmacenDatosRAM;

public class DialogoSalir {
    private Activity actividad;
    private PopupWindow popupSalir;
    private Button si, no;
    private TextView salir;
    private int tamanoLetra25;
    private int tamanoLetra20;
    private LinearLayout linearLayoutPrincipal;

    public DialogoSalir(Activity actividad) {
        this.actividad = actividad;
        gestionandoResolucion();
        crearGUI();
    }

    private void gestionandoResolucion() {
        float tamanoLetraResolucionIncluida = AlmacenDatosRAM.tamanoLetraResolucionIncluida;
        tamanoLetra25 = (int) tamanoLetraResolucionIncluida;
        tamanoLetra20 = (int) (0.8f * tamanoLetraResolucionIncluida);
    }

    private void crearGUI() {
        linearLayoutPrincipal = new LinearLayout(actividad);
        linearLayoutPrincipal.setBackgroundColor(Color.YELLOW);
        linearLayoutPrincipal.setOrientation(LinearLayout.VERTICAL);
        linearLayoutPrincipal.setWeightSum(3);

        // LinearLayoutArriba
        LinearLayout linearLayoutArriba = new LinearLayout(actividad);
        linearLayoutArriba.setOrientation(LinearLayout.HORIZONTAL);

        // LinearLayoutMedio
        LinearLayout linearLayoutMedio = new LinearLayout(actividad);
        linearLayoutMedio.setOrientation(LinearLayout.HORIZONTAL);

        // LinearLayoutAbajo
        LinearLayout linearLayoutAbajo = new LinearLayout(actividad);
        linearLayoutAbajo.setOrientation(LinearLayout.HORIZONTAL);

        // Parámetro para pegar elementos a cada linearlayout
        LinearLayout.LayoutParams parametros = new LinearLayout.LayoutParams(
                0, ViewGroup.LayoutParams.MATCH_PARENT);
        parametros.weight = 1.0f;

        // Elementos
        salir = new TextView(actividad);
        salir.setTextSize(tamanoLetra20);
        salir.setText("__________ ¿SALIR? __________");
        salir.setTextColor(Color.BLACK);
        salir.setGravity(Gravity.CENTER);

        si = new Button(actividad);
        si.setTextSize(tamanoLetra25);
        si.setText("SÍ");
        si.getBackground().setColorFilter(Color.rgb(255, 255, 100), PorterDuff.Mode.MULTIPLY);

        no = new Button(actividad);
        no.setTextSize(tamanoLetra25);
        no.setText("NO");
        no.getBackground().setColorFilter(Color.rgb(255, 255, 100), PorterDuff.Mode.MULTIPLY);

        linearLayoutArriba.addView(salir, parametros);
        linearLayoutMedio.addView(si, parametros);
        linearLayoutAbajo.addView(no, parametros);

        linearLayoutPrincipal.addView(linearLayoutArriba);
        linearLayoutPrincipal.addView(linearLayoutMedio);
        linearLayoutPrincipal.addView(linearLayoutAbajo);

        eventosPopMenu();
    }

    public void mostrarPopMenu() {
        popupSalir = new PopupWindow(actividad);
        popupSalir.setWidth(WindowManager.LayoutParams.WRAP_CONTENT);
        popupSalir.setHeight(WindowManager.LayoutParams.WRAP_CONTENT);
        popupSalir.setContentView(linearLayoutPrincipal);
        popupSalir.setFocusable(true);
        popupSalir.showAtLocation(linearLayoutPrincipal, Gravity.CENTER, 0, 0);
    }

    private void eventosPopMenu() {
        si.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                actividad.finish();
                popupSalir.dismiss();
            }
        });

        no.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                popupSalir.dismiss();
            }
        });
    }
}