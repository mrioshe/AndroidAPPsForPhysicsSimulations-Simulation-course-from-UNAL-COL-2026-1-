package com.curso_simulaciones.mivigesimacuartaapp.vista;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.MotionEvent;
import android.view.View;

import com.curso_simulaciones.mivigesimacuartaapp.datos.AlmacenDatosRAM;

// import para objetos de la librería simulphysics (según uso en el documento)
import com.curso_simulaciones.simulphysics.objetos_laboratorio.ObjetoLaboratorio;

public class Pizarra extends View {

    private ObjetoLaboratorio objetosLab[];

    public boolean evento_touch=true;

    private float evento_x_touch_en_pixeles= AlmacenDatosRAM.x_en_pixeles;
    private float evento_y_touch_en_pixeles=AlmacenDatosRAM.y_en_pixeles;
    private float factorConversion_metroApixel,factorConversion_pixelAmetro;


    private float origen_x, origen_y;
    private float m_x = 1;
    private float m_y = 1;


    /**
     * Constructor
     *
     * @param context
     */
    public Pizarra(Context context) {
        super(context);

        eventos();

    }


    public void setSistemaCoordenadas(float origen_x, float origen_y, float m_x, float m_y) {

        this.origen_x = origen_x;
        this.origen_y = origen_y;
        this.m_x = m_x;
        this.m_y = m_y;

    }

    public void eventos(){

        this.setOnTouchListener(new View.OnTouchListener() {


            public boolean onTouch(View v, MotionEvent event) {

                int action = event.getAction();

                if(evento_touch==true) {
                    //touch en pixeles
                    evento_x_touch_en_pixeles = event.getX()-origen_x;
                    evento_y_touch_en_pixeles = -(event.getY()-origen_y) + CR.pcApxY(50);


                }



                switch (action) {

                    case MotionEvent.ACTION_DOWN:


                        break;

                    case MotionEvent.ACTION_MOVE:

                        if(evento_touch==true) {

                            dibujarEstadoInicial();


                        }


                        break;

                    case MotionEvent.ACTION_UP:


                        break;


                }

                return true;
            }

        });//fin onTouch


    }//fin eventos


    public void setEstadoEscena(ObjetoLaboratorio[] cuerpos) {

        this.objetosLab = cuerpos;

    }


    //Método para dibujar la escena
    private void dibujarEscena(Canvas canvas, Paint pincel) {

        //dibujar las objetos de laboratorio
        for (int i = 0; i < objetosLab.length; i++) {
            if (objetosLab[i] != null) {
                objetosLab[i].dibujese(canvas, pincel);
            }
        }


    }


    //método para dibujar
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);


        Paint pincel = new Paint();
        //evita efecto sierra
        pincel.setAntiAlias(true);

        canvas.save();
        //cambio a sistema de coordenadas tradicional
        canvas.translate(origen_x, origen_y);
        canvas.scale(m_x, m_y);

        //dibujar objetos
        if (objetosLab != null)
            dibujarEscena(canvas, pincel);

        canvas.restore();

        //dibujar letreros
        dibujarLetreros(canvas, pincel);


        //necesario para actualizar los dibujos en animaciones
        invalidate();

    }

    private void dibujarLetreros(Canvas canvas, Paint pincel) {

        pincel.setTextSize(CR.pcApxL(2.5f));
        pincel.setColor(Color.rgb(100, 100, 100));

        //Aquí se deben usar variables propias de la app (según el documento)
        String tiempo = String.format("%.2f", AlmacenDatosRAM.tiempo);
        canvas.drawText("Tiempo = " + tiempo + " s", CR.pcApxX(2), CR.pcApxY(50), pincel);

        //otros letreros según el documento original...
    }

    private void dibujarEstadoInicial(){
        // implementación según el documento original
    }

    private void factorConversion() {

       /*
        Para dar una equivalencia de pixeles
        en metros se aumirá que 50 m equivale
        al ALTO de la pantalla (en posición
        LANSCAPE) en pixeles. Con base en esto
        el factor de conversion de metos  apixeles
        es:

        factorConversion_metroApixel= (ALTO en pixeles/ 50 metros)
        factorConversion_pixelAmetro= (ALTO metros / ALTO en pixeles)

        */

        factorConversion_metroApixel = CR.pcApxY(100f) / 50f;

        factorConversion_pixelAmetro = 50f / CR.pcApxY(100f);

    }

}
