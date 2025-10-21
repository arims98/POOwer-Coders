//DAO (Data Access Object): Es una capa independiente que se encarga exclusivamente del acceso y la gestión de los datos en la base de datos.
// Separa la lógica de acceso a datos del Modelo, permitiendo que el Modelo use DAO para obtener o persistir datos sin preocuparse por detalles de conexión o consultas.
package dao;

//Creamos un Repositorio como plantilla genérica que define las operaciones básicas que nuestro repositrios deben implementar.
//Agregar, buscar, listar y eliminar.

import java.util.List;
// Utilizamos un tipo genérico <T> para que pueda funcionar con cualquier clase de objeto!
public interface Repositorio<T> {
    //Agregar un elemento
    // Recibe un objeto de tipo T y lo añade al repositorio.
    void agregar(T objeto);

    // Buscar por ID
    // Busca un objeto por su identificador único, es decir, código, número de pedido o NIF
    T buscarPorId(String id);

    // Listar todos los elementos
    // Devuelve una lista con todos los objetos almacenados en el repositorio
    List<T> listar();

    // Eliminar un elemento
    // Elimina un objeto por su identificador único, es decir, código o número de pedido
    void eliminar(String id);
}
