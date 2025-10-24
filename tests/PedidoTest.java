import model.Articulo;
import model.Cliente;
import model.ClienteEstandar;
import model.Pedido;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.time.LocalDateTime;

class PedidoTest {

    @Test
    void calcularTotal() {
        Cliente c = new ClienteEstandar("Juan", "Calle Falsa 123", "12345678A", "juan@mail.com");
        Articulo a = new Articulo("A1", "Monitor", 200.0, 20.0, 5);
        Pedido p = new Pedido(c, a, 2, LocalDateTime.now());

        double totalEsperado = 200.0 * 2 + 20.0;
        assertEquals(totalEsperado, p.calcularTotal(), 0.01);
    }

    @Test
    void pedidoEnviado() throws InterruptedException {
        Cliente c = new ClienteEstandar("Juan", "Calle Falsa 123", "12345678A", "juan@mail.com");
        Articulo a = new Articulo("A1", "Monitor", 200.0, 20.0, 0); // tiempo 0 = enviado inmediato
        Pedido p = new Pedido(c, a, 1, LocalDateTime.now().minusMinutes(1));

        assertTrue(p.pedidoEnviado());
    }
}
