package controller;

import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import model.Cliente;
import model.ClienteEstandar;

import java.util.List;

public class ClienteController {

    @FXML
    private TableView<Cliente> clientesTable;
    @FXML
    private TableColumn<Cliente, String> nifColumn;
    @FXML
    private TableColumn<Cliente, String> nombreColumn;
    @FXML
    private TableColumn<Cliente, String> emailColumn;
    @FXML
    private TableColumn<Cliente, String> domicilioColumn;
    @FXML
    private TableColumn<Cliente, String> tipoColumn;
    @FXML
    private TextArea outputArea;

    private ClienteControlador clienteCtrl;
    private Stage stage;

    public void setClienteControlador(ClienteControlador clienteCtrl) {
        this.clienteCtrl = clienteCtrl;
    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    @FXML
    private void initialize() {
        // Configurar las columnas de la tabla
        nifColumn.setCellValueFactory(new PropertyValueFactory<>("nif"));
        nombreColumn.setCellValueFactory(new PropertyValueFactory<>("nombre"));
        emailColumn.setCellValueFactory(new PropertyValueFactory<>("email"));
        domicilioColumn.setCellValueFactory(new PropertyValueFactory<>("domicilio"));
        tipoColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getClass().getSimpleName()));
    }

    @FXML
    private void handleAddCliente() {
        // Crear un diálogo personalizado para añadir cliente
        Dialog<ButtonType> dialog = new Dialog<>();
        dialog.setTitle("Añadir Cliente");
        dialog.setHeaderText("Introduce los datos del nuevo cliente");

        // Crear campos de entrada
        TextField nifField = new TextField();
        nifField.setPromptText("NIF");

        TextField nombreField = new TextField();
        nombreField.setPromptText("Nombre");

        TextField emailField = new TextField();
        emailField.setPromptText("Email");

        TextField domicilioField = new TextField();
        domicilioField.setPromptText("Domicilio");

        // Layout
        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.add(new Label("NIF:"), 0, 0);
        grid.add(nifField, 1, 0);
        grid.add(new Label("Nombre:"), 0, 1);
        grid.add(nombreField, 1, 1);
        grid.add(new Label("Email:"), 0, 2);
        grid.add(emailField, 1, 2);
        grid.add(new Label("Domicilio:"), 0, 3);
        grid.add(domicilioField, 1, 3);

        dialog.getDialogPane().setContent(grid);
        dialog.getDialogPane().getButtonTypes().addAll(ButtonType.OK, ButtonType.CANCEL);

        dialog.showAndWait().ifPresent(buttonType -> {
            if (buttonType == ButtonType.OK) {
                try {
                    String nif = nifField.getText();
                    String nombre = nombreField.getText();
                    String email = emailField.getText();
                    String domicilio = domicilioField.getText();

                    // Crear cliente estándar por defecto
                    clienteCtrl.agregarCliente(nombre, domicilio, nif, email, "E");
                    outputArea.appendText("Cliente añadido: " + nif + " - " + nombre + "\n");

                    // Recargar la tabla
                    handleShowClientes();
                } catch (Exception e) {
                    outputArea.appendText("Error al añadir cliente: " + e.getMessage() + "\n");
                }
            }
        });
    }

    @FXML
    private void handleShowClientes() {
        try {
            List<Cliente> clientes = clienteCtrl.listarClientes();
            ObservableList<Cliente> observableClientes = FXCollections.observableArrayList(clientes);
            clientesTable.setItems(observableClientes);
            outputArea.appendText("Clientes cargados: " + clientes.size() + "\n");
        } catch (Exception e) {
            outputArea.appendText("Error al cargar clientes: " + e.getMessage() + "\n");
        }
    }

    @FXML
    private void handleBack() {
        stage.close();
    }
}