// Importamos las librerías y las clases de modelo, repositorio y controladores
import controller.ArticuloControlador;
import controller.ClienteControlador;
import controller.PedidoControlador;
import dao.ArticuloRepositorio;
import dao.ClienteRepositorio;
import dao.PedidoRepositorio;
import view.ArticuloVista;
import view.ClienteVista;
import view.PedidoVista;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        // Repositorios
        PedidoRepositorio pedidoRepo = new PedidoRepositorio();
        ClienteRepositorio clienteRepo = new ClienteRepositorio();
        ArticuloRepositorio articuloRepo = new ArticuloRepositorio();
        // Cargar datos de prueba
        pedidoRepo.cargarDatosPrueba();
        clienteRepo.cargarDatosPrueba();
        articuloRepo.cargarDatosPrueba();
        // Controladores
        PedidoControlador pedidoCtrl = new PedidoControlador(pedidoRepo);
        ClienteControlador clienteCtrl = new ClienteControlador(clienteRepo);
        ArticuloControlador articuloCtrl = new ArticuloControlador(articuloRepo);
        // Vistas
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
            opcionPrincipal = sc.nextInt();
            sc.nextLine();

            switch (opcionPrincipal) {
                case 1 -> articuloVista.iniciar(sc);
                case 2 -> clienteVista.iniciar(sc);
                case 3 -> pedidoVista.iniciar(sc);
                case 0 -> System.out.println("Saliendo del programa...");
                default -> System.out.println("Opción incorrecta.");
            }
        } while (opcionPrincipal != 0);

        sc.close();
    }
}
