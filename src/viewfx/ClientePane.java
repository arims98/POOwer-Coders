package viewfx;

import controller.ClienteControlador;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import model.Cliente;
import model.ClientePremium;

public class ClientePane extends VBox {

    private final ClienteControlador clienteCtrl;

    private final ObservableList<Cliente> data = FXCollections.observableArrayList();
    private final TableView<Cliente> table = new TableView<>(data);

    private final TextField tfNombre = new TextField();
    private final TextField tfDomicilio = new TextField();
    private final TextField tfNif = new TextField();
    private final TextField tfEmail = new TextField();
    private final ChoiceBox<String> cbTipo = new ChoiceBox<>(FXCollections.observableArrayList("E", "P"));

    private final ToggleGroup tgFiltro = new ToggleGroup();
    private final RadioButton rbTodos = new RadioButton("Todos");
    private final RadioButton rbE = new RadioButton("Estándar");
    private final RadioButton rbP = new RadioButton("Premium");

    public ClientePane(ClienteControlador clienteCtrl) {
        this.clienteCtrl = clienteCtrl;

        setSpacing(10);
        setPadding(new Insets(12));

        table.setPlaceholder(new Label("No hay clientes para mostrar"));

        // ---- Formulario ----
        tfNombre.setPromptText("Nombre");
        tfDomicilio.setPromptText("Domicilio");
        tfNif.setPromptText("NIF");
        tfEmail.setPromptText("Email");
        cbTipo.setValue("E");

        Button btnAdd = new Button("Añadir cliente");
        btnAdd.setOnAction(e -> onAdd());

        Button btnDel = new Button("Eliminar por NIF");
        btnDel.setOnAction(e -> onDelete());

        HBox form = new HBox(8,
                new Label("Nuevo:"),
                tfNombre, tfDomicilio, tfNif, tfEmail, cbTipo,
                btnAdd, btnDel
        );

        // ---- Filtros ----
        rbTodos.setToggleGroup(tgFiltro);
        rbE.setToggleGroup(tgFiltro);
        rbP.setToggleGroup(tgFiltro);
        rbTodos.setSelected(true);

        tgFiltro.selectedToggleProperty().addListener((o, a, b) -> refrescar());

        HBox filtros = new HBox(12, new Label("Filtro:"), rbTodos, rbE, rbP);

        // ---- Tabla ----
        TableColumn<Cliente, String> cNif = new TableColumn<>("NIF");
        cNif.setCellValueFactory(c -> new SimpleStringProperty(c.getValue().getNif()));

        TableColumn<Cliente, String> cNom = new TableColumn<>("Nombre");
        cNom.setCellValueFactory(c -> new SimpleStringProperty(c.getValue().getNombre()));

        TableColumn<Cliente, String> cDom = new TableColumn<>("Domicilio");
        cDom.setCellValueFactory(c -> new SimpleStringProperty(c.getValue().getDomicilio()));

        TableColumn<Cliente, String> cEmail = new TableColumn<>("Email");
        cEmail.setCellValueFactory(c -> new SimpleStringProperty(c.getValue().getEmail()));

        TableColumn<Cliente, String> cTipo = new TableColumn<>("Tipo");
        cTipo.setCellValueFactory(c ->
                new SimpleStringProperty((c.getValue() instanceof ClientePremium) ? "Premium" : "Estandar")
        );

        table.getColumns().setAll(cNif, cNom, cDom, cEmail, cTipo);
        table.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY_FLEX_LAST_COLUMN);

        Button btnRefresh = new Button("Refrescar");
        btnRefresh.setOnAction(e -> refrescar());

        getChildren().addAll(form, filtros, btnRefresh, table);

        refrescar();
    }

    private void onAdd() {
        try {
            clienteCtrl.agregarCliente(
                    tfNombre.getText().trim(),
                    tfDomicilio.getText().trim(),
                    tfNif.getText().trim(),
                    tfEmail.getText().trim(),
                    cbTipo.getValue()
            );

            tfNombre.clear();
            tfDomicilio.clear();
            tfNif.clear();
            tfEmail.clear();
            cbTipo.setValue("E");

            FxMsg.info("OK", "Cliente añadido correctamente.");
            refrescar();

        } catch (Exception ex) {
            FxMsg.error("Error", ex.getMessage());
        }
    }

    private void onDelete() {
        try {
            String nif = tfNif.getText().trim();
            if (nif.isEmpty()) {
                FxMsg.error("Falta NIF", "Introduce el NIF en el campo NIF para eliminar.");
                return;
            }

            if (!FxMsg.confirm("Confirmar", "¿Seguro que quieres eliminar el cliente con NIF " + nif + "?")) {
                return;
            }

            clienteCtrl.eliminar(nif);
            FxMsg.info("OK", "Cliente eliminado (si existía).");
            refrescar();

        } catch (Exception ex) {
            FxMsg.error("Error", ex.getMessage());
        }
    }

    private void refrescar() {
        if (rbE.isSelected()) data.setAll(clienteCtrl.listarClientesEstandar());
        else if (rbP.isSelected()) data.setAll(clienteCtrl.listarClientesPremium());
        else data.setAll(clienteCtrl.listarClientes());
    }
}