//DAO (Data Access Object): Es una capa independiente que se encarga exclusivamente del acceso y la gestión de los datos en la base de datos.
// Separa la lógica de acceso a datos del Modelo, permitiendo que el Modelo use DAO para obtener o persistir datos sin preocuparse por detalles de conexión o consultas.
package dao;

import model.*;
import java.util.List; // Interfaz de listas!

// Implementa la interfaz Repositorio para manejar objetos Pedido
public interface IPedidoDAO {

    public Pedido agregar(Pedido pedido);

    public Pedido buscarPorId(String numeroPedido);

    public List<Pedido> listar();

    public boolean eliminar(String numeroPedido);

}


    /*// DATOS
    public void cargarDatosPrueba() {
        // Pedido 1: cliente estándar, pendiente de envío
        ClienteEstandar cliente1 = new ClienteEstandar("Sergio Gomez Gutierrez", "Calle Luna 3, Barcelona", "12345678A", "sgomezg@uoc.edu");
        Articulo articulo1 = new Articulo("A01", "Auriculares Bluetooth", 60.0, 5.0, 5);
        Pedido pedido1 = new Pedido("P01", cliente1, articulo1, 3, LocalDateTime.now());
        pedido1.setEstado("Pendiente de envío"); // Marcamos explícitamente el estado
        this.agregar(pedido1);

        // Pedido 2: cliente premium, ya enviado
        ClientePremium cliente2 = new ClientePremium("Ariadna Martínez Serra", "Av. Sol 10, Barcelona", "87654321B", "amartinezs@uoc.edu");
        Articulo articulo2 = new Articulo("A02", "Teclado", 80.0, 5.0, 5);
        Pedido pedido2 = new Pedido("P02", cliente2, articulo2, 2, LocalDateTime.now().minusDays(1));
        pedido2.setEstado("Enviado");
        this.agregar(pedido2);

        // Pedido 3: cliente estándar, entregado
        ClienteEstandar cliente3 = new ClienteEstandar("Meritxell Moreno Moya", "Calle Estrella 5, Barcelona", "23456789C", "mmorenom@uoc.edu");
        Articulo articulo3 = new Articulo("A03", "Ratón Inalámbrico", 20.0, 5.0, 5);
        Pedido pedido3 = new Pedido("P03", cliente3, articulo3, 1, LocalDateTime.now().minusDays(2));
        pedido3.setEstado("Entregado");
        this.agregar(pedido3);

        // Pedido 4: cliente premium, ya enviado
        ClientePremium cliente4 = new ClientePremium("Cèlia Trullà Estruch", "Plaza Mayor 12, Barcelona", "98765432D", "ctrullae@uoc.edu");
        Articulo articulo4 = new Articulo("A04", "Monitor 24 pulgadas", 120.0, 10.0, 10);
        Pedido pedido4 = new Pedido("P04", cliente4, articulo4, 1, LocalDateTime.now().minusDays(3));
        pedido4.setEstado("Enviado");
        this.agregar(pedido4);
    }*/

