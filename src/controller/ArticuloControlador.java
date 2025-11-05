//Controller: Es el intermediario entre la Vista y el Modelo.
// Recibe las acciones del usuario desde la Vista, procesa esa información y actualiza el Modelo o cambia la Vista.
// Controla el flujo de la aplicación y orquesta la interacción entre datos y presentación.
package controller;

import dao.ArticuloRepositorio;
import model.Articulo;
import java.util.List;

public class ArticuloControlador {
    // Repositorio que maneja el almacenamiento y recuperación de artículos
    private final ArticuloRepositorio repositorio;
    // Constructor que recibe un repositorio de artículos
    public ArticuloControlador(ArticuloRepositorio repositorio) {
        this.repositorio = repositorio;
    }

    public void agregarArticulo(String codigo, String descripcion, double precio, double envio, int tiempo) throws Exception {
        // Verifica si ya existe un artículo con el mismo código
        if (repositorio.buscarPorId(codigo) != null)
            throw new Exception("El código de artículo ya existe."); //throws Exception Si ya existe un artículo con el mismo código
        // Crea un nuevo objeto Articulo con los datos proporcionados
        Articulo a = new Articulo(codigo, descripcion, precio, envio, tiempo);

        repositorio.agregar(a);
    }

    public List<Articulo> listarArticulos() {
        // Devuelve la lista de artículos desde el repositorio
        return repositorio.listar();
    }
}

