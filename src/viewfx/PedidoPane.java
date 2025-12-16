package viewfx;

import controller.ArticuloControlador;
import controller.ClienteControlador;
import controller.PedidoControlador;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import model.Articulo;
import model.Cliente;
import model.Pedido;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class PedidoPane extends VBox {

    private final PedidoControlador pedidoCtrl;
    private final ClienteControlador clienteCtrl;
    private final ArticuloControlador articuloCtrl;

    private final ObservableList<Pedido> data = FXCollections.observableArrayList();
    private final TableView<Pedido> table = new TableView<>(data);

    // Crear
    private final TextField tfNumPedido = new TextField();
    private final TextField tfNif = new TextField();
    private final TextField tfCodArticulo = new TextField();
    private final TextField tfCantidad = new TextField();

    // Buscar/Eliminar
    private final TextField tfBuscarNum = new TextField();

    // Filtros
    private final ToggleGroup tgFiltro = new ToggleGroup();
    private final RadioButton rbTodos = new RadioButton("Todos");
    private final RadioButton rbPend = new RadioButton("Pendientes");
    private final RadioButton rbEnv = new RadioButton("Enviados");
    private final TextField tfFiltroNif = new TextField();

    public PedidoPane(PedidoControlador pedidoCtrl, ClienteControlador clienteCtrl, ArticuloControlador articuloCtrl) {
        this.pedidoCtrl = pedidoCtrl;
        this.clienteCtrl = clienteCtrl;
        this.articuloCtrl = articuloCtrl;

        setSpacing(10);
        setPadding(new Insets(12));

        table.setPlaceholder(new Label("No hay pedidos para mostrar"));

        // ---- Formulario CREAR ----
        tfNumPedido.setPromptText("Nº pedido (opcional)");
        tfNif.setPromptText("NIF cliente");
        tfCodArticulo.setPromptText("Código artículo");
        tfCantidad.setPromptText("Cantidad");

        Button btnCrear = new Button("Crear pedido");
        btnCrear.setOnAction(e -> onCrear());

        HBox formCrear = new HBox(8,
                new Label("Nuevo:"),
                tfNumPedido, tfNif, tfCodArticulo, tfCantidad,
                btnCrear
        );

        // ---- Buscar / Eliminar ----
        tfBuscarNum.setPromptText("Nº pedido (ej: P01)");

        Button btnBuscar = new Button("Buscar");
        btnBuscar.setOnAction(e -> onBuscar());

        Button btnEliminar = new Button("Eliminar");
        btnEliminar.setOnAction(e -> onEliminar());

        HBox formGestion = new HBox(8,
                new Label("Gestión:"),
                tfBuscarNum, btnBuscar, btnEliminar
        );

        // ---- Filtros ----
        rbTodos.setToggleGroup(tgFiltro);
        rbPend.setToggleGroup(tgFiltro);
        rbEnv.setToggleGroup(tgFiltro);
        rbTodos.setSelected(true);

        tfFiltroNif.setPromptText("Filtrar por NIF (opcional)");

        tgFiltro.selectedToggleProperty().addListener((o, a, b) -> refrescar());
        tfFiltroNif.textProperty().addListener((o, a, b) -> refrescar());

        HBox filtros = new HBox(12,
                new Label("Filtro:"),
                rbTodos, rbPend, rbEnv,
                new Label("NIF:"),
                tfFiltroNif
        );

        // ---- Tabla ----
        TableColumn<Pedido, String> cNum = new TableColumn<>("Nº Pedido");
        cNum.setCellValueFactory(p -> new SimpleStringProperty(p.getValue().getNumeroPedido()));

        TableColumn<Pedido, String> cNif = new TableColumn<>("NIF Cliente");
        cNif.setCellValueFactory(p -> new SimpleStringProperty(p.getValue().getCliente().getNif()));

        TableColumn<Pedido, String> cArt = new TableColumn<>("Artículo");
        cArt.setCellValueFactory(p -> new SimpleStringProperty(p.getValue().getArticulo().getCodigo()));

        TableColumn<Pedido, Integer> cCant = new TableColumn<>("Cantidad");
        cCant.setCellValueFactory(p -> new SimpleObjectProperty<>(p.getValue().getCantidad()));

        TableColumn<Pedido, String> cEstado = new TableColumn<>("Estado");
        cEstado.setCellValueFactory(p -> new SimpleStringProperty(p.getValue().getEstado()));

        TableColumn<Pedido, LocalDateTime> cFecha = new TableColumn<>("Fecha/Hora");
        cFecha.setCellValueFactory(p -> new SimpleObjectProperty<>(p.getValue().getFechaHora()));

        // ✅ Formato fecha/hora
        DateTimeFormatter fmt = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
        cFecha.setCellFactory(col -> new TableCell<>() {
            @Override
            protected void updateItem(LocalDateTime item, boolean empty) {
                super.updateItem(item, empty);
                setText(empty || item == null ? "" : fmt.format(item));
            }
        });

        table.getColumns().setAll(cNum, cNif, cArt, cCant, cEstado, cFecha);
        table.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY_FLEX_LAST_COLUMN);

        Button btnRefresh = new Button("Refrescar");
        btnRefresh.setOnAction(e -> refrescar());

        getChildren().addAll(formCrear, formGestion, filtros, btnRefresh, table);

        refrescar();
    }

    private void onCrear() {
        try {
            String numero = tfNumPedido.getText().trim();
            String nif = tfNif.getText().trim();
            String cod = tfCodArticulo.getText().trim();
            int cantidad = Integer.parseInt(tfCantidad.getText().trim());

            if (nif.isEmpty() || cod.isEmpty()) {
                FxMsg.error("Faltan datos", "NIF y Código de artículo son obligatorios.");
                return;
            }

            Cliente cliente = clienteCtrl.listarClientes().stream()
                    .filter(c -> c.getNif().equalsIgnoreCase(nif))
                    .findFirst().orElse(null);

            if (cliente == null) {
                FxMsg.error("Cliente no encontrado", "No existe cliente con NIF: " + nif);
                return;
            }

            Articulo articulo = articuloCtrl.listarArticulos().stream()
                    .filter(a -> a.getCodigo().equalsIgnoreCase(cod))
                    .findFirst().orElse(null);

            if (articulo == null) {
                FxMsg.error("Artículo no encontrado", "No existe artículo con código: " + cod);
                return;
            }

            pedidoCtrl.crearPedido(numero.isEmpty() ? null : numero, cliente, articulo, cantidad);

            tfNumPedido.clear();
            tfNif.clear();
            tfCodArticulo.clear();
            tfCantidad.clear();

            FxMsg.info("OK", "Pedido creado correctamente.");
            refrescar();

        } catch (NumberFormatException ex) {
            FxMsg.error("Formato incorrecto", "Cantidad debe ser un número entero.");
        } catch (Exception ex) {
            FxMsg.error("Error", ex.getMessage());
        }
    }

    private void onBuscar() {
        String num = tfBuscarNum.getText().trim();
        if (num.isEmpty()) {
            FxMsg.error("Falta número", "Introduce el número de pedido (ej: P01).");
            return;
        }

        Pedido p = pedidoCtrl.buscarPedido(num);
        if (p == null) {
            FxMsg.error("No encontrado", "No existe el pedido " + num);
            return;
        }

        FxMsg.info("Pedido encontrado",
                p.getNumeroPedido()
                        + " | " + p.getCliente().getNif()
                        + " | " + p.getArticulo().getCodigo()
                        + " | x" + p.getCantidad()
                        + " | " + p.getEstado());
    }

    private void onEliminar() {
        try {
            String num = tfBuscarNum.getText().trim();
            if (num.isEmpty()) {
                FxMsg.error("Falta número", "Introduce el número de pedido para eliminar (ej: P01).");
                return;
            }

            if (!FxMsg.confirm("Confirmar", "¿Seguro que quieres eliminar el pedido " + num + "?")) {
                return;
            }

            pedidoCtrl.eliminarPedido(num);
            FxMsg.info("OK", "Pedido eliminado correctamente.");
            refrescar();

        } catch (Exception ex) {
            FxMsg.error("Error", ex.getMessage());
        }
    }

    private void refrescar() {
        String nif = tfFiltroNif.getText().trim();
        boolean filtrarPorNif = !nif.isEmpty();

        List<Pedido> lista;

        if (rbPend.isSelected()) {
            lista = filtrarPorNif ? pedidoCtrl.listarPedidosPendientesPorCliente(nif)
                    : pedidoCtrl.listarPedidosPendientes();
        } else if (rbEnv.isSelected()) {
            lista = filtrarPorNif ? pedidoCtrl.listarPedidosEnviadosPorCliente(nif)
                    : pedidoCtrl.listarPedidosEnviados();
        } else {
            lista = pedidoCtrl.listarPedidos();
            if (filtrarPorNif) {
                lista = lista.stream()
                        .filter(p -> p.getCliente().getNif().equalsIgnoreCase(nif))
                        .toList();
            }
        }

        data.setAll(lista);
    }
}