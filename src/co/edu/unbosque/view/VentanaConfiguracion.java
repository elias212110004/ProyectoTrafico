package co.edu.unbosque.view;

import java.awt.*;
import java.awt.event.*;
import co.edu.unbosque.controller.Controlador;

public class VentanaConfiguracion extends Frame {

    private TextField campoCalles;
    private TextField campoCarreras;
    private Controlador controlador;

    public VentanaConfiguracion(Controlador ctrl) {
        super("Configuración de Ciudad");
        controlador = ctrl;
        setSize(300, 200);
        setLayout(new GridLayout(4, 1));
        setLocationRelativeTo(null);

        Label etiqueta1 = new Label("Número de calles:");
        campoCalles = new TextField();

        Label etiqueta2 = new Label("Número de carreras:");
        campoCarreras = new TextField();

        Button boton = new Button("Iniciar Simulación");
        boton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                int calles = leerEntero(campoCalles.getText());
                int carreras = leerEntero(campoCarreras.getText());
                if (calles > 0 && carreras > 0) {
                    dispose();
                    controlador.iniciarSimulacion(calles, carreras);
                } else {
                    System.out.println("Valores inválidos. Intente nuevamente.");
                }
            }
        });

        add(etiqueta1);
        add(campoCalles);
        add(etiqueta2);
        add(campoCarreras);
        add(boton);

        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                dispose();
            }
        });
    }

    private int leerEntero(String texto) {
        int numero = -1;
        boolean esValido = true;
        for (int i = 0; i < texto.length(); i++) {
            char c = texto.charAt(i);
            if (c < '0' || c > '9') {
                esValido = false;
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