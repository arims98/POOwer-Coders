package test;

import controller.PedidoControlador;
import dao.PedidoRepositorio;
import model.*;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

class PedidoControladorTest {
    private PedidoRepositorio repositorio;
    private PedidoControlador controlador;

    PedidoControladorTest() {
    }

    @BeforeEach
    void setUp() {
        this.repositorio = new PedidoRepositorio();
        this.controlador = new PedidoControlador(this.repositorio);
        this.cargarDatosPrueba();
    }

    private void cargarDatosPrueba() {
        ClienteEstandar cliente1 = new ClienteEstandar("Sergio Gomez Gutierrez", "Calle Luna 3, Barcelona", "12345678A", "sgomezg@uoc.edu");
        Articulo articulo1 = new Articulo("A01", "Auriculares Bluetooth", 60.0, 5.0, 5);
        Pedido pedido1 = new Pedido("P01", cliente1, articulo1, 3, LocalDateTime.now());
        this.repositorio.agregar(pedido1);
        ClientePremium cliente2 = new ClientePremium("Ariadna Martínez Serra", "Av. Sol 10, Barcelona", "87654321B", "amartinezs@uoc.edu");
        Articulo articulo2 = new Articulo("A02", "Teclado", 80.0, 5.0, 5);
        Pedido pedido2 = new Pedido("P02", cliente2, articulo2, 2, LocalDateTime.now());
        this.repositorio.agregar(pedido2);
        ClienteEstandar cliente3 = new ClienteEstandar("Meritxell Moreno Moya", "Calle Estrella 5, Barcelona", "23456789C", "mmorenom@uoc.edu");
        Articulo articulo3 = new Articulo("A03", "Ratón Inalámbrico", 20.0, 5.0, 5);
        Pedido pedido3 = new Pedido("P03", cliente3, articulo3, 1, LocalDateTime.now());
        this.repositorio.agregar(pedido3);
        ClientePremium cliente4 = new ClientePremium("Cèlia Trullà Estruch", "Plaza Mayor 12, Barcelona", "98765432D", "ctrullae@uoc.edu");
        Articulo articulo4 = new Articulo("A04", "Monitor 24 pulgadas", 12.0, 10.0, 10);
        Pedido pedido4 = new Pedido("P04", cliente4, articulo4, 1, LocalDateTime.now());
        this.repositorio.agregar(pedido4);
    }

    @Test
    void testCrearPedidoCorrecto() throws Exception {
        Cliente cliente = new ClienteEstandar("Nuevo Cliente", "Calle Falsa 123", "55555555F", "nuevo@mail.com");
        Articulo articulo = new Articulo("A05", "Tablet", 150.0, 7.0, 3);
        this.controlador.crearPedido("P05", cliente, articulo, 2);
        Assertions.assertEquals(5, this.controlador.listarPedidos().size());
        Assertions.assertEquals("P05", ((Pedido)this.controlador.listarPedidos().get(4)).getNumeroPedido());
    }

    @Test
    void testCrearPedidoCantidadInvalida() {
        Cliente cliente = new ClienteEstandar("Cliente Error", "Calle Error 1", "66666666G", "error@mail.com");
        Articulo articulo = new Articulo("A06", "Altavoz", 45.0, 5.0, 2);
        Exception exception = (Exception)Assertions.assertThrows(Exception.class, () -> {
            this.controlador.crearPedido("P06", cliente, articulo, 0);
        });
        Assertions.assertEquals("La cantidad debe ser mayor que cero.", exception.getMessage());
    }

    @Test
    void testEliminarPedido() throws Exception {
        Assertions.assertEquals(4, this.controlador.listarPedidos().size());
        this.controlador.eliminarPedido("P02");
        Assertions.assertEquals(3, this.controlador.listarPedidos().size());
        Assertions.assertNull(this.controlador.buscarPedido("P02"));
    }
}