package test;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import model.ListaClientes;
import model.ClienteEstandar;
import model.Cliente;

public class ListaClientesTest {

    private ListaClientes listaClientes;
    private final String EMAIL_ESTANDAR = "juan@e.com";

    @BeforeEach
    void setUp() {
        listaClientes = new ListaClientes();
        listaClientes.agregarCliente(new ClienteEstandar("Juan", "Calle Sol", "1A", EMAIL_ESTANDAR));
    }

    @Test
    void testBuscarClienteExistente() {
        Cliente clienteEncontrado = listaClientes.buscarClientePorEmail(EMAIL_ESTANDAR);
        assertNotNull(clienteEncontrado, "El cliente Estándar debería haber sido encontrado.");
    }
}