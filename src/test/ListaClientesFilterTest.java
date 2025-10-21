package test;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import model.ListaClientes;
import model.ClientePremium;

public class ListaClientesFilterTest {

    private ListaClientes listaClientes;
    private final String EMAIL_PREMIUM = "ana@e.com";

    @BeforeEach
    void setUp() {
        listaClientes = new ListaClientes();
        listaClientes.agregarCliente(new ClientePremium("Ana", "Av Luna", "2B", EMAIL_PREMIUM));
    }

    @Test
    void testFiltrarClientesPremium() {
        int cantidadPremium = listaClientes.getClientesPremium().size();
        assertEquals(1, cantidadPremium, "La lista de Premium debe contener exactamente 1 cliente.");
    }
}
