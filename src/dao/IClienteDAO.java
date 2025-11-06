//DAO (Data Access Object): Es una capa independiente que se encarga exclusivamente del acceso y la gestión de los datos en la base de datos.
// Separa la lógica de acceso a datos del Modelo, permitiendo que el Modelo use DAO para obtener o persistir datos sin preocuparse por detalles de conexión o consultas.
package dao;

import model.Cliente;
import java.util.List;

// Repositorio encargado de gestionar clientes, implementa la interfaz genérica Repositorio<Cliente>
public interface IClienteDAO {
    
    public Cliente agregar(Cliente cliente);

    public Cliente buscarPorId(String nif);

    public List<Cliente> listar();

    public boolean eliminar(String nif);
    
    

    /*  ==================== DATOS DE PRUEBA ====================
    // Carga algunos clientes de ejemplo en la lista
    public void cargarDatosPrueba() {
        // estándar
        agregar(new ClienteEstandar("Sergio Gomez Gutierrez", "Calle Luna 3, Barcelona", "12345678A", "sgomezg@uoc.edu"));
        // premium
        agregar(new ClientePremium("Ariadna Martínez Serra", "Av. Sol 10, Barcelona", "87654321B", "amartinezs@uoc.edu"));
        // estándar
        agregar(new ClienteEstandar("Meritxell Moreno Moya", "Calle Estrella 5, Barcelona", "23456789C", "mmorenom@uoc.edu"));
        // premium
        agregar(new ClientePremium("Cèlia Trullà Estruch", "Plaza Mayor 12, Barcelona", "98765432D", "ctrullae@uoc.edu"));
    }*/
}

