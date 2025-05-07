package co.edu.unbosque.model;

import java.awt.Color;

public class Semaforo {
    private int x;
    private int y;
    private String estado; // "ROJO", "AMARILLO", "VERDE"

    public Semaforo(int x, int y) {
        this.x = x;
        this.y = y;
        estado = "ROJO";
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public String getEstado() {
        return estado;
    }

    public Color getColor() {
        if (estado.equals("ROJO"))
            return Color.RED;
        if (estado.equals("VERDE"))
            return Color.GREEN;
        return Color.YELLOW;
    }

    public void cambiarEstado() {
        if (estado.equals("ROJO"))
            estado = "VERDE";
        else if (estado.equals("VERDE"))
            estado = "AMARILLO";
        else
            estado = "ROJO";
    }
}