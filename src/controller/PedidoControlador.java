package controller;

import dao.Repositorio;
import model.Pedido;
import model.Cliente;
import model.Articulo;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

public class PedidoControlador {
    
    private final Repositorio<Pedido> pedidoRepo;

    // El constructor acepta la interfaz Repositorio<Pedido>
    public PedidoControlador(Repositorio<Pedido> pedidoRepo) {
        this.pedidoRepo = pedidoRepo;
    }

    public void crearPedido(String numeroPedido, Cliente cliente, Articulo articulo, int cantidad) throws Exception {
        if (cantidad <= 0) {
            throw new Exception("La cantidad debe ser mayor que cero.");
        }
        Pedido nuevoPedido = new Pedido(numeroPedido, cliente, articulo, cantidad, LocalDateTime.now());
        pedidoRepo.agregar(nuevoPedido);
    }

    public List<Pedido> listarPedidos() {
        return pedidoRepo.listar();
    }

    public Pedido buscarPedido(String numeroPedido) {
        // En el controlador, buscamos en la lista completa obtenida del repositorio.
        // En la implementación JDBC del repositorio, podrías buscar directamente en la DB.
        return pedidoRepo.listar().stream()
                .filter(p -> p.getNumeroPedido().equalsIgnoreCase(numeroPedido))
                .findFirst().orElse(null);
    }

    public void eliminarPedido(String numeroPedido) throws Exception {
        Pedido pedido = buscarPedido(numeroPedido);
        if (pedido == null) {
            throw new Exception("Pedido no encontrado.");
        }

        long diffMinutos = java.time.Duration.between(pedido.getFechaHora(), LocalDateTime.now()).toMinutes();
        if (diffMinutos >= pedido.getArticulo().getTiempoPreparacion()) {
            throw new Exception("No se puede eliminar. El pedido ya ha pasado su tiempo de preparación y se asume enviado.");
        }

        // Llamada al DAO/Repositorio para ejecutar la eliminación por ID
        pedidoRepo.eliminar(numeroPedido); 
    }

    public List<Pedido> listarPedidosPendientes() {
        return pedidoRepo.listar().stream()
                .filter(p -> p.getEstado().equals("Pendiente de envío"))
                .collect(Collectors.toList());
    }

    public List<Pedido> listarPedidosEnviados() {
        return pedidoRepo.listar().stream()
                .filter(p -> java.time.Duration.between(p.getFechaHora(), LocalDateTime.now()).toMinutes() >= p.getArticulo().getTiempoPreparacion())
                .peek(p -> p.setEstado("Enviado")) 
                .collect(Collectors.toList());
    }

    public List<Pedido> listarPedidosPendientesPorCliente(String nifCliente) {
        return listarPedidosPendientes().stream()
                .filter(p -> p.getCliente().getNif().equalsIgnoreCase(nifCliente))
                .collect(Collectors.toList());
    }

    public List<Pedido> listarPedidosEnviadosPorCliente(String nifCliente) {
        return listarPedidosEnviados().stream()
                .filter(p -> p.getCliente().getNif().equalsIgnoreCase(nifCliente))
                .collect(Collectors.toList());
    }
}