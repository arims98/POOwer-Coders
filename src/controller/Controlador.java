package controller;

import model.Datos;
import view.Consola; // NecesitarÃ¡s pasarme esta clase si da error
import model.Articulo;
import model.Cliente;
import model.ClienteEstandar;
import model.ClientePremium;
import model.Pedido;

import java.time.LocalDateTime;
import java.util.List;

public class Controlador {
    
    private Datos datos;     
    private Consola vista;   

    public Controlador() {
        this.datos = new Datos();
        this.vista = new Consola(); // Asumo que tienes esta clase en 'view'
        
        // <-- CAMBIO: Â¡YA NO CARGAMOS DATOS DEMO!
        // this.datos.cargarDatosDemo(); 
    }

    public static void main(String[] args) {
        Controlador app = new Controlador();
        app.iniciarApp(); 
    }
    
    // ... (Tu 'iniciarApp' y 'switch' se quedan igual) ...
    // Pega el resto de tu mÃ©todo iniciarApp() aquÃ­
    public void iniciarApp() {
        
        int opcion;

        do {
            opcion = vista.menuPrincipal(); 
            
            switch (opcion) {
                case 1:
                    menuGestionArticulos();
                    break;
                case 2:
                    menuGestionClientes();
                    break;
                case 3:
                    menuGestionPedidos();
                    break;
                case 4: 
                    vista.mostrarMensaje("Â¡Gracias por visitar Online Store!");
                    break;
                default:
                    vista.mostrarMensaje("OpciÃ³n no vÃ¡lida. IntÃ©ntalo de nuevo.");
            }
        } while (opcion != 4);
    }
    
    // ... (Tus mÃ©todos de 'menu' se quedan igual) ...
    // Pega tus mÃ©todos menuGestionArticulos(), menuGestionClientes(), menuGestionPedidos()
    private void menuGestionArticulos() {
        int opcion;
        do {
            opcion = vista.menuArticulos();
            switch (opcion) {
                case 1:
                    mostrarArticulos();
                    break;
                case 2:
                    // agregarArticulo(); // TendrÃ­as que crear este mÃ©todo
                    break;
                case 3:
                    // eliminarArticulo(); // TendrÃ­as que crear este mÃ©todo
                    break;
                case 4:
                    vista.mostrarMensaje("Volviendo al menÃº principal...");
                    break;
                default:
                    vista.mostrarMensaje("OpciÃ³n no vÃ¡lida.");
            }
        } while (opcion != 4);
    }

    private void menuGestionClientes() {
        int opcion;
        do {
            opcion = vista.menuClientes();
            switch (opcion) {
                case 1:
                    mostrarClientes();
                    break;
                case 2:
                    agregarCliente();
                    break;
                case 3:
                    // eliminarCliente(); // TendrÃ­as que crear este mÃ©todo
                    break;
                case 4:
                    vista.mostrarMensaje("Volviendo al menÃº principal...");
                    break;
                default:
                    vista.mostrarMensaje("OpciÃ³n no vÃ¡lida.");
            }
        } while (opcion != 4);
    }

    private void menuGestionPedidos() {
        int opcion;
        do {
            opcion = vista.menuPedidos();
            switch (opcion) {
                case 1:
                    mostrarPedidos();
                    break;
                case 2:
                    agregarPedido();
                    break;
                case 3:
                    eliminarPedido();
                    break;
                case 4:
                    vista.mostrarMensaje("Volviendo al menÃº principal...");
                    break;
                default:
                    vista.mostrarMensaje("OpciÃ³n no vÃ¡lida.");
            }
        } while (opcion != 4);
    }

    // --- MÃ‰TODOS DE LÃ“GICA (AHORA USAN DAOs) ---
    
    private void mostrarArticulos() {
        vista.mostrarMensaje("\n--- LISTA DE ARTÃCULOS ---");
        try {
            // <-- CAMBIO: Usamos el DAO
            List<Articulo> articulos = datos.getRepoArticulo().listar();
            
            if (articulos.isEmpty()) {
                vista.mostrarMensaje("No hay artÃ­culos que mostrar.");
            } else {
                for (Articulo a : articulos) {
                    vista.mostrarMensaje(a.toString());
                }
            }
        } catch (Exception e) {
            vista.mostrarMensaje("Error al listar artÃ­culos: " + e.getMessage());
        }
    }

