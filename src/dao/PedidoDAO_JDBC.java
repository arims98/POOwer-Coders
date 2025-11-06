package dao;

import model.Pedido;
import model.Cliente; 
import model.Articulo; 
import util.ConexionDB;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.List;
import java.util.ArrayList;
import java.sql.ResultSet;

// Importaciones necesarias para que DAOFactory funcione internamente
// y para poder llamar a los métodos de búsqueda de otros DAOs

public class PedidoDAO_JDBC implements IPedidoDAO {

    // --- Constantes SQL ---
    private static final String SQL_INSERT = "INSERT INTO pedido (num_pedido, nif, codigo_articulo, cantidad, fecha_hora, estado) VALUES (?, ?, ?, ?, ?, ?)";
    private static final String SQL_SELECT = "SELECT num_pedido, nif, codigo_articulo, cantidad, fecha_hora, estado FROM pedido";
    private static final String SQL_SELECT_BY_ID = SQL_SELECT + " WHERE num_pedido = ?";
    private static final String SQL_DELETE = "DELETE FROM pedido WHERE num_pedido = ?"; 

    // ---------------------------------------------------------------------------------
    // MÉTODO AGREGAR (INSERT)
    // ---------------------------------------------------------------------------------
    @Override
    public Pedido agregar(Pedido pedido) {
        Connection conn = null;
        PreparedStatement pstmt = null;
        try {
            conn = ConexionDB.getConnection();
            pstmt = conn.prepareStatement(SQL_INSERT);
            pstmt.setString(1, pedido.getNumeroPedido());
            pstmt.setString(2, pedido.getCliente().getNif());
            pstmt.setString(3, pedido.getArticulo().getCodigo());
            pstmt.setInt(4, pedido.getCantidad());
            pstmt.setTimestamp(5, Timestamp.valueOf(pedido.getFechaHora()));
            pstmt.setString(6, pedido.getEstado());
            pstmt.executeUpdate();
            return pedido;
        } catch (SQLException e) {
            System.err.println("Error al agregar el pedido: " + e.getMessage());
            throw new RuntimeException(e);
        } finally {
            ConexionDB.close(pstmt);
            ConexionDB.close(conn);
        }  
    }
    
    // ---------------------------------------------------------------------------------
    // MÉTODO BUSCAR POR ID (SELECT) - Mapeo de Relaciones
    // ---------------------------------------------------------------------------------
    @Override
    public Pedido buscarPorId(String numeroPedido) {
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        
        
            try {
                conn = ConexionDB.getConnection();
                pstmt = conn.prepareStatement(SQL_SELECT_BY_ID);
                pstmt.setString(1, numeroPedido);
                rs = pstmt.executeQuery();
                Pedido pedido = null;

                if (rs.next()) {
                    // Se crea el pedido con valores temporales, luego se setean los correctos
                    pedido = new Pedido(
                        rs.getString("num_pedido"),
                        null, // cliente se setea después
                        null, // articulo se setea después
                        rs.getInt("cantidad"),
                        rs.getTimestamp("fecha_hora").toLocalDateTime(),
                        rs.getString("estado")
                    );

                    // 1. Obtener los IDs (FKs) de la tabla Pedido
                    String nif = rs.getString("nif");
                    String codigoArticulo = rs.getString("codigo_articulo");

                    // 2. Usar el DAOFactory para obtener los objetos relacionados (¡CRÍTICO!)
                    //    Esto requiere que ArticuloDAO_JDBC y ClienteDAO_JDBC existan.
                    IClienteDAO clienteDAO = DAOFactory.getClienteDAO();
                    IArticuloDAO articuloDAO = DAOFactory.getArticuloDAO();

                    Cliente cliente = clienteDAO.buscarPorId(nif); 
                    Articulo articulo = articuloDAO.buscarPorId(codigoArticulo); 

                    // 3. Mapear los atributos del Pedido
                    pedido.setCliente(cliente);
                    pedido.setArticulo(articulo);
                    pedido.setNumeroPedido(rs.getString("num_pedido"));
                    pedido.setFechaHora(rs.getTimestamp("fecha_hora").toLocalDateTime());
                    pedido.setCantidad(rs.getInt("cantidad"));
                    pedido.setEstado(rs.getString("estado"));
                }
                return pedido;
            } catch (SQLException e) {
                System.err.println("Error al buscar el pedido por ID: " + e.getMessage());
                throw new RuntimeException(e);
            } finally {
                ConexionDB.close(rs);
                ConexionDB.close(pstmt);
                ConexionDB.close(conn);    
            }
    }
    
    // ---------------------------------------------------------------------------------
    // MÉTODO LISTAR (SELECT) - Implementación de la Interfaz
    // ---------------------------------------------------------------------------------
   
    @Override
    public List<Pedido> listar() {
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        List<Pedido> pedidos = new ArrayList<>();
        
        // Se necesitan los DAOs para buscar el Cliente y Articulo en cada iteración:
        IClienteDAO clienteDAO = DAOFactory.getClienteDAO();
        IArticuloDAO articuloDAO = DAOFactory.getArticuloDAO();
        
        try {
            conn = ConexionDB.getConnection();
            pstmt = conn.prepareStatement(SQL_SELECT);
            rs = pstmt.executeQuery();  
            
            while (rs.next()) {
                Pedido pedido = new Pedido(
                    rs.getString("num_pedido"),
                    null, // cliente se setea después
                    null, // articulo se setea después
                    rs.getInt("cantidad"),
                    rs.getTimestamp("fecha_hora").toLocalDateTime(),
                    rs.getString("estado")
                );
                // Mapeo de IDs para buscar los objetos
                String nif = rs.getString("nif");
                String codigoArticulo = rs.getString("codigo_articulo");
                // Búsqueda de objetos relacionados
                Cliente cliente = clienteDAO.buscarPorId(nif); 
                Articulo articulo = articuloDAO.buscarPorId(codigoArticulo);
                // Mapeo de atributos y relaciones
                pedido.setCliente(cliente);
                pedido.setArticulo(articulo);
                pedidos.add(pedido);
            }
            return pedidos;
        } catch (SQLException e) {
            System.err.println("Error al listar los pedidos: " + e.getMessage());
            throw new RuntimeException(e);
        } finally {
            ConexionDB.close(rs);
            ConexionDB.close(pstmt);
            ConexionDB.close(conn); 
        }
    }
    
    // ---------------------------------------------------------------------------------
    // MÉTODO ELIMINAR (DELETE) - Usando la constante SQL_DELETE
    // ---------------------------------------------------------------------------------
    @Override
    public boolean eliminar(String numeroPedido) {
        Connection conn = null;
        PreparedStatement pstmt = null;
        
        try {
            conn = ConexionDB.getConnection();
            pstmt = conn.prepareStatement(SQL_DELETE); // Usando la constante de clase
            pstmt.setString(1, numeroPedido);

            // Devuelve true si la sentencia afectó al menos una fila (eliminación exitosa)
            return pstmt.executeUpdate() > 0; 
            
        } catch (SQLException e) {
            System.err.println("Error al eliminar el pedido: " + e.getMessage());
            throw new RuntimeException(e);
        } finally {
            ConexionDB.close(pstmt);
            ConexionDB.close(conn); 
        }   
    }
}