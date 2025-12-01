package app;

import controller.ArticuloControlador;
import controller.ClienteControlador;
import controller.PedidoControlador;
import dao.DAOFactory;
import dao.JPADAOFactory;
import util.EntityManagerUtil;
import view.ArticuloVista;
import view.ClienteVista;
import view.PedidoVista;
import java.util.Scanner;


public class Main {

    public static void main(String[] args) {
        
        // Info Tecnología
        System.out.println("╔════════════════════════════════════════════════════════════╗");
        System.out.println("║           ONLINE STORE - Gestión de Tienda Online         ║");
        System.out.println("╠════════════════════════════════════════════════════════════╣");
        System.out.println("║  Tecnologías:                                              ║");
        System.out.println("║    - Persistencia: JPA (Java Persistence API) 3.1         ║");
        System.out.println("║    - ORM: Hibernate 6.4.4 Final                           ║");
        System.out.println("║    - Base de Datos: MySQL 8.0 (online_store_bd)           ║");
        System.out.println("║    - Patrón de Diseño: MVC (Modelo-Vista-Controlador)     ║");
        System.out.println("╚════════════════════════════════════════════════════════════╝");
        System.out.println();
        
        System.out.println(">> Inicializando conexión con base de datos...");
        
        // Usar JPADAOFactory para persistencia con JPA/Hibernate
        DAOFactory factory = new JPADAOFactory();

        System.out.println(">> Hibernate + JPA configurado correctamente");
        System.out.println(">> Conexión establecida con MySQL (online_store_bd)");
        System.out.println();

        // Controladores (Reciben las instancias de los Repositorios del Factory)
        PedidoControlador pedidoCtrl = new PedidoControlador(factory.getPedidoRepositorio());
        ClienteControlador clienteCtrl = new ClienteControlador(factory.getClienteRepositorio());
        ArticuloControlador articuloCtrl = new ArticuloControlador(factory.getArticuloRepositorio());
        
        // 4. Vistas
        ArticuloVista articuloVista = new ArticuloVista(articuloCtrl);
        ClienteVista clienteVista = new ClienteVista(clienteCtrl);
        PedidoVista pedidoVista = new PedidoVista(pedidoCtrl, clienteCtrl, articuloCtrl);

        Scanner sc = new Scanner(System.in);
        int opcionPrincipal;
        
        // MENÚ PRINCIPAL
        do {
            System.out.println("\n=== ONLINE STORE ===");
            System.out.println("1. Gestión de Artículos");
            System.out.println("2. Gestión de Clientes");
            System.out.println("3. Gestión de Pedidos");
            System.out.println("0. Salir");
            System.out.print("Elige una opción: ");
            
            if (sc.hasNextInt()) {
                opcionPrincipal = sc.nextInt();
                sc.nextLine(); // Consumir el salto de línea
            } else {
                System.out.println("Entrada no válida. Introduce un número.");
                sc.nextLine(); // Consumir la entrada no válida
                opcionPrincipal = -1; // Opción que fuerza la repetición
                continue;
            }

            switch (opcionPrincipal) {
                case 1 -> articuloVista.iniciar(sc);
                case 2 -> clienteVista.iniciar(sc);
                case 3 -> pedidoVista.iniciar(sc);
                case 0 -> System.out.println("Saliendo del programa...");
                default -> System.out.println("Opción incorrecta.");
            }
        } while (opcionPrincipal != 0);

        sc.close();
        
        System.out.println();
        System.out.println(">> Cerrando conexiones...");
        
        // Se cierra el EntityManagerFactory al finalizar la aplicación
        EntityManagerUtil.closeEntityManagerFactory();
        
        System.out.println(">> Conexión con base de datos cerrada");
        System.out.println(">> EntityManagerFactory de Hibernate cerrado");
        System.out.println();
        System.out.println("╔════════════════════════════════════════════════════════════╗");
        System.out.println("║           ¡Programa finalizado correctamente!             ║");
        System.out.println("║     Gracias por usar Online Store con JPA/Hibernate       ║");
        System.out.println("╚════════════════════════════════════════════════════════════╝");
    }
}
