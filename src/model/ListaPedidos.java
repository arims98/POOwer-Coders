package model;

import java.util.ArrayList;     //Creamos lista dinÃ¡mica
import java.util.List;

public class ListaPedidos {

    private List<Pedido> lista;     //Listado de todos los pedidos

    private static int nextNumeroPedido = 1;   //Asignamos ID Ãºnico a cada pedido

    //Constructor que inicializa la lista (estanterÃ­a), como Array list vacÃ­a
    public ListaPedidos() {
        this.lista = new ArrayList<>();
    }

    //MÃ©todo para aÃ±adir pedidos a la lista
    public void agregarPedido(Pedido pedido) {
        //Asignamos un nÃºmero de pedido Ãºnico, e icrementamos el contador para el siguiente pedido
        pedido.setNumeroPedido(nextNumeroPedido++);
        //AÃ±adimos el pedido a la lista
        this.lista.add(pedido);
    }

    //MÃ©todo para buscar un pedido por su ID
    public Pedido buscarPedidoPorNumero(int numeroPedido) {
        //Recorremos la lista de pedidos
        for (Pedido pedido : lista) {
            //Si el ID del pedido coincide con el nÃºmero buscado
            if (pedido.getNumeroPedido() == numeroPedido) {
                //Devolvemos el pedido encontrado
                return pedido;
            }
        }
        //Si no se encuentra el ID del pedido, devolvemos null
        return null;
    }
    
    //MÃ©todo eliminar pedido, buscando por ID
    public boolean eliminarPedidoPorNumero(int numeroPedido) {
        //Buscamos el pedido por su ID
        Pedido pedidoAEliminar = buscarPedidoPorNumero(numeroPedido);
        //Si se encuentra el pedido, lo eliminamos de la lista
        if (pedidoAEliminar != null) {
            lista.remove(pedidoAEliminar);
            return true;    //EliminaciÃ³n completada
        }
        return false;   //No se encontrÃ³ el pedido para eliminar
    }
    
    //MÃ©todo para obtener la lista de pedidos pendientes
    // NOTA: Como la BD ya no tiene campo "estado", este mÃ©todo devuelve todos los pedidos
    // PodrÃ­as filtrar por fecha u otro criterio si lo necesitas
    public List<Pedido> getPedidosPendientes() {
        // Devolvemos todos los pedidos (ya no hay estado en la BD)
        return new ArrayList<>(lista);
    }

    //mÃ©todo para obtener la lista de pedidos enviados
    // NOTA: Como la BD ya no tiene campo "estado", este mÃ©todo devuelve lista vacÃ­a
    // O podrÃ­as usar otro criterio para identificar pedidos "enviados"
    public List<Pedido> getPedidosEnviados() {
        // Sin campo estado en BD, devolvemos lista vacÃ­a
        // PodrÃ­as implementar otro filtro si lo necesitas
        return new ArrayList<>();
    }
}
