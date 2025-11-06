package dao;

import model.Articulo;
import util.ConexionDB;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;


public class ArticuloDAO_JDBC implements IArticuloDAO {
    private static final String SQL_SELECT_BY_ID = "SELECT codigo, descripcion, precio_venta, gastos_envio, tiempo_preparacion FROM articulo WHERE codigo = ?";

    @Override
    public Articulo buscarPorId(String codigo) {
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Articulo articulo = null;
        try {
            conn = ConexionDB.getConnection();
            pstmt = conn.prepareStatement(SQL_SELECT_BY_ID);
            pstmt.setString(1, codigo);
            rs = pstmt.executeQuery();
            if (rs.next()) {
                articulo = new Articulo(
                    rs.getString("codigo"),
                    rs.getString("descripcion"),
                    rs.getDouble("precio_venta"),
                    rs.getDouble("gastos_envio"),
                    rs.getInt("tiempo_preparacion")
                );
            }
            return articulo;
        } catch (SQLException e) {
            throw new RuntimeException("Error al buscar artículo: " + e.getMessage(), e);
        } finally {
            ConexionDB.close(rs);
            ConexionDB.close(pstmt);
            ConexionDB.close(conn);
        }
    }

    // Métodos stub para cumplir la interfaz
    @Override
    public Articulo agregar(Articulo articulo) { throw new UnsupportedOperationException(); }
    @Override
    public List<Articulo> listar() { throw new UnsupportedOperationException(); }
    @Override
    public boolean eliminar(String codigo) { throw new UnsupportedOperationException(); }
}
