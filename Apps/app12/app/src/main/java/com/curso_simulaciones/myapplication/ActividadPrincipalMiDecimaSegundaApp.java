package com.curso_simulaciones.myapplication;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.curso_simulaciones.myapplication.objetos_laboratorio.CuerpoRectangular;
import com.curso_simulaciones.myapplication.objetos_laboratorio.Polea;
import com.curso_simulaciones.myapplication.objetos_laboratorio.Rueda;
import com.curso_simulaciones.myapplication.vista.CR;
import com.curso_simulaciones.myapplication.vista.Pizarra;

/**
 * Actividad principal para MiDecimaSegundaApp.
 * Crea la pizarra y controla el hilo de animación.
 */
public class ActividadPrincipalMiDecimaSegundaApp extends Activity implements Runnable {

    private Pizarra pizarra;
    private Polea polea_1, polea_2, polea_3;
    private Polea[] poleas = new Polea[10];
    private Rueda rueda_1;
    private Rueda[] ruedas = new Rueda[10];
    private CuerpoRectangular rectangular_1, rectangular_2;
    private CuerpoRectangular[] cuerposRectangulares = new CuerpoRectangular[10];

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

        linearArriba.addView(pizarra, new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT, 1.0f));

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
                tiempo = tiempo + 0.05f;
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
        float x_1 = CR.pcApxX(10);
        float y_1 = CR.pcApxY(25);
        float radio_1 = CR.pcApxL(10);
        polea_1 = new Polea(x_1, y_1, radio_1);
        poleas[0] = polea_1;

        float x_2 = CR.pcApxX(15);
        float y_2 = CR.pcApxY(50);
        float radio_2 = CR.pcApxL(15);
        polea_2 = new Polea(x_2, y_2, radio_2);
        polea_2.setColor(Color.BLACK);
        poleas[1] = polea_2;

        float x_3 = CR.pcApxX(10);
        float y_3 = CR.pcApxY(75);
        float radio_3 = CR.pcApxL(10);
        polea_3 = new Polea(x_3, y_3, radio_3);
        polea_3.setColor(Color.MAGENTA);
        poleas[2] = polea_3;

        float x_4 = CR.pcApxX(50);
        float y_4 = CR.pcApxY(50);
        float radio_4 = CR.pcApxL(15);
        rueda_1 = new Rueda(x_4, y_4, radio_4);
        rueda_1.setColor(Color.rgb(0, 128, 0));
        ruedas[0] = rueda_1;

        float x_5 = CR.pcApxX(50);
        float y_5 = CR.pcApxY(50);
        float ancho_1 = CR.pcApxL(40);
        float alto_1 = CR.pcApxL(20);
        rectangular_1 = new CuerpoRectangular(x_5, y_5, ancho_1, alto_1);
        rectangular_1.setColor(Color.argb(100, 250, 250, 100));
        cuerposRectangulares[0] = rectangular_1;

        float x_6 = CR.pcApxX(75);
        float y_6 = CR.pcApxY(40);
        float ancho_2 = CR.pcApxL(5);
        float alto_2 = CR.pcApxL(60);
        rectangular_2 = new CuerpoRectangular(x_6, y_6, ancho_2, alto_2);
        cuerposRectangulares[1] = rectangular_2;

        pizarra.setEstadoEscena(poleas, ruedas, cuerposRectangulares);
    }

    private void cambiarEstadosEscenaPizarra(float tiempo) {
        float desplazamiento_x_1 = CR.pcApxX(2 * tiempo);
        polea_1.mover(desplazamiento_x_1, 0);

        float teta_2 = 100 * tiempo;
        polea_2.mover(teta_2);

        float desplazamiento_x_3 = CR.pcApxX(2 * tiempo);
        float teta_3 = 100 * tiempo;
        polea_3.mover(desplazamiento_x_3, 0, teta_3);

        float teta_4 = 100 * tiempo;
        rueda_1.mover(teta_4);

        float teta_5 = 50 * tiempo;
        rectangular_1.mover(-teta_5);

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