package com.curso_simulaciones.midecimanovenaapp.actividades_secundarias;

import android.app.Activity;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.curso_simulaciones.midecimanovenaapp.datos.AlmacenDatosRAM;

public class ActividadSecundaria_2 extends Activity {

    private int tamanoLetraResolucionIncluida;
    private int margenesResolucionIncluida;
    private TextView textNombre;
    private EditText editNombre;
    private ImageView imageView;

    // Convierte dp a px para consistencia entre densidades
    private int dpToPx(int dp) {
        return (int) (dp * getResources().getDisplayMetrics().density + 0.5f);
    }

    // Crea un drawable rectangular con stroke y color de fondo
    private GradientDrawable crearBorde(int grosorPx, int colorBorde, int colorFondo, int radioPx) {
        GradientDrawable drawable = new GradientDrawable();
        drawable.setShape(GradientDrawable.RECTANGLE);
        drawable.setStroke(grosorPx, colorBorde);
        drawable.setColor(colorFondo);
        drawable.setCornerRadius(radioPx);
        return drawable;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        gestionarResolucion();

        // para crear elementos de la GUI
        crearElementosGUI();

        // para informar cómo se debe pegar el administrador de diseño obtenido con el método GUI
        ViewGroup.LayoutParams parametro_layout_principal = new ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT);

        // pegar el contenedor con la GUI
        this.setContentView(crearGUI(), parametro_layout_principal);

