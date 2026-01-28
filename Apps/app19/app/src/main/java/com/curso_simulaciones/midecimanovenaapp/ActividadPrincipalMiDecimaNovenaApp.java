package com.curso_simulaciones.midecimanovenaapp;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.curso_simulaciones.midecimanovenaapp.actividades_secundarias.ActividadSecundaria_1;
import com.curso_simulaciones.midecimanovenaapp.actividades_secundarias.ActividadSecundaria_2;
import com.curso_simulaciones.midecimanovenaapp.actividades_secundarias.ActividadSecundaria_3;
import com.curso_simulaciones.midecimanovenaapp.datos.AlmacenDatosRAM;

public class ActividadPrincipalMiDecimaNovenaApp extends Activity {

    private Button botonUno, botonDos, botonTres, botonCuatro;
    private int tamanoLetraResolucionIncluida;

    // ImageViews para las dos zonas "IMAGEN"
    private ImageView imageTop, imageBottom;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        gestionarResolucion();

        // crear todos los elementos (botones e imagenes)
        crearElementosGUI();

        ViewGroup.LayoutParams parametro_layout_principal = new ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT);

        // pegar el contenedor con la GUI
        this.setContentView(crearGUI(), parametro_layout_principal);

        // para administrar los eventos
        eventos();
    } // fin onCreate

    private void gestionarResolucion() {

        // independencia de la resolución de la pantalla
        DisplayMetrics displayMetrics = this.getApplicationContext().getResources().getDisplayMetrics();
        int alto = displayMetrics.heightPixels;
        int ancho = displayMetrics.widthPixels;
        int dimensionReferencia;

        // tomar el menor valor entre alto y ancho de pantalla
        if (alto > ancho) {
            dimensionReferencia = ancho;
        } else {
            dimensionReferencia = alto;
        }

        // una estimación de un buen tamaño
        int tamanoLetra = dimensionReferencia / 20;

        // tamano de letra para usar acomodado a la resolución de pantalla
        tamanoLetraResolucionIncluida = (int) (tamanoLetra / displayMetrics.scaledDensity);

        // guardar en el almacen de datos para que otras clases la accedan fácilmente
        AlmacenDatosRAM.tamanoLetraResolucionIncluida = tamanoLetraResolucionIncluida;

    } // fin gestionarResolucion()

    /* Convierte dp a px */
    private int dpToPx(int dp) {
        return (int) (dp * getResources().getDisplayMetrics().density + 0.5f);
    }

    /* Crea un drawable rectangular con stroke y color de fondo */
    private GradientDrawable crearBorde(int grosorPx, int colorBorde, int colorFondo, int radioPx) {
        GradientDrawable drawable = new GradientDrawable();
        drawable.setShape(GradientDrawable.RECTANGLE);
        drawable.setStroke(grosorPx, colorBorde);
        drawable.setColor(colorFondo);
        drawable.setCornerRadius(radioPx);
        return drawable;
    }

    /* método responsable de la creación de los elementos de la GUI (botones + imageviews) */
    private void crearElementosGUI() {

        /*
         1. El tamaño a usar de la letra tiene corrección de
            resolución y tamaño de pantalla.
         */

        // BOTONES: se crean aquí, pero su apariencia se establecerá en crearGUI() con drawables
        botonUno = new Button(this);
        botonUno.setTextSize(TypedValue.COMPLEX_UNIT_SP, tamanoLetraResolucionIncluida);
        botonUno.setText("UNO");

        botonDos = new Button(this);
        botonDos.setTextSize(TypedValue.COMPLEX_UNIT_SP, tamanoLetraResolucionIncluida);
        botonDos.setText("DOS");

        botonTres = new Button(this);
        botonTres.setTextSize(TypedValue.COMPLEX_UNIT_SP, tamanoLetraResolucionIncluida);
        botonTres.setText("TRES");
        botonTres.setEnabled(false);

        botonCuatro = new Button(this);
        botonCuatro.setTextSize(TypedValue.COMPLEX_UNIT_SP, tamanoLetraResolucionIncluida);
        botonCuatro.setText("CUATRO");
        botonCuatro.setEnabled(false);

        // IMAGEVIEWS
        imageTop = new ImageView(this);
        imageTop.setAdjustViewBounds(false);
        imageTop.setScaleType(ImageView.ScaleType.CENTER_CROP);
        imageTop.setBackgroundColor(Color.TRANSPARENT);

        imageBottom = new ImageView(this);
        imageBottom.setAdjustViewBounds(false);
        imageBottom.setScaleType(ImageView.ScaleType.CENTER_CROP);
        imageBottom.setBackgroundColor(Color.TRANSPARENT);

        // cargar drawables (fallback: imagen1, imagen2, imagen)
        int idTop = getResources().getIdentifier("imagen1", "drawable", getPackageName());
        if (idTop == 0) idTop = getResources().getIdentifier("imagen", "drawable", getPackageName());
        if (idTop != 0) imageTop.setImageResource(idTop);

        int idBottom = getResources().getIdentifier("imagen2", "drawable", getPackageName());
        if (idBottom == 0) idBottom = getResources().getIdentifier("imagen", "drawable", getPackageName());
        if (idBottom != 0) imageBottom.setImageResource(idBottom);
    } // fin crearElementosGUI

    /* método responsable de administrar el diseño de la GUI */
    private LinearLayout crearGUI() {

        // medidas en px basadas en dp
        final int strokeOuter = dpToPx(6);    // borde exterior negro
        final int strokeInner = dpToPx(3);    // borde interior negro del fondo verde
        final int strokeYellow = dpToPx(4);   // borde amarillo que rodea a los botones
        final int cornerLarge = dpToPx(8);
        final int cornerMedium = dpToPx(6);
        final int cornerSmall = dpToPx(4);
        final int paddingPrincipal = dpToPx(6);
        final int paddingInterior = dpToPx(8);

        // espacio entre la fila superior (30%) y la inferior (70%)
        final int espacioEntre = dpToPx(10); // <-- aquí ajusta el valor en dp si quieres más/menos espacio

        // LinearLayout principal (borde negro exterior)
        LinearLayout linearPrincipal = new LinearLayout(this);
        linearPrincipal.setOrientation(LinearLayout.VERTICAL);
        linearPrincipal.setPadding(paddingPrincipal, paddingPrincipal, paddingPrincipal, paddingPrincipal);
        linearPrincipal.setBackground(crearBorde(strokeOuter, Color.BLACK, Color.TRANSPARENT, cornerLarge));

        // LinearLayout interior con fondo verde claro (el "panel" del que hablas en la imagen)
        LinearLayout linearInterior = new LinearLayout(this);
        linearInterior.setOrientation(LinearLayout.VERTICAL);
        linearInterior.setPadding(paddingInterior, paddingInterior, paddingInterior, paddingInterior);
        linearInterior.setWeightSum(10.0f); // usaremos 10 para repartir 3 (30%) + 7 (70%)
        linearInterior.setBackground(crearBorde(strokeInner, Color.BLACK, Color.rgb(144, 238, 144), cornerMedium));

        // --- FILA SUPERIOR (30%) y FILA INFERIOR (70%) ---
        LinearLayout filaSuperior = new LinearLayout(this);
        filaSuperior.setOrientation(LinearLayout.HORIZONTAL);
        filaSuperior.setWeightSum(10.0f);

        // fila inferior se mantiene como contenedor (pero le pondremos margen superior para el espacio)
        LinearLayout contImagenBottom = new LinearLayout(this);
        contImagenBottom.setOrientation(LinearLayout.VERTICAL);
        contImagenBottom.setGravity(Gravity.CENTER);
        // borde negro exterior y fondo cyan para imitar la imagen
        contImagenBottom.setBackground(crearBorde(dpToPx(4), Color.BLACK, Color.CYAN, cornerMedium));
        contImagenBottom.setPadding(dpToPx(6), dpToPx(6), dpToPx(6), dpToPx(6));

        // LayoutParams para filas (alto 0 + weight)
        LinearLayout.LayoutParams lpFilaSup = new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT, 0, 3.0f); // 3 de 10 -> 30%
        // aplicamos margen inferior en la fila superior para crear separación
        lpFilaSup.setMargins(0, 0, 0, espacioEntre);

        LinearLayout.LayoutParams lpFilaInf = new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT, 0, 7.0f); // 7 de 10 -> 70%
        // (opcional) también podemos aplicar margen superior a la fila inferior para mayor compatibilidad:
        lpFilaInf.setMargins(0, 0, 0, 0);

        // --- COLUMNA IZQUIERDA (20%) en fila superior: botones ---
        LinearLayout columnaBotones = new LinearLayout(this);
        columnaBotones.setOrientation(LinearLayout.VERTICAL);
        columnaBotones.setWeightSum(4.0f); // 4 botones -> cada uno weight 1
        // un contenedor amarillo con borde negro que engloba los botones (como referencia en la imagen)
        LinearLayout wrapperAmarillo = new LinearLayout(this);
        wrapperAmarillo.setOrientation(LinearLayout.VERTICAL);
        wrapperAmarillo.setPadding(dpToPx(4), dpToPx(4), dpToPx(4), dpToPx(4));
        wrapperAmarillo.setBackground(crearBorde(strokeYellow, Color.BLACK, Color.rgb(255, 255, 0), cornerSmall));
        wrapperAmarillo.setClipToPadding(false);

        LinearLayout.LayoutParams lpColIzq = new LinearLayout.LayoutParams(
                0, ViewGroup.LayoutParams.MATCH_PARENT, 2.0f); // 2 de 10 -> 20% ancho de la fila
        LinearLayout.LayoutParams lpWrapper = new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);

        // parámetros de cada botón: altura 0, weight 1 (cuatro filas iguales)
        LinearLayout.LayoutParams lpBoton = new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT, 0, 1.0f);
        lpBoton.setMargins(dpToPx(4), dpToPx(4), dpToPx(4), dpToPx(4));

        // Crear estilos de botón con borde negro y color de fondo como en la imagen
        int cornerBtn = dpToPx(4);
        botonUno.setBackground(crearBorde(dpToPx(2), Color.BLACK, Color.rgb(255, 165, 0), cornerBtn)); // naranja
        botonDos.setBackground(crearBorde(dpToPx(2), Color.BLACK, Color.rgb(220, 20, 60), cornerBtn)); // rojo oscuro
        botonTres.setBackground(crearBorde(dpToPx(2), Color.BLACK, Color.rgb(30, 144, 255), cornerBtn)); // azul
        botonCuatro.setBackground(crearBorde(dpToPx(2), Color.BLACK, Color.rgb(0, 255, 127), cornerBtn)); // verde

        // Añadimos los botones dentro del wrapper amarillo
        wrapperAmarillo.addView(botonUno, lpBoton);
        wrapperAmarillo.addView(botonDos, lpBoton);
        wrapperAmarillo.addView(botonTres, lpBoton);
        wrapperAmarillo.addView(botonCuatro, lpBoton);

        columnaBotones.addView(wrapperAmarillo, lpWrapper);
        filaSuperior.addView(columnaBotones, lpColIzq);

        // --- COLUMNA DERECHA (80%) en fila superior: imagen pequeña con borde ---
        LinearLayout contImagenTop = new LinearLayout(this);
        contImagenTop.setOrientation(LinearLayout.VERTICAL);
        contImagenTop.setGravity(Gravity.CENTER);
        // borde negro alrededor de la imagen (similar a tu ejemplo)
        int strokeImgTop = dpToPx(4);
        contImagenTop.setBackground(crearBorde(strokeImgTop, Color.BLACK, Color.rgb(255, 200, 170), cornerSmall));
        // padding = grosor del stroke para que la imagen no se solape con el trazo
        contImagenTop.setPadding(strokeImgTop, strokeImgTop, strokeImgTop, strokeImgTop);

        LinearLayout.LayoutParams lpColDer = new LinearLayout.LayoutParams(
                0, ViewGroup.LayoutParams.MATCH_PARENT, 8.0f); // 8 de 10 -> 80% ancho de la fila
        lpColDer.setMargins(dpToPx(6), 0, 0, 0);

        // imageTop ocupa TODO el contenedor interior
        LinearLayout.LayoutParams lpImageTop = new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        contImagenTop.addView(imageTop, lpImageTop);

        filaSuperior.addView(contImagenTop, lpColDer);

        // --- FILA INFERIOR: imageBottom añadida al contenedor contImagenBottom ---
        LinearLayout.LayoutParams lpImageBottom = new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        contImagenBottom.addView(imageBottom, lpImageBottom);

        // montar todo en linearInterior (colocamos filaSuperior con margen inferior para separación)
        linearInterior.addView(filaSuperior, lpFilaSup);
        linearInterior.addView(contImagenBottom, lpFilaInf);

        // meter linearInterior dentro del principal
        linearPrincipal.addView(linearInterior, new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT));

        return linearPrincipal;
    } // fin crearGUI

    /* Administra los eventos de la GUI */
    private void eventos() {

        // evento del boton con etiqueta UNO
        botonUno.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                lanzarActividadSecundaria_1();
            }
        });

        // evento del boton con etiqueta DOS
        botonDos.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                lanzarActividadSecundaria_2();
            }
        });

        // evento del boton con etiqueta TRES
        botonTres.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                lanzarActividadSecundaria_3();
            }
        });

        // Si quieres que CUATRO lance algo, la funcionalidad original no la tenía.
    } // fin eventos

    // métodos que lanzan las actividades secundarias
    private void lanzarActividadSecundaria_1() {
        Intent intent = new Intent(this, ActividadSecundaria_1.class);
        startActivity(intent);
    }

    private void lanzarActividadSecundaria_2() {
        Intent intent = new Intent(this, ActividadSecundaria_2.class);
        startActivity(intent);
    }

    private void lanzarActividadSecundaria_3() {
        Intent intent = new Intent(this, ActividadSecundaria_3.class);
        startActivity(intent);
    }

    /* Métodos automáticos */

    @Override
    protected void onResume() {
        super.onResume();

        if (AlmacenDatosRAM.habilitar_boton_tres == true) {
            botonTres.setEnabled(true);
        } else {
            botonTres.setEnabled(false);
        }
    }

    @Override
    protected void onDestroy() {

        // Volver los valores de los datos a su estado por defecto
        AlmacenDatosRAM.nombreImagen1 = "xxxx";
        AlmacenDatosRAM.nombreImagen2 = "xxxx";
        AlmacenDatosRAM.habilitar_boton_tres = false;

        this.finish();

        super.onDestroy();
    } // fin onDestroy

} // fin clase
