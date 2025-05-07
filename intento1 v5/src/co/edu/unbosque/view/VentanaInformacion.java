package co.edu.unbosque.view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class VentanaInformacion extends JFrame {

    private JLabel labelTituloCarros;
    private JLabel labelCantidadCarros;
    private JButton botonDisminuirCarros;
    private JTextField campoCantidadCarros;
    private JButton botonAumentarCarros;
    private int cantidadInicialCarros = 15; // Cantidad inicial

    private JLabel labelTituloSemaforos;
    private JLabel labelCantidadSemaforos;
    private JButton botonDisminuirSemaforos;
    private JTextField campoCantidadSemaforos;
    private JButton botonAumentarSemaforos;
    private int cantidadInicialSemaforos = 10; // Cantidad inicial

    private JLabel labelTituloTiempo;
    private JLabel labelTiempoSimulacion;

    private Font fuenteTitulo = new Font("Arial", Font.BOLD, 18);
    private Font fuenteDatos = new Font("Arial", Font.PLAIN, 16);

    public VentanaInformacion() {
        setTitle("Información de Simulación");
        setSize(300, 800);
        setLayout(new GridLayout(6, 1));
        getContentPane().setBackground(new Color(220, 220, 250));

        // Panel de Carros
        JPanel panelCarros = new JPanel(new FlowLayout(FlowLayout.CENTER));
        panelCarros.setBackground(new Color(220, 220, 250));

        labelTituloCarros = new JLabel("Cantidad de Carros");
        labelTituloCarros.setFont(fuenteTitulo);
        panelCarros.add(labelTituloCarros);

        labelCantidadCarros = new JLabel(String.valueOf(cantidadInicialCarros));
        labelCantidadCarros.setFont(fuenteDatos);
        panelCarros.add(labelCantidadCarros);

        botonDisminuirCarros = new JButton("-");
        botonAumentarCarros = new JButton("+");
        campoCantidadCarros = new JTextField(String.valueOf(cantidadInicialCarros), 5);

        panelCarros.add(botonDisminuirCarros);
        panelCarros.add(campoCantidadCarros);
        panelCarros.add(botonAumentarCarros);

        add(panelCarros);

        // Panel de Semáforos
        JPanel panelSemaforos = new JPanel(new FlowLayout(FlowLayout.CENTER));
        panelSemaforos.setBackground(new Color(220, 220, 250));

        labelTituloSemaforos = new JLabel("Cantidad de Semáforos");
        labelTituloSemaforos.setFont(fuenteTitulo);
        panelSemaforos.add(labelTituloSemaforos);

        labelCantidadSemaforos = new JLabel(String.valueOf(cantidadInicialSemaforos));
        labelCantidadSemaforos.setFont(fuenteDatos);
        panelSemaforos.add(labelCantidadSemaforos);

        botonDisminuirSemaforos = new JButton("-");
        botonAumentarSemaforos = new JButton("+");
        campoCantidadSemaforos = new JTextField(String.valueOf(cantidadInicialSemaforos), 5);

        panelSemaforos.add(botonDisminuirSemaforos);
        panelSemaforos.add(campoCantidadSemaforos);
        panelSemaforos.add(botonAumentarSemaforos);

        add(panelSemaforos);

        // Panel de Tiempo
        JPanel panelTiempo = new JPanel(new FlowLayout(FlowLayout.CENTER));
        panelTiempo.setBackground(new Color(220, 220, 250));

        labelTituloTiempo = new JLabel("Tiempo de Simulación");
        labelTituloTiempo.setFont(fuenteTitulo);
        panelTiempo.add(labelTituloTiempo);

        labelTiempoSimulacion = new JLabel("00:00:00");
        labelTiempoSimulacion.setFont(fuenteDatos);
        panelTiempo.add(labelTiempoSimulacion);

        add(panelTiempo);

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    public void actualizarCantidadCarros(int cantidad) {
        labelCantidadCarros.setText(String.valueOf(cantidad));
        campoCantidadCarros.setText(String.valueOf(cantidad)); // Actualizar el JTextField
        cantidadInicialCarros = cantidad; // Actualizar la cantidad inicial
    }

    public void actualizarCantidadSemaforos(int cantidad) {
        labelCantidadSemaforos.setText(String.valueOf(cantidad));
        campoCantidadSemaforos.setText(String.valueOf(cantidad)); // Actualizar el JTextField
        cantidadInicialSemaforos = cantidad; // Actualizar la cantidad inicial
    }

    public void actualizarTiempoSimulacion(int tiempoEnSegundos) {
        int horas = tiempoEnSegundos / 3600;
        int minutos = (tiempoEnSegundos % 3600) / 60;
        int segundos = tiempoEnSegundos % 60;
        labelTiempoSimulacion.setText(String.format("%02d:%02d:%02d", horas, minutos, segundos));
    }

    // Métodos para acceder a los componentes (para el Controlador)
    public JButton getBotonDisminuirCarros() { return botonDisminuirCarros; }
    public JTextField getCampoCantidadCarros() { return campoCantidadCarros; }
    public JButton getBotonAumentarCarros() { return botonAumentarCarros; }

    public JButton getBotonDisminuirSemaforos() { return botonDisminuirSemaforos; }
    public JTextField getCampoCantidadSemaforos() { return campoCantidadSemaforos; }
    public JButton getBotonAumentarSemaforos() { return botonAumentarSemaforos; }

    public int getCantidadInicialCarros() { return cantidadInicialCarros; }
    public int getCantidadInicialSemaforos() { return cantidadInicialSemaforos; }
}