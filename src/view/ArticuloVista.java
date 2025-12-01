//View: Es la interfaz que el usuario ve.
// Obtiene datos del Modelo para mostrar y se encarga solo de mostrar la información y recibir interacciones del usuario que envía al Controlador.
// No contiene lógica de negocio ni acceso a datos.
package view;

import controller.ArticuloControlador;
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

            if (sc.hasNextLine()) {
                opcion = sc.nextInt();
                sc.nextLine(); // Consumimos el salto de línea
            } else {
                System.out.println("Entrada no válida. Introduce un número.");
                sc.nextLine();
                opcion = -1;
                continue;
            }
            switch (opcion) {
                case 1: // Añadir artículo
                    try {
                        String siguienteCodigo = articuloCtrl.obtenerSiguienteCodigo();
                        System.out.print("Código [" + siguienteCodigo + "]:");
                        String cod = sc.nextLine();
                        if (cod.isBlank())
                            cod = siguienteCodigo;
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
                    break;
                case 2: // Mostrar todos los artículos
                    System.out.println("\n=== LISTADO DE ARTÍCULOS ===");
                    articuloCtrl.listarArticulos().forEach(System.out::println);
                    break;
                case 0:
                    System.out.println("Volviendo al menú principal...");
                    break;
                default:
                    System.out.println("Opción incorrecta.");
                    break;
            }
        } while (opcion != 0);
    }
}