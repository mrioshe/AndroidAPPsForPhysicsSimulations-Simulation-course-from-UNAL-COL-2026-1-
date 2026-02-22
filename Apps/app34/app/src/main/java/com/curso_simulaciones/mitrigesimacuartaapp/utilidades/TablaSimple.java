package com.curso_simulaciones.mitrigesimacuartaapp.utilidades;

import android.content.Context;
import android.graphics.Color;
import android.util.TypedValue;
import android.view.Gravity;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.curso_simulaciones.mitrigesimacuartaapp.datos.AlmacenDatosRAM;

public class TablaSimple extends LinearLayout {
    private ScrollView panelScroll;
    private TableLayout table;
    private int tamanoLetraResolucionIncluida;
    private int dimensionReferencia;
    private Context context;
    private int contador = -1;

    // Etiquetas de las 6 columnas
    private String etiquetaNumeroDato = "# Dato";
    private String etiquetaTiempo = "Tiempo";
    private String etiquetaAx = "ax";
    private String etiquetaAy = "ay";
    private String etiquetaAz = "az";
    private String etiquetaA = "a";

    // Colores para las columnas
    private int colorColumna1 = Color.YELLOW;
    private int colorColumna2 = Color.CYAN;
    private int colorColumna3 = Color.rgb(255, 150, 150);
    private int colorColumna4 = Color.rgb(150, 255, 150);
    private int colorColumna5 = Color.rgb(150, 150, 255);
    private int colorColumna6 = Color.rgb(255, 200, 100);

