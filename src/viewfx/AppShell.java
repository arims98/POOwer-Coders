package viewfx;

import controller.ArticuloControlador;
import controller.ClienteControlador;
import controller.PedidoControlador;
import javafx.scene.layout.StackPane;

public class AppShell extends StackPane {

    private final HomeView home;
    private final MainView main;

    public AppShell(ArticuloControlador articuloCtrl,
                    ClienteControlador clienteCtrl,
                    PedidoControlador pedidoCtrl) {

        home = new HomeView(
                () -> showMain(0),
                () -> showMain(1),
                () -> showMain(2)
        );

        main = new MainView(articuloCtrl, clienteCtrl, pedidoCtrl, this::showHome);

        getChildren().addAll(home, main);
        showHome();
    }

    private void showHome() {
        home.setVisible(true);
        home.toFront();
        main.setVisible(false);
    }

    private void showMain(int tabIndex) {
        main.setVisible(true);
        main.toFront();
        home.setVisible(false);
        main.selectTab(tabIndex);
    }
}
