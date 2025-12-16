package viewfx;

import controller.ArticuloControlador;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import model.Articulo;

public class ArticuloPane extends VBox {

    private final ArticuloControlador articuloCtrl;

    private final ObservableList<Articulo> data = FXCollections.observableArrayList();
    private final TableView<Articulo> table = new TableView<>(data);

    private final TextField tfCodigo = new TextField();
    private final TextField tfDesc = new TextField();
    private final TextField tfPrecio = new TextField();
    private final TextField tfEnvio = new TextField();
    private final TextField tfTiempo = new TextField();

    public ArticuloPane(ArticuloControlador articuloCtrl) {
        this.articuloCtrl = articuloCtrl;

        setSpacing(10);
        setPadding(new Insets(12));

        table.setPlaceholder(new Label("No hay artículos para mostrar"));

        // ---- Formulario ----
        tfCodigo.setPromptText("Código (opcional)");
        tfDesc.setPromptText("Descripción");
        tfPrecio.setPromptText("Precio");
        tfEnvio.setPromptText("Gastos envío");
        tfTiempo.setPromptText("Tiempo prep (min)");

        Button btnAdd = new Button("Añadir artículo");
        btnAdd.setOnAction(e -> onAdd());

        HBox form = new HBox(8,
                new Label("Nuevo:"),
                tfCodigo, tfDesc, tfPrecio, tfEnvio, tfTiempo,
                btnAdd
        );

        // ---- Tabla ----
        TableColumn<Articulo, String> cCod = new TableColumn<>("Código");
        cCod.setCellValueFactory(new PropertyValueFactory<>("codigo"));

        TableColumn<Articulo, String> cDesc = new TableColumn<>("Descripción");
        cDesc.setCellValueFactory(new PropertyValueFactory<>("descripcion"));

        TableColumn<Articulo, Double> cPrecio = new TableColumn<>("Precio");
        cPrecio.setCellValueFactory(new PropertyValueFactory<>("precioVenta"));
        cPrecio.setCellFactory(col -> new TableCell<>() {
            @Override
            protected void updateItem(Double item, boolean empty) {
                super.updateItem(item, empty);
                setText(empty || item == null ? "" : String.format("%.2f", item));
            }
        });

        TableColumn<Articulo, Double> cEnvio = new TableColumn<>("Envío");
        cEnvio.setCellValueFactory(new PropertyValueFactory<>("gastosEnvio"));
        cEnvio.setCellFactory(col -> new TableCell<>() {
            @Override
            protected void updateItem(Double item, boolean empty) {
                super.updateItem(item, empty);
                setText(empty || item == null ? "" : String.format("%.2f", item));
            }
        });

        TableColumn<Articulo, Integer> cTiempo = new TableColumn<>("Prep (min)");
        cTiempo.setCellValueFactory(new PropertyValueFactory<>("tiempoPreparacion"));

        table.getColumns().setAll(cCod, cDesc, cPrecio, cEnvio, cTiempo);
        table.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY_FLEX_LAST_COLUMN);

        Button btnRefresh = new Button("Refrescar");
        btnRefresh.setOnAction(e -> refrescar());

        getChildren().addAll(form, btnRefresh, table);

        refrescar();
    }

    private void onAdd() {
        try {
            String codigo = tfCodigo.getText().trim();
            String desc = tfDesc.getText().trim();

            double precio = Double.parseDouble(tfPrecio.getText().trim());
            double envio = Double.parseDouble(tfEnvio.getText().trim());
            int tiempo = Integer.parseInt(tfTiempo.getText().trim());

            articuloCtrl.agregarArticulo(codigo.isEmpty() ? null : codigo, desc, precio, envio, tiempo);

            tfCodigo.clear();
            tfDesc.clear();
            tfPrecio.clear();
            tfEnvio.clear();
            tfTiempo.clear();

            FxMsg.info("OK", "Artículo añadido correctamente.");
            refrescar();

        } catch (NumberFormatException ex) {
            FxMsg.error("Formato incorrecto", "Precio/Envío deben ser número y Tiempo un entero.");
        } catch (Exception ex) {
            FxMsg.error("Error", ex.getMessage());
        }
    }

    private void refrescar() {
        data.setAll(articuloCtrl.listarArticulos());
    }
}