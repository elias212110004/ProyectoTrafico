package co.edu.unbosque.model;

import java.util.ArrayList;
import java.util.Random;

public class Ciudad {
    private int calles;
    private int carreras;
    private ArrayList<Semaforo> semaforos;
    private ArrayList<Vehiculo> vehiculos;
    private Random aleatorio;
    private int turno = 0;
    private ArrayList<EventoEspecial> eventos = new ArrayList<>();
    private ArrayList<Calle> listaCalles;

    public Ciudad(int calles, int carreras) {
        this.calles = calles;
        this.carreras = carreras;
        this.semaforos = new ArrayList<>();
        this.vehiculos = new ArrayList<>();
        this.aleatorio = new Random();
        this.listaCalles = new ArrayList<>();
        inicializarCalles();
        inicializarSemaforos();
        generarVehiculosIniciales();
    }

    private void inicializarCalles() {
        for (int i = 0; i < calles; i++) {
            listaCalles.add(new Calle(i));
        }
    }

    private void inicializarSemaforos() {
        int numSemaforos = Math.max(10, (calles * carreras) / 10);
        for (int i = 0; i < numSemaforos; i++) {
            int x = aleatorio.nextInt(carreras);
            int y = aleatorio.nextInt(calles);
            if (!existeSemaforo(x, y)) {
                semaforos.add(new Semaforo(x, y));
            } else {
                i--;
            }
        }
    }

    private boolean existeSemaforo(int x, int y) {
        for (Semaforo s : semaforos) {
            if (s.getX() == x && s.getY() == y) {
                return true;
            }
        }
        return false;
    }

    private void generarVehiculosIniciales() {
        int numVehiculos = 15;
        for (int i = 0; i < numVehiculos; i++) {
            int calle = aleatorio.nextInt(calles);
            int posicion = 0;
            int velocidad = 1;
            int direccion = aleatorio.nextInt(2); // 0: Derecha, 1: Abajo
            vehiculos.add(new Vehiculo(calle, posicion, velocidad, direccion));
        }
    }

    public int getCalles() {
        return calles;
    }

    public int getCarreras() {
        return carreras;
    }

    public ArrayList<Semaforo> getSemaforos() {
        return semaforos;
    }

    public ArrayList<Vehiculo> getVehiculos() {
        return vehiculos;
    }

    private ArrayList<Vehiculo> getVehiculosEnCalle(int calle, int direccion) {
        ArrayList<Vehiculo> vehiculosEnCalle = new ArrayList<>();
        for (Vehiculo vehiculo : vehiculos) {
            if (vehiculo.getCalle() == calle && vehiculo.getDireccion() == direccion) {
                vehiculosEnCalle.add(vehiculo);
            }
        }
        return vehiculosEnCalle;
    }

    public void moverVehiculos() {
        turno++;
        if (turno % 2 == 0) {
            for (Semaforo semaforo : semaforos) {
                semaforo.cambiarEstado();
            }
        }
        actualizarEventos();
        for (int i = 0; i < vehiculos.size(); i++) {
            Vehiculo vehiculo = vehiculos.get(i);
            Semaforo semaforo = null;
            int nuevaDireccion = vehiculo.getDireccion();
             if (vehiculo.getDireccion() == 0) { // Derecha
                semaforo = buscarSemaforo(vehiculo.getPosicion() + 1, vehiculo.getCalle());
            } else if (vehiculo.getDireccion() == 1) { // Abajo
                semaforo = buscarSemaforo(vehiculo.getCalle(), vehiculo.getPosicion() + 1);
            }
            Calle calleObj = buscarCalle(vehiculo.getCalle());
            if (calleObj != null) {
                calleObj.aplicarEfectosEventos(vehiculo);
            }
            int nuevaPosicion = vehiculo.mover(getVehiculosEnCalle(vehiculo.getCalle(), vehiculo.getDireccion()), semaforo, this);

            if (semaforo != null && vehiculo.puedeGirar()) {
                nuevaDireccion = aleatorio.nextInt(2);
                vehiculo.setDireccion(nuevaDireccion);
                nuevaPosicion = vehiculo.mover(getVehiculosEnCalle(vehiculo.getCalle(), nuevaDireccion), null, this);
            }
            vehiculo.setPosicion(nuevaPosicion);
            if (vehiculo.getDireccion() == 0) { // Derecha
                if (vehiculo.getPosicion() >= carreras) {
                    vehiculo.setPosicion(0); 
                }
            } else if (vehiculo.getDireccion() == 1) { // Abajo
                if (vehiculo.getPosicion() >= calles) {
                    vehiculo.setPosicion(0); 
                }
            }
        }
    }

    private Semaforo buscarSemaforo(int x, int y) {
        for (Semaforo s : semaforos) {
            if (s.getX() == x && s.getY() == y) {
                return s;
            }
        }
        return null;
    }

    private Calle buscarCalle(int calleId) {
        for (Calle calle : listaCalles) {
            if (calle.getId() == calleId) {
                return calle;
            }
        }
        return null;
    }

    public void agregarEvento(EventoEspecial evento) {
        eventos.add(evento);
        Calle calle = buscarCalle(evento.getCalleAfectada());
        if (calle != null) {
            calle.agregarEvento(evento);
        }
    }

    public void actualizarEventos() {
        for (int i = 0; i < eventos.size(); i++) {
            EventoEspecial evento = eventos.get(i);
            evento.decrementarDuracion();
            if (evento.getDuracion() == 0) {
                eventos.remove(i);
                Calle calle = buscarCalle(evento.getCalleAfectada());
                if (calle != null) {
                    calle.eliminarEvento(evento);
                }
                i--;
            }
        }
    }
}
