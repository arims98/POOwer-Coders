package viewfx;

import controller.ArticuloControlador;
import controller.ClienteControlador;
import controller.PedidoControlador;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.layout.BorderPane;

public class MainView extends BorderPane {

    private final ArticuloControlador articuloCtrl;
    private final ClienteControlador clienteCtrl;
    private final PedidoControlador pedidoCtrl;

    public MainView(ArticuloControlador articuloCtrl,
                    ClienteControlador clienteCtrl,
                    PedidoControlador pedidoCtrl) {

        this.articuloCtrl = articuloCtrl;
        this.clienteCtrl = clienteCtrl;
        this.pedidoCtrl = pedidoCtrl;

        TabPane tabs = new TabPane();

        Tab tArticulos = new Tab("Artículos", new ArticuloPane(articuloCtrl));
        tArticulos.setClosable(false);

        Tab tClientes = new Tab("Clientes", new ClientePane(clienteCtrl));
        tClientes.setClosable(false);

        Tab tPedidos = new Tab("Pedidos", new PedidoPane(pedidoCtrl, clienteCtrl, articuloCtrl));
        tPedidos.setClosable(false);

        tabs.getTabs().addAll(tArticulos, tClientes, tPedidos);
        setCenter(tabs);
    }
}
