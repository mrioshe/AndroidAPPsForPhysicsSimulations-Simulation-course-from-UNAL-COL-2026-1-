package com.curso_simulaciones.midecimaterceraapp;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.curso_simulaciones.midecimaterceraapp.vista.CR;
import com.curso_simulaciones.midecimaterceraapp.objetos_laboratorio.CuerpoRectangular;
import com.curso_simulaciones.midecimaterceraapp.objetos_laboratorio.Flecha;
import com.curso_simulaciones.midecimaterceraapp.objetos_laboratorio.ObjetoLaboratorio;
import com.curso_simulaciones.midecimaterceraapp.vista.Pizarra;
import com.curso_simulaciones.midecimaterceraapp.objetos_laboratorio.Polea;
import com.curso_simulaciones.midecimaterceraapp.objetos_laboratorio.Resorte;
import com.curso_simulaciones.midecimaterceraapp.objetos_laboratorio.Rueda;

/**
 * Actividad principal para MiDecimaTerceraApp
 */
public class ActividadPrincipalMiDecimaTerceraApp extends Activity implements Runnable {

    // Pizarra para dibujar
    private Pizarra pizarra;
    // objetos dibujables
    private Polea polea_1, polea_2, polea_3;
    private Rueda rueda_1;
    private CuerpoRectangular rectangular_1, rectangular_2;
    private Flecha flecha_1, flecha_2;
    private Resorte resorte_1;
    public ObjetoLaboratorio[] objetosLab = new ObjetoLaboratorio[10];

    // periodo de muestreo (ms)
    private long periodo_muestreo = 50;
    private float tiempo;
    private Thread hilo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        crearElementosGui();

