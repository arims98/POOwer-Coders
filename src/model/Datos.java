package model;

import java.time.LocalDateTime;

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

    // --- CARGA DE DATOS DE DEMOSTRACIÓN ---
    /**
     * Inserta algunos registros de ejemplo para poder navegar por los menús
     * sin tener que introducir datos manualmente cada vez.
     */
    public void cargarDatosDemo() {
        // Artículos
        Articulo a1 = new Articulo(1001, "Auriculares Bluetooth", 29.99f, 3.90f, 10);
        Articulo a2 = new Articulo(1002, "Teclado mecánico", 59.95f, 4.50f, 15);
        Articulo a3 = new Articulo(1003, "Ratón inalámbrico", 19.99f, 2.99f, 8);
        listaArticulos.agregarArticulo(a1);
        listaArticulos.agregarArticulo(a2);
        listaArticulos.agregarArticulo(a3);

        // Clientes
        Cliente c1 = new ClienteEstandar("Ana", "C/ Luna 12", "11111111A", "ana@example.com");
        Cliente c2 = new ClientePremium("Luis", "Av. Sol 34", "22222222B", "luis@example.com");
        Cliente c3 = new ClienteEstandar("Marta", "Pza. Mar 5", "33333333C", "marta@example.com");
        listaClientes.agregarCliente(c1);
        listaClientes.agregarCliente(c2);
        listaClientes.agregarCliente(c3);

        // Pedidos
        Pedido p1 = new Pedido(c2, a2, 1, LocalDateTime.now().minusDays(1));
        Pedido p2 = new Pedido(c1, a1, 2, LocalDateTime.now().minusHours(5));
        listaPedidos.agregarPedido(p1);
        listaPedidos.agregarPedido(p2);
    }
}