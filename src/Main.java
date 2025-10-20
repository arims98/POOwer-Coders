import controller.PedidoControlador;
import dao.PedidoRepositorio;
import java.io.PrintStream;
import java.util.List;
import java.util.Scanner;
import model.Articulo;
import model.Cliente;
import model.ClienteEstandar;
import model.ClientePremium;
import model.Pedido;

public class Main {
    public Main() {
    }

    public static void main(String[] args) {
        PedidoRepositorio repositorio = new PedidoRepositorio();
        repositorio.cargarDatosPrueba();
        PedidoControlador controlador = new PedidoControlador(repositorio);
        Scanner sc = new Scanner(System.in);

        int opcion;
        do {
            System.out.println("\n=== MENÚ PEDIDOS ===");
            System.out.println("1. Crear pedido");
            System.out.println("2. Listar pedidos");
            System.out.println("3. Buscar pedido");
            System.out.println("4. Eliminar pedido");
            System.out.println("0. Salir");
            System.out.print("Elige una opción: ");
            opcion = sc.nextInt();
            sc.nextLine();
            String num;
            String domicilio;
            switch (opcion) {
                case 0:
                    System.out.println("Saliendo...");
                    break;
                case 1:
                    try {
                        System.out.print("Número de pedido: ");
                        num = sc.nextLine();
                        System.out.print("Nombre cliente: ");
                        String nombre = sc.nextLine();
                        System.out.print("Domicilio cliente: ");
                        domicilio = sc.nextLine();
                        System.out.print("NIF cliente: ");
                        String nif = sc.nextLine();
                        System.out.print("Email cliente: ");
                        String email = sc.nextLine();
                        System.out.print("Tipo cliente (E = Estandar / P = Premium): ");
                        String tipo = sc.nextLine().toUpperCase();
                        Cliente cliente = tipo.equals("P") ? new ClientePremium(nombre, domicilio, nif, email) : new ClienteEstandar(nombre, domicilio, nif, email);
                        System.out.print("Código artículo: ");
                        String cod = sc.nextLine();
                        System.out.print("Descripción artículo: ");
                        String desc = sc.nextLine();
                        System.out.print("Precio venta: ");
                        double precio = sc.nextDouble();
                        System.out.print("Gastos envío: ");
                        double envio = sc.nextDouble();
                        System.out.print("Tiempo preparación: ");
                        int tiempo = sc.nextInt();
                        sc.nextLine();
                        Articulo articulo = new Articulo(cod, desc, precio, envio, tiempo);
                        System.out.print("Cantidad: ");
                        int cant = sc.nextInt();
                        sc.nextLine();
                        controlador.crearPedido(num, (Cliente)cliente, articulo, cant);
                        System.out.println("Pedido creado correctamente.");
                    } catch (Exception var21) {
                        System.out.println("Error: " + var21.getMessage());
                    }
                    break;
                case 2:
                    System.out.println("\n=== LISTADO DE PEDIDOS ===");
                    List var10000 = controlador.listarPedidos();
                    PrintStream var10001 = System.out;
                    var10000.forEach(var10001::println);
                    break;
                case 3:
                    System.out.print("Número de pedido a buscar: ");
                    num = sc.nextLine();
                    Pedido p = controlador.buscarPedido(num);
                    System.out.println(p != null ? p : "Pedido no encontrado.");
                    break;
                case 4:
                    System.out.print("Número de pedido a eliminar: ");
                    domicilio = sc.nextLine();
                    controlador.eliminarPedido(domicilio);
                    System.out.println("Pedido eliminado si existía.");
                    break;
                default:
                    System.out.println("Opción incorrecta.");
            }
        } while(opcion != 0);

        sc.close();
    }
}