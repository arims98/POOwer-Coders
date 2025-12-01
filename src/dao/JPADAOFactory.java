package dao;

/**
 * Factory JPA para crear instancias de repositorios que usan JPA/Hibernate
 */
public class JPADAOFactory implements DAOFactory {

    @Override
    public Repositorio<model.Cliente> getClienteRepositorio() {
        return new ClienteRepositorioJPA();
    }

    @Override
    public Repositorio<model.Articulo> getArticuloRepositorio() {
        return new ArticuloRepositorioJPA();
    }

    @Override
    public Repositorio<model.Pedido> getPedidoRepositorio() {
        return new PedidoRepositorioJPA();
    }
}
