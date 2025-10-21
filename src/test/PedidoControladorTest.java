//Carpeta para los Test de JUNIT
package test;

import controller.PedidoControlador;
import dao.PedidoRepositorio;
import model.*;
import org.junit.jupiter.api.Assertions; //JUnit 5 Librerias
import org.junit.jupiter.api.BeforeEach; //JUnit 5 Librerias
import org.junit.jupiter.api.Test; //JUnit 5 Librerias

import java.time.LocalDateTime;

class PedidoControladorTest {
    private PedidoRepositorio repositorio; // Repositorio de pedidos para las pruebas
    private PedidoControlador controlador;  // Controlador que vamos a probar

    // Metodo que se ejecuta antes de cada test
    @BeforeEach
    void setUp() {
        this.repositorio = new PedidoRepositorio(); // Creamos un nuevo repositorio limpio
        this.controlador = new PedidoControlador(this.repositorio); // Inicializamos el controlador con el repositorio
        this.cargarDatosPrueba(); // Cargamos datos iniciales de prueba
    }

    // Carga datos de prueba: 4 clientes y 4 pedidos
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

    // Test: Crear un pedido válido
    @Test
    void testCrearPedidoCorrecto() throws Exception {
        Cliente cliente = new ClienteEstandar("Paco Lopez", "Calle Galaxia 12", "456789123F", "pacol@uoc.edu");
        Articulo articulo = new Articulo("A05", "Tablet", 150, 7, 3);
        this.controlador.crearPedido("P05", cliente, articulo, 0);
        Assertions.assertEquals(5, this.controlador.listarPedidos().size()); // Comprobamos que hay 5 pedidos
        // Verificamos que el último pedido creado tiene el número correcto
        Assertions.assertEquals("P05", ((Pedido)this.controlador.listarPedidos().get(4)).getNumeroPedido());
    }
    // Test: Crear un pedido con cantidad inválida (0)
    @Test
    void testCrearPedidoCantidadInvalida() {
        Cliente cliente = new ClienteEstandar("Cliente Error", "Calle Horrible 1", "66666666S", "error@uoc.edu");
        Articulo articulo = new Articulo("A06", "Altavoz", 45, 5, 2);
        // Comprobamos que lanza una excepción
        Exception exception = Assertions.assertThrows(Exception.class, () -> {
            this.controlador.crearPedido("P06", cliente, articulo, 0); // Cantidad 0 => inválida!!
        });
        // Verificamos el mensaje de la excepción
        Assertions.assertEquals("La cantidad debe ser mayor que cero.", exception.getMessage());
    }

    // Test: Eliminar un pedido
    @Test
    void testEliminarPedido() throws Exception {
        Assertions.assertEquals(4, this.controlador.listarPedidos().size());
        this.controlador.eliminarPedido("P02"); // Eliminamos el pedido P02
        Assertions.assertEquals(3, this.controlador.listarPedidos().size()); // Comprobamos que ahora hay 3 pedidos
        Assertions.assertNull(this.controlador.buscarPedido("P02"));
    }
}
