package modelo;

import java.util.Objects;

public class Articulo {
    private final String codigo;        // inmutable: identificador único
    private String descripcion;
    private double precioVenta;         // PVP del artículo
    private double gastosEnvio;         // coste de envío por unidad
    private int tiempoPreparacion;      // en horas o días (según vuestro enunciado)

    public Articulo(String codigo, String descripcion, double precioVenta,
                    double gastosEnvio, int tiempoPreparacion) {
        if (codigo == null || codigo.isBlank()) throw new IllegalArgumentException("Código vacío");
        if (precioVenta < 0) throw new IllegalArgumentException("Precio negativo");
        if (gastosEnvio < 0) throw new IllegalArgumentException("Gastos de envío negativos");
        if (tiempoPreparacion < 0) throw new IllegalArgumentException("Tiempo de preparación negativo");
        this.codigo = codigo.trim();
        this.descripcion = descripcion;
        this.precioVenta = precioVenta;
        this.gastosEnvio = gastosEnvio;
        this.tiempoPreparacion = tiempoPreparacion;
    }

    // Getters / setters (sin setCodigo para mantener la inmutabilidad del ID)
    public String getCodigo() { return codigo; }

    public String getDescripcion() { return descripcion; }
    public void setDescripcion(String descripcion) { this.descripcion = descripcion; }

    public double getPrecioVenta() { return precioVenta; }
    public void setPrecioVenta(double precioVenta) {
        if (precioVenta < 0) throw new IllegalArgumentException("Precio negativo");
        this.precioVenta = precioVenta;
    }

    public double getGastosEnvio() { return gastosEnvio; }
    public void setGastosEnvio(double gastosEnvio) {
        if (gastosEnvio < 0) throw new IllegalArgumentException("Gastos de envío negativos");
        this.gastosEnvio = gastosEnvio;
    }

    public int getTiempoPreparacion() { return tiempoPreparacion; }
    public void setTiempoPreparacion(int tiempoPreparacion) {
        if (tiempoPreparacion < 0) throw new IllegalArgumentException("Tiempo de preparación negativo");
        this.tiempoPreparacion = tiempoPreparacion;
    }

    /** Precio total por unidad (venta + envío) para usar en el cálculo de pedidos. */
    public double getPrecioUnidadConEnvio() {
        return precioVenta + gastosEnvio;
    }

    @Override public boolean equals(Object o) {
        return (o instanceof Articulo a) && codigo.equals(a.codigo);
    }
    @Override public int hashCode() { return Objects.hash(codigo); }

    @Override public String toString() {
        return "Articulo{" +
                "codigo='" + codigo + '\'' +
                ", descripcion='" + descripcion + '\'' +
                ", precioVenta=" + precioVenta +
                ", gastosEnvio=" + gastosEnvio +
                ", tiempoPreparacion=" + tiempoPreparacion +
                '}';
    }
}

