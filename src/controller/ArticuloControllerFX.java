package controller;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import model.Articulo;

public class ArticuloControllerFX {

    private ArticuloControlador articuloControlador;

    @FXML private TextField codigoField;
    @FXML private TextField descripcionField;
    @FXML private TextField precioField;
    @FXML private TextField envioField;
    @FXML private TextField tiempoField;

    @FXML private TableView<Articulo> tablaArticulos;

    @FXML private TableColumn<Articulo, String> colCodigo;
    @FXML private TableColumn<Articulo, String> colDescripcion;
    @FXML private TableColumn<Articulo, Double> colPrecio;
    @FXML private TableColumn<Articulo, Double> colEnvio;
    @FXML private TableColumn<Articulo, Integer> colTiempo;

    // 🔥 Este método se ejecuta AUTOMÁTICAMENTE al cargar el FXML
    @FXML
    public void initialize() {

        // --- MAPEOS A PROPIEDADES DEL MODELO ---
        colCodigo.setCellValueFactory(new PropertyValueFactory<>("codigo"));
        colDescripcion.setCellValueFactory(new PropertyValueFactory<>("descripcion"));
        colPrecio.setCellValueFactory(new PropertyValueFactory<>("precioVenta"));
        colEnvio.setCellValueFactory(new PropertyValueFactory<>("gastosEnvio"));
        colTiempo.setCellValueFactory(new PropertyValueFactory<>("tiempoPreparacion"));
    }

    public void setArticuloControlador(ArticuloControlador ctrl) {
        this.articuloControlador = ctrl;
        actualizarTabla();
    }

    @FXML
    public void agregarArticulo() {
        try {
            String cod = codigoField.getText().isBlank()
                    ? articuloControlador.obtenerSiguienteCodigo()
                    : codigoField.getText();

            String desc = descripcionField.getText();
            double precio = Double.parseDouble(precioField.getText());
            double envio = Double.parseDouble(envioField.getText());
            int tiempo = Integer.parseInt(tiempoField.getText());

            articuloControlador.agregarArticulo(cod, desc, precio, envio, tiempo);
            mostrar("✅ Artículo añadido correctamente.");
            actualizarTabla();

            codigoField.clear();
            descripcionField.clear();
            precioField.clear();
            envioField.clear();
            tiempoField.clear();
        } catch (Exception e) {
            mostrar("❌ Error: " + e.getMessage());
        }
    }

    private void actualizarTabla() {
        if (articuloControlador != null) {
            tablaArticulos.setItems(
                FXCollections.observableArrayList(articuloControlador.listarArticulos())
            );
        }
    }

    private void mostrar(String msg) {
        Alert a = new Alert(Alert.AlertType.INFORMATION, msg);
        a.show();
    }

    @FXML
    public void eliminarArticulo() {
        try {
            Articulo seleccionado = tablaArticulos.getSelectionModel().getSelectedItem();

            if (seleccionado == null) {
                mostrar("⚠️ Selecciona un artículo de la tabla para eliminarlo.");
                return;
            }

            // Confirmación opcional
            Alert confirm = new Alert(Alert.AlertType.CONFIRMATION);
            confirm.setHeaderText(null);
            confirm.setContentText("¿Seguro que deseas eliminar el artículo " + seleccionado.getCodigo() + "?");

            if (confirm.showAndWait().get().getButtonData().isCancelButton()) {
                return;
            }

            // Llamada al DAO a través del controlador
            articuloControlador.eliminarArticulo(seleccionado.getCodigo());

            mostrar("🗑️ Artículo eliminado correctamente.");
            actualizarTabla();

        } catch (Exception e) {
            mostrar("❌ Error eliminando artículo: " + e.getMessage());
        }
    }

}
