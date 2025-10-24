package model;
import java.util.*;


public class Datos {
    private final Map<String, Cliente> clientes = new HashMap<>();
    private final Map<String, Articulo> articulos = new HashMap<>();

    private final List<Pedido> pedidos = new ArrayList<>();
    public List<Cliente> getClientesFiltrados;

    private int contadorPedidos = 0;

//=====================================================================
// ARTÍCULOS
//=====================================================================

    public boolean addArticulo(Articulo a) {
        if (articulos.containsKey(a.getCodigo())) return false;
        articulos.put(a.getCodigo(), a);
        return true;
    }

    public Articulo getArticulo(String codigo) {
        return articulos.get(codigo);
    }

    public List<Articulo> getArticulos() {
        return new ArrayList<>(articulos.values());
    }

//=====================================================================
// CLIENTES
//=====================================================================

    public boolean addCliente(Cliente c) {
        if (clientes.containsKey(c.getEmail())) return false;
        clientes.put(c.getEmail(), c);
        return true;
    }

    public Cliente getCliente(String email) {
        return clientes.get(email);
    }



//*FILTRAR POR TIPO DE CLIENTE

    public List<Cliente> getClientesFiltrados(String tipo) {
        List<Cliente> filtrados = new ArrayList<>();
        for (Cliente c : clientes.values()) {
            if (tipo.equalsIgnoreCase("Estandar")) {
                if (c instanceof ClienteEstandar) {
                    filtrados.add(c);
                }
            } else if (tipo.equalsIgnoreCase("Premium")) {
                if (c instanceof ClientePremium) {
                    filtrados.add(c);
                }
            }

        }
        return filtrados;
    }
//=====================================================================
// PEDIDOS
//=====================================================================

    public Pedido addNumPedido(Pedido p) {
        p.setNumeroPedido(++contadorPedidos);
        pedidos.add(p);
        return p;
    }
    public Pedido getPedido(int numero) {
        for (Pedido p : pedidos)
            if (p.getNumeroPedido() == numero)
                return p;
            return null;
    }
        public boolean eliminarPedido(Pedido p) {
            return pedidos.remove(p);

    }
}










