package dao;

import model.Cliente;
import util.ConexionDB;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;


public class ClienteDAO_JDBC implements IClienteDAO {
    private static final String SQL_SELECT_BY_ID = "SELECT nombre, domicilio, nif, email FROM cliente WHERE nif = ?";

    @Override
    public Cliente buscarPorId(String nif) {
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Cliente cliente = null;
        try {
            conn = ConexionDB.getConnection();
            pstmt = conn.prepareStatement(SQL_SELECT_BY_ID);
            pstmt.setString(1, nif);
            rs = pstmt.executeQuery();
            if (rs.next()) {
                cliente = new model.Cliente(
                    rs.getString("nombre"),
                    rs.getString("domicilio"),
                    rs.getString("nif"),
                    rs.getString("email")
                ) {
                    // clase anónima porque Cliente es abstracto
                };
            }
            return cliente;
        } catch (SQLException e) {
            throw new RuntimeException("Error al buscar cliente: " + e.getMessage(), e);
        } finally {
            ConexionDB.close(rs);
            ConexionDB.close(pstmt);
            ConexionDB.close(conn);
        }
    }

    // Métodos stub para cumplir la interfaz
    @Override
    public Cliente agregar(Cliente cliente) { throw new UnsupportedOperationException(); }
    @Override
    public List<Cliente> listar() { throw new UnsupportedOperationException(); }
    @Override
    public boolean eliminar(String nif) { throw new UnsupportedOperationException(); }
}
