package modelo;

import java.time.LocalDateTime;
import java.util.*;

public class Datos {
    private final Map<String, Cliente> clientes = new HashMap<>();   // nif -> Cliente
    private final Map<String, Articulo> articulos = new HashMap<>(); // codigo -> Articulo
    private final List<Pedido> pedidos = new ArrayList<>();

    // ------- Clientes -------
    public void altaCliente(Cliente c) {
        Objects.requireNonNull(c, "Cliente requerido");
        if (clientes.containsKey(c.getNif())) throw new DuplicadoException("Ya existe el cliente " + c.getNif());
        clientes.put(c.getNif(), c);
    }
    public Cliente verCliente(String nif) {
        Cliente c = clientes.get(nif);
        if (c == null) throw new NoEncontradoException("No existe cliente " + nif);
        return c;
    }
    public List<Cliente> listarClientes() { return new ArrayList<>(clientes.values()); }
    public void bajaCliente(String nif) {
        if (clientes.remove(nif) == null) throw new NoEncontradoException("No existe cliente " + nif);
    }

    // ------- Artículos -------
    public void altaArticulo(Articulo a) {
        Objects.requireNonNull(a, "Artículo requerido");
        if (articulos.containsKey(a.getCodigo())) throw new DuplicadoException("Ya existe el artículo " + a.getCodigo());
        articulos.put(a.getCodigo(), a);
    }
    public Articulo verArticulo(String codigo) {
        Articulo a = articulos.get(codigo);
        if (a == null) throw new NoEncontradoException("No existe artículo " + codigo);
        return a;
    }
    public List<Articulo> listarArticulos() { return new ArrayList<>(articulos.values()); }

    // ------- Pedidos (1 artículo por pedido, según tu clase) -------
    public double crearPedido(String nifCliente, String codigoArticulo, int cantidad) {
        if (cantidad <= 0) throw new NegocioException("La cantidad debe ser > 0");
        Cliente cliente = verCliente(nifCliente);
        Articulo articulo = verArticulo(codigoArticulo);

        String numeroPedido = "P-" + (pedidos.size() + 1);
        Pedido p = new Pedido(numeroPedido, cliente, articulo, cantidad, LocalDateTime.now());
        double total = p.calcularTotal();
        pedidos.add(p);
        return total;
    }
    public List<Pedido> listarPedidos() { return Collections.unmodifiableList(pedidos); }

    // ------- Datos de demostración (opcional) -------
    public void cargarDemo() {
        altaArticulo(new Articulo("A-001", "Teclado", 25.99, 3.50, 2));
        altaArticulo(new Articulo("A-002", "Ratón", 15.50, 2.99, 1));
        altaArticulo(new Articulo("A-003", "Monitor 24\"", 149.99, 6.99, 3));

        altaCliente(new ClienteEstandar("Meri", "C/ Luna 1", "111A", "meri@onlinestore.com"));
        altaCliente(new ClientePremium("Joan", "Av. Mar 3", "222B", "joan@onlinestore.com"));
    }
}
