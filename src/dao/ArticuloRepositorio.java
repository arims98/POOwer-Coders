//DAO (Data Access Object): Es una capa independiente que se encarga exclusivamente del acceso y la gestión de los datos en la base de datos.
// Separa la lógica de acceso a datos del Modelo, permitiendo que el Modelo use DAO para obtener o persistir datos sin preocuparse por detalles de conexión o consultas.
package dao;

import model.Articulo;
import java.util.ArrayList;
import java.util.List;

public class ArticuloRepositorio implements Repositorio<Articulo> {
    private List<Articulo> articulos = new ArrayList<>();

    //MÉTODOS CRUD
    // Agregar un artículo
    @Override
    public void agregar(Articulo articulo) {
        // Añade el artículo recibido a la lista interna
        articulos.add(articulo);
    }
    //Buscar un artículo por código
    @Override
    public Articulo buscarPorId(String codigo) {
        // Recorre la lista de artículos y devuelve el que tenga el código coincidente
        for (Articulo a : articulos) {
            if (a.getCodigo().equals(codigo)) return a; // Si lo encuentra, lo devuelve
        }
        return null;
    }
    //Listar todos los artículos
    @Override
    public List<Articulo> listar() {
        return articulos;
    }
    // Eliminar un artículo por código
    @Override
    public void eliminar(String codigo) {

        articulos.removeIf(a -> a.getCodigo().equals(codigo));
    }

    //Datos
    public void cargarDatosPrueba() {
        // Añade algunos artículos de ejemplo para pruebas
        agregar(new Articulo("A01", "Auriculares Bluetooth", 60, 5, 5));
        agregar(new Articulo("A02", "Teclado", 80, 5, 5));
        agregar(new Articulo("A03", "Ratón inalámbrico", 20, 5, 5));
        agregar(new Articulo("A04", "Monitor 24 pulgadas", 120, 10, 10));
    }
}

