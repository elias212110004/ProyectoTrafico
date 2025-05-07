package co.edu.unbosque.view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class VentanaControlTiempo extends JFrame {

    private JLabel labelTiempo;
    private JButton botonPausar;
    private JButton botonAcelerar;
    private Font fuente = new Font("Arial", Font.BOLD, 20);
    private int tiempoRestante; // Tiempo restante en segundos
    private Timer timerCuentaRegresiva; // Timer para la cuenta regresiva

    public VentanaControlTiempo() {
        setTitle("Control de Tiempo");
        setSize(800, 100);
        setLayout(new FlowLayout(FlowLayout.CENTER, 20, 20));
        getContentPane().setBackground(new Color(240, 240, 240));

        labelTiempo = new JLabel("Tiempo Restante: 00:05:00");
        labelTiempo.setFont(fuente);

        botonPausar = new JButton("Pausar");
        botonPausar.setFont(fuente);

        botonAcelerar = new JButton("Acelerar");
        botonAcelerar.setFont(fuente);

        add(labelTiempo);
        add(botonPausar);
        add(botonAcelerar);

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Listeners (Controlador manejará esto)
    }

    public void iniciarCuentaRegresiva(int duracionEnSegundos) {
        tiempoRestante = duracionEnSegundos;
        actualizarLabelTiempo();
        timerCuentaRegresiva = new Timer(1000, e -> {
            tiempoRestante--;
            if (tiempoRestante < 0) {
                timerCuentaRegresiva.stop();
                // Aquí puedes agregar lógica para finalizar la simulación
                labelTiempo.setText("Simulación Terminada");
            } else {
                actualizarLabelTiempo();
            }
        });
        timerCuentaRegresiva.start();
    }

    private void actualizarLabelTiempo() {
        int horas = tiempoRestante / 3600;
        int minutos = (tiempoRestante % 3600) / 60;
        int segundos = tiempoRestante % 60;
        labelTiempo.setText(String.format("Tiempo Restante: %02d:%02d:%02d", horas, minutos, segundos));
    }

    public JButton getBotonPausar() {
        return botonPausar;
    }

    public JButton getBotonAcelerar() {
        return botonAcelerar;
    }

    public void detenerCuentaRegresiva() {
        if (timerCuentaRegresiva != null) {
            timerCuentaRegresiva.stop();
        }
    }

    public void reanudarCuentaRegresiva() {
        if (timerCuentaRegresiva != null && !timerCuentaRegresiva.isRunning()) {
            timerCuentaRegresiva.start();
        }
    }
}