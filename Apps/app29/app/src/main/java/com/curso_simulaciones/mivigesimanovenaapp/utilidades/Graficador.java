package com.curso_simulaciones.mivigesimanovenaapp.utilidades;

import android.content.Context;
import android.graphics.Color;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;

import java.util.ArrayList;

public class Graficador extends LineChart {
    private String tituloEjeX = "Tiempo (s)";
    private String tituloEjeY = "Campo Magnético (µT)";
    private float grosorLinea = 2.5f;
    private int colorLinea = Color.GREEN;
    private int colorValores = Color.YELLOW;
    private int colorMarcadores = Color.CYAN;
    private int colorFondo = Color.BLACK;
    private int colorTextoEjes = Color.WHITE;

    public Graficador(Context context) {
        super(context);
        configurarGrafica();
    }

    private void configurarGrafica() {
        // Configuración general
        this.setBackgroundColor(colorFondo);
        this.setDrawGridBackground(false);
        this.setTouchEnabled(true);
        this.setDragEnabled(true);
        this.setScaleEnabled(true);
        this.setPinchZoom(true);
        this.setDoubleTapToZoomEnabled(true);

        // Descripción
        this.getDescription().setEnabled(false);

        // Configurar eje X
        XAxis xAxis = this.getXAxis();
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setTextColor(colorTextoEjes);
        xAxis.setDrawGridLines(true);
        xAxis.setGridColor(Color.rgb(60, 60, 80));
        xAxis.setAxisLineColor(colorTextoEjes);
        xAxis.setTextSize(12f);

        // Configurar eje Y izquierdo
        YAxis yAxisLeft = this.getAxisLeft();
        yAxisLeft.setTextColor(colorTextoEjes);
        yAxisLeft.setDrawGridLines(true);
        yAxisLeft.setGridColor(Color.rgb(60, 60, 80));
        yAxisLeft.setAxisLineColor(colorTextoEjes);
        yAxisLeft.setTextSize(12f);

        // Deshabilitar eje Y derecho
        YAxis yAxisRight = this.getAxisRight();
        yAxisRight.setEnabled(false);

        // Configurar leyenda
        Legend legend = this.getLegend();
        legend.setEnabled(true);
        legend.setTextColor(colorTextoEjes);
        legend.setTextSize(12f);
        legend.setForm(Legend.LegendForm.LINE);
    }

    public void setTituloEjeX(String titulo) {
        this.tituloEjeX = titulo;
        XAxis xAxis = this.getXAxis();
        // MPAndroidChart no tiene método directo para título de eje
        // Se usa el label en la leyenda
    }

    public void setTituloEjeY(String titulo) {
        this.tituloEjeY = titulo;
    }

    public void setGrosorLinea(float grosor) {
        this.grosorLinea = grosor;
    }

    public void setColorLinea(int color) {
        this.colorLinea = color;
    }

    public void setColorValores(int color) {
        this.colorValores = color;
    }

    public void setColorMarcadores(int color) {
        this.colorMarcadores = color;
    }

    public void setColorFondo(int color) {
        this.colorFondo = color;
        this.setBackgroundColor(colorFondo);
    }

    public void setColorTextoEjes(int color) {
        this.colorTextoEjes = color;

        XAxis xAxis = this.getXAxis();
        xAxis.setTextColor(colorTextoEjes);
        xAxis.setAxisLineColor(colorTextoEjes);

        YAxis yAxisLeft = this.getAxisLeft();
        yAxisLeft.setTextColor(colorTextoEjes);
        yAxisLeft.setAxisLineColor(colorTextoEjes);

        Legend legend = this.getLegend();
        legend.setTextColor(colorTextoEjes);
    }

    public void actualizarGrafica(ArrayList<Float> datos) {
        if (datos == null || datos.isEmpty()) {
            return;
        }

        ArrayList<Entry> entries = new ArrayList<>();

        for (int i = 0; i < datos.size(); i++) {
            entries.add(new Entry(i, datos.get(i)));
        }

        LineDataSet dataSet = new LineDataSet(entries, tituloEjeY);

        // Configurar apariencia del dataset
        dataSet.setColor(colorLinea);
        dataSet.setLineWidth(grosorLinea);
        dataSet.setDrawCircles(true);
        dataSet.setCircleColor(colorMarcadores);
        dataSet.setCircleRadius(3f);
        dataSet.setDrawCircleHole(false);
        dataSet.setValueTextColor(colorValores);
        dataSet.setValueTextSize(9f);
        dataSet.setDrawValues(false);  // No mostrar valores por defecto para no saturar
        dataSet.setMode(LineDataSet.Mode.LINEAR);

        // Efecto de relleno bajo la línea (opcional)
        dataSet.setDrawFilled(true);
        dataSet.setFillColor(colorLinea);
        dataSet.setFillAlpha(30);

        LineData lineData = new LineData(dataSet);
        this.setData(lineData);

        // Animación suave
        this.animateX(300);

        // Refrescar la gráfica
        this.invalidate();
    }
}