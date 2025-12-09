package controller;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import dao.Repositorio;
import model.Articulo;
import model.Cliente;
import model.Pedido;

public class PedidoControlador {
    
    private final Repositorio<Pedido> pedidoRepo;

    // El constructor acepta la interfaz Repositorio<Pedido>
    public PedidoControlador(Repositorio<Pedido> pedidoRepo) {
        this.pedidoRepo = pedidoRepo;
    }

    public String obtenerSiguienteNumeroPedido() {
        int max = 0;
        for (Pedido p : pedidoRepo.listar()) {
            String codigo = p.getNumeroPedido(); // Ej: "P01"

            if (codigo != null && codigo.matches("P\\d+")) {
                int num = Integer.parseInt(codigo.substring(1)); // Ignora la P
                if (num > max) max = num;
            } else {
                System.out.println("Número de pedido inválido: " + codigo);
            }
        }
        return String.format("P%02d", max + 1);
    }

    public void crearPedido(String numeroPedido, Cliente cliente, Articulo articulo, int cantidad) throws Exception {
        if (cantidad <= 0) {
            throw new Exception("La cantidad debe ser mayor que cero.");
        }
        String numeroFinal = (numeroPedido == null || numeroPedido.trim().isEmpty()) ?
                             obtenerSiguienteNumeroPedido() :
                             numeroPedido;
        Pedido nuevoPedido = new Pedido(numeroFinal, cliente, articulo, cantidad, LocalDateTime.now());
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

        // REGLA: solo puede eliminarse si está pendiente
        if (!"Pendiente de envío".equalsIgnoreCase(pedido.getEstado())) {
            throw new Exception("No se puede eliminar un pedido que ya está enviado.");
        }

        pedidoRepo.eliminar(numeroPedido);
    }

    public List<Pedido> listarPedidosPendientes() {
        return pedidoRepo.listar().stream()
                .filter(p -> p.getEstado().equals("Pendiente de envío"))
                .collect(Collectors.toList());
    }

    public List<Pedido> listarPedidosEnviados() {
    return pedidoRepo.listar().stream()
            .filter(p -> "Enviado".equalsIgnoreCase(p.getEstado()))
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