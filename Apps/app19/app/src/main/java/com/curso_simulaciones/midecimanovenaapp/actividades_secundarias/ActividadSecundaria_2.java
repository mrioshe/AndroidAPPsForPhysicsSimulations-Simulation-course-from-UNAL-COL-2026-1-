package com.curso_simulaciones.midecimanovenaapp.actividades_secundarias;

import android.app.Activity;
import android.graphics.Color;
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

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        gestionarResolucion();

        //para crear elementos de la GUI
        crearElementosGUI();

        //para informar cómo se debe pegar el administrador de
        //diseño obtenido con el método GUI
        ViewGroup.LayoutParams parametro_layout_principal = new ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT);

        //pegar el contenedor con la GUI
        this.setContentView(crearGUI(), parametro_layout_principal);

        //para administrar los eventos
        eventos();

    } //fin del método onCreate

    private void gestionarResolucion() {

        tamanoLetraResolucionIncluida = (int) (0.8f * AlmacenDatosRAM.tamanoLetraResolucionIncluida);
        margenesResolucionIncluida = (int) (1.2f * AlmacenDatosRAM.tamanoLetraResolucionIncluida);

    }//fin método gestionarResolucion()

    /*método responsable de la creación de los elementos de la GUI*/
    private void crearElementosGUI() {

        textNombre = new TextView(this);
        textNombre.setGravity(Gravity.CENTER_VERTICAL);
        textNombre.setBackgroundColor(Color.rgb(200, 200, 200));
        textNombre.setTextSize(TypedValue.COMPLEX_UNIT_SP, tamanoLetraResolucionIncluida);
        textNombre.setPadding(margenesResolucionIncluida, 0, margenesResolucionIncluida, 0);
        textNombre.setText("NOMBRE");
        textNombre.setTextColor(Color.BLACK);

        editNombre = new EditText(this);
        editNombre.setTextSize(TypedValue.COMPLEX_UNIT_SP, tamanoLetraResolucionIncluida);
        editNombre.setText(AlmacenDatosRAM.nombreImagen2);
        editNombre.setHint("Xxxx");

        imageView = new ImageView(this);
        imageView.setScaleType(ImageView.ScaleType.CENTER_INSIDE);
        // Aquí puedes agregar una imagen desde drawable si está disponible
        // imageView.setImageResource(R.drawable.tu_imagen);
        imageView.setBackgroundColor(Color.rgb(240, 240, 240));

    }//fin método crearElementosGUI

    /*método responsable de administrar el diseño de la GUI*/
    private LinearLayout crearGUI() {

        // LinearLayout principal con marco verde
        LinearLayout linearPrincipal = new LinearLayout(this);
        linearPrincipal.setOrientation(LinearLayout.VERTICAL);
        linearPrincipal.setBackgroundColor(Color.WHITE);
        linearPrincipal.setPadding(10, 10, 10, 10);

        // LinearLayout interior (marco verde)
        LinearLayout linearInterior = new LinearLayout(this);
        linearInterior.setOrientation(LinearLayout.VERTICAL);
        linearInterior.setBackgroundColor(Color.rgb(144, 238, 144)); // verde claro
        linearInterior.setPadding(15, 15, 15, 15);
        linearInterior.setWeightSum(10.0f);

        // Barra superior (10% de la altura) - contiene NOMBRE y campo de texto
        LinearLayout linearBarraSuperior = new LinearLayout(this);
        linearBarraSuperior.setOrientation(LinearLayout.HORIZONTAL);
        linearBarraSuperior.setWeightSum(10.0f);

        // ImageView principal (90% de la altura)
        LinearLayout linearImagenPrincipal = new LinearLayout(this);
        linearImagenPrincipal.setOrientation(LinearLayout.VERTICAL);
        linearImagenPrincipal.setGravity(Gravity.CENTER);

        // Parámetros de pegado vertical con pesos
        LinearLayout.LayoutParams parametros_barra_superior = new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT, 0);
        parametros_barra_superior.weight = 1.0f;

        LinearLayout.LayoutParams parametros_imagen = new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT, 0);
        parametros_imagen.weight = 9.0f;

        // Pegar barra superior e imagen al linear interior
        linearInterior.addView(linearBarraSuperior, parametros_barra_superior);
        linearInterior.addView(linearImagenPrincipal, parametros_imagen);

        // Configurar elementos de la barra superior
        // TextView NOMBRE (35% del ancho)
        LinearLayout.LayoutParams parametros_texto_nombre = new LinearLayout.LayoutParams(0,
                ViewGroup.LayoutParams.MATCH_PARENT);
        parametros_texto_nombre.weight = 3.5f;
        parametros_texto_nombre.setMargins(5, 5, 5, 5);

        // EditText (65% del ancho)
        LinearLayout.LayoutParams parametros_edit_nombre = new LinearLayout.LayoutParams(0,
                ViewGroup.LayoutParams.MATCH_PARENT);
        parametros_edit_nombre.weight = 6.5f;
        parametros_edit_nombre.setMargins(5, 5, 5, 5);

        linearBarraSuperior.addView(textNombre, parametros_texto_nombre);
        linearBarraSuperior.addView(editNombre, parametros_edit_nombre);

        // Agregar ImageView al contenedor de imagen
        LinearLayout.LayoutParams parametros_image_view = new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT);
        parametros_image_view.setMargins(10, 10, 10, 10);
        linearImagenPrincipal.addView(imageView, parametros_image_view);

        // Agregar el linear interior al principal
        LinearLayout.LayoutParams parametros_interior = new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT);
        linearPrincipal.addView(linearInterior, parametros_interior);

        return linearPrincipal;

    }//fin método crearGUI

    /*Administra los eventos de la GUI*/
    private void eventos() {

    }//fin método eventos

    /* Este método es automático*/
    protected void onPause() {

        String nombre = editNombre.getText().toString();
        AlmacenDatosRAM.nombreImagen2 = nombre;
        AlmacenDatosRAM.habilitar_boton_tres = true;

        super.onPause();

    }

}//fin Actividad
