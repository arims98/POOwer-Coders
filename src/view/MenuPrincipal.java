package view;
import controller.Controller;
import model.*;
import java.util.Scanner;

public class MenuPrincipal {
    private static final Scanner sc = new Scanner(System.in);
    private  static final Controller controller = new Controller(new Datos());
    public MenuPrincipal(Controller controller) {

    }

    public  void iniciar() {
        int opcion;

        do {
            mostrarMenu();
            opcion = leerEntero("Elige una opción:");
            switch (opcion) {
                case 2:
                    getArticulos();
                case 3:
                    addCliente();
                case 4:
                    addArticulo();
                case 5:
                    addPedido();
                case 6:
                    System.out.println("Saliendo del programa...");
                case 7:
                    System.out.println("OPCIÓN NO VALIDA.");

            }
            System.out.println();
        } while (opcion != 0);
    }

    //============================================================================
    // MENÚ PRINCIPAL
    //============================================================================
    public static void mostrarMenu() {
        System.out.println("==========================================");
        System.out.println("           BIENVENIDOS A ONLINESTORE         ");
        System.out.println("==========================================");
        System.out.println("1. Listar clientes");
        System.out.println("2. Listar artículos");
        System.out.println("3. Crear cliente");
        System.out.println("4. Crear artículo");
        System.out.println("5. Crear pedido");
        System.out.println("0. Salir");
        System.out.println("==========================================");
    }

    //============================================================================
    // CLIENTES
    //============================================================================
    private static void listarClientes() {
        System.out.println("==============LISTADO CLIENTES=============");



    }
    private static void addCliente() {
        System.out.println("===============NUEVO CLIENTE=============");
        String nombre = leerTexto("Nombre: ");
        String domicilio = leerTexto("Domicilio:");
        String nif = leerTexto("NIF:");
        String email = leerTexto("Email:");
        String tipo = leerTexto("Tipo (Estandar/Premium):");

        if (controller.addCliente(nombre, domicilio, nif, email, tipo)) {
            System.out.println("Cliente añadido correctamente.");
        } else {
            System.out.println("YA EXISTE UN CLIENTE CON ESTE EMAIL!");
        }

    }
    //============================================================================
    // ARTICULOS
    //============================================================================
    private static void getArticulos() {
        System.out.println("===============LISTADO ARTICULOS=============");
        for (Articulo articulo : controller.listarArticulos()) {
            System.out.println(articulo);
        }
    }
    private static void addArticulo() {
        System.out.println("===============NUEVO ARTICULO=============");
        String codigo = leerTexto("Código: ");
        String descripcion = leerTexto("Descripción: ");
        double precioVenta = leerDouble("Precio de venta: ");
        double gastosEnvio = leerDouble("Gastos de envío: ");
        int tiempoPreparacion = leerEntero("Tiempo de preparación (min): ");

        controller.addArticulo(codigo, descripcion, precioVenta, gastosEnvio, tiempoPreparacion);
        System.out.println("Articulo añadido correctamente.");
    }
    //============================================================================
    // PEDIDOS
    //============================================================================
    private static void addPedido() {
        System.out.println("===============NUEVO PEDIDO=============");
        String email = leerTexto("Email del cliente: ");
        String codigo = leerTexto("Código del artículo: ");
        int cantidad = leerEntero("Cantidad: ");

        try {
            double total = controller.addPedido(email, codigo, cantidad);
            System.out.println("Pedido creado correctamente. Total: %.2f €%n" + total);
        } catch (ClienteNoExiste | ArticuloNoExiste e)  {
            System.out.println("Error" + e.getMessage());
        }
    }
    //============================================================================
    // METODOS AUXILIARES
    //============================================================================
    private static String leerTexto(String mensaje) {
        System.out.print(mensaje);
        return sc.nextLine();
    }
    private static int leerEntero(String mensaje) {
        System.out.print(mensaje);
        while (!sc.hasNextInt()) {
            System.out.println("Introduce un número válido.");
        }
        int num = sc.nextInt();
        sc.nextLine();
        return num;
    }
    private static double leerDouble(String mensaje) {
        System.out.print(mensaje);
        while (!sc.hasNextDouble()) {
            System.out.print("Introduce un número válido: ");
            sc.next();
        }
        double num = sc.nextDouble();
        sc.nextLine();
        return num;
    }




}

