package co.edu.unbosque.view;

import java.awt.*;//no lo hemos visto
import java.awt.event.*;
import java.util.ArrayList;
import co.edu.unbosque.model.*;

public class Ventana extends Frame implements InterfazVentana {
    private Ciudad ciudad;
    private int tamaño = 40;

    public Ventana() {
        setTitle("Ciudad con Semáforos - AWT");
        setSize(600, 600);
        setVisible(true);
        setLocationRelativeTo(null);
        setBackground(Color.WHITE);
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                dispose();//no lo hemos visto
            }
        });

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
                            repaint();//no lo hemos visto
                        }
                    }
                }
            }
        });
    }

    public void mostrarCiudad(Ciudad ciudad) {
        this.ciudad = ciudad;
        repaint();//no lo hemos visto
    }

    public void paint(Graphics g) {//no lo hemos visto
        if (ciudad == null) return;

        int filas = ciudad.getCalles();
        int columnas = ciudad.getCarreras();

        for (int i = 0; i < filas; i++) {
            for (int j = 0; j < columnas; j++) {
                g.setColor(Color.BLACK);
                g.drawRect(50 + j * tamaño, 50 + i * tamaño, tamaño, tamaño);//no lo hemos visto pero okis
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