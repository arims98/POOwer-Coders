<<<<<<< Updated upstream
import controller.Controller;
import model.Datos;
import view.MenuPrincipal;
=======
// Importamos las librerías y las clases de modelo, repositorio y controladores
import controller.ArticuloControlador;
import controller.ClienteControlador;
import controller.PedidoControlador;
import dao.IArticuloDAO;
import dao.IClienteDAO;
import dao.IPedidoDAO;
import dao.DAOFactory;
import view.ArticuloVista;
import view.ClienteVista;
import view.PedidoVista;
import java.util.Scanner;
>>>>>>> Stashed changes

public class Main {
    public static void main(String[] args) {
<<<<<<< Updated upstream
        Datos datos = new Datos();
        Controller controller = new Controller(datos);
=======
    // Repositorios obtenidos desde la fábrica
    IPedidoDAO pedidoRepo = DAOFactory.getPedidoDAO();
    IClienteDAO clienteRepo = DAOFactory.getClienteDAO();
    IArticuloDAO articuloRepo = DAOFactory.getArticuloDAO();
        // Controladores
        PedidoControlador pedidoCtrl = new PedidoControlador(pedidoRepo);
        ClienteControlador clienteCtrl = new ClienteControlador(clienteRepo);
        ArticuloControlador articuloCtrl = new ArticuloControlador(articuloRepo);
        // Vistas
        ArticuloVista articuloVista = new ArticuloVista(articuloCtrl);
        ClienteVista clienteVista = new ClienteVista(clienteCtrl);
        PedidoVista pedidoVista = new PedidoVista(pedidoCtrl, clienteCtrl, articuloCtrl);
>>>>>>> Stashed changes

        MenuPrincipal app = new MenuPrincipal(controller);
        app.iniciar();


    }

}