package app;

import controller.ArticuloControlador;
import controller.ClienteControlador;
import controller.PedidoControlador;
import dao.DAOFactory;
import dao.MySQLDAOFactory;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import viewfx.AppShell;

public class MainFX extends Application {

    private EntityManagerFactory emf;
    private EntityManager em;

    @Override
    public void start(Stage stage) {

        emf = Persistence.createEntityManagerFactory("OnlineStorePU");
        em = emf.createEntityManager();

        DAOFactory factory = new MySQLDAOFactory(em);

        PedidoControlador pedidoCtrl = new PedidoControlador(factory.getPedidoRepositorio());
        ClienteControlador clienteCtrl = new ClienteControlador(factory.getClienteRepositorio());
        ArticuloControlador articuloCtrl = new ArticuloControlador(factory.getArticuloRepositorio());

        AppShell root = new AppShell(articuloCtrl, clienteCtrl, pedidoCtrl);

        Scene scene = new Scene(root, 1200, 720);
        scene.getStylesheets().add(getClass().getResource("/styles.css").toExternalForm());

        stage.setTitle("POOwer Coders - Online Store");
        stage.setScene(scene);
        stage.show();
    }

    @Override
    public void stop() {
        if (em != null && em.isOpen()) em.close();
        if (emf != null && emf.isOpen()) emf.close();
    }

    public static void main(String[] args) {
        launch(args);
    }
}