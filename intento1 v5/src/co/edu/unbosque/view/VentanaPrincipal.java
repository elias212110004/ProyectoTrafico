package co.edu.unbosque.view;

import co.edu.unbosque.model.Ciudad;
import co.edu.unbosque.model.Semaforo;
import co.edu.unbosque.model.Vehiculo;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.geom.AffineTransform;

public class VentanaPrincipal extends JFrame implements InterfazVentana {
    private Ciudad ciudad;
    private PanelCiudad panelCiudad;

    public VentanaPrincipal() {
        setTitle("Simulación de Tráfico");
        setSize(800, 800);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
    }

    @Override
    public void mostrarCiudad(Ciudad ciudad) {
        this.ciudad = ciudad;
        panelCiudad = new PanelCiudad(ciudad);
        add(panelCiudad);
        setVisible(true);
    }

    public PanelCiudad getPanelCiudad() { // Agregado el getter
        return panelCiudad;
    }

    public class PanelCiudad extends JPanel {
        private final int tamBloque = 50;

        public PanelCiudad(Ciudad ciudad) {
            addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    int x = e.getX() / tamBloque;
                    int y = e.getY() / tamBloque;
                    for (Semaforo s : ciudad.getSemaforos()) {
                        if (s.getX() == x && s.getY() == y) {
                            s.cambiarEstado();
                            repaint();
                            break;
                        }
                }
            }
        });
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        setBackground(new Color(230, 230, 230));

        // Dibujar la cuadrícula de calles y carreras
        for (int i = 0; i < ciudad.getCalles(); i++) {
            for (int j = 0; j < ciudad.getCarreras(); j++) {
                g.setColor(new Color(200, 200, 200));
                g.fillRect(j * tamBloque, i * tamBloque, tamBloque, tamBloque);
            }
        }

        // Líneas amarillas de separación
        g.setColor(Color.YELLOW);
        for (int i = 0; i <= ciudad.getCalles(); i++) {
            g.drawLine(0, i * tamBloque, ciudad.getCarreras() * tamBloque, i * tamBloque);
        }
        for (int j = 0; j <= ciudad.getCarreras(); j++) {
            g.drawLine(j * tamBloque, 0, j * tamBloque, ciudad.getCalles() * tamBloque);
        }

        // Semáforos
        for (Semaforo s : ciudad.getSemaforos()) {
            int x = s.getX() * tamBloque + tamBloque / 4;
            int y = s.getY() * tamBloque + tamBloque / 4;
            g.setColor(s.getColor());
            g.fillOval(x, y, tamBloque / 2, tamBloque / 2);
            g.setColor(Color.BLACK);
            g.drawOval(x, y, tamBloque / 2, tamBloque / 2);
        }

        // Vehículos
        for (Vehiculo v : ciudad.getVehiculos()) {
            int x = v.getPosicion() * tamBloque;
            int y = v.getCalle() * tamBloque;

            Graphics2D g2d = (Graphics2D) g.create();
            AffineTransform at = new AffineTransform();

            at.translate(x + Vehiculo.getAnchoVehiculo() / 2, y + Vehiculo.getAltoVehiculo() / 2);

            if (v.getDireccion() == 1) {
                at.rotate(Math.PI / 2);
            }

            at.translate(-Vehiculo.getAnchoVehiculo() / 2, -Vehiculo.getAltoVehiculo() / 2);
            g2d.transform(at);
            g2d.drawImage(v.getImagen(), x, y, null);
            g2d.dispose();
        }
    }
}
}

