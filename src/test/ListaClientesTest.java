package test; // Asegúrate de que este paquete coincida con la ubicación de tu archivo test

import static org.junit.jupiter.api.Assertions.*; // Importa los métodos para hacer verificaciones (Assertions)

import org.junit.jupiter.api.BeforeEach; // Importa la anotación para la configuración inicial
import org.junit.jupiter.api.Test; // Importa la anotación para marcar un método de prueba

import model.ListaClientes;
import model.ClienteEstandar;
import model.ClientePremium;
import model.Cliente;

public class ListaClientesTest {
    
    // Declaramos el objeto que vamos a probar para que sea accesible en todos los tests
    private ListaClientes listaClientes;
    private final String EMAIL_ESTANDAR = "juan@e.com";
    private final String EMAIL_PREMIUM = "ana@e.com";

    /**
     * Este método se ejecuta antes de CADA prueba (@Test) para garantizar que
     * la lista siempre empieza con el mismo estado limpio y conocido.
     */
    @BeforeEach 
    void setUp() {
        // 1. Creamos una listaClientes nueva
    listaClientes = new ListaClientes();

    // 2. Añadimos datos de prueba conocidos a esa lista
    listaClientes.agregarCliente(new ClienteEstandar("Juan", "Calle Sol", "1A", EMAIL_ESTANDAR));
    listaClientes.agregarCliente(new ClientePremium("Ana", "Av Luna", "2B", EMAIL_PREMIUM));
    }

    /**
     * Prueba si el método buscarCliente() encuentra un cliente existente.
     */
    @Test
    void testBuscarClienteExistente() {
        // Usamos assertNotNull() para verificar que el resultado de la búsqueda NO es nulo
    Cliente clienteEncontrado = listaClientes.buscarClientePorEmail(EMAIL_ESTANDAR);
        assertNotNull(clienteEncontrado, "El cliente Estándar debería haber sido encontrado.");
    }
    
    /**
     * Prueba si el método buscarCliente() gestiona correctamente un cliente que no existe.
     */
    @Test
    void testBuscarClienteNoExistente() {
        // Usamos assertNull() para verificar que el resultado de la búsqueda es nulo
    assertNull(listaClientes.buscarClientePorEmail("error@e.com"), "No se debe encontrar un cliente con email erróneo.");
    }
    
    /**
     * Prueba si el método getClientesPremium() filtra correctamente.
     */
    @Test
    void testFiltrarClientesPremium() {
        // Usamos assertEquals() para verificar que el tamaño de la lista filtrada es 1
        int cantidadPremium = listaClientes.getClientesPremium().size();
        assertEquals(1, cantidadPremium, "La lista de Premium debe contener exactamente 1 cliente.");
    }
    
    /**
     * Prueba si el método getClientesEstandar() filtra correctamente.
     */
    @Test
    void testFiltrarClientesEstandar() {
        // Usamos assertEquals() para verificar que el tamaño de la lista filtrada es 1
        int cantidadEstandar = listaClientes.getClientesEstandar().size();
        assertEquals(1, cantidadEstandar, "La lista de Estándar debe contener exactamente 1 cliente.");
    }
}