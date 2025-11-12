package dao;

import model.Articulo;
import model.Cliente;
import model.Pedido;

// Utilizamos el patrón Factory para la creación de DAOs (Repositorios)
// Esto permite cambiar el motor de base de datos sin alterar el Main o los Controladores.
public interface DAOFactory {
    
    // Factory method para obtener el repositorio de Articulos
    Repositorio<Articulo> getArticuloRepositorio();
    
    // Factory method para obtener el repositorio de Clientes
    Repositorio<Cliente> getClienteRepositorio();
    
    // Factory method para obtener el repositorio de Pedidos
    Repositorio<Pedido> getPedidoRepositorio();
}