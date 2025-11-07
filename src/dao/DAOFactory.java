package dao; // O 'package factory;' si creas una carpeta nueva

import model.Articulo;
import model.Cliente;
import model.Pedido;

/**
 * Esta es la Fábrica de DAOs.
 * Es el único lugar del programa que sabe que estamos usando MySQL.
 * Si mañana quisiéramos cambiar a Oracle, solo modificaríamos esta clase.
 */
public class DAOFactory {

    // El tipo de persistencia que usaremos.
    // Por ahora, solo tenemos MySQL.
    // (Esto podría venir de un archivo de configuración en un futuro)
    private static final int TIPO_PERSISTENCIA = 1; // 1 = MySQL

    // Métodos "fabricadores"
    
    public static Repositorio<Articulo, Integer> getArticuloDAO() {
        switch (TIPO_PERSISTENCIA) {
            case 1:
                return new MySqlArticuloDAO();
            // case 2:
                // return new OracleArticuloDAO(); // Si existiera
            default:
                throw new IllegalArgumentException("Tipo de persistencia no válido");
        }
    }

    public static Repositorio<Cliente, String> getClienteDAO() {
        switch (TIPO_PERSISTENCIA) {
            case 1:
                return new MySqlClienteDAO();
            default:
                throw new IllegalArgumentException("Tipo de persistencia no válido");
        }
    }

    public static Repositorio<Pedido, Integer> getPedidoDAO() {
        switch (TIPO_PERSISTENCIA) {
            case 1:
                return new MySqlPedidoDAO();
            default:
                throw new IllegalArgumentException("Tipo de persistencia no válido");
        }
    }
}