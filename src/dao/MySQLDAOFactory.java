package dao;

import jakarta.persistence.EntityManager;

public class MySQLDAOFactory extends DAOFactory {

    private final EntityManager em;

    public MySQLDAOFactory(EntityManager em) {
        this.em = em;
    }

    @Override
    public Repositorio getArticuloRepositorio() {
        return new ArticuloRepositorio(em);
    }

    @Override
    public Repositorio getClienteRepositorio() {
        return new ClienteRepositorio(em);
    }

    @Override
    public Repositorio getPedidoRepositorio() {
        return new PedidoRepositorio(em);
    }
}
