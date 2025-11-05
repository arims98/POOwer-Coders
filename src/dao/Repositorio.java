package dao;

import java.util.List;

/**
 * Interfaz de Repositorio Genérico (El "Contrato" DAO).
 *
 * @param <T> El tipo de la entidad (ej: Articulo, Cliente)
 * @param <K> El tipo de la Clave Primaria (ej: Integer o String)
 */
public interface Repositorio<T, K> {

    /**
     * Inserta un nuevo objeto en la base de datos.
     * Añadimos 'throws Exception' para capturar errores de SQL.
     */
    void agregar(T objeto) throws Exception;

    /**
     * Busca un objeto por su Clave Primaria.
     *
     * @param id La Clave Primaria (puede ser Integer o String).
     * @return El objeto encontrado, o null si no existe.
     */
    T buscarPorId(K id) throws Exception;

    /**
     * Devuelve una lista con todos los objetos de la tabla.
     */
    List<T> listar() throws Exception;

    /**
     * Elimina un objeto de la base de datos usando su Clave Primaria.
     */
    void eliminar(K id) throws Exception;
    
    /**
     * (Opcional pero recomendado para el futuro)
     * Actualiza un objeto existente en la base de datos.
     */
    // void actualizar(T objeto) throws Exception;
}