package vista;

import controlador.Controlador;
import modelo.Datos;

public class MainApp {
    public static void main(String[] args) {
        var datos = new Datos();
        var ctrl = new Controlador(datos);
        new Consola(ctrl).iniciar();
    }
}
