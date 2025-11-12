package dao;

import model.Articulo;
import model.Cliente;
import model.ClientePremium;
import model.Pedido;
import util.ConexionBD;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.sql.Types;
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
        double precioTotal = rs.getDouble("precio_total");  // Campo de la BD

        // 4. Creamos el objeto Pedido usando el constructor completo
        return new Pedido(numPedido, cliente, articulo, cantidad, fechaHora.toLocalDateTime(), precioTotal);
    }

    /**
     * AGREGA un nuevo pedido a la base de datos.
     * AHORA USA TRANSACCIONES para garantizar la integridad.
     * CALCULA el precio_total automáticamente basado en el artículo y cantidad.
     * 
     * ¿Por qué transacciones?: Si algo falla (ej: el artículo no existe),
     * se deshace todo y no queda basura en la BD.
     */
    @Override
    public void agregar(Pedido pedido) throws Exception {
        String sql = "INSERT INTO Pedido (cliente_email, articulo_codigo, cantidad, fecha_hora, precio_total) VALUES (?, ?, ?, ?, ?)";
        Connection conn = null;
        PreparedStatement ps = null;

        try {
            conn = ConexionBD.getConexion();
            
            // ⭐ INICIAR TRANSACCIÓN
            conn.setAutoCommit(false);
            
            // CALCULAR PRECIO TOTAL
            // precio_total = (precio_venta * cantidad) + gastos_envio
            Articulo articulo = pedido.getArticulo();
            Cliente cliente = pedido.getCliente();
            int cantidad = pedido.getCantidad();
            
            double subtotal = articulo.getPrecioVenta() * cantidad;
            double gastosEnvio = articulo.getGastosEnvio();
            
            // Si es cliente Premium, aplicar descuento en envío
            if (cliente instanceof ClientePremium) {
                ClientePremium premium = (ClientePremium) cliente;
                double descuento = premium.getDescuentoEnvio() / 100.0;  // 20 -> 0.20
                gastosEnvio = gastosEnvio * (1 - descuento);
            }
            
            double precioTotal = subtotal + gastosEnvio;
            pedido.setPrecioTotal(precioTotal);
            
            ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

            // Asignamos los valores (¡usamos los IDs de los objetos!)
            ps.setString(1, pedido.getCliente().getEmail());
            ps.setInt(2, pedido.getArticulo().getCodigoArticulo());
            ps.setInt(3, cantidad);
            ps.setTimestamp(4, Timestamp.valueOf(pedido.getFechaHora()));
            ps.setDouble(5, precioTotal);  // Precio total calculado

            ps.executeUpdate();

            // Obtenemos el ID 'num_pedido' generado por MySQL
            ResultSet rsKeys = ps.getGeneratedKeys();
            if (rsKeys.next()) {
                pedido.setNumPedido(rsKeys.getInt(1));
            }
            
            // ⭐ CONFIRMAR TRANSACCIÓN (todo OK)
            conn.commit();
            System.out.println("✅ Pedido agregado con éxito (Total: " + precioTotal + "€)");

        } catch (SQLException e) {
            // ⭐ REVERTIR TRANSACCIÓN (algo falló)
            if (conn != null) {
                try {
                    conn.rollback();
                    System.out.println("❌ Error: transacción revertida");
                } catch (SQLException ex) {
                    System.err.println("Error al hacer rollback: " + ex.getMessage());
                }
            }
            throw new Exception("Error al insertar pedido: " + e.getMessage(), e);
        } finally {
            ConexionBD.cerrar(ps);
            if (conn != null) {
                try {
                    // ⭐ RESTAURAR autocommit al modo normal
                    conn.setAutoCommit(true);
                } catch (SQLException e) {
                    System.err.println("Error al restaurar autoCommit: " + e.getMessage());
                }
                ConexionBD.cerrar(conn);
            }
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
    
    // =====================================================
    // MÉTODOS QUE USAN PROCEDIMIENTOS ALMACENADOS
    // =====================================================
    
    /**
     * CREA UN PEDIDO USANDO EL PROCEDIMIENTO ALMACENADO.
     * 
     * ¿Qué hace?: Llama al procedimiento sp_agregar_pedido_completo
     * ¿Por qué usarlo?: El procedimiento valida que cliente y artículo existan
     *                   Y calcula el precio_total automáticamente
     * 
     * @param clienteEmail Email del cliente
     * @param articuloCodigo Código del artículo
     * @param cantidad Cantidad a pedir
     * @return El número del pedido creado (o 0 si hubo error)
     * @throws Exception si algo falla
     */
    public int agregarPedidoConProcedimiento(String clienteEmail, int articuloCodigo, int cantidad) throws Exception {
        // CALL sp_agregar_pedido_completo(?, ?, ?, ?, ?, ?)
        String sql = "{CALL sp_agregar_pedido_completo(?, ?, ?, ?, ?, ?)}";
        
        Connection conn = null;
        CallableStatement cs = null;
        
        try {
            conn = ConexionBD.getConexion();
            cs = conn.prepareCall(sql);
            
            // Parámetros de ENTRADA (IN)
            cs.setString(1, clienteEmail);      // p_cliente_email
            cs.setInt(2, articuloCodigo);       // p_articulo_codigo
            cs.setInt(3, cantidad);             // p_cantidad
            
            // Parámetros de SALIDA (OUT)
            cs.registerOutParameter(4, Types.INTEGER);    // p_num_pedido
            cs.registerOutParameter(5, Types.DECIMAL);    // p_precio_total
            cs.registerOutParameter(6, Types.VARCHAR);    // p_mensaje
            
            // Ejecutar el procedimiento
            cs.execute();
            
            // Leer los resultados
            int numPedido = cs.getInt(4);
            double precioTotal = cs.getDouble(5);
            String mensaje = cs.getString(6);
            
            System.out.println("📦 Resultado del procedimiento: " + mensaje);
            
            // Si el número de pedido es 0, hubo un error
            if (numPedido == 0) {
                throw new Exception(mensaje);
            }
            
            return numPedido;
            
        } catch (SQLException e) {
            throw new Exception("Error al ejecutar procedimiento agregar_pedido: " + e.getMessage(), e);
        } finally {
            ConexionBD.cerrar(cs);
            ConexionBD.cerrar(conn);
        }
    }
    
    /**
     * CALCULA EL TOTAL DE UN PEDIDO USANDO PROCEDIMIENTO ALMACENADO.
     * 
     * ¿Qué hace?: Llama al procedimiento sp_calcular_total_pedido
     * ¿Por qué usarlo?: Calcula precios con descuentos Premium automáticamente
     * 
     * @param numPedido Número del pedido
     * @return Array con [subtotal, gastos_envio, descuento, total]
     * @throws Exception si algo falla
     */
    public double[] calcularTotalPedido(int numPedido) throws Exception {
        // CALL sp_calcular_total_pedido(?, ?, ?, ?, ?, ?)
        String sql = "{CALL sp_calcular_total_pedido(?, ?, ?, ?, ?, ?)}";
        
        Connection conn = null;
        CallableStatement cs = null;
        
        try {
            conn = ConexionBD.getConexion();
            cs = conn.prepareCall(sql);
            
            // Parámetro de ENTRADA (IN)
            cs.setInt(1, numPedido);
            
            // Parámetros de SALIDA (OUT)
            cs.registerOutParameter(2, Types.DECIMAL);  // p_subtotal
            cs.registerOutParameter(3, Types.DECIMAL);  // p_gastos_envio
            cs.registerOutParameter(4, Types.DECIMAL);  // p_descuento
            cs.registerOutParameter(5, Types.DECIMAL);  // p_total
            cs.registerOutParameter(6, Types.VARCHAR);  // p_mensaje
            
            // Ejecutar
            cs.execute();
            
            // Leer resultados
            double subtotal = cs.getDouble(2);
            double gastosEnvio = cs.getDouble(3);
            double descuento = cs.getDouble(4);
            double total = cs.getDouble(5);
            String mensaje = cs.getString(6);
            
            System.out.println("💰 Cálculo del pedido: " + mensaje);
            System.out.println("   Subtotal: " + subtotal + "€");
            System.out.println("   Gastos envío: " + gastosEnvio + "€");
            System.out.println("   Descuento: " + descuento + "€");
            System.out.println("   TOTAL: " + total + "€");
            
            // Devolver como array
            return new double[] {subtotal, gastosEnvio, descuento, total};
            
        } catch (SQLException e) {
            throw new Exception("Error al calcular total del pedido: " + e.getMessage(), e);
        } finally {
            ConexionBD.cerrar(cs);
            ConexionBD.cerrar(conn);
        }
    }
    
    /**
     * ACTUALIZA un pedido existente en la base de datos.
     * 
     * ¿Qué actualiza?: Cantidad y precio_total (se recalcula automáticamente)
     * ¿Qué NO actualiza?: El num_pedido (PK), cliente, artículo ni fecha
     * 
     * Casos de uso: Cambiar cantidad antes de procesar el pedido
     */
    @Override
    public void actualizar(Pedido pedido) throws Exception {
        String sql = "UPDATE Pedido SET cantidad = ?, precio_total = ? WHERE num_pedido = ?";
        
        Connection conn = null;
        PreparedStatement ps = null;
        
        try {
            conn = ConexionBD.getConexion();
            
            // RECALCULAR PRECIO TOTAL con la nueva cantidad
            Articulo articulo = pedido.getArticulo();
            Cliente cliente = pedido.getCliente();
            int cantidad = pedido.getCantidad();
            
            double subtotal = articulo.getPrecioVenta() * cantidad;
            double gastosEnvio = articulo.getGastosEnvio();
            
            // Si es cliente Premium, aplicar descuento en envío
            if (cliente instanceof ClientePremium) {
                ClientePremium premium = (ClientePremium) cliente;
                double descuento = premium.getDescuentoEnvio() / 100.0;
                gastosEnvio = gastosEnvio * (1 - descuento);
            }
            
            double precioTotal = subtotal + gastosEnvio;
            
            ps = conn.prepareStatement(sql);
            
            // Asignamos los nuevos valores
            ps.setInt(1, cantidad);
            ps.setDouble(2, precioTotal);
            ps.setInt(3, pedido.getNumPedido());  // WHERE (el ID)
            
            int filasActualizadas = ps.executeUpdate();
            
            if (filasActualizadas == 0) {
                throw new Exception("No se encontró el pedido número: " + pedido.getNumPedido());
            }
            
            pedido.setPrecioTotal(precioTotal);  // Actualizar el objeto también
            System.out.println("✅ Pedido actualizado correctamente (Nuevo total: " + precioTotal + "€)");
            
        } catch (SQLException e) {
            throw new Exception("Error al actualizar pedido: " + e.getMessage(), e);
        } finally {
            ConexionBD.cerrar(ps);
            ConexionBD.cerrar(conn);
        }
    }
}