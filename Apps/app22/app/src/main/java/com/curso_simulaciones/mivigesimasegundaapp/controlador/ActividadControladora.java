package com.curso_simulaciones.mivigesimasegundaapp.controlador;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import com.curso_simulaciones.mivigesimasegundaapp.datos.AlmacenDatosRAM;
import com.curso_simulaciones.mivigesimasegundaapp.vista.CR;
import com.curso_simulaciones.mivigesimasegundaapp.vista.Pizarra;
import com.curso_simulaciones.simulphysics.objetos_laboratorio.CuerpoRectangular;
import com.curso_simulaciones.simulphysics.objetos_laboratorio.Cuerda;
import com.curso_simulaciones.simulphysics.objetos_laboratorio.Marca;
import com.curso_simulaciones.simulphysics.objetos_laboratorio.Masa;
import com.curso_simulaciones.simulphysics.objetos_laboratorio.ObjetoLaboratorio;
import com.curso_simulaciones.simulphysics.objetos_laboratorio.Polea;

public class ActividadControladora extends Activity {

    private Pizarra pizarra;

    private Masa masa_1, masa_2, masa_3;
    private Polea polea_azul_izq, polea_azul_der, polea_verde_P;
    private CuerpoRectangular cuerpo_amarillo, base_oscura;
    private Cuerda cuerda_horizontal, cuerda_m1, cuerda_vertical_a_P, cuerda_m2, cuerda_m3;
    private Marca marca_P, marca_m1, marca_m2, marca_m3;

