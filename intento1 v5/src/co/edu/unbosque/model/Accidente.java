package co.edu.unbosque.model;

public class Accidente extends EventoEspecial {
    public Accidente(int calleAfectada, int turnoInicio) {
        super(calleAfectada, turnoInicio, 3); // Duración de 3 turnos
    }

    @Override
    public String getTipo() { return "Accidente"; }
}

