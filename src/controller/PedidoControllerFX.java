package controller;

import java.util.List;

import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import model.Articulo;
import model.Cliente;
import model.Pedido;

public class PedidoControllerFX {

    private PedidoControlador pedidoCtrl;
    private ClienteControlador clienteCtrl;
    private ArticuloControlador articuloCtrl;

    @FXML private TextField numPedidoField, nifClienteField, codArticuloField, cantidadField;

    @FXML private TableView<Pedido> tablaPedidos;

    @FXML private TableColumn<Pedido, String> colNumero;
    @FXML private TableColumn<Pedido, String> colEstado; // nueva columna para estado
    @FXML private TableColumn<Pedido, String> colCliente;   // ahora String (nombre)
    @FXML private TableColumn<Pedido, String> colArticulo;  // ahora String (descripción)
    @FXML private TableColumn<Pedido, Integer> colCantidad;


    // 🔥 Se ejecuta automáticamente al cargar el FXML
    @FXML
    public void initialize() {

        // Propiedad del número (usa el nombre del getter: getNumeroPedido -> "numeroPedido")
        colNumero.setCellValueFactory(new PropertyValueFactory<>("numeroPedido"));

        colEstado.setCellValueFactory(cellData -> {
        Pedido p = cellData.getValue();
        return new SimpleStringProperty((p != null) ? p.getEstado() : "");
        });

        // Mostrar SOLO el nombre del cliente (con comprobación null)
        colCliente.setCellValueFactory(cellData -> {
            Pedido p = cellData.getValue();
            Cliente c = (p != null) ? p.getCliente() : null;
            String nombre = (c != null && c.getNombre() != null) ? c.getNombre() : "";
            return new SimpleStringProperty(nombre);
        });

        // Mostrar SOLO la descripción del artículo (con comprobación null)
        colArticulo.setCellValueFactory(cellData -> {
            Pedido p = cellData.getValue();
            Articulo a = (p != null) ? p.getArticulo() : null;
            String desc = (a != null && a.getDescripcion() != null) ? a.getDescripcion() : "";
            return new SimpleStringProperty(desc);
        });

        // Cantidad (entero)
        colCantidad.setCellValueFactory(new PropertyValueFactory<>("cantidad"));
    }

    public void setControladores(PedidoControlador pedido, ClienteControlador cliente, ArticuloControlador articulo) {
        this.pedidoCtrl = pedido;
        this.clienteCtrl = cliente;
        this.articuloCtrl = articulo;
        actualizarTabla();
    }

    @FXML
    public void crearPedido() {
        try {
            String num = numPedidoField.getText().isBlank()
                    ? pedidoCtrl.obtenerSiguienteNumeroPedido()
                    : numPedidoField.getText();

            String nif = nifClienteField.getText();
            String cod = codArticuloField.getText();
            int cantidad = Integer.parseInt(cantidadField.getText());

            Cliente cliente = clienteCtrl.listarClientes().stream()
                    .filter(c -> c.getNif().equalsIgnoreCase(nif))
                    .findFirst().orElse(null);
            if (cliente == null)
                throw new Exception("Cliente no encontrado");

            Articulo articulo = articuloCtrl.listarArticulos().stream()
                    .filter(a -> a.getCodigo().equalsIgnoreCase(cod))
                    .findFirst().orElse(null);
            if (articulo == null)
                throw new Exception("Artículo no encontrado");

            pedidoCtrl.crearPedido(num, cliente, articulo, cantidad);
            mostrar("✅ Pedido creado correctamente.");
            actualizarTabla();

            numPedidoField.clear();
            nifClienteField.clear();
            codArticuloField.clear();
            cantidadField.clear();

        } catch (Exception e) {
            mostrar("❌ Error: " + e.getMessage());
        }
    }

    @FXML
    public void eliminarPedido() {
        Pedido seleccionado = tablaPedidos.getSelectionModel().getSelectedItem();
        if (seleccionado != null) {
            try {
                pedidoCtrl.eliminarPedido(seleccionado.getNumeroPedido());
                actualizarTabla();
                mostrar("✅ Pedido eliminado correctamente.");
            } catch (Exception e) {
                mostrar("❌ Error al eliminar: " + e.getMessage());
            }
        } else {
            mostrar("Selecciona un pedido para eliminarlo.");
        }
    }

    @FXML
    public void buscarPedido() {
        String num = numPedidoField.getText();
        if (num != null && !num.isBlank()) {
            Pedido p = pedidoCtrl.buscarPedido(num);
            if (p != null) {
                tablaPedidos.setItems(FXCollections.observableArrayList(p));
            } else {
                mostrar("❌ Pedido no encontrado.");
            }
        } else {
            mostrar("Introduce un número de pedido para buscar.");
        }
    }

    @FXML
    public void mostrarPendientes() {
        tablaPedidos.setItems(FXCollections.observableArrayList(pedidoCtrl.listarPedidosPendientes()));
    }

    @FXML
    public void mostrarEnviados() {
        tablaPedidos.setItems(FXCollections.observableArrayList(pedidoCtrl.listarPedidosEnviados()));
    }

    @FXML
    public void mostrarTodos() {
        actualizarTabla();
    }

    private void actualizarTabla() {
        if (pedidoCtrl != null) {
            List<Pedido> pedidos = pedidoCtrl.listarPedidos();
            tablaPedidos.setItems(FXCollections.observableArrayList(pedidos));
        }
    }

    private void mostrar(String msg) {
        Alert a = new Alert(Alert.AlertType.INFORMATION, msg);
        a.show();
    }
}
