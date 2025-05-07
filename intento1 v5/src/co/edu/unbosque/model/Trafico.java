package co.edu.unbosque.model;

public class Trafico extends EventoEspecial {
    public Trafico(int calleAfectada, int turnoInicio) {
        super(calleAfectada, turnoInicio, 1); // Duración de 1 turno
    }

    @Override
    public String getTipo() { return "Trafico"; }
}