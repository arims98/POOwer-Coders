package dao;
import model.Articulo;
import model.Cliente;
import model.Pedido;

// Implementación concreta de la fábrica para MySQL
public class MySQLDAOFactory implements DAOFactory {
    
    @Override
    public Repositorio<Articulo> getArticuloRepositorio() {
        return new ArticuloRepositorio();
    }

    @Override
    public Repositorio<Cliente> getClienteRepositorio() {
        return new ClienteRepositorio();
    }

    @Override
    public Repositorio<Pedido> getPedidoRepositorio() {
        return new PedidoRepositorio();
    }
}