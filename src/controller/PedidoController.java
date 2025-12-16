package controller;

import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import model.Articulo;
import model.Cliente;
import model.Pedido;

import java.time.LocalDateTime;
import java.util.List;

public class PedidoController {

    @FXML
    private TableView<Pedido> pedidosTable;
    @FXML
    private TableColumn<Pedido, String> numeroColumn;
    @FXML
    private TableColumn<Pedido, String> clienteColumn;
    @FXML
    private TableColumn<Pedido, String> articuloColumn;
    @FXML
    private TableColumn<Pedido, Integer> cantidadColumn;
    @FXML
    private TableColumn<Pedido, LocalDateTime> fechaColumn;
    @FXML
    private TableColumn<Pedido, String> estadoColumn;
    @FXML
    private TextArea outputArea;

    private PedidoControlador pedidoCtrl;
    private ArticuloControlador articuloCtrl;
    private ClienteControlador clienteCtrl;
    private Stage stage;

    public void setPedidoControlador(PedidoControlador pedidoCtrl) {
        this.pedidoCtrl = pedidoCtrl;
    }

    public void setArticuloControlador(ArticuloControlador articuloCtrl) {
        this.articuloCtrl = articuloCtrl;
    }

    public void setClienteControlador(ClienteControlador clienteCtrl) {
        this.clienteCtrl = clienteCtrl;
    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    @FXML
    private void initialize() {
        // Configurar las columnas de la tabla
        numeroColumn.setCellValueFactory(new PropertyValueFactory<>("numeroPedido"));
        clienteColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getCliente().getNombre()));
        articuloColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getArticulo().getDescripcion()));
        cantidadColumn.setCellValueFactory(new PropertyValueFactory<>("cantidad"));
        fechaColumn.setCellValueFactory(new PropertyValueFactory<>("fechaHora"));
        estadoColumn.setCellValueFactory(new PropertyValueFactory<>("estado"));
    }

    @FXML
    private void handleAddPedido() {
        // Crear un diálogo personalizado para añadir pedido
        Dialog<ButtonType> dialog = new Dialog<>();
        dialog.setTitle("Añadir Pedido");
        dialog.setHeaderText("Introduce los datos del nuevo pedido");

        // Crear campos de entrada
        ComboBox<String> clienteCombo = new ComboBox<>();
        List<Cliente> clientes = clienteCtrl.listarClientes();
        for (Cliente c : clientes) {
            clienteCombo.getItems().add(c.getNif() + " - " + c.getNombre());
        }
        clienteCombo.setPromptText("Selecciona Cliente");

        ComboBox<String> articuloCombo = new ComboBox<>();
        List<Articulo> articulos = articuloCtrl.listarArticulos();
        for (Articulo a : articulos) {
            articuloCombo.getItems().add(a.getCodigo() + " - " + a.getDescripcion());
        }
        articuloCombo.setPromptText("Selecciona Artículo");

        TextField cantidadField = new TextField();
        cantidadField.setPromptText("Cantidad");

        // Layout
        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.add(new Label("Cliente:"), 0, 0);
        grid.add(clienteCombo, 1, 0);
        grid.add(new Label("Artículo:"), 0, 1);
        grid.add(articuloCombo, 1, 1);
        grid.add(new Label("Cantidad:"), 0, 2);
        grid.add(cantidadField, 1, 2);

        dialog.getDialogPane().setContent(grid);
        dialog.getDialogPane().getButtonTypes().addAll(ButtonType.OK, ButtonType.CANCEL);

        dialog.showAndWait().ifPresent(buttonType -> {
            if (buttonType == ButtonType.OK) {
                try {
                    String clienteSel = clienteCombo.getValue();
                    String articuloSel = articuloCombo.getValue();
                    int cantidad = Integer.parseInt(cantidadField.getText());

                    if (clienteSel == null || articuloSel == null) {
                        outputArea.appendText("Selecciona cliente y artículo.\n");
                        return;
                    }

                    String nif = clienteSel.split(" - ")[0];
                    String codigo = articuloSel.split(" - ")[0];

                    Cliente cliente = clienteCtrl.listarClientes().stream().filter(c -> c.getNif().equals(nif)).findFirst().orElse(null);
                    Articulo articulo = articuloCtrl.listarArticulos().stream().filter(a -> a.getCodigo().equals(codigo)).findFirst().orElse(null);

                    if (cliente == null || articulo == null) {
                        outputArea.appendText("Cliente o artículo no encontrado.\n");
                        return;
                    }

                    // Número automático
                    String numero = pedidoCtrl.obtenerSiguienteNumeroPedido();
                    LocalDateTime fecha = LocalDateTime.now();

                    pedidoCtrl.crearPedido(numero, cliente, articulo, cantidad);
                    outputArea.appendText("Pedido añadido: " + numero + "\n");

                    // Recargar la tabla
                    handleShowPedidos();
                } catch (Exception e) {
                    outputArea.appendText("Error al añadir pedido: " + e.getMessage() + "\n");
                }
            }
        });
    }

    @FXML
    private void handleShowPedidos() {
        try {
            List<Pedido> pedidos = pedidoCtrl.listarPedidos();
            ObservableList<Pedido> observablePedidos = FXCollections.observableArrayList(pedidos);
            pedidosTable.setItems(observablePedidos);
            outputArea.appendText("Pedidos cargados: " + pedidos.size() + "\n");
        } catch (Exception e) {
            outputArea.appendText("Error al cargar pedidos: " + e.getMessage() + "\n");
        }
    }

    @FXML
    private void handleBack() {
        stage.close();
    }
}