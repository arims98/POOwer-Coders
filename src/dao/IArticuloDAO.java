//DAO (Data Access Object): Es una capa independiente que se encarga exclusivamente del acceso y la gestión de los datos en la base de datos.
// Separa la lógica de acceso a datos del Modelo, permitiendo que el Modelo use DAO para obtener o persistir datos sin preocuparse por detalles de conexión o consultas.
package dao;

import model.Articulo;
import java.util.List;

public interface IArticuloDAO {
    
    public Articulo agregar(Articulo articulo);

    public Articulo buscarPorId(String codigo);

    public List<Articulo> listar();

    public boolean eliminar(String codigo);

    //Datos
    //public void cargarDatosPrueba() {
        // Añade algunos artículos de ejemplo para pruebas
       // agregar(new Articulo("A01", "Auriculares Bluetooth", 60, 5, 5));
        //agregar(new Articulo("A02", "Teclado", 80, 5, 5));
        //agregar(new Articulo("A03", "Ratón inalámbrico", 20, 5, 5));
        //agregar(new Articulo("A04", "Monitor 24 pulgadas", 120, 10, 10));
    //}
}
