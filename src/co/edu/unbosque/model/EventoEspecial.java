package co.edu.unbosque.model;

public abstract class EventoEspecial {
    private int calleAfectada;
    private int turnoInicio;
    private int duracion;

    public EventoEspecial(int calleAfectada, int turnoInicio, int duracion) {
        this.calleAfectada = calleAfectada;
        this.turnoInicio = turnoInicio;
        this.duracion = duracion;
    }

    public int getCalleAfectada() {
        return calleAfectada;
    }

    public int getTurnoInicio() {
        return turnoInicio;
    }

    public int getDuracion() {
        return duracion;
    }

    public abstract String getTipo(); 
    
    public void decrementarDuracion() {
        duracion--;
    }
}

