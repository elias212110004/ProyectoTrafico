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
    private int cantidadInicialVehiculos = 15;
    private int cantidadInicialSemaforos = 10;

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

    // Nuevos métodos para establecer las cantidades iniciales
    public void setCantidadInicialVehiculos(int cantidad) {
        this.cantidadInicialVehiculos = cantidad;
        generarVehiculosIniciales(); // Regenerar vehículos con la nueva cantidad
    }

    public void setCantidadInicialSemaforos(int cantidad) {
        this.cantidadInicialSemaforos = cantidad;
        inicializarSemaforos(); // Reinicializar semáforos con la nueva cantidad
    }

    private void inicializarCalles() {
        for (int i = 0; i < calles; i++) {
            listaCalles.add(new Calle(i));
        }
    }

    private void inicializarSemaforos() {
        semaforos.clear(); // Limpiar la lista antes de generar nuevos
        int numSemaforos = Math.max(10, (calles * carreras) / 10);
        numSemaforos = Math.max(numSemaforos, cantidadInicialSemaforos); // Asegurar al menos la cantidad inicial
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
        vehiculos.clear(); // Limpiar la lista antes de generar nuevos
        for (int i = 0; i < cantidadInicialVehiculos; i++) {
            int calle = aleatorio.nextInt(calles);
            int posicion = 0;
            int velocidad = 1;
            int direccion = aleatorio.nextInt(2); // 0: Derecha, 1: Abajo
            vehiculos.add(new Vehiculo(calle, posicion, velocidad, direccion));
        }
    
}

    private int generarCalleConProbabilidad() {
        // Simula calles principales con más probabilidad
        double prob = aleatorio.nextDouble();
        if (prob < 0.3) {
            return aleatorio.nextInt(calles / 4); // Primeras calles (más concurridas)
        } else {
            return aleatorio.nextInt(calles);
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
        for (int i = vehiculos.size() - 1; i >= 0; i--) { // Iterar hacia atrás
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
            vehiculo.setPosicion(nuevaPosicion);

            // Verificar si el vehículo salió de la ciudad y eliminarlo
            if (vehiculo.getDireccion() == 0) { // Derecha
                if (vehiculo.getPosicion() >= carreras) {
                    vehiculos.remove(i);
                    continue; // Importante: Saltar el resto del bucle
                }
            } else if (vehiculo.getDireccion() == 1) { // Abajo
                if (vehiculo.getPosicion() >= calles) {
                    vehiculos.remove(i);
                    continue; // Importante: Saltar el resto del bucle
                }
            }

            if (semaforo != null && vehiculo.puedeGirar()) {
                nuevaDireccion = aleatorio.nextInt(2);
                vehiculo.setDireccion(nuevaDireccion);
                nuevaPosicion = vehiculo.mover(getVehiculosEnCalle(vehiculo.getCalle(), nuevaDireccion), null, this);
                vehiculo.setPosicion(nuevaPosicion);
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
    public int getCantidadInicialSemaforos() {
        return cantidadInicialSemaforos;
    }
}
