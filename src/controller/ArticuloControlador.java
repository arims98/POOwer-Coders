package controller;

import dao.Repositorio;
import model.Articulo;
import java.util.Collections;
import java.util.List;

public class ArticuloControlador {
    
    private final Repositorio<Articulo> articuloRepo;

    public ArticuloControlador(Repositorio<Articulo> articuloRepo) {
        this.articuloRepo = articuloRepo;
    }

    public String obtenerSiguienteCodigo() {
        int max = 0;
        // Usa el método robusto para listar artículos
        for (Articulo art : this.listarArticulos()) { 
            String codigo = art.getCodigo(); 
            if (codigo != null && codigo.length() >= 2) { 
                try {
                    // Intenta obtener el número a partir del segundo carácter (ej: de "A01" obtiene "01" -> 1)
                    int num = Integer.parseInt(codigo.substring(1)); 
                    if (num > max) max = num;
                } catch (NumberFormatException e) {
                    System.out.println("Código no válido, la parte numérica es incorrecta: " + codigo);
                }
            } else {
                // Notificación de códigos que no cumplen el formato AXX
                System.out.println("Código inválido o demasiado corto para ser contado: " + codigo);
            }
        }
        
        // Formato A01, A02, etc. (Si max es 0, devuelve A01)
        return String.format("A%02d", max + 1); 
    }

    public void agregarArticulo(String codigo, String descripcion, double precioVenta, double gastosEnvio, int tiempoPreparacion) throws Exception {
        if (precioVenta <= 0 || gastosEnvio < 0 || tiempoPreparacion <= 0) {
            throw new Exception("Los precios y tiempos deben ser valores positivos.");
        }
        String codigoFinal = (codigo == null || codigo.trim().isEmpty()) ?
                             obtenerSiguienteCodigo() :
                             codigo;
        Articulo nuevoArticulo = new Articulo(codigoFinal, descripcion, precioVenta, gastosEnvio, tiempoPreparacion);
        articuloRepo.agregar(nuevoArticulo);
    }

    /**
     * Lista artículos. Envuelve la llamada al repositorio en un try-catch para 
     * evitar que la aplicación se caiga si falla la conexión a la DB y devuelve una lista vacía.
     */
    public List<Articulo> listarArticulos() {
        try {
            return articuloRepo.listar();
        } catch (Exception e) {
            System.err.println(" Error al acceder a la base de datos para listar artículos: " + e.getMessage());
            // Devuelve una lista vacía si hay un error, asegurando que el contador funcione.
            return Collections.emptyList(); 
        }
    }
}
