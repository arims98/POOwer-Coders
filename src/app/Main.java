package app;

import controller.ArticuloControlador;
import controller.ClienteControlador;
import controller.PedidoControlador;
import dao.DAOFactory;
import dao.Repositorio;
import dao.ArticuloRepositorio;
import dao.MySQLDAOFactory;
import view.ArticuloVista;
import view.ClienteVista;
import view.PedidoVista;
import java.util.Scanner;


public class Main {

    public static void main(String[] args) {
        System.out.println("Iniciando aplicación con persistencia MySQL/JDBC y patrón Factory...");

        // ====================================================================
        // 1. Inicialización de la Fábrica DAO (DAOFactory)
        // Se instancia la fábrica específica de MySQL (MySQLDAOFactory)
        // ====================================================================
        DAOFactory factory = new MySQLDAOFactory();

        // 2. Obtener Repositorios (DAOs) a través de la fábrica
        // El Controlador usa la interfaz 'Repositorio', desacoplándose de la implementación MySQL
        Repositorio<?> pedidoRepo = factory.getPedidoRepositorio();
        Repositorio<?> clienteRepo = factory.getClienteRepositorio();
        Repositorio<?> articuloRepo = factory.getArticuloRepositorio();

        // Carga de datos de prueba iniciales (Artículos y Clientes)
        // Se hace un casting para acceder al método específico que llama al Procedimiento Almacenado
        if (articuloRepo instanceof ArticuloRepositorio) {
            System.out.println("Cargando datos iniciales (Clientes y Artículos) mediante SP...");
            ((ArticuloRepositorio) articuloRepo).cargarDatosPrueba();
        }


        // 3. Controladores (Reciben las instancias de los Repositorios del Factory)
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
            System.out.println("\n=== MENÚ PRINCIPAL ===");
            System.out.println("1. Gestión de Artículos");
            System.out.println("2. Gestión de Clientes");
            System.out.println("3. Gestión de Pedidos");
            System.out.println("0. Salir");
            System.out.print("Elige una opción: ");
            
            if (sc.hasNextInt()) {
                opcionPrincipal = sc.nextInt();
                sc.nextLine(); // Consumir el salto de línea
            } else {
                System.out.println("⚠️ Entrada no válida. Introduce un número.");
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
        System.out.println("¡Programa finalizado!");
    }
}