package controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import model.Articulo;

import java.util.List;

public class ArticuloController {

    @FXML
    private TableView<Articulo> articulosTable;
    @FXML
    private TableColumn<Articulo, String> codigoColumn;
    @FXML
    private TableColumn<Articulo, String> descripcionColumn;
    @FXML
    private TableColumn<Articulo, Double> precioColumn;
    @FXML
    private TableColumn<Articulo, Double> envioColumn;
    @FXML
    private TableColumn<Articulo, Integer> tiempoColumn;
    @FXML
    private TextArea outputArea;

    private ArticuloControlador articuloCtrl;
    private Stage stage;

    public void setArticuloControlador(ArticuloControlador articuloCtrl) {
        this.articuloCtrl = articuloCtrl;
    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    @FXML
    private void initialize() {
        // Configurar las columnas de la tabla
        codigoColumn.setCellValueFactory(new PropertyValueFactory<>("codigo"));
        descripcionColumn.setCellValueFactory(new PropertyValueFactory<>("descripcion"));
        precioColumn.setCellValueFactory(new PropertyValueFactory<>("precioVenta"));
        envioColumn.setCellValueFactory(new PropertyValueFactory<>("gastosEnvio"));
        tiempoColumn.setCellValueFactory(new PropertyValueFactory<>("tiempoPreparacion"));
    }

    @FXML
    private void handleAddArticulo() {
        // Crear un diálogo personalizado para añadir artículo
        Dialog<ButtonType> dialog = new Dialog<>();
        dialog.setTitle("Añadir Artículo");
        dialog.setHeaderText("Introduce los datos del nuevo artículo");

        // Crear campos de entrada
        TextField descripcionField = new TextField();
        descripcionField.setPromptText("Descripción");

        TextField precioField = new TextField();
        precioField.setPromptText("Precio de Venta");

        TextField envioField = new TextField();
        envioField.setPromptText("Gastos de Envío");

        TextField tiempoField = new TextField();
        tiempoField.setPromptText("Tiempo de Preparación (minutos)");

        // Layout
        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.add(new Label("Descripción:"), 0, 0);
        grid.add(descripcionField, 1, 0);
        grid.add(new Label("Precio Venta:"), 0, 1);
        grid.add(precioField, 1, 1);
        grid.add(new Label("Gastos Envío:"), 0, 2);
        grid.add(envioField, 1, 2);
        grid.add(new Label("Tiempo Prep.:"), 0, 3);
        grid.add(tiempoField, 1, 3);

        dialog.getDialogPane().setContent(grid);
        dialog.getDialogPane().getButtonTypes().addAll(ButtonType.OK, ButtonType.CANCEL);

        dialog.showAndWait().ifPresent(buttonType -> {
            if (buttonType == ButtonType.OK) {
                try {
                    String descripcion = descripcionField.getText();
                    double precio = Double.parseDouble(precioField.getText());
                    double envio = Double.parseDouble(envioField.getText());
                    int tiempo = Integer.parseInt(tiempoField.getText());

                    // Código automático
                    String codigo = articuloCtrl.obtenerSiguienteCodigo();

                    articuloCtrl.agregarArticulo(codigo, descripcion, precio, envio, tiempo);
                    outputArea.appendText("Artículo añadido: " + codigo + " - " + descripcion + "\n");

                    // Recargar la tabla
                    handleShowArticulos();
                } catch (Exception e) {
                    outputArea.appendText("Error al añadir artículo: " + e.getMessage() + "\n");
                }
            }
        });
    }

    @FXML
    private void handleShowArticulos() {
        try {
            List<Articulo> articulos = articuloCtrl.listarArticulos();
            ObservableList<Articulo> observableArticulos = FXCollections.observableArrayList(articulos);
            articulosTable.setItems(observableArticulos);
            outputArea.appendText("Artículos cargados: " + articulos.size() + "\n");
        } catch (Exception e) {
            outputArea.appendText("Error al cargar artículos: " + e.getMessage() + "\n");
        }
    }

    @FXML
    private void handleBack() {
        stage.close();
    }
}