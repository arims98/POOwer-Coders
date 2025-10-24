package model;
import java.time.LocalDateTime;

public class Pedido {
    private int numeroPedido;
    private Cliente cliente;
    private Articulo articulo;
    private int cantidad;
    private LocalDateTime fechaHora;
    private String estado;

    // Constructor completo
    public Pedido(Cliente cliente, Articulo articulo, int cantidad, LocalDateTime fechaHora, String estado) {
        this.cliente = cliente;
        this.articulo = articulo;
        this.cantidad = cantidad;
        this.fechaHora = fechaHora;
        this.estado = estado;
    }

    // Constructor de estado por defecto
    public Pedido(Cliente cliente, Articulo articulo, int cantidad, LocalDateTime fechaHora) {
        this(cliente, articulo, cantidad, fechaHora, "Pendiente de envío");
    }

    // Getters y setters
    //NumeroPedido
    public int getNumeroPedido() { return this.numeroPedido; }
    public void setNumeroPedido(int numeroPedido) { this.numeroPedido = numeroPedido; }
    //Cliente
    public Cliente getCliente() { return this.cliente; }
    public void setCliente(Cliente cliente) { this.cliente = cliente; }
    //Articulo
    public Articulo getArticulo() { return this.articulo; }
    public void setArticulo(Articulo articulo) { this.articulo = articulo; }
    //Cantidad
    public int getCantidad() { return this.cantidad; }
    public void setCantidad(int cantidad) { this.cantidad = cantidad; }
    //Fecha
    public LocalDateTime getFechaHora() { return this.fechaHora; }
    public void setFechaHora(LocalDateTime fechaHora) { this.fechaHora = fechaHora; }
    //Estado de envio
    public String getEstado() { return estado; }
    public void setEstado(String estado) { this.estado = estado; }

//===================================================================================================
// METODOS
//===================================================================================================
    public double calcularTotal() {
        double costeArticulos = articulo.getPrecioVenta() * cantidad;
        double envioConDescuento = articulo.getGastosEnvio() * (1 - cliente.getDescuento());
        return (costeArticulos + envioConDescuento);
    }

    public boolean pedidoEnviado() {
        LocalDateTime limiteCancelacion = fechaHora.plusMinutes(articulo.getTiempoPreparacion());
        return LocalDateTime.now().isAfter(limiteCancelacion);
    }

    @Override
    public String toString() {
        String tipoCliente = cliente instanceof ClientePremium ? "Premium" : "Estandar";
        return String.format(
                "Pedido %s: Cliente: %s (%s), Artículo: %s, Cantidad: %d, Fecha: %s, Estado: %s, Total: %.2f, Enviado: %b ",
                numeroPedido,
                cliente.getNombre(),
                tipoCliente,
                articulo.getDescripcion(),
                cantidad,
                fechaHora,
                estado,
                calcularTotal(),
                pedidoEnviado()
        );
    }
}


