package co.edu.unbosque.model;

import java.util.ArrayList;

public class Calle {
    private int id;
    private ArrayList<Vehiculo> vehiculos;
    private ArrayList<EventoEspecial> eventos;

    public Calle(int id) {
        this.id = id;
        this.vehiculos = new ArrayList<>();
        this.eventos = new ArrayList<>();
    }

    public int getId() {
        return id;
    }

    public ArrayList<Vehiculo> getVehiculos() {
        return vehiculos;
    }

    public ArrayList<EventoEspecial> getEventos() {
        return eventos;
    }

    public void agregarVehiculo(Vehiculo vehiculo) {
        vehiculos.add(vehiculo);
    }

    public void agregarEvento(EventoEspecial evento) {
        eventos.add(evento);
    }

    public void eliminarEvento(EventoEspecial evento) {
        eventos.remove(evento);
    }

    public void aplicarEfectosEventos(Vehiculo vehiculo) {
        for (EventoEspecial evento : eventos) {
            if (evento instanceof Accidente) {
                vehiculo.setDetenido(true);
            } else if (evento instanceof EstadoVia) {
                vehiculo.setVelocidad(vehiculo.getVelocidad() / 2);
            } else if (evento instanceof Trafico) {
                vehiculo.setVelocidad(vehiculo.getVelocidad() / 3);
            }
        }
    }
}