        ViewGroup.LayoutParams parametro_layout_principal =
                new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);

        this.setContentView(crearGui(), parametro_layout_principal);

        hilo = new Thread(this);
        hilo.start();
    }

    private void crearElementosGui() {
        pizarra = new Pizarra(this);
        pizarra.setBackgroundColor(Color.WHITE);
    }

    private LinearLayout crearGui() {
        LinearLayout linearPrincipal = new LinearLayout(this);
        linearPrincipal.setOrientation(LinearLayout.VERTICAL);
        linearPrincipal.setBackgroundColor(Color.BLACK);
        linearPrincipal.setWeightSum(10.0f);

        LinearLayout linearArriba = new LinearLayout(this);
        LinearLayout linearAbajo = new LinearLayout(this);
        linearAbajo.setBackgroundColor(Color.YELLOW);

        LinearLayout.LayoutParams parametrosPegadoArriba =
                new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 0);
        parametrosPegadoArriba.weight = 8.5f;
        parametrosPegadoArriba.setMargins(50, 50, 50, 50);
        linearPrincipal.addView(linearArriba, parametrosPegadoArriba);

        LinearLayout.LayoutParams parametrosPegadoAbajo =
                new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 0);
        parametrosPegadoAbajo.weight = 1.5f;
        linearPrincipal.addView(linearAbajo, parametrosPegadoAbajo);

        linearArriba.addView(pizarra);

        return linearPrincipal;
    }

    @Override
    public void run() {
        boolean ON = true;
        while (true) {
            try {
                Thread.sleep(periodo_muestreo);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            if (pizarra.getWidth() != 0 && ON) {
                crearObjetosConResponsividad();
                ON = false;
            }

            if (!ON) {
                tiempo += 0.05f;
                cambiarEstadosEscenaPizarra(tiempo);
            }
        }
    }

    private void crearObjetosConResponsividad() {
        CR.anchoPizarra = pizarra.getWidth();
        CR.altoPizarra = pizarra.getHeight();
        crearObjetosLaboratorio();
    }

    private void crearObjetosLaboratorio() {
        // polea 1
        float x1 = CR.pcApxX(10);
        float y1 = CR.pcApxY(25);
        float r1 = CR.pcApxL(10);
        polea_1 = new Polea(x1, y1, r1);
        objetosLab[0] = polea_1;

        // polea 2
        float x2 = CR.pcApxX(15);
        float y2 = CR.pcApxY(50);
        float r2 = CR.pcApxL(15);
        polea_2 = new Polea(x2, y2, r2);
        polea_2.setColor(Color.BLACK);
        objetosLab[1] = polea_2;

        // polea 3
        float x3 = CR.pcApxX(10);
        float y3 = CR.pcApxY(75);
        float r3 = CR.pcApxL(10);
        polea_3 = new Polea(x3, y3, r3);
        polea_3.setColor(Color.MAGENTA);
        objetosLab[2] = polea_3;

        // rueda
        float x4 = CR.pcApxX(50);
        float y4 = CR.pcApxY(50);
        float r4 = CR.pcApxL(15);
        rueda_1 = new Rueda(x4, y4, r4);
        rueda_1.setColor(Color.rgb(0, 128, 0));
        objetosLab[3] = rueda_1;

        // rectángulo 1
        float x5 = CR.pcApxX(50);
        float y5 = CR.pcApxY(50);
        float ancho1 = CR.pcApxL(40);
        float alto1 = CR.pcApxL(20);
        rectangular_1 = new CuerpoRectangular(x5, y5, ancho1, alto1);
        rectangular_1.setColor(Color.argb(100, 250, 250, 100));
        objetosLab[4] = rectangular_1;

        // rectángulo 2
        float x6 = CR.pcApxX(75);
        float y6 = CR.pcApxY(40);
        float ancho2 = CR.pcApxL(5);
        float alto2 = CR.pcApxL(60);
        rectangular_2 = new CuerpoRectangular(x6, y6, ancho2, alto2);
        objetosLab[5] = rectangular_2;

        // flechas y resortes de ejemplo
        flecha_1 = new Flecha(CR.pcApxX(5), CR.pcApxY(90), CR.pcApxX(20), CR.pcApxY(90));
        flecha_2 = new Flecha(CR.pcApxX(20), CR.pcApxY(10), CR.pcApxX(40), CR.pcApxY(10));
        objetosLab[6] = flecha_1;
        objetosLab[7] = flecha_2;

        resorte_1 = new Resorte(CR.pcApxX(60), CR.pcApxY(80), CR.pcApxX(80), CR.pcApxY(80));
        objetosLab[8] = resorte_1;

        // asignar al pizarra
        pizarra.setEstadoEscena(objetosLab);
    }

    private void cambiarEstadosEscenaPizarra(float tiempo) {
        // polea 1: movimiento rectilíneo uniforme en X (en porcentaje)
        float desplazamiento_x_1 = CR.pcApxX(2 * tiempo);
        polea_1.mover(desplazamiento_x_1, 0);

        // polea 2: rotación
        float teta_2 = 100 * tiempo;
        polea_2.mover(teta_2);

        // polea 3: MU + rotación
        float desplazamiento_x_3 = CR.pcApxX(2 * tiempo);
        float teta_3 = 100 * tiempo;
        polea_3.mover(desplazamiento_x_3, 0, teta_3);

        // rueda: rotación
        float teta_4 = 100 * tiempo;
        rueda_1.mover(teta_4);

        // rectangular 1: rotación pequeña
        float teta_5 = 50 * tiempo;
        rectangular_1.mover(-teta_5);

        // rectangular 2: oscilación armónica (rotación alrededor de punto fijo)
        float x_6 = CR.pcApxX(75);
        float y_6 = CR.pcApxY(20);
        float fase_en_grados = 100 * tiempo;
        float fase_en_radianes = (float) Math.toRadians(fase_en_grados);
        float amplitud_en_grados = 10;
        float amplitud_en_radianes = (float) Math.toRadians(amplitud_en_grados);
        float teta_6_en_radianes = (float) (amplitud_en_radianes * Math.sin(fase_en_radianes));
        float teta_6_en_grados = (float) Math.toDegrees(teta_6_en_radianes);
        rectangular_2.rotar(x_6, y_6, teta_6_en_grados);
    }
}
