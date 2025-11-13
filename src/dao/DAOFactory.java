package dao; // O 'package factory;' si creas una carpeta nueva

import model.Articulo;
import model.Cliente;
import model.Pedido;

/**
 * Esta es la Fabrica de DAOs.
 * Es el unico lugar del programa que sabe que estamos usando MySQL.
 * Si manana quisiéramos cambiar a Oracle, solo modificaríamos esta clase.
 */
public class DAOFactory {

    // El tipo de persistencia que usaremos.
    // Por ahora, solo tenemos MySQL.
    // (Esto podrÃ­a venir de un archivo de configuraciÃ³n en un futuro)
    private static final int TIPO_PERSISTENCIA = 1; // 1 = MySQL

    // MÃ©todos "fabricadores"
    
    public static Repositorio<Articulo, Integer> getArticuloDAO() {
        switch (TIPO_PERSISTENCIA) {
            case 1:
                return new MySqlArticuloDAO();
            // case 2:
                // return new OracleArticuloDAO(); // Si existiera
            default:
                throw new IllegalArgumentException("Tipo de persistencia no valido");
        }
    }

    public static Repositorio<Cliente, String> getClienteDAO() {
        switch (TIPO_PERSISTENCIA) {
            case 1:
                return new MySqlClienteDAO();
            default:
                throw new IllegalArgumentException("Tipo de persistencia no valido");
        }
    }

    public static Repositorio<Pedido, Integer> getPedidoDAO() {
        switch (TIPO_PERSISTENCIA) {
            case 1:
                return new MySqlPedidoDAO();
            default:
                throw new IllegalArgumentException("Tipo de persistencia no valido");
        }
    }
}