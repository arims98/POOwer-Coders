package dao;

import util.Conexion;
import model.Articulo;
import model.Cliente;
import model.Pedido;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

// Implementación del Repositorio de Pedidos para MySQL
public class PedidoRepositorio implements Repositorio<Pedido> {

    // CONSULTAS SQL
    // Se utiliza el procedimiento almacenado para la inserción para aplicar transacciones
    private static final String SQL_CALL_INSERT = "{CALL SP_INSERTAR_PEDIDO(?, ?, ?, ?)}";
    private static final String SQL_SELECT_ALL = "SELECT p.*, c.nombre, c.domicilio, c.email, c.tipo, a.descripcion, a.precio_venta, a.gastos_envio, a.tiempo_preparacion " +
                                                 "FROM PEDIDO p JOIN CLIENTE c ON p.cliente_nif = c.nif " +
                                                 "JOIN ARTICULO a ON p.articulo_codigo = a.codigo";
    private static final String SQL_SELECT_BY_ID = SQL_SELECT_ALL + " WHERE p.numero_pedido = ?";
    private static final String SQL_CALL_DELETE = "{CALL SP_ELIMINAR_PEDIDO(?)}"; // Procedimiento para DELETE

    /**
     * Helper para mapear un ResultSet a un objeto Pedido
     * Necesita un ArticuloRepositorio y ClienteRepositorio para reconstruir los objetos.
     */
    private Pedido mapearPedido(ResultSet rs) throws SQLException {
        // Se necesitan los DAOs para reconstruir Articulo y Cliente
        ArticuloRepositorio articuloRepo = new ArticuloRepositorio();
        ClienteRepositorio clienteRepo = new ClienteRepositorio();

        // Obtener IDs
        String nifCliente = rs.getString("cliente_nif");
        String codArticulo = rs.getString("articulo_codigo");

        // Buscar objetos completos (podríamos optimizar leyendo los datos de los JOIN, pero
        // para mantener la integridad, llamamos a los repositorios)
        Cliente cliente = clienteRepo.buscarPorId(nifCliente);
        Articulo articulo = articuloRepo.buscarPorId(codArticulo);

        if (cliente == null || articulo == null) {
             throw new SQLException("Error de integridad de datos: Cliente o Artículo no encontrado para el pedido " + rs.getString("numero_pedido"));
        }
        
        // Mapeo de Pedido
        return new Pedido(
            rs.getString("numero_pedido"),
            cliente,
            articulo,
            rs.getInt("cantidad"),
            rs.getTimestamp("fecha_hora").toLocalDateTime(), // Convertir TIMESTAMP a LocalDateTime
            rs.getString("estado")
        );
    }

    @Override
    public void agregar(Pedido pedido) {
        Connection conn = null;
        CallableStatement cs = null;
        try {
            conn = Conexion.getConnection();
            // 1. Usar CallableStatement para llamar al procedimiento almacenado
            cs = conn.prepareCall(SQL_CALL_INSERT); 
            
            // 2. Parámetros del procedimiento
            cs.setString(1, pedido.getNumeroPedido());
            cs.setString(2, pedido.getCliente().getNif());
            cs.setString(3, pedido.getArticulo().getCodigo());
            cs.setInt(4, pedido.getCantidad());
            
            cs.executeUpdate();
            System.out.println("Pedido " + pedido.getNumeroPedido() + " agregado usando Transacción.");

        } catch (SQLException e) {
            System.err.println("Error al insertar pedido (transacción fallida): " + e.getMessage());
        } finally {
            try { if (cs != null) cs.close(); } catch (SQLException e) { /* Ignorar */ }
            Conexion.close(conn);
        }
    }

    @Override
    public Pedido buscarPorId(String numeroPedido) {
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        Pedido pedido = null;
        try {
            conn = Conexion.getConnection();
            ps = conn.prepareStatement(SQL_SELECT_BY_ID);
            ps.setString(1, numeroPedido);
            rs = ps.executeQuery();

            if (rs.next()) {
                pedido = mapearPedido(rs);
            }
        } catch (SQLException e) {
            System.err.println("Error al buscar pedido: " + e.getMessage());
        } finally {
            try { if (rs != null) rs.close(); } catch (SQLException e) { /* Ignorar */ }
            try { if (ps != null) ps.close(); } catch (SQLException e) { /* Ignorar */ }
            Conexion.close(conn);
        }
        return pedido;
    }

    @Override
    public List<Pedido> listar() {
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        List<Pedido> pedidos = new ArrayList<>();
        try {
            conn = Conexion.getConnection();
            ps = conn.prepareStatement(SQL_SELECT_ALL);
            rs = ps.executeQuery();

            while (rs.next()) {
                pedidos.add(mapearPedido(rs));
            }
        } catch (SQLException e) {
            System.err.println("Error al listar pedidos: " + e.getMessage());
        } finally {
            try { if (rs != null) rs.close(); } catch (SQLException e) { /* Ignorar */ }
            try { if (ps != null) ps.close(); } catch (SQLException e) { /* Ignorar */ }
            Conexion.close(conn);
        }
        return pedidos;
    }

    @Override
    public void eliminar(String numeroPedido) {
        Connection conn = null;
        CallableStatement cs = null;
        try {
            conn = Conexion.getConnection();
            // 1. Usar CallableStatement para llamar al procedimiento de eliminación
            cs = conn.prepareCall(SQL_CALL_DELETE); 
            
            // 2. Parámetros del procedimiento
            cs.setString(1, numeroPedido);
            
            cs.executeUpdate();
            
        } catch (SQLException e) {
            System.err.println("Error al eliminar pedido: " + e.getMessage());
        } finally {
            try { if (cs != null) cs.close(); } catch (SQLException e) { /* Ignorar */ }
            Conexion.close(conn);
        }
    }
    
    /**
     * NOTA: La carga de datos de prueba de pedidos no se realiza aquí,
     * ya que los pedidos de prueba originales usan LocalDateTime.now(),
     * y el SP de datos de prueba solo inserta Clientes y Artículos.
     * La lógica de Pedido de prueba se mantendrá en Main.java (o se simplificará).
     */
    public void cargarDatosPrueba() {
        // No se realiza ninguna acción aquí. La lógica de Pedidos de prueba se maneja en Main.
    }
}