    private ObjetoLaboratorio[] objetos = new ObjetoLaboratorio[20];

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

    }//fin onCreate

    /*Método auxiliar para asuntos de resolución*/
    private void gestionarResolucion() {

        /*
        Según el diseño de la GUI se puede anticipar cuál es la
        dimensión de la pizarra. En este caso es el 100% del ancho
        de la pantalla y el 100% del alto de la misma
        */
        CR.anchoPizarra = AlmacenDatosRAM.ancho_pantalla;
        CR.altoPizarra = AlmacenDatosRAM.alto_pantalla;

    }

    /*método responsable de la creación de los elementos de la GUI*/
    private void crearElementosGUI() {

        //crear pizarra sabiendo de antemano sus dimensiones
        pizarra = new Pizarra(this);
        pizarra.setBackgroundColor(Color.WHITE);

        crearObjetosLaboratorio();

    }//fin crearElementosGUI

    /*método responsable de administrar el diseño de la GUI*/
    private LinearLayout crearGUI() {

        //el linear principal
        LinearLayout linear_principal = new LinearLayout(this);
        linear_principal.setOrientation(LinearLayout.VERTICAL);

        //pegar pizarra a linearArriba
        linear_principal.addView(pizarra);

        return linear_principal;

    }//fin crearGUI

    /*
     Crea los objetos cuerpo rígido con su estado inicial
     -X esta en porcentaje del ancho del canvas
     -Y está en porcentaje del alto del canvas
     -Cualquier otra dimensión está en porcentaje del menor
      entre el alto y el ancho del canvas
     */
    private void crearObjetosLaboratorio() {

        /*
          Coordenadas de puntos básicos y
          dimensiones de los elementos
         */

        // Dimensiones del rectángulo amarillo vertical
        float ancho_rectangulo = CR.pcApxL(45);
        float alto_rectangulo = CR.pcApxL(55);

        // Posición del rectángulo amarillo (centrado)
        float x_rectangulo = CR.pcApxX(50);
        float y_rectangulo = CR.pcApxY(45);

        // Radio de las poleas azules
        float radio = CR.pcApxL(5);

        // Radio de la polea verde P (mitad del tamaño de las azules)
        float radio_verde = radio / 2f;

        // Coordenadas de las esquinas superiores del rectángulo
        float esquina_sup_izq_x = x_rectangulo - 0.5f * ancho_rectangulo;
        float esquina_sup_izq_y = y_rectangulo - 0.5f * alto_rectangulo;
        float esquina_sup_der_x = x_rectangulo + 0.5f * ancho_rectangulo;
        float esquina_sup_der_y = y_rectangulo - 0.5f * alto_rectangulo;

        // Poleas azules en las esquinas superiores
        // Sus centros están desplazados para que el borde toque tangencialmente la esquina
        float xp_azul_izq = esquina_sup_izq_x - radio;
        float yp_azul_izq = esquina_sup_izq_y - radio;

        float xp_azul_der = esquina_sup_der_x + radio;
        float yp_azul_der = esquina_sup_der_y - radio;

        // Polea verde P en la columna derecha, debajo de la polea azul derecha
        float xp_verde = xp_azul_der + radio;
        float yp_verde = y_rectangulo + CR.pcApxL(5);

        // Dimensiones de las masas - aún más anchas (mitad del anterior)
        float ancho_masa = radio / 2f;  // Mitad del anterior (era radio)
        float alto_masa = radio;

        // Posiciones de las masas
        // m1 cuelga del lado izquierdo de la polea azul izquierda
        float x_m1 = xp_azul_izq - radio;
        float y_m1 = yp_azul_izq + 5 * radio;

        // m2 cuelga verticalmente de la polea verde P
        float x_m2 = xp_verde-radio_verde;
        float y_m2 = yp_verde + 2 * radio;

        // m3 cuelga verticalmente de la polea verde P
        float x_m3 = xp_verde+radio_verde;
        float y_m3 = yp_verde + 4 * radio;

        /*
        Creación de objetos físicos y dibujo del
        estado inicial de la escena física
       */

        // Base oscura unida al rectángulo amarillo
        float base_y = y_rectangulo + 0.5f * alto_rectangulo + CR.pcApxL(4);
        base_oscura = new CuerpoRectangular(CR.pcApxX(50), base_y,
                CR.pcApxL(60), CR.pcApxL(8));
        base_oscura.setColor(Color.rgb(10, 50, 10));
        objetos[0] = base_oscura;

        // Cuerpo rectangular amarillo vertical
        cuerpo_amarillo = new CuerpoRectangular(x_rectangulo, y_rectangulo,
                ancho_rectangulo, alto_rectangulo);
        cuerpo_amarillo.setColor(Color.YELLOW);
        cuerpo_amarillo.setGrosorLinea(CR.pcApxL(0.5f));
        objetos[1] = cuerpo_amarillo;

        // Polea azul izquierda - rotar 135° antihorario respecto a su centroide
        polea_azul_izq = new Polea(xp_azul_izq, yp_azul_izq, radio);
        polea_azul_izq.setColor(Color.BLUE);
        polea_azul_izq.setGrosorLinea(CR.pcApxL(0.5f));
        polea_azul_izq.setSoportePolea(true);
        polea_azul_izq.rotarEje(-45);
        polea_azul_izq.rotar(xp_azul_izq, yp_azul_izq, 0);
        objetos[2] = polea_azul_izq;

        // Polea azul derecha - rotar 45° antihorario respecto a su centroide
        polea_azul_der = new Polea(xp_azul_der, yp_azul_der, radio);
        polea_azul_der.setColor(Color.BLUE);
        polea_azul_der.setGrosorLinea(CR.pcApxL(0.5f));
        polea_azul_der.setSoportePolea(true);
        polea_azul_der.rotarEje(45);
        polea_azul_der.rotar(xp_azul_der, yp_azul_der, 45);
        objetos[3] = polea_azul_der;

        // Polea verde P - mitad de tamaño
        polea_verde_P = new Polea(xp_verde, yp_verde, radio_verde);
        polea_verde_P.setColor(Color.rgb(0, 200, 0));
        polea_verde_P.setGrosorLinea(CR.pcApxL(0.5f));
        objetos[4] = polea_verde_P;

        // Cuerda horizontal tangente superior entre poleas azules - prolongada un radio a cada lado
        float cuerda_sup_y = yp_azul_izq - radio;
        cuerda_horizontal = new Cuerda(xp_azul_izq, cuerda_sup_y,
                xp_azul_der, cuerda_sup_y);
        cuerda_horizontal.setColor(Color.RED);
        cuerda_horizontal.setGrosorLinea(CR.pcApxL(0.5f));
        objetos[5] = cuerda_horizontal;

        // Cuerda que sostiene m1 (desde polea azul izquierda hacia abajo)
        cuerda_m1 = new Cuerda(xp_azul_izq - radio, yp_azul_izq,
                x_m1, y_m1 - 0.5f * alto_masa);
        cuerda_m1.setColor(Color.RED);
        cuerda_m1.setGrosorLinea(CR.pcApxL(0.5f));
        objetos[6] = cuerda_m1;

        // Cuerda vertical desde polea azul derecha hasta polea verde P
        cuerda_vertical_a_P = new Cuerda(xp_azul_der+radio, yp_azul_der ,
                xp_verde, yp_verde - radio_verde);
        cuerda_vertical_a_P.setColor(Color.RED);
        cuerda_vertical_a_P.setGrosorLinea(CR.pcApxL(0.5f));
        objetos[7] = cuerda_vertical_a_P;

        // Cuerda verde tangente izquierda que sostiene m2
        cuerda_m2 = new Cuerda(xp_verde - radio_verde, yp_verde,
                x_m2, y_m2 - 0.5f * alto_masa);
        cuerda_m2.setColor(Color.RED);
        cuerda_m2.setGrosorLinea(CR.pcApxL(0.5f));
        objetos[8] = cuerda_m2;

        // Cuerda verde tangente derecha que sostiene m3
        cuerda_m3 = new Cuerda(xp_verde + radio_verde, yp_verde,
                x_m3, y_m3 - 0.5f * alto_masa);
        cuerda_m3.setColor(Color.RED);
        cuerda_m3.setGrosorLinea(CR.pcApxL(0.5f));
        objetos[9] = cuerda_m3;

        // Masa m1 sin etiqueta interna
        masa_1 = new Masa(x_m1, y_m1, ancho_masa, alto_masa);
        masa_1.setColor(Color.rgb(0, 180, 0));
        objetos[10] = masa_1;

        // Masa m2 sin etiqueta interna
        masa_2 = new Masa(x_m2, y_m2, ancho_masa, alto_masa);
        masa_2.setColor(Color.rgb(0, 180, 0));
        objetos[11] = masa_2;

        // Masa m3 sin etiqueta interna
        masa_3 = new Masa(x_m3, y_m3, ancho_masa, alto_masa);
        masa_3.setColor(Color.rgb(0, 180, 0));
        objetos[12] = masa_3;

        // Marca P para la polea verde
        marca_P = new Marca("P", xp_verde + 2.5f * radio_verde, yp_verde);
        marca_P.setColor(Color.BLACK);
        marca_P.setTamano(CR.pcApxL(4f));
        objetos[13] = marca_P;

        // Marca m1
        marca_m1 = new Marca("m₁", x_m1 - 1.5f * radio, y_m1);
        marca_m1.setColor(Color.BLACK);
        marca_m1.setTamano(CR.pcApxL(3f));
        objetos[14] = marca_m1;

        // Marca m2
        marca_m2 = new Marca("m₂", x_m2 - 1.25f * radio, y_m2);
        marca_m2.setColor(Color.BLACK);
        marca_m2.setTamano(CR.pcApxL(3f));
        objetos[15] = marca_m2;

        // Marca m3
        marca_m3 = new Marca("m₃", x_m3 + 0.5f * radio, y_m3);
        marca_m3.setColor(Color.BLACK);
        marca_m3.setTamano(CR.pcApxL(3f));
        objetos[16] = marca_m3;

        //desplegar la escena inicial
        pizarra.setEstadoEscena(objetos);

    }

}