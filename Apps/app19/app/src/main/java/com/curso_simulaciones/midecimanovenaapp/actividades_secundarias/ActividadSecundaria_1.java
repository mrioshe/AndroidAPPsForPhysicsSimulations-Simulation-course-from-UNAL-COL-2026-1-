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

public class ActividadSecundaria_1 extends Activity {

    private int tamanoLetraResolucionIncluida;
    private int margenesResolucionIncluida;
    private TextView textNombre;
    private EditText editNombre;
    private ImageView imageView;

    // Convierte dp a px
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
        crearElementosGUI();

        ViewGroup.LayoutParams parametro_layout_principal = new ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT);

        this.setContentView(crearGUI(), parametro_layout_principal);

        eventos();
    }

    private void gestionarResolucion() {
        tamanoLetraResolucionIncluida = (int) (0.8f * AlmacenDatosRAM.tamanoLetraResolucionIncluida);
        margenesResolucionIncluida = (int) (1.2f * AlmacenDatosRAM.tamanoLetraResolucionIncluida);
    }

    /* Creación de vistas */
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
        editNombre.setText(AlmacenDatosRAM.nombreImagen1);
        editNombre.setHint("Xxxx");
        editNombre.setPadding(padInternal, padInternal, padInternal, padInternal);

        imageView = new ImageView(this);

        // Ajustes para que ocupe TODO el contenedor interior (pero será recortada por el padding del contenedor)
        imageView.setAdjustViewBounds(false);
        imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);

        // Sin fondo ni padding en el ImageView
        imageView.setBackgroundColor(Color.TRANSPARENT);
        imageView.setPadding(0, 0, 0, 0);

        // Cargar drawable (verifica que exista res/drawable/imagen.png)
        int idDrawable = getResources().getIdentifier("imagen", "drawable", getPackageName());
        if (idDrawable != 0) {
            imageView.setImageResource(idDrawable);
        }
    }

    /* Construcción del layout completo */
    private LinearLayout crearGUI() {

        int strokeOuter = dpToPx(3);
        int strokeInner = dpToPx(2);
        final int strokeSmall = dpToPx(4); // <-- Grosor del borde alrededor de la imagen (aumenta si quieres)
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

        // LinearLayout interior con fondo verde claro
        LinearLayout linearInterior = new LinearLayout(this);
        linearInterior.setOrientation(LinearLayout.VERTICAL);
        linearInterior.setPadding(paddingInterior, paddingInterior, paddingInterior, paddingInterior);
        linearInterior.setWeightSum(10.0f);
        linearInterior.setBackground(crearBorde(strokeInner, Color.BLACK, Color.rgb(144, 238, 144), cornerMedium));

        // Contenedor de la imagen (con borde visible). El padding interior es igual al grosor del strokeSmall
        // para que el contenido (ImageView) no se superponga al trazo y así reduzca el área útil.
        LinearLayout linearImagenPrincipal = new LinearLayout(this);
        linearImagenPrincipal.setOrientation(LinearLayout.VERTICAL);
        linearImagenPrincipal.setGravity(Gravity.CENTER);
        linearImagenPrincipal.setBackground(crearBorde(strokeSmall, Color.BLACK, Color.TRANSPARENT, cornerSmall));
        // Padding igual al grosor del borde (esto reduce el área útil de la imagen)
        linearImagenPrincipal.setPadding(strokeSmall, strokeSmall, strokeSmall, strokeSmall);

        // Barra inferior que contiene la etiqueta y el edittext. Esta barra es amarilla.
        LinearLayout linearBarraSuperior = new LinearLayout(this);
        linearBarraSuperior.setOrientation(LinearLayout.HORIZONTAL);
        linearBarraSuperior.setBackground(crearBorde(strokeInner, Color.BLACK, Color.YELLOW, cornerSmall));
        linearBarraSuperior.setPadding(dpToPx(4), dpToPx(4), dpToPx(4), dpToPx(4));

        LinearLayout.LayoutParams parametros_imagen = new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT, 0, 9.0f);
        // dejamos sólo un pequeño espacio entre la imagen y la barra
        parametros_imagen.setMargins(0, 0, 0, espacioEntre);

        LinearLayout.LayoutParams parametros_barra_superior = new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT, 0, 1.0f);
        parametros_barra_superior.setMargins(dpToPx(10), 0, dpToPx(10), dpToPx(10));

        linearInterior.addView(linearImagenPrincipal, parametros_imagen);
        linearInterior.addView(linearBarraSuperior, parametros_barra_superior);

        LinearLayout linearNombreContainer = new LinearLayout(this);
        linearNombreContainer.setOrientation(LinearLayout.VERTICAL);
        linearNombreContainer.setGravity(Gravity.CENTER);
        linearNombreContainer.setBackground(crearBorde(dpToPx(1), Color.BLACK, Color.rgb(255, 165, 0), cornerSmall));
        linearNombreContainer.setPadding(dpToPx(4), dpToPx(4), dpToPx(4), dpToPx(4));

        LinearLayout.LayoutParams parametros_texto_nombre = new LinearLayout.LayoutParams(
                0, ViewGroup.LayoutParams.MATCH_PARENT, 3.5f);
        parametros_texto_nombre.setMargins(dpToPx(5), dpToPx(5), dpToPx(5), dpToPx(5));

        LinearLayout.LayoutParams parametros_edit_nombre = new LinearLayout.LayoutParams(
                0, ViewGroup.LayoutParams.MATCH_PARENT, 6.5f);
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

        linearPrincipal.addView(linearInterior, new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT));

        return linearPrincipal;
    }

    private void eventos() {
        // listeners si los necesitas
    }

    @Override
    protected void onPause() {
        AlmacenDatosRAM.nombreImagen1 = editNombre.getText().toString();
        AlmacenDatosRAM.habilitar_boton_tres = true;
        super.onPause();
    }
}
