package dao;

import model.Cliente;
import model.ClienteEstandar;
import model.ClientePremium;
import util.ConexionBD;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;

/**
 * Implementación de MySQL para el Repositorio de Clientes.
 * Implementa Repositorio<Cliente, String> porque su ID es el 'email' (un String).
 */
public class MySqlClienteDAO implements Repositorio<Cliente, String> {

    /**
     * Mapea una fila del ResultSet a un objeto Cliente (Estandar o Premium).
     * Esta es la parte más importante para manejar la herencia.
     */
    private Cliente mapearCliente(ResultSet rs) throws SQLException {
        // Leemos los datos comunes de la tabla Cliente
        String email = rs.getString("email");
        String nombre = rs.getString("nombre");
        String domicilio = rs.getString("domicilio");
        String nif = rs.getString("nif");

        // Leemos los datos de la tabla ClientePremium (pueden ser NULL)
        // Usamos getString() para 'cuota_anual' y 'descuento_envio' para
        // poder detectar si son NULL (lo que significa que NO es Premium).
        String cuotaAnualStr = rs.getString("cuota_anual");

        if (cuotaAnualStr != null) {
            // ¡Es Premium! Creamos un objeto ClientePremium
            ClientePremium premium = new ClientePremium(nombre, domicilio, nif, email);
            
            // Opcional: Si necesitaras usar la cuota o descuento en Java:
            // premium.setCuota(rs.getDouble("cuota_anual"));
            // double descuentoDB = rs.getInt("descuento_envio");
            // premium.setDescuento(descuentoDB / 100.0); // Convertimos 20 a 0.2
            
            return premium;
        } else {
            // Es Estandar
            return new ClienteEstandar(nombre, domicilio, nif, email);
        }
    }

    /**
     * AGREGA un nuevo cliente.
     * Si es Premium, usa una TRANSACCIÓN para insertarlo en las dos tablas.
     */
    @Override
    public void agregar(Cliente cliente) throws Exception {
        Connection conn = null;
        PreparedStatement psCliente = null;
        PreparedStatement psPremium = null;

        // Sentencias SQL
        String sqlCliente = "INSERT INTO Cliente (email, nombre, domicilio, nif, tipo_cliente) VALUES (?, ?, ?, ?, ?)";
        String sqlPremium = "INSERT INTO ClientePremium (cliente_email, cuota_anual, descuento_envio) VALUES (?, ?, ?)";

        try {
            conn = ConexionBD.getConexion();
            // 1. Iniciamos la TRANSACCIÓN
            conn.setAutoCommit(false);

            // --- Parte 1: Insertar en la tabla Cliente (esto es común) ---
            psCliente = conn.prepareStatement(sqlCliente);
            psCliente.setString(1, cliente.getEmail());
            psCliente.setString(2, cliente.getNombre());
            psCliente.setString(3, cliente.getDomicilio());
            psCliente.setString(4, cliente.getNif());

            if (cliente instanceof ClientePremium) {
                psCliente.setString(5, "Premium"); // OJO: La BD usa 'Premium', no 'PREMIUM'
            } else {
                psCliente.setString(5, "Estándar"); // OJO: La BD usa 'Estándar', no 'ESTANDAR'
            }
            psCliente.executeUpdate();

            // --- Parte 2: Si es Premium, insertar en la tabla ClientePremium ---
            if (cliente instanceof ClientePremium) {
                // Hacemos "casting" para acceder a los métodos de Premium
                ClientePremium premium = (ClientePremium) cliente;

                psPremium = conn.prepareStatement(sqlPremium);
                psPremium.setString(1, premium.getEmail());
                psPremium.setDouble(2, premium.getCuotaAnual()); // Guardamos el 30
                
                // ¡El "desajuste"! Java tiene 0.2, la BD espera 20
                int descuentoDB = (int) (premium.getDescuentoEnvio() * 100); // 0.2 * 100 = 20
                psPremium.setInt(3, descuentoDB);
                
                psPremium.executeUpdate();
            }

            // 2. Si todo fue bien, CONFIRMAMOS la transacción
            conn.commit();

        } catch (SQLException e) {
            // 3. Si algo falló, DESHACEMOS la transacción
            if (conn != null) {
                conn.rollback();
            }
            throw new Exception("Error al agregar cliente (transacción revertida): " + e.getMessage(), e);
        } finally {
            // 4. Cerramos todo y devolvemos la conexión al modo normal
            ConexionBD.cerrar(psCliente);
            ConexionBD.cerrar(psPremium);
            if (conn != null) {
                conn.setAutoCommit(true); // Importante
                ConexionBD.cerrar(conn);
            }
        }
    }

