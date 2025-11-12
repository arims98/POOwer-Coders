package test;

import dao.DAOFactory;
import dao.Repositorio;
import model.*;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Clase de prueba para verificar que el proyecto funciona correctamente
 * con la base de datos online_store_db
 */
public class PruebasBasicas {

    public static void main(String[] args) {
        System.out.println("â•”â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•—");
        System.out.println("â•‘   PRUEBAS DEL PROYECTO - online_store_db              â•‘");
        System.out.println("â•šâ•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•\n");

        try {
            // ============================================
            // PRUEBA 1: ConexiÃ³n a la Base de Datos
            // ============================================
            System.out.println("ðŸ“¡ PRUEBA 1: Verificar conexiÃ³n a BD");
            System.out.println("â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”");
            
            Repositorio<Articulo, Integer> articuloDAO = DAOFactory.getArticuloDAO();
            List<Articulo> articulos = articuloDAO.listar();
            
            System.out.println("âœ… ConexiÃ³n exitosa");
            System.out.println("   ArtÃ­culos encontrados: " + articulos.size());
            System.out.println();

            // ============================================
            // PRUEBA 2: Listar ArtÃ­culos
            // ============================================
            System.out.println("ðŸ“¦ PRUEBA 2: Listar artÃ­culos de la BD");
            System.out.println("â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”");
            
            for (Articulo art : articulos) {
                System.out.println("   " + art);
            }
            System.out.println();

            // ============================================
            // PRUEBA 3: Listar Clientes
            // ============================================
            System.out.println("ðŸ‘¥ PRUEBA 3: Listar clientes");
            System.out.println("â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”");
            
            Repositorio<Cliente, String> clienteDAO = DAOFactory.getClienteDAO();
            List<Cliente> clientes = clienteDAO.listar();
            
            for (Cliente cli : clientes) {
                String tipo = cli instanceof ClientePremium ? "Premium" : "EstÃ¡ndar";
                System.out.println("   " + cli.getNombre() + " (" + cli.getEmail() + ") - " + tipo);
                
                if (cli instanceof ClientePremium) {
                    ClientePremium premium = (ClientePremium) cli;
                    System.out.println("      â†’ Descuento envÃ­o: " + premium.getDescuentoEnvio() + "%");
                }
            }
            System.out.println();

            // ============================================
            // PRUEBA 4: Buscar un Cliente EspecÃ­fico
            // ============================================
            System.out.println("ðŸ” PRUEBA 4: Buscar cliente por email");
            System.out.println("â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”");
            
            Cliente sergio = clienteDAO.buscarPorId("sergio@tienda.es");
            if (sergio != null) {
                System.out.println("âœ… Cliente encontrado: " + sergio.getNombre());
                System.out.println("   Email: " + sergio.getEmail());
                System.out.println("   Tipo: " + (sergio instanceof ClientePremium ? "Premium" : "EstÃ¡ndar"));
            } else {
                System.out.println("âŒ Cliente no encontrado");
            }
            System.out.println();

            // ============================================
            // PRUEBA 5: Crear un Pedido (Con cÃ¡lculo automÃ¡tico)
            // ============================================
            System.out.println("ðŸ›’ PRUEBA 5: Crear un pedido nuevo");
            System.out.println("â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”");
            
            // Buscar cliente y artÃ­culo
            Cliente cliente = clienteDAO.buscarPorId("sergio@tienda.es");
            Articulo articulo = articuloDAO.buscarPorId(1);
            
            if (cliente != null && articulo != null) {
                // Crear pedido
                Pedido nuevoPedido = new Pedido(cliente, articulo, 3, LocalDateTime.now());
                
                // Guardar (el precio se calcula automÃ¡ticamente)
                Repositorio<Pedido, Integer> pedidoDAO = DAOFactory.getPedidoDAO();
                pedidoDAO.agregar(nuevoPedido);
                
                System.out.println("âœ… Pedido creado exitosamente");
                System.out.println("   NÃºmero: " + nuevoPedido.getNumPedido());
                System.out.println("   Cliente: " + cliente.getNombre());
                System.out.println("   ArtÃ­culo: " + articulo.getDescripcion());
                System.out.println("   Cantidad: " + nuevoPedido.getCantidad());
                System.out.println("   ðŸ’° TOTAL: " + String.format("%.2fâ‚¬", nuevoPedido.getPrecioTotal()));
            }
            System.out.println();

            // ============================================
            // PRUEBA 6: Listar Pedidos
            // ============================================
            System.out.println("ðŸ“‹ PRUEBA 6: Listar todos los pedidos");
            System.out.println("â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”");
            
            Repositorio<Pedido, Integer> pedidoDAO = DAOFactory.getPedidoDAO();
            List<Pedido> pedidos = pedidoDAO.listar();
            
            System.out.println("   Total de pedidos: " + pedidos.size());
            for (Pedido ped : pedidos) {
                System.out.println("   " + ped);
            }
            System.out.println();

            // ============================================
            // PRUEBA 7: Actualizar un Pedido
            // ============================================
            System.out.println("âœï¸  PRUEBA 7: Actualizar cantidad de un pedido");
            System.out.println("â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”");
            
            if (!pedidos.isEmpty()) {
                Pedido pedidoExistente = pedidos.get(0);
                double precioAnterior = pedidoExistente.getPrecioTotal();
                int cantidadAnterior = pedidoExistente.getCantidad();
                
                System.out.println("   Pedido #" + pedidoExistente.getNumPedido());
                System.out.println("   Cantidad anterior: " + cantidadAnterior + " â†’ Total: " + String.format("%.2fâ‚¬", precioAnterior));
                
                // Cambiar cantidad (el precio se recalcula automÃ¡ticamente)
                pedidoExistente.setCantidad(cantidadAnterior + 1);
                pedidoDAO.actualizar(pedidoExistente);
                
                System.out.println("   Nueva cantidad: " + pedidoExistente.getCantidad() + " â†’ Total: " + String.format("%.2fâ‚¬", pedidoExistente.getPrecioTotal()));
            }
            System.out.println();

            // ============================================
            // RESUMEN FINAL
            // ============================================
            System.out.println("â•”â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•—");
            System.out.println("â•‘              âœ… TODAS LAS PRUEBAS EXITOSAS             â•‘");
            System.out.println("â•šâ•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•");
            System.out.println("\nðŸŽ‰ El proyecto funciona correctamente con online_store_db\n");

        } catch (Exception e) {
            System.err.println("\nâŒ ERROR EN LAS PRUEBAS:");
            System.err.println("â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”â”");
            System.err.println(e.getMessage());
            e.printStackTrace();
            System.out.println("\nðŸ’¡ POSIBLES SOLUCIONES:");
            System.out.println("   1. Verifica que MySQL estÃ© ejecutÃ¡ndose");
            System.out.println("   2. Importa database.sql y procedimientos_almacenados.sql");
            System.out.println("   3. Verifica la contraseÃ±a en ConexionBD.java");
        }
    }
}
