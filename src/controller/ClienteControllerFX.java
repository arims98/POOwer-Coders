package controller;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import model.Cliente;

public class ClienteControllerFX {

    private ClienteControlador clienteCtrl;

    @FXML private TextField nombreField, domicilioField, nifField, emailField, tipoField;

    @FXML private TableView<Cliente> tablaClientes;

    @FXML private TableColumn<Cliente, String> colNombre;
    @FXML private TableColumn<Cliente, String> colDomicilio;
    @FXML private TableColumn<Cliente, String> colNif;
    @FXML private TableColumn<Cliente, String> colEmail;
    @FXML private TableColumn<Cliente, String> colTipo;

    // 🔥 Inicialización automática
    @FXML
    public void initialize() {
        colNombre.setCellValueFactory(new PropertyValueFactory<>("nombre"));
        colDomicilio.setCellValueFactory(new PropertyValueFactory<>("domicilio"));
        colNif.setCellValueFactory(new PropertyValueFactory<>("nif"));
        colEmail.setCellValueFactory(new PropertyValueFactory<>("email"));
        colTipo.setCellValueFactory(new PropertyValueFactory<>("tipo"));
    }

    public void setClienteControlador(ClienteControlador ctrl) {
        this.clienteCtrl = ctrl;
        actualizarTabla();
    }

    @FXML
    public void agregarCliente() {
        try {
            clienteCtrl.agregarCliente(
                nombreField.getText(),
                domicilioField.getText(),
                nifField.getText(),
                emailField.getText(),
                tipoField.getText()
            );

            mostrar("✅ Cliente añadido correctamente.");
            actualizarTabla();

            nombreField.clear();
            domicilioField.clear();
            nifField.clear();
            emailField.clear();
            tipoField.clear();

        } catch (Exception e) {
            mostrar("❌ Error: " + e.getMessage());
        }
    }

    private void actualizarTabla() {
        if (clienteCtrl != null) {
            tablaClientes.setItems(FXCollections.observableArrayList(clienteCtrl.listarClientes()));
        }
    }

    private void mostrar(String msg) {
        Alert a = new Alert(Alert.AlertType.INFORMATION, msg);
        a.show();
    }
    
    @FXML
    public void eliminarCliente() {
        Cliente seleccionado = tablaClientes.getSelectionModel().getSelectedItem();
        if (seleccionado != null) {
            try {
                clienteCtrl.eliminar(seleccionado.getNif());
                actualizarTabla();
                mostrar("✅ Cliente eliminado correctamente.");
            } catch (Exception e) {
                mostrar("❌ Error al eliminar: " + e.getMessage());
            }
        } else {
            mostrar("Selecciona un cliente para eliminarlo");
        }
    }

    @FXML
    public void mostrarPremium() {
        if (clienteCtrl != null) {
            tablaClientes.setItems(FXCollections.observableArrayList(clienteCtrl.listarClientesPremium()));
        }
    }

    @FXML
    public void mostrarEstandar() {
        if (clienteCtrl != null) {
            tablaClientes.setItems(FXCollections.observableArrayList(clienteCtrl.listarClientesEstandar()));
        }
    }

    @FXML
    public void mostrarTodos() {
        actualizarTabla();
    }
    
}
