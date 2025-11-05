//View: Es la interfaz que el usuario ve.
// Obtiene datos del Modelo para mostrar y se encarga solo de mostrar la información y recibir interacciones del usuario que envía al Controlador.
// No contiene lógica de negocio ni acceso a datos.
package view;

import controller.PedidoControlador;
import controller.ClienteControlador;
import controller.ArticuloControlador;
import model.Pedido;
import model.Cliente;
import model.Articulo;

import java.util.List;
import java.util.Scanner;

public class PedidoVista {
    private final PedidoControlador pedidoCtrl;
    private final ClienteControlador clienteCtrl;
    private final ArticuloControlador articuloCtrl;

    // Constructor recibe todos los controladores que necesita
    public PedidoVista(PedidoControlador pedidoCtrl, ClienteControlador clienteCtrl, ArticuloControlador articuloCtrl) {
        this.pedidoCtrl = pedidoCtrl;
        this.clienteCtrl = clienteCtrl;
        this.articuloCtrl = articuloCtrl;
    }

    public void iniciar(Scanner sc) {
        int opcion;
        do {
            System.out.println("\n=== GESTIÓN DE PEDIDOS ===");
            System.out.println("1. Crear pedido");
            System.out.println("2. Listar todos los pedidos");
            System.out.println("3. Buscar pedido");
            System.out.println("4. Eliminar pedido (solo si no está enviado)");
            System.out.println("5. Mostrar pedidos pendientes de envío");
            System.out.println("6. Mostrar pedidos enviados");
            System.out.println("0. Volver al menú principal");
            System.out.print("Elige una opción: ");
            opcion = sc.nextInt();
            sc.nextLine();

            switch (opcion) {
                case 1 -> { // Crear pedido
                    try {
                        System.out.print("Número de pedido: ");
                        String num = sc.nextLine();
                        System.out.print("NIF del cliente: ");
                        String nif = sc.nextLine();
                        Cliente cliente = clienteCtrl.listarClientes().stream()
                                .filter(c -> c.getNif().equalsIgnoreCase(nif))
                                .findFirst()
                                .orElse(null);

                        if (cliente == null) {
                            System.out.println("Cliente no encontrado. Introduce sus datos:");
                            System.out.print("Nombre: ");
                            String nombre = sc.nextLine();
                            System.out.print("Domicilio: ");
                            String domicilio = sc.nextLine();
                            System.out.print("Email: ");
                            String email = sc.nextLine();
                            System.out.print("Tipo (E/P): ");
                            String tipo = sc.nextLine();
                            clienteCtrl.agregarCliente(nombre, domicilio, nif, email, tipo);
                            cliente = clienteCtrl.listarClientes().stream()
                                    .filter(c -> c.getNif().equalsIgnoreCase(nif))
                                    .findFirst().orElse(null);
                        }

                        System.out.print("Código del artículo: ");
                        String cod = sc.nextLine();
                        Articulo articulo = articuloCtrl.listarArticulos().stream()
                                .filter(a -> a.getCodigo().equalsIgnoreCase(cod))
                                .findFirst().orElse(null);
                        if (articulo == null) {
                            System.out.println("El artículo no existe.");
                            break;
                        }

                        System.out.print("Cantidad: ");
                        int cant = sc.nextInt();
                        sc.nextLine();
                        pedidoCtrl.crearPedido(num, cliente, articulo, cant);
                        System.out.println("Pedido creado correctamente.");
                    } catch (Exception e) {
                        System.out.println("Error: " + e.getMessage());
                    }
                }
                case 2 -> { // Listar pedidos
                    System.out.println("\n=== LISTADO DE PEDIDOS ===");
                    pedidoCtrl.listarPedidos().forEach(System.out::println);
                }
                case 3 -> { // Buscar pedido
                    System.out.print("Número de pedido: ");
                    String num = sc.nextLine();
                    Pedido p = pedidoCtrl.buscarPedido(num);
                    System.out.println(p != null ? p : "Pedido no encontrado.");
                }
                case 4 -> { // Eliminar pedido
                    System.out.print("Número de pedido: ");
                    String num = sc.nextLine();
                    Pedido pedido = pedidoCtrl.buscarPedido(num);
                    if (pedido == null) System.out.println("Pedido no encontrado.");
                    else {
                        long diff = java.time.Duration.between(pedido.getFechaHora(),
                                java.time.LocalDateTime.now()).toMinutes();
                        if (diff >= pedido.getArticulo().getTiempoPreparacion())
                            System.out.println("No se puede eliminar. Pedido ya enviado.");
                        else {
                            pedidoCtrl.eliminarPedido(num);
                            System.out.println("Pedido eliminado correctamente.");
                        }
                    }
                }
                case 5 -> { // Pedidos pendientes
                    System.out.println("\n=== PEDIDOS PENDIENTES ===");
                    System.out.print("Filtrar por NIF cliente (S/N): ");
                    String filtrar = sc.nextLine();
                    if (filtrar.equalsIgnoreCase("S")) {
                        System.out.print("NIF cliente: ");
                        String nif = sc.nextLine();
                        List<Pedido> pedidos = pedidoCtrl.listarPedidosPendientesPorCliente(nif);
                        pedidos.forEach(System.out::println);
                    } else {
                        pedidoCtrl.listarPedidosPendientes().forEach(System.out::println);
                    }
                }
                case 6 -> { // Pedidos enviados
                    System.out.println("\n=== PEDIDOS ENVIADOS ===");
                    System.out.print("Filtrar por NIF cliente (S/N): ");
                    String filtrar = sc.nextLine();
                    if (filtrar.equalsIgnoreCase("S")) {
                        System.out.print("NIF cliente: ");
                        String nif = sc.nextLine();
                        List<Pedido> pedidos = pedidoCtrl.listarPedidosEnviadosPorCliente(nif);
                        pedidos.forEach(System.out::println);
                    } else {
                        pedidoCtrl.listarPedidosEnviados().forEach(System.out::println);
                    }
                }
                case 0 -> System.out.println("Volviendo al menú principal...");
                default -> System.out.println("Opción incorrecta.");
            }
        } while (opcion != 0);
    }
}
