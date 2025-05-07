package co.edu.unbosque.controller;

import co.edu.unbosque.model.Ciudad;
import co.edu.unbosque.view.VentanaPrincipal;
import co.edu.unbosque.view.VentanaControlTiempo;
import co.edu.unbosque.view.VentanaInformacion;
import co.edu.unbosque.view.VentanaConfiguracion;

import java.awt.*;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Controlador implements Runnable {

    private Ciudad ciudad;
    private VentanaPrincipal vistaPrincipal;
    private VentanaControlTiempo vistaControlTiempo;
    private VentanaInformacion vistaInformacion;
    private Timer timerSimulacion;
    private int tiempoSimulacion = 0;
    private int duracionSimulacion; // Duración total de la simulación

    public Controlador() {
        EventQueue.invokeLater(this);
    }

    @Override
    public void run() {
        VentanaConfiguracion configuracion = new VentanaConfiguracion(this);
        configuracion.setVisible(true);
    }

    public void iniciarSimulacion(int calles, int carreras, int duracionEnSegundos) {
        ciudad = new Ciudad(calles, carreras);
        vistaPrincipal = new VentanaPrincipal();
        vistaControlTiempo = new VentanaControlTiempo();
        vistaInformacion = new VentanaInformacion();
        this.duracionSimulacion = duracionEnSegundos; // Guardar la duración

        // Obtener Dimensiones de la Pantalla
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        int principalWidth = 800;
        int principalHeight = 800;

        // Posicionar Ventanas
        vistaPrincipal.setBounds((screenSize.width - principalWidth) / 2, (screenSize.height - principalHeight) / 2, principalWidth, principalHeight);
        vistaControlTiempo.setBounds((screenSize.width - principalWidth) / 2, (screenSize.height - principalHeight) / 2 - 100, principalWidth, 100);
        vistaInformacion.setBounds((screenSize.width - principalWidth) / 2 - 200, (screenSize.height - principalHeight) / 2, 200, principalHeight);

        vistaPrincipal.mostrarCiudad(ciudad);
        vistaPrincipal.setVisible(true);
        vistaControlTiempo.setVisible(true);
        vistaInformacion.setVisible(true);

        vistaControlTiempo.iniciarCuentaRegresiva(duracionSimulacion); // Iniciar la cuenta regresiva

        // Iniciar la simulación con un Timer
        timerSimulacion = new Timer(1000, e -> {
            ciudad.moverVehiculos();
            vistaPrincipal.getPanelCiudad().repaint();
            tiempoSimulacion++;
            vistaInformacion.actualizarTiempoSimulacion(tiempoSimulacion);
            vistaInformacion.actualizarCantidadCarros(ciudad.getVehiculos().size());
            vistaInformacion.actualizarCantidadSemaforos(ciudad.getSemaforos().size());

            if (tiempoSimulacion >= duracionSimulacion) {
                timerSimulacion.stop();
                vistaControlTiempo.detenerCuentaRegresiva();
                JOptionPane.showMessageDialog(vistaPrincipal, "Simulación Terminada", "Fin", JOptionPane.INFORMATION_MESSAGE);
            }
        });
        timerSimulacion.start();

        // Listeners
        vistaControlTiempo.getBotonPausar().addActionListener(e -> {
            if (timerSimulacion.isRunning()) {
                timerSimulacion.stop();
                vistaControlTiempo.detenerCuentaRegresiva();
            } else {
                timerSimulacion.start();
                vistaControlTiempo.reanudarCuentaRegresiva();
            }
        });

        vistaControlTiempo.getBotonAcelerar().addActionListener(e -> {
            timerSimulacion.setDelay(timerSimulacion.getDelay() / 2);
        });
    }
}