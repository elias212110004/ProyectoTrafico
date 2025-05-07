package co.edu.unbosque.model;

public class EstadoVia extends EventoEspecial {
    public EstadoVia(int calleAfectada, int turnoInicio) {
        super(calleAfectada, turnoInicio, 2); // Duración de 2 turnos
    }

    @Override
    public String getTipo() { return "EstadoVia"; }
}
