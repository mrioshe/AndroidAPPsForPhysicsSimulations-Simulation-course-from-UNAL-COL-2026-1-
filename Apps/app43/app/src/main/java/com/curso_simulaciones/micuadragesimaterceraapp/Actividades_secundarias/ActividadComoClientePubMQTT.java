package com.curso_simulaciones.micuadragesimaterceraapp.Actividades_secundarias;

import android.app.Activity;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.TextView;
import android.util.TypedValue;

import androidx.annotation.Nullable;

import com.curso_simulaciones.micuadragesimaterceraapp.comunicaciones.ClientePubSubMQTT;
import com.curso_simulaciones.micuadragesimaterceraapp.datos.AlmacenDatosRAM;
import com.curso_simulaciones.micuadragesimaterceraapp.gui_auxiliares.DialogoSalir;
import com.curso_simulaciones.micuadragesimaterceraapp.utilidades.TableroColor;

import org.json.JSONException;
import org.json.JSONObject;

public class ActividadComoClientePubMQTT extends Activity implements Runnable {

    private int tamanoLetraResolucionIncluida;

    // Objetos GUI necesarios
    private TextView textRojo, textVerde, textAzul;
    private TextView textviewAviso;
    private Button botonConectar;
    private SeekBar seekBarRojo, seekBarVerde, seekBarAzul;
    private TableroColor tablero;

    // Valores de las variables
    private int rojo, verde, azul;

    private ClientePubSubMQTT cliente;
    private Thread hilo;
    private volatile boolean ejecutando = false;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        gestionarResolucion();
        crearElementosGUI();

        ViewGroup.LayoutParams parametro_layout_principal =
                new ViewGroup.LayoutParams(
                        ViewGroup.LayoutParams.MATCH_PARENT,
                        ViewGroup.LayoutParams.MATCH_PARENT
                );

        setContentView(crearGUI(), parametro_layout_principal);

        eventos();
        crearCliente();
        hilo = new Thread(this);

