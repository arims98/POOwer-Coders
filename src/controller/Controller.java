package controller;

import model.*;
import java.time.LocalDateTime;
import java.util.List;

public class Controller {

    private final Datos datos;

    public Controller(Datos datos) {
        this.datos = datos;
        cargarDatosPrueba();
    }

    // =========================================================================
    // GESTIÓN DE CLIENTES
    // =========================================================================
    public boolean addCliente(String nombre, String domicilio, String nif, String email, String tipo) {
        Cliente c;
        if (tipo.equalsIgnoreCase("Premium")) {
            c = new ClientePremium(nombre, domicilio, nif, email);
        } else {
            c = new ClienteEstandar(nombre, domicilio, nif, email);
        }
        return datos.addCliente(c);
    }

    public void addClienteEstandar(String nombre, String domicilio, String nif, String email) {
        datos.addCliente(new ClienteEstandar(nombre, domicilio, nif, email));
    }

    public void addClientePremium(String nombre, String domicilio, String nif, String email) {
        datos.addCliente(new ClientePremium(nombre, domicilio, nif, email));
    }


    // =========================================================================
    // GESTIÓN DE ARTÍCULOS
    // =========================================================================
    public void addArticulo(String codigo, String descripcion, double precio, double envio, int tiempoPreparacion) {
        datos.addArticulo(new Articulo(codigo, descripcion, precio, envio, tiempoPreparacion));
    }

    public List<Articulo> listarArticulos() {
        return datos.getArticulos();
    }

    // =========================================================================
    // GESTIÓN DE PEDIDOS
    // =========================================================================
    public double addPedido(String email, String codigoArticulo, int cantidad)
            throws ClienteNoExiste, ArticuloNoExiste {

        Cliente cliente = datos.getCliente(email);
        if (cliente == null) {
            throw new ClienteNoExiste("El cliente con email: " + email + " no existe.");
        }

        Articulo articulo = datos.getArticulo(codigoArticulo);
        if (articulo == null) {
            throw new ArticuloNoExiste("El artículo con código: " + codigoArticulo + " no existe.");
        }

        Pedido nuevoPedido = new Pedido(cliente, articulo, cantidad, LocalDateTime.now());
        datos.addNumPedido(nuevoPedido);

        return nuevoPedido.calcularTotal();
    }

    // =========================================================================
    // DATOS DE PRUEBA
    // =========================================================================
    private void cargarDatosPrueba() {
        // Clientes
        addClienteEstandar("Ana García", "C/ Falsa 123", "11111111A", "ana@mail.com");
        addClientePremium("Luis Pérez", "Av. Principal 45", "22222222B", "luis@mail.com");

        // Artículos
        addArticulo("A001", "Monitor Curvo 27'", 199.99, 15.00, 5);
        addArticulo("B002", "Teclado Mecánico", 65.50, 5.00, 1);

        // Pedidos
        try {
            addPedido("ana@mail.com", "A001", 1);
            addPedido("luis@mail.com", "B002", 2);
        } catch (ClienteNoExiste | ArticuloNoExiste e) {
            System.err.println("Error al cargar datos de prueba: " + e.getMessage());
        }
    }
}
