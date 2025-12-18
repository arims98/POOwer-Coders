package viewfx;

import controller.ArticuloControlador;
import controller.ClienteControlador;
import controller.PedidoControlador;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.*;
import model.Articulo;
import model.Cliente;
import model.Pedido;

import java.time.format.DateTimeFormatter;
import java.util.List;

public class PedidoPane extends VBox {

    private final PedidoControlador pedidoCtrl;
    private final ClienteControlador clienteCtrl;
    private final ArticuloControlador articuloCtrl;

    private final ObservableList<Pedido> data = FXCollections.observableArrayList();
    private final TableView<Pedido> table = new TableView<>(data);

    // Crear pedido
    private final TextField tfNumNuevo = new TextField();
    private final TextField tfNifNuevo = new TextField();
    private final TextField tfCodArtNuevo = new TextField();
    private final TextField tfCantidad = new TextField();

    // Gestión
    private final TextField tfNumGestion = new TextField();

    // Filtros
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

        // ===== Fila inputs: Crear =====
        tfNumNuevo.setPromptText("Nº pedido (opcional)");
        tfNifNuevo.setPromptText("NIF cliente");
        tfCodArtNuevo.setPromptText("Código artículo");
        tfCantidad.setPromptText("Cantidad");

        tfNumNuevo.setPrefWidth(160);
        tfNifNuevo.setPrefWidth(160);
        tfCodArtNuevo.setPrefWidth(170);
        tfCantidad.setPrefWidth(120);

        Button btnCrear = new Button("Crear pedido");
        btnCrear.getStyleClass().add("primary-button");
        btnCrear.setOnAction(e -> onCrear());

        HBox rowCrear = new HBox(10,
                new Label("Nuevo:"),
                tfNumNuevo, tfNifNuevo, tfCodArtNuevo, tfCantidad,
                btnCrear
        );
        rowCrear.setAlignment(Pos.CENTER_LEFT);

        // ===== Fila inputs: Gestión =====
        tfNumGestion.setPromptText("Nº pedido (ej: P01)");
        tfNumGestion.setPrefWidth(200);

        Button btnBuscar = new Button("Buscar");
        btnBuscar.getStyleClass().add("ghost-button");
        btnBuscar.setOnAction(e -> onBuscar());

        Button btnEliminar = new Button("Eliminar");
        btnEliminar.getStyleClass().add("ghost-button");
        btnEliminar.setOnAction(e -> onEliminar());

        HBox rowGestion = new HBox(10,
                new Label("Gestión:"),
                tfNumGestion, btnBuscar, btnEliminar
        );
        rowGestion.setAlignment(Pos.CENTER_LEFT);

        // ===== Fila filtros =====
        ToggleGroup tg = new ToggleGroup();
        rbTodos.setToggleGroup(tg);
        rbPend.setToggleGroup(tg);
        rbEnv.setToggleGroup(tg);
        rbTodos.setSelected(true);

        tfFiltroNif.setPromptText("Filtrar por NIF (opcional)");
        tfFiltroNif.setPrefWidth(220);

        HBox rowFiltro = new HBox(14,
                new Label("Filtro:"),
                rbTodos, rbPend, rbEnv,
                new Label("NIF:"),
                tfFiltroNif
        );
        rowFiltro.setAlignment(Pos.CENTER_LEFT);

        // ===== Botones abajo (estética igual que las otras pestañas) =====
        Button btnLimpiar = new Button("Limpiar");
        btnLimpiar.getStyleClass().add("ghost-button");
        btnLimpiar.setOnAction(e -> limpiar());

        Button btnRefrescar = new Button("Refrescar");
        btnRefrescar.getStyleClass().add("ghost-button");
        btnRefrescar.setOnAction(e -> refrescar());

        HBox rowButtons = new HBox(10, btnLimpiar, btnRefrescar);
        rowButtons.setAlignment(Pos.CENTER_LEFT);

        // refrescar cuando cambian filtros
        tg.selectedToggleProperty().addListener((o,a,b) -> refrescar());
        tfFiltroNif.textProperty().addListener((o,a,b) -> refrescar());

        // ===== Tabla =====
        TableColumn<Pedido, String> cNum = new TableColumn<>("Nº Pedido");
        cNum.setCellValueFactory(new PropertyValueFactory<>("numeroPedido"));

        TableColumn<Pedido, String> cNif = new TableColumn<>("NIF Cliente");
        cNif.setCellValueFactory(cell -> new ReadOnlyObjectWrapper<>(cell.getValue().getCliente().getNif()));

        TableColumn<Pedido, String> cArt = new TableColumn<>("Artículo");
        cArt.setCellValueFactory(cell -> new ReadOnlyObjectWrapper<>(cell.getValue().getArticulo().getCodigo()));

        TableColumn<Pedido, Integer> cCant = new TableColumn<>("Cantidad");
        cCant.setCellValueFactory(new PropertyValueFactory<>("cantidad"));

