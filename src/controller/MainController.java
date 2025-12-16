package controller;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.application.Platform; // Para cerrar la aplicación
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * Controlador de la Vista Principal (MainView.fxml).
 * Su responsabilidad es manejar los movimientos de la GUI (clicks, etc.)
 * y coordinar las llamadas a los controladores de la lógica de negocio.
 */
public class MainController {

    // --- Componentes de la Vista (Inyección desde FXML) ---
    // Usaremos @FXML para enlazar estos objetos con los elementos definidos en MainView.fxml
    @FXML
    private Label welcomeLabel;

    // Puedes inyectar los botones para habilitarlos o deshabilitarlos, si es necesario.
    @FXML
    private Button btnArticulos;
    
    @FXML
    private Button btnClientes;
    
    @FXML
    private Button btnPedidos;


    // --- Dependencias del Modelo/Negocio (Inyección desde App.java) ---
    // Estas son las referencias a tus controladores de negocio existentes.
    private ArticuloControlador articuloCtrl;
    private ClienteControlador clienteCtrl;
    private PedidoControlador pedidoCtrl;

    
    /**
     * Método público usado por app.App para inyectar los controladores de negocio.
     */
    public void setControladores(ArticuloControlador articuloCtrl, 
                                 ClienteControlador clienteCtrl, 
                                 PedidoControlador pedidoCtrl) {
        this.articuloCtrl = articuloCtrl;
        this.clienteCtrl = clienteCtrl;
        this.pedidoCtrl = pedidoCtrl;
        
        // Opcional: cargar el nombre de la tienda o un saludo.
        welcomeLabel.setText("Bienvenidos");
    }
    
    
    /**
     * Método que maneja el clic del botón "Gestión de Artículos".
     */
    @FXML
    private void handleGestionArticulos() {
        System.out.println("-> Iniciando Gestión de Artículos...");
        try {
            // Cargar la vista de artículos
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/ArticuloView.fxml"));
            Parent root = loader.load();

            // Obtener el controlador y pasarle las dependencias
            ArticuloController articuloController = loader.getController();
            articuloController.setArticuloControlador(articuloCtrl);

            // Crear nueva escena
            Scene scene = new Scene(root);
            Stage stage = new Stage();
            stage.setTitle("Gestión de Artículos");
            stage.setScene(scene);
            articuloController.setStage(stage);
            stage.show();
        } catch (Exception e) {
            System.err.println("Error al cargar la vista de artículos: " + e.getMessage());
        }
    }

    /**
     * Método que maneja el clic del botón "Gestión de Clientes".
     */
    @FXML
    private void handleGestionClientes() {
        System.out.println("-> Iniciando Gestión de Clientes...");
        try {
            // Cargar la vista de clientes
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/ClienteView.fxml"));
            Parent root = loader.load();

            // Obtener el controlador y pasarle las dependencias
            ClienteController clienteController = loader.getController();
            clienteController.setClienteControlador(clienteCtrl);

            // Crear nueva escena
            Scene scene = new Scene(root);
            Stage stage = new Stage();
            stage.setTitle("Gestión de Clientes");
            stage.setScene(scene);
            clienteController.setStage(stage);
            stage.show();
        } catch (Exception e) {
            System.err.println("Error al cargar la vista de clientes: " + e.getMessage());
        }
    }

    /**
     * Método que maneja el clic del botón "Gestión de Pedidos".
     */
    @FXML
    private void handleGestionPedidos() {
        System.out.println("-> Iniciando Gestión de Pedidos...");
        try {
            // Cargar la vista de pedidos
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/PedidoView.fxml"));
            Parent root = loader.load();

            // Obtener el controlador y pasarle las dependencias
            PedidoController pedidoController = loader.getController();
            pedidoController.setPedidoControlador(pedidoCtrl);
            pedidoController.setArticuloControlador(articuloCtrl);
            pedidoController.setClienteControlador(clienteCtrl);

            // Crear nueva escena
            Scene scene = new Scene(root);
            Stage stage = new Stage();
            stage.setTitle("Gestión de Pedidos");
            stage.setScene(scene);
            pedidoController.setStage(stage);
            stage.show();
        } catch (Exception e) {
            System.err.println("Error al cargar la vista de pedidos: " + e.getMessage());
        }
    }
    
    /**
     * Método que maneja el clic del botón "Salir".
     */
    @FXML
    private void handleExit() {
        // Cierra la aplicación JavaFX de forma segura.
        Platform.exit();
    }
    
    // Método opcional llamado inmediatamente después de que se cargan todos los elementos FXML
    public void initialize() {
        
    }
}