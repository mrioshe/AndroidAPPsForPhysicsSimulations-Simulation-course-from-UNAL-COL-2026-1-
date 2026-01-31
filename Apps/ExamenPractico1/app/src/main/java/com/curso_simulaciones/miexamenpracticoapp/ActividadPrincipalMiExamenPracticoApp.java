package com.curso_simulaciones.miexamenpracticoapp;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.curso_simulaciones.miexamenpracticoapp.objetos_laboratorio.Dibujable;
import com.curso_simulaciones.miexamenpracticoapp.objetos_laboratorio.Rodillo;
import com.curso_simulaciones.miexamenpracticoapp.vista.CR;
import com.curso_simulaciones.miexamenpracticoapp.vista.Pizarra;

/**
 * ActividadPrincipalMiExamenPracticoApp
 * Actividad principal que despliega el movimiento de al menos 3 objetos tipo Rodillo
 * cumpliendo con los tres comportamientos polimórficos solicitados:
 * 1. mover rotar (solo rotación)
 * 2. mover trasladar (solo traslación)
 * 3. trasladar y rotar (traslación + rotación)
 *
 * La rotación se realiza alrededor de un eje que pasa por el centroide
 */
public class ActividadPrincipalMiExamenPracticoApp extends Activity implements Runnable {

    // Pizarra para dibujar
    private Pizarra pizarra;

    // Objetos Rodillo para Pizarra
    private Rodillo rodillo1; // Comportamiento: solo TRASLACIÓN (mover trasladar)
    private Rodillo rodillo2; // Comportamiento: solo ROTACIÓN (mover rotar)
    private Rodillo rodillo3; // Comportamiento: TRASLACIÓN + ROTACIÓN (trasladar y rotar)

    private Dibujable[] objetosDibujables = new Dibujable[10];

    // Período de muestreo en milisegundos
    private long periodoMuestreo = 50;
    private float tiempo;

    // Hilo responsable de controlar la animación
    private Thread hilo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        /*
         * Llamada al método para crear los elementos de la
         * interfaz gráfica de usuario (GUI)
         */
        crearElementosGui();

