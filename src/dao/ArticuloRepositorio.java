package dao;

import util.Conexion;
import model.Articulo;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

// Implementación del Repositorio de Artículos para MySQL
public class ArticuloRepositorio implements Repositorio<Articulo> {

    // CONSULTAS SQL
    private static final String SQL_INSERT = "INSERT INTO ARTICULO (codigo, descripcion, precio_venta, gastos_envio, tiempo_preparacion) VALUES (?, ?, ?, ?, ?)";
    private static final String SQL_SELECT_ALL = "SELECT * FROM ARTICULO";
    private static final String SQL_SELECT_BY_ID = "SELECT * FROM ARTICULO WHERE codigo = ?";
    private static final String SQL_DELETE = "DELETE FROM ARTICULO WHERE codigo = ?";
    

    @Override
    public void agregar(Articulo articulo) {
        Connection conn = null;
        PreparedStatement ps = null;
        try {
            conn = Conexion.getConnection();
            ps = conn.prepareStatement(SQL_INSERT);
            
            // Uso de PreparedStatement para prevenir SQL Injection
            ps.setString(1, articulo.getCodigo());
            ps.setString(2, articulo.getDescripcion());
            ps.setDouble(3, articulo.getPrecioVenta());
            ps.setDouble(4, articulo.getGastosEnvio());
            ps.setInt(5, articulo.getTiempoPreparacion());
            
            ps.executeUpdate();
        } catch (SQLException e) {
            // Manejo de errores (por ejemplo, clave primaria duplicada)
            System.err.println("Error al insertar artículo: " + e.getMessage());
        } finally {
            // Cierre seguro de recursos
            try { if (ps != null) ps.close(); } catch (SQLException e) { /* Ignorar */ }
            Conexion.close(conn);
        }
    }

    @Override
    public Articulo buscarPorId(String codigo) {
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        Articulo articulo = null;
        try {
            conn = Conexion.getConnection();
            ps = conn.prepareStatement(SQL_SELECT_BY_ID);
            ps.setString(1, codigo);
            rs = ps.executeQuery();

            if (rs.next()) {
                // Mapeo de ResultSet a objeto Articulo
                articulo = new Articulo(
                    rs.getString("codigo"),
                    rs.getString("descripcion"),
                    rs.getDouble("precio_venta"),
                    rs.getDouble("gastos_envio"),
                    rs.getInt("tiempo_preparacion")
                );
            }
        } catch (SQLException e) {
            System.err.println("Error al buscar artículo: " + e.getMessage());
        } finally {
            try { if (rs != null) rs.close(); } catch (SQLException e) { /* Ignorar */ }
            try { if (ps != null) ps.close(); } catch (SQLException e) { /* Ignorar */ }
            Conexion.close(conn);
        }
        return articulo;
    }

    @Override
    public List<Articulo> listar() {
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        List<Articulo> articulos = new ArrayList<>();
        try {
            conn = Conexion.getConnection();
            ps = conn.prepareStatement(SQL_SELECT_ALL);
            rs = ps.executeQuery();

            while (rs.next()) {
                Articulo articulo = new Articulo(
                    rs.getString("codigo"),
                    rs.getString("descripcion"),
                    rs.getDouble("precio_venta"),
                    rs.getDouble("gastos_envio"),
                    rs.getInt("tiempo_preparacion")
                );
                articulos.add(articulo);
            }
        } catch (SQLException e) {
            System.err.println("Error al listar artículos: " + e.getMessage());
        } finally {
            try { if (rs != null) rs.close(); } catch (SQLException e) { /* Ignorar */ }
            try { if (ps != null) ps.close(); } catch (SQLException e) { /* Ignorar */ }
            Conexion.close(conn);
        }
        return articulos;
    }

    @Override
    public void eliminar(String codigo) {
        Connection conn = null;
        PreparedStatement ps = null;
        try {
            conn = Conexion.getConnection();
            ps = conn.prepareStatement(SQL_DELETE);
            ps.setString(1, codigo);
            
            int rowsAffected = ps.executeUpdate();
            if (rowsAffected == 0) {
                System.out.println("No se encontró el artículo con código: " + codigo);
            }
        } catch (SQLException e) {
            System.err.println("Error al eliminar artículo: " + e.getMessage());
        } finally {
            try { if (ps != null) ps.close(); } catch (SQLException e) { /* Ignorar */ }
            Conexion.close(conn);
        }
    }

   
}