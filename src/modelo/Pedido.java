package modelo;

import java.time.LocalDateTime;

public class Pedido {
    private String numeroPedido;
    private Cliente cliente;
    private Articulo articulo;
    private int cantidad;
    private LocalDateTime fechaHora;

    public Pedido(String numeroPedido, Cliente cliente, Articulo articulo, int cantidad, LocalDateTime fechaHora) {
        if (numeroPedido == null || numeroPedido.isBlank()) throw new IllegalArgumentException("Número de pedido vacío");
        if (cliente == null || articulo == null) throw new IllegalArgumentException("Cliente/Artículo requeridos");
        if (cantidad <= 0) throw new IllegalArgumentException("Cantidad debe ser > 0");
        this.numeroPedido = numeroPedido.trim();
        this.cliente = cliente;
        this.articulo = articulo;
        this.cantidad = cantidad;
        this.fechaHora = (fechaHora == null) ? LocalDateTime.now() : fechaHora;
    }

    // Getters y setters
    public String getNumeroPedido() { return numeroPedido; }
    public void setNumeroPedido(String numeroPedido) { this.numeroPedido = numeroPedido; }
    public Cliente getCliente() { return cliente; }
    public void setCliente(Cliente cliente) { this.cliente = cliente; }
    public Articulo getArticulo() { return articulo; }
    public void setArticulo(Articulo articulo) { this.articulo = articulo; }
    public int getCantidad() { return cantidad; }
    public void setCantidad(int cantidad) { this.cantidad = cantidad; }
    public LocalDateTime getFechaHora() { return fechaHora; }
    public void setFechaHora(LocalDateTime fechaHora) { this.fechaHora = fechaHora; }

    /** Total = (precioVenta + gastosEnvio*(1-descuentoCliente)) * cantidad */
    public double calcularTotal() {
        double precio = articulo.getPrecioVenta();
        double envioConDescuento = articulo.getGastosEnvio() * (1.0 - cliente.getDescuento());
        return (precio + envioConDescuento) * cantidad;
    }

    @Override
    public String toString() {
        return "Pedido{" +
                "numeroPedido='" + numeroPedido + '\'' +
                ", cliente=" + cliente +
                ", articulo=" + articulo +
                ", cantidad=" + cantidad +
                ", fechaHora=" + fechaHora +
                ", total=" + String.format("%.2f", calcularTotal()) +
                '}';
    }
}