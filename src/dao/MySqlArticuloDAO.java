package dao;

import model.Articulo;
import util.ConexionBD;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

/**
 * Esta es la implementacion de MySQL para el Repositorio de Articulos.
 * Implementa el "contrato" (Interfaz Repositorio)
 * El <Articulo, Integer> significa que maneja "Articulos" y su ID es un "Integer".
 */
public class MySqlArticuloDAO implements Repositorio<Articulo, Integer> {

    // Metodo para "traducir" una fila de la BD a un objeto Articulo
    private Articulo mapearArticulo(ResultSet rs) throws SQLException {
        // Leemos los nombres de columna EXACTOS de tu script SQL
        int codigoArticulo = rs.getInt("codigo_articulo");
        String descripcion = rs.getString("descripcion");
        double precioVenta = rs.getDouble("precio_venta");
        double gastosEnvio = rs.getDouble("gastos_envio");
        int tiempoPrep = rs.getInt("tiempoPrep");

        // Usamos el constructor que creamos en Articulo.java
        return new Articulo(codigoArticulo, descripcion, precioVenta, gastosEnvio, tiempoPrep);
    }

    /**
     * AGREGA un nuevo articulo a la base de datos.
     * Usa el constructor SIN ID que anadimos.
     */
    @Override
    public void agregar(Articulo articulo) throws Exception {
        // 1. Sentencia SQL (con '?' para evitar SQL Injection)
        // Usamos los nombres de columna EXACTOS de tu script SQL
        String sql = "INSERT INTO Articulo (descripcion, precio_venta, gastos_envio, tiempoPrep) VALUES (?, ?, ?, ?)";
        Connection conn = null;
        PreparedStatement ps = null;

        try {
            // 2. Obtenemos una conexion del "fontanero"
            conn = ConexionBD.getConexion();
            
            // 3. Preparamos la sentencia SQL
            // El 'RETURN_GENERATED_KEYS' es para que MySQL nos devuelva el ID que acaba de crear
            ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

            // 4. Asignamos los valores a los '?'
            ps.setString(1, articulo.getDescripcion());
            ps.setDouble(2, articulo.getPrecioVenta());
            ps.setDouble(3, articulo.getGastosEnvio());
            ps.setInt(4, articulo.getTiempoPreparacion());

            // 5. Ejecutamos la insercion
            ps.executeUpdate();

            // 6. (Opcional pero bueno) Obtenemos el ID generado y lo ponemos en el objeto
            ResultSet rsKeys = ps.getGeneratedKeys();
            if (rsKeys.next()) {
                articulo.setCodigoArticulo(rsKeys.getInt(1));
            }

        } catch (SQLException e) {
            // Si algo va mal, lanzamos una excepcion
            throw new Exception("Error al insertar articulo: " + e.getMessage(), e);
        } finally {
            // 7. Cerramos todo (siempre!)
            ConexionBD.cerrar(ps);
            ConexionBD.cerrar(conn);
        }
    }

    /**
     * BUSCA un articulo por su ID (que ahora es un Integer).
     */
    @Override
    public Articulo buscarPorId(Integer id) throws Exception {
        String sql = "SELECT * FROM Articulo WHERE codigo_articulo = ?";
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            conn = ConexionBD.getConexion();
            ps = conn.prepareStatement(sql);
            ps.setInt(1, id); // Asignamos el ID al '?'

            rs = ps.executeQuery(); // Ejecutamos la consulta

            if (rs.next()) {
                // Si encontramos algo, lo "mapeamos" a un objeto
                return mapearArticulo(rs);
            }
            
            // Si no hay 'rs.next()', no se encontrÃ³ nada
            return null; 

        } catch (SQLException e) {
            throw new Exception("Error al buscar articulo: " + e.getMessage(), e);
        } finally {
            ConexionBD.cerrar(rs);
            ConexionBD.cerrar(ps);
            ConexionBD.cerrar(conn);
        }
    }

    /**
     * LISTA todos los articulos de la tabla.
     */
    @Override
    public List<Articulo> listar() throws Exception {
        String sql = "SELECT * FROM Articulo";
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        List<Articulo> articulos = new ArrayList<>(); // Lista para guardar los resultados

        try {
            conn = ConexionBD.getConexion();
            ps = conn.prepareStatement(sql);
            rs = ps.executeQuery();

            // Mapeamos CADA fila que encontramos
            while (rs.next()) {
                articulos.add(mapearArticulo(rs));
            }

        } catch (SQLException e) {
            throw new Exception("Error al listar articulos: " + e.getMessage(), e);
        } finally {
            ConexionBD.cerrar(rs);
            ConexionBD.cerrar(ps);
            ConexionBD.cerrar(conn);
        }
        
        return articulos; // Devolvemos la lista (puede estar vacia)
    }

    /**
     * ELIMINA un articulo por su ID (Integer).
     */
    @Override
    public void eliminar(Integer id) throws Exception {
        String sql = "DELETE FROM Articulo WHERE codigo_articulo = ?";
        Connection conn = null;
        PreparedStatement ps = null;

        try {
            conn = ConexionBD.getConexion();
            ps = conn.prepareStatement(sql);
            ps.setInt(1, id); // Asignamos el ID a eliminar

            ps.executeUpdate(); // Ejecutamos el borrado

        } catch (SQLException e) {
            // Ojo: Si el articulo esta en un Pedido, esto fallara
            // (por la FOREIGN KEY). Eso es bueno, se llama integridad referencial.
            throw new Exception("Error al eliminar articulo: " + e.getMessage(), e);
        } finally {
            ConexionBD.cerrar(ps);
            ConexionBD.cerrar(conn);
        }
    }
    
    /**
     * ACTUALIZA un articulo existente en la base de datos.
     * 
     * Que actualiza?: Descripcion, precio, gastos envio y tiempo preparacion
     * Que NO actualiza?: El codigo_articulo (es la PRIMARY KEY, no se cambia)
     */
    @Override
    public void actualizar(Articulo articulo) throws Exception {
        String sql = "UPDATE Articulo SET descripcion = ?, precio_venta = ?, " +
                    "gastos_envio = ?, tiempoPrep = ? WHERE codigo_articulo = ?";
        
        Connection conn = null;
        PreparedStatement ps = null;
        
        try {
            conn = ConexionBD.getConexion();
            ps = conn.prepareStatement(sql);
            
            // Asignamos los nuevos valores
            ps.setString(1, articulo.getDescripcion());
            ps.setDouble(2, articulo.getPrecioVenta());
            ps.setDouble(3, articulo.getGastosEnvio());
            ps.setInt(4, articulo.getTiempoPreparacion());
            ps.setInt(5, articulo.getCodigoArticulo());  // WHERE (el ID)
            
            int filasActualizadas = ps.executeUpdate();
            
            if (filasActualizadas == 0) {
                throw new Exception("No se encontro el articulo con codigo: " + articulo.getCodigoArticulo());
            }
            
            System.out.println("Articulo actualizado correctamente");
            
        } catch (SQLException e) {
            throw new Exception("Error al actualizar articulo: " + e.getMessage(), e);
        } finally {
            ConexionBD.cerrar(ps);
            ConexionBD.cerrar(conn);
        }
    }
}