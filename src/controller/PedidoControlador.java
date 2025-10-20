package controller;

import dao.PedidoRepositorio;
import java.time.LocalDateTime;
import java.util.List;
import model.Articulo;
import model.Cliente;
import model.Pedido;

public class PedidoControlador {
    private PedidoRepositorio repositorio;

    public PedidoControlador(PedidoRepositorio repositorio) {
        this.repositorio = repositorio;
    }

    public void crearPedido(String numeroPedido, Cliente cliente, Articulo articulo, int cantidad) throws Exception {
        if (cantidad <= 0) {
            throw new Exception("La cantidad debe ser mayor que cero.");
        } else if (this.repositorio.buscarPorId(numeroPedido) != null) {
            throw new Exception("Número de pedido ya existe.");
        } else {
            Pedido pedido = new Pedido(numeroPedido, cliente, articulo, cantidad, LocalDateTime.now());
            this.repositorio.agregar(pedido);
        }
    }

    public void crearPedido(Pedido pedido) throws Exception {
        if (pedido.getCantidad() <= 0) {
            throw new Exception("La cantidad del pedido debe ser mayor que cero.");
        } else if (this.repositorio.buscarPorId(pedido.getNumeroPedido()) != null) {
            throw new Exception("El número de pedido ya existe.");
        } else {
            this.repositorio.agregar(pedido);
        }
    }

    public List<Pedido> listarPedidos() {
        return this.repositorio.listar();
    }

    public Pedido buscarPedido(String numeroPedido) {
        return this.repositorio.buscarPorId(numeroPedido);
    }

    public void eliminarPedido(String numeroPedido) {
        this.repositorio.eliminar(numeroPedido);
    }
}