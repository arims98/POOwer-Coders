//View: Es la interfaz que el usuario ve.
// Obtiene datos del Modelo para mostrar y se encarga solo de mostrar la información y recibir interacciones del usuario que envía al Controlador.
// No contiene lógica de negocio ni acceso a datos.
package view;

import controller.ClienteControlador;
import java.util.Scanner;

public class ClienteVista {
    private final ClienteControlador clienteCtrl;

    // Constructor recibe el controlador
    public ClienteVista(ClienteControlador clienteCtrl) {
        this.clienteCtrl = clienteCtrl;
    }

    public void iniciar(Scanner sc) {
        int opcion;
        do {
            System.out.println("\n=== GESTIÓN DE CLIENTES ===");
            System.out.println("1. Añadir cliente");
            System.out.println("2. Mostrar todos los clientes");
            System.out.println("3. Mostrar clientes Estándar");
            System.out.println("4. Mostrar clientes Premium");
            System.out.println("0. Volver al menú principal");
            System.out.print("Elige una opción: ");
            opcion = sc.nextInt();
            sc.nextLine();

            switch (opcion) {
                case 1: { // Añadir cliente
                    try {
                        System.out.print("Nombre: ");
                        String nombre = sc.nextLine();
                        System.out.print("Domicilio: ");
                        String domicilio = sc.nextLine();
                        System.out.print("NIF: ");
                        String nif = sc.nextLine();
                        System.out.print("Email: ");
                        String email = sc.nextLine();
                        System.out.print("Tipo (E = Estándar / P = Premium): ");
                        String tipo = sc.nextLine();
                        clienteCtrl.agregarCliente(nombre, domicilio, nif, email, tipo);
                        System.out.println("Cliente añadido correctamente.");
                    } catch (Exception e) {
                        System.out.println("Error: " + e.getMessage());
                    }
                    break;
                }
                case 2: { // Mostrar todos
                    System.out.println("\n=== LISTADO DE CLIENTES ===");
                    clienteCtrl.listarClientes().forEach(System.out::println);
                    break;
                }
                case 3: { // Clientes Estándar
                    System.out.println("\n=== CLIENTES ESTÁNDAR ===");
                    clienteCtrl.listarClientesEstandar().forEach(System.out::println);
                    break;
                }
                case 4: { // Clientes Premium
                    System.out.println("\n=== CLIENTES PREMIUM ===");
                    clienteCtrl.listarClientesPremium().forEach(System.out::println);
                    break;
                }
                case 0: // Volver
                    break;
                default:
                    System.out.println("Opción incorrecta.");
            }
        } while (opcion != 0);
    }
}