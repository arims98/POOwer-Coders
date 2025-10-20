package model;

import java.util.ArrayList;     //Creamos lista dinámica
import java.util.List;  
import java.util.stream.Collectors; //Para usar streams y collectors (hace de filtro)

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
    public List<Pedido> getPedidosPendientes() {
        //Se usan streams y "instanceof" para filtrar la lista
        return lista.stream()
                .filter(p -> p.getEstado() == Pedido.Estado.PENDIENTE)
                .collect(Collectors.toList());
    }

    //método para obtener la lista de pedidos enviados
    public List<Pedido> getPedidosEnviados() {
        //Se usan streams y "instanceof" para filtrar la lista
        return lista.stream()
                .filter(p -> p.getEstado() == Pedido.Estado.ENVIADO)
                .collect(Collectors.toList());
    }
}
