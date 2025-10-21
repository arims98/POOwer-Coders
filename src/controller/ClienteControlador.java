//Controller: Es el intermediario entre la Vista y el Modelo.
// Recibe las acciones del usuario desde la Vista, procesa esa información y actualiza el Modelo o cambia la Vista.
// Controla el flujo de la aplicación y orquesta la interacción entre datos y presentación.
package controller;

import dao.ClienteRepositorio;
import model.Cliente;
import model.ClienteEstandar;
import model.ClientePremium;
import java.util.List;
import java.util.stream.Collectors;

public class ClienteControlador {
    // Repositorio que gestiona la persistencia y acceso a los clientes
    private ClienteRepositorio repositorio;
    // Constructor que recibe un repositorio de clientes
    public ClienteControlador(ClienteRepositorio repositorio) {
        this.repositorio = repositorio;
    }
    public void agregarCliente(String nombre, String domicilio, String nif, String email, String tipo) throws Exception {
        // Verifica si ya existe un cliente con el mismo NIF
        if (repositorio.buscarPorId(nif) != null)
            throw new Exception("El cliente con ese NIF ya existe.");

        // Crea el cliente dependiendo del tipo
        Cliente c = tipo.equalsIgnoreCase("P")
                ? new ClientePremium(nombre, domicilio, nif, email) // Cliente Premium
                : new ClienteEstandar(nombre, domicilio, nif, email); // Cliente Estándar

        repositorio.agregar(c);
    }

    // Devuelve la lista completa de clientes
    public List<Cliente> listarClientes() {
        return repositorio.listar();
    }
    // Devuelve solo los clientes estándar
    public List<Cliente> listarClientesEstandar() {
        return repositorio.listar().stream()
                .filter(c -> c instanceof ClienteEstandar) // Filtra por tipo Estándar
                .collect(Collectors.toList());
    }
    //Devuelve solo los clientes premium
    public List<Cliente> listarClientesPremium() {
        return repositorio.listar().stream()
                .filter(c -> c instanceof ClientePremium) // Filtra por tipo Premium
                .collect(Collectors.toList());
    }
}
