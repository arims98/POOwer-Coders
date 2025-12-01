package dao;

public abstract class DAOFactory {

    public abstract Repositorio getArticuloRepositorio();
    public abstract Repositorio getClienteRepositorio();
    public abstract Repositorio getPedidoRepositorio();
}
