package util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * Clase utilitaria para gestionar la conexión a la base de datos MySQL.
 */
public class ConexionBD {
    
    // Parámetros de conexión a la base de datos
    private static final String URL = "jdbc:mysql://localhost:3306/tienda";
    private static final String USUARIO = "root";
    private static final String PASSWORD = "";
    
    /**
     * Obtiene una conexión a la base de datos.
     * 
     * @return Connection objeto de conexión a la BD
     * @throws SQLException si hay un error al conectar
     */
    public static Connection getConexion() throws SQLException {
        try {
            // Cargar el driver de MySQL (opcional en versiones recientes de JDBC)
            Class.forName("com.mysql.cj.jdbc.Driver");
            return DriverManager.getConnection(URL, USUARIO, PASSWORD);
        } catch (ClassNotFoundException e) {
            throw new SQLException("Driver de MySQL no encontrado: " + e.getMessage(), e);
        }
    }
    
    /**
     * Cierra un objeto Connection de forma segura.
     * 
     * @param conn Connection a cerrar
     */
    public static void cerrar(Connection conn) {
        if (conn != null) {
            try {
                conn.close();
            } catch (SQLException e) {
                System.err.println("Error al cerrar la conexión: " + e.getMessage());
            }
        }
    }
    
    /**
     * Cierra un objeto Statement de forma segura.
     * 
     * @param stmt Statement a cerrar
     */
    public static void cerrar(Statement stmt) {
        if (stmt != null) {
            try {
                stmt.close();
            } catch (SQLException e) {
                System.err.println("Error al cerrar el Statement: " + e.getMessage());
            }
        }
    }
    
    /**
     * Cierra un objeto PreparedStatement de forma segura.
     * 
     * @param ps PreparedStatement a cerrar
     */
    public static void cerrar(PreparedStatement ps) {
        if (ps != null) {
            try {
                ps.close();
            } catch (SQLException e) {
                System.err.println("Error al cerrar el PreparedStatement: " + e.getMessage());
            }
        }
    }
    
    /**
     * Cierra un objeto ResultSet de forma segura.
     * 
     * @param rs ResultSet a cerrar
     */
    public static void cerrar(ResultSet rs) {
        if (rs != null) {
            try {
                rs.close();
            } catch (SQLException e) {
                System.err.println("Error al cerrar el ResultSet: " + e.getMessage());
            }
        }
    }
}
