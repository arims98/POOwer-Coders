//Modelo: Contiene la lógica y los datos esenciales de la aplicación.
//Representa el estado de la aplicación y reglas de negocio. Puede usar DAO para manipular la persistencia de datos.
package model;

import jakarta.persistence.*;

@Entity
@Table(name = "ARTICULO")
public class Articulo {
    
    @Id
    @Column(name = "codigo", length = 10, nullable = false)
    private String codigo;
    
    @Column(name = "descripcion", length = 255, nullable = false)
    private String descripcion;
    
    @Column(name = "precio_venta", nullable = false)
    private double precioVenta;
    
    @Column(name = "gastos_envio", nullable = false)
    private double gastosEnvio;
    
    @Column(name = "tiempo_preparacion", nullable = false)
    private int tiempoPreparacion;

    // Constructor vacío requerido por JPA
    public Articulo() {
    }

    public Articulo(String codigo, String descripcion, double precioVenta, double gastosEnvio, int tiempoPreparacion) {
        this.codigo = codigo;
        this.descripcion = descripcion;
        this.precioVenta = precioVenta;
        this.gastosEnvio = gastosEnvio;
        this.tiempoPreparacion = tiempoPreparacion;
    }
    //GETTERS Y SETTERS
    //Código
    public String getCodigo() {
        return this.codigo;
    }
    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }
    //Descripción
    public String getDescripcion() {
        return this.descripcion;
    }
    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }
    //Precio de Venta
    public double getPrecioVenta() {
        return this.precioVenta;
    }
    public void setPrecioVenta(double precioVenta) {
        this.precioVenta = precioVenta;
    }
    //Gastos de Envio
    public double getGastosEnvio() {
        return this.gastosEnvio;
    }
    public void setGastosEnvio(double gastosEnvio) {
        this.gastosEnvio = gastosEnvio;
    }
    //Tiempo de Preparación
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