package app;

import controller.ArticuloControlador;
import controller.ClienteControlador;
import controller.PedidoControlador;
import dao.DAOFactory;
import dao.MySQLDAOFactory;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import view.ArticuloVista;
import view.ClienteVista;
import view.PedidoVista;

import java.util.Scanner;

public class Main {

    public static void main(String[] args) {

        // Crear UN SOLO EntityManagerFactory en toda la aplicación
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("OnlineStorePU");

        // Crear EntityManager (se puede reusar)
        EntityManager em = emf.createEntityManager();

        // Pasar el EntityManager a la fábrica
        DAOFactory factory = new MySQLDAOFactory(em);

        // Controladores
        PedidoControlador pedidoCtrl = new PedidoControlador(factory.getPedidoRepositorio());
        ClienteControlador clienteCtrl = new ClienteControlador(factory.getClienteRepositorio());
        ArticuloControlador articuloCtrl = new ArticuloControlador(factory.getArticuloRepositorio());

        // Vistas
        ArticuloVista articuloVista = new ArticuloVista(articuloCtrl);
        ClienteVista clienteVista = new ClienteVista(clienteCtrl);
        PedidoVista pedidoVista = new PedidoVista(pedidoCtrl, clienteCtrl, articuloCtrl);

        Scanner sc = new Scanner(System.in);
        int opcionPrincipal;

        // Menú principal
        do {
            System.out.println("\n=== ONLINE STORE ===");
            System.out.println("1. Gestión de Artículos");
            System.out.println("2. Gestión de Clientes");
            System.out.println("3. Gestión de Pedidos");
            System.out.println("0. Salir");
            System.out.print("Elige una opción: ");

            if (sc.hasNextInt()) {
                opcionPrincipal = sc.nextInt();
                sc.nextLine();
            } else {
                System.out.println("Entrada no válida.");
                sc.nextLine();
                opcionPrincipal = -1;
                continue;
            }

            switch (opcionPrincipal) {
                case 1:
                    articuloVista.iniciar(sc);
                    break;
                case 2:
                    clienteVista.iniciar(sc);
                    break;
                case 3:
                    pedidoVista.iniciar(sc);
                    break;
                case 0:
                    System.out.println("Saliendo...");
                    break;
                default:
                    System.out.println("Opción incorrecta.");
                    break;
            }
        } while (opcionPrincipal != 0);

        sc.close();
        em.close();
        emf.close();

        System.out.println("¡Programa finalizado!");
    }
}
