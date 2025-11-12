//Modelo: Contiene la lógica y los datos esenciales de la aplicación.
//Representa el estado de la aplicación y reglas de negocio. Puede usar DAO para manipular la persistencia de datos.
package model;

import java.time.LocalDateTime;

public class Pedido {
    private String numeroPedido;
    private Cliente cliente;
    private Articulo articulo;
    private int cantidad;
    private LocalDateTime fechaHora;
    private String estado;

    // Constructor completo
    public Pedido(String numeroPedido, Cliente cliente, Articulo articulo, int cantidad, LocalDateTime fechaHora, String estado) {
        this.numeroPedido = numeroPedido;
        this.cliente = cliente;
        this.articulo = articulo;
        this.cantidad = cantidad;
        this.fechaHora = fechaHora;
        this.estado = estado;
    }

    // Constructor de estado por defecto
    public Pedido(String numeroPedido, Cliente cliente, Articulo articulo, int cantidad, LocalDateTime fechaHora) {
        this(numeroPedido, cliente, articulo, cantidad, fechaHora, "Pendiente de envío");
    }

    // Getters y setters
    //NumeroPedido
    public String getNumeroPedido() { return this.numeroPedido; }
    public void setNumeroPedido(String numeroPedido) { this.numeroPedido = numeroPedido; }
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

    @Override
    public String toString() {
        String tipoCliente = this.cliente instanceof ClientePremium ? "Premium" : "Estandar";
        return String.format(
                "Pedido %s: Cliente: %s (%s), Artículo: %s, Cantidad: %d, Fecha: %s, Estado: %s",
                this.numeroPedido,
                this.cliente.getNombre(),
                tipoCliente,
                this.articulo.getDescripcion(),
                this.cantidad,
                this.fechaHora.toString(),
                this.estado
        );
    }
}