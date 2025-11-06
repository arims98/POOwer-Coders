
package dao;

public class DAOFactory {

	// Instancias singleton para reutilización simple
	private static final IClienteDAO CLIENTE_DAO = new ClienteDAO_JDBC();
	private static final IArticuloDAO ARTICULO_DAO = new ArticuloDAO_JDBC();
	private static final IPedidoDAO PEDIDO_DAO = new PedidoDAO_JDBC();

	public static IClienteDAO getClienteDAO() {
		return CLIENTE_DAO;
	}

	public static IArticuloDAO getArticuloDAO() {
		return ARTICULO_DAO;
	}

	public static IPedidoDAO getPedidoDAO() {
		return PEDIDO_DAO;
	}
}
