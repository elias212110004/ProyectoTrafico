package co.edu.unbosque.view;

import co.edu.unbosque.model.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

public class Ventana extends JFrame implements InterfazVentana {
    private Ciudad ciudad;
    private int tamaño = 40;
    private JPanel panelCiudad; 
    
    public Ventana() {
        setTitle("Ciudad con Semáforos - Swing");
        setSize(600, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); 
        setLocationRelativeTo(null);
        setBackground(Color.WHITE);

        panelCiudad = new PanelCiudad();
        add(panelCiudad);

        addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                if (ciudad != null) {
                    ArrayList<Semaforo> semaforos = ciudad.getSemaforos();
                    for (Semaforo s : semaforos) {
                        int xReal = 50 + s.getX() * tamaño;
                        int yReal = 50 + s.getY() * tamaño;
                        Rectangle area = new Rectangle(xReal + 12, yReal + 12, 16, 16);
                        if (area.contains(e.getPoint())) {
                            s.cambiarEstado();
                            panelCiudad.repaint(); 
                        }
                    }
                }
            }
        });
    }

    public void mostrarCiudad(Ciudad ciudad) {
        this.ciudad = ciudad;
        panelCiudad.repaint();
    }

    class PanelCiudad extends JPanel {
        @Override
        protected void paintComponent(Graphics g) { 
            super.paintComponent(g);
            if (ciudad == null) return;

            int filas = ciudad.getCalles();
            int columnas = ciudad.getCarreras();

            for (int i = 0; i < filas; i++) {
                for (int j = 0; j < columnas; j++) {
                    g.setColor(Color.BLACK);
                    g.drawRect(50 + j * tamaño, 50 + i * tamaño, tamaño, tamaño);
                }
            }

            for (Semaforo s : ciudad.getSemaforos()) {
                int x = 50 + s.getX() * tamaño;
                int y = 50 + s.getY() * tamaño;
                g.setColor(s.getColor());
                g.fillOval(x + 12, y + 12, 16, 16);
                g.setColor(Color.BLACK);
                g.drawOval(x + 12, y + 12, 16, 16);
            }
        }
    }
}