        TableColumn<Pedido, String> cEstado = new TableColumn<>("Estado");
        cEstado.setCellValueFactory(new PropertyValueFactory<>("estado"));

        TableColumn<Pedido, String> cFecha = new TableColumn<>("Fecha/Hora");
        DateTimeFormatter fmt = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
        cFecha.setCellValueFactory(cell -> new ReadOnlyObjectWrapper<>(
                cell.getValue().getFechaHora() == null ? "" : cell.getValue().getFechaHora().format(fmt)
        ));

        table.getColumns().setAll(cNum, cNif, cArt, cCant, cEstado, cFecha);
        table.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY_FLEX_LAST_COLUMN);

        // seleccionar fila -> rellena nº gestión
        table.getSelectionModel().selectedItemProperty().addListener((obs, old, sel) -> {
            if (sel != null) tfNumGestion.setText(sel.getNumeroPedido());
        });

        getChildren().addAll(rowCrear, rowGestion, rowFiltro, rowButtons, table);

        refrescar();
    }

    private void onCrear() {
        try {
            String num = tfNumNuevo.getText().trim();
            String nif = tfNifNuevo.getText().trim();
            String cod = tfCodArtNuevo.getText().trim();
            String cantStr = tfCantidad.getText().trim();

            if (nif.isEmpty()) throw new Exception("El NIF del cliente es obligatorio.");
            if (cod.isEmpty()) throw new Exception("El código del artículo es obligatorio.");
            int cant = Integer.parseInt(cantStr);

            // buscar cliente y artículo usando los controladores existentes
            Cliente cliente = clienteCtrl.listarClientes().stream()
                    .filter(c -> c.getNif().equalsIgnoreCase(nif))
                    .findFirst().orElse(null);

            if (cliente == null) throw new Exception("Cliente no encontrado: " + nif);

            Articulo art = articuloCtrl.listarArticulos().stream()
                    .filter(a -> a.getCodigo().equalsIgnoreCase(cod))
                    .findFirst().orElse(null);

            if (art == null) throw new Exception("Artículo no encontrado: " + cod);

            pedidoCtrl.crearPedido(num.isEmpty() ? null : num, cliente, art, cant);

            FxMsg.info("OK", "Pedido creado correctamente.");
            limpiar();
            refrescar();

        } catch (NumberFormatException ex) {
            FxMsg.error("Formato incorrecto", "La cantidad debe ser un número entero.");
        } catch (Exception ex) {
            FxMsg.error("Error", ex.getMessage());
        }
    }

    private void onBuscar() {
        try {
            String num = tfNumGestion.getText().trim();
            if (num.isEmpty()) {
                FxMsg.error("Falta Nº pedido", "Escribe un número de pedido para buscar.");
                return;
            }
            Pedido p = pedidoCtrl.buscarPedido(num);
            if (p == null) {
                FxMsg.info("No encontrado", "No existe el pedido " + num);
                return;
            }
            FxMsg.info("Pedido encontrado",
                    p.getNumeroPedido() + " | " + p.getCliente().getNif() + " | " +
                            p.getArticulo().getCodigo() + " | x" + p.getCantidad() + " | " + p.getEstado());
        } catch (Exception ex) {
            FxMsg.error("Error", ex.getMessage());
        }
    }

    private void onEliminar() {
        try {
            Pedido sel = table.getSelectionModel().getSelectedItem();
            String num = (sel != null) ? sel.getNumeroPedido() : tfNumGestion.getText().trim();

            if (num.isEmpty()) {
                FxMsg.error("Falta Nº pedido", "Selecciona un pedido o escribe su número para eliminar.");
                return;
            }

            if (!FxMsg.confirm("Confirmar", "¿Seguro que quieres eliminar el pedido " + num + "?")) {
                return;
            }

            pedidoCtrl.eliminarPedido(num);

            FxMsg.info("OK", "Pedido eliminado correctamente.");
            limpiar();
            refrescar();

        } catch (Exception ex) {
            FxMsg.error("Error", ex.getMessage());
        }
    }

    private void limpiar() {
        tfNumNuevo.clear();
        tfNifNuevo.clear();
        tfCodArtNuevo.clear();
        tfCantidad.clear();
        tfNumGestion.clear();
        // no borro filtro para que sea cómodo, pero si lo quieres:
        // tfFiltroNif.clear();
        table.getSelectionModel().clearSelection();
    }

    private void refrescar() {
        List<Pedido> list;

        if (rbPend.isSelected()) list = pedidoCtrl.listarPedidosPendientes();
        else if (rbEnv.isSelected()) list = pedidoCtrl.listarPedidosEnviados();
        else list = pedidoCtrl.listarPedidos();

        // filtro por NIF (opcional)
        String nif = tfFiltroNif.getText().trim();
        if (!nif.isEmpty()) {
            list = list.stream()
                    .filter(p -> p.getCliente().getNif().equalsIgnoreCase(nif))
                    .toList();
        }

        data.setAll(list);
    }
}