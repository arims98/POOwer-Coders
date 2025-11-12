package controller;

import dao.Repositorio;
import model.Cliente;
import model.ClienteEstandar;
import model.ClientePremium;
import java.util.List;
import java.util.stream.Collectors;

public class ClienteControlador {
    
    private final Repositorio<Cliente> clienteRepo;

    // El constructor acepta la interfaz Repositorio<Cliente>
    public ClienteControlador(Repositorio<Cliente> clienteRepo) {
        this.clienteRepo = clienteRepo;
    }

    public void agregarCliente(String nombre, String domicilio, String nif, String email, String tipo) throws Exception {
        Cliente cliente;
        if (tipo.equalsIgnoreCase("E")) {
            cliente = new ClienteEstandar(nombre, domicilio, nif, email);
        } else if (tipo.equalsIgnoreCase("P")) {
            cliente = new ClientePremium(nombre, domicilio, nif, email);
        } else {
            throw new Exception("Tipo de cliente no válido. Use 'E' (Estándar) o 'P' (Premium).");
        }
        clienteRepo.agregar(cliente);
    }

    public List<Cliente> listarClientes() {
        return clienteRepo.listar();
    }

    public List<Cliente> listarClientesEstandar() {
        return clienteRepo.listar().stream()
                .filter(c -> c instanceof ClienteEstandar)
                .collect(Collectors.toList());
    }

    public List<Cliente> listarClientesPremium() {
        return clienteRepo.listar().stream()
                .filter(c -> c instanceof ClientePremium)
                .collect(Collectors.toList());
    }
}