    private void mostrarClientes() {
        vista.mostrarMensaje("\n--- LISTA DE CLIENTES ---");
        try {
            // <-- CAMBIO: Usamos el DAO
            List<Cliente> clientes = datos.getRepoCliente().listar();
            
            if (clientes.isEmpty()) {
                vista.mostrarMensaje("No hay clientes que mostrar.");
            } else {
                for (Cliente c : clientes) {
                    vista.mostrarMensaje(c.toString());
                }
            }
        } catch (Exception e) {
            vista.mostrarMensaje("Error al listar clientes: " + e.getMessage());
        }
    }

    private void agregarCliente() {
        vista.mostrarMensaje("\n--- CREAR NUEVO CLIENTE ---");
        
        String nombre = vista.pedirString("Nombre: ");
        String domicilio = vista.pedirString("Domicilio: ");
        String nif = vista.pedirString("NIF: ");
        String email = vista.pedirString("Email: ");
        int tipo = vista.pedirInt("Tipo de cliente (1=EstÃ¡ndar, 2=Premium): ");
        
        Cliente nuevoCliente;
        if (tipo == 2) {
            nuevoCliente = new ClientePremium(nombre, domicilio, nif, email);
        } else {
            nuevoCliente = new ClienteEstandar(nombre, domicilio, nif, email);
        }
        
        try {
            // <-- CAMBIO: Usamos el DAO
            datos.getRepoCliente().agregar(nuevoCliente);
            vista.mostrarMensaje("Cliente '" + nombre + "' creado correctamente.");
        } catch (Exception e) {
            vista.mostrarMensaje("Error al crear cliente: " + e.getMessage());
        }
    }
    
    private void mostrarPedidos() {
        vista.mostrarMensaje("\n--- LISTA DE PEDIDOS ---");
        try {
            // <-- CAMBIO: Usamos el DAO
            List<Pedido> pedidos = datos.getRepoPedido().listar();
            
            if (pedidos.isEmpty()) {
                vista.mostrarMensaje("No hay pedidos que mostrar.");
            } else {
                for (Pedido p : pedidos) {
                    vista.mostrarMensaje(p.toString());
                }
            }
        } catch (Exception e) {
            vista.mostrarMensaje("Error al listar pedidos: " + e.getMessage());
            e.printStackTrace(); // Para ver el error completo
        }
    }

    private void agregarPedido() {
        vista.mostrarMensaje("\n--- CREAR NUEVO PEDIDO ---");
        
        String emailCliente = vista.pedirString("Email del cliente: ");
        int codigoArticulo = vista.pedirInt("CÃ³digo del artÃ­culo: ");
        int cantidad = vista.pedirInt("Cantidad de unidades: ");
        
        try {
            // <-- CAMBIO: Buscamos con los DAOs
            Cliente cliente = datos.getRepoCliente().buscarPorId(emailCliente);
            Articulo articulo = datos.getRepoArticulo().buscarPorId(codigoArticulo);
            
            if (cliente == null || articulo == null) {
                vista.mostrarMensaje("ERROR!!: Cliente o ArtÃ­culo no encontrado.");
                return;
            }
            
            // <-- CAMBIO: Usamos el constructor sin ID de Pedido
            Pedido nuevoPedido = new Pedido(cliente, articulo, cantidad, LocalDateTime.now());
            
            // <-- CAMBIO: Usamos el DAO
            datos.getRepoPedido().agregar(nuevoPedido);
            
            vista.mostrarMensaje(" Pedido N.Âº " + nuevoPedido.getNumPedido() + " (ID de BD) creado correctamente.");
            
        } catch (Exception e) {
            vista.mostrarMensaje("Error al crear pedido: " + e.getMessage());
        }
    }

    private void eliminarPedido() {
        vista.mostrarMensaje("\n--- ELIMINAR PEDIDO ---");
        int numeroPedido = vista.pedirInt("Introduce el nÃºmero de pedido a eliminar: ");
        
        try {
            // <-- CAMBIO: Usamos el DAO
            datos.getRepoPedido().eliminar(numeroPedido);
            vista.mostrarMensaje(" Pedido N.Âº " + numeroPedido + " eliminado correctamente.");
        } catch (Exception e) {
            vista.mostrarMensaje("ERROR!!: El pedido no se pudo eliminar: " + e.getMessage());
        }
    }
}