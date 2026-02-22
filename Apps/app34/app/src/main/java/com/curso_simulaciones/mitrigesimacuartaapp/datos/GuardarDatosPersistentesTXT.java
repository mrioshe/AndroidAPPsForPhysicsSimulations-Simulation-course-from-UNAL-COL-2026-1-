package com.curso_simulaciones.mitrigesimacuartaapp.datos;

import android.app.Activity;
import android.os.Environment;
import android.widget.Toast;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Vector;

public class GuardarDatosPersistentesTXT {
    private Vector almacenNumeroDato = new Vector();
    private Vector almacenTiempo = new Vector();
    private Vector almacenAceleracionX = new Vector();
    private Vector almacenAceleracionY = new Vector();
    private Vector almacenAceleracionZ = new Vector();
    private Vector almacenAceleracionTotal = new Vector();

    public GuardarDatosPersistentesTXT() {
    }

    /**
     * Para recibir los datos que se grabarán en archivo .txt
     * Serán 6 columnas: # dato, tiempo, ax, ay, az, a
     */
    public void llenarDatos(int numeroDato, double tiempo, double ax, double ay, double az, double a) {
        almacenNumeroDato.addElement(numeroDato);
        almacenTiempo.addElement(tiempo);
        almacenAceleracionX.addElement(ax);
        almacenAceleracionY.addElement(ay);
        almacenAceleracionZ.addElement(az);
        almacenAceleracionTotal.addElement(a);
    }

    /**
     * Para borrar los datos. Estos no serán guardados en el archivo .txt.
     */
    public void borrarDatos() {
        almacenNumeroDato.removeAllElements();
        almacenTiempo.removeAllElements();
        almacenAceleracionX.removeAllElements();
        almacenAceleracionY.removeAllElements();
        almacenAceleracionZ.removeAllElements();
        almacenAceleracionTotal.removeAllElements();
    }

    /**
     * Para guardar los datos en formato .txt
     */
    public void guardar(Activity actividad, String carpeta) {
        Date date = new Date();
        DateFormat horaFecha = new SimpleDateFormat("yy-MM-dd_HH-mm-ss");

        try {
            String marca = horaFecha.format(date).toString();
            String nombreArchivo = "datos_acelerometro_" + marca + ".txt";

            File file = null;
            File path = null;

            if (android.os.Build.VERSION.SDK_INT < android.os.Build.VERSION_CODES.Q) {
                // Versiones inferiores a Android 10
                path = new File(Environment.getExternalStorageDirectory(), carpeta);
            } else {
                // Versiones desde Android 10 en adelante
                path = new File(actividad.getExternalFilesDir(null), carpeta);
            }

            file = new File(path, nombreArchivo);
            FileOutputStream flujoSalida = new FileOutputStream(file);
            OutputStreamWriter escritor = new OutputStreamWriter(flujoSalida);

            // Escribir encabezados
            escritor.write("# Dato\t\tTiempo(s)\t\tax(m/s²)\t\tay(m/s²)\t\taz(m/s²)\t\ta(m/s²)\r\n");
            escritor.write("======\t\t=========\t\t========\t\t========\t\t========\t\t=======\r\n");

            // Escribir datos
            for (int i = 0; i < almacenNumeroDato.size(); i++) {
                int numeroDato = ((Integer) (almacenNumeroDato.get(i))).intValue();
                double tiempo = ((Double) (almacenTiempo.get(i))).doubleValue();
                double ax = ((Double) (almacenAceleracionX.get(i))).doubleValue();
                double ay = ((Double) (almacenAceleracionY.get(i))).doubleValue();
                double az = ((Double) (almacenAceleracionZ.get(i))).doubleValue();
                double a = ((Double) (almacenAceleracionTotal.get(i))).doubleValue();

                // Formatear con dos decimales
                float tiempoDosDecimales = (float) (Math.round(tiempo * 100) / 100f);
                float axDosDecimales = (float) (Math.round(ax * 100) / 100f);
                float ayDosDecimales = (float) (Math.round(ay * 100) / 100f);
                float azDosDecimales = (float) (Math.round(az * 100) / 100f);
                float aDosDecimales = (float) (Math.round(a * 100) / 100f);

                escritor.write(numeroDato + "\t\t");
                escritor.write(tiempoDosDecimales + "\t\t");
                escritor.write(axDosDecimales + "\t\t");
                escritor.write(ayDosDecimales + "\t\t");
                escritor.write(azDosDecimales + "\t\t");
                escritor.write(aDosDecimales + "\r\n");
            }

            escritor.flush();
            escritor.close();

            String aviso = "Los datos fueron guardados en la carpeta:\n" +
                    "Mis archivos/" + carpeta;
            Toast.makeText(actividad, aviso, Toast.LENGTH_LONG).show();

        } catch (IOException ex) {
            ex.printStackTrace();
            Toast.makeText(actividad, "Error al guardar el archivo", Toast.LENGTH_SHORT).show();
        }
    }
}