package dao;

import java.util.List;

/**
 * Interfaz de Repositorio Genérico (El "Contrato" DAO).
 * @param <T> El tipo de la entidad (ej: Articulo, Cliente)
 * @param <K> El tipo de la Clave Primaria (ej: Integer o String)
 */
public interface Repositorio<T, K> {
    void agregar(T objeto) throws Exception;
    T buscarPorId(K id) throws Exception;
    List<T> listar() throws Exception;
    void eliminar(K id) throws Exception;
}