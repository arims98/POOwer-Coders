package controller;

import dao.DAOFactory;
import dao.MySQLDAOFactory;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.stage.Stage;

public class VentanaController {

    private ArticuloControlador articuloCtrl;
    private ClienteControlador clienteCtrl;
    private PedidoControlador pedidoCtrl;

    public VentanaController() {
        // Inicialización del backend
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("OnlineStorePU");
        EntityManager em = emf.createEntityManager();

        DAOFactory factory = new MySQLDAOFactory(em);

        pedidoCtrl = new PedidoControlador(factory.getPedidoRepositorio());
        clienteCtrl = new ClienteControlador(factory.getClienteRepositorio());
        articuloCtrl = new ArticuloControlador(factory.getArticuloRepositorio());
    }

    // Botones para abrir ventanas de módulos
    @FXML
    public void abrirVentanaArticulo() {
        abrirVentana("/META-INF/ArticuloView.fxml", "Gestión de Artículos");
    }

    @FXML
    public void abrirVentanaCliente() {
        abrirVentana("/META-INF/ClienteView.fxml", "Gestión de Clientes");
    }

    @FXML
    public void abrirVentanaPedido() {
        abrirVentana("/META-INF/PedidoView.fxml", "Gestión de Pedidos");
    }

    private void abrirVentana(String rutaFXML, String titulo) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(rutaFXML));
            Scene scene = new Scene(loader.load());

            // Vincular controladores
            Object controller = loader.getController();

            if (controller instanceof ArticuloControllerFX ac) {
                ac.setArticuloControlador(articuloCtrl);
            }
            if (controller instanceof ClienteControllerFX cc) {
                cc.setClienteControlador(clienteCtrl);
            }
            if (controller instanceof PedidoControllerFX pc) {
                pc.setControladores(pedidoCtrl, clienteCtrl, articuloCtrl);
            }

            Stage stage = new Stage();
            stage.setTitle(titulo);
            stage.setScene(scene);
            stage.show();

        } catch (Exception e) {
            e.printStackTrace();
            mostrar("Error cargando ventana: " + e.getMessage());
        }
    }

    @FXML
    public void salir() {
        System.exit(0);
    }

    private void mostrar(String msg) {
        Alert a = new Alert(Alert.AlertType.INFORMATION, msg);
        a.show();
    }
}
