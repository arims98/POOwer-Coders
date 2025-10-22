package vista;

import controlador.Controlador;
import modelo.NegocioException;

import java.util.Scanner;

public class Consola {
    private final Controlador ctrl;
    private final Scanner sc = new Scanner(System.in);

    public Consola(Controlador ctrl) { this.ctrl = ctrl; }

    public void iniciar() {
        ctrl.cargarDemo();
        int op;
        do {
            menu();
            op = leerInt("Elige opción: ");
            try {
                switch (op) {
                    case 1 -> menuClientes();
                    case 2 -> menuArticulos();
                    case 3 -> menuPedidos();
                    case 4 -> listarTodo();
                    case 0 -> System.out.println("Saliendo... ¡Gracias por usar Online Store!");
                    default -> System.out.println("Opción no válida");
                }
            } catch (NegocioException | IllegalArgumentException e) {
                System.out.println("[ERROR] " + e.getMessage());
            }
        } while (op != 0);
    }

    private void menu() {
        System.out.println("\n=== Online Store ===");
        System.out.println("1) Gestionar clientes");
        System.out.println("2) Gestionar artículos");
        System.out.println("3) Crear pedido (1 artículo)");
        System.out.println("4) Listados");
        System.out.println("0) Salir");
    }

    private void menuClientes() {
        System.out.println("\n-- Clientes --");
        System.out.println("1) Alta estándar");
        System.out.println("2) Alta premium");
        System.out.println("3) Baja");
        System.out.println("4) Listar");
        int op = leerInt("Opción: ");
        switch (op) {
            case 1 -> {
                String nif = leer("NIF: ");
                ctrl.altaClienteEstandar(nif, leer("Nombre: "), leer("Domicilio: "), leer("Email: "));
                System.out.println("Cliente estándar dado de alta.");
            }
            case 2 -> {
                String nif = leer("NIF: ");
                ctrl.altaClientePremium(nif, leer("Nombre: "), leer("Domicilio: "), leer("Email: "));
                System.out.println("Cliente premium dado de alta.");
            }
            case 3 -> { ctrl.bajaCliente(leer("NIF: ")); System.out.println("Cliente eliminado."); }
            case 4 -> ctrl.listarClientes().forEach(System.out::println);
            default -> System.out.println("Opción no válida");
        }
    }

    private void menuArticulos() {
        System.out.println("\n-- Artículos --");
        System.out.println("1) Alta");
        System.out.println("2) Listar");
        int op = leerInt("Opción: ");
        switch (op) {
            case 1 -> {
                ctrl.altaArticulo(leer("Código: "), leer("Descripción: "),
                        leerDouble("Precio venta: "), leerDouble("Gastos envío: "), leerInt("Tiempo preparación: "));
                System.out.println("Artículo dado de alta.");
            }
            case 2 -> ctrl.listarArticulos().forEach(System.out::println);
            default -> System.out.println("Opción no válida");
        }
    }

    private void menuPedidos() {
        System.out.println("\n-- Crear pedido (1 artículo) --");
        String nif = leer("NIF cliente: ");
        String cod = leer("Código artículo: ");
        int u = leerInt("Cantidad: ");
        double total = ctrl.crearPedido(nif, cod, u);
        System.out.printf("Pedido creado. Total final: %.2f €%n", total);
    }

    private void listarTodo() {
        System.out.println("-- Clientes --");
        ctrl.listarClientes().forEach(System.out::println);
        System.out.println("-- Artículos --");
        ctrl.listarArticulos().forEach(System.out::println);
        System.out.println("-- Pedidos --");
        ctrl.listarPedidos().forEach(System.out::println);
    }

    // Utils lectura
    private String leer(String msg) { System.out.print(msg); return sc.nextLine().trim(); }
    private int leerInt(String msg) {
        while (true) { try { return Integer.parseInt(leer(msg)); } catch (NumberFormatException e) { System.out.println("Introduce un entero."); } }
    }
    private double leerDouble(String msg) {
        while (true) { try { return Double.parseDouble(leer(msg)); } catch (NumberFormatException e) { System.out.println("Introduce un decimal."); } }
    }
}

