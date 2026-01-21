package com.curso_simulaciones.minovenaapp;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.curso_simulaciones.minovenaapp.objetos_laboratorio.Rueda;
import com.curso_simulaciones.minovenaapp.vista.CR;
import com.curso_simulaciones.minovenaapp.vista.Pizarra;

/**
 * Actividad principal de MiNovenaApp
 *
 * Esta aplicación despliega 4 ruedas con diferentes comportamientos:
 * - Rueda 1: Esquina inferior derecha, rotando
 * - Rueda 2: Centro de la pizarra, rotando
 * - Rueda 3: 25% de altura, solo trasladándose
 * - Rueda 4: 75% de altura, trasladándose y rotando
 *
 * Todas las ruedas se despliegan con RESPONSIVIDAD
 */
public class ActividadPrincipalMiNovenaApp extends Activity implements Runnable {

    // Pizarra para dibujar
    private Pizarra pizarra;

    // Arreglo que contiene las 4 ruedas
    private Rueda ruedas[] = new Rueda[4];

    // Período de muestreo en milisegundos (50ms = 20 fps)
    private long periodo_muestreo = 50;

    // Variable de tiempo para las animaciones
    private float tiempo;

    // Hilo responsable de controlar la animación
    private Thread hilo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Llamada al método para crear los elementos de la GUI
        crearElementosGui();

        // Configurar cómo se debe adaptar la GUI a la pantalla
        ViewGroup.LayoutParams parametro_layout_principal =
                new ViewGroup.LayoutParams(
                        ViewGroup.LayoutParams.MATCH_PARENT,
                        ViewGroup.LayoutParams.MATCH_PARENT
                );

        // Pegar al contenedor la GUI
        this.setContentView(crearGui(), parametro_layout_principal);

