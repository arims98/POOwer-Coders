package model;

import java.util.ArrayList;     //Creamos lista dinámica
import java.util.List;  

public class ListaArticulos {

    //"Estantería dónde guardamos los artículos"
    private List<Articulo> lista;

    //Constructor que inicializa la lista (estantería), como Array list vacía
    public Lsita Articulos() {
        this.lista = new ArrayList<>();
    }

    //Método para añadir artículos a la lista
    public void agregarArticulo(Articulo articulo) {
        //Añadimos el artículo a la lista
        lista.add(articulo);
    }

    //Método para obtener la lista de artículos
    public List<Articulo> getLista() {
        //Devuelve la lista completa
        return lista;  
    }

    //Método para buscar un artículo por su código
    public Articulo buscarArticuloPorCodigo(String codigo) {
        //Recorremos la lista de artículos
        for (Articulo articulo : lista) {
            //Si el código del artículo coincide con el código buscado
            if (articulo.getCodigo().equals(codigo)) {
                //Devolvemos el artículo encontrado
                return articulo;
            }
        }
        //Si no se encuentra el artículo, devolvemos null
        return null;
    }
    
}