    public TablaSimple(Context context) {
        super(context);
        this.context = context;
        gestionarResolucion();
        this.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));
        gui();
    }

    private void gestionarResolucion() {
        dimensionReferencia = (int) (0.4f * AlmacenDatosRAM.alto);
        tamanoLetraResolucionIncluida = (int) (0.6 * AlmacenDatosRAM.tamanoLetraResolucionIncluida);
    }

    private void gui() {
        panelScroll = new ScrollView(context);
        table = new TableLayout(context);

        LinearLayout linearLayoutPrincipal = new LinearLayout(context);
        linearLayoutPrincipal.setOrientation(LinearLayout.VERTICAL);
        linearLayoutPrincipal.setBackgroundColor(Color.BLACK);

        LinearLayout.LayoutParams parametroPegado = new LinearLayout.LayoutParams(
                LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);

        panelScroll.addView(table);
        linearLayoutPrincipal.addView(panelScroll);
        this.addView(linearLayoutPrincipal, parametroPegado);
    }

    /**
     * Modifica las etiquetas de las columnas
     */
    public void setEtiquetaColumnas(String etiquetaNumeroDato, String etiquetaTiempo,
                                    String etiquetaAx, String etiquetaAy,
                                    String etiquetaAz, String etiquetaA) {
        this.etiquetaNumeroDato = etiquetaNumeroDato;
        this.etiquetaTiempo = etiquetaTiempo;
        this.etiquetaAx = etiquetaAx;
        this.etiquetaAy = etiquetaAy;
        this.etiquetaAz = etiquetaAz;
        this.etiquetaA = etiquetaA;
    }

    /**
     * Envía los datos a la tabla (6 columnas)
     */
    public void enviarDatos(int numeroDato, float tiempo, float ax, float ay, float az, float a) {
        contador = contador + 1;
        incrementarFila(numeroDato, tiempo, ax, ay, az, a);
    }

    /**
     * Borra los datos enviados a la tabla
     */
    public void borrar() {
        removerFilas();
    }

    /**
     * Modifica los colores de las columnas
     */
    public void setColorColumnas(int color1, int color2, int color3, int color4, int color5, int color6) {
        this.colorColumna1 = color1;
        this.colorColumna2 = color2;
        this.colorColumna3 = color3;
        this.colorColumna4 = color4;
        this.colorColumna5 = color5;
        this.colorColumna6 = color6;
    }

    private void incrementarFila(int numeroDato, float tiempo, float ax, float ay, float az, float a) {
        TableRow fila = new TableRow(context);

        // Ancho de cada columna (aproximadamente 1/6 del ancho disponible)
        TableRow.LayoutParams layoutTexto = new TableRow.LayoutParams(
                (int) (0.16 * dimensionReferencia), TableRow.LayoutParams.WRAP_CONTENT);

        // Columna 1: Número de dato
        TextView textNumeroDato = new TextView(context);
        textNumeroDato.setTextSize(TypedValue.COMPLEX_UNIT_SP, tamanoLetraResolucionIncluida);
        textNumeroDato.setLayoutParams(layoutTexto);

        if (contador == 0) {
            textNumeroDato.setText(etiquetaNumeroDato);
        } else {
            textNumeroDato.setText("" + numeroDato);
        }

        textNumeroDato.setTextColor(colorColumna1);
        textNumeroDato.setGravity(Gravity.CENTER_HORIZONTAL);

        // Columna 2: Tiempo
        TextView textTiempo = new TextView(context);
        textTiempo.setTextSize(TypedValue.COMPLEX_UNIT_SP, tamanoLetraResolucionIncluida);
        textTiempo.setLayoutParams(layoutTexto);

        if (contador == 0) {
            textTiempo.setText(etiquetaTiempo);
        } else {
            textTiempo.setText(String.format("%.2f", tiempo));
        }

        textTiempo.setTextColor(colorColumna2);
        textTiempo.setGravity(Gravity.CENTER_HORIZONTAL);

        // Columna 3: ax
        TextView textAx = new TextView(context);
        textAx.setTextSize(TypedValue.COMPLEX_UNIT_SP, tamanoLetraResolucionIncluida);
        textAx.setLayoutParams(layoutTexto);

        if (contador == 0) {
            textAx.setText(etiquetaAx);
        } else {
            textAx.setText(String.format("%.2f", ax));
        }

        textAx.setTextColor(colorColumna3);
        textAx.setGravity(Gravity.CENTER_HORIZONTAL);

        // Columna 4: ay
        TextView textAy = new TextView(context);
        textAy.setTextSize(TypedValue.COMPLEX_UNIT_SP, tamanoLetraResolucionIncluida);
        textAy.setLayoutParams(layoutTexto);

        if (contador == 0) {
            textAy.setText(etiquetaAy);
        } else {
            textAy.setText(String.format("%.2f", ay));
        }

        textAy.setTextColor(colorColumna4);
        textAy.setGravity(Gravity.CENTER_HORIZONTAL);

        // Columna 5: az
        TextView textAz = new TextView(context);
        textAz.setTextSize(TypedValue.COMPLEX_UNIT_SP, tamanoLetraResolucionIncluida);
        textAz.setLayoutParams(layoutTexto);

        if (contador == 0) {
            textAz.setText(etiquetaAz);
        } else {
            textAz.setText(String.format("%.2f", az));
        }

        textAz.setTextColor(colorColumna5);
        textAz.setGravity(Gravity.CENTER_HORIZONTAL);

        // Columna 6: a (magnitud)
        TextView textA = new TextView(context);
        textA.setTextSize(TypedValue.COMPLEX_UNIT_SP, tamanoLetraResolucionIncluida);
        textA.setLayoutParams(layoutTexto);

        if (contador == 0) {
            textA.setText(etiquetaA);
        } else {
            textA.setText(String.format("%.2f", a));
        }

        textA.setTextColor(colorColumna6);
        textA.setGravity(Gravity.CENTER_HORIZONTAL);

        fila.setGravity(Gravity.CENTER_HORIZONTAL);

        // Adicionar las seis columnas a la fila
        fila.addView(textNumeroDato);
        fila.addView(textTiempo);
        fila.addView(textAx);
        fila.addView(textAy);
        fila.addView(textAz);
        fila.addView(textA);

        // Adicionar TabRow a la Tabla
        table.addView(fila, new TableLayout.LayoutParams(
                LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));
    }

    private void removerFilas() {
        if (contador > 1) {
            table.removeAllViews();
            contador = -1;
        }
    }
}