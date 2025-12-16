package viewfx;

import controller.ClienteControlador;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.*;
import javafx.beans.property.ReadOnlyStringWrapper;
import model.Cliente;
import model.ClienteEstandar;
import model.ClientePremium;

import java.util.List;

public class ClientePane extends VBox {

    private final ClienteControlador clienteCtrl;

    private final ObservableList<Cliente> data = FXCollections.observableArrayList();
    private final TableView<Cliente> table = new TableView<>(data);

    private final TextField tfNombre = new TextField();
    private final TextField tfDomicilio = new TextField();
    private final TextField tfNif = new TextField();
    private final TextField tfEmail = new TextField();

    // Tipo: E / P (lo que espera tu controlador)
    private final ComboBox<String> cbTipo = new ComboBox<>();

    private final RadioButton rbTodos = new RadioButton("Todos");
    private final RadioButton rbEstandar = new RadioButton("Cliente Estándar");
    private final RadioButton rbPremium = new RadioButton("Cliente Premium");

    public ClientePane(ClienteControlador clienteCtrl) {
        this.clienteCtrl = clienteCtrl;

        setSpacing(10);
        setPadding(new Insets(12));

        table.setPlaceholder(new Label("No hay clientes para mostrar"));

        // ---- Inputs ----
        tfNombre.setPromptText("Nombre");
        tfDomicilio.setPromptText("Domicilio");
        tfNif.setPromptText("NIF");
        tfEmail.setPromptText("Email");

        // Importante: valores E/P
        cbTipo.getItems().addAll("E", "P");
        cbTipo.setValue("E");
        cbTipo.setPrefWidth(90);

        Label lblNuevo = new Label("Nuevo:");
        HBox rowInputs = new HBox(10, lblNuevo, tfNombre, tfDomicilio, tfNif, tfEmail, cbTipo);
        rowInputs.setAlignment(Pos.CENTER_LEFT);

        // Grow para que no se corte
        HBox.setHgrow(tfDomicilio, Priority.ALWAYS);
        tfDomicilio.setMinWidth(240);

        tfNombre.setPrefWidth(180);
        tfNif.setPrefWidth(140);
        tfEmail.setPrefWidth(220);

        // ---- Filtros ----
        ToggleGroup filtros = new ToggleGroup();
        rbTodos.setToggleGroup(filtros);
        rbEstandar.setToggleGroup(filtros);
        rbPremium.setToggleGroup(filtros);
        rbTodos.setSelected(true);

        HBox rowFiltro = new HBox(14, new Label("Filtro:"), rbTodos, rbEstandar, rbPremium);
        rowFiltro.setAlignment(Pos.CENTER_LEFT);

        // ---- Botones (abajo, como Artículos) ----
        Button btnAdd = new Button("Añadir cliente");
        btnAdd.getStyleClass().add("primary-button");
        btnAdd.setOnAction(e -> onAdd());

        Button btnDel = new Button("Eliminar cliente");
        btnDel.getStyleClass().add("ghost-button");
        btnDel.setOnAction(e -> onDelete());

        Button btnClear = new Button("Limpiar");
        btnClear.getStyleClass().add("ghost-button");
        btnClear.setOnAction(e -> limpiarFormulario());

        Button btnRefresh = new Button("Refrescar");
        btnRefresh.getStyleClass().add("ghost-button");
        btnRefresh.setOnAction(e -> refrescar());

        HBox rowButtons = new HBox(10, btnAdd, btnDel, btnClear, btnRefresh);
        rowButtons.setAlignment(Pos.CENTER_LEFT);

        // ---- Tabla ----
        TableColumn<Cliente, String> cNif = new TableColumn<>("NIF");
        cNif.setCellValueFactory(new PropertyValueFactory<>("nif"));

        TableColumn<Cliente, String> cNombre = new TableColumn<>("Nombre");
        cNombre.setCellValueFactory(new PropertyValueFactory<>("nombre"));

        TableColumn<Cliente, String> cDom = new TableColumn<>("Domicilio");
        cDom.setCellValueFactory(new PropertyValueFactory<>("domicilio"));

        TableColumn<Cliente, String> cEmail = new TableColumn<>("Email");
        cEmail.setCellValueFactory(new PropertyValueFactory<>("email"));

        // Tipo calculado (no depende de getTipo())
        TableColumn<Cliente, String> cTipo = new TableColumn<>("Tipo de cliente");
        cTipo.setCellValueFactory(cell -> new ReadOnlyStringWrapper(tipoCliente(cell.getValue())));

        table.getColumns().setAll(cNif, cNombre, cDom, cEmail, cTipo);
        table.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY_FLEX_LAST_COLUMN);

        // Selección rellena formulario
        table.getSelectionModel().selectedItemProperty().addListener((obs, old, sel) -> {
            if (sel != null) {
                tfNif.setText(sel.getNif());
                tfNombre.setText(sel.getNombre());
                tfDomicilio.setText(sel.getDomicilio());
                tfEmail.setText(sel.getEmail());
                cbTipo.setValue(sel instanceof ClientePremium ? "P" : "E");
            }
        });

        // refrescar al cambiar filtros
        filtros.selectedToggleProperty().addListener((o, a, b) -> refrescar());

        getChildren().addAll(rowInputs, rowFiltro, rowButtons, table);

        refrescar();
    }

    private void onAdd() {
        try {
            String nombre = tfNombre.getText().trim();
            String domicilio = tfDomicilio.getText().trim();
            String nif = tfNif.getText().trim();
            String email = tfEmail.getText().trim();
            String tipo = cbTipo.getValue(); // "E" o "P"

            if (nif.isEmpty()) throw new Exception("El NIF es obligatorio.");
            if (nombre.isEmpty()) throw new Exception("El nombre es obligatorio.");

            // ✅ Tu método real
            clienteCtrl.agregarCliente(nombre, domicilio, nif, email, tipo);

            FxMsg.info("OK", "Cliente añadido correctamente.");
            limpiarFormulario();
            refrescar();

        } catch (Exception ex) {
            FxMsg.error("Error", ex.getMessage());
        }
    }

    private void onDelete() {
        try {
            Cliente sel = table.getSelectionModel().getSelectedItem();
            String nif = (sel != null) ? sel.getNif() : tfNif.getText().trim();

            if (nif.isEmpty()) {
                FxMsg.error("Falta NIF", "Selecciona un cliente o escribe su NIF para eliminar.");
                return;
            }

            if (!FxMsg.confirm("Confirmar", "¿Seguro que quieres eliminar el cliente con NIF " + nif + "?")) {
                return;
            }

            // ✅ Tu método real en el controlador
            clienteCtrl.eliminar(nif);

            FxMsg.info("OK", "Cliente eliminado correctamente.");
            limpiarFormulario();
            refrescar();

        } catch (Exception ex) {
            FxMsg.error("Error", ex.getMessage());
        }
    }

    private void limpiarFormulario() {
        tfNombre.clear();
        tfDomicilio.clear();
        tfNif.clear();
        tfEmail.clear();
        cbTipo.setValue("E");
        table.getSelectionModel().clearSelection();
    }

    private void refrescar() {
        List<Cliente> list;

        if (rbEstandar.isSelected()) {
            list = clienteCtrl.listarClientesEstandar();
        } else if (rbPremium.isSelected()) {
            list = clienteCtrl.listarClientesPremium();
        } else {
            list = clienteCtrl.listarClientes();
        }

        data.setAll(list);
    }

    private String tipoCliente(Cliente c) {
        if (c instanceof ClientePremium) return "Premium";
        if (c instanceof ClienteEstandar) return "Estandar";
        return "Desconocido";
    }
}