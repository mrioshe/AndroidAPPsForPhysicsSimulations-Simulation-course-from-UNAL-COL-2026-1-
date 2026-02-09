package com.curso_simulaciones.mivigesimaquintaapp.controlador;

import android.app.Activity;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.SeekBar;
import android.widget.TextView;

import com.curso_simulaciones.mivigesimaquintaapp.datos.AlmacenDatosRAM;
import com.curso_simulaciones.mivigesimaquintaapp.modelo.ModeloFisico;
import com.curso_simulaciones.mivigesimaquintaapp.utilidades.Boton;
import com.curso_simulaciones.mivigesimaquintaapp.vista.CR;
import com.curso_simulaciones.mivigesimaquintaapp.vista.Pizarra;
import com.curso_simulaciones.simulphysics.objetos_laboratorio.CuerpoRectangular;
import com.curso_simulaciones.simulphysics.objetos_laboratorio.Cuerda;
import com.curso_simulaciones.simulphysics.objetos_laboratorio.Marca;
import com.curso_simulaciones.simulphysics.objetos_laboratorio.Masa;
import com.curso_simulaciones.simulphysics.objetos_laboratorio.ObjetoLaboratorio;
import com.curso_simulaciones.simulphysics.objetos_laboratorio.Polea;

/**
 * Actividad principal que controla la simulación del sistema de poleas
 */
public class ActividadControladora extends Activity {

    private Pizarra pizarra;
    private HiloAnimacion hiloAnimacion;
    private ModeloFisico modeloFisico;

    // Objetos de la escena
    private Masa masa_1, masa_2, masa_3;
    private Polea polea_azul_izq, polea_azul_der, polea_verde_P;
    private CuerpoRectangular cuerpo_amarillo, base_oscura;
    private Cuerda cuerda_horizontal, cuerda_m1, cuerda_vertical_a_P, cuerda_m2, cuerda_m3;
    private Marca marca_P, marca_m1, marca_m2, marca_m3;
    private ObjetoLaboratorio[] objetos = new ObjetoLaboratorio[20];

    // Posiciones de referencia (en píxeles)
    private float radio, radio_verde;
    private float xp_azul_izq, yp_azul_izq;
    private float xp_azul_der, yp_azul_der;
    private float alto_masa, ancho_masa;

    // Elementos de la GUI - Panel izquierdo
    private TextView tv_tiempo, tv_m1_info, tv_m2_info, tv_m3_info;
    private TextView tv_y1, tv_y2, tv_y3, tv_yP;
    private TextView tv_v1, tv_v2, tv_v3, tv_vP;
    private TextView tv_a1, tv_a2, tv_a3, tv_aP;
    private TextView tv_T, tv_T2, tv_T3;

    // Elementos de la GUI - Panel derecho (controles) - CAMBIADO A SEEKBAR
    private SeekBar seekBarM1, seekBarM2, seekBarM3;
    private TextView tvValorM1, tvValorM2, tvValorM3;
    private Boton btnIniciar, btnPausar, btnReiniciar;

    private int tamanoLetraResolucionIncluida;
    private boolean simulacionIniciada = false;

    // Rangos para los sliders (kg)
    private static final float MIN_MASA = 1.0f;
    private static final float MAX_MASA = 30.0f;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        gestionarResolucion();
        crearElementosGUI();

