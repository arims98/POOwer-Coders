package dao;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import model.Articulo;
import model.ClienteEstandar;
import model.ClientePremium;
import model.Pedido;

public class PedidoRepositorio implements Repositorio<Pedido> {
    private List<Pedido> pedidos = new ArrayList();

    public PedidoRepositorio() {
    }

    public void agregar(Pedido pedido) {
        this.pedidos.add(pedido);
    }

    public Pedido buscarPorId(String numeroPedido) {
        Iterator var2 = this.pedidos.iterator();

        Pedido p;
        do {
            if (!var2.hasNext()) {
                return null;
            }

            p = (Pedido)var2.next();
        } while(!p.getNumeroPedido().equals(numeroPedido));

        return p;
    }

    public List<Pedido> listar() {
        return this.pedidos;
    }

    public void eliminar(String numeroPedido) {
        this.pedidos.removeIf((p) -> {
            return p.getNumeroPedido().equals(numeroPedido);
        });
    }

    public void cargarDatosPrueba() {
        ClienteEstandar cliente1 = new ClienteEstandar("Sergio Gomez Gutierrez", "Calle Luna 3, Barcelona", "12345678A", "sgomezg@uoc.edu");
        Articulo articulo1 = new Articulo("A01", "Auriculares Bluetooth", 60.0, 5.0, 5);
        Pedido pedido1 = new Pedido("P01", cliente1, articulo1, 3, LocalDateTime.now());
        this.agregar(pedido1);
        ClientePremium cliente2 = new ClientePremium("Ariadna Martínez Serra", "Av. Sol 10, Barcelona", "87654321B", "amartinezs@uoc.edu");
        Articulo articulo2 = new Articulo("A02", "Teclado ", 80.0, 5.0, 5);
        Pedido pedido2 = new Pedido("P02", cliente2, articulo2, 2, LocalDateTime.now());
        this.agregar(pedido2);
        ClienteEstandar cliente3 = new ClienteEstandar("Meritxell Moreno Moya", "Calle Estrella 5, Barcelona", "23456789C", "mmorenom@uoc.edu");
        Articulo articulo3 = new Articulo("A03", "Ratón Inalámbrico", 20.0, 5.0, 5);
        Pedido pedido3 = new Pedido("P03", cliente3, articulo3, 1, LocalDateTime.now());
        this.agregar(pedido3);
        ClientePremium cliente4 = new ClientePremium("Cèlia Trullà Estruch", "Plaza Mayor 12, Barcelona", "98765432D", "ctrullae@uoc.edu");
        Articulo articulo4 = new Articulo("A04", "Monitor 24 pulgadas", 12.0, 10.0, 10);
        Pedido pedido4 = new Pedido("P04", cliente4, articulo4, 1, LocalDateTime.now());
        this.agregar(pedido4);
    }
}