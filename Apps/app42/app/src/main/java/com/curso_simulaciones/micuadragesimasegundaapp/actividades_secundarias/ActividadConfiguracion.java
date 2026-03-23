package com.curso_simulaciones.micuadragesimasegundaapp.actividades_secundarias;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.os.Build;
import android.os.Bundle;
import android.text.InputType;
import android.text.method.PasswordTransformationMethod;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.RequiresApi;

import com.curso_simulaciones.micuadragesimasegundaapp.datos.AlmacenDatosRAM;

/**
 * Pantalla de configuración del broker MQTT.
 * Permite editar URL del broker, usuario, contraseña y tópico.
 * Los valores se persisten con SharedPreferences.
 */
public class ActividadConfiguracion extends Activity {

    private EditText edit_text_broker, edit_text_usuario,
            edit_text_topico, edit_text_contrasena;
    private TextView text_broker, text_usuario, text_contrasena, text_topico;
    private Button   boton_grabar;
    private int      tamano_letra;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        gestionarResolucion();
        crearElementosGUI();
        setContentView(crearGUI(),
                new ViewGroup.LayoutParams(
                        ViewGroup.LayoutParams.MATCH_PARENT,
                        ViewGroup.LayoutParams.MATCH_PARENT));
        eventosGui();
    }

    private void gestionarResolucion() {
        tamano_letra = (int)(0.5f * AlmacenDatosRAM.tamanoLetraResolucionIncluida);
    }

    private void eventosGui() {
        boton_grabar.setOnClickListener(v -> {
            guardar();
            onBackPressed();
        });
    }

    private void crearElementosGUI() {

        text_broker = label(" URL DEL BROKER");
        edit_text_broker = campo("" + AlmacenDatosRAM.MQTTHOST);

        text_usuario = label(" USUARIO");
        edit_text_usuario = campo("" + AlmacenDatosRAM.USERNAME);

        text_contrasena = label(" CONTRASEÑA");
        edit_text_contrasena = campo("" + AlmacenDatosRAM.PASSWORD);
        edit_text_contrasena.setInputType(
                InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
        edit_text_contrasena.setTransformationMethod(
                PasswordTransformationMethod.getInstance());

        text_topico = label(" TOPICO");
        edit_text_topico = campo("" + AlmacenDatosRAM.topicStr);

        boton_grabar = new Button(this);
        boton_grabar.setTextSize(TypedValue.COMPLEX_UNIT_SP, tamano_letra);
        boton_grabar.setText("GUARDAR");
        boton_grabar.getBackground().setColorFilter(
                Color.rgb(255, 255, 100), PorterDuff.Mode.MULTIPLY);
    }

    private TextView label(String texto) {
        TextView tv = new TextView(this);
        tv.setGravity(Gravity.FILL_VERTICAL);
        tv.setBackgroundColor(Color.YELLOW);
        tv.setTextSize(tamano_letra);
        tv.setText(texto);
        tv.setTextColor(Color.BLACK);
        return tv;
    }

    private EditText campo(String valor) {
        EditText et = new EditText(this);
        et.setTextSize(tamano_letra);
        et.setText(valor);
        return et;
    }

    private LinearLayout crearGUI() {
        LinearLayout principal = new LinearLayout(this);
        principal.setOrientation(LinearLayout.VERTICAL);
        principal.setBackgroundColor(Color.WHITE);

        // Parámetros comunes para filas de etiqueta + campo
        LinearLayout.LayoutParams pFila = new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT, 0);
        pFila.weight = 0.5f;

        LinearLayout.LayoutParams pI = new LinearLayout.LayoutParams(
                0, ViewGroup.LayoutParams.MATCH_PARENT);
        pI.weight = 1f;
        LinearLayout.LayoutParams pD = new LinearLayout.LayoutParams(
                0, ViewGroup.LayoutParams.MATCH_PARENT);
        pD.weight = 1f;

        // Fila broker
        LinearLayout f1 = fila(Color.YELLOW, 2f);
        f1.setLayoutParams(pFila);
        text_broker.setLayoutParams(pI);
        edit_text_broker.setLayoutParams(pD);
        f1.addView(text_broker);
        f1.addView(edit_text_broker);

        // Fila usuario
        LinearLayout f2 = fila(Color.YELLOW, 2f);
        f2.setLayoutParams(pFila);
        text_usuario.setLayoutParams(pI);
        edit_text_usuario.setLayoutParams(pD);
        f2.addView(text_usuario);
        f2.addView(edit_text_usuario);

        // Fila contraseña
        LinearLayout f3 = fila(Color.YELLOW, 2f);
        f3.setLayoutParams(pFila);
        text_contrasena.setLayoutParams(pI);
        edit_text_contrasena.setLayoutParams(pD);
        f3.addView(text_contrasena);
        f3.addView(edit_text_contrasena);

        // Fila tópico
        LinearLayout f4 = fila(Color.YELLOW, 2f);
        f4.setLayoutParams(pFila);
        text_topico.setLayoutParams(pI);
        edit_text_topico.setLayoutParams(pD);
        f4.addView(text_topico);
        f4.addView(edit_text_topico);

        // Fila botón GUARDAR
        LinearLayout f5 = fila(Color.YELLOW, 1f);
        f5.setLayoutParams(pFila);
        LinearLayout.LayoutParams pBtn = new LinearLayout.LayoutParams(
                0, ViewGroup.LayoutParams.MATCH_PARENT);
        pBtn.weight = 1f;
        f5.addView(boton_grabar, pBtn);

        // Relleno inferior
        LinearLayout f6 = new LinearLayout(this);
        f6.setBackgroundColor(Color.BLACK);
        LinearLayout.LayoutParams pRelleno = new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT, 0);
        pRelleno.weight = 7.5f;
        f6.setLayoutParams(pRelleno);

        principal.addView(f1); principal.addView(f2); principal.addView(f3);
        principal.addView(f4); principal.addView(f5); principal.addView(f6);

        return principal;
    }

    private LinearLayout fila(int color, float weightSum) {
        LinearLayout l = new LinearLayout(this);
        l.setOrientation(LinearLayout.HORIZONTAL);
        l.setBackgroundColor(color);
        l.setWeightSum(weightSum);
        return l;
    }

    @Override
    protected void onPause() { super.onPause(); }

    /** Persiste los datos de configuración en SharedPreferences. */
    private void guardar() {
        AlmacenDatosRAM.MQTTHOST = edit_text_broker.getText().toString();
        AlmacenDatosRAM.USERNAME = edit_text_usuario.getText().toString();
        AlmacenDatosRAM.PASSWORD = edit_text_contrasena.getText().toString();
        AlmacenDatosRAM.topicStr = edit_text_topico.getText().toString();

        SharedPreferences.Editor editor =
                getSharedPreferences("MisPreferencias", Context.MODE_PRIVATE).edit();
        editor.putString("broker",  AlmacenDatosRAM.MQTTHOST);
        editor.putString("usuario", AlmacenDatosRAM.USERNAME);
        editor.putString("pasword", AlmacenDatosRAM.PASSWORD);
        editor.putString("topico",  AlmacenDatosRAM.topicStr);
        editor.commit();
    }
}