package model;

import java.util.ArrayList;     //Creamos lista dinÃ¡mica
import java.util.List;  

public class ListaArticulos {

    //"EstanterÃ­a dÃ³nde guardamos los artÃ­culos"
    private List<Articulo> lista;

    //Constructor que inicializa la lista (estanterÃ­a), como Array list vacÃ­a
    public ListaArticulos() {
        this.lista = new ArrayList<>();
    }

    //MÃ©todo para aÃ±adir artÃ­culos a la lista
    public void agregarArticulo(Articulo articulo) {
        //AÃ±adimos el artÃ­culo a la lista
        lista.add(articulo);
    }

    //MÃ©todo para obtener la lista de artÃ­culos
    public List<Articulo> getLista() {
        //Devuelve la lista completa
        return lista;  
    }

    //MÃ©todo para buscar un artÃ­culo por su cÃ³digo
    public Articulo buscarArticuloPorCodigo(String codigo) {
        //Recorremos la lista de artÃ­culos
        for (Articulo articulo : lista) {
            //Si el cÃ³digo del artÃ­culo coincide con el cÃ³digo buscado
            if (String.valueOf(articulo.getCodigoArticulo()).equals(codigo)) {
                //Devolvemos el artÃ­culo encontrado
                return articulo;
            }
        }
        //Si no se encuentra el artÃ­culo, devolvemos null
        return null;
    }
    
}
