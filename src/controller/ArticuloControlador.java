package controller;

import dao.Repositorio;
import model.Articulo;
import java.util.List;

public class ArticuloControlador {
    
    private final Repositorio<Articulo> articuloRepo;

    // El constructor acepta la interfaz Repositorio<Articulo>
    public ArticuloControlador(Repositorio<Articulo> articuloRepo) {
        this.articuloRepo = articuloRepo;
    }

    public void agregarArticulo(String codigo, String descripcion, double precioVenta, double gastosEnvio, int tiempoPreparacion) throws Exception {
        if (precioVenta <= 0 || gastosEnvio < 0 || tiempoPreparacion <= 0) {
            throw new Exception("Los precios y tiempos deben ser valores positivos.");
        }
        Articulo nuevoArticulo = new Articulo(codigo, descripcion, precioVenta, gastosEnvio, tiempoPreparacion);
        articuloRepo.agregar(nuevoArticulo);
    }

    public List<Articulo> listarArticulos() {
        return articuloRepo.listar();
    }
}