        AlmacenDatosRAM.estado_conexion_nube = 1;
        actualizarAviso();
    }

    private void gestionarResolucion() {
        tamanoLetraResolucionIncluida = (int) (0.8f * AlmacenDatosRAM.tamanoLetraResolucionIncluida);
    }

    private void crearElementosGUI() {

        botonConectar = new Button(this);
        botonConectar.setTextSize(TypedValue.COMPLEX_UNIT_SP, tamanoLetraResolucionIncluida);
        botonConectar.setText("CONECTAR");
        botonConectar.getBackground().setColorFilter(
                Color.rgb(183, 216, 199),
                PorterDuff.Mode.MULTIPLY
        );

        textRojo = new TextView(this);
        textRojo.setTextSize(TypedValue.COMPLEX_UNIT_SP, tamanoLetraResolucionIncluida);
        textRojo.setGravity(Gravity.CENTER);
        textRojo.setBackgroundColor(Color.BLACK);
        textRojo.setText("ROJO\n0 A 255");

        textVerde = new TextView(this);
        textVerde.setTextSize(TypedValue.COMPLEX_UNIT_SP, tamanoLetraResolucionIncluida);
        textVerde.setGravity(Gravity.CENTER);
        textVerde.setBackgroundColor(Color.BLACK);
        textVerde.setText("VERDE\n0 A 255");

        textAzul = new TextView(this);
        textAzul.setTextSize(TypedValue.COMPLEX_UNIT_SP, tamanoLetraResolucionIncluida);
        textAzul.setGravity(Gravity.CENTER);
        textAzul.setBackgroundColor(Color.BLACK);
        textAzul.setText("AZUL\n0 A 255");

        seekBarRojo = new SeekBar(this);
        seekBarRojo.setMax(255);
        seekBarRojo.setScaleY(0.2f);
        seekBarRojo.setProgress(100);
        rojo = seekBarRojo.getProgress();

        seekBarVerde = new SeekBar(this);
        seekBarVerde.setMax(255);
        seekBarVerde.setScaleY(0.2f);
        seekBarVerde.setProgress(200);
        verde = seekBarVerde.getProgress();

        seekBarAzul = new SeekBar(this);
        seekBarAzul.setMax(255);
        seekBarAzul.setScaleY(0.2f);
        seekBarAzul.setProgress(60);
        azul = seekBarAzul.getProgress();

        textviewAviso = new TextView(this);
        textviewAviso.setGravity(Gravity.CENTER_VERTICAL);
        textviewAviso.setBackgroundColor(Color.rgb(183, 216, 199));
        textviewAviso.setTextSize(TypedValue.COMPLEX_UNIT_SP, 0.8f * tamanoLetraResolucionIncluida);
        textviewAviso.setText(" ");
        textviewAviso.setTextColor(Color.BLACK);

        tablero = new TableroColor(this);
    }

    private LinearLayout crearGUI() {

        LinearLayout linearPrincipal = new LinearLayout(this);
        linearPrincipal.setOrientation(LinearLayout.VERTICAL);
        linearPrincipal.setBackgroundColor(Color.YELLOW);
        linearPrincipal.setWeightSum(10.0f);

        LinearLayout linearPrincipalArriba = new LinearLayout(this);
        linearPrincipalArriba.setOrientation(LinearLayout.HORIZONTAL);
        linearPrincipalArriba.setBackgroundColor(Color.YELLOW);

        LinearLayout linearPrincipalAbajo = new LinearLayout(this);
        linearPrincipalAbajo.setOrientation(LinearLayout.HORIZONTAL);
        linearPrincipalAbajo.setBackgroundColor(Color.rgb(183, 216, 199));
        linearPrincipalAbajo.setWeightSum(1.0f);

        LinearLayout linear_izquierda = new LinearLayout(this);
        linear_izquierda.setOrientation(LinearLayout.VERTICAL);
        linear_izquierda.setBackgroundColor(Color.WHITE);
        linear_izquierda.setWeightSum(1.0f);

        LinearLayout linear_derecha = new LinearLayout(this);
        linear_derecha.setBackgroundColor(Color.YELLOW);
        linear_derecha.setOrientation(LinearLayout.VERTICAL);
        linear_derecha.setWeightSum(6.0f);

        LinearLayout.LayoutParams parametros_pegado_izquierdo =
                new LinearLayout.LayoutParams(0, ViewGroup.LayoutParams.MATCH_PARENT);
        parametros_pegado_izquierdo.weight = 8.0f;
        parametros_pegado_izquierdo.setMargins(5, 5, 5, 5);
        linearPrincipalArriba.addView(linear_izquierda, parametros_pegado_izquierdo);

        LinearLayout.LayoutParams parametros_pegado_derecho =
                new LinearLayout.LayoutParams(0, ViewGroup.LayoutParams.MATCH_PARENT);
        parametros_pegado_derecho.weight = 2.0f;
        parametros_pegado_derecho.setMargins(5, 5, 5, 5);
        linearPrincipalArriba.addView(linear_derecha, parametros_pegado_derecho);

        LinearLayout linear_1 = new LinearLayout(this);
        linear_1.setBackgroundColor(Color.BLUE);
        linear_1.setOrientation(LinearLayout.VERTICAL);

        LinearLayout linear_2 = new LinearLayout(this);
        linear_2.setBackgroundColor(Color.YELLOW);
        linear_2.setOrientation(LinearLayout.VERTICAL);

        LinearLayout linear_3 = new LinearLayout(this);
        linear_3.setBackgroundColor(Color.YELLOW);
        linear_3.setOrientation(LinearLayout.VERTICAL);

        LinearLayout linear_4 = new LinearLayout(this);
        linear_4.setBackgroundColor(Color.YELLOW);
        linear_4.setOrientation(LinearLayout.VERTICAL);

        LinearLayout linear_5 = new LinearLayout(this);
        linear_5.setBackgroundColor(Color.YELLOW);
        linear_5.setOrientation(LinearLayout.VERTICAL);

        LinearLayout linear_6 = new LinearLayout(this);
        linear_6.setBackgroundColor(Color.YELLOW);
        linear_6.setOrientation(LinearLayout.VERTICAL);

        LinearLayout.LayoutParams parametros_pegado_linears =
                new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 0);
        parametros_pegado_linears.weight = 1.0f;
        parametros_pegado_linears.setMargins(5, 5, 5, 5);

        linear_derecha.addView(linear_1, parametros_pegado_linears);
        linear_derecha.addView(linear_2, parametros_pegado_linears);
        linear_derecha.addView(linear_3, parametros_pegado_linears);
        linear_derecha.addView(linear_4, parametros_pegado_linears);
        linear_derecha.addView(linear_5, parametros_pegado_linears);
        linear_derecha.addView(linear_6, parametros_pegado_linears);

        LinearLayout.LayoutParams parametros_pegado_componentes_text =
                new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 0);
        parametros_pegado_componentes_text.weight = 1.0f;
        parametros_pegado_componentes_text.setMargins(5, 5, 5, 5);

        LinearLayout.LayoutParams parametros_pegado_componentes_seek =
                new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 0);
        parametros_pegado_componentes_seek.weight = 1.0f;
        parametros_pegado_componentes_seek.setMargins(5, 5, 5, 5);

        linear_1.addView(textRojo, parametros_pegado_componentes_text);
        linear_2.addView(seekBarRojo, parametros_pegado_componentes_seek);

        linear_3.addView(textVerde, parametros_pegado_componentes_text);
        linear_4.addView(seekBarVerde, parametros_pegado_componentes_seek);

        linear_5.addView(textAzul, parametros_pegado_componentes_text);
        linear_6.addView(seekBarAzul, parametros_pegado_componentes_seek);

        linear_izquierda.addView(tablero, parametros_pegado_componentes_text);

        LinearLayout.LayoutParams parametrosPegadoBotonesAbajo =
                new LinearLayout.LayoutParams(0, ViewGroup.LayoutParams.MATCH_PARENT);
        parametrosPegadoBotonesAbajo.weight = 1.0f;
        linearPrincipalAbajo.addView(botonConectar, parametrosPegadoBotonesAbajo);

        LinearLayout linearPrincipalIntermedia = new LinearLayout(this);
        LinearLayout.LayoutParams parametrosPegadoTextView =
                new LinearLayout.LayoutParams(0, ViewGroup.LayoutParams.MATCH_PARENT);
        parametrosPegadoTextView.weight = 1.0f;
        linearPrincipalIntermedia.addView(textviewAviso, parametrosPegadoTextView);

        LinearLayout.LayoutParams parametrosPegadoArriba =
                new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 0);
        parametrosPegadoArriba.weight = 8.6f;

        LinearLayout.LayoutParams parametrosPegadoIntermedia =
                new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 0);
        parametrosPegadoIntermedia.weight = 0.4f;

        LinearLayout.LayoutParams parametrosPegadoAbajo =
                new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 0);
        parametrosPegadoAbajo.weight = 1.0f;

        linearPrincipal.addView(linearPrincipalArriba, parametrosPegadoArriba);
        linearPrincipal.addView(linearPrincipalIntermedia, parametrosPegadoIntermedia);
        linearPrincipal.addView(linearPrincipalAbajo, parametrosPegadoAbajo);

        return linearPrincipal;
    }

    private void eventos() {

        botonConectar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String textoBoton = String.valueOf(botonConectar.getText());

                if ("CONECTAR".equals(textoBoton)) {
                    botonConectar.setText("EMPEZAR");

                    if (cliente != null) {
                        cliente.conectar();
                    }

                    AlmacenDatosRAM.estado_conexion_nube = 2;
                    actualizarAviso();

                } else if ("EMPEZAR".equals(textoBoton)) {

                    if (hilo == null || !hilo.isAlive()) {
                        ejecutando = true;
                        hilo = new Thread(ActividadComoClientePubMQTT.this);
                        hilo.start();
                    }

                    botonConectar.setEnabled(false);
                    AlmacenDatosRAM.estado_conexion_nube = 4;
                    actualizarAviso();
                }
            }
        });

        seekBarRojo.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                rojo = progress;
                tablero.setColoRojo(rojo);
            }

            @Override public void onStartTrackingTouch(SeekBar seekBar) { }
            @Override public void onStopTrackingTouch(SeekBar seekBar) { }
        });

        seekBarVerde.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                verde = progress;
                tablero.setColoVerde(verde);
            }

            @Override public void onStartTrackingTouch(SeekBar seekBar) { }
            @Override public void onStopTrackingTouch(SeekBar seekBar) { }
        });

        seekBarAzul.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                azul = progress;
                tablero.setColoAzul(azul);
            }

            @Override public void onStartTrackingTouch(SeekBar seekBar) { }
            @Override public void onStopTrackingTouch(SeekBar seekBar) { }
        });
    }

    public void crearCliente() {
        cliente = new ClientePubSubMQTT(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        AlmacenDatosRAM.estado_conexion_nube = 1;
        actualizarAviso();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ejecutando = false;
        if (hilo != null) {
            hilo.interrupt();
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            DialogoSalir dialogo_salir = new DialogoSalir(this);
            dialogo_salir.mostrarPopMenuCoeficientes();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    public void run() {
        while (ejecutando && !Thread.currentThread().isInterrupted()) {
            try {
                Thread.sleep(200);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                break;
            }

            escribir();
        }
    }

    private void escribir() {
        String dato = getStringJSON();

        if (cliente != null && dato != null && !dato.isEmpty()) {
            cliente.setEnviarMensajes(dato.getBytes());
            AlmacenDatosRAM.estado_conexion_nube = 4;
        } else {
            AlmacenDatosRAM.estado_conexion_nube = 3;
        }

        actualizarAviso();
    }

    private String getStringJSON() {
        JSONObject obj = new JSONObject();

        try {
            obj.put("r", rojo);
            obj.put("g", verde);
            obj.put("b", azul);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return obj.toString();
    }

    private void actualizarAviso() {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if (AlmacenDatosRAM.estado_conexion_nube == 1) {
                    AlmacenDatosRAM.conectado_PubSub = "Hacer clic en CONECTAR para acceder al BROKER...";
                } else if (AlmacenDatosRAM.estado_conexion_nube == 2) {
                    AlmacenDatosRAM.conectado_PubSub = "Hacer clic en EMPEZAR para publicar datos del BROKER...";
                } else if (AlmacenDatosRAM.estado_conexion_nube == 3) {
                    AlmacenDatosRAM.conectado_PubSub = "No se están enviando datos ...";
                } else if (AlmacenDatosRAM.estado_conexion_nube == 4) {
                    AlmacenDatosRAM.conectado_PubSub = "Enviando datos ...";
                }

                textviewAviso.setText(AlmacenDatosRAM.conectado_PubSub);
            }
        });
    }
}