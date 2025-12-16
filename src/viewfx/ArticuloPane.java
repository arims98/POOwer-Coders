package viewfx;

import controller.ArticuloControlador;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.*;
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

        // --- Inputs ---
        tfCodigo.setPromptText("Código (opcional)");
        tfDesc.setPromptText("Descripción");
        tfPrecio.setPromptText("Precio");
        tfEnvio.setPromptText("Gastos envío");
        tfTiempo.setPromptText("Tiempo prep (min)");

        // Que la descripción ocupe más, y el resto se adapten
        HBox rowInputs = new HBox(10,
                new Label("Nuevo:"),
                tfCodigo, tfDesc, tfPrecio, tfEnvio, tfTiempo
        );
        rowInputs.setAlignment(javafx.geometry.Pos.CENTER_LEFT);

        HBox.setHgrow(tfDesc, Priority.ALWAYS);
        tfDesc.setMinWidth(260);

        tfCodigo.setPrefWidth(150);
        tfPrecio.setPrefWidth(120);
        tfEnvio.setPrefWidth(120);
        tfTiempo.setPrefWidth(150);

        // --- Botones (fila aparte) ---
        Button btnAdd = new Button("Añadir artículo");
        btnAdd.getStyleClass().add("primary-button");
        btnAdd.setOnAction(e -> onAdd());

        Button btnDel = new Button("Eliminar artículo");
        btnDel.getStyleClass().add("ghost-button");
        btnDel.setOnAction(e -> onDelete());

        Button btnClear = new Button("Limpiar");
        btnClear.getStyleClass().add("ghost-button");
        btnClear.setOnAction(e -> limpiarFormulario());

        Button btnRefresh = new Button("Refrescar");
        btnRefresh.getStyleClass().add("ghost-button");
        btnRefresh.setOnAction(e -> refrescar());

        HBox rowButtons = new HBox(10, btnAdd, btnDel, btnClear, btnRefresh);
        rowButtons.setAlignment(javafx.geometry.Pos.CENTER_LEFT);

        // --- Tabla ---
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

        // Selección rellena formulario (para borrar rápido)
        table.getSelectionModel().selectedItemProperty().addListener((obs, old, sel) -> {
            if (sel != null) {
                tfCodigo.setText(sel.getCodigo());
                tfDesc.setText(sel.getDescripcion());
                tfPrecio.setText(String.valueOf(sel.getPrecioVenta()));
                tfEnvio.setText(String.valueOf(sel.getGastosEnvio()));
                tfTiempo.setText(String.valueOf(sel.getTiempoPreparacion()));
            }
        });

        getChildren().addAll(rowInputs, rowButtons, table);

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

            FxMsg.info("OK", "Artículo añadido correctamente.");
            limpiarFormulario();
            refrescar();

        } catch (NumberFormatException ex) {
            FxMsg.error("Formato incorrecto", "Precio/Envío deben ser número y Tiempo un entero.");
        } catch (Exception ex) {
            FxMsg.error("Error", ex.getMessage());
        }
    }

    private void onDelete() {
        try {
            Articulo sel = table.getSelectionModel().getSelectedItem();
            String codigo = (sel != null) ? sel.getCodigo() : tfCodigo.getText().trim();

            if (codigo.isEmpty()) {
                FxMsg.error("Falta código", "Selecciona un artículo o escribe su código para eliminar.");
                return;
            }

            if (!FxMsg.confirm("Confirmar", "¿Seguro que quieres eliminar el artículo " + codigo + "?")) {
                return;
            }

            articuloCtrl.eliminarArticulo(codigo);

            FxMsg.info("OK", "Artículo eliminado correctamente.");
            limpiarFormulario();
            refrescar();

        } catch (Exception ex) {
            FxMsg.error("Error", ex.getMessage());
        }
    }

    private void limpiarFormulario() {
        tfCodigo.clear();
        tfDesc.clear();
        tfPrecio.clear();
        tfEnvio.clear();
        tfTiempo.clear();
        table.getSelectionModel().clearSelection();
    }

    private void refrescar() {
        data.setAll(articuloCtrl.listarArticulos());
    }
}