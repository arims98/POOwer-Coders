package dao;

import java.util.List;

public interface Repositorio<T> {
    void agregar(T var1);

    T buscarPorId(String var1);

    List<T> listar();

    void eliminar(String var1);
}