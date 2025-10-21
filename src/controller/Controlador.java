package controller;

import model.Datos;
import view.Consola;
import model.Articulo;
import model.Cliente;
import model.ClienteEstandar;
import model.ClientePremium;
import model.Pedido;
import java.time.LocalDateTime;

public class Controlador {
    
    // Atributos: Los puentes hacia las otras capas.
    private Datos datos;     
    private Consola vista;   

    public Controlador() {
        // Inicialización del Modelo y la Vista
        this.datos = new Datos();
        this.vista = new Consola();
    }

    public static void main(String[] args) {
        Controlador app = new Controlador();
        app.iniciarApp(); 
    }
    
    // GESTIÓN DEL FLUJO PRINCIPAL

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
                    vista.mostrarMensaje("¡Gracias por visitar Online Store! 👋");
                    break;
                default:
                    vista.mostrarMensaje("Opción no válida. Intente de nuevo.");
            }
        } while (opcion != 4);
    }
    
    
    // MENÚS DE GESTIÓN (PUENTES)

    private void menuGestionArticulos() {
        int opcion;
        do {
            opcion = vista.menuArticulos(); 
            
            switch (opcion) {
                case 1: anadirArticulo(); break; 
                case 2: 
                    vista.mostrarLista(datos.getListaArticulos().getLista());
                    break;
                case 0: vista.mostrarMensaje("Volviendo al menú principal."); break;
                default: vista.mostrarMensaje("Opción no válida.");
            }
        } while (opcion != 0);
    }

    private void menuGestionClientes() {
        int opcion;
        do {
            opcion = vista.menuClientes(); 
            
            switch (opcion) {
                case 1: anadirCliente(); break;
                case 2: 
                    vista.mostrarLista(datos.getListaClientes().getLista());
                    break;
                case 3: 
                    vista.mostrarLista(datos.getListaClientes().getClientesEstandar());
                    break;
                case 4: 
                    vista.mostrarLista(datos.getListaClientes().getClientesPremium());
                    break;
                case 0: vista.mostrarMensaje("Volviendo al menú principal."); break;
                default: vista.mostrarMensaje("Opción no válida.");
            }
        } while (opcion != 0);
    }

    private void menuGestionPedidos() {
        int opcion;
        do {
            opcion = vista.menuPedidos(); 
            
            switch (opcion) {
                case 1: anadirPedido(); break;
                case 2: eliminarPedido(); break;
                case 3: 
                    vista.mostrarLista(datos.getListaPedidos().getPedidosPendientes()); 
                    break;
                case 4: 
                    vista.mostrarLista(datos.getListaPedidos().getPedidosEnviados()); 
                    break;
                case 0: vista.mostrarMensaje("Volviendo al menú principal."); break;
                default: vista.mostrarMensaje("Opción no válida.");
            }
        } while (opcion != 0);
    }

    // CREACIÓN DE OBJETOS Y OTRAS OPERACIONES

    private void anadirArticulo() {
        vista.mostrarMensaje("\n--- AÑADIR NUEVO ARTÍCULO ---");
        
        int codigo = vista.pedirInt("Introduce el código del artículo: ");
        String descripcion = vista.pedirString("Introduce la descripción: ");
        float precio = vista.pedirFloat("Introduce el precio de venta (€): ");
        float gastos = vista.pedirFloat("Introduce los gastos de envío (€): ");
        int tiempo = vista.pedirInt("Introduce el tiempo de preparación (minutos): ");
        
        Articulo nuevoArticulo = new Articulo(codigo, descripcion, precio, gastos, tiempo);
        datos.getListaArticulos().agregarArticulo(nuevoArticulo);
        
        vista.mostrarMensaje(" Artículo añadido correctamente.");
    }
    
    private void anadirCliente() {
        vista.mostrarMensaje("\n--- AÑADIR NUEVO CLIENTE ---");
        
        String nombre = vista.pedirString("Introduce el nombre: ");
        String domicilio = vista.pedirString("Introduce el domicilio: ");
        String nif = vista.pedirString("Introduce el NIF: ");
        String email = vista.pedirString("Introduce el email (ID): ");
        
        if (datos.getListaClientes().buscarClientePorEmail(email) != null) {
            vista.mostrarMensaje("ERROR!!: Ya existe un cliente con ese email.");
            return;
        }

        vista.mostrarMensaje("Tipo de cliente: (1) Estándar, (2) Premium");
        int tipo = vista.pedirInt("Seleccione tipo (1 o 2): ");
        
        Cliente nuevoCliente = null; // será ClienteEstandar o ClientePremium según selección
        if (tipo == 1) {
            nuevoCliente = new ClienteEstandar(nombre, domicilio, nif, email);
        } else if (tipo == 2) {
            nuevoCliente = new ClientePremium(nombre, domicilio, nif, email);
        } else {
            vista.mostrarMensaje("ATENCIÓN!! Tipo de cliente no válido. No se ha añadido el cliente.");
            return;
        }
        
        datos.getListaClientes().agregarCliente(nuevoCliente);
        vista.mostrarMensaje(" Cliente añadido correctamente.");
    }

    private void anadirPedido() {
        vista.mostrarMensaje("\n--- CREAR NUEVO PEDIDO ---");
        
        String emailCliente = vista.pedirString("Email del cliente: ");
        int codigoArticulo = vista.pedirInt("Código del artículo: ");
        int cantidad = vista.pedirInt("Cantidad de unidades: ");
        
        Cliente cliente = datos.getListaClientes().buscarClientePorEmail(emailCliente);
        Articulo articulo = datos.getListaArticulos().buscarArticuloPorCodigo(String.valueOf(codigoArticulo));
        
        if (cliente == null || articulo == null) {
            vista.mostrarMensaje("ERROR!!: Cliente o Artículo no encontrado.");
            return;
        }
        
        Pedido nuevoPedido = new Pedido(cliente, articulo, cantidad, LocalDateTime.now());
        datos.getListaPedidos().agregarPedido(nuevoPedido);
        
        vista.mostrarMensaje(" Pedido N.º " + nuevoPedido.getNumeroPedido() + " creado correctamente.");
    }

    private void eliminarPedido() {
        vista.mostrarMensaje("\n--- ELIMINAR PEDIDO ---");
        int numeroPedido = vista.pedirInt("Introduce el número de pedido a eliminar: ");
        
        boolean exito = datos.getListaPedidos().eliminarPedidoPorNumero(numeroPedido);
        
        if (exito) {
            vista.mostrarMensaje(" Pedido N.º " + numeroPedido + " eliminado correctamente.");
        } else {
            vista.mostrarMensaje("ERROR!!: El pedido no se pudo eliminar (no encontrado o tiempo límite superado).");
        }
    }
}