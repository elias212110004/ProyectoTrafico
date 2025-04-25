package co.edu.unbosque.view;

import co.edu.unbosque.model.Ciudad;
import co.edu.unbosque.model.Vehiculo;
import co.edu.unbosque.model.Semaforo;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

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

        new Timer(1000, e -> {
            ciudad.moverVehiculos();
            panelCiudad.repaint();
        }).start();
    }

    class PanelCiudad extends JPanel {
        private Ciudad ciudad;
        private final int tamBloque = 50;

        public PanelCiudad(Ciudad ciudad) {
            this.ciudad = ciudad;

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

            for (int i = 0; i < ciudad.getCalles(); i++) {
                for (int j = 0; j < ciudad.getCarreras(); j++) {
                    g.setColor(new Color(200, 200, 200));
                    g.fillRect(j * tamBloque, i * tamBloque, tamBloque, tamBloque);
                }
            }


            g.setColor(Color.YELLOW);
            for (int i = 0; i <= ciudad.getCalles(); i++) {
                g.drawLine(0, i * tamBloque, ciudad.getCarreras() * tamBloque, i * tamBloque);
            }
            for (int j = 0; j <= ciudad.getCarreras(); j++) {
                g.drawLine(j * tamBloque, 0, j * tamBloque, ciudad.getCalles() * tamBloque);
            }

            for (Semaforo s : ciudad.getSemaforos()) {
                g.setColor(s.getColor());
                g.fillOval(s.getX() * tamBloque + 20, s.getY() * tamBloque + 20, 10, 10);
            }

            for (Vehiculo v : ciudad.getVehiculos()) {
                if (v.getDireccion() == 0) { // horizontal
                    g.drawImage(v.getImagen(), v.getPosicion() * tamBloque + 5, v.getCalle() * tamBloque + 15, null);
                } else { // vertical
                    g.drawImage(v.getImagen(), v.getCalle() * tamBloque + 15, v.getPosicion() * tamBloque + 5, 20, 40, null);
                }
            }
        }
    }
}