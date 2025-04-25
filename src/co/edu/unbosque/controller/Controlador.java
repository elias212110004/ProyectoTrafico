package co.edu.unbosque.controller;

import co.edu.unbosque.model.Ciudad;
import co.edu.unbosque.view.InterfazVentana;
import co.edu.unbosque.view.VentanaConfiguracion;
import co.edu.unbosque.view.VentanaPrincipal;

import java.awt.EventQueue;

public class Controlador implements Runnable {
    private Ciudad ciudad;
    private InterfazVentana vistaPrincipal;

    public Controlador() {
        EventQueue.invokeLater(this);
    }

    @Override
    public void run() {
        VentanaConfiguracion configuracion = new VentanaConfiguracion(this);
        configuracion.setVisible(true);
    }

    public void iniciarSimulacion(int calles, int carreras) {
        ciudad = new Ciudad(calles, carreras);
        vistaPrincipal = new VentanaPrincipal();
        vistaPrincipal.mostrarCiudad(ciudad);
    }
}