package com.curso_simulaciones.mivigesimaquintaapp.utilidades;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.util.TypedValue;
import android.view.Gravity;
import androidx.appcompat.widget.AppCompatButton;

/**
 * Clase para crear botones personalizados con estilo consistente
 */
public class Boton extends AppCompatButton {

    public Boton(Context context) {
        super(context);

        // Configurar apariencia por defecto
        setTextColor(Color.WHITE);
        setTextSize(TypedValue.COMPLEX_UNIT_SP, 12); // Reducido de 14 a 12
        setTypeface(null, Typeface.BOLD);
        setGravity(Gravity.CENTER);

        setBackgroundColor(Color.rgb(33, 150, 243)); // Azul material
        setPadding(12, 6, 12, 6); // Reducido padding: horizontal de 15 a 12, vertical de 8 a 6
        setAllCaps(false);

        // Efecto de elevación
        setElevation(4f); // Reducido de 6f a 4f
        setStateListAnimator(null);

        // Altura mínima reducida
        setMinimumHeight(0);
    }

    /**
     * Establece una imagen de fondo desde recursos
     * @param idRecurso ID del recurso drawable
     */
    public void setImagen(int idRecurso) {
        try {
            setBackgroundResource(idRecurso);
        } catch (Exception e) {
            // Si no se encuentra la imagen, mantener el fondo por defecto
            e.printStackTrace();
        }
    }

    /**
     * Establece el color de fondo del botón
     * @param color Color en formato ARGB
     */
    public void setColorFondo(int color) {
        setBackgroundColor(color);
    }

    /**
     * Cambia el estado visual del botón (habilitado/deshabilitado)
     * @param habilitado true si está habilitado
     */
    @Override
    public void setEnabled(boolean habilitado) {
        super.setEnabled(habilitado);
        if (habilitado) {
            setAlpha(1.0f);
        } else {
            setAlpha(0.5f);
        }
    }
}