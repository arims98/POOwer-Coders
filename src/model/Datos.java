package model;

import dao.DAOFactory;
import dao.Repositorio;

// La clase Datos ahora es la que conoce la FÃ¡brica
public class Datos {
    
    private Repositorio<Articulo, Integer> repoArticulo;
    private Repositorio<Cliente, String> repoCliente;
    private Repositorio<Pedido, Integer> repoPedido;

    // Constructor: Inicializa los DAOs pidiÃ©ndoselos a la FÃ¡brica
    public Datos() {
        this.repoArticulo = DAOFactory.getArticuloDAO();
        this.repoCliente = DAOFactory.getClienteDAO();
        this.repoPedido = DAOFactory.getPedidoDAO();
    }
    
    // --- MÃ‰TODOS GETTERS PARA ACCEDER A LOS DAOs ---
    public Repositorio<Articulo, Integer> getRepoArticulo() {
        return repoArticulo;
    }

    public Repositorio<Cliente, String> getRepoCliente() {
        return repoCliente;
    }

    public Repositorio<Pedido, Integer> getRepoPedido() {
        return repoPedido;
    }
    
    // El mÃ©todo cargarDatosDemo() ya no es necesario
    // porque los datos ya estÃ¡n en la base de datos (los INSERTs del script SQL).
}