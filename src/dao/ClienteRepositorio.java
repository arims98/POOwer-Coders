package dao;


import util.Conexion;
import model.Cliente;
import model.ClienteEstandar;
import model.ClientePremium;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.sql.Types;


// Implementación del Repositorio de Clientes para MySQL
public class ClienteRepositorio implements Repositorio<Cliente> {

    // CONSULTAS SQL
    private static final String SQL_INSERT = "INSERT INTO CLIENTE (nif, nombre, domicilio, email, tipo, cuota_anual, descuento_envio) VALUES (?, ?, ?, ?, ?, ?, ?)";
    private static final String SQL_SELECT_ALL = "SELECT * FROM CLIENTE";
    private static final String SQL_SELECT_BY_ID = "SELECT * FROM CLIENTE WHERE nif = ?";
    private static final String SQL_DELETE = "DELETE FROM CLIENTE WHERE nif = ?";
    

    /**
     * Helper para mapear un ResultSet a un objeto Cliente (Estandar o Premium)
     */
    private Cliente mapearCliente(ResultSet rs) throws SQLException {
        String tipo = rs.getString("tipo");
        String nif = rs.getString("nif");
        String nombre = rs.getString("nombre");
        String domicilio = rs.getString("domicilio");
        String email = rs.getString("email");
        
        if ("Premium".equalsIgnoreCase(tipo)) {
            // Se asume que los valores de cuota y descuento son fijos en el modelo Java,
            // por lo que no es necesario leerlos de la DB (se usan solo para el INSERT).
            return new ClientePremium(nombre, domicilio, nif, email);
        } else {
            return new ClienteEstandar(nombre, domicilio, nif, email);
        }
    }

    @Override
    public void agregar(Cliente cliente) throws Exception {
        Connection conn = null;
        PreparedStatement ps = null;
        try {
            conn = Conexion.getConnection();
            ps = conn.prepareStatement(SQL_INSERT);
            
            // Datos comunes
            ps.setString(1, cliente.getNif());
            ps.setString(2, cliente.getNombre());
            ps.setString(3, cliente.getDomicilio());
            ps.setString(4, cliente.getEmail());

            if (cliente instanceof ClientePremium premium) {
                ps.setString(5, "Premium");
                // Datos específicos de Premium
                ps.setDouble(6, premium.getCuotaAnual());
                ps.setDouble(7, premium.getDescuentoEnvio());
            } else { // ClienteEstandar
                ps.setString(5, "Estandar");
                // Valores NULL para campos específicos de Premium
                ps.setNull(6, Types.DECIMAL); 
                ps.setNull(7, Types.DECIMAL);
            }
            
            ps.executeUpdate();

            conn.commit();
            System.out.println("Cliente " + cliente.getNif() + "agregado y transacción confirmada.");
        } catch (SQLException e) {
            if (conn != null) {
                try {
                    System.err.println("Transacción fallida. Realizando ROLLBACK...");
                    conn.rollback();
                } catch (SQLException ex) {
                    System.err.println("Error al intentar ROLLBACK --" + ex.getMessage());
                }
            } throw new Exception ("Error al insertar cliente " + e.getMessage(), e);
        } finally {
            try { if (ps != null) ps.close(); } catch (SQLException e) { /* Ignorar */ }
            Conexion.close(conn);
        }
    }

    @Override
    public Cliente buscarPorId(String nif) {
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        Cliente cliente = null;
        try {
            conn = Conexion.getConnection();
            ps = conn.prepareStatement(SQL_SELECT_BY_ID);
            ps.setString(1, nif);
            rs = ps.executeQuery();

            if (rs.next()) {
                cliente = mapearCliente(rs);
            }
        } catch (SQLException e) {
            System.err.println("Error al buscar cliente: " + e.getMessage());
        } finally {
            try { if (rs != null) rs.close(); } catch (SQLException e) { /* Ignorar */ }
            try { if (ps != null) ps.close(); } catch (SQLException e) { /* Ignorar */ }
            Conexion.close(conn);
        }
        return cliente;
    }

    @Override
    public List<Cliente> listar() {
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        List<Cliente> clientes = new ArrayList<>();
        try {
            conn = Conexion.getConnection();
            ps = conn.prepareStatement(SQL_SELECT_ALL);
            rs = ps.executeQuery();

            while (rs.next()) {
                clientes.add(mapearCliente(rs));
            }
        } catch (SQLException e) {
            System.err.println("Error al listar clientes: " + e.getMessage());
        } finally {
            try { if (rs != null) rs.close(); } catch (SQLException e) { /* Ignorar */ }
            try { if (ps != null) ps.close(); } catch (SQLException e) { /* Ignorar */ }
            Conexion.close(conn);
        }
        return clientes;
    }

    @Override
    public void eliminar(String nif) {
        Connection conn = null;
        PreparedStatement ps = null;
        try {
            conn = Conexion.getConnection();
            ps = conn.prepareStatement(SQL_DELETE);
            ps.setString(1, nif);
            
            int rowsAffected = ps.executeUpdate();
            if (rowsAffected == 0) {
                System.out.println("No se encontró el cliente con NIF: " + nif);
            }
        } catch (SQLException e) {
            System.err.println("Error al eliminar cliente: " + e.getMessage());
        } finally {
            try { if (ps != null) ps.close(); } catch (SQLException e) { /* Ignorar */ }
            Conexion.close(conn);
        }
    }

   
}