package co.edu.unbosque.view;

import co.edu.unbosque.controller.Controlador;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class VentanaConfiguracion extends JFrame {
    private JTextField campoCalles;
    private JTextField campoCarreras;
    private JTextField campoDuracion; // Nuevo campo para la duración
    private Controlador controlador;
    private JButton botonIniciar;

    public VentanaConfiguracion(Controlador ctrl) {
        super("Configuración de Ciudad");
        this.controlador = ctrl;

        setSize(350, 250);
        setLayout(new GridBagLayout());
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 10, 5, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        JLabel labelCalles = new JLabel("Número de calles:");
        gbc.gridx = 0;
        gbc.gridy = 0;
        add(labelCalles, gbc);

        campoCalles = new JTextField();
        gbc.gridx = 1;
        gbc.gridy = 0;
        add(campoCalles, gbc);
        campoCalles.addActionListener(e -> campoCarreras.requestFocusInWindow()); // Cambiar foco con Enter

        JLabel labelCarreras = new JLabel("Número de carreras:");
        gbc.gridx = 0;
        gbc.gridy = 1;
        add(labelCarreras, gbc);

        campoCarreras = new JTextField();
        gbc.gridx = 1;
        gbc.gridy = 1;
        add(campoCarreras, gbc);
        campoCarreras.addActionListener(e -> campoDuracion.requestFocusInWindow()); // Cambiar foco con Enter

        JLabel labelDuracion = new JLabel("Duración (minutos):");
        gbc.gridx = 0;
        gbc.gridy = 2;
        add(labelDuracion, gbc);

        campoDuracion = new JTextField("5");
        gbc.gridx = 1;
        gbc.gridy = 2;
        add(campoDuracion, gbc);

        botonIniciar = new JButton("Iniciar Simulación");
        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.gridwidth = 2;
        add(botonIniciar, gbc);

        // Acción con Enter o Click en el botón
        ActionListener iniciar = e -> {
            int calles = leerEntero(campoCalles.getText());
            int carreras = leerEntero(campoCarreras.getText());
            int duracion = leerEntero(campoDuracion.getText());

            if (calles > 0 && carreras > 0 && duracion > 0) {
                dispose();
                controlador.iniciarSimulacion(calles, carreras, duracion * 60);
            } else {
                JOptionPane.showMessageDialog(this,
                        "Por favor, ingrese valores válidos (mayores a 0).",
                        "Error de entrada", JOptionPane.ERROR_MESSAGE);
            }
        };

        botonIniciar.addActionListener(iniciar);
        campoDuracion.addActionListener(iniciar);

        //  Mover el foco al primer campo al iniciar
        SwingUtilities.invokeLater(() -> campoCalles.requestFocusInWindow());

        setVisible(true);
    }

    private int leerEntero(String texto) {
        int numero = -1;
        boolean esValido = true;
        for (int i = 0; i < texto.length(); i++) {
            char c = texto.charAt(i);
            if (c < '0' || c > '9') {
                esValido = false;
                break;
            }
        }
        if (esValido && texto.length() > 0) {
            numero = 0;
            for (int i = 0; i < texto.length(); i++) {
                numero = numero * 10 + (texto.charAt(i) - '0');
            }
        }
        return numero;
    }
}