package co.edu.unbosque.model;

import java.awt.Image;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;
import javax.imageio.ImageIO;

public class Vehiculo {
    private int calle;
    private int posicion;
    private int velocidad;
    private int direccion;
    private boolean detenido;
    private Image imagen;
    private boolean frenadoParcial;
    private Random random; // Declarar Random
    private int numeroCarroImagenes = 3;
    private static final int ANCHO_VEHICULO = 40;
    private static final int ALTO_VEHICULO = 20;

    public Vehiculo(int calle, int posicion, int velocidad, int direccion) {
        this.calle = calle;
        this.posicion = posicion;
        this.velocidad = velocidad;
        this.direccion = direccion;
        this.detenido = false;
        this.random = new Random();  // Inicializar Random AQUÍ (¡CRUCIAL!)
        cargarImagenAleatoria();
    }

    private void cargarImagenAleatoria() {
        try {
            int indice = random.nextInt(numeroCarroImagenes) + 1;
            String rutaImagen = "carro" + indice + ".png";
            imagen = ImageIO.read(new File(rutaImagen));
            imagen = imagen.getScaledInstance(ANCHO_VEHICULO, ALTO_VEHICULO, Image.SCALE_DEFAULT);
        } catch (IOException e) {
            e.printStackTrace();
            cargarImagenPorDefecto();
        }
    }

    private void cargarImagenPorDefecto() {
        try {
            imagen = ImageIO.read(new File("carro1.png"));
            imagen = imagen.getScaledInstance(ANCHO_VEHICULO, ALTO_VEHICULO, Image.SCALE_DEFAULT);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public int mover(ArrayList<Vehiculo> vehiculosEnCalle, Semaforo semaforo, Ciudad ciudad) {
        if (detenido) {
            detenido = false;
            return posicion;
        }

        if (semaforo != null && semaforo.getEstado().equals("ROJO")) {
            detenido = true;
            return posicion;
        }

        Vehiculo vehiculoAdelante = getVehiculoAdelante(vehiculosEnCalle);
        if (vehiculoAdelante != null) {
            int distancia = vehiculoAdelante.getPosicion() - this.posicion;
            if (distancia <= velocidad) {
                this.posicion = vehiculoAdelante.getPosicion() - 1;
                return posicion;
            }
            if (vehiculoAdelante.getVelocidad() < this.velocidad) {
                this.velocidad = vehiculoAdelante.getVelocidad();
            }
        }
        int distanciaAvance = velocidad;
        if (frenadoParcial) {
            distanciaAvance = velocidad / 2;
            frenadoParcial = false;
        }
        posicion += distanciaAvance;
        return posicion;
    }

    private Vehiculo getVehiculoAdelante(ArrayList<Vehiculo> vehiculosEnCalle) {
        Vehiculo masCercano = null;
        int distanciaMinima = Integer.MAX_VALUE;
        for (Vehiculo otroVehiculo : vehiculosEnCalle) {
            if (otroVehiculo != this && otroVehiculo.getDireccion() == this.direccion && otroVehiculo.getPosicion() > this.posicion && otroVehiculo.getCalle() == this.calle) {
                int distancia = otroVehiculo.getPosicion() - this.posicion;
                if (distancia < distanciaMinima) {
                    distanciaMinima = distancia;
                    masCercano = otroVehiculo;
                }
            }
        }
        return masCercano;
    }

    public int getCalle() {
        return calle;
    }

    public int getPosicion() {
        return posicion;
    }

    public void setPosicion(int posicion) {
        this.posicion = posicion;
    }

    public int getVelocidad() {
        return velocidad;
    }

    public void setVelocidad(int velocidad) {
        this.velocidad = velocidad;
    }

    public int getDireccion() {
        return direccion;
    }

    public void setDireccion(int direccion) {
        this.direccion = direccion;
    }

    public Image getImagen() {
        return imagen;
    }

    public boolean isDetenido() {
        return detenido;
    }

    public void setDetenido(boolean detenido) {
        this.detenido = detenido;
    }

    public void setFrenadoParcial(boolean frenadoParcial) {
        this.frenadoParcial = frenadoParcial;
    }

    public int getFuturaPosicion() {
        return posicion + velocidad;
    }

    public boolean puedeGirar() {
        return random.nextDouble() < 0.25; // probabilidad de girar
    }

    public static int getAnchoVehiculo() {
        return ANCHO_VEHICULO;
    }

    public static int getAltoVehiculo() {
        return ALTO_VEHICULO;
    }

    public void setCalle(int calle) {
        this.calle = calle;
    	
	}
}