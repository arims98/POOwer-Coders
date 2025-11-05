//View: Es la interfaz que el usuario ve.
// Obtiene datos del Modelo para mostrar y se encarga solo de mostrar la información y recibir interacciones del usuario que envía al Controlador.
// No contiene lógica de negocio ni acceso a datos.
package view;

import controller.ArticuloControlador;
import model.Articulo;

import java.util.Scanner;

public class ArticuloVista {
    private final ArticuloControlador articuloCtrl;

    // Constructor recibe el controlador
    public ArticuloVista(ArticuloControlador articuloCtrl) {
        this.articuloCtrl = articuloCtrl;
    }

    public void iniciar(Scanner sc) {
        int opcion;
        do {
            System.out.println("\n=== GESTIÓN DE ARTÍCULOS ===");
            System.out.println("1. Añadir artículo");
            System.out.println("2. Mostrar artículos");
            System.out.println("0. Volver al menú principal");
            System.out.print("Elige una opción: ");
            opcion = sc.nextInt();
            sc.nextLine();

            switch (opcion) {
                case 1 -> { // Añadir artículo
                    try {
                        System.out.print("Código: ");
                        String cod = sc.nextLine();
                        System.out.print("Descripción: ");
                        String desc = sc.nextLine();
                        System.out.print("Precio venta: ");
                        double precio = Double.parseDouble(sc.nextLine());
                        System.out.print("Gastos envío: ");
                        double envio = Double.parseDouble(sc.nextLine());
                        System.out.print("Tiempo preparación (minutos): ");
                        int tiempo = Integer.parseInt(sc.nextLine());
                        articuloCtrl.agregarArticulo(cod, desc, precio, envio, tiempo);
                        System.out.println("Artículo añadido correctamente.");
                    } catch (Exception e) {
                        System.out.println("⚠️ Error al añadir artículo: " + e.getMessage());
                    }
                }
                case 2 -> { // Mostrar todos los artículos
                    System.out.println("\n=== LISTADO DE ARTÍCULOS ===");
                    articuloCtrl.listarArticulos().forEach(System.out::println);
                }
                case 0 -> System.out.println("Volviendo al menú principal...");
                default -> System.out.println("Opción incorrecta.");
            }
        } while (opcion != 0);
    }
}
