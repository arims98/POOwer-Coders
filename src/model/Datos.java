package model;

import dao.DAOFactory;
import dao.Repositorio;

// La clase Datos ahora es la que conoce la Fábrica
public class Datos {
    
    private Repositorio<Articulo, Integer> repoArticulo;
    private Repositorio<Cliente, String> repoCliente;
    private Repositorio<Pedido, Integer> repoPedido;

    // Constructor: Inicializa los DAOs pidiéndoselos a la Fábrica
    public Datos() {
        this.repoArticulo = DAOFactory.getArticuloDAO();
        this.repoCliente = DAOFactory.getClienteDAO();
        this.repoPedido = DAOFactory.getPedidoDAO();
    }
    
    // --- MÉTODOS GETTERS PARA ACCEDER A LOS DAOs ---
    public Repositorio<Articulo, Integer> getRepoArticulo() {
        return repoArticulo;
    }

    public Repositorio<Cliente, String> getRepoCliente() {
        return repoCliente;
    }

    public Repositorio<Pedido, Integer> getRepoPedido() {
        return repoPedido;
    }
    
    // El método cargarDatosDemo() ya no es necesario
    // porque los datos ya están en tu base de datos (los INSERTs del script SQL).
}