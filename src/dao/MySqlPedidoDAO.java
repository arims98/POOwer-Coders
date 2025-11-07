package dao;

import model.Articulo;
import model.Cliente;
import model.Pedido;
import util.ConexionBD;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

/**
 * Implementación de MySQL para el Repositorio de Pedidos.
 * Implementa Repositorio<Pedido, Integer> porque su ID es 'num_pedido' (Integer).
 */
public class MySqlPedidoDAO implements Repositorio<Pedido, Integer> {

    // DAOs para buscar los objetos Cliente y Articulo
    // Pedimos a la fábrica que nos dé los DAOs que necesitamos.
    private Repositorio<Cliente, String> clienteDAO = DAOFactory.getClienteDAO();
    private Repositorio<Articulo, Integer> articuloDAO = DAOFactory.getArticuloDAO();


    /**
     * Mapea una fila del ResultSet a un objeto Pedido completo.
     * Aquí es donde "reconstruimos" el pedido.
     */
    private Pedido mapearPedido(ResultSet rs) throws Exception {
        // 1. Obtenemos los IDs (claves foráneas) de la tabla Pedido
        int numPedido = rs.getInt("num_pedido");
        String clienteEmail = rs.getString("cliente_email");
        int articuloCodigo = rs.getInt("articulo_codigo");

        // 2. Usamos los otros DAOs para buscar los objetos completos
        Cliente cliente = clienteDAO.buscarPorId(clienteEmail);
        Articulo articulo = articuloDAO.buscarPorId(articuloCodigo);

        // 3. Obtenemos el resto de datos del pedido
        int cantidad = rs.getInt("cantidad");
        Timestamp fechaHora = rs.getTimestamp("fecha_hora");

        // 4. Creamos el objeto Pedido usando el constructor
        // Nota: Asumimos que el constructor de Pedido ha sido actualizado
        // para aceptar 'int numPedido'.
        return new Pedido(numPedido, cliente, articulo, cantidad, fechaHora.toLocalDateTime());
    }

    /**
     * AGREGA un nuevo pedido a la base de datos.
     */
    @Override
    public void agregar(Pedido pedido) throws Exception {
        String sql = "INSERT INTO Pedido (cliente_email, articulo_codigo, cantidad, fecha_hora) VALUES (?, ?, ?, ?)";
        Connection conn = null;
        PreparedStatement ps = null;

        try {
            conn = ConexionBD.getConexion();
            ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

            // Asignamos los valores (¡usamos los IDs de los objetos!)
            ps.setString(1, pedido.getCliente().getEmail());
            ps.setInt(2, pedido.getArticulo().getCodigoArticulo());
            ps.setInt(3, pedido.getCantidad());
            ps.setTimestamp(4, Timestamp.valueOf(pedido.getFechaHora()));

            ps.executeUpdate();

            // Obtenemos el ID 'num_pedido' generado por MySQL
            ResultSet rsKeys = ps.getGeneratedKeys();
            if (rsKeys.next()) {
                pedido.setNumPedido(rsKeys.getInt(1));
            }

        } catch (SQLException e) {
            throw new Exception("Error al insertar pedido: " + e.getMessage(), e);
        } finally {
            ConexionBD.cerrar(ps);
            ConexionBD.cerrar(conn);
        }
    }

    /**
     * BUSCA un pedido por su ID (num_pedido).
     */
    @Override
    public Pedido buscarPorId(Integer id) throws Exception {
        String sql = "SELECT * FROM Pedido WHERE num_pedido = ?";
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            conn = ConexionBD.getConexion();
            ps = conn.prepareStatement(sql);
            ps.setInt(1, id);
            rs = ps.executeQuery();

            if (rs.next()) {
                return mapearPedido(rs); // Usamos el mapeador
            }
            return null; // No se encontró

        } catch (Exception e) { // Capturamos Exception (por el mapearPedido)
            throw new Exception("Error al buscar pedido: " + e.getMessage(), e);
        } finally {
            ConexionBD.cerrar(rs);
            ConexionBD.cerrar(ps);
            ConexionBD.cerrar(conn);
        }
    }

    /**
     * LISTA todos los pedidos.
     */
    @Override
    public List<Pedido> listar() throws Exception {
        String sql = "SELECT * FROM Pedido";
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        List<Pedido> pedidos = new ArrayList<>();

        try {
            conn = ConexionBD.getConexion();
            ps = conn.prepareStatement(sql);
            rs = ps.executeQuery();

            while (rs.next()) {
                pedidos.add(mapearPedido(rs)); // Usamos el mapeador
            }

        } catch (Exception e) { // Capturamos Exception (por el mapearPedido)
            throw new Exception("Error al listar pedidos: " + e.getMessage(), e);
        } finally {
            ConexionBD.cerrar(rs);
            ConexionBD.cerrar(ps);
            ConexionBD.cerrar(conn);
        }
        return pedidos;
    }

    /**
     * ELIMINA un pedido por su ID (num_pedido).
     */
    @Override
    public void eliminar(Integer id) throws Exception {
        String sql = "DELETE FROM Pedido WHERE num_pedido = ?";
        Connection conn = null;
        PreparedStatement ps = null;

        try {
            conn = ConexionBD.getConexion();
            ps = conn.prepareStatement(sql);
            ps.setInt(1, id);
            ps.executeUpdate();

        } catch (SQLException e) {
            throw new Exception("Error al eliminar pedido: " + e.getMessage(), e);
        } finally {
            ConexionBD.cerrar(ps);
            ConexionBD.cerrar(conn);
        }
    }
}