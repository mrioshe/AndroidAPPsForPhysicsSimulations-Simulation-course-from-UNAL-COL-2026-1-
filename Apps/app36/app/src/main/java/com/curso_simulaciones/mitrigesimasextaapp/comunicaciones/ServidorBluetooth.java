package com.curso_simulaciones.mitrigesimasextaapp.comunicaciones;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothServerSocket;
import android.bluetooth.BluetoothSocket;

import com.curso_simulaciones.mitrigesimasextaapp.datos.AlmacenDatosRAM;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.IOException;
import java.util.UUID;

/**
 * Clase que implementa la funcionalidad de servidor Bluetooth.
 * Acepta conexiones entrantes y gestiona los flujos de comunicación.
 *
 * Protocolo servidor:
 *  1. Abrir BluetoothServerSocket y esperar conexiones
 *  2. Aceptar conexión → crear BluetoothSocket de comunicación
 *  3. Asociar flujos de E/S
 *  4. Leer / escribir
 *  6. Cerrar flujos y sockets
 */
public class ServidorBluetooth {

    public static final String NOMBRE_SERVICIO = "BluetoothServiceSecure";
    public static final UUID   UUID_SPP =
            UUID.fromString("00001101-0000-1000-8000-00805F9B34FB");

    public BluetoothAdapter      adaptadorBluetooth;
    public BluetoothServerSocket serverSocket;
    public BluetoothSocket       clienteSocket;

    private BufferedInputStream  flujoEntrada;
    private BufferedOutputStream flujoSalida;

    public ServidorBluetooth() {
        adaptadorBluetooth = BluetoothAdapter.getDefaultAdapter();
    }

    // ── Paso 1 ────────────────────────────────────────────────────────────────
    /** Crea el ServerSocket y lo pone a escuchar peticiones de conexión. */
    public void abrirSocketServidor() {
        if (serverSocket != null) serverSocket = null;
        try {
            serverSocket = adaptadorBluetooth
                    .listenUsingRfcommWithServiceRecord(NOMBRE_SERVICIO, UUID_SPP);
        } catch (IOException e) { e.printStackTrace(); }
    }

    // ── Paso 2 ────────────────────────────────────────────────────────────────
    /**
     * Bloquea el hilo hasta recibir una conexión entrante.
     * Una vez aceptada, crea el socket de comunicación con el cliente.
     */
    public void abrirSocketCliente() {
        try {
            clienteSocket = serverSocket.accept();
            AlmacenDatosRAM.conexion_bluetooth =
                    " Conectado con " + clienteSocket.getRemoteDevice().getName();
        } catch (IOException e) {
            AlmacenDatosRAM.conexion_bluetooth = "Falló la conexión";
            e.printStackTrace();
        }
    }

    // ── Paso 3 ────────────────────────────────────────────────────────────────
    public void abrirFlujoEntrada() {
        try {
            flujoEntrada = new BufferedInputStream(clienteSocket.getInputStream());
        } catch (IOException e) { e.printStackTrace(); }
    }

    public void abrirFlujoSalida() {
        try {
            flujoSalida = new BufferedOutputStream(clienteSocket.getOutputStream());
        } catch (IOException e) { e.printStackTrace(); }
    }

    // ── Paso 4 ────────────────────────────────────────────────────────────────
    /** Lee bytes enviados por el cliente. */
    public byte[] leerBytes() {
        byte[] buffer = new byte[1024];
        if (flujoEntrada != null) {
            try {
                int n = flujoEntrada.read(buffer);
                return (new String(buffer, 0, n)).getBytes();
            } catch (IOException e) { e.printStackTrace(); }
        }
        return null;
    }

    /** Envía bytes al cliente. */
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