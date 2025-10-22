package controlador;

import modelo.*;

import java.util.List;

public class Controlador {
    private final Datos datos;
    public Controlador(Datos datos) { this.datos = datos; }

    public void cargarDemo() { datos.cargarDemo(); }

    // Clientes
    public void altaClienteEstandar(String nif, String nombre, String domicilio, String email) {
        datos.altaCliente(new ClienteEstandar(nombre, domicilio, nif, email));
    }
    public void altaClientePremium(String nif, String nombre, String domicilio, String email) {
        datos.altaCliente(new ClientePremium(nombre, domicilio, nif, email));
    }
    public List<Cliente> listarClientes() { return datos.listarClientes(); }
    public void bajaCliente(String nif) { datos.bajaCliente(nif); }

    // Artículos
    public void altaArticulo(String codigo, String descripcion, double precio, double envio, int prep) {
        datos.altaArticulo(new Articulo(codigo, descripcion, precio, envio, prep));
    }
    public List<Articulo> listarArticulos() { return datos.listarArticulos(); }

    // Pedidos
    public double crearPedido(String nif, String codigoArticulo, int cantidad) {
        return datos.crearPedido(nif, codigoArticulo, cantidad);
    }
    public List<Pedido> listarPedidos() { return datos.listarPedidos(); }
}

