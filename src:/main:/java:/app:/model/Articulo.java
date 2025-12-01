//Modelo: Contiene la lógica y los datos esenciales de la aplicación.
//Representa el estado de la aplicación y reglas de negocio. Puede usar DAO para manipular la persistencia de datos.
package app.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

//ANOTACIONES JPA
@Entity //Indica que esta clase es una entidad de persistencia
@Table(name = "articulo") // Especificamos el nombre de la tabla en MySQL
public class Articulo {
    @Id // Marcamos el campo como clave primaria
    private String codigo;
    @Column(name = "descripcion")
    private String descripcion;
    @Column (name = "precio_venta")
    private double precioVenta;
    @Column (name = "gastos_envio")
    private double gastosEnvio;
    @Column (name = "tiempo_preparacion")
    private int tiempoPreparacion;

    public Articulo() {} //Necesario un constructor vacio para JPA

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