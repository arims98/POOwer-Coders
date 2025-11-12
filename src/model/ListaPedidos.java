package model;

import java.util.ArrayList;     //Creamos lista dinámica
import java.util.List;

public class ListaPedidos {

    private List<Pedido> lista;     //Listado de todos los pedidos

    private static int nextNumeroPedido = 1;   //Asignamos ID único a cada pedido

    //Constructor que inicializa la lista (estantería), como Array list vacía
    public ListaPedidos() {
        this.lista = new ArrayList<>();
    }

    //Método para añadir pedidos a la lista
    public void agregarPedido(Pedido pedido) {
        //Asignamos un número de pedido único, e icrementamos el contador para el siguiente pedido
        pedido.setNumeroPedido(nextNumeroPedido++);
        //Añadimos el pedido a la lista
        this.lista.add(pedido);
    }

    //Método para buscar un pedido por su ID
    public Pedido buscarPedidoPorNumero(int numeroPedido) {
        //Recorremos la lista de pedidos
        for (Pedido pedido : lista) {
            //Si el ID del pedido coincide con el número buscado
            if (pedido.getNumeroPedido() == numeroPedido) {
                //Devolvemos el pedido encontrado
                return pedido;
            }
        }
        //Si no se encuentra el ID del pedido, devolvemos null
        return null;
    }
    
    //Método eliminar pedido, buscando por ID
    public boolean eliminarPedidoPorNumero(int numeroPedido) {
        //Buscamos el pedido por su ID
        Pedido pedidoAEliminar = buscarPedidoPorNumero(numeroPedido);
        //Si se encuentra el pedido, lo eliminamos de la lista
        if (pedidoAEliminar != null) {
            lista.remove(pedidoAEliminar);
            return true;    //Eliminación completada
        }
        return false;   //No se encontró el pedido para eliminar
    }
    
    //Método para obtener la lista de pedidos pendientes
    // NOTA: Como la BD ya no tiene campo "estado", este método devuelve todos los pedidos
    // Podrías filtrar por fecha u otro criterio si lo necesitas
    public List<Pedido> getPedidosPendientes() {
        // Devolvemos todos los pedidos (ya no hay estado en la BD)
        return new ArrayList<>(lista);
    }

    //método para obtener la lista de pedidos enviados
    // NOTA: Como la BD ya no tiene campo "estado", este método devuelve lista vacía
    // O podrías usar otro criterio para identificar pedidos "enviados"
    public List<Pedido> getPedidosEnviados() {
        // Sin campo estado en BD, devolvemos lista vacía
        // Podrías implementar otro filtro si lo necesitas
        return new ArrayList<>();
    }
}