    /**
     * BUSCA un cliente por su ID (email).
     * Usa LEFT JOIN para traer los datos de ambas tablas.
     */
    @Override
    public Cliente buscarPorId(String email) throws Exception {
        // Esta SQL une Cliente con ClientePremium.
        // Si el cliente no está en ClientePremium, los campos (cuota_anual) vendrán como NULL.
        String sql = "SELECT c.*, cp.cuota_anual, cp.descuento_envio " +
                     "FROM Cliente c " +
                     "LEFT JOIN ClientePremium cp ON c.email = cp.cliente_email " +
                     "WHERE c.email = ?";
        
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            conn = ConexionBD.getConexion();
            ps = conn.prepareStatement(sql);
            ps.setString(1, email);
            rs = ps.executeQuery();

            if (rs.next()) {
                return mapearCliente(rs); // Usamos el mapeador
            }
            return null; // No se encontró

        } catch (SQLException e) {
            throw new Exception("Error al buscar cliente: " + e.getMessage(), e);
        } finally {
            ConexionBD.cerrar(rs);
            ConexionBD.cerrar(ps);
            ConexionBD.cerrar(conn);
        }
    }

    /**
     * LISTA todos los clientes (Estandar y Premium).
     */
    @Override
    public List<Cliente> listar() throws Exception {
        String sql = "SELECT c.*, cp.cuota_anual, cp.descuento_envio " +
                     "FROM Cliente c " +
                     "LEFT JOIN ClientePremium cp ON c.email = cp.cliente_email";
        
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        List<Cliente> clientes = new ArrayList<>();

        try {
            conn = ConexionBD.getConexion();
            ps = conn.prepareStatement(sql);
            rs = ps.executeQuery();

            while (rs.next()) {
                clientes.add(mapearCliente(rs)); // Usamos el mapeador
            }
        } catch (SQLException e) {
            throw new Exception("Error al listar clientes: " + e.getMessage(), e);
        } finally {
            ConexionBD.cerrar(rs);
            ConexionBD.cerrar(ps);
            ConexionBD.cerrar(conn);
        }
        return clientes;
    }

    /**
     * ELIMINA un cliente por su ID (email).
     * Gracias a "ON DELETE CASCADE" en la BD, si borramos de Cliente,
     * se borra automáticamente de ClientePremium.
     */
    @Override
    public void eliminar(String email) throws Exception {
        String sql = "DELETE FROM Cliente WHERE email = ?";
        Connection conn = null;
        PreparedStatement ps = null;

        try {
            conn = ConexionBD.getConexion();
            ps = conn.prepareStatement(sql);
            ps.setString(1, email);
            ps.executeUpdate();
            
        } catch (SQLException e) {
            // Fallará si el cliente tiene pedidos (por la FOREIGN KEY)
            throw new Exception("Error al eliminar cliente: " + e.getMessage(), e);
        } finally {
            ConexionBD.cerrar(ps);
            ConexionBD.cerrar(conn);
        }
    }
    
    // =====================================================
    // MÉTODOS QUE USAN PROCEDIMIENTOS ALMACENADOS
    // =====================================================
    
    /**
     * OBTIENE ESTADÍSTICAS DE UN CLIENTE USANDO PROCEDIMIENTO ALMACENADO.
     * 
     * ¿Qué hace?: Llama al procedimiento sp_obtener_estadisticas_cliente
     * ¿Por qué usarlo?: Centraliza la lógica de estadísticas en la BD
     * 
     * @param email Email del cliente
     * @return Array con [totalPedidos, gastoTotal] (como String el gasto)
     * @throws Exception si algo falla
     */
    public String obtenerEstadisticasCliente(String email) throws Exception {
        // CALL sp_obtener_estadisticas_cliente(?, ?, ?, ?, ?)
        String sql = "{CALL sp_obtener_estadisticas_cliente(?, ?, ?, ?, ?)}";
        
        Connection conn = null;
        CallableStatement cs = null;
        
        try {
            conn = ConexionBD.getConexion();
            cs = conn.prepareCall(sql);
            
            // Parámetro de ENTRADA (IN)
            cs.setString(1, email);
            
            // Parámetros de SALIDA (OUT)
            cs.registerOutParameter(2, Types.INTEGER);   // p_total_pedidos
            cs.registerOutParameter(3, Types.DECIMAL);   // p_gasto_total
            cs.registerOutParameter(4, Types.VARCHAR);   // p_tipo_cliente
            cs.registerOutParameter(5, Types.VARCHAR);   // p_mensaje
            
            // Ejecutar
            cs.execute();
            
            // Leer resultados
            int totalPedidos = cs.getInt(2);
            double gastoTotal = cs.getDouble(3);
            String tipoCliente = cs.getString(4);
            // String mensaje = cs.getString(5);  // Opcional: mensaje del procedimiento
            
            // Mostrar información
            System.out.println("\n📊 ESTADÍSTICAS DEL CLIENTE");
            System.out.println("━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━");
            System.out.println("Email: " + email);
            System.out.println("Tipo: " + tipoCliente);
            System.out.println("Total de pedidos: " + totalPedidos);
            System.out.println("Gasto total: " + gastoTotal + "€");
            System.out.println("━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━\n");
            
            return String.format("Pedidos: %d, Gasto: %.2f€, Tipo: %s", 
                               totalPedidos, gastoTotal, tipoCliente);
            
        } catch (SQLException e) {
            throw new Exception("Error al obtener estadísticas: " + e.getMessage(), e);
        } finally {
            ConexionBD.cerrar(cs);
            ConexionBD.cerrar(conn);
        }
    }
    
    /**
     * ACTUALIZA un cliente existente en la base de datos.
     * 
     * ¿Qué actualiza?: Nombre, domicilio y NIF
     * ¿Qué NO actualiza?: El email (PRIMARY KEY) ni el tipo de cliente
     * 
     * NOTA: Para cambiar de Estándar a Premium (o viceversa), 
     * sería necesario otra lógica más compleja con transacciones.
     */
    @Override
    public void actualizar(Cliente cliente) throws Exception {
        String sql = "UPDATE Cliente SET nombre = ?, domicilio = ?, nif = ? WHERE email = ?";
        
        Connection conn = null;
        PreparedStatement ps = null;
        
        try {
            conn = ConexionBD.getConexion();
            ps = conn.prepareStatement(sql);
            
            // Asignamos los nuevos valores
            ps.setString(1, cliente.getNombre());
            ps.setString(2, cliente.getDomicilio());
            ps.setString(3, cliente.getNif());
            ps.setString(4, cliente.getEmail());  // WHERE (el ID)
            
            int filasActualizadas = ps.executeUpdate();
            
            if (filasActualizadas == 0) {
                throw new Exception("No se encontró el cliente con email: " + cliente.getEmail());
            }
            
            System.out.println("✅ Cliente actualizado correctamente");
            
        } catch (SQLException e) {
            throw new Exception("Error al actualizar cliente: " + e.getMessage(), e);
        } finally {
            ConexionBD.cerrar(ps);
            ConexionBD.cerrar(conn);
        }
    }
}