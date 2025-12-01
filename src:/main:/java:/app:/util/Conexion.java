package util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Conexion {
    
    // Configuración de la conexión y el driver
    private static final String DRIVER = "com.mysql.cj.jdbc.Driver"; 
    private static final String URL = "jdbc:mysql://localhost:3306/online_store_bd";
    private static final String USUARIO = "root"; 
    private static final String CLAVE = "rootroot"; 

    private Conexion() {}

    // Bloque estático para cargar el driver al inicio del programa.
    static {
        try {
            // Carga explícita del driver
            Class.forName(DRIVER);
        } catch (ClassNotFoundException e) {
            System.err.println("ERROR: No se pudo cargar el driver JDBC.");
            e.printStackTrace();
            // Detiene la ejecución si el driver no está disponible
            throw new RuntimeException("Fallo al cargar el driver JDBC. Asegúrate de que el JAR esté en el Classpath.");
        }
    }

    /**
     * Establece y devuelve una nueva conexión a la base de datos.
     */
    public static Connection getConnection() throws SQLException {
        Connection conn = DriverManager.getConnection(URL, USUARIO, CLAVE);
        conn.setAutoCommit(false);//Desactivamos el autocommit para controlar las transacciones manualmente
        // Se conecta usando el DriverManager (el driver ya está registrado).
        return conn;
        
    }

    /**
     * Cierra la conexión de forma segura.
     */
    public static void close(Connection conn) {
        if (conn != null) {
            try {
                conn.close();
            } catch (SQLException e) {
                System.err.println("Error al cerrar la conexión: " + e.getMessage());
            }
        }
    }
}