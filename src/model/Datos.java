package model;

// La clase Datos actúa como el contenedor de todos los gestores del Modelo.

public class Datos {
    
    // Declaramos las instancias de las clases gestoras como atributos.
    // El Controlador solo interactuará con este objeto 'Datos'.
    private ListaArticulos listaArticulos;
    private ListaClientes listaClientes;
    private ListaPedidos listaPedidos;

    // Constructor: Inicializa cada uno de los gestores.
    public Datos() {
        this.listaArticulos = new ListaArticulos();
        this.listaClientes = new ListaClientes();
        this.listaPedidos = new ListaPedidos();
        // Opcional: Aquí podrías llamar a un método para cargar datos de prueba.
    }
    
    // --- MÉTODOS GETTERS PARA ACCEDER A LOS GESTORES ---
    
    // El Controlador usará estos métodos para acceder a la lógica de negocio.
    
    public ListaArticulos getListaArticulos() {
        return listaArticulos;
    }

    public ListaClientes getListaClientes() {
        return listaClientes;
    }

    public ListaPedidos getListaPedidos() {
        return listaPedidos;
    }
}