        /*
         * Para informar cómo se debe adaptar la GUI a la pantalla del dispositivo
         */
        ViewGroup.LayoutParams parametroLayoutPrincipal =
                new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                        ViewGroup.LayoutParams.MATCH_PARENT);

        /*
         * Pegar al contenedor la GUI:
         * en el argumento se está llamando al método crearGui()
         */
        this.setContentView(crearGui(), parametroLayoutPrincipal);

        /*
         * Los rodillos con responsividad se crearán dentro
         * del hilo con el fin de garantizar que las dimensiones
         * de la pizarra donde se desplegarán con responsividad
         * tengan ya dimensiones no nulas
         */
        // Hilo que administra la animación
        hilo = new Thread(this);
        hilo.start();
    }

    /**
     * Crear los objetos de la interfaz gráfica de usuario (GUI)
     */
    private void crearElementosGui() {
        // Crear pizarra
        pizarra = new Pizarra(this);
        pizarra.setBackgroundColor(Color.WHITE);
    }

    /**
     * Organizar la distribución de los objetos de la GUI usando administradores de diseño
     * @return LinearLayout con la GUI organizada
     */
    private LinearLayout crearGui() {
        // El LinearLayout principal
        LinearLayout linearPrincipal = new LinearLayout(this);
        linearPrincipal.setOrientation(LinearLayout.VERTICAL);
        linearPrincipal.setBackgroundColor(Color.BLACK);
        linearPrincipal.setWeightSum(10.0f);

        // LinearLayout secundario arriba (para la pizarra)
        LinearLayout linearArriba = new LinearLayout(this);

        // LinearLayout secundario abajo (área informativa)
        LinearLayout linearAbajo = new LinearLayout(this);
        linearAbajo.setBackgroundColor(Color.YELLOW);

        // Pegar linearArriba al principal
        LinearLayout.LayoutParams parametrosPegadoArriba =
                new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 0);
        parametrosPegadoArriba.weight = 8.5f;
        parametrosPegadoArriba.setMargins(50, 50, 50, 50);
        linearPrincipal.addView(linearArriba, parametrosPegadoArriba);

        // Pegar linearAbajo al principal
        LinearLayout.LayoutParams parametrosPegadoAbajo =
                new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 0);
        parametrosPegadoAbajo.weight = 1.5f;
        linearPrincipal.addView(linearAbajo, parametrosPegadoAbajo);

        // Pegar pizarra a linearArriba
        linearArriba.addView(pizarra);

        return linearPrincipal;
    }

    @Override
    public void run() {
        boolean ON = true;

        // Hilo sin fin
        while (true) {
            try {
                Thread.sleep(periodoMuestreo);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            /*
             * Hacer la creación de los rodillos con
             * responsividad SÓLO cuando se garantice que la
             * GUI se conformó completamente con el fin de que
             * las dimensiones de la pizarra NO SEAN NULAS
             */
            if (pizarra.getWidth() != 0 && ON == true) {
                crearObjetosConResponsividad();
                ON = false;
            }

            // Ya creados los rodillos con responsividad, hacer efectiva la animación
            if (ON == false) {
                tiempo = tiempo + 0.05f;
                // Cambio de estado de la escena física en la pizarra
                cambiarEstadosEscenaPizarra(tiempo);
            }
        }
    }

    /**
     * Crear objetos con responsividad una vez que se conocen las dimensiones de la pizarra
     */
    private void crearObjetosConResponsividad() {
        CR.anchoPizarra = pizarra.getWidth();
        CR.altoPizarra = pizarra.getHeight();
        crearObjetosLaboratorio();
    }

    /**
     * Crea los objetos Rodillo con su estado inicial
     * - X está en porcentaje del ancho del canvas
     * - Y está en porcentaje del alto del canvas
     * - Cualquier otra dimensión está en porcentaje del menor
     *   entre el alto y el ancho del canvas
     *
     * Cada rodillo tiene diferente color y tamaño según especificación del examen
     */
    private void crearObjetosLaboratorio() {

        // ========== RODILLO 1: SOLO TRASLACIÓN (mover trasladar) ==========
        // Movimiento Uniforme (MU) - solo traslación horizontal
        // Posición inicial en el lado izquierdo superior
        float x_1 = CR.pcApxX(15);
        float y_1 = CR.pcApxY(25);
        float radio_1 = CR.pcApxL(10);  // Tamaño pequeño
        rodillo1 = new Rodillo(x_1, y_1, radio_1);
        // Color ROJO (por defecto)
        rodillo1.setColorCirculoExterior(Color.RED);
        rodillo1.setColorAnilloInterno(Color.rgb(255, 165, 0)); // Naranja
        // Agregar al arreglo de objetos dibujables
        objetosDibujables[0] = rodillo1;

        // ========== RODILLO 2: SOLO ROTACIÓN (mover rotar) ==========
        // Movimiento Circular Uniforme (MCU) - solo rotación alrededor de su centro
        // Posición fija en el centro de la pizarra
        float x_2 = CR.pcApxX(50);
        float y_2 = CR.pcApxY(50);
        float radio_2 = CR.pcApxL(15);  // Tamaño mediano
        rodillo2 = new Rodillo(x_2, y_2, radio_2);
        // Color AZUL (diferente al rodillo 1)
        rodillo2.setColorCirculoExterior(Color.BLUE);
        rodillo2.setColorAnilloInterno(Color.CYAN);
        rodillo2.setColorDiscosYLineas(Color.rgb(173, 216, 230)); // Azul claro
        // Agregar al arreglo de objetos dibujables
        objetosDibujables[1] = rodillo2;

        // ========== RODILLO 3: TRASLACIÓN + ROTACIÓN (trasladar y rotar) ==========
        // MU + MCU - traslación y rotación simultáneas
        // Posición inicial en el lado izquierdo inferior
        float x_3 = CR.pcApxX(15);
        float y_3 = CR.pcApxY(75);
        float radio_3 = CR.pcApxL(12);  // Tamaño intermedio
        rodillo3 = new Rodillo(x_3, y_3, radio_3);
        // Color VERDE (diferente a rodillos 1 y 2)
        rodillo3.setColorCirculoExterior(Color.GREEN);
        rodillo3.setColorAnilloInterno(Color.rgb(144, 238, 144)); // Verde claro
        rodillo3.setColorDiscosYLineas(Color.rgb(255, 255, 153)); // Amarillo claro
        // Agregar al arreglo de objetos dibujables
        objetosDibujables[2] = rodillo3;

        // Desplegar la escena inicial
        pizarra.setEstadoEscena(objetosDibujables);
    }

    /**
     * Cambia el estado de movimiento de los rodillos
     * Implementa los tres comportamientos polimórficos:
     * 1. Rodillo 1: Solo traslación usando mover(x, y)
     * 2. Rodillo 2: Solo rotación usando mover(angulo)
     * 3. Rodillo 3: Traslación + rotación usando mover(x, y, angulo)
     *
     * - X está en porcentaje del ancho del canvas
     * - Y está en porcentaje del alto del canvas
     * - Cualquier otra dimensión está en porcentaje del menor
     *   entre el alto y el ancho del canvas
     *
     * @param tiempo Tiempo transcurrido para calcular el movimiento
     */
    private void cambiarEstadosEscenaPizarra(float tiempo) {

        // ========== RODILLO 1: MU (solo traslación) ==========
        // Comportamiento: mover trasladar
        // Movimiento horizontal de izquierda a derecha
        float desplazamientoX_1 = CR.pcApxX(2 * tiempo);

        // Usar el método mover(float, float) para SOLO TRASLACIÓN
        rodillo1.mover(desplazamientoX_1, 0);

        // ========== RODILLO 2: MCU (solo rotación) ==========
        // Comportamiento: mover rotar
        // Rotación alrededor de su propio centro (posición fija)
        float teta_2 = 100 * tiempo;  // Velocidad angular

        // Usar el método mover(float) para SOLO ROTACIÓN
        rodillo2.mover(teta_2);

        // ========== RODILLO 3: MU + MCU (traslación + rotación) ==========
        // Comportamiento: trasladar y rotar
        // Movimiento horizontal con rotación simultánea
        float desplazamientoX_3 = CR.pcApxX(2 * tiempo);
        float teta_3 = 100 * tiempo;  // Velocidad angular

        // Usar el método mover(float, float, float) para TRASLACIÓN + ROTACIÓN
        rodillo3.mover(desplazamientoX_3, 0, teta_3);
    }
}