//Controller: Es el intermediario entre la Vista y el Modelo.
// Recibe las acciones del usuario desde la Vista, procesa esa información y actualiza el Modelo o cambia la Vista.
// Controla el flujo de la aplicación y orquesta la interacción entre datos y presentación.
package controller;

import dao.PedidoRepositorio;
import java.time.LocalDateTime;
import java.time.Duration;
import java.util.List;
import model.Articulo;
import model.Cliente;
import model.Pedido;

public class PedidoControlador {

    //Atributos: El controlador mantiene una referencia al repositorio de pedidos para poder gestionar la creación, búsqueda, listado y eliminación.
    private PedidoRepositorio repositorio;

    //Inicializa el controlador con un repositorio de pedidos
    public PedidoControlador(PedidoRepositorio repositorio) {
        this.repositorio = repositorio;
    }

    // CREAR PEDIDO
    // Crea un nuevo pedido verificando que la cantidad sea válida y que no exista otro pedido con el mismo número
    public void crearPedido(String numeroPedido, Cliente cliente, Articulo articulo, int cantidad) throws Exception {
        if (cantidad <= 0) {
            // No se puede crear un pedido con cantidad <= 0
            throw new Exception("La cantidad debe ser mayor que cero.");
        } else if (this.repositorio.buscarPorId(numeroPedido) != null) {
            // No se puede crear un pedido si el número ya existe
            throw new Exception("Número de pedido ya existe.");
        } else {
            // Estado por defecto de un pedido nuevo
            String estado = "Pendiente de envío";
            // Creamos el objeto Pedido con la fecha actual
            Pedido pedido = new Pedido(numeroPedido, cliente, articulo, cantidad, LocalDateTime.now(), estado);
            // Y lo añadimos al repositorio
            this.repositorio.agregar(pedido);
        }
    }

    // LISTAR Y BUSCAR PEDIDOS
    public List<Pedido> listarPedidos() {
        return this.repositorio.listar();
    }
    // Busca un pedido específico por su número de pedido
    public Pedido buscarPedido(String numeroPedido) {
        return this.repositorio.buscarPorId(numeroPedido);
    }
    // Elimina un pedido por su número
    public void eliminarPedido(String numeroPedido) {
        this.repositorio.eliminar(numeroPedido);
    }

    // PEDIDOS PENDIENTES Y ENVIADOS
    // Devuelve SOLO los pedidos que están pendientes de envío
    public List<Pedido> listarPedidosPendientes() {
        return this.repositorio.listar().stream()
                .filter(p -> "Pendiente de envío".equalsIgnoreCase(p.getEstado()))
                .toList();
    }
    // Devuelve SOLO los pedidos que están enviados
    public List<Pedido> listarPedidosEnviados() {
        return this.repositorio.listar().stream()
                .filter(p -> "Enviado".equalsIgnoreCase(p.getEstado()))
                .toList();
    }
    // FILTRADO POR CLIENTE
    // Devuelve los pedidos pendientes de envío para un cliente específico utilizando el NIF
    public List<Pedido> listarPedidosPendientesPorCliente(String nifCliente) {
        return this.repositorio.listar().stream()
                .filter(p -> "Pendiente de envío".equalsIgnoreCase(p.getEstado()))
                .filter(p -> p.getCliente().getNif().equalsIgnoreCase(nifCliente))
                .toList();
    }
    // Devuelve los pedidos enviados para un cliente específico utilizando el NIF
    public List<Pedido> listarPedidosEnviadosPorCliente(String nifCliente) {
        return this.repositorio.listar().stream()
                .filter(p -> "Enviado".equalsIgnoreCase(p.getEstado()))
                .filter(p -> p.getCliente().getNif().equalsIgnoreCase(nifCliente))
                .toList();
    }
}