        ViewGroup.LayoutParams parametro_layout_principal = new ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT);

        this.setContentView(crearGUI(), parametro_layout_principal);

        // Crear el hilo de animación (pero no iniciarlo aún)
        hiloAnimacion = new HiloAnimacion(this);
    }

    private void gestionarResolucion() {
        DisplayMetrics displayMetrics = this.getApplicationContext().getResources().getDisplayMetrics();
        int alto = displayMetrics.heightPixels;
        int ancho = displayMetrics.widthPixels;
        int dimensionReferencia;

        if (alto > ancho) {
            dimensionReferencia = ancho;
        } else {
            dimensionReferencia = alto;
        }

        int tamanoLetra = dimensionReferencia / 25;
        tamanoLetraResolucionIncluida = (int) (tamanoLetra / displayMetrics.scaledDensity);

        AlmacenDatosRAM.tamanoLetraResolucionIncluida = tamanoLetraResolucionIncluida;
        AlmacenDatosRAM.ancho_pantalla = ancho;
        AlmacenDatosRAM.alto_pantalla = alto;
    }

    private void crearElementosGUI() {
        pizarra = new Pizarra(this);
        pizarra.setBackgroundColor(Color.WHITE);

        crearObjetosLaboratorio();

        modeloFisico = new ModeloFisico();

        AlmacenDatosRAM.m1 = 15.0f;
        AlmacenDatosRAM.m2 = 10.0f;
        AlmacenDatosRAM.m3 = 8.0f;
        AlmacenDatosRAM.tiempo = 0.0f;

        // TextViews para información
        tv_tiempo = new TextView(this);
        tv_m1_info = new TextView(this);
        tv_m2_info = new TextView(this);
        tv_m3_info = new TextView(this);
        tv_y1 = new TextView(this);
        tv_y2 = new TextView(this);
        tv_y3 = new TextView(this);
        tv_yP = new TextView(this);
        tv_v1 = new TextView(this);
        tv_v2 = new TextView(this);
        tv_v3 = new TextView(this);
        tv_vP = new TextView(this);
        tv_a1 = new TextView(this);
        tv_a2 = new TextView(this);
        tv_a3 = new TextView(this);
        tv_aP = new TextView(this);
        tv_T = new TextView(this);
        tv_T2 = new TextView(this);
        tv_T3 = new TextView(this);

        // CAMBIADO: SeekBars en vez de EditText
        seekBarM1 = crearSeekBar();
        seekBarM2 = crearSeekBar();
        seekBarM3 = crearSeekBar();

        // TextViews para mostrar valores actuales de los sliders
        tvValorM1 = crearTextoValor();
        tvValorM2 = crearTextoValor();
        tvValorM3 = crearTextoValor();

        // Establecer valores iniciales
        seekBarM1.setProgress(masaAProgress(AlmacenDatosRAM.m1));
        seekBarM2.setProgress(masaAProgress(AlmacenDatosRAM.m2));
        seekBarM3.setProgress(masaAProgress(AlmacenDatosRAM.m3));

        actualizarTextoValor(tvValorM1, AlmacenDatosRAM.m1);
        actualizarTextoValor(tvValorM2, AlmacenDatosRAM.m2);
        actualizarTextoValor(tvValorM3, AlmacenDatosRAM.m3);

        btnIniciar = new Boton(this);
        btnIniciar.setText("INICIAR");
        btnIniciar.setColorFondo(Color.rgb(76, 175, 80)); // Verde

        btnPausar = new Boton(this);
        btnPausar.setText("PAUSAR");
        btnPausar.setColorFondo(Color.rgb(255, 152, 0)); // Naranja
        btnPausar.setEnabled(false);

        btnReiniciar = new Boton(this);
        btnReiniciar.setText("REINICIAR");
        btnReiniciar.setColorFondo(Color.rgb(244, 67, 54)); // Rojo
    }

    private SeekBar crearSeekBar() {
        SeekBar seekBar = new SeekBar(this);
        seekBar.setMax(100); // 0-100 para mapear MIN_MASA a MAX_MASA

        // Reducir el tamaño del SeekBar
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT);
        params.setMargins(0, 0, 0, 0); // Sin márgenes
        seekBar.setLayoutParams(params);

        // Reducir el padding del SeekBar si es posible
        seekBar.setPadding(0, 0, 0, 0);

        return seekBar;
    }

    private TextView crearTextoValor() {
        TextView tv = new TextView(this);
        tv.setTextSize(tamanoLetraResolucionIncluida * 0.65f); // Reducido de 0.75f a 0.65f
        tv.setTextColor(Color.rgb(0, 100, 200));
        tv.setGravity(Gravity.CENTER);
        tv.setTypeface(null, Typeface.BOLD);
        tv.setPadding(0, 0, 0, 0); // Sin padding
        return tv;
    }

    private void actualizarTextoValor(TextView tv, float masa) {
        tv.setText(String.format("%.1f kg", masa));
    }

    private int masaAProgress(float masa) {
        return (int) ((masa - MIN_MASA) / (MAX_MASA - MIN_MASA) * 100);
    }

    private float progressAMasa(int progress) {
        return MIN_MASA + (progress / 100.0f) * (MAX_MASA - MIN_MASA);
    }

    private LinearLayout crearGUI() {
        LinearLayout linearLayoutPrincipal = new LinearLayout(this);
        linearLayoutPrincipal.setOrientation(LinearLayout.HORIZONTAL);
        linearLayoutPrincipal.setGravity(Gravity.FILL);
        linearLayoutPrincipal.setBackgroundColor(Color.WHITE);
        linearLayoutPrincipal.setWeightSum(10);

        // Panel izquierdo con información en tiempo real
        ScrollView scrollIzq = new ScrollView(this);
        LinearLayout panelIzquierdo = crearPanelIzquierdo();
        scrollIzq.addView(panelIzquierdo);
        LinearLayout.LayoutParams parametrosPanelIzq = new LinearLayout.LayoutParams(0, ViewGroup.LayoutParams.MATCH_PARENT);
        parametrosPanelIzq.weight = 2.5f;
        scrollIzq.setLayoutParams(parametrosPanelIzq);

        // Pizarra en el centro
        LinearLayout.LayoutParams parametrosPizarra = new LinearLayout.LayoutParams(0, ViewGroup.LayoutParams.MATCH_PARENT);
        parametrosPizarra.weight = 5.0f;
        pizarra.setLayoutParams(parametrosPizarra);

        // Panel derecho con controles
        ScrollView scrollDer = new ScrollView(this);
        LinearLayout panelDerecho = crearPanelDerecho();
        scrollDer.addView(panelDerecho);
        LinearLayout.LayoutParams parametrosPanelDer = new LinearLayout.LayoutParams(0, ViewGroup.LayoutParams.MATCH_PARENT);
        parametrosPanelDer.weight = 2.5f;
        scrollDer.setLayoutParams(parametrosPanelDer);

        linearLayoutPrincipal.addView(scrollIzq);
        linearLayoutPrincipal.addView(pizarra);
        linearLayoutPrincipal.addView(scrollDer);

        return linearLayoutPrincipal;
    }

    private LinearLayout crearPanelIzquierdo() {
        LinearLayout panel = new LinearLayout(this);
        panel.setOrientation(LinearLayout.VERTICAL);
        panel.setGravity(Gravity.CENTER_HORIZONTAL);
        panel.setBackgroundColor(Color.rgb(240, 240, 240));
        panel.setPadding(8, 8, 8, 8); // Reducido de 10 a 8

        TextView titulo = new TextView(this);
        titulo.setText("VALORES EN TIEMPO REAL");
        titulo.setTextSize(tamanoLetraResolucionIncluida * 0.85f); // Reducido de 0.9f
        titulo.setTypeface(null, Typeface.BOLD);
        titulo.setTextColor(Color.rgb(33, 33, 33));
        titulo.setGravity(Gravity.CENTER);
        panel.addView(titulo);

        agregarEspacio(panel, 10); // Reducido de 15

        agregarTexto(panel, "Parámetros:", true);
        tv_m1_info.setText("m₁ = " + AlmacenDatosRAM.m1 + " kg");
        tv_m2_info.setText("m₂ = " + AlmacenDatosRAM.m2 + " kg");
        tv_m3_info.setText("m₃ = " + AlmacenDatosRAM.m3 + " kg");
        configurarTextoInfo(tv_m1_info);
        configurarTextoInfo(tv_m2_info);
        configurarTextoInfo(tv_m3_info);
        panel.addView(tv_m1_info);
        panel.addView(tv_m2_info);
        panel.addView(tv_m3_info);

        agregarEspacio(panel, 10); // Reducido de 15

        agregarTexto(panel, "Tiempo:", true);
        configurarTextoInfo(tv_tiempo);
        panel.addView(tv_tiempo);

        agregarEspacio(panel, 8); // Reducido de 10

        agregarTexto(panel, "Posiciones (m):", true);
        configurarTextoInfo(tv_y1);
        configurarTextoInfo(tv_y2);
        configurarTextoInfo(tv_y3);
        configurarTextoInfo(tv_yP);
        panel.addView(tv_y1);
        panel.addView(tv_y2);
        panel.addView(tv_y3);
        panel.addView(tv_yP);

        agregarEspacio(panel, 8); // Reducido de 10

        agregarTexto(panel, "Velocidades (m/s):", true);
        configurarTextoInfo(tv_v1);
        configurarTextoInfo(tv_v2);
        configurarTextoInfo(tv_v3);
        configurarTextoInfo(tv_vP);
        panel.addView(tv_v1);
        panel.addView(tv_v2);
        panel.addView(tv_v3);
        panel.addView(tv_vP);

        agregarEspacio(panel, 8); // Reducido de 10

        agregarTexto(panel, "Aceleraciones (m/s²):", true);
        configurarTextoInfo(tv_a1);
        configurarTextoInfo(tv_a2);
        configurarTextoInfo(tv_a3);
        configurarTextoInfo(tv_aP);
        panel.addView(tv_a1);
        panel.addView(tv_a2);
        panel.addView(tv_a3);
        panel.addView(tv_aP);

        agregarEspacio(panel, 8); // Reducido de 10

        agregarTexto(panel, "Tensiones (N):", true);
        configurarTextoInfo(tv_T);
        configurarTextoInfo(tv_T2);
        configurarTextoInfo(tv_T3);
        panel.addView(tv_T);
        panel.addView(tv_T2);
        panel.addView(tv_T3);

        return panel;
    }

    private LinearLayout crearPanelDerecho() {
        LinearLayout panel = new LinearLayout(this);
        panel.setOrientation(LinearLayout.VERTICAL);
        panel.setBackgroundColor(Color.rgb(250, 250, 250));
        panel.setPadding(10, 10, 10, 10); // Reducido de 15

        TextView titulo = new TextView(this);
        titulo.setText("PARÁMETROS");
        titulo.setTextSize(tamanoLetraResolucionIncluida * 0.9f); // Reducido de 1.0f
        titulo.setTypeface(null, Typeface.BOLD);
        titulo.setTextColor(Color.rgb(33, 33, 33));
        titulo.setGravity(Gravity.CENTER);
        panel.addView(titulo);

        agregarEspacio(panel, 10); // Reducido de 15

        agregarTexto(panel, "Masas (kg):", true);
        agregarEspacio(panel, 5);

        // Slider para m1
        agregarTexto(panel, "m₁:", false);
        panel.addView(tvValorM1);
        agregarEspacio(panel, 2); // Espacio mínimo
        panel.addView(seekBarM1);
        agregarEspacio(panel, 8); // Reducido de 12

        // Slider para m2
        agregarTexto(panel, "m₂:", false);
        panel.addView(tvValorM2);
        agregarEspacio(panel, 2); // Espacio mínimo
        panel.addView(seekBarM2);
        agregarEspacio(panel, 8); // Reducido de 12

        // Slider para m3
        agregarTexto(panel, "m₃:", false);
        panel.addView(tvValorM3);
        agregarEspacio(panel, 2); // Espacio mínimo
        panel.addView(seekBarM3);

        agregarEspacio(panel, 12); // Reducido de 20

        agregarTexto(panel, "Control:", true);
        agregarEspacio(panel, 6); // Reducido de 10

        panel.addView(btnIniciar);
        agregarEspacio(panel, 6); // Reducido de 10

        panel.addView(btnPausar);
        agregarEspacio(panel, 6); // Reducido de 10

        panel.addView(btnReiniciar);

        // Eventos de SeekBars
        seekBarM1.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                float masa = progressAMasa(progress);
                actualizarTextoValor(tvValorM1, masa);
                if (!simulacionIniciada) {
                    AlmacenDatosRAM.m1 = masa;
                    tv_m1_info.setText("m₁ = " + String.format("%.1f", masa) + " kg");
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {}

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {}
        });

        seekBarM2.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                float masa = progressAMasa(progress);
                actualizarTextoValor(tvValorM2, masa);
                if (!simulacionIniciada) {
                    AlmacenDatosRAM.m2 = masa;
                    tv_m2_info.setText("m₂ = " + String.format("%.1f", masa) + " kg");
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {}

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {}
        });

        seekBarM3.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                float masa = progressAMasa(progress);
                actualizarTextoValor(tvValorM3, masa);
                if (!simulacionIniciada) {
                    AlmacenDatosRAM.m3 = masa;
                    tv_m3_info.setText("m₃ = " + String.format("%.1f", masa) + " kg");
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {}

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {}
        });

        // Eventos de botones
        btnIniciar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                iniciarSimulacion();
            }
        });

        btnPausar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pausarSimulacion();
            }
        });

        btnReiniciar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                reiniciarSimulacion();
            }
        });

        return panel;
    }

    private void agregarTexto(LinearLayout layout, String texto, boolean negrita) {
        TextView tv = new TextView(this);
        tv.setText(texto);
        tv.setTextSize(tamanoLetraResolucionIncluida * 0.7f); // Reducido de 0.8f
        tv.setTextColor(Color.rgb(66, 66, 66));
        if (negrita) {
            tv.setTypeface(null, Typeface.BOLD);
        }
        tv.setPadding(0, 0, 0, 0); // Sin padding extra
        layout.addView(tv);
    }

    private void configurarTextoInfo(TextView tv) {
        tv.setTextSize(tamanoLetraResolucionIncluida * 0.65f); // Reducido de 0.7f
        tv.setTextColor(Color.rgb(0, 100, 200));
        tv.setPadding(3, 1, 3, 1); // Reducido de (5, 2, 5, 2)
    }

    private void agregarEspacio(LinearLayout layout, int altura) {
        View espacio = new View(this);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT, altura);
        espacio.setLayoutParams(params);
        layout.addView(espacio);
    }

    private void crearObjetosLaboratorio() {
        CR.anchoPizarra = AlmacenDatosRAM.ancho_pantalla * 0.5f;
        CR.altoPizarra = AlmacenDatosRAM.alto_pantalla;

        float ancho_rectangulo = CR.pcApxL(35);
        float alto_rectangulo = CR.pcApxL(45);

        float x_rectangulo = CR.pcApxX(50);
        float y_rectangulo = CR.pcApxY(50);

        AlmacenDatosRAM.origenY_en_pixeles = y_rectangulo - 0.5f * alto_rectangulo;
        AlmacenDatosRAM.origenY_en_metros = 0.0f;

        radio = CR.pcApxL(4);
        radio_verde = radio / 2f;
        AlmacenDatosRAM.radio = radio;
        AlmacenDatosRAM.radio_verde = radio_verde;

        float esquina_sup_izq_x = x_rectangulo - 0.5f * ancho_rectangulo;
        float esquina_sup_izq_y = y_rectangulo - 0.5f * alto_rectangulo;
        float esquina_sup_der_x = x_rectangulo + 0.5f * ancho_rectangulo;
        float esquina_sup_der_y = y_rectangulo - 0.5f * alto_rectangulo;

        xp_azul_izq = esquina_sup_izq_x - radio;
        yp_azul_izq = esquina_sup_izq_y - radio;
        xp_azul_der = esquina_sup_der_x + radio;
        yp_azul_der = esquina_sup_der_y - radio;

        float xp_verde = xp_azul_der + radio;
        float yp_verde = y_rectangulo + CR.pcApxL(5);

        ancho_masa = radio / 2f;
        alto_masa = radio;

        float x_m1 = xp_azul_izq - radio;
        float y_m1 = yp_azul_izq + 5 * radio;
        float x_m2 = xp_verde - radio_verde;
        float y_m2 = yp_verde + 2 * radio;
        float x_m3 = xp_verde + radio_verde;
        float y_m3 = yp_verde + 4 * radio;

        AlmacenDatosRAM.yi1_en_pixeles = y_m1;
        AlmacenDatosRAM.yi2_en_pixeles = y_m2;
        AlmacenDatosRAM.yi3_en_pixeles = y_m3;
        AlmacenDatosRAM.yiP_en_pixeles = yp_verde;

        AlmacenDatosRAM.x1_en_pixeles = x_m1;
        AlmacenDatosRAM.x2_en_pixeles = x_m2;
        AlmacenDatosRAM.x3_en_pixeles = x_m3;
        AlmacenDatosRAM.xP_en_pixeles = xp_verde;

        float factorConversion_pixelAmetro = 2 / CR.pcApxY(100f);
        AlmacenDatosRAM.yi1_en_metros = (y_m1 - AlmacenDatosRAM.origenY_en_pixeles) * factorConversion_pixelAmetro;
        AlmacenDatosRAM.yi2_en_metros = (y_m2 - AlmacenDatosRAM.origenY_en_pixeles) * factorConversion_pixelAmetro;
        AlmacenDatosRAM.yi3_en_metros = (y_m3 - AlmacenDatosRAM.origenY_en_pixeles) * factorConversion_pixelAmetro;
        AlmacenDatosRAM.yiP_en_metros = (yp_verde - AlmacenDatosRAM.origenY_en_pixeles) * factorConversion_pixelAmetro;

        float base_y = y_rectangulo + 0.5f * alto_rectangulo + CR.pcApxL(3);
        base_oscura = new CuerpoRectangular(CR.pcApxX(50), base_y,
                CR.pcApxL(40), CR.pcApxL(6));
        base_oscura.setColor(Color.rgb(10, 50, 10));
        objetos[0] = base_oscura;

        cuerpo_amarillo = new CuerpoRectangular(x_rectangulo, y_rectangulo,
                ancho_rectangulo, alto_rectangulo);
        cuerpo_amarillo.setColor(Color.YELLOW);
        cuerpo_amarillo.setGrosorLinea(CR.pcApxL(0.5f));
        objetos[1] = cuerpo_amarillo;

        polea_azul_izq = new Polea(xp_azul_izq, yp_azul_izq, radio);
        polea_azul_izq.setColor(Color.BLUE);
        polea_azul_izq.setGrosorLinea(CR.pcApxL(0.5f));
        polea_azul_izq.setSoportePolea(true);
        polea_azul_izq.rotarEje(-45);
        polea_azul_izq.rotar(xp_azul_izq, yp_azul_izq, 0);
        objetos[2] = polea_azul_izq;

        polea_azul_der = new Polea(xp_azul_der, yp_azul_der, radio);
        polea_azul_der.setColor(Color.BLUE);
        polea_azul_der.setGrosorLinea(CR.pcApxL(0.5f));
        polea_azul_der.setSoportePolea(true);
        polea_azul_der.rotarEje(45);
        polea_azul_der.rotar(xp_azul_der, yp_azul_der, 45);
        objetos[3] = polea_azul_der;

        polea_verde_P = new Polea(xp_verde, yp_verde, radio_verde);
        polea_verde_P.setColor(Color.rgb(0, 200, 0));
        polea_verde_P.setGrosorLinea(CR.pcApxL(0.5f));
        objetos[4] = polea_verde_P;

        float cuerda_sup_y = yp_azul_izq - radio;
        cuerda_horizontal = new Cuerda(xp_azul_izq, cuerda_sup_y,
                xp_azul_der, cuerda_sup_y);
        cuerda_horizontal.setColor(Color.RED);
        cuerda_horizontal.setGrosorLinea(CR.pcApxL(0.5f));
        objetos[5] = cuerda_horizontal;

        cuerda_m1 = new Cuerda(xp_azul_izq - radio, yp_azul_izq,
                x_m1, y_m1 - 0.5f * alto_masa);
        cuerda_m1.setColor(Color.RED);
        cuerda_m1.setGrosorLinea(CR.pcApxL(0.5f));
        objetos[6] = cuerda_m1;

        cuerda_vertical_a_P = new Cuerda(xp_azul_der + radio, yp_azul_der,
                xp_verde, yp_verde - radio_verde);
        cuerda_vertical_a_P.setColor(Color.RED);
        cuerda_vertical_a_P.setGrosorLinea(CR.pcApxL(0.5f));
        objetos[7] = cuerda_vertical_a_P;

        cuerda_m2 = new Cuerda(xp_verde - radio_verde, yp_verde,
                x_m2, y_m2 - 0.5f * alto_masa);
        cuerda_m2.setColor(Color.RED);
        cuerda_m2.setGrosorLinea(CR.pcApxL(0.5f));
        objetos[8] = cuerda_m2;

        cuerda_m3 = new Cuerda(xp_verde + radio_verde, yp_verde,
                x_m3, y_m3 - 0.5f * alto_masa);
        cuerda_m3.setColor(Color.RED);
        cuerda_m3.setGrosorLinea(CR.pcApxL(0.5f));
        objetos[9] = cuerda_m3;

        masa_1 = new Masa(x_m1, y_m1, ancho_masa, alto_masa);
        masa_1.setColor(Color.rgb(0, 180, 0));
        objetos[10] = masa_1;

        masa_2 = new Masa(x_m2, y_m2, ancho_masa, alto_masa);
        masa_2.setColor(Color.rgb(0, 180, 0));
        objetos[11] = masa_2;

        masa_3 = new Masa(x_m3, y_m3, ancho_masa, alto_masa);
        masa_3.setColor(Color.rgb(0, 180, 0));
        objetos[12] = masa_3;

        marca_P = new Marca("P", xp_verde + 2.5f * radio_verde, yp_verde);
        marca_P.setColor(Color.BLACK);
        marca_P.setTamano(CR.pcApxL(3f));
        objetos[13] = marca_P;

        marca_m1 = new Marca("m₁", x_m1 - 1.5f * radio, y_m1);
        marca_m1.setColor(Color.BLACK);
        marca_m1.setTamano(CR.pcApxL(2.5f));
        objetos[14] = marca_m1;

        marca_m2 = new Marca("m₂", x_m2 - 1.25f * radio, y_m2);
        marca_m2.setColor(Color.BLACK);
        marca_m2.setTamano(CR.pcApxL(2.5f));
        objetos[15] = marca_m2;

        marca_m3 = new Marca("m₃", x_m3 + 0.5f * radio, y_m3);
        marca_m3.setColor(Color.BLACK);
        marca_m3.setTamano(CR.pcApxL(2.5f));
        objetos[16] = marca_m3;

        pizarra.setEstadoEscena(objetos);
    }

    private void iniciarSimulacion() {
        if (!simulacionIniciada) {
            // Obtener masas de los sliders
            float m1 = progressAMasa(seekBarM1.getProgress());
            float m2 = progressAMasa(seekBarM2.getProgress());
            float m3 = progressAMasa(seekBarM3.getProgress());

            AlmacenDatosRAM.m1 = m1;
            AlmacenDatosRAM.m2 = m2;
            AlmacenDatosRAM.m3 = m3;

            // Actualizar información
            tv_m1_info.setText("m₁ = " + String.format("%.1f", m1) + " kg");
            tv_m2_info.setText("m₂ = " + String.format("%.1f", m2) + " kg");
            tv_m3_info.setText("m₃ = " + String.format("%.1f", m3) + " kg");

            // Deshabilitar sliders
            seekBarM1.setEnabled(false);
            seekBarM2.setEnabled(false);
            seekBarM3.setEnabled(false);

            // Actualizar botones
            btnIniciar.setEnabled(false);
            btnPausar.setEnabled(true);

            // Iniciar hilo
            simulacionIniciada = true;
            hiloAnimacion.iniciar();
        }
    }

    private void pausarSimulacion() {
        // No implementado en esta versión simple
        // Se puede agregar una bandera de pausa si se requiere
    }

    private void reiniciarSimulacion() {
        // Detener hilo si está corriendo
        if (simulacionIniciada) {
            hiloAnimacion.detener();
            simulacionIniciada = false;
        }

        // Reiniciar tiempo
        AlmacenDatosRAM.tiempo = 0.0f;

        // Habilitar sliders
        seekBarM1.setEnabled(true);
        seekBarM2.setEnabled(true);
        seekBarM3.setEnabled(true);

        // Actualizar botones
        btnIniciar.setEnabled(true);
        btnPausar.setEnabled(false);

        // Recrear objetos
        crearObjetosLaboratorio();

        // Actualizar valores
        actualizarPanelValores();

        // Recrear hilo
        hiloAnimacion = new HiloAnimacion(this);
    }

    public void actualizarSimulacion() {
        modeloFisico.setCalculos(AlmacenDatosRAM.tiempo,
                AlmacenDatosRAM.m1,
                AlmacenDatosRAM.m2,
                AlmacenDatosRAM.m3);

        // Si se alcanzó algún límite, reiniciar automáticamente (repetir animación)
        if (verificarLimitesAlcanzados()) {
            reiniciarAnimacionAutomatico();
            return;
        }

        actualizarPosicionesObjetos();
        actualizarPanelValores();
        pizarra.actualizar();
    }

    /**
     * Reinicia la animación automáticamente sin detener el hilo
     * Solo reinicia el tiempo a cero y vuelve a las posiciones iniciales
     */
    private void reiniciarAnimacionAutomatico() {
        // Solo reiniciar el tiempo (el hilo sigue corriendo)
        AlmacenDatosRAM.tiempo = 0.0f;
    }

    /**
     * Verifica si se han alcanzado los límites para reiniciar la animación
     * A. Cualquier bloque toca el piso
     * B. m1 alcanza la posición Y de las poleas azules - mitad del alto del bloque
     * C. Polea P alcanza la altura de las poleas azules - radio azul - radio verde
     * D. m2 o m3 alcanzan la posición Y de la polea P
     */
    private boolean verificarLimitesAlcanzados() {
        float y1_actual = AlmacenDatosRAM.y1_en_pixeles;
        float y2_actual = AlmacenDatosRAM.y2_en_pixeles;
        float y3_actual = AlmacenDatosRAM.y3_en_pixeles;
        float yP_actual = AlmacenDatosRAM.yP_en_pixeles;

        // A. Verificar si algún bloque toca el piso
        float limite_piso = CR.pcApxY(100f);
        if ((y1_actual + 0.5f * alto_masa) >= limite_piso ||
                (y2_actual + 0.5f * alto_masa) >= limite_piso ||
                (y3_actual + 0.5f * alto_masa) >= limite_piso) {
            return true;
        }

        // B. Verificar si m1 alcanza el límite superior
        float limite_m1 = yp_azul_izq - 0.5f * alto_masa;
        if (y1_actual <= limite_m1) {
            return true;
        }

        // C. Verificar si la polea P alcanza su límite superior
        float limite_P = yp_azul_der - radio - radio_verde;
        if (yP_actual <= limite_P) {
            return true;
        }

        // D. Verificar si m2 o m3 alcanzan la posición de la polea P
        if ((y2_actual - 0.5f * alto_masa) <= yP_actual ||
                (y3_actual - 0.5f * alto_masa) <= yP_actual) {
            return true;
        }

        return false;
    }

    private void actualizarPosicionesObjetos() {
        float y1_px = AlmacenDatosRAM.y1_en_pixeles;
        float y2_px = AlmacenDatosRAM.y2_en_pixeles;
        float y3_px = AlmacenDatosRAM.y3_en_pixeles;
        float yP_px = AlmacenDatosRAM.yP_en_pixeles;

        float x_m1 = AlmacenDatosRAM.x1_en_pixeles;
        float x_m2 = AlmacenDatosRAM.x2_en_pixeles;
        float x_m3 = AlmacenDatosRAM.x3_en_pixeles;
        float xp_verde = AlmacenDatosRAM.xP_en_pixeles;

        masa_1 = new Masa(x_m1, y1_px, ancho_masa, alto_masa);
        masa_1.setColor(Color.rgb(0, 180, 0));
        objetos[10] = masa_1;

        marca_m1 = new Marca("m₁", x_m1 - 1.5f * radio, y1_px);
        marca_m1.setColor(Color.BLACK);
        marca_m1.setTamano(CR.pcApxL(2.5f));
        objetos[14] = marca_m1;

        cuerda_m1 = new Cuerda(xp_azul_izq - radio, yp_azul_izq,
                x_m1, y1_px - 0.5f * alto_masa);
        cuerda_m1.setColor(Color.RED);
        cuerda_m1.setGrosorLinea(CR.pcApxL(0.5f));
        objetos[6] = cuerda_m1;

        polea_verde_P = new Polea(xp_verde, yP_px, radio_verde);
        polea_verde_P.setColor(Color.rgb(0, 200, 0));
        polea_verde_P.setGrosorLinea(CR.pcApxL(0.5f));
        polea_verde_P.rotar(xp_verde, yP_px, AlmacenDatosRAM.teta_P);
        objetos[4] = polea_verde_P;

        marca_P = new Marca("P", xp_verde + 2.5f * radio_verde, yP_px);
        marca_P.setColor(Color.BLACK);
        marca_P.setTamano(CR.pcApxL(3f));
        objetos[13] = marca_P;

        cuerda_vertical_a_P = new Cuerda(xp_azul_der + radio, yp_azul_der,
                xp_verde, yP_px - radio_verde);
        cuerda_vertical_a_P.setColor(Color.RED);
        cuerda_vertical_a_P.setGrosorLinea(CR.pcApxL(0.5f));
        objetos[7] = cuerda_vertical_a_P;

        masa_2 = new Masa(x_m2, y2_px, ancho_masa, alto_masa);
        masa_2.setColor(Color.rgb(0, 180, 0));
        objetos[11] = masa_2;

        marca_m2 = new Marca("m₂", x_m2 - 1.25f * radio, y2_px);
        marca_m2.setColor(Color.BLACK);
        marca_m2.setTamano(CR.pcApxL(2.5f));
        objetos[15] = marca_m2;

        cuerda_m2 = new Cuerda(xp_verde - radio_verde, yP_px,
                x_m2, y2_px - 0.5f * alto_masa);
        cuerda_m2.setColor(Color.RED);
        cuerda_m2.setGrosorLinea(CR.pcApxL(0.5f));
        objetos[8] = cuerda_m2;

        masa_3 = new Masa(x_m3, y3_px, ancho_masa, alto_masa);
        masa_3.setColor(Color.rgb(0, 180, 0));
        objetos[12] = masa_3;

        marca_m3 = new Marca("m₃", x_m3 + 0.5f * radio, y3_px);
        marca_m3.setColor(Color.BLACK);
        marca_m3.setTamano(CR.pcApxL(2.5f));
        objetos[16] = marca_m3;

        cuerda_m3 = new Cuerda(xp_verde + radio_verde, yP_px,
                x_m3, y3_px - 0.5f * alto_masa);
        cuerda_m3.setColor(Color.RED);
        cuerda_m3.setGrosorLinea(CR.pcApxL(0.5f));
        objetos[9] = cuerda_m3;

        polea_azul_izq.rotar(xp_azul_izq, yp_azul_izq, AlmacenDatosRAM.teta_azul_izq);
        polea_azul_der.rotar(xp_azul_der, yp_azul_der, AlmacenDatosRAM.teta_azul_der);

        pizarra.setEstadoEscena(objetos);
    }

    private void actualizarPanelValores() {
        tv_tiempo.setText("t = " + String.format("%.2f", AlmacenDatosRAM.tiempo) + " s");

        tv_y1.setText("y₁ = " + String.format("%.3f", AlmacenDatosRAM.y1_en_metros) + " m");
        tv_y2.setText("y₂ = " + String.format("%.3f", AlmacenDatosRAM.y2_en_metros) + " m");
        tv_y3.setText("y₃ = " + String.format("%.3f", AlmacenDatosRAM.y3_en_metros) + " m");
        tv_yP.setText("yₚ = " + String.format("%.3f", AlmacenDatosRAM.yP_en_metros) + " m");

        tv_v1.setText("v₁ = " + String.format("%.3f", AlmacenDatosRAM.v1) + " m/s");
        tv_v2.setText("v₂ = " + String.format("%.3f", AlmacenDatosRAM.v2) + " m/s");
        tv_v3.setText("v₃ = " + String.format("%.3f", AlmacenDatosRAM.v3) + " m/s");
        tv_vP.setText("vₚ = " + String.format("%.3f", AlmacenDatosRAM.vP) + " m/s");

        tv_a1.setText("a₁ = " + String.format("%.3f", AlmacenDatosRAM.a1) + " m/s²");
        tv_a2.setText("a₂ = " + String.format("%.3f", AlmacenDatosRAM.a2) + " m/s²");
        tv_a3.setText("a₃ = " + String.format("%.3f", AlmacenDatosRAM.a3) + " m/s²");
        tv_aP.setText("aₚ = " + String.format("%.3f", AlmacenDatosRAM.aP) + " m/s²");

        tv_T.setText("T = " + String.format("%.2f", AlmacenDatosRAM.T) + " N");
        tv_T2.setText("T₂ = " + String.format("%.2f", AlmacenDatosRAM.T2) + " N");
        tv_T3.setText("T₃ = " + String.format("%.2f", AlmacenDatosRAM.T3) + " N");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (hiloAnimacion != null) {
            hiloAnimacion.detener();
        }
    }
}