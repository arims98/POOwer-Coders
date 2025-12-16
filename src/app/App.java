package app;

// Importaciones de JavaFX
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

// Importaciones existentes (Controladores, DAO, JPA)
import controller.ArticuloControlador;
import controller.ClienteControlador;
import controller.PedidoControlador;
import dao.DAOFactory;
import dao.MySQLDAOFactory;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import controller.MainController;

public class App extends Application { // Extiende Application

    // Referencias a los componentes esenciales para que sean accesibles
    private EntityManagerFactory emf;
    private EntityManager em;
    private DAOFactory factory;

    private PedidoControlador pedidoCtrl;
    private ClienteControlador clienteCtrl;
    private ArticuloControlador articuloCtrl;
    
    // --- NUEVO MÉTODO: Inicialización de la Lógica de Negocio (Modelo/DAO) ---
    private void inicializarComponentes() {
        // La lógica de JPA/DAO sigue siendo la misma
        if (emf == null) {
            System.out.println("Inicializando JPA y DAOs...");
            this.emf = Persistence.createEntityManagerFactory("OnlineStorePU");
            this.em = emf.createEntityManager();
            this.factory = new MySQLDAOFactory(em);
            
            // Inicialización de Controladores (Controladores de Lógica de Negocio)
            this.pedidoCtrl = new PedidoControlador(factory.getPedidoRepositorio());
            this.clienteCtrl = new ClienteControlador(factory.getClienteRepositorio());
            this.articuloCtrl = new ArticuloControlador(factory.getArticuloRepositorio());
        }
    }
    
    // --- MÉTODO CLAVE DE JAVAFX: Arranque de la GUI ---
    @Override
    public void start(Stage primaryStage) throws Exception {
        
        // 1. Inicializar la lógica de negocio (solo una vez)
        inicializarComponentes();
        
        // 2. Cargar el diseño FXML de la Vista Principal
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/MainView.fxml"));
        Parent root = loader.load();
        
        // 3. Obtener el Controlador de la Vista y pasarle las dependencias
        // Este es el punto CRÍTICO donde se ENLAZAN los componentes.
        Object controllerInstance = loader.getController();
        if (controllerInstance instanceof MainController) {
             MainController mainController = (MainController) controllerInstance;
             mainController.setControladores(articuloCtrl, clienteCtrl, pedidoCtrl);
        } else {
             System.err.println("Error: El controlador cargado no es MainController.");
        }

        // 4. Configurar y mostrar la ventana
        primaryStage.setTitle("Online Store - JavaFX");
        primaryStage.setScene(new Scene(root));
        primaryStage.show();
    }
    
    // --- MÉTODO CLAVE DE JAVAFX: Detención y Limpieza ---
    @Override
    public void stop() {
        System.out.println("Cerrando recursos de JPA...");
        if (em != null) {
            em.close();
        }
        if (emf != null) {
            emf.close();
        }
    }

    // --- PUNTO DE ENTRADA PRINCIPAL ---
    public static void main(String[] args) {
        // CAMBIO 2: Llama al método de lanzamiento de la aplicación JavaFX
        launch(args);
    }
}