package model;

public class Articulo {
    private String codigo;
    private String descripcion;
    private double precioVenta;
    private double gastosEnvio;
    private int tiempoPreparacion;

    public Articulo(String codigo, String descripcion, double precioVenta, double gastosEnvio, int tiempoPreparacion) {
        this.codigo = codigo;
        this.descripcion = descripcion;
        this.precioVenta = precioVenta;
        this.gastosEnvio = gastosEnvio;
        this.tiempoPreparacion = tiempoPreparacion;
    }
    //GETTERS Y SETTERS
    public String getCodigo() {
        return this.codigo;
    }
    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }
    public String getDescripcion() {
        return this.descripcion;
    }
    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }
    public double getPrecioVenta() {
        return this.precioVenta;
    }
    public void setPrecioVenta(double precioVenta) {
        this.precioVenta = precioVenta;
    }
    public double getGastosEnvio() {
        return this.gastosEnvio;
    }
    public void setGastosEnvio(double gastosEnvio) {
        this.gastosEnvio = gastosEnvio;
    }
    public int getTiempoPreparacion() {
        return this.tiempoPreparacion;
    }
    public void setTiempoPreparacion(int tiempoPreparacion) {
        this.tiempoPreparacion = tiempoPreparacion;
    }

    @Override
    public String toString() {
        return String.format(
                "Código: %s, Descripción: %s, Precio de Venta: %.2f, Gastos de Envío: %.2f, Tiempo Preparación: %d min",
                this.codigo,
                this.descripcion,
                this.precioVenta,
                this.gastosEnvio,
                this.tiempoPreparacion
        );
    }
}

