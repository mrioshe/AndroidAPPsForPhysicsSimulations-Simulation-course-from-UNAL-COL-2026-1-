package com.curso_simulaciones.mitrigesimasextaapp.comunicaciones;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.util.Log;

import com.curso_simulaciones.mitrigesimasextaapp.datos.AlmacenDatosRAM;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.IOException;
import java.util.UUID;

/**
 * Clase que implementa la funcionalidad de cliente Bluetooth.
 * Gestiona conexión, lectura y escritura de datos sobre RFCOMM/SPP.
 *
 * Protocolo cliente:
 *  1. Abrir BluetoothSocket hacia la dirección MAC del servidor
 *  2. Establecer conexión
 *  3. Asociar flujos de E/S
 *  4. Leer / escribir
 *  6. Cerrar flujos y socket
 */
public class ClienteBluetooth {

    private static final String TAG = "ClienteBluetooth";

    // UUID estándar Serial Port Profile (SPP) — RFCOMM
    public static final UUID UUID_SPP =
            UUID.fromString("00001101-0000-1000-8000-00805F9B34FB");

    public BluetoothAdapter adaptadorBluetooth;
    public BluetoothDevice  dispositivo;
    public BluetoothSocket  clienteSocket;

    private BufferedInputStream  flujoEntrada;
    private BufferedOutputStream flujoSalida;
    private String datoString;

    public ClienteBluetooth() { }

    // ── Paso 1 ────────────────────────────────────────────────────────────────
    /** Crea el BluetoothSocket apuntando a la dirección MAC del servidor. */
    public void abrirSocketCliente(String direccion) {
        adaptadorBluetooth = BluetoothAdapter.getDefaultAdapter();
        dispositivo = adaptadorBluetooth.getRemoteDevice(direccion);
        try {
            clienteSocket = dispositivo.createRfcommSocketToServiceRecord(UUID_SPP);
        } catch (IOException e) {
            Log.e(TAG, "Error al crear socket cliente", e);
        }
    }

    // ── Paso 2 ────────────────────────────────────────────────────────────────
    /** Establece la conexión con el servidor (llamada bloqueante). */
    public void conectarSocketCliente() {
        try {
            clienteSocket.connect();
            AlmacenDatosRAM.conexion_bluetooth =
                    " Conectado con " + clienteSocket.getRemoteDevice().getName();
        } catch (IOException e) {
            AlmacenDatosRAM.conexion_bluetooth = " No se pudo conectar...";
            e.printStackTrace();
        }
    }

    // ── Paso 3 ────────────────────────────────────────────────────────────────
    public void abrirFlujoEntrada() {
        if (clienteSocket != null) {
            try {
                flujoEntrada = new BufferedInputStream(clienteSocket.getInputStream());
            } catch (IOException e) { e.printStackTrace(); }
        }
    }

    public void abrirFlujoSalida() {
        if (clienteSocket != null) {
            try {
                flujoSalida = new BufferedOutputStream(clienteSocket.getOutputStream());
            } catch (IOException e) { e.printStackTrace(); }
        }
    }

    // ── Paso 4 ────────────────────────────────────────────────────────────────
    /**
     * Lee el flujo de bytes entrante y devuelve el contenido como String (JSON).
     * Llamada bloqueante; debe ejecutarse en un hilo secundario.
     */
    public String leerString() {
        byte[] buffer = new byte[8 * 1024];
        try {
            if (flujoEntrada != null) {
                int dato = flujoEntrada.read(buffer);
                datoString = new String(buffer, 0, dato);
            }
        } catch (IOException e) {
            Log.d(TAG, "Falla en lectura");
            e.printStackTrace();
        }
        return datoString;
    }

    /** Envía un array de bytes al servidor. */
    public void escribirBytes(byte[] datos) {
        if (datos != null && flujoSalida != null) {
            try {
                flujoSalida.write(datos);
                flujoSalida.flush();
            } catch (IOException e) { e.printStackTrace(); }
        }
    }

    // ── Paso 6 ────────────────────────────────────────────────────────────────
    public void cerrarFlujoEntrada() {
        if (flujoEntrada != null) {
            try { flujoEntrada.close(); } catch (IOException e) { e.printStackTrace(); }
        }
    }

    public void cerrarFlujoSalida() {
        if (flujoSalida != null) {
            try { flujoSalida.flush(); flujoSalida.close(); }
            catch (IOException e) { e.printStackTrace(); }
        }
    }

    public void cerrarSocketCliente() {
        if (clienteSocket != null) {
            try { clienteSocket.close(); } catch (IOException e) { e.printStackTrace(); }
        }
    }
}