        // Iniciar hilo que administra la animación
        hilo = new Thread(this);
        hilo.start();
    }

    /**
     * Crea los objetos de la interfaz gráfica de usuario (GUI)
     */
    private void crearElementosGui() {
        // Crear pizarra
        pizarra = new Pizarra(this);
        pizarra.setBackgroundColor(Color.rgb(230, 230, 230));  // Gris claro
    }

    /**
     * Organiza la distribución de los objetos de la GUI
     * usando administradores de diseño
     */
    private LinearLayout crearGui() {
        // Linear principal
        LinearLayout linearPrincipal = new LinearLayout(this);
        linearPrincipal.setOrientation(LinearLayout.VERTICAL);
        linearPrincipal.setBackgroundColor(Color.BLACK);
        linearPrincipal.setWeightSum(10.0f);

        // Linear secundario arriba (contendrá la pizarra)
        LinearLayout linearArriba = new LinearLayout(this);

        // Linear secundario abajo (espacio inferior)
        LinearLayout linearAbajo = new LinearLayout(this);
        linearAbajo.setBackgroundColor(Color.rgb(50, 50, 50));  // Gris oscuro

        // Pegar linearArriba al principal
        LinearLayout.LayoutParams parametrosPegadoArriba =
                new LinearLayout.LayoutParams(
                        ViewGroup.LayoutParams.MATCH_PARENT, 0
                );
        parametrosPegadoArriba.weight = 8.5f;
        parametrosPegadoArriba.setMargins(50, 50, 50, 50);
        linearPrincipal.addView(linearArriba, parametrosPegadoArriba);

        // Pegar linearAbajo al principal
        LinearLayout.LayoutParams parametrosPegadoAbajo =
                new LinearLayout.LayoutParams(
                        ViewGroup.LayoutParams.MATCH_PARENT, 0
                );
        parametrosPegadoAbajo.weight = 1.5f;
        linearPrincipal.addView(linearAbajo, parametrosPegadoAbajo);

        // Pegar pizarra a linearArriba
        linearArriba.addView(pizarra);

        return linearPrincipal;
    }

    /**
     * Método run() implementado de la interface Runnable
     * Controla la animación de las ruedas
     */
    @Override
    public void run() {

        boolean ON = true;

        // Hilo infinito para la animación
        while (true) {

            try {
                Thread.sleep(periodo_muestreo);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            // Crear las ruedas con responsividad SOLO cuando se garantice
            // que la GUI se conformó completamente y las dimensiones de la
            // pizarra NO SEAN NULAS
            if (pizarra.getWidth() != 0 && ON == true) {
                crearRuedasConResponsividad();
                ON = false;
            }

            // Ya creadas las ruedas con responsividad, hacer efectiva la animación
            if (ON == false) {
                tiempo = tiempo + 0.05f;
                // Cambio de estado de la escena física en la pizarra
                cambiarEstadosEscenaPizarra(tiempo);
            }
        }
    }

    /**
     * Crea las ruedas con responsividad una vez que la pizarra
     * tiene dimensiones definidas
     */
    private void crearRuedasConResponsividad() {
        CR.anchoPizarra = pizarra.getWidth();
        CR.altoPizarra = pizarra.getHeight();
        crearObjetosLaboratorio();
    }

    /**
     * Crea los objetos Rueda con su estado inicial
     * Todas las posiciones y tamaños se definen de forma RESPONSIVA
     */
    private void crearObjetosLaboratorio() {

        // Definir radios diferentes para cada rueda (en porcentaje)
        float radio1 = CR.pcApxL(8);   // 8% de la menor dimensión
        float radio2 = CR.pcApxL(12);  // 12% (rueda central más grande)
        float radio3 = CR.pcApxL(7);   // 7%
        float radio4 = CR.pcApxL(10);  // 10%

        // Variables auxiliares para posiciones
        float x_c, y_c;

        // RUEDA 1: Esquina inferior derecha, rotando
        x_c = CR.pcApxX(100);  // 100% del ancho (derecha)
        y_c = CR.pcApxY(100);  // 100% del alto (abajo)
        ruedas[0] = new Rueda(x_c, y_c, radio1);
        ruedas[0].setColorDisco(Color.rgb(200, 50, 50));  // Rojo oscuro

        // RUEDA 2: Centro de la pizarra, rotando
        x_c = CR.pcApxX(50);   // 50% del ancho (centro)
        y_c = CR.pcApxY(50);   // 50% del alto (centro)
        ruedas[1] = new Rueda(x_c, y_c, radio2);
        ruedas[1].setColorDisco(Color.rgb(50, 100, 200));  // Azul

        // RUEDA 3: 25% de altura, solo trasladándose
        x_c = CR.pcApxX(0);    // Comienza en el lado izquierdo
        y_c = CR.pcApxY(25);   // 25% de la altura
        ruedas[2] = new Rueda(x_c, y_c, radio3);
        ruedas[2].setColorDisco(Color.rgb(50, 200, 50));  // Verde

        // RUEDA 4: 75% de altura, trasladándose y rotando
        x_c = CR.pcApxX(0);    // Comienza en el lado izquierdo
        y_c = CR.pcApxY(75);   // 75% de la altura
        ruedas[3] = new Rueda(x_c, y_c, radio4);
        ruedas[3].setColorDisco(Color.rgb(200, 150, 50));  // Naranja/Amarillo

        // Actualizar escena en la pizarra
        pizarra.setEstadoEscena(ruedas);
    }

    /**
     * Cambia el estado de los objetos Rueda y lo comunica a la pizarra
     * Implementa los modelos cinemáticos de cada rueda
     */
    private void cambiarEstadosEscenaPizarra(float tiempo) {

        // RUEDA 1: Rota con velocidad angular W = 80 grados/unidad tiempo
        // Solo rotación, sin traslación
        float teta_1 = 80f * tiempo;  // Ecuación MCU (Movimiento Circular Uniforme)
        ruedas[0].moverRueda(teta_1);

        // RUEDA 2: Rota con velocidad angular W = 150 grados/unidad tiempo
        // Solo rotación, sin traslación
        float teta_2 = 150f * tiempo;  // Ecuación MCU
        ruedas[1].moverRueda(teta_2);

        // RUEDA 3: Se traslada con velocidad Vx = 8% del ancho por unidad tiempo
        // Solo traslación, sin rotación (Vy = 0)
        float desplazamiento_3_x = CR.pcApxX(8 * tiempo);  // Ecuación MU (Movimiento Uniforme)
        float desplazamiento_3_y = 0;
        ruedas[2].moverRueda(desplazamiento_3_x, desplazamiento_3_y);

        // RUEDA 4: Se traslada con velocidad Vx = 6% del ancho por unidad tiempo
        // Y rota con W = 200 grados/unidad tiempo
        float desplazamiento_4_x = CR.pcApxX(6 * tiempo);  // Ecuación MU
        float desplazamiento_4_y = 0;
        float teta_4 = 200f * tiempo;  // Ecuación MCU
        ruedas[3].moverRueda(desplazamiento_4_x, desplazamiento_4_y, teta_4);
    }
}