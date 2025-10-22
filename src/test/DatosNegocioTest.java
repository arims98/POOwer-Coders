package test;

import modelo.*;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class DatosNegocioTest {

    @Test
    void pedido_aplica_descuento_solo_en_envio_para_premium() {
        Datos datos = new Datos();
        datos.altaArticulo(new Articulo("A", "Algo", 100.0, 10.0, 1));
        datos.altaCliente(new ClientePremium("Pepe", "Calle X", "1", "p@x"));

        // Total esperado: (precio 100 + envío 10*(1-0.2)=8) * 2 = 216
        double total = datos.crearPedido("1", "A", 2);
        assertEquals(216.0, total, 1e-6);
    }

    @Test
    void alta_cliente_duplicado_lanza_excepcion() {
        Datos datos = new Datos();
        datos.altaCliente(new ClienteEstandar("Ana", "C/ Sol", "X", "a@x"));
        assertThrows(DuplicadoException.class,
                () -> datos.altaCliente(new ClienteEstandar("Ana2", "C/ Luna", "X", "b@x")));
    }
}