        // para administrar los eventos
        eventos();
    } // fin del método onCreate

    private void gestionarResolucion() {
        tamanoLetraResolucionIncluida = (int) (0.8f * AlmacenDatosRAM.tamanoLetraResolucionIncluida);
        margenesResolucionIncluida = (int) (1.2f * AlmacenDatosRAM.tamanoLetraResolucionIncluida);
    } // fin método gestionarResolucion()

    /* método responsable de la creación de los elementos de la GUI */
    private void crearElementosGUI() {

        textNombre = new TextView(this);
        textNombre.setGravity(Gravity.CENTER);
        textNombre.setText("NOMBRE");
        textNombre.setTextColor(Color.BLACK);
        textNombre.setTextSize(TypedValue.COMPLEX_UNIT_SP, tamanoLetraResolucionIncluida);
        int padInternal = dpToPx(6);
        textNombre.setPadding(padInternal, padInternal, padInternal, padInternal);

        editNombre = new EditText(this);
        editNombre.setTextSize(TypedValue.COMPLEX_UNIT_SP, tamanoLetraResolucionIncluida);
        editNombre.setText(AlmacenDatosRAM.nombreImagen2);
        editNombre.setHint("Xxxx");
        editNombre.setPadding(padInternal, padInternal, padInternal, padInternal);

        imageView = new ImageView(this);
        // Ajustes para que ocupe TODO el contenedor (pero será recortada por el padding del contenedor con borde)
        imageView.setAdjustViewBounds(false);
        imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
        imageView.setBackgroundColor(Color.TRANSPARENT);
        imageView.setPadding(0, 0, 0, 0);

        // Cargar drawable (asegúrate del nombre en res/drawable/imagen.png)
        int idDrawable = getResources().getIdentifier("imagen2", "drawable", getPackageName());
        if (idDrawable != 0) {
            imageView.setImageResource(idDrawable);
        } else {
            // Si no existe, puedes dejar un color neutro (no cambia funcionalidad)
            imageView.setBackgroundColor(Color.rgb(240, 240, 240));
        }
    } // fin método crearElementosGUI

    /* método responsable de administrar el diseño de la GUI */
    private LinearLayout crearGUI() {

        int strokeOuter = dpToPx(3);
        int strokeInner = dpToPx(2);
        final int strokeSmall = dpToPx(4); // grosor del borde alrededor de la imagen (ajusta si quieres)
        int cornerLarge = dpToPx(10);
        int cornerMedium = dpToPx(8);
        int cornerSmall = dpToPx(6);
        int paddingPrincipal = dpToPx(6);
        int paddingInterior = dpToPx(12);
        int espacioEntre = dpToPx(8);

        // LinearLayout principal (borde negro exterior)
        LinearLayout linearPrincipal = new LinearLayout(this);
        linearPrincipal.setOrientation(LinearLayout.VERTICAL);
        linearPrincipal.setPadding(paddingPrincipal, paddingPrincipal, paddingPrincipal, paddingPrincipal);
        linearPrincipal.setBackground(crearBorde(strokeOuter, Color.BLACK, Color.TRANSPARENT, cornerLarge));

        // LinearLayout interior con fondo verde claro (verde del ejemplo) y borde negro fino
        LinearLayout linearInterior = new LinearLayout(this);
        linearInterior.setOrientation(LinearLayout.VERTICAL);
        linearInterior.setPadding(paddingInterior, paddingInterior, paddingInterior, paddingInterior);
        linearInterior.setWeightSum(10.0f);
        linearInterior.setBackground(crearBorde(strokeInner, Color.BLACK, Color.rgb(144, 238, 144), cornerMedium));

        // Barra superior (10%) que contiene NOMBRE y campo de texto — la ponemos arriba ahora
        LinearLayout linearBarraSuperior = new LinearLayout(this);
        linearBarraSuperior.setOrientation(LinearLayout.HORIZONTAL);
        linearBarraSuperior.setBackground(crearBorde(strokeInner, Color.BLACK, Color.YELLOW, cornerSmall));
        linearBarraSuperior.setPadding(dpToPx(4), dpToPx(4), dpToPx(4), dpToPx(4));

        // Contenedor de la imagen (90%) - con borde visible que reduce área útil
        LinearLayout linearImagenPrincipal = new LinearLayout(this);
        linearImagenPrincipal.setOrientation(LinearLayout.VERTICAL);
        linearImagenPrincipal.setGravity(Gravity.CENTER);
        linearImagenPrincipal.setBackground(crearBorde(strokeSmall, Color.BLACK, Color.TRANSPARENT, cornerSmall));
        // Padding igual al grosor para que la imagen no monte sobre el borde
        linearImagenPrincipal.setPadding(strokeSmall, strokeSmall, strokeSmall, strokeSmall);

        // Parámetros de pegado vertical con pesos (barra arriba, imagen abajo)
        LinearLayout.LayoutParams parametros_barra_superior = new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT, 0, 1.0f);
        parametros_barra_superior.setMargins(dpToPx(10), 0, dpToPx(10), dpToPx(0));

        LinearLayout.LayoutParams parametros_imagen = new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT, 0, 9.0f);
        parametros_imagen.setMargins(0, espacioEntre, 0, 0);

        // Pegar barra superior e imagen al linear interior (barra arriba)
        linearInterior.addView(linearBarraSuperior, parametros_barra_superior);
        linearInterior.addView(linearImagenPrincipal, parametros_imagen);

        // Configurar elementos de la barra superior
        // TextView NOMBRE (35% del ancho) dentro de un contenedor naranja con borde fino
        LinearLayout linearNombreContainer = new LinearLayout(this);
        linearNombreContainer.setOrientation(LinearLayout.VERTICAL);
        linearNombreContainer.setGravity(Gravity.CENTER);
        linearNombreContainer.setBackground(crearBorde(dpToPx(1), Color.BLACK, Color.rgb(255, 165, 0), cornerSmall));
        linearNombreContainer.setPadding(dpToPx(4), dpToPx(4), dpToPx(4), dpToPx(4));

        LinearLayout.LayoutParams parametros_texto_nombre = new LinearLayout.LayoutParams(0,
                ViewGroup.LayoutParams.MATCH_PARENT, 3.5f);
        parametros_texto_nombre.setMargins(dpToPx(5), dpToPx(5), dpToPx(5), dpToPx(5));

        // EditText (65% del ancho) con fondo blanco y borde fino
        LinearLayout.LayoutParams parametros_edit_nombre = new LinearLayout.LayoutParams(0,
                ViewGroup.LayoutParams.MATCH_PARENT, 6.5f);
        parametros_edit_nombre.setMargins(dpToPx(5), dpToPx(5), dpToPx(5), dpToPx(5));

        linearNombreContainer.addView(textNombre, new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));

        editNombre.setBackground(crearBorde(dpToPx(1), Color.BLACK, Color.WHITE, cornerSmall));

        linearBarraSuperior.addView(linearNombreContainer, parametros_texto_nombre);
        linearBarraSuperior.addView(editNombre, parametros_edit_nombre);

        // Añadir ImageView para que ocupe TODO el área interior del contenedor (respetando el padding del contenedor)
        LinearLayout.LayoutParams parametros_image_view = new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT);
        parametros_image_view.setMargins(0, 0, 0, 0);

        imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
        imageView.setAdjustViewBounds(false);
        linearImagenPrincipal.addView(imageView, parametros_image_view);

        // Agregar el linear interior al principal
        LinearLayout.LayoutParams parametros_interior = new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT);
        linearPrincipal.addView(linearInterior, parametros_interior);

        return linearPrincipal;
    } // fin método crearGUI

    /* Administra los eventos de la GUI */
    private void eventos() {
        // no se cambió la funcionalidad; puedes añadir listeners si los necesitas
    } // fin método eventos

    /* Este método es automático */
    @Override
    protected void onPause() {
        String nombre = editNombre.getText().toString();
        AlmacenDatosRAM.nombreImagen2 = nombre;
        AlmacenDatosRAM.habilitar_boton_tres = true;
        super.onPause();
    }
} // fin Actividad
