package viewfx;

import controller.ArticuloControlador;
import controller.ClienteControlador;
import controller.PedidoControlador;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;

public class MainView extends BorderPane {

    private final TabPane tabs = new TabPane();

    public MainView(ArticuloControlador articuloCtrl,
                    ClienteControlador clienteCtrl,
                    PedidoControlador pedidoCtrl,
                    Runnable onHome) {

        getStyleClass().add("home-bg");

        // Top bar
        ToolBar bar = new ToolBar();
        bar.getStyleClass().add("topbar");
        bar.getStyleClass().add("app-header");

        Label title = new Label("Gestión de Online Store");
        title.getStyleClass().add("topbar-title");

        Region spacer = new Region();
        HBox.setHgrow(spacer, Priority.ALWAYS);

        Button btnHome = new Button("Inicio");
        btnHome.getStyleClass().add("ghost-button");
        btnHome.setOnAction(e -> {
            if (onHome != null) onHome.run();
        });

        bar.getItems().addAll(title, spacer, btnHome);
        setTop(bar);

        // Tabs
        Tab tArticulos = new Tab("Artículos", new ArticuloPane(articuloCtrl));
        tArticulos.setClosable(false);

        Tab tClientes = new Tab("Clientes", new ClientePane(clienteCtrl));
        tClientes.setClosable(false);

        Tab tPedidos = new Tab("Pedidos", new PedidoPane(pedidoCtrl, clienteCtrl, articuloCtrl));
        tPedidos.setClosable(false);

        tabs.getTabs().addAll(tArticulos, tClientes, tPedidos);
        tabs.getStyleClass().add("app-tabs");

        VBox content = new VBox(tabs);
        content.getStyleClass().add("content-surface");
        content.setPadding(new Insets(16));
        content.setMaxWidth(1200);

        BorderPane.setMargin(content, new Insets(18, 18, 18, 18));
        setCenter(content);
    }

    public void selectTab(int index) {
        tabs.getSelectionModel().select(index